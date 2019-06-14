package com.demo.cases.login;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.demo.base.LoginBase;
import com.demo.pagesoperation.LoginOperation;
import com.demo.utils.TestFailListener;
import com.demo.utils.TestRetryListener;

import io.qameta.allure.Description;
@Listeners({ TestFailListener.class ,TestRetryListener.class})
public class LoginPage_003_Logout extends  LoginBase {
	
	/**
	 * @Description 登录测试用例001：成功登录126邮箱
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	@Test(dataProvider = "testdata" ,description = "登出邮箱测试用例")
	@Description("操作步骤："
				+ "1、打开已登录页面；"
				+ "2、点击退出按钮；"
				+ "预期结果："
				+ "1、成功打开已登录页面；"
				+ "2、成功退出邮箱；")
	public void loginSucess(String expecttext,ITestContext itestcontext) throws Exception {
		//登陆
		LoginOperation.loginFree(seleniumUtil,testurl,itestcontext);
		//登出
		LoginOperation.logout(seleniumUtil);
		//登出信息断言
		String actual = LoginOperation.getLogoutInfo(seleniumUtil);
		Assert.assertEquals(actual, expecttext);
	}
}
