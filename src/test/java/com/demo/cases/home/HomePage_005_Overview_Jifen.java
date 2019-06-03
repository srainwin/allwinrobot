package com.demo.cases.home;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.demo.base.LoginBase;
import com.demo.pageoperation.HomeOperation;
import com.demo.pageoperation.LoginOperation;

import io.qameta.allure.Description;

public class HomePage_005_Overview_Jifen extends LoginBase {

	/**
	 * @Description 首页测试用例005：总览快捷跳转积分（已下架）
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	@Test(dataProvider = "testdata" ,description = "总览快捷跳转积分")
	@Description("操作步骤：\r\n"
			+ "1、输入用户名和密码，点击登录；\r\n"
			+ "2、默认进入首页Tab后点击总览积分；\r\n"
			+ "预期结果：\r\n"
			+ "1、成功登录，且当前用户名信息正确；\r\n"
			+ "2、成功打开新窗口进入网易邮箱用户俱乐部页面；\r\n")
	public void overviewJifen(String username,String password,String expect) throws Exception {
		//登陆
		LoginOperation.login(driver, username, password,baseurl);
		Thread.sleep(2000);
		//点击总览积分
		String currentHandle = HomeOperation.overviewJifen(driver);
		//进入积分断言
		String actual = HomeOperation.getOverviewJifenWindowTitle(driver, currentHandle);
		Assert.assertEquals(actual, expect);
	}
}
