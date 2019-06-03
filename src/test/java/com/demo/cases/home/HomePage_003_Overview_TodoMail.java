package com.demo.cases.home;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.demo.base.LoginBase;
import com.demo.pageoperation.HomeOperation;
import com.demo.pageoperation.LoginOperation;

import io.qameta.allure.Description;

public class HomePage_003_Overview_TodoMail extends LoginBase {

	/**
	 * @Description 首页测试用例003：总览快捷跳转待办邮件
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	@Test(dataProvider = "testdata" ,description = "总览快捷跳转待办邮件")
	@Description("操作步骤：\n"
			+ "1、输入用户名和密码，点击登录；\n"
			+ "2、默认进入首页Tab后点击总览待办邮件；\n"
			+ "预期结果：\n"
			+ "1、成功登录，且当前用户名信息正确；\n"
			+ "2、成功跳转到待办邮件Tab页；\n")
	public void overviewTodoMail(String username,String password,String except) throws Exception {
		//登陆
		LoginOperation.login(driver, username, password,baseurl);
		Thread.sleep(2000);
		//点击总览待办邮件
		HomeOperation.overviewTodoMailClick(driver);
		//进入待办邮件断言
		String actual = HomeOperation.getOverviewTodoMailTab(driver);
		Assert.assertEquals(actual, except);
	}
}
