package com.demo.cases.home;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.demo.base.LoginBase;
import com.demo.pagesoperation.HomeOperation;
import com.demo.pagesoperation.LoginOperation;
import com.demo.utils.TestNGListener;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
@Listeners({ TestNGListener.class})
@Epic("126邮箱自动化测试实战")
@Feature("用户首页")
public class HomePage_001_Overview_Tab extends LoginBase {

	/**
	 * @Description 首页测试用例001：切换为首页Tab
	 * @param username
	 * @param itestcontext
	 * @throws Exception
	 */
	@Test(dataProvider = "testdata", description = "切换为首页Tab")
	@Severity(SeverityLevel.NORMAL)
	@Story("首页Tab")
	@Description("操作步骤：" + "1、打开已登录页面；" + "2、点击首页Tab；" + "预期结果：" + "1、成功登录，且当前用户名信息正确；" + "2、当前切换的Tab为首页；")
	public void overviewTabSwitch(String username, String expect) throws Exception {
		LoginOperation loginOperation = new LoginOperation();
		HomeOperation homeOperation = new HomeOperation();
		// 打开已登录页面
		loginOperation.loginFree(seleniumUtil, testurl, cookiesConfigFilePath);
		// 点击首页tab
		homeOperation.homepageTabClick(seleniumUtil);
		// 进入首页tab断言
		homeOperation.assertHomepageSign(seleniumUtil, "expect");//模仿断言失败看截图与重跑效果
	}
}