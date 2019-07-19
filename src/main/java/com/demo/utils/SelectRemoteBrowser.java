package com.demo.utils;

import java.net.URL;

import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * @author XWR
 * @Description 基于selenium grid2分布式测试，选择远程node节点服务器浏览器（前提是先配置好hub和node节点）
 */
public class SelectRemoteBrowser {

	public static Logger logger = Logger.getLogger(SelectRemoteBrowser.class.getName());

	public WebDriver selectByName(String browsername, String huburl) {
		
		logger.info("准备启动远程测试浏览器：[" + browsername + "]");
		
		if (browsername.equalsIgnoreCase("ie")) {
			return createRemoteIEDriver(huburl);
		} else if (browsername.equalsIgnoreCase("chrome")) {
			return createRemoteChromeDriver(huburl);
		} else if (browsername.equalsIgnoreCase("firefox")) {
			return createRemoteFirefoxDriver(huburl);
		} else if (browsername.equalsIgnoreCase("ghost")) {
			return createRemotePhantomjsDriver(huburl);
		} else {
			logger.warn(browsername + "浏览器不支持，支持ie、chrome、firefox和ghost(phantomjs)，将默认使用chrome浏览器进行");
			return createRemoteChromeDriver(huburl);
		}
	}

	/** 启用远程IE浏览器 */
	public static WebDriver createRemoteIEDriver(String huburl) {
		// 指定调用IE进行测试
		DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
		// IE的常规设置，需要忽略浏览器安全保护模式的设置避免各个域的安全级别不一致导致的错误
		ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		// IE的常规设置，需要忽略浏览器缩放级别设置，便于执行自动化测试
		ieCapabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
		// 设置浏览器名字
		ieCapabilities.setBrowserName("ie");
		// 设置浏览器版本号，若node没配置版本号可不设置
		// capability.setVersion("");
		// 设置平台类型（如windows、linux、mac等，any是任意平台）
		ieCapabilities.setPlatform(Platform.ANY);
		try {
			return new RemoteWebDriver(new URL(huburl), ieCapabilities);
		} catch (Exception e) {
			logger.error("启用远程IE浏览器发生异常", e);
			return null;
		}
	}

	// 启用远程调用chrome
	public static WebDriver createRemoteChromeDriver(String huburl) {
		// 指定调用chrome进行测试
		DesiredCapabilities chromeCapability = DesiredCapabilities.chrome();
		// chrome常规设置，去掉提示“chrome正受到自动化测试软件的控制”
		ChromeOptions option = new ChromeOptions();
		option.addArguments("disable-infobars");
		chromeCapability.setCapability(ChromeOptions.CAPABILITY, option);
		// 设置浏览器名字
		chromeCapability.setBrowserName("chrome");
		// 设置浏览器版本号，若node没配置版本号可不设置
		// chromeCapability.setVersion("");
		// 设置平台类型（如windows、linux、mac等，any是任意平台）
		chromeCapability.setPlatform(Platform.ANY);
		try {
			return new RemoteWebDriver(new URL(huburl), chromeCapability);
		} catch (Exception e) {
			logger.error("启用远程chrome浏览器发生异常", e);
			return null;
		}
	}

	// 启用远程调用firefox
	public static WebDriver createRemoteFirefoxDriver(String huburl) {
		// 指定调用firefox进行测试
		DesiredCapabilities firefoxCapability = DesiredCapabilities.firefox();
		// firefox常规设置，设置不使用MARIONETTE，MARIONETTE是较新版firefox自带的自动化驱动，与webdriver不可交互
		// firefoxCapability.setCapability(FirefoxDriver.MARIONETTE, false);
		// 设置浏览器名字
		firefoxCapability.setBrowserName("firefox");
		// 设置浏览器版本号，若node没配置版本号可不设置
		// firefoxCapability.setVersion("");
		// 设置平台类型（如windows、linux、mac等，any是任意平台）
		firefoxCapability.setPlatform(Platform.ANY);
		try {
			return new RemoteWebDriver(new URL(huburl), firefoxCapability);
		} catch (Exception e) {
			logger.error("启用远程firefox浏览器发生异常", e);
			return null;
		}
	}

	// 启用远程调用phantomjs
	public static WebDriver createRemotePhantomjsDriver(String huburl) {
		// 指定调用chrome进行测试
		DesiredCapabilities phantomjsCapability = DesiredCapabilities.firefox();
		// 设置浏览器名字
		phantomjsCapability.setBrowserName("phantomjs");
		// 设置浏览器版本号，若node没配置版本号可不设置
		// phantomjsCapability.setVersion("");
		// 设置平台类型（如windows、linux、mac等，any是任意平台）
		phantomjsCapability.setPlatform(Platform.ANY);
		try {
			return new RemoteWebDriver(new URL(huburl), phantomjsCapability);
		} catch (Exception e) {
			logger.error("启用远程phantomjs无界面浏览器发生异常", e);
			return null;
		}
	}

}
