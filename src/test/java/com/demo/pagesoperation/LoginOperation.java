package com.demo.pagesoperation;

import org.apache.log4j.Logger;
import org.testng.ITestContext;

import com.demo.pages.FramePage;
import com.demo.pages.LoginPage;
import com.demo.utils.SeleniumUtil;

/**
 * @author XWR
 * @Description 登录126邮箱页面元素操作封装
 */
public class LoginOperation {
	static Logger logger = Logger.getLogger(LoginOperation.class.getName());

	/**
	 * @Description 登陆方法
	 * @param username
	 * @param password
	 * @param testurl
	 */
	public static void login(SeleniumUtil seleniumUtil, String username,String password,String testurl){
		try {
			logger.info("开始输入126邮箱网址");
			seleniumUtil.get(testurl);
			logger.info("使用账号方式登陆");
			seleniumUtil.click(LoginPage.loginByAccount);
			logger.info("开始用户登录");
			seleniumUtil.inFrame(FramePage.loginFrame);
			seleniumUtil.clear(LoginPage.loginUsername);
			seleniumUtil.type(LoginPage.loginUsername, username);
			seleniumUtil.clear(LoginPage.loginPassword);
			seleniumUtil.type(LoginPage.loginPassword, password);
			seleniumUtil.click(LoginPage.loginButton);
		} catch(Exception e){
			logger.error("登陆异常",e);
		}
	}
	
	/**
	 * @Description 免登陆方法（利用cookies）
	 * @param itestcontext
	 */
	public static void loginFree(SeleniumUtil seleniumUtil, String testurl, ITestContext itestcontext){
		try {
			logger.info("开始输入126邮箱网址");
			seleniumUtil.get(testurl);
			logger.info("使用cookies方式免登陆");
			seleniumUtil.delAllcookies();
			seleniumUtil.addcookies(itestcontext);
			seleniumUtil.refresh();
		} catch(Exception e){
			logger.error("使用cookies方式免登陆异常",e);
		}
	}

	/**
	 * @Description 获取登陆错误提示信息
	 * @return
	 */
	public static String getLoginErrorInfo(SeleniumUtil seleniumUtil){
		String text = "";
		try {
			logger.info("开始获取登陆错误提示信息");
			text = seleniumUtil.getText(LoginPage.loginErrorInfo);
		}catch(Exception e){
			logger.error("获取不到登陆错误提示信息！",e);
		}
		return text;
		
	}
	
	/**
	 * @Description 获取当前登陆用户名信息
	 * @return
	 */
	public static String getLoginCurrentUser(SeleniumUtil seleniumUtil){
		String text = "";
		try {
			logger.info("开始获取当前登陆用户名信息");
			text = seleniumUtil.getText(LoginPage.loginCurrentUser);
		}catch(Exception e){
			logger.error("获取不到当前登陆用户名信息！",e);
		}
		return text;
	}
	
	/**
	 * @Description 退出方法
	 */
	public static void logout(SeleniumUtil seleniumUtil){
		try {
			logger.info("开始退出登录");
			seleniumUtil.click(LoginPage.logoutButton);
		}catch(Exception e){
			logger.error("退出登录异常！",e);
		}
	}
	
	/**
	 * @Description 获取退出页面信息
	 */
	public static String getLogoutInfo(SeleniumUtil seleniumUtil){
		String text = "";
		try {
			logger.info("开始获取退出页面信息");
			text = seleniumUtil.getText(LoginPage.logoutInfo);
		}catch(Exception e){
			logger.error("获取退出页面信息异常！",e);
		}
		return text;
	}
}
