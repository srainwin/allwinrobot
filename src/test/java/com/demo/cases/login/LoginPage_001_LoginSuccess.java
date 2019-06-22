package com.demo.cases.login;

import org.testng.annotations.Test;

import com.demo.base.LoginBase;
import com.demo.pagesoperation.LoginOperation;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import static io.qameta.allure.Allure.step;

@Epic("126邮箱自动化测试实战") // allure用例组织，一级模块
@Feature("邮箱登录") // allure组织，二级模块
public class LoginPage_001_LoginSuccess extends LoginBase {

	/**
	 * @Description 登录测试用例001：成功登录126邮箱
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	@Test(dataProvider = "testdata", description = "正确的账号密码登录") // allure用例名，description
	@Severity(SeverityLevel.BLOCKER)
	@Story("登陆正向测试用例") // allure用例组织，三级模块
	@Description("操作步骤：" + "1、输入用户名和密码，点击登录；" + "预期结果：" + "1、成功登录，且当前用户名信息正确；") // allure用例描述
	public void loginSucess(String username, String password, String expect) throws Exception {
		// 账号登陆
		step("选择账号方式，并且输入用户名{1}和密码{2}，点击登录");
		LoginOperation.login(seleniumUtil, username, password, testurl);
		// 获取cookies保存，便于其他用例免登录
		seleniumUtil.cookiesSaveInFile(cookiesConfigFilePath);
		// 用户信息断言
		step("成功登录后判断当前用户名信息是否为:{1}");
		LoginOperation.assertLoginCurrentUser(seleniumUtil, expect);
	}
}
