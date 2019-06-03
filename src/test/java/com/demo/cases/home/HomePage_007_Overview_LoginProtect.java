package com.demo.cases.home;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.demo.base.LoginBase;
import com.demo.pageoperation.HomeOperation;
import com.demo.pageoperation.LoginOperation;

import io.qameta.allure.Description;

public class HomePage_007_Overview_LoginProtect extends LoginBase {

	/**
	 * @Description 首页测试用例007：总览快捷跳转登录保护
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	@Test(dataProvider = "testdata" ,description = "总览快捷跳转登录保护")
	@Description("操作步骤：\n"
			+ "1、输入用户名和密码，点击登录；\n"
			+ "2、默认进入首页Tab后点击总览登录保护；\n"
			+ "预期结果：\n"
			+ "1、成功登录，且当前用户名信息正确；\n"
			+ "2、成功跳转到设置Tab页并显示邮件登录保护相关内容；\n")
	public void overviewLoginProtect(String username,String password,String expect) throws Exception {
		//登陆
		LoginOperation.login(driver, username, password,baseurl);
		Thread.sleep(2000);
		//点击总览安全度
		HomeOperation.overviewLoginProtectClick(driver);
		//进入安全度断言
		String actual = HomeOperation.getOverviewLoginProtectSign(driver);
		Assert.assertEquals(actual, expect);
	}
}
