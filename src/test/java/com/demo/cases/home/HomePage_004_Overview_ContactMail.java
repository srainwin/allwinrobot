package com.demo.cases.home;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.demo.base.LoginBase;
import com.demo.pageoperation.HomeOperation;
import com.demo.pageoperation.LoginOperation;
import com.demo.utils.TestFailListener;
import com.demo.utils.TestRetryListener;

import io.qameta.allure.Description;

@Listeners({ TestFailListener.class ,TestRetryListener.class})
public class HomePage_004_Overview_ContactMail extends LoginBase {

	/**
	 * @Description 首页测试用例004：总览快捷跳转联系人邮件
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	@Test(dataProvider = "testdata" ,description = "总览快捷跳转联系人邮件")
	@Description("操作步骤：\r\n"
			+ "1、输入用户名和密码，点击登录；\r\n"
			+ "2、默认进入首页Tab后点击总览联系人邮件；\r\n"
			+ "预期结果：\r\n"
			+ "1、成功登录，且当前用户名信息正确；\r\n"
			+ "2、成功跳转到联系人邮件Tab页；\r\n")
	public void overviewContactMail(String username,String password,String except) throws Exception {
		//登陆
		LoginOperation.login(driver, username, password,baseurl);
		Thread.sleep(2000);
		//点击总览联系人邮件
		HomeOperation.overviewContactMailClick(driver);
		//进入联系人邮件断言
		String actual = HomeOperation.getOverviewContactMailTab(driver);
		Assert.assertEquals(actual, except);
	}
}
