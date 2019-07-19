package com.demo.cases.home;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.demo.base.LoginBase;
import com.demo.pagesteps.HomeSteps;
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
@Feature("用户首页") // allure组织，二级模块
public class HomePage_002_Overview_UnreadMail extends LoginBase {

	@Story("首页Tab") // allure用例组织，三级模块
	@Test(groups = {"home"}, dataProvider = "testdata", description = "总览快捷跳转未读邮件") // allure用例名是description
	@Description("登录邮箱后，在首页Tab右方用户总览点击未读邮件") // allure用例描述
	@Severity(SeverityLevel.NORMAL) // allure用例重要等级
	public void overviewUnreadMail(String expect) {
		// 打开已登录页面
		LoginSteps.loginFree(seleniumUtil, testurl, cookiesConfigFilePath);
		// 点击总览未读邮件
		HomeSteps.overviewUnreadMailClick(seleniumUtil);
		// 进入未读邮件断言
		HomeSteps.assertOverviewUnreadMailTab(seleniumUtil, expect);
	}
}
