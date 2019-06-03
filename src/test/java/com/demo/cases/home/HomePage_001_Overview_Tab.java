package com.demo.cases.home;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.demo.base.LoginBase;
import com.demo.pageoperation.HomeOperation;
import com.demo.pageoperation.LoginOperation;

import io.qameta.allure.Description;

public class HomePage_001_Overview_Tab extends LoginBase {

	/**
	 * @Description 首页测试用例001：切换为首页Tab
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	@Test(dataProvider = "testdata" ,description = "切换为首页Tab")
	@Description("操作步骤：\r\n"
			+ "1、输入用户名和密码，点击登录；\r\n"
			+ "2、点击首页Tab；\r\n"
			+ "预期结果：\r\n"
			+ "1、成功登录，且当前用户名信息正确；\r\n"
			+ "2、当前切换的Tab为首页；\r\n")
	public void overviewTabSwitch(String username,String password) throws Exception {
		//登陆
		LoginOperation.login(driver, username, password,baseurl);
		Thread.sleep(2000);
		//点击首页tab
		HomeOperation.homepageTabClick(driver);
		//进入首页tab断言
		String actual = HomeOperation.gethomepageSign(driver);
		Assert.assertEquals(actual, username);
	}
}
