package com.demo.utils;

import java.io.FileReader;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.os.WindowsUtils;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @author young
 * @decription 在不同的平台上选择对应的浏览器,系统平台程序自动判断是什么平台
 */
public class SelectBrowser {
	static Logger logger = Logger.getLogger(SelectBrowser.class.getName());

	public WebDriver selectByName(String browsername, String driverConfigFilePath) {
		try {
			logger.info("启动测试浏览器：[" + browsername + "]");
			// 调用getPropertiesData方法获取各类型浏览器驱动的路径
			String chromedriverPath = getPropertiesData(driverConfigFilePath, "chromedriver");
			String firefoxdriverPath = getPropertiesData(driverConfigFilePath, "firefoxdriver");
			String iedriverPath = getPropertiesData(driverConfigFilePath, "iedriver");
			String ghostdriverPath = getPropertiesData(driverConfigFilePath, "ghostdriver");

			if (browsername.equalsIgnoreCase("ie")) {
				WindowsUtils.killByName("IEDriverServer.exe");
				System.setProperty("webdriver.ie.driver", iedriverPath);
				// IE的常规设置，需要忽略浏览器安全保护模式的设置和忽略浏览器缩放级别设置，便于执行自动化测试。
				DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
				ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
				ieCapabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
				// 返回ie浏览器对象
				return new InternetExplorerDriver(ieCapabilities);
			} else if (browsername.equalsIgnoreCase("chrome")) {
				WindowsUtils.killByName("chromedriver.exe");
				System.setProperty("webdriver.chrome.driver", chromedriverPath);
				// 去掉提示“chrome正受到自动化测试软件的控制”
				ChromeOptions option=new ChromeOptions();
				option.addArguments("disable-infobars");
				// 返回谷歌浏览器对象
				return new ChromeDriver(option);
			} else if (browsername.equalsIgnoreCase("firefox")) {
				WindowsUtils.killByName("geckodriver.exe");
				System.setProperty("webdriver.gecko.driver", firefoxdriverPath);
				// 返回火狐浏览器对象
				return new FirefoxDriver();
				//System.setProperty("webdriver.firefox.bin", firefoxdriverPath);
				//System.setProperty("webdriver.firefox.marionette", firefoxdriverPath);
				//DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				//capabilities.setCapability("marionette", false); 
				//return new FirefoxDriver(capabilities);
			} else if (browsername.equalsIgnoreCase("ghost")) {
				WindowsUtils.killByName("phantomjs.exe");
				// ghost的常规设置
				DesiredCapabilities ghostCapabilities = new DesiredCapabilities();
				ghostCapabilities.setJavascriptEnabled(true);
				ghostCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,ghostdriverPath);
				// 返回ghost对象
				return new PhantomJSDriver(ghostCapabilities);
			} else {
				WindowsUtils.killByName("chromedriver.exe");
				logger.warn(browsername + "浏览器不支持，支持ie、chrome、firefox和ghost，将默认使用chrome浏览器进行");
				System.setProperty("webdriver.chrome.driver", chromedriverPath);
				// 返回谷歌浏览器对象
				return new ChromeDriver();
			}
		} catch (Exception e) {
			logger.error("选择浏览器类型发生异常",e);
			return null;
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
}
