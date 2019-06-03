package com.demo.pageoperation;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.demo.pages.LoginPage;

/**
 * @author XWR
 * @Description 登录126邮箱页面元素操作封装
 */
public class LoginOperation {
	static Logger logger = Logger.getLogger(LoginOperation.class.getName());

	/**
	 * @Description 登陆方法
	 * @param driver
	 * @param username
	 * @param password
	 */
	public static void login(WebDriver driver,String username,String password,String baseurl){
		try {
			logger.info("开始输入126邮箱网址");
			LoginPage.url(driver, baseurl);
			logger.info("使用账号方式登陆");
			LoginPage.loginByNormal(driver).click();
			logger.info("开始用户登录");
			LoginPage.loginFrame(driver);
			LoginPage.loginUsername(driver).clear();
			LoginPage.loginUsername(driver).sendKeys(username);
			LoginPage.loginPassword(driver).clear();
			LoginPage.loginPassword(driver).sendKeys(password);
			LoginPage.loginButton(driver).click();
		} catch(Exception e){
			logger.error("登陆异常",e);
		}
	}
	
	/**
	 * @Description 获取登陆错误提示信息
	 * @param driver
	 * @return
	 */
	public static String getLoginErrorInfo(WebDriver driver){
		String text = "";
		try {
			logger.info("开始获取登陆错误提示信息");
			text = LoginPage.loginErrorInfo(driver).getText();
		}catch(Exception e){
			logger.error("获取不到登陆错误提示信息！",e);
		}
		return text;
		
	}
	
	/**
	 * @Description 获取当前登陆用户名信息
	 * @param driver
	 * @return
	 */
	public static String getLoginCurrentUser(WebDriver driver){
		String text = "";
		try {
			logger.info("开始获取当前登陆用户名信息");
			text = LoginPage.loginCurrentUser(driver).getText();
		}catch(Exception e){
			logger.error("获取不到当前获取登陆用户名信息！",e);
		}
		return text;
	}
	
	/**
	 * @Description 退出方法
	 * @param driver
	 */
	public static void logout(WebDriver driver){
		try {
			logger.info("开始退出登录");
			LoginPage.logoutButton(driver).click();
		}catch(Exception e){
			logger.error("退出登录异常！",e);
		}
		
	}
}
