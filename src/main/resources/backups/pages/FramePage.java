package com.demo.pages;

import org.openqa.selenium.By;

/**
 * @author XWR
 * @Description 这个类算不上一个page页，因为这个网站涉及到的frame可能比较多，所以我们把frame抓取出来用page页来存储
 */
public class FramePage {
	/**登录页 登录表单frame名字 */
	public static final By loginFrame = By.cssSelector("iframe[id^='x-URS-iframe']");
	//public static final By loginFrame = By.xpath("//iframe[starts-with(@id,'x-URS-iframe')]")
	
	/**首页 登录保护表单iframe名字 */
	public static final By loginProtectFrame = By.cssSelector("iframe.frame-main-cont-iframe");
}
