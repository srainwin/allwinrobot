package com.demo.cases.home;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.demo.base.LoginBase;
import com.demo.pageoperation.HomeOperation;
import com.demo.pageoperation.LoginOperation;

import io.qameta.allure.Description;

public class HomePage_006_Overview_SafetyDegree extends LoginBase {

	/**
	 * @Description 首页测试用例006：总览快捷跳转安全度
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	@Test(dataProvider = "testdata" ,description = "总览快捷跳转安全度")
	@Description("操作步骤：\r\n"
			+ "1、输入用户名和密码，点击登录；\r\n"
			+ "2、默认进入首页Tab后点击总览安全度；\r\n"
			+ "预期结果：\r\n"
			+ "1、成功登录，且当前用户名信息正确；\r\n"
			+ "2、成功跳转到设置Tab页并显示邮件安全相关内容；\r\n")
	public void overviewSafetyDegree(String username,String password,String expect) throws Exception {
		//登陆
		LoginOperation.login(driver, username, password,baseurl);
		Thread.sleep(2000);
		//点击总览安全度
		HomeOperation.overviewSafetyDegreeClick(driver);
		//进入安全度断言
		String actual = HomeOperation.getOverviewSafetyDegreeSign(driver);
		Assert.assertEquals(actual, expect);
	}
}
