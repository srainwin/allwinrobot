package com.demo.utils;

import java.io.FileReader;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @author young
 * @decription 在不同的平台上选择对应的浏览器,系统平台程序自动判断是什么平台
 */
public class SelectLocalBrowser {
	static Logger logger = Logger.getLogger(SelectLocalBrowser.class.getName());

	public WebDriver selectByName(String browsername, String driverConfigFilePath) {

		logger.info("准备启动本地测试浏览器：[" + browsername + "]");
		// 调用getPropertiesData方法获取各类型浏览器驱动的路径
		String chromedriverPath = getPropertiesData(driverConfigFilePath, "chromedriver");
		String firefoxdriverPath = getPropertiesData(driverConfigFilePath, "firefoxdriver");
		String iedriverPath = getPropertiesData(driverConfigFilePath, "iedriver");
		String ghostdriverPath = getPropertiesData(driverConfigFilePath, "ghostdriver");

		if (browsername.equalsIgnoreCase("ie")) {
			return createLocalIEDriver(iedriverPath);
			
		} else if (browsername.equalsIgnoreCase("chrome")) {
			return createLocalChromeDriver(chromedriverPath);
			
		} else if (browsername.equalsIgnoreCase("firefox")) {
			return createLocalFirefoxDriver(firefoxdriverPath);
			
		} else if (browsername.equalsIgnoreCase("ghost")) {
			return createLocalPhantomjsDriver(ghostdriverPath);
			
		} else {
			logger.warn(browsername + "浏览器不支持，支持ie、chrome、firefox和ghost，将默认使用chrome浏览器进行");
			return createLocalChromeDriver(chromedriverPath);
		}
	}

	/**
	 * @Description 调用方法读取driver.properties配置文件，获取浏览器驱动的路径值
	 * @param propertiesFilePath
	 * @param key
	 * @return
	 */
	public static String getPropertiesData(String propertiesFilePath, String key) {
		String value = null;
		try {
			Properties properties = new Properties();
			// 读取driver.properties文件
			FileReader filereader = new FileReader(propertiesFilePath);
			properties.load(filereader);
			// 获取key对应的value值
			value = properties.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/** 启用本地IE浏览器 */
	public static WebDriver createLocalIEDriver(String iedriverPath) {
		System.setProperty("webdriver.ie.driver", iedriverPath);
		// IE的常规设置，需要忽略浏览器安全保护模式的设置避免各个域的安全级别不一致导致的错误,还有忽略浏览器缩放级别设置，便于执行自动化测试。
		DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
		ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		ieCapabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
		// 返回ie浏览器对象
		try {
			return new InternetExplorerDriver(ieCapabilities);
		} catch (Exception e) {
			logger.error("启用本地IE浏览器发生异常", e);
			return null;
		}
	}

	/** 启用本地chrome浏览器 */
	public static WebDriver createLocalChromeDriver(String chromedriverPath) {
		System.setProperty("webdriver.chrome.driver", chromedriverPath);
		// 去掉提示“chrome正受到自动化测试软件的控制”
		ChromeOptions option = new ChromeOptions();
		option.addArguments("disable-infobars");
		// 返回谷歌浏览器对象
		try {
			return new ChromeDriver(option);
		} catch (Exception e) {
			logger.error("启用本地chrome浏览器发生异常", e);
			return null;
		}
	}

	/** 启用本地firefox浏览器 */
	public static WebDriver createLocalFirefoxDriver(String firefoxdriverPath) {
		System.setProperty("webdriver.gecko.driver", firefoxdriverPath);
		// 返回火狐浏览器对象
		try {
			return new FirefoxDriver();
		} catch (Exception e) {
			logger.error("启用本地firefox浏览器发生异常", e);
			return null;
		}

		// System.setProperty("webdriver.firefox.bin", firefoxdriverPath);
		// System.setProperty("webdriver.firefox.marionette",
		// firefoxdriverPath);
		// DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		// capabilities.setCapability(FirefoxDriver.MARIONETTE, false);
		// return new FirefoxDriver(capabilities);
	}

	/** 启用本地phantomjs无界面浏览器 */
	public static WebDriver createLocalPhantomjsDriver(String ghostdriverPath) {
		// ghost的常规设置
		DesiredCapabilities ghostCapabilities = new DesiredCapabilities();
		ghostCapabilities.setJavascriptEnabled(true);
		ghostCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, ghostdriverPath);
		// 返回ghost对象
		try {
			return new PhantomJSDriver(ghostCapabilities);
		} catch (Exception e) {
			logger.error("启用本地phantomjs无界面浏览器发生异常", e);
			return null;
		}
	}
}
