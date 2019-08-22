package com.demo.pages;

import org.openqa.selenium.By;

/**
 * @author XWR
 * @Description 126邮箱首页元素定位封装
 */
public class HomePage {
	/** 首页Tab */
	public static final By homepageTab = By.id("_mail_tabitem_0_3");
	
	/** 首页Tab-标志：问候名 */
	public static final By homepageSign = By.cssSelector("span[id$='dvGreetName']");
	
	/** 总览-未读邮件 */
	public static final By overviewUnreadMail = By.cssSelector(".gWel-mailInfo-unread");

	/** 总览-未读邮件Tab-标题:未读邮件 */
	public static final By overviewUnreadMailTab = By.cssSelector("li[id^='_mail_tabitem'][role='tab'][title='未读邮件']");
	
	/** 总览-待办邮件 */
	public static final By overviewTodoMail = By.cssSelector(".gWel-mailInfo-todo");
	
	/** 总览-待办邮件Tab-标题:待办邮件 */
	public static final By overviewTodoMailTab = By.cssSelector("li[id^='_mail_tabitem'][role='tab'][title='待办邮件']");
	
	/** 总览-联系人邮件 */
	public static final By overviewContactMail = By.cssSelector(".gWel-mailInfo-cnta");

	/** 总览-联系人Tab-标题:联系人邮件 */
	public static final By overviewContactMailTab = By.cssSelector("li[id^='_mail_tabitem'][role='tab'][title='联系人邮件']");
	
	/** 总览-积分 */
	public static final By overviewJifen = By.cssSelector("li[id$='_dvClub']");
	
	/** 总览-安全度 */
	public static final By overviewSafetyDegree = By.cssSelector("li[id$='_dvHealth']");
	
	/** 总览-安全度-标志:邮箱健康指数 */
	public static final By overviewSafetyDegreeSign = By.cssSelector(".ip0");
	
	/** 总览-登录保护 */
	public static final By overviewLoginProtect = By.cssSelector("li.gWel-mailInfo-pinle[title='登录保护']");
	
	/** 总览-登录保护-标志:邮箱登录二次验证 */
	public static final By overviewLoginProtectSign = By.cssSelector("div.P-intro > p:nth-child(2) > strong");
	//public static final By overviewLoginProtectSign = By.cssSelector("strong[data-reactid='.0.0.1.0']";
	
	/** 总览-每日生活 */
	public static final By overviewDailyLife = By.cssSelector("li.gWel-mailInfo-pinle[title='每日生活']");
	
	/** 总览-每日生活Tab-标题:严选每日推荐 */
	public static final By overviewDailyLifeTab = By.cssSelector("li[id^='_mail_tabitem'][role='tab'][title='严选每日推荐']");
	
	/** 收信 */
	public static final By receiveLetters = By.xpath("//span[text()='收 信']");
	
	/** 写信 */
	public static final By writeLetters = By.xpath("//span[text()='写 信']");
}
