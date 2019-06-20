package com.demo.cases.home;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.demo.base.LoginBase;
import com.demo.pagesoperation.HomeOperation;
import com.demo.pagesoperation.LoginOperation;
import com.demo.utils.TestNGListener;

import io.qameta.allure.Description;
@Listeners({ TestNGListener.class})
public class HomePage_006_Overview_SafetyDegree extends LoginBase {

	/**
	 * @Description 首页测试用例006：总览快捷跳转安全度
	 * @param expect
	 * @param itestcontext
	 * @throws Exception
	 */
	@Test(dataProvider = "testdata", description = "总览快捷跳转安全度")
	@Description("操作步骤：" + "1、打开已登录页面；" + "2、默认进入首页Tab后点击总览安全度；" + "预期结果：" + "1、成功登录，且当前用户名信息正确；"
			+ "2、成功跳转到设置Tab页并显示邮件安全相关内容；")
	public void overviewSafetyDegree(String expect) throws Exception {
		// 打开已登录页面
		LoginOperation.loginFree(seleniumUtil, testurl, cookiesConfigFilePath);
		// 点击总览安全度
		HomeOperation.overviewSafetyDegreeClick(seleniumUtil);
		// 进入安全度断言
		HomeOperation.assertOverviewSafetyDegreeSign(seleniumUtil, expect);
	}
}
