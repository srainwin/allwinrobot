package com.demo.cases.home;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.demo.base.LoginBase;
import com.demo.pagesoperation.HomeOperation;
import com.demo.pagesoperation.LoginOperation;
import com.demo.utils.TestFailListener;
import com.demo.utils.TestRetryListener;

import io.qameta.allure.Description;

@Listeners({ TestFailListener.class ,TestRetryListener.class})
public class HomePage_001_Overview_Tab extends LoginBase {

	/**
	 * @Description 首页测试用例001：切换为首页Tab
	 * @param username
	 * @param itestcontext
	 * @throws Exception
	 */
	@Test(dataProvider = "testdata" ,description = "切换为首页Tab")
	@Description("操作步骤："
				+ "1、打开已登录页面；"
				+ "2、点击首页Tab；"
				+ "预期结果："
				+ "1、成功登录，且当前用户名信息正确；"
				+ "2、当前切换的Tab为首页；")
	public void overviewTabSwitch(String username,ITestContext itestcontext) throws Exception {
		//打开已登录页面
		LoginOperation.loginFree(seleniumUtil,testurl,itestcontext);
		Thread.sleep(2000);
		//点击首页tab
		HomeOperation.homepageTabClick(seleniumUtil);
		//进入首页tab断言
		String actual = HomeOperation.gethomepageSign(seleniumUtil);
		Assert.assertEquals(actual, username);
	}
}
