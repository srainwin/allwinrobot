package com.demo.pagesteps;

import org.apache.log4j.Logger;

import com.demo.pages.FramePage;
import com.demo.pages.LoginPage;
import com.demo.utils.SeleniumUtil;

import io.qameta.allure.Step;

/**
 * @author XWR
 * @Description 登录126邮箱页面元素操作封装
 */
public class LoginSteps {
	static Logger logger = Logger.getLogger(LoginSteps.class.getName());

	@Step("选择账号方式，并且输入用户名{1}和密码{2}，点击登录") // allure的步骤显示
	public static void login(SeleniumUtil seleniumUtil, String username, String password, String testurl) {
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
	}

	@Step("打开126邮箱网址后添加cookies刷新免登录") // allure的步骤显示
	public static void loginFree(SeleniumUtil seleniumUtil, String testurl, String cookiesConfigFilePath) {
		logger.info("开始输入126邮箱网址");
		seleniumUtil.get(testurl);
		logger.info("使用cookies方式免登陆");
		seleniumUtil.delAllcookies();
		seleniumUtil.addcookies(cookiesConfigFilePath);
		seleniumUtil.refresh();
	}

	@Step("获取登陆错误提示信息") // allure的步骤显示
	public static String getLoginErrorInfo(SeleniumUtil seleniumUtil) {
		String text = "";
		logger.info("开始获取登陆错误提示信息");
		text = seleniumUtil.getText(LoginPage.loginErrorInfo);
		return text;
	}

	@Step("断言登陆错误提示信息是否为“{1}”") // allure的步骤显示
	public static void assertLoginErrorInfo(SeleniumUtil seleniumUtil, String expect) {
		String actual = LoginSteps.getLoginErrorInfo(seleniumUtil);
		seleniumUtil.assertEquals(actual, expect);
		logger.info("成功断言登陆错误提示信息");
	}

	@Step("获取当前登陆用户名信息") // allure的步骤显示
	public static String getLoginCurrentUser(SeleniumUtil seleniumUtil) {
		String text = "";
		logger.info("开始获取当前登陆用户名信息");
		text = seleniumUtil.getText(LoginPage.loginCurrentUser);
		return text;
	}

	@Step("断言当前登陆用户名信息是否为“{1}”")
	public static void assertLoginCurrentUser(SeleniumUtil seleniumUtil, String expect) {
		String actual = LoginSteps.getLoginCurrentUser(seleniumUtil);
		seleniumUtil.assertEquals(actual, expect);
		logger.info("成功断言当前登陆用户名信息");
	}

	@Step("退出登录")
	public static void logout(SeleniumUtil seleniumUtil) {
		logger.info("开始退出登录");
		seleniumUtil.click(LoginPage.logoutButton);
	}

	@Step("获取退出页面信息")
	public static String getLogoutInfo(SeleniumUtil seleniumUtil) {
		String text = "";
		logger.info("开始获取退出页面信息");
		text = seleniumUtil.getText(LoginPage.logoutInfo);
		return text;
	}

	@Step("断言退出页面信息是否为“{1}”")
	public static void assertLogoutInfo(SeleniumUtil seleniumUtil, String expect) {
		String actual = LoginSteps.getLogoutInfo(seleniumUtil);
		seleniumUtil.assertEquals(actual, expect);
		logger.info("成功断言退出页面信息");
	}
}
