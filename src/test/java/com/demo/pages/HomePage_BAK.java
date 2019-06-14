package com.demo.pages;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author XWR
 * @Description 126邮箱首页元素定位封装
 */
public class HomePage_BAK {
	private static WebElement element = null;

	/**
	 * @Description 当前窗口句柄
	 * @param driver
	 * @return
	 */
	public static String windowHandle (WebDriver driver){
		String Handle = driver.getWindowHandle();
		return Handle;
	}
	
	/**
	 * @Description 所有窗口句柄
	 * @param driver
	 * @return
	 */
	public static Set<String> windowHandles (WebDriver driver){
		Set<String> Handles = driver.getWindowHandles();
		return Handles;
	}
	
	/**
	 * @Description 窗口标题
	 * @param driver
	 * @return
	 */
	public static String windowTitile (WebDriver driver){
		String title = driver.getTitle();
		return title;
	}
	
	/**
	 * @Description 登录保护的iframe
	 * @param driver
	 * @return
	 */
	public static WebDriver loginProtectFrame(WebDriver driver){
		element = driver.findElement(By.cssSelector("iframe.frame-main-cont-iframe"));
		driver = driver.switchTo().frame(element);
		return driver;
	}
	
	/**
	 * @Description 退出iframe
	 * @param driver
	 * @return
	 */
	public static WebDriver outFrame(WebDriver driver){
		driver = driver.switchTo().defaultContent();
		return driver;
	}
	
	/**
	 * @Description 首页Tab
	 * @param driver
	 * @return
	 */
	public static WebElement homepageTab (WebDriver driver){
		element = driver.findElement(By.id("_mail_tabitem_0_3"));
		return element;
	}
	
	/**
	 * @Description 首页Tab-标志：问候名
	 * @param driver
	 * @return
	 */
	public static WebElement homepageSign (WebDriver driver){
		element = driver.findElement(By.cssSelector("span[id$='dvGreetName']"));
		return element;
	}
	
	/**
	 * @Description 总览-未读邮件
	 * @param driver
	 * @return
	 */
	public static WebElement overviewUnreadMail (WebDriver driver){
		element = driver.findElement(By.cssSelector(".gWel-mailInfo-unread"));
 		return element;
	}
	
	/**
	 * @Description 总览-未读邮件Tab-标题:未读邮件
	 * @param driver
	 * @return
	 */
	public static WebElement overviewUnreadMailTab (WebDriver driver){
		element = driver.findElement(By.cssSelector("li[id^='_mail_tabitem'][role='tab'][title='未读邮件']"));
 		return element;
	}
	
	/**
	 * @Description 总览-待办邮件
	 * @param driver
	 * @return
	 */
	public static WebElement overviewTodoMail (WebDriver driver){
		element = driver.findElement(By.cssSelector(".gWel-mailInfo-todo"));
		return element;
	}
	
	/**
	 * @Description 总览-待办邮件Tab-标题:待办邮件
	 * @param driver
	 * @return
	 */
	public static WebElement overviewTodoMailTab (WebDriver driver){
		element = driver.findElement(By.cssSelector("li[id^='_mail_tabitem'][role='tab'][title='待办邮件']"));
 		return element;
	}
	
	/**
	 * @Description 总览-联系人邮件
	 * @param driver
	 * @return
	 */
	public static WebElement overviewContactMail (WebDriver driver){
		element = driver.findElement(By.cssSelector(".gWel-mailInfo-cnta"));
		return element;
	}
	
	/**
	 * @Description 总览-联系人Tab-标题:联系人邮件
	 * @param driver
	 * @return
	 */
	public static WebElement overviewContactMailTab (WebDriver driver){
		element = driver.findElement(By.cssSelector("li[id^='_mail_tabitem'][role='tab'][title='联系人邮件']"));
 		return element;
	}

	/**
	 * @Description 总览-积分
	 * @param driver
	 * @return
	 */
	public static WebElement overviewJifen (WebDriver driver){
		element = driver.findElement(By.cssSelector("li[id$='_dvClub']"));
		return element;
	}
	
	/**
	 * @Description 总览-安全度
	 * @param driver
	 * @return
	 */
	public static WebElement overviewSafetyDegree (WebDriver driver){
		element = driver.findElement(By.cssSelector("li[id$='_dvHealth']"));
		return element;
	}
	
	/**
	 * @Description 总览-安全度-标志:邮箱健康指数"
	 * @param driver
	 * @return
	 */
	public static WebElement overviewSafetyDegreeSign (WebDriver driver){
		element = driver.findElement(By.cssSelector(".ip0"));
 		return element;
	}
	
	/**
	 * @Description 总览-登录保护
	 * @param driver
	 * @return
	 */
	public static WebElement overviewLoginProtect (WebDriver driver){
		element = driver.findElement(By.cssSelector("li.gWel-mailInfo-pinle[title='登录保护']"));
		return element;
	}
	
	/**
	 * @Description 总览-登录保护-标志:邮箱登录二次验证"
	 * @param driver
	 * @return
	 */
	public static WebElement overviewLoginProtectSign (WebDriver driver){
		//element = driver.findElement(By.cssSelector("strong[data-reactid='.0.0.1.0']"));
		element = driver.findElement(By.cssSelector("div.P-intro > p:nth-child(2) > strong"));
 		return element;
	}
	
	/**
	 * @Description 总览-每日生活
	 * @param driver
	 * @return
	 */
	public static WebElement overviewDailyLife (WebDriver driver){
		element = driver.findElement(By.cssSelector("li.gWel-mailInfo-pinle[title='每日生活']"));
		return element;
	}
	
	/**
	 * @Description 总览-每日生活Tab-标题:严选每日推荐
	 * @param driver
	 * @return
	 */
	public static WebElement overviewDailyLifeTab (WebDriver driver){
		element = driver.findElement(By.cssSelector("li[id^='_mail_tabitem'][role='tab'][title='严选每日推荐']"));
 		return element;
	}
}
