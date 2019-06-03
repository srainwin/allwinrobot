package com.demo.cases.home;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.demo.base.LoginBase;
import com.demo.pageoperation.HomeOperation;
import com.demo.pageoperation.LoginOperation;

import io.qameta.allure.Description;

public class HomePage_002_Overview_UnreadMail extends LoginBase {

	/**
	 * @Description 首页测试用例002：总览快捷跳转未读邮件
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	@Test(dataProvider = "testdata" ,description = "总览快捷跳转未读邮件")
	@Description("操作步骤：\n"
			+ "1、输入用户名和密码，点击登录；\n"
			+ "2、默认进入首页Tab后点击总览未读邮件；\n"
			+ "预期结果：\n"
			+ "1、成功登录，且当前用户名信息正确；\n"
			+ "2、成功跳转到未读邮件Tab页；\n")
	public void overviewUnreadMail(String username,String password,String expect) throws Exception {
		//登陆
		LoginOperation.login(driver, username, password,baseurl);
		Thread.sleep(2000);
		//点击总览未读邮件
		HomeOperation.overviewUnreadMailClick(driver);
		//进入未读邮件断言
		String actual = HomeOperation.getOverviewUnreadMailTab(driver);
		Assert.assertEquals(actual, expect);
	}
}
