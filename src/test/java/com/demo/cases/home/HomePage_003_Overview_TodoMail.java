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
public class HomePage_003_Overview_TodoMail extends LoginBase {

	/**
	 * @Description 首页测试用例003：总览快捷跳转待办邮件
	 * @param expect
	 * @param itestcontext
	 * @throws Exception
	 */
	@Test(dataProvider = "testdata" ,description = "总览快捷跳转待办邮件")
	@Description("操作步骤："
			+ "1、打开已登录页面；"
			+ "2、默认进入首页Tab后点击总览待办邮件；"
			+ "预期结果："
			+ "1、成功登录，且当前用户名信息正确；"
			+ "2、成功跳转到待办邮件Tab页；")
	public void overviewTodoMail(String except,ITestContext itestcontext) throws Exception {
		//打开已登录页面
		LoginOperation.loginFree(seleniumUtil,testurl,itestcontext);
		Thread.sleep(2000);
		//点击总览待办邮件
		HomeOperation.overviewTodoMailClick(seleniumUtil);
		//进入待办邮件断言
		String actual = HomeOperation.getOverviewTodoMailTab(seleniumUtil);
		Assert.assertEquals(actual, except);
	}
}
