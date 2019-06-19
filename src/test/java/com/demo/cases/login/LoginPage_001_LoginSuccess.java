package com.demo.cases.login;

import org.testng.annotations.Test;

import com.demo.base.LoginBase;
import com.demo.pagesoperation.LoginOperation;

import io.qameta.allure.Description;
//@Listeners({ TestFailListener.class ,TestRetryListener.class})
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
	public void loginSucess(String username, String password, String expect) throws Exception {
		//登陆
		LoginOperation.login(seleniumUtil,username, password, testurl);
		//获取cookies保存，便于其他用例免登录
		seleniumUtil.cookiesSaveInFile(cookiesConfigFilePath);
		//用户信息断言
		LoginOperation.assertLoginCurrentUser(seleniumUtil, expect);
	}
}
