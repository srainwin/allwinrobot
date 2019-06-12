package com.demo.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;

/**
 * @author xwr
 * @Description 包装所有selenium的操作以及通用方法，简化用例中代码量
 */
public class SeleniumUtil {
	public static Logger logger = Logger.getLogger(SeleniumUtil.class.getName());
	public ITestResult it = null;
	public WebDriver driver = null;
	public WebDriver window = null;

	/***
	 * 启动浏览器并打开页面
	 */
	public void launchBrowser(String browserName, ITestContext itestcontext, String testurl, int timeOut) {
		SelectBrowser selectbrowser = new SelectBrowser();
		// 可选择不同的浏览器来启动
		driver = selectbrowser.selectByName(browserName, itestcontext);
		try {
			maxWindow();
			waitForPageLoading(timeOut);
			get(testurl);
			logger.info("成功启动" + browserName + "浏览器，并成功打开测试网址");
		} catch (TimeoutException e) {
			logger.error("注意：页面没有完全加载出来，刷新重试！！", e);
			refresh();
			String status = (String) (executeJS("return document.readyState"));
			logger.info("打印状态：" + status);
		} catch (Exception e) {
			logger.error("启动" + browserName + "浏览器并打开页面异常", e);
		}
	}

	/**
	 * 最大化浏览器操作
	 */
	public void maxWindow() {
		try {
			driver.manage().window().maximize();
			logger.info("成功最大化浏览器");
		} catch (Exception e) {
			logger.error("最大化浏览器失败", e);
		}
	}

	/**
	 * 设定浏览器窗口大小： 设置浏览器窗口的大小有下面两个比较常见的用途：
	 * 1、在统一的浏览器大小下运行用例，可以比较容易的跟一些基于图像比对的工具进行结合
	 * ，提升测试的灵活性及普遍适用性。比如可以跟sikuli结合，使用sikuli操作flash；
	 * 2、在不同的浏览器大小下访问测试站点，对测试页面截图并保存，然后观察或使用图像比对工具对被测页面的前端样式进行评测。
	 * 比如可以将浏览器设置成移动端大小(320x480)，然后访问移动站点，对其样式进行评估；
	 */
	public void setBrowserSize(int width, int height) {
		try {
			driver.manage().window().setSize(new Dimension(width, height));
			logger.info("成功设置浏览器窗口大小");
		} catch (Exception e) {
			logger.error("设置浏览器窗口大小失败", e);
		}

	}

	/**
	 * 地址栏输入网址
	 */
	public void get(String testurl) {
		try {
			driver.get(testurl);
			logger.info("成功打开测试页面:[" + testurl + "]");
		} catch (Exception e) {
			logger.error("打开测试页面异常", e);
		}
	}

	/**
	 * 退出浏览器
	 */
	public void quit() {
		try {
			driver.quit();
			logger.info("成功退出浏览器");
		} catch (Exception e) {
			logger.error("退出浏览器异常", e);
		}
	}

	/**
	 * 关闭网页
	 */
	public void close() {
		try {
			driver.close();
			logger.info("成功关闭网页");
		} catch (Exception e) {
			logger.error("关闭网页异常", e);
		}
	}

	/**
	 * 刷新网页
	 */
	public void refresh() {
		try {
			driver.navigate().refresh();
			logger.info("成功刷新页面");
		} catch (Exception e) {
			logger.error("刷新网页异常", e);
		}
	}

	/**
	 * 后退网页
	 */
	public void back() {
		try {
			driver.navigate().back();
			logger.info("成功回退上一个网页");
		} catch (Exception e) {
			logger.error("回退上一个网页异常", e);
		}
	}

	/**
	 * 前进网页
	 */
	public void forward() {
		try {
			driver.navigate().forward();
			logger.info("成功重返网页");
		} catch (Exception e) {
			logger.error("重返网页异常", e);
		}
	}

	/**
	 * 获取cookies保存到本地文件中，用于需要添加cookies的时候从文件读取
	 */
	public void cookiesSaveInFile(ITestContext itestcontext) {
		pause(2000);
		Set<Cookie> cookies = driver.manage().getCookies();
		String cookiesConfigFilePath = itestcontext.getCurrentXmlTest().getParameter("cookiesConfigFilePath");
		File file = new File(cookiesConfigFilePath);
		if(file.exists()) {
            file.delete();
            logger.info("成功删除旧的cookies文件");
            try {
                file.createNewFile();
                logger.info("成功创建新的cookies文件");
            } catch (Exception e) {
            	logger.error("创建新的cookies文件发生异常",e);
            }
        }
		try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for(Cookie c:cookies) {
                bw.write(c.getName()+" = "+c.getValue());
                bw.newLine();
            }
            bw.flush();
            bw.close();
            logger.info("成功获取网站cookies并写入到cookies文件中");
        } catch (Exception e) {
        	logger.error("获取网站cookies并写入到cookies文件中发生异常",e);
        }
		driver.quit();
	}
	
	/**
	 * 添加cookies,从文件读取cookies后添加，做自动登陆的必要方法
	 */
	public void addcookies(ITestContext itestcontext){
		String cookiesConfigFilePath = itestcontext.getCurrentXmlTest().getParameter("cookiesConfigFilePath");
		try {
			Properties properties = new Properties();
			// 读取cookies.properties文件
			FileReader filereader = new FileReader(cookiesConfigFilePath);
			properties.load(filereader);
			// 获取key和value并添加到cookies中
			Set<Entry<Object, Object>> keyAndValues = properties.entrySet();
			for(Entry<Object, Object> keyAndValue : keyAndValues){
				Cookie ck = new Cookie(String.valueOf(keyAndValue.getKey()),String.valueOf(keyAndValue.getValue()));
                driver.manage().addCookie(ck);
			}
			logger.info("成功给浏览器添加cookies");
		} catch (Exception e) {
        	logger.error("给浏览器添加cookies发生异常",e);
		}
	}

	/**
	 * 定位查找元素的方法 element
	 */
	public WebElement findElementBy(By byElement) {
		WebElement element = null;
		try {
			element = driver.findElement(byElement);
			logger.info("成功定位元素");
		} catch (Exception e) {
			logger.error("定位元素异常", e);
		}
		return element;
	}

	/**
	 * 定位查找元素的方法 elements
	 */
	public List<WebElement> findElementsBy(By byElement) {
		List<WebElement> element = null;
		try {
			element = driver.findElements(byElement);
			logger.info("成功定位元素");
		} catch (Exception e) {
			logger.error("定位元素异常", e);
		}
		return element;
	}

	/**
	 * 清除操作
	 */
	public void clear(By byElement) {
		try {
			findElementBy(byElement).clear();
			logger.info("成功清除元素 [" + byElement + "]上的内容");
		} catch (Exception e) {
			logger.error("清除元素 [" + byElement + "] 上的内容异常", e);
		}
	}

	/**
	 * 单击操作
	 */
	public void click(By byElement) {
		try {
			clickByRetry(byElement, System.currentTimeMillis(), 2500);
			logger.info("成功点击元素 [" + byElement + "]");
		} catch (StaleElementReferenceException e) {
			logger.error("你点击的元素:[" + byElement + "]不再存在!", e);
		} catch (Exception e) {
			logger.error("点击元素 [" + byElement + "]失败", e);
		}
	}

	/** 不能单击时候重试单击操作 */
	public void clickByRetry(By byElement, long startTime, int timeOut) throws Exception {
		try {
			findElementBy(byElement).click();
			logger.info("成功点击元素");
		} catch (Exception e) {
			if (System.currentTimeMillis() - startTime > timeOut) {
				logger.error(byElement + "元素不可点击", e);
			} else {
				Thread.sleep(500);
				logger.warn(byElement + "元素不可点击, 重试中");
				clickByRetry(byElement, startTime, timeOut);
			}
		}
	}

	/**
	 * 获得页面的标题
	 */
	public String getTitle() {
		String title = null;
		try {
			title = driver.getTitle();
			logger.info("成功获取页面标题");
		} catch (Exception e) {
			logger.error("获取页面标题异常", e);
		}
		return title;
	}

	/**
	 * 获得元素的文本
	 */
	public String getText(By byElement) {
		String text = null;
		try {
			text = driver.findElement(byElement).getText().trim();
			logger.info("成功元素的文本");
		} catch (Exception e) {
			logger.error("获取元素的文本异常", e);
		}
		return text;
	}

	/**
	 * 获得元素的属性值
	 */
	public String getAttributeText(By byElement, String attribute) {
		String attributeText = "";
		try {
			attributeText = driver.findElement(byElement).getAttribute(attribute).trim();
			logger.info("成功获取元素的属性值");
		} catch (Exception e) {
			logger.error("获取元素的属性值异常", e);
		}
		return attributeText;
	}

	/**
	 * 向输入框输入内容
	 */
	public void type(By byElement, CharSequence sendkeys) {
		try {
			findElementBy(byElement).sendKeys(sendkeys);
			logger.info("成功输入[" + sendkeys + "] 到 [" + byElement + "]");
		} catch (Exception e) {
			logger.error("输入 [" + sendkeys + "] 到 元素[" + byElement + "]异常", e);
		}
	}

	/**
	 * 模拟键盘操作的,比如Ctrl+A,Ctrl+C等 参数详解： 1、WebElement element - 要被操作的元素 2、Keys key-
	 * 键盘上的功能键 比如Keys.CONTROL是ctrl键 3、String keyword - 键盘上的字母
	 */
	public void pressKeysOnKeyboard(WebElement element, Keys key, String keyword) {
		try {
			element.sendKeys(Keys.chord(key, keyword));
			logger.info("成功键盘操作" + key.name() + "+" + keyword);
		} catch (Exception e) {
			logger.error("键盘操作" + key.name() + "+" + keyword + "异常", e);
		}
	}

	/**
	 * 等待alert出现后切换到alert窗口
	 */
	public Alert switchToPromptedAlertAfterWait(long waitMillisecondsForAlert)  {
		Alert alert = null;
		long endTime = System.currentTimeMillis() + waitMillisecondsForAlert;
		//每隔200毫秒执行切换alert窗口直到超时
		final int ONE_ROUND_WAIT = 200;
		for (long i = 0; i < waitMillisecondsForAlert; i += ONE_ROUND_WAIT) {
			try {
				alert = driver.switchTo().alert();
				return alert;
			} catch (NoAlertPresentException e) {
				logger.error("提示框不存在",e);
			} catch (Exception e) {
				logger.error("页面切换到提示框窗口异常",e);
			}
			//暂停等待200毫秒后继续循环执行
			logger.info("稍后，重试中...");
			pause(ONE_ROUND_WAIT);

			if (System.currentTimeMillis() > endTime) {
				logger.warn("已超时，页面仍未能切换到提示框窗口");
				break;
			}
		}
		return alert;
	}

	/**
	 * sleep，强制等待。暂停当前用例的执行，暂停的时间为：millisSleepTime，随后恢复执行用例
	 */
	public void pause(int millisSleepTime) {
		if (millisSleepTime <= 0) {
			logger.info("时间值小于0，当前用例不执行暂停");
			return;
		}
		try {
			logger.info("开始暂停当前用例的执行，" + millisSleepTime +"毫秒后恢复执行");
			Thread.sleep(millisSleepTime);
			logger.info("开始恢复当前用例的执行");
		} catch (Exception e) {
			logger.error("暂停执行当前用例无效，发生异常",e);
		}
	}

	/**
	 * WebDriverWait，显示等待。在给定的时间内去定位查找元素，如果没找到则超时，抛出异常
	 */
	public WebElement waitForElementToLoad(long timeOut, final By byElement) {
		WebElement element = null;
		try {
			//元素最多等待timeOut秒
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			//等待元素可见且可被单击
			wait.until(ExpectedConditions.elementToBeClickable(byElement));
			//获取元素
			element = wait.until(new ExpectedCondition<WebElement>() {
				public WebElement apply(WebDriver driver) {
					return driver.findElement(byElement);
				}
			});
			logger.info("成功找到了元素");
		} catch (TimeoutException e) {
			logger.error("超时!! " + timeOut + " 秒之后还没找到元素 [" + byElement + "]", e);
		} catch (Exception e) {
			logger.error("给定的时间内定位查找元素异常", e);
		}
		return element;
	}

	/** implicitlyWait，隐式等待。识别对象时的超时时间。过了这个时间如果对象还没找到的话就会抛出NoSuchElement异常 */
	public void implicitlyWait(long timeOut) {
		try {
			driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
			logger.info("成功等待元素定位出现");
		} catch (NoSuchElementException e) {
			logger.error("超时未找到元素", e);
		} catch (Exception e) {
			logger.error("未能等待元素定位出现，发生异常", e);
		}
	}

	/**
	 * pageLoadTimeout。页面加载时的超时时间。因为webdriver会等页面加载完毕在进行后面的操作，
	 * 所以如果页面在这个超时时间内没有加载完成，那么webdriver就会抛出异常
	 */

	public void waitForPageLoading(long pageLoadTime) {
		try {
			driver.manage().timeouts().pageLoadTimeout(pageLoadTime, TimeUnit.SECONDS);
			logger.info("成功加载页面");
		} catch (Exception e) {
			logger.error("超时未能完全加载页面", e);
		}
	}

	/** setScriptTimeout。异步脚本的超时时间。webdriver可以异步执行脚本，这个是设置异步执行脚本脚本返回结果的超时时间 */
	public void setScriptTimeout(long timeOut) {
		try {
			driver.manage().timeouts().setScriptTimeout(timeOut, TimeUnit.SECONDS);
			logger.info("成功异步执行脚本");
		} catch (Exception e) {
			logger.error("超时未能异步执行脚本", e);
		}
	}

	/**
	 * 切换frame - 根据frame id或name
	 */
	public void inFrame(String nameOrId) {
		try{
			driver.switchTo().frame(nameOrId);
			logger.info("成功切换frame");
		}catch(Exception e){
			logger.error("切换frame发生异常",e);
		}
	}

	/**
	 * 切换frame - 根据frame在当前页面中的DOM顺序来定位
	 */
	public void inFrame(int index) {
		try{
			driver.switchTo().frame(index);
			logger.info("成功切换frame");
		}catch(Exception e){
			logger.error("切换frame发生异常",e);
		}
	}

	/**
	 * 切换frame - 根据页面frame元素定位
	 */
	public void inFrame(WebElement frameElement) {
		try {
			logger.info("正在切换frame:[" + getLocatorByElement(frameElement, ">") + "]");
			driver.switchTo().frame(frameElement);
			logger.info("成功切换frame: [" + getLocatorByElement(frameElement, ">") + "] ");
		} catch (Exception e) {
			logger.error("切换frame: [" + getLocatorByElement(frameElement, ">") + "] 发生异常",e);
		}
	}

	/** 跳出frame */
	public void outFrame() {
		try{
			driver.switchTo().defaultContent();
			logger.info("成功跳出frame");
		}catch(Exception e){
			logger.error("跳出frame发生异常",e);
		}
	}

	/**
	 * selenium模拟鼠标操作 - 鼠标左击
	 */
	public void mouseLeftClick(By byElement) {
		try{
			Actions builder = new Actions(driver);
			builder.click(findElementBy(byElement)).perform();
			logger.info("成功鼠标左击");
		}catch(Exception e){
			logger.error("鼠标左击发生异常",e);
		}
	}

	/**
	 * selenium模拟鼠标操作 - 鼠标右击
	 */
	public void mouseRightClick(By byElement) {
		try{
			Actions builder = new Actions(driver);
			builder.contextClick(findElementBy(byElement)).perform();
			logger.info("成功鼠标右击");
		}catch(Exception e){
			logger.error("鼠标右击发生异常",e);
		}
	}

	/**
	 * selenium模拟鼠标操作 - 鼠标双击
	 */
	public void mouseDoubleClick(By byElement) {
		try{
			Actions builder = new Actions(driver);
			builder.doubleClick(findElementBy(byElement)).perform();
			logger.info("成功鼠标双击");
		}catch(Exception e){
			logger.error("鼠标双击发生异常",e);
		}
	}

	/**
	 * selenium模拟鼠标操作 - 鼠标移动到指定元素
	 */
	public void mouseMoveToElement(WebElement element) {
		try{
			Actions builder = new Actions(driver);
			builder.moveToElement(element).perform();
			logger.info("成功移动鼠标到指定元素");
		}catch(Exception e){
			logger.error("移动鼠标到指定元素发生异常",e);
		}
	}

	/**
	 * selenium模拟鼠标操作 - 鼠标移动到指定元素,这里的 (xOffset, yOffset) 是以元素element的左上角为 (0,0) 开始的 (x, y) 坐标轴。
	 */
	public void mouseMoveToElement(WebElement element,int xOffset,int yOffset) {
		try{
			Actions builder = new Actions(driver);
			builder.moveToElement(element, xOffset, yOffset).perform();
			logger.info("成功移动鼠标到指定元素的(x,y)位置");
		}catch(Exception e){
			logger.error("移动鼠标到指定元素的(x,y)位置发生异常",e);
		}
	}

	/**
	 * selenium模拟鼠标操作 - 鼠标拖拽source元素到target元素位置
	 */
	public void mouseDragAndDrop(WebElement source,WebElement target) {
		try{
			Actions builder = new Actions(driver);
			builder.dragAndDrop(source, target).perform();
			logger.info("成功鼠标拖拽source元素到target元素位置");
		}catch(Exception e){
			logger.error("鼠标拖拽source元素到target元素位置发生异常",e);
		}
	}

	/**
	 * selenium模拟鼠标操作 - 鼠标拖拽source元素到(xOffset, yOffset)位置,其中 xOffset 为横坐标，yOffset 为纵坐标。
	 */
	public void mouseDragAndDrop(WebElement source,int xOffset,int yOffset) {
		try{
			Actions builder = new Actions(driver);
			builder.dragAndDropBy(source, xOffset, yOffset).perform();
			logger.info("成功鼠标拖拽source元素到(xOffset, yOffset)位置");
		}catch(Exception e){
			logger.error("鼠标拖拽source元素到(xOffset, yOffset)位置发生异常",e);
		}
	}

	/**
	 * selenium模拟鼠标操作 - 鼠标悬停
	 */
	public void mouseClickAndHold(WebElement element) {
		try{
			Actions builder = new Actions(driver);
			builder.clickAndHold(element).perform();
			logger.info("成功鼠标悬停");
		}catch(Exception e){
			logger.error("鼠标悬停发生异常",e);
		}
	}

	/**
	 * selenium模拟鼠标操作 - 鼠标释放（一般发生在鼠标悬停clickAndHold之后）
	 */
	public void mouseRelease(WebElement element) {
		try{
			Actions builder = new Actions(driver);
			builder.release().perform();
			logger.info("成功鼠标悬停");
		}catch(Exception e){
			logger.error("鼠标悬停发生异常",e);
		}
	}

	/** 获得CSS value */
	public String getCSSValue(WebElement e, String key) {

		return e.getCssValue(key);
	}

	/** 使用testng的assetTrue方法 */
	public void assertTrue(WebElement e, String content) {
		String str = e.getText();
		Assert.assertTrue(str.contains(content), "字符串数组中不含有：" + content);

	}

	/** 根据元素来获取此元素的定位值 */
	public String getLocatorByElement(WebElement element, String expectText) {
		String text = element.toString();
		String expect = null;
		try {
			expect = text.substring(text.indexOf(expectText) + 1, text.length() - 1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("failed to find the string [" + expectText + "]");
		}
		return expect;

	}

	/**
	 * 这是一堆相同的elements中 选择 其中方的 一个 然后在这个选定的中 继续定位
	 */
	public WebElement getOneElement(By bys, By by, int index) {
		return findElementsBy(bys).get(index).findElement(by);
	}

	/**
	 * 上传文件，需要点击弹出上传照片的窗口才行
	 * 
	 * @param brower
	 *            使用的浏览器名称
	 * @param file
	 *            需要上传的文件及文件名
	 */
	public void handleUpload(String browser, File file) {
		String filePath = file.getAbsolutePath();
		String executeFile = "res/script/autoit/Upload.exe";
		String cmd = "\"" + executeFile + "\"" + " " + "\"" + browser + "\"" + " " + "\"" + filePath + "\"";
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description 对于windows GUI弹出框，要求输入用户名和密码时，
	 *              seleniumm不能直接操作，需要借助http://modifyusername:modifypassword@yoururl
	 *              这种方法
	 * 
	 */
	public void loginOnWinGUI(String username, String password, String url) {
		driver.get(username + ":" + password + "@" + url);
	}

	/**
	 * 检查元素是否可编辑
	 */
	public void isInputEdit(WebElement element) {
		Boolean ie = false;
		try {
			ie = element.isEnabled();
		} catch (Exception e) {
			logger.error("判断元素是否可编辑发生异常", e);
		}
		if (ie){
			logger.info("成功判断元素可编辑");
		}else{
			logger.info("成功判断元素不可编辑");
		}
	}

	/** 检查元素是否显示 */
	public boolean isDisplayed(WebElement element) {
		boolean isDisplay = false;
		if (element.isDisplayed()) {
			logger.info("The element: [" + getLocatorByElement(element, ">") + "] is displayed");
			isDisplay = true;
		} else if (element.isDisplayed() == false) {
			logger.warn("The element: [" + getLocatorByElement(element, ">") + "] is not displayed");

			isDisplay = false;
		}
		return isDisplay;
	}

	/** 检查元素是不是存在 */
	public boolean doesElementsExist(By byElement) {
		try {
			findElementBy(byElement);
			return true;
		} catch (NoSuchElementException nee) {

			return false;
		}

	}

	/** 检查元素是不是勾选，常用于checkbox */
	public boolean isSelected(WebElement element) {
		boolean flag = false;
		try{
			if (element.isSelected() == true) {
				logger.info("成功检查CheckBox被勾选");
				flag = true;
			} else
				logger.info("成功检查CheckBox没有被勾选");
			flag = false;
		}catch(Exception e){
			logger.error("检查CheckBox是否勾选发生异常",e);
		}
		return flag;
	}

	/**
	 * 选择下拉选项 -根据value
	 */
	public void selectByValue(By by, String value) {
		try{
			Select s = new Select(driver.findElement(by));
			s.selectByValue(value);
			logger.info("成功根据value选择下拉选项");
		}catch(Exception e){
			logger.error("根据value选择下拉选项发生异常",e);
		}
	}

	/**
	 * 选择下拉选项 -根据index角标
	 */
	public void selectByIndex(By by, int index) {
		try{
			Select s = new Select(driver.findElement(by));
			s.selectByIndex(index);
			logger.info("成功根据index角标选择下拉选项");
		}catch(Exception e){
			logger.error("根据index角标选择下拉选项发生异常",e);
		}
		
	}

	/**
	 * 选择下拉选项 -根据文本内容
	 */
	public void selectByText(By by, String text) {
		try{
			Select s = new Select(driver.findElement(by));
			s.selectByVisibleText(text);
			logger.info("成功根据文本内容选择下拉选项");
		}catch(Exception e){
			logger.error("根据文本内容选择下拉选项发生异常",e);
		}
	}

	/**
	 * 获得当前select可供选择的那些值
	 */
	public List<WebElement> getCurrentSelectValue(By by) {
		List<WebElement> options = null;
		try{
			Select s = new Select(driver.findElement(by));
			options = s.getAllSelectedOptions();
			logger.info("成功获得当前下拉的所有选项的元素定位");
		}catch(Exception e){
			logger.error("获得当前下拉的所有选项的元素定位发生异常",e);
		}
		return options;
	}

	/**
	 * 获得输入框的值 这个方法是针对某些input输入框没有value属性，但是又想取得input的值的方法
	 */
	public String getInputValue(String chose, String choseValue) {
		String value = null;
		try{
			switch (chose.toLowerCase()) {
			case "name":
				// 把JS执行的值返回出去
				String jsByName = "return document.getElementsByName('" + choseValue + "')[0].value;"; 
				value = (String) ((JavascriptExecutor) driver).executeScript(jsByName);
				break;
			case "id":
				// 把JS执行的值返回出去
				String jsById = "return document.getElementById('" + choseValue + "').value;"; 
				value = (String)executeJS(jsById);
				break;
			default:
				logger.error("未定义的chose:" + chose);
			}
			logger.info("成功获得输入框的值");
		}catch(Exception e){
			logger.error("获得输入框的值发生异常",e);
		}
		return value;
	}

	/**
	 * 执行JavaScript 方法
	 */
	public Object executeJS(String js) {
		Object obj = null;
		try{
			obj = ((JavascriptExecutor) driver).executeScript(js);
			logger.info("成功执行JavaScript语句：[" + js + "]");
		}catch(Exception e){
			logger.error("执行JavaScript语句：[" + js + "]发生异常",e);
		}
		return obj;
	}

	/**
	 * 执行JavaScript 方法和对象 
	 * 用法：seleniumUtil.executeJS("arguments[0].click();",seleniumUtil.findElementBy(By));
	 */
	public Object executeJS(String js, Object... args) {
		Object obj = null;
		try{
			((JavascriptExecutor) driver).executeScript(js, args);
			logger.info("成功执行JavaScript语句[" + js + "]");
		}catch(Exception e){
			logger.error("执行JavaScript语句[" + js + "]异常",e);
		}
		return obj;
	}

	/**
	 * 判断实际文本时候包含期望文本
	 * 
	 * @param actual
	 *            实际文本
	 * @param expect
	 *            期望文本
	 */
	public void isContains(String actual, String expect) {
		try {
			Assert.assertTrue(actual.contains(expect));
		} catch (AssertionError e) {
			logger.error("The [" + actual + "] is not contains [" + expect + "]");
			Assert.fail("The [" + actual + "] is not contains [" + expect + "]");
		}
		logger.info("The [" + actual + "] is contains [" + expect + "]");
	}

	/** 获得屏幕的分辨率 - 宽 */
	public static double getScreenWidth() {
		return java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	}

	/**
	 * 文本断言，判断文本是不是和需求要求的文本一致
	 **/
	public void textAssertEquals(String actual, String expected) {
		try {
			Assert.assertEquals(actual, expected);
			logger.info("成功找到了期望的文字: [" + expected + "]");
		} catch (AssertionError e) {
			Assert.fail("期望的文字是 [" + expected + "] 但是找到了 [" + actual + "]", e);
		}
	}
}
