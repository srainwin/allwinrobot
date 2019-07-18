package com.demo.cases.login;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.demo.base.LoginBase;
import com.demo.pagesteps.LoginSteps;
import com.demo.utils.TestNGListener;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
@Listeners({TestNGListener.class})
@Epic("126邮箱自动化测试实战") // allure用例组织，一级模块
@Feature("邮箱登录") // allure组织，二级模块
public class LoginPage_003_Logout extends LoginBase {

	@Story("登出邮箱测试用例") // allure用例组织，三级模块
	@Test(groups = {"login"}, dataProvider = "testdata", description = "登出邮箱") // allure用例名是description
	@Description("输入正确的用户名和密码登录后，点击登出邮箱成功。注意：登出邮箱后之前获取保存的cookies将失效，需重新登录后getcookies保存。") // allure用例描述
	@Severity(SeverityLevel.BLOCKER) // allure用例重要等级
	public void loginOut(String username, String password,String expect) {
		// 账号登陆
		LoginSteps.login(seleniumUtil, username, password, testurl);
		// 登出
		LoginSteps.logout(seleniumUtil);
		// 登出信息断言
		LoginSteps.assertLogoutInfo(seleniumUtil, expect);
	}
}
