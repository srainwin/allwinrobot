package com.demo.pages;

import org.openqa.selenium.By;

/**
 * @author XWR
 * @Description 登录126邮箱页面元素定位封装
 */
public class LoginPage {
	
	/** 账号形式的登陆选择图标 */
	public static final By loginByAccount = By.cssSelector("#lbNormal");

	/** 用户名输入框 */
	public static final By loginUsername = By.cssSelector(".dlemail");

	/** 密码输入框 */
	public static final By loginPassword = By.cssSelector(".dlpwd");
	
	/** 登陆按钮 */
	public static final By loginButton = By.className("u-loginbtn");
	/** 当前用户名信息 */
	public static final By loginCurrentUser = By.id("spnUid");
	
	/** 退出按钮 */
	public static final By logoutButton = By.linkText("退出");
	
	/** 退出页面信息 */
	public static final By logoutInfo = By.cssSelector("section.info > h1");
	
	/** 登陆错误提示信息 */
	public static final By loginErrorInfo = By.cssSelector(".ferrorhead");
}
