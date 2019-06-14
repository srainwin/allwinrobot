package com.demo.pagesoperation;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

import com.demo.pages.LoginPage_BAK;
import com.demo.utils.SeleniumUtil;

/**
 * @author XWR
 * @Description 登录126邮箱页面元素操作封装
 */
public class LoginOperation_BAK {
	static Logger logger = Logger.getLogger(LoginOperation_BAK.class.getName());
	static SeleniumUtil seleniumUtil = new SeleniumUtil();

	/**
	 * @Description 登陆方法
	 * @param driver
	 * @param username
	 * @param password
	 */
	public static void login(WebDriver driver,String username,String password,String testurl){
		try {
			logger.info("开始输入126邮箱网址");
			LoginPage_BAK.url(driver, testurl);
			logger.info("使用账号方式登陆");
			LoginPage_BAK.loginByAccount(driver).click();
			logger.info("开始用户登录");
			LoginPage_BAK.loginFrame(driver);
			LoginPage_BAK.loginUsername(driver).clear();
			LoginPage_BAK.loginUsername(driver).sendKeys(username);
			LoginPage_BAK.loginPassword(driver).clear();
			LoginPage_BAK.loginPassword(driver).sendKeys(password);
			LoginPage_BAK.loginButton(driver).click();
		} catch(Exception e){
			logger.error("登陆异常",e);
		}
	}
	/**
	 * @Description 免登陆方法（利用cookies）
	 * @param driver
	 * @param username
	 * @param password
	 */
	public static void loginFree(WebDriver driver,String testurl,ITestContext itestcontext){
		try {
			logger.info("开始输入126邮箱网址");
			LoginPage_BAK.url(driver, testurl);
			logger.info("使用cookies方式免登陆");
			seleniumUtil.addcookies(itestcontext);
		} catch(Exception e){
			logger.error("使用cookies方式免登陆异常",e);
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
			text = LoginPage_BAK.loginErrorInfo(driver).getText();
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
			text = LoginPage_BAK.loginCurrentUser(driver).getText();
		}catch(Exception e){
			logger.error("获取不到当前登陆用户名信息！",e);
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
			LoginPage_BAK.logoutButton(driver).click();
		}catch(Exception e){
			logger.error("退出登录异常！",e);
		}
		
	}
}
