package com.demo.cases.home;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.demo.base.LoginBase;
import com.demo.pagesteps.HomeOperation;
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
@Feature("用户首页") // allure组织，二级模块
public class HomePage_008_Overview_DailyLife extends LoginBase {

	@Story("首页Tab") // allure用例组织，三级模块
	@Test(dataProvider = "testdata", description = "总览快捷跳转每日生活") // allure用例名是description
	@Description("登录邮箱后，在首页Tab右方用户总览点击每日生活") // allure用例描述
	@Severity(SeverityLevel.NORMAL) // allure用例重要等级
	public void overviewDailyLife(String expect) throws Exception {
		// 打开已登录页面
		LoginOperation.loginFree(seleniumUtil, testurl, cookiesConfigFilePath);
		// 点击总览安全度
		HomeOperation.overviewDailyLifeClick(seleniumUtil);
		// 进入安全度断言
		HomeOperation.assertOverviewDailyLifeTab(seleniumUtil, expect);
	}
}
