package com.demo.cases.login;

import org.testng.annotations.Test;

import com.demo.base.LoginBase;
import com.demo.pagesoperation.LoginOperation;

import io.qameta.allure.Description;

public class LoginPage_002_LoginFail extends  LoginBase {
	
	/**
	 * @Description 登录测试用例002：失败登录126邮箱
	 * @param username
	 * @param password
	 * @param expecttext
	 * @throws Exception
	 */
	@Test(dataProvider = "testdata" ,description = "登陆反向测试用例")
	@Description("操作步骤：\r\n"
				+ "1、输入用户名和密码，点击登录；\r\n"
				+ "预期结果：\r\n"
				+ "1、失败登录，且显示相应登陆失败提示信息\r\n")
	public void loginFail(String username,String password,String expect) throws Exception {
		//登陆
		LoginOperation.login(seleniumUtil,username, password,testurl);
		//用户信息断言
		LoginOperation.assertLoginErrorInfo(seleniumUtil, expect);
	}
}
