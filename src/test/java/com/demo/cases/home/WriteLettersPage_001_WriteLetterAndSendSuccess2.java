package com.demo.cases.home;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.demo.base.LoginBase;
import com.demo.pagesteps.HomeSteps;
import com.demo.pagesteps.LoginSteps;
import com.demo.pagesteps.WriteLettersSteps;
import com.demo.utils.TestNGListener;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
@Listeners({TestNGListener.class})
@Epic("126邮箱自动化测试实战") // allure用例组织，一级模块
@Feature("用户首页") // allure组织，二级模块
public class WriteLettersPage_001_WriteLetterAndSendSuccess2 extends LoginBase {

	@Story("首页Tab") // allure用例组织，三级模块
	@Test(groups = {"home"}, dataProvider = "testdata", description = "写信发送") // allure用例名是description
	@Description("登录邮箱后，在首页Tab下方点击写信，填写相关内容后发送邮件") // allure用例描述
	@Severity(SeverityLevel.NORMAL) // allure用例重要等级
	public void writeLetterAndSendSuccess2(String addressee, String theme,String exeName,String WritingContent) {
		// 打开已登录页面
		LoginSteps.loginFree(seleniumUtil, testurl, cookiesConfigFilePath);
		// 点击写信
		HomeSteps.writeLetterClick(seleniumUtil);
		// 填写收件人
		WriteLettersSteps.inputAddressee(sikuliUtil, addressee);
		// 填写主题
		WriteLettersSteps.inputTheme(sikuliUtil, theme);
		// 关闭附件提示
		WriteLettersSteps.closeAppendixTips(sikuliUtil);
		// 添加附件并上传
//		WriteLettersSteps.addAttachments(seleniumUtil, exeName, autoitFolderPath);
		// 填写写信内容
		WriteLettersSteps.inputWritingContent(sikuliUtil, WritingContent);
		// 点击发送信件
		WriteLettersSteps.sendMailClick(sikuliUtil);
		// 断言发送成功
		WriteLettersSteps.assertSendMailSucc(sikuliUtil);
	}
}
