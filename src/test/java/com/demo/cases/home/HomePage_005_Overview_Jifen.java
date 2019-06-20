package com.demo.cases.home;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.demo.base.LoginBase;
import com.demo.pagesoperation.HomeOperation;
import com.demo.pagesoperation.LoginOperation;
import com.demo.utils.TestNGListener;

import io.qameta.allure.Description;
@Listeners({ TestNGListener.class})
public class HomePage_005_Overview_Jifen extends LoginBase {

	/**
	 * @Description 首页测试用例005：总览快捷跳转积分（已下架）
	 * @param expect
	 * @param itestcontext
	 * @throws Exception
	 */
	@Test(dataProvider = "testdata", description = "总览快捷跳转积分")
	@Description("操作步骤：" + "1、打开已登录页面；" + "2、默认进入首页Tab后点击总览积分；" + "预期结果：" + "1、成功登录，且当前用户名信息正确；"
			+ "2、成功打开新窗口进入网易邮箱用户俱乐部页面；")
	public void overviewJifen(String expect) throws Exception {
		// 打开已登录页面
		LoginOperation.loginFree(seleniumUtil, testurl, cookiesConfigFilePath);
		// 点击总览积分
		String currentHandle = HomeOperation.overviewJifen(seleniumUtil);
		// 进入积分断言
		HomeOperation.assertOverviewJifenWindowTitle(seleniumUtil, expect, currentHandle);
	}
}
