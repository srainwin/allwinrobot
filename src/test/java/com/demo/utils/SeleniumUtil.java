package com.demo.utils;

import java.io.File;
import java.util.List;
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
		SelectBrowser select = new SelectBrowser();
		// 可选择不同的浏览器来启动
		driver = select.selectByName(browserName, itestcontext);
		try {
			maxWindow();
			waitForPageLoading(timeOut);
			get(testurl);
		} catch (TimeoutException e) {
			logger.error("注意：页面没有完全加载出来，刷新重试！！", e);
			refresh();
			String status = (String) (executeJS("return document.readyState"));
			logger.info("打印状态：" + status);
		} catch (Exception e) {
			logger.error("启动" + browserName + "浏览器并打开页面异常", e);
		}
		logger.info("成功启动" + browserName + "浏览器，并成功打开测试网址");
	}

	/**
	 * 最大化浏览器操作
	 */
	public void maxWindow() {
		try {
			driver.manage().window().maximize();
		} catch (Exception e) {
			logger.error("最大化浏览器失败", e);
		}
		logger.info("成功最大化浏览器");
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
		} catch (Exception e) {
			logger.error("设置浏览器窗口大小失败", e);
		}
		logger.info("成功设置浏览器窗口大小");

	}

	// webdriver中可以设置很多的超时时间
	/** implicitlyWait。识别对象时的超时时间。过了这个时间如果对象还没找到的话就会抛出NoSuchElement异常 */
	public void implicitlyWait(long timeOut) {
		try {
			driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.error("未能等待元素定位出现，发生异常", e);
		}
		logger.info("成功等待元素定位出现");
	}

	/** setScriptTimeout。异步脚本的超时时间。webdriver可以异步执行脚本，这个是设置异步执行脚本脚本返回结果的超时时间 */
	public void setScriptTimeout(long timeOut) {
		try {
			driver.manage().timeouts().setScriptTimeout(timeOut, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.error("超时未能异步执行脚本", e);
		}
		logger.info("成功异步执行脚本");
	}

	/**
	 * pageLoadTimeout。页面加载时的超时时间。因为webdriver会等页面加载完毕在进行后面的操作，
	 * 所以如果页面在这个超时时间内没有加载完成，那么webdriver就会抛出异常
	 */

	public void waitForPageLoading(long pageLoadTime) {
		try {
			driver.manage().timeouts().pageLoadTimeout(pageLoadTime, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.error("超时未能完全加载页面", e);
		}
		logger.info("成功加载页面");
	}

	/**
	 * get方法包装
	 */
	public void get(String testurl) {
		try {
			driver.get(testurl);
		} catch (Exception e) {
			logger.error("打开测试页面异常", e);
		}
		logger.info("成功打开测试页面:[" + testurl + "]");
	}

	/**
	 * 退出
	 */
	public void quit() {
		try {
			driver.quit();
		} catch (Exception e) {
			logger.error("退出浏览器异常", e);
		}
		logger.info("成功退出浏览器");
	}

	/**
	 * close方法包装
	 */
	public void close() {
		try {
			driver.close();
		} catch (Exception e) {
			logger.error("关闭网页异常", e);
		}
		logger.info("成功关闭网页");
	}

	/**
	 * 刷新方法包装
	 */
	public void refresh() {
		try {
			driver.navigate().refresh();
		} catch (Exception e) {
			logger.error("刷新网页异常", e);
		}
		logger.info("成功刷新页面");
	}

	/**
	 * 后退方法包装
	 */
	public void back() {
		try {
			driver.navigate().back();
		} catch (Exception e) {
			logger.error("回退上一个网页异常", e);
		}
		logger.info("成功回退上一个网页");
	}

	/**
	 * 前进方法包装
	 */
	public void forward() {
		try {
			driver.navigate().forward();
		} catch (Exception e) {
			logger.error("重返网页异常", e);
		}
		logger.info("成功重返网页");
	}

	/**
	 * 包装定位查找元素的方法 element
	 */
	public WebElement findElementBy(By byElement) {
		WebElement element = null;
		try {
			element = driver.findElement(byElement);
		} catch (Exception e) {
			logger.error("定位元素异常", e);
		}
		logger.info("成功定位元素");
		return element;
	}

	/**
	 * 包装定位查找元素的方法 elements
	 */
	public List<WebElement> findElementsBy(By byElement) {
		List<WebElement> element = null;
		try {
			element = driver.findElements(byElement);
		} catch (Exception e) {
			logger.error("定位元素异常", e);
		}
		logger.info("成功定位元素");
		return element;
	}

	/**
	 * 在给定的时间内去定位查找元素，如果没找到则超时，抛出异常
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
		} catch (TimeoutException e) {
			logger.error("超时!! " + timeOut + " 秒之后还没找到元素 [" + byElement + "]", e);
		} catch (Exception e) {
			logger.error("给定的时间内定位查找元素异常", e);
		}
		logger.info("成功找到了元素");
		return element;
	}

	/**
	 * 包装点击操作
	 */
	public void click(By byElement) {
		try {
			clickByRetry(byElement, System.currentTimeMillis(), 2500);
		} catch (StaleElementReferenceException e) {
			logger.error("你点击的元素:[" + byElement + "]不再存在!", e);
		} catch (Exception e) {
			logger.error("点击元素 [" + byElement + "]失败", e);
		}
		logger.info("成功点击元素 [" + byElement + "]");
	}

	/** 不能点击时候重试点击操作 */
	public void clickByRetry(By byElement, long startTime, int timeOut) throws Exception {
		try {
			findElementBy(byElement).click();
		} catch (Exception e) {
			if (System.currentTimeMillis() - startTime > timeOut) {
				logger.error(byElement + "元素不可点击", e);
				e.printStackTrace();
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
		} catch (Exception e) {
			logger.error("获取页面标题异常", e);
		}
		logger.info("成功获取页面标题");
		return title;

	}

	/**
	 * 获得元素的文本
	 */
	public String getText(By byElement) {
		String text = null;
		try {
			text = driver.findElement(byElement).getText().trim();
		} catch (Exception e) {
			logger.error("获取元素的文本异常", e);
		}
		logger.info("成功元素的文本");
		return text;
	}

	/**
	 * 获得元素的属性值
	 */
	public String getAttributeText(By byElement, String attribute) {
		String attributeText = null;
		try {
			attributeText = driver.findElement(byElement).getAttribute(attribute).trim();
		} catch (Exception e) {
			logger.error("获取元素的属性值异常", e);
		}
		logger.info("成功元素的属性值");
		return attributeText;
	}

	/**
	 * 包装元素清除操作
	 */
	public void clear(By byElement) {
		try {
			findElementBy(byElement).clear();
		} catch (Exception e) {
			logger.error("清除元素 [" + byElement + "] 上的内容异常", e);
		}
		logger.info("成功清除元素 [" + byElement + "]上的内容");
	}

	/**
	 * 向输入框输入内容
	 */
	public void type(By byElement, CharSequence sendkeys) {
		try {
			findElementBy(byElement).sendKeys(sendkeys);
		} catch (Exception e) {
			logger.error("输入 [" + sendkeys + "] 到 元素[" + byElement + "]异常", e);
		}
		logger.info("成功输入[" + sendkeys + "] 到 [" + byElement + "]");
	}

	/**
	 * 模拟键盘操作的,比如Ctrl+A,Ctrl+C等 参数详解： 1、WebElement element - 要被操作的元素 2、Keys key-
	 * 键盘上的功能键 比如Keys.CONTROL是ctrl键 3、String keyword - 键盘上的字母
	 */
	public void pressKeysOnKeyboard(WebElement element, Keys key, String keyword) {
		try {
			element.sendKeys(Keys.chord(key, keyword));
		} catch (Exception e) {
			logger.error("键盘操作" + key.name() + "+" + keyword + "异常", e);
		}
		logger.info("成功键盘操作" + key.name() + "+" + keyword);

	}

	/**
	 * 判断文本是不是和需求要求的文本一致
	 **/
	public void isTextCorrect(String actual, String expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			logger.error("期望的文字是 [" + expected + "] 但是找到了 [" + actual + "]", e);
			Assert.fail("期望的文字是 [" + expected + "] 但是找到了 [" + actual + "]", e);

		}
		logger.info("找到了期望的文字: [" + expected + "]");

	}

	/**
	 * 判断编辑框是不是可编辑
	 */
	public void isInputEdit(WebElement element) {

	}

	/**
	 * 等待alert出现
	 */
	public Alert switchToPromptedAlertAfterWait(long waitMillisecondsForAlert) throws NoAlertPresentException {
		final int ONE_ROUND_WAIT = 200;
		NoAlertPresentException lastException = null;

		long endTime = System.currentTimeMillis() + waitMillisecondsForAlert;

		for (long i = 0; i < waitMillisecondsForAlert; i += ONE_ROUND_WAIT) {
			try {
				Alert alert = driver.switchTo().alert();
				return alert;
			} catch (NoAlertPresentException e) {
				lastException = e;
			}
			pause(ONE_ROUND_WAIT);

			if (System.currentTimeMillis() > endTime) {
				break;
			}
		}
		throw lastException;
	}

	/**
	 * 暂停当前用例的执行，暂停的时间为：sleepTime
	 */
	public void pause(int sleepTime) {
		if (sleepTime <= 0) {
			return;
		}
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 切换frame - 根据String类型（frame名字）
	 */
	public void inFrame(String frameId) {
		driver.switchTo().frame(frameId);
	}

	/**
	 * 切换frame - 根据frame在当前页面中的顺序来定位
	 */
	public void inFrame(int frameNum) {
		driver.switchTo().frame(frameNum);
	}

	/**
	 * 切换frame - 根据页面元素定位
	 */
	public void switchFrame(WebElement element) {
		try {
			logger.info("正在跳进frame:[" + getLocatorByElement(element, ">") + "]");
			driver.switchTo().frame(element);
		} catch (Exception e) {
			logger.info("跳进frame: [" + getLocatorByElement(element, ">") + "] 失败");
			Assert.fail("跳进frame: [" + getLocatorByElement(element, ">") + "] 失败");
		}
		logger.info("进入frame: [" + getLocatorByElement(element, ">") + "]成功 ");
	}

	/**
	 * 选择下拉选项 -根据value
	 */
	public void selectByValue(By by, String value) {
		Select s = new Select(driver.findElement(by));
		s.selectByValue(value);
	}

	/**
	 * 选择下拉选项 -根据index角标
	 */
	public void selectByIndex(By by, int index) {
		Select s = new Select(driver.findElement(by));
		s.selectByIndex(index);
	}

	/** 检查checkbox是不是勾选 */
	public boolean doesCheckboxSelected(By elementLocator) {
		if (findElementBy(elementLocator).isSelected() == true) {
			logger.info("CheckBox: " + getLocatorByElement(findElementBy(elementLocator), ">") + " 被勾选");
			return true;
		} else
			logger.info("CheckBox: " + getLocatorByElement(findElementBy(elementLocator), ">") + " 没有被勾选");
		return false;

	}

	/**
	 * 选择下拉选项 -根据文本内容
	 */
	public void selectByText(By by, String text) {
		Select s = new Select(driver.findElement(by));
		s.selectByVisibleText(text);
	}

	/**
	 * 获得当前select选择的值
	 */
	public List<WebElement> getCurrentSelectValue(By by) {
		List<WebElement> options = null;
		Select s = new Select(driver.findElement(by));
		options = s.getAllSelectedOptions();
		return options;
	}

	/**
	 * 获得输入框的值 这个方法 是针对某些input输入框 没有value属性，但是又想取得input的 值得方法
	 */
	public String getInputValue(String chose, String choseValue) {
		String value = null;
		switch (chose.toLowerCase()) {
		case "name":
			String jsName = "return document.getElementsByName('" + choseValue + "')[0].value;"; // 把JS执行的值
																									// 返回出去
			value = (String) ((JavascriptExecutor) driver).executeScript(jsName);
			break;

		case "id":
			String jsId = "return document.getElementById('" + choseValue + "').value;"; // 把JS执行的值
																							// 返回出去
			value = (String) ((JavascriptExecutor) driver).executeScript(jsId);
			break;

		default:
			logger.error("未定义的chose:" + chose);
			Assert.fail("未定义的chose:" + chose);

		}
		return value;

	}

	/**
	 * 执行JavaScript 方法
	 */
	public Object executeJS(String js) {
		logger.info("执行JavaScript语句：[" + js + "]");
		return ((JavascriptExecutor) driver).executeScript(js);
	}

	/**
	 * 执行JavaScript 方法和对象 用法：seleniumUtil.executeJS("arguments[0].click();",
	 * seleniumUtil.findElementBy(MyOrdersPage.MOP_TAB_ORDERCLOSE));
	 */
	public Object executeJS(String js, Object... args) {
		logger.info("执行JavaScript语句：[" + js + "]");
		return ((JavascriptExecutor) driver).executeScript(js, args);
	}

	/**
	 * 包装selenium模拟鼠标操作 - 鼠标移动到指定元素
	 */
	public void mouseMoveToElement(By by) {
		Actions builder = new Actions(driver);
		Actions mouse = builder.moveToElement(driver.findElement(by));
		mouse.perform();
	}

	/**
	 * 包装selenium模拟鼠标操作 - 鼠标移动到指定元素
	 */
	public void mouseMoveToElement(WebElement element) {
		Actions builder = new Actions(driver);
		Actions mouse = builder.moveToElement(element);
		mouse.perform();
	}

	/**
	 * 包装selenium模拟鼠标操作 - 鼠标右击
	 */
	public void mouseRightClick(By element) {
		Actions builder = new Actions(driver);
		Actions mouse = builder.contextClick(findElementBy(element));
		mouse.perform();
	}

	/**
	 * 添加cookies,做自动登陆的必要方法
	 */
	public void addCookies(int sleepTime) {
		pause(sleepTime);
		Set<Cookie> cookies = driver.manage().getCookies();
		for (Cookie c : cookies) {
			System.out.println(c.getName() + "->" + c.getValue());
			if (c.getName().equals("logisticSessionid")) {
				Cookie cook = new Cookie(c.getName(), c.getValue());
				driver.manage().addCookie(cook);
				System.out.println(c.getName() + "->" + c.getValue());
				System.out.println("添加成功");
			} else {
				System.out.println("没有找到logisticSessionid");
			}

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

	/** 跳出frame */
	public void outFrame() {
		driver.switchTo().defaultContent();
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

	/** 检查元素是否被勾选 */
	public boolean isSelected(WebElement element) {
		boolean flag = false;
		if (element.isSelected() == true) {
			logger.info("The element: [" + getLocatorByElement(element, ">") + "] is selected");
			flag = true;
		} else if (element.isSelected() == false) {
			logger.info("The element: [" + getLocatorByElement(element, ">") + "] is not selected");
			flag = false;
		}
		return flag;
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

}
