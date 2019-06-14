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
public class LoginPage_001_LoginSuccess extends  LoginBase {
	
	/**
	 * @Description 登录测试用例001：成功登录126邮箱
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	@Test(dataProvider = "testdata" ,description = "登陆正向测试用例")
	@Description("操作步骤："
				+ "1、输入用户名和密码，点击登录；"
				+ "预期结果："
				+ "1、成功登录，且当前用户名信息正确；")
	public void loginSucess(String username,String password,ITestContext itestcontext) throws Exception {
		//登陆
		LoginOperation.login(seleniumUtil,username, password, testurl);
		Thread.sleep(2000);
		//获取cookies保存，便于其他用例免登录
		seleniumUtil.cookiesSaveInFile(itestcontext);
		//用户信息断言
		String actual = LoginOperation.getLoginCurrentUser(seleniumUtil);
		Assert.assertEquals(actual, username + "@126.com");
	}
}
