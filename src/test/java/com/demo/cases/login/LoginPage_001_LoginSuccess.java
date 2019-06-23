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
@Epic("126邮箱自动化测试实战") // allure用例组织，一级模块（史诗）
@Feature("邮箱登录") // allure组织，二级模块（场景）
public class LoginPage_001_LoginSuccess extends LoginBase {
	
	@Story("登陆正向测试用例") // allure用例组织，三级模块（故事）
	@Test(dataProvider = "testdata", description = "输入正确的账号和密码登录") // allure用例名是description
	@Description("输入正确的账号和密码并登录成功") // allure用例描述
	@Severity(SeverityLevel.BLOCKER) // allure用例重要等级
	public void loginSucess(String username, String password, String expect) throws Exception {
		// 账号登陆
		LoginOperation.login(seleniumUtil, username, password, testurl);
		// 获取cookies保存，便于其他用例免登录
		seleniumUtil.cookiesSaveInFile(cookiesConfigFilePath);
		// 用户信息断言
		LoginOperation.assertLoginCurrentUser(seleniumUtil, expect);
	}
}
