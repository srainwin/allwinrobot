package com.demo.utils;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
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
 * @Description 包装所有selenium的操作以及通用方法，简化用例中代码量(在pageoperation包的元素页对象操作类中使用)
 */
public class SeleniumUtil {
	public static Logger logger = Logger.getLogger(SeleniumUtil.class.getName());
	public ITestResult it = null;
	public static WebDriver driver = null;//使用static是用于监听类的用例失败截图功能调用到driver

	/***
	 * 启动浏览器
	 */
	public void launchBrowser(String browserName, ITestContext itestcontext, int timeOut) {
		SelectBrowser selectbrowser = new SelectBrowser();
		// 可选择不同的浏览器来启动，并使得类中成员变量driver获得浏览器驱动值，以便于其他成员方法共用同一个driver有值
		driver = selectbrowser.selectByName(browserName, itestcontext);
		try {
			maxWindow();
			pageLoadTimeout(timeOut);
			logger.info("成功启动" + browserName + "浏览器");
		} catch (Exception e) {
			logger.error("启动" + browserName + "浏览器发生异常", e);
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

	/** 获得屏幕的分辨率 - 宽和高 */
	public static double[] getScreenWidth() {
		double[] wh = new double[2];
		try{
			wh[0] = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
			wh[1] = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
			logger.info("成功获得屏幕的分辨率-宽和高");
		}catch(Exception e){
			logger.error("获得屏幕的分辨率-宽和高 发生异常",e);
		}
		return wh;
	}

	/**
	 * 地址栏输入网址
	 */
	public void get(String testurl) {
		try {
			driver.get(testurl);
			logger.info("成功打开测试页面:[" + testurl + "]");
		} catch (TimeoutException e) {
			logger.error("注意：页面没有完全加载出来，正在刷新重试！！", e);
			refresh();
			String status = (String) (executeJS("return document.readyState"));
			logger.info("打印状态：" + status);
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
		pause(5000);
		Set<Cookie> cookies = driver.manage().getCookies();
		String cookiesConfigFilePath = itestcontext.getCurrentXmlTest().getParameter("cookiesConfigFilePath");
		File file = new File(cookiesConfigFilePath);
		if(file.exists()) {
            try {
                file.delete();
                logger.info("成功删除旧的cookies文件");
                file.createNewFile();
                logger.info("成功重建新的cookies文件");
            } catch (Exception e) {
            	logger.error("重建新的cookies文件发生异常",e);
            }
        }
		try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for(Cookie c:cookies) {
            	// expiry特殊处理，先getTime()转long类型，再转字符串。为空时转成null字样的字符串
            	String expiry =null;
            	if(c.getExpiry()==null){
            		expiry = "null";
            	}else{
            		expiry = String.valueOf(c.getExpiry().getTime());
            	}
            	bw.write(c.getName()+";"+c.getValue()+";"+c.getDomain()+";"+c.getPath()+";"+expiry+";"+c.isSecure());
                bw.newLine();
            }
            bw.flush();
            bw.close();
            logger.info("成功获取网站cookies并写入到cookies文件中");
        } catch (Exception e) {
        	logger.error("获取网站cookies并写入到cookies文件中发生异常",e);
        }
		
	}
	
	/**
	 * 添加cookies,从文件读取cookies后添加，做自动登陆的必要方法
	 */
	public void addcookies(ITestContext itestcontext){
		String cookiesConfigFilePath = itestcontext.getCurrentXmlTest().getParameter("cookiesConfigFilePath");
		try {
            File cookieFile = new File(cookiesConfigFilePath);
            FileReader fr = new FileReader(cookieFile);
            BufferedReader br = new BufferedReader(fr);
            String line = null;
            while ((line = br.readLine()) != null){
                String [] strArray = line.split(";");
                String name = strArray[0].trim();
                String value = strArray[1].trim();
                String domain = strArray[2].trim();
                String path = strArray[3].trim();
                // expiry特殊处理，为null字样的字符串则保留date类型为空，非null字样（long字样）的则转long类型为new date的参数
                Date expiry = null;
                if(!(strArray[4].equals("null"))){
                	expiry = new Date(Long.parseLong(strArray[4]));
                }
                Boolean isSecure = Boolean.valueOf(strArray[5]);
                Cookie ck = new Cookie(name,value,domain,path,expiry,isSecure);
                driver.manage().addCookie(ck);
            }
            br.close();
			logger.info("成功给浏览器添加cookies");
		} catch (Exception e) {
        	logger.error("给浏览器添加cookies发生异常",e);
		}
	}
	
	/**
	 * 清除cookies
	 */
	public void delAllcookies(){
		try {
			driver.manage().deleteAllCookies();
			logger.info("成功给浏览器清除cookies");
		} catch (Exception e) {
        	logger.error("给浏览器清除cookies发生异常",e);
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
	 * WebDriverWait，显示等待。在给定的时间内去定位查找元素，如果没找到则超时，抛出异常
	 */
	public WebElement findElementByWait(int timeOutInSeconds, final By byElement) {
		WebElement element = null;
		try {
			//元素最多等待timeOut秒
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			//等待元素可见且可被单击
			//WebDriverWait的until()功能是在限定时间内一直等待元素某个条件为真，并返回元素本身
			//ExpectedConditions是selenium的工具类，可对元素做各种期望判断，如是否可点击、是否包含某文本等等)
			wait.until(ExpectedConditions.elementToBeClickable(byElement));
			//获取元素
			element = wait.until(new ExpectedCondition<WebElement>() {
				@Override
				public WebElement apply(WebDriver driver) {
					return driver.findElement(byElement);
				}
			});
			logger.info("成功找到了元素");
		} catch (TimeoutException e) {
			logger.error("超时!! " + timeOutInSeconds + " 秒之后还没找到元素 [" + byElement + "]", e);
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

	public void pageLoadTimeout(long pageLoadTime) {
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
	 * sleep，强制等待。暂停当前用例的执行，暂停的时间为：millisSleepTime，随后恢复执行用例
	 */
	public void pause(long millisSleepTime) {
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
			text = findElementByWait(5, byElement).getText().trim();
			logger.info("成功获取元素的文本");
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
			attributeText = findElementByWait(5, byElement).getAttribute(attribute).trim();
			logger.info("成功获取元素的属性值");
		} catch (Exception e) {
			logger.error("获取元素的属性值发生异常", e);
		}
		return attributeText;
	}

	/**
	 * 清除操作
	 */
	public void clear(By byElement) {
		try {
			findElementByWait(5,byElement).clear();
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
			findElementByWait(5,byElement).click();
			logger.info("成功点击元素 [" + byElement + "]");
		} catch (StaleElementReferenceException e) {
			logger.error("你点击的元素:[" + byElement + "]不再存在!", e);
		} catch (Exception e) {
			logger.error("点击元素 [" + byElement + "]发生异常", e);
		}
	}

	/** 不能单击时候重试单击操作，starttime可以是当前系统时间System.currentTimeMillis()或new Date().getTime() */
	public void clickByRetry(By byElement, long startTime, int timeOut) throws Exception {
		try {
			findElementBy(byElement).click();
			logger.info("成功点击元素");
		} catch (Exception e) {
			new Date().getTime();
			if (System.currentTimeMillis() - startTime > timeOut) {
				logger.error(byElement + "元素不可点击", e);
			} else {
				logger.warn(byElement + "元素不可点击, 重试中");
				Thread.sleep(500);
				clickByRetry(byElement, startTime, timeOut);
			}
		}
	}

	/**
	 * 向输入框输入内容
	 */
	public void type(By byElement, CharSequence sendkeys) {
		try {
			findElementByWait(5,byElement).sendKeys(sendkeys);
			logger.info("成功输入[" + sendkeys + "] 到 [" + byElement + "]");
		} catch (Exception e) {
			logger.error("输入 [" + sendkeys + "] 到 元素[" + byElement + "]异常", e);
		}
	}

	/**
	 * 表单提交（回车）
	 */
	public void submit(By byElement) {
		try {
			findElementByWait(5,byElement).submit();
			logger.info("成功表单提交");
		} catch (Exception e) {
			logger.error("表单提交发生异常", e);
		}
	}

	/**
	 * 模拟键盘操作的,比如Ctrl+A,Ctrl+C等 参数详解： 1、WebElement element - 要被操作的元素 2、Keys key-
	 * 键盘上的功能键 比如Keys.CONTROL是ctrl键 3、String keyword - 键盘上的字母
	 */
	public void pressKeys(By byElement, Keys key, String keyword) {
		try {
			findElementByWait(5,byElement).sendKeys(Keys.chord(key, keyword));
			logger.info("成功键盘操作" + key.name() + "+" + keyword);
		} catch (Exception e) {
			logger.error("键盘操作" + key.name() + "+" + keyword + "异常", e);
		}
	}

	/**
	 * 等待alert出现后切换到alert窗口
	 */
	public Alert switchToAlert(long waitMillisecondsForAlert)  {
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
	 * 多表单切换frame - 根据frame id或name
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
	 * 多表单切换frame - 根据frame在当前页面中的DOM顺序来定位
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
	 * 多表单切换frame - 根据页面frame元素定位
	 */
	public void inFrame(By ByframeElement) {
		try {
			logger.info("正在切换frame");
			driver.switchTo().frame(findElementByWait(5,ByframeElement));
			logger.info("成功切换frame");
		} catch (Exception e) {
			logger.error("切换frame发生异常",e);
		}
	}

	/** 
	 * 多表单切换frame - 跳出frame 
	 */
	public void outFrame() {
		try{
			driver.switchTo().defaultContent();
			logger.info("成功跳出frame");
		}catch(Exception e){
			logger.error("跳出frame发生异常",e);
		}
	}

	/**
	 * 多窗口切换handle - 获取窗口标题
	 */
	public String getHandleTitle (){
		String title = null;
		try{
			driver.getTitle();
			logger.info("成功获取窗口标题");
		}catch(Exception e){
			logger.error("获取窗口标题发生异常",e);
		}
		return title;
	}

	/**
	 * 多窗口切换handle - 获取当前窗口句柄
	 */
	public String getCurrentHandle() {
		String handle = null;
		try {
			handle = driver.getWindowHandle();
			logger.info("成功获取所有窗口句柄");
		} catch (Exception e) {
			logger.error(" 获取所有窗口句柄发生异常",e);
		}
		return handle;
	}

	/**
	 * 多窗口切换handle - 获取所有窗口句柄
	 */
	public Set<String> getAllHandles() {
		Set<String> handles = null;
		try {
			handles = driver.getWindowHandles();
			logger.info("成功获取所有窗口句柄");
		} catch (Exception e) {
			logger.error(" 获取所有窗口句柄发生异常",e);
		}
		return handles;
	}
	
	/**
	 * 多窗口切换handle - 切换指定窗口句柄
	 */
	public void switchToHandle(String handle) {
		try {
			logger.info("正在切换handle");
			driver.switchTo().window(handle);
			logger.info("成功切换handle");
		} catch (Exception e) {
			logger.error("切换handle发生异常",e);
		}
	}
	
	/**
	 * 多窗口切换handle - 只有两个窗口时切换另一个窗口句柄
	 */
	public void switchToAnotherHandle() {
		String handle = null;
		Set<String> handles = null;
		try {
			handle = getCurrentHandle();
			handles = getAllHandles();
			for( String hd : handles ){
				// 如果不是当前窗口则切换这个句柄
				if( !(hd.equals(handle)) ){
					switchToHandle(hd);
					break;
				}
			}
			logger.info("成功切换另一个handle");
		} catch (Exception e) {
			logger.error("切换另一个handle发生异常",e);
		}
	}

	/**
	 * Actions模拟鼠标操作 - 鼠标左击
	 */
	public void mouseLeftClick(By byElement) {
		try{
			Actions builder = new Actions(driver);
			builder.click(findElementByWait(5,byElement)).perform();
			logger.info("成功鼠标左击");
		}catch(Exception e){
			logger.error("鼠标左击发生异常",e);
		}
	}

	/**
	 * Actions模拟鼠标操作 - 鼠标右击
	 */
	public void mouseRightClick(By byElement) {
		try{
			Actions builder = new Actions(driver);
			builder.contextClick(findElementByWait(5,byElement)).perform();
			logger.info("成功鼠标右击");
		}catch(Exception e){
			logger.error("鼠标右击发生异常",e);
		}
	}

	/**
	 * Actions模拟鼠标操作 - 鼠标双击
	 */
	public void mouseDoubleClick(By byElement) {
		try{
			Actions builder = new Actions(driver);
			builder.doubleClick(findElementByWait(5,byElement)).perform();
			logger.info("成功鼠标双击");
		}catch(Exception e){
			logger.error("鼠标双击发生异常",e);
		}
	}

	/**
	 * Actions模拟鼠标操作 - 鼠标移动到指定元素
	 */
	public void mouseMoveToElement(By byElement) {
		try{
			Actions builder = new Actions(driver);
			builder.moveToElement(findElementByWait(5,byElement)).perform();
			logger.info("成功移动鼠标到指定元素");
		}catch(Exception e){
			logger.error("移动鼠标到指定元素发生异常",e);
		}
	}

	/**
	 * Actions模拟鼠标操作 - 鼠标移动到指定元素,这里的 (xOffset, yOffset) 是以元素element的左上角为 (0,0) 开始的 (x, y) 坐标轴。
	 */
	public void mouseMoveToElement(By byElement,int xOffset,int yOffset) {
		try{
			Actions builder = new Actions(driver);
			builder.moveToElement(findElementByWait(5,byElement), xOffset, yOffset).perform();
			logger.info("成功移动鼠标到指定元素的(x,y)位置");
		}catch(Exception e){
			logger.error("移动鼠标到指定元素的(x,y)位置发生异常",e);
		}
	}

	/**
	 * Actions模拟鼠标操作 - 鼠标拖拽source元素到target元素位置
	 */
	public void mouseDragAndDrop(By BySourceElement,By ByTargetElement) {
		try{
			Actions builder = new Actions(driver);
			builder.dragAndDrop(findElementByWait(5,BySourceElement), findElementByWait(5,ByTargetElement)).perform();
			logger.info("成功鼠标拖拽source元素到target元素位置");
		}catch(Exception e){
			logger.error("鼠标拖拽source元素到target元素位置发生异常",e);
		}
	}

	/**
	 * Actions模拟鼠标操作 - 鼠标拖拽source元素到(xOffset, yOffset)位置,其中 xOffset 为横坐标，yOffset 为纵坐标。
	 */
	public void mouseDragAndDrop(By BySourceElement,int xOffset,int yOffset) {
		try{
			Actions builder = new Actions(driver);
			builder.dragAndDropBy(findElementByWait(5,BySourceElement), xOffset, yOffset).perform();
			logger.info("成功鼠标拖拽source元素到(xOffset, yOffset)位置");
		}catch(Exception e){
			logger.error("鼠标拖拽source元素到(xOffset, yOffset)位置发生异常",e);
		}
	}

	/**
	 * Actions模拟鼠标操作 - 鼠标悬停
	 */
	public void mouseClickAndHold(By byElement) {
		try{
			Actions builder = new Actions(driver);
			builder.clickAndHold(findElementByWait(5,byElement)).perform();
			logger.info("成功鼠标悬停");
		}catch(Exception e){
			logger.error("鼠标悬停发生异常",e);
		}
	}

	/**
	 * Actions模拟鼠标操作 - 鼠标释放（一般发生在鼠标悬停clickAndHold之后）
	 */
	public void mouseRelease() {
		try{
			Actions builder = new Actions(driver);
			builder.release().perform();
			logger.info("成功鼠标悬停");
		}catch(Exception e){
			logger.error("鼠标悬停发生异常",e);
		}
	}

	/** 获得CSS value ,常用于特殊判断，例如必填项输入框会变红色，这时getCssValue("colour")获取颜色断言*/
	public String getCSSValue(By byElement, String key) {
		String css = null;
		try{
			css = findElementByWait(5,byElement).getCssValue(key);
			logger.info("成功获得CSS value");
		}catch(Exception e){
			logger.error("获得CSS value发生异常",e);
		}
		return css;
	}

	/**
	 * 检查元素是否可编辑
	 */
	public boolean isInputEdit(By byElement) {
		boolean isEdit = false;
		try {
			isEdit = findElementByWait(5,byElement).isEnabled();
			if (isEdit){
				logger.info("成功检查元素可编辑");
			}else{
				logger.info("成功检查元素不可编辑");
			}
		} catch (Exception e) {
			logger.error("检查元素是否可编辑发生异常", e);
		}
		return isEdit;
	}

	/** 检查元素是否显示 */
	public boolean isDisplayed(By byElement) {
		boolean isDisplay = false;
		try{
			isDisplay = findElementByWait(5,byElement).isDisplayed();
			if (isDisplay){
				logger.info("成功检查元素可显示");
			}else{
				logger.info("成功检查元素不可显");
			}
		}catch(Exception e){
			logger.error("检查元素是否显示发生异常",e);
		}
		return isDisplay;
	}

	/** 检查元素是不是存在 */
	public boolean doesElementsExist(By byElement) {
		try {
			findElementByWait(5,byElement);
			logger.info("成功检查元素存在");
			return true;
		} catch (NoSuchElementException e) {
			logger.info("成功检查元素不存在");
			return false;
		}catch (Exception e) {
			logger.info("检查元素是否存在时发生异常");
			return false;
		}

	}

	/** 检查元素是不是勾选，常用于checkbox */
	public boolean isSelected(By byElement) {
		boolean flag = false;
		try{
			flag = findElementByWait(5,byElement).isSelected();
			if (flag) {
				logger.info("成功检查CheckBox被勾选");
			} else{
				logger.info("成功检查CheckBox没有被勾选");
			}
		}catch(Exception e){
			logger.error("检查CheckBox是否勾选发生异常",e);
		}
		return flag;
	}

	/**
	 * 选择下拉选项 -根据value
	 */
	public void selectByValue(By byElement, String value) {
		try{
			Select s = new Select(findElementByWait(5,byElement));
			s.selectByValue(value);
			logger.info("成功根据value选择下拉选项");
		}catch(Exception e){
			logger.error("根据value选择下拉选项发生异常",e);
		}
	}

	/**
	 * 选择下拉选项 -根据index角标
	 */
	public void selectByIndex(By byElement, int index) {
		try{
			Select s = new Select(findElementByWait(5,byElement));
			s.selectByIndex(index);
			logger.info("成功根据index角标选择下拉选项");
		}catch(Exception e){
			logger.error("根据index角标选择下拉选项发生异常",e);
		}
		
	}

	/**
	 * 选择下拉选项 -根据文本内容
	 */
	public void selectByText(By byElement, String text) {
		try{
			Select s = new Select(findElementByWait(5,byElement));
			s.selectByVisibleText(text);
			logger.info("成功根据文本内容选择下拉选项");
		}catch(Exception e){
			logger.error("根据文本内容选择下拉选项发生异常",e);
		}
	}

	/**
	 * 获得select有哪些值可供选择
	 */
	public List<WebElement> getSelectOptions(By byElement) {
		List<WebElement> optionsList = null;
		try{
			Select s = new Select(findElementByWait(5,byElement));
			optionsList = s.getAllSelectedOptions();
			logger.info("成功获得当前下拉的所有选项的元素定位");
		}catch(Exception e){
			logger.error("获得当前下拉的所有选项的元素定位发生异常",e);
		}
		return optionsList;
	}

	/**
	 * @Description 获得输入框的值，这个方法是针对某些input输入框没有value属性，但是又想取得input的值的方法
	 * @param nameOrId 选择name或id定位
	 * @param nameOrIdValue name或id的值是什么
	 * @return 定位后获取的value属性值
	 */
	public String getInputValue(String nameOrId, String nameOrIdValue) {
		String value = null;
		try{
			switch (nameOrId.toLowerCase()) {
			case "name":
				// 把JS执行的值返回出去
				String jsByName = "return document.getElementsByName('" + nameOrIdValue + "')[0].value;"; 
				value = (String) ((JavascriptExecutor) driver).executeScript(jsByName);
				break;
			case "id":
				// 把JS执行的值返回出去
				String jsById = "return document.getElementById('" + nameOrIdValue + "').value;"; 
				value = (String)executeJS(jsById);
				break;
			default:
				logger.error("未定义的定位选择:" + nameOrId);
			}
			logger.info("成功获得输入框的值");
		}catch(Exception e){
			logger.error("获得输入框的值发生异常",e);
		}
		return value;
	}

	/**
	 * 执行JavaScript方法
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
	 * 执行JavaScript方法和对象
	 * 用法：seleniumUtil.executeJS("arguments[0].click();",seleniumUtil.findElementBy(By));
	 * 		右边参数结果代入arguments[?]
	 * 常用于html5处理，如:
	 * 		WebElement vedio = driver.findElement(By.id("preview-player_html5_api"));
	 * 		JavascriptExecutor js = (JavascriptExecutor) driver;
	 * 		js.executeScript("arguments[0].play()", vedio)
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
	 * @Description 判断实际文本时候包含期望文本
	 * @param actual 实际文本
	 * @param expect 期望文本
	 */
	public void isActualContainsExpect(String actual, String expect) {
		try {
			Assert.assertTrue(actual.contains(expect));
		} catch (AssertionError e) {
			logger.error("实际文本[" + actual + "]不包含期望文本[" + expect + "]");
			Assert.fail("实际文本[" + actual + "]包含期望文本[" + expect + "]");
		}
		logger.info("The [" + actual + "] is contains [" + expect + "]");
	}

	/**
	 * 文本断言，判断文本是不是和需求要求的文本一致，使用testng的assertEquals方法
	 **/
	public void assertEquals(String actual, String expected) {
		try {
			Assert.assertEquals(actual, expected);
			logger.info("成功找到了期望的文字: [" + expected + "]");
		} catch (AssertionError e) {
			Assert.fail("期望的文字是 [" + expected + "] 但是找到了 [" + actual + "]", e);
		}
	}

	/** 文本断言，判断元素getText()获取的文本是否包含指定内容，使用testng的assetTrue方法 */
	public void assertTrue(By byElement, String textcontent) {
		try{
			String str = findElementByWait(5,byElement).getText();
			Assert.assertTrue(str.contains(textcontent), "assert为flase，获取元素的text中应含有：" + textcontent+"才对，发现不含有。当assert为ture时则看不到这句话。");
			logger.info("成功断言获取元素的text是否含有指定内容");
		}catch(Exception e){
			logger.error("断言获取元素的text是否含有指定内容发生异常",e);
		}
		
	}

	/**
	 * 对于windows GUI弹出框，要求输入用户名和密码时，
	 * seleniumm不能直接操作，需要借助http://modifyusername:modifypassword@yoururl这种方法
	 */
	public void loginOnWinGUI(String username, String password, String url) {
		try{
			driver.get(username + ":" + password + "@" + url);
			logger.info("成功处理windows GUI的登陆弹出框");
		}catch(Exception e){
			logger.error("处理windows GUI的登陆弹出框发生异常",e);
		}
	}

	/**
	 * @Description 上传文件,通过定位上传按钮后sendKeys()上传文件
	 * @param byElement 定位上传按钮
	 * @param file 上传的文件
	 */
	public void uploadFile(By byElement, File file) {
		String filePath = file.getAbsolutePath();
		try {
			//定位上传按钮，添加本地文件
			findElementByWait(5,byElement).sendKeys(filePath);
			logger.info("成功通过定位上传按钮后sendKeys()上传文件");
		} catch (Exception e) {
			logger.error("通过定位上传按钮后sendKeys()上传文件发生异常",e);
		}
	}
	
	/**
	 * @Description 上传文件，通过执行autoit的exe脚本上传文件，预先利用autoit编写上传脚本并生成exe可执行文件存放到项目
	 * @param byElement 定位上传按钮
	 * @param exeName autoit的exe文件名
	 * @param itestcontext 通过获取testng.xml文件参数来获得autoit文件存放路径
	 */
	public void uploadFile(By byElement, String exeName, ITestContext itestcontext) {
		String uploadExe = itestcontext.getCurrentXmlTest().getParameter("autoitFolderPath") + exeName;
		try {
			// 点击上传按钮弹出windows选择文件窗口
			click(byElement);
			pause(2000);
			Runtime rn = Runtime.getRuntime();
			Process p = rn.exec(uploadExe);
			//等待上传进程完成
			int staus = p.waitFor();
			if( staus == 0){
				logger.info("已上传完毕并终止exe脚本进程");
			}else{
				logger.warn("上传失败，异常终止exe脚本进程");
			}
		} catch (Exception e) {
			logger.error("通过执行autoit的exe脚本上传文件发生异常",e);
		}
	}
	
	/**
	 * @Description 截图保存功能
	 */
	public void takesScreenshot() {
		String screenName = String.valueOf(new Date().getTime()) + ".png";
		File dir = new File("./result/screenshot");
		if(!dir.exists()){
			dir.mkdirs();
		}
        try {
			String screenPath = dir.getAbsolutePath() + "/"+screenName;
			File srcFile = ((TakesScreenshot) SeleniumUtil.driver).getScreenshotAs(OutputType.FILE);
			File destFile = new File(screenPath);
			FileUtils.copyFile(srcFile, destFile);
			logger.info("成功截图保存");
        } catch (IOException e) {
        	logger.error("截图保存发生异常",e);
        }
	}
}
