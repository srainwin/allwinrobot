package com.demo.cases.home;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.demo.base.LoginBase;
import com.demo.pagesoperation.HomeOperation;
import com.demo.pagesoperation.LoginOperation;
import com.demo.utils.TestNGListener;

import io.qameta.allure.Description;
@Listeners({ TestNGListener.class})
public class HomePage_004_Overview_ContactMail extends LoginBase {

	/**
	 * @Description 首页测试用例004：总览快捷跳转联系人邮件
	 * @param except
	 * @param itestcontext
	 * @throws Exception
	 */
	@Test(dataProvider = "testdata", description = "总览快捷跳转联系人邮件")
	@Description("操作步骤：" + "1、打开已登录页面；" + "2、默认进入首页Tab后点击总览联系人邮件；" + "预期结果：" + "1、成功登录，且当前用户名信息正确；"
			+ "2、成功跳转到联系人邮件Tab页；")
	public void overviewContactMail(String expect) throws Exception {
		// 打开已登录页面
		LoginOperation.loginFree(seleniumUtil, testurl, cookiesConfigFilePath);
		// 点击总览联系人邮件
		HomeOperation.overviewContactMailClick(seleniumUtil);
		// 进入联系人邮件断言
		HomeOperation.assertOverviewContactMailTab(seleniumUtil, expect);
	}
}
