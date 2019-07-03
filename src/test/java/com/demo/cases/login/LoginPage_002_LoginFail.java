package com.demo.cases.login;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.demo.base.LoginBase;
import com.demo.pagesteps.LoginOperation;
import com.demo.utils.TestNGListener;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
@Listeners({ TestNGListener.class}) //用例监听，主要是失败用例截图功能
@Epic("126邮箱自动化测试实战") // allure用例组织，一级模块
@Feature("邮箱登录") // allure组织，二级模块
public class LoginPage_002_LoginFail extends  LoginBase {
	
	@Story("登陆反向测试用例") // allure用例组织，三级模块
	@Test(dataProvider = "testdata" ,description = "输入错误用户名或密码登录") // allure用例名是description
	@Description("输入错误用户名或密码并登录失败显示提示信息") // allure用例描述
	@Severity(SeverityLevel.BLOCKER) // allure用例重要等级
	public void loginFail(String username,String password,String expect) {
		//登陆
		LoginOperation.login(seleniumUtil,username, password,testurl);
		//用户信息断言
		LoginOperation.assertLoginErrorInfo(seleniumUtil, expect);
	}
}
