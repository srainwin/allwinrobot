package com.demo.cases.login;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.demo.base.LoginBase;
import com.demo.pagesoperation.LoginOperation;
import com.demo.utils.TestNGListener;

import io.qameta.allure.Description;
@Listeners({ TestNGListener.class})
public class LoginPage_003_Logout extends LoginBase {

	/**
	 * @Description登录测试用例003：成功登出126邮箱。注意：登出邮箱后之前获取保存的cookies将失效，需重新登录后getcookies保存。 @param
	 *                                                                               expecttext
	 * @param itestcontext
	 * @throws Exception
	 */
	@Test(dataProvider = "testdata", description = "登出邮箱测试用例")
	@Description("操作步骤：" + "1、打开已登录页面；" + "2、点击退出按钮；" + "预期结果：" + "1、成功打开已登录页面；" + "2、成功退出邮箱；")
	public void loginSucess(String expect) throws Exception {
		// 登陆
		LoginOperation.loginFree(seleniumUtil, testurl, cookiesConfigFilePath);
		// 登出
		LoginOperation.logout(seleniumUtil);
		// 登出信息断言
		LoginOperation.assertLogoutInfo(seleniumUtil, expect);
	}
}
