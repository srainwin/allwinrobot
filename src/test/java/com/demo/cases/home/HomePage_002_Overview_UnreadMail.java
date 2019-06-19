package com.demo.cases.home;

import org.testng.annotations.Test;

import com.demo.base.LoginBase;
import com.demo.pagesoperation.HomeOperation;
import com.demo.pagesoperation.LoginOperation;

import io.qameta.allure.Description;

public class HomePage_002_Overview_UnreadMail extends LoginBase {

	/**
	 * @Description 首页测试用例002：总览快捷跳转未读邮件
	 * @param expect
	 * @param itestcontext
	 * @throws Exception
	 */
	@Test(dataProvider = "testdata", description = "总览快捷跳转未读邮件")
	@Description("操作步骤：" + "1、打开已登录页面；" + "2、默认进入首页Tab后点击总览未读邮件；" + "预期结果：" + "1、成功登录，且当前用户名信息正确；" + "2、成功跳转到未读邮件Tab页；")
	public void overviewUnreadMail(String expect) throws Exception {
		// 打开已登录页面
		LoginOperation.loginFree(seleniumUtil, testurl, cookiesConfigFilePath);
		// 点击总览未读邮件
		HomeOperation.overviewUnreadMailClick(seleniumUtil);
		// 进入未读邮件断言
		HomeOperation.assertOverviewUnreadMailTab(seleniumUtil, expect);
	}
}
