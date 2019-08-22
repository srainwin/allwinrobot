package com.demo.pagesteps;

import org.apache.log4j.Logger;
import org.sikuli.script.Region;

import com.demo.pages.WriteLettersPage;
import com.demo.utils.SeleniumUtil;
import com.demo.utils.SikuliUtil;

import io.qameta.allure.Step;

/**
 * @author XWR
 * @Description 126邮箱写信页面图像操作封装
 */
public class WriteLettersSteps {
	static Logger logger = Logger.getLogger(WriteLettersSteps.class.getName());

	@Step("填写收件人") // allure的步骤显示
	public static void inputAddressee(SikuliUtil sikuliUtil, String addressee) {
		logger.info("开始填写收件人");
		String image = sikuliUtil.pickOneImage(WriteLettersPage.addressee,WriteLettersPage.addressee2);
		Region region = sikuliUtil.getImageRight(image, 2, 20);
		sikuliUtil.keyboardPasteTextRegion(region, addressee);
	}

	@Step("填写主题") // allure的步骤显示
	public static void inputTheme(SikuliUtil sikuliUtil, String theme) {
		logger.info("开始填写主题");
		String image = sikuliUtil.pickOneImage(WriteLettersPage.theme,WriteLettersPage.theme2);
		Region region = sikuliUtil.getImageRight(image, 2, 20);
		sikuliUtil.keyboardPasteTextRegion(region, theme);
	}

	@Step("关闭附件提示") // allure的步骤显示
	public static void closeAppendixTips(SikuliUtil sikuliUtil) {
		logger.info("开始关闭附件提示");
		String image = sikuliUtil.pickOneImage(WriteLettersPage.appendixTips,WriteLettersPage.appendixTips2);
		Region region = sikuliUtil.getInnerRegion(image, WriteLettersPage.appendixTipsClose);
		sikuliUtil.mouseClickRegion(region);
	}

	@Step("添加附件并上传") // allure的步骤显示
	public static void addAttachments(SeleniumUtil seleniumUtil, String exeName, String autoitFolderPath) {
		logger.info("开始添加附件并上传");
		seleniumUtil.uploadFile(WriteLettersPage.addAttachments, exeName, autoitFolderPath);
		seleniumUtil.findElementByWait(15, WriteLettersPage.attachProgressOK);
	}

	@Step("填写写信内容") // allure的步骤显示
	public static void inputWritingContent(SikuliUtil sikuliUtil, String WritingContent) {
		logger.info("开始填写写信内容");
		String image = sikuliUtil.pickOneImage(WriteLettersPage.writingContent,WriteLettersPage.writingContent2);
		Region region = sikuliUtil.getImageBelow(image, 2, 30);
		sikuliUtil.mouseClickRegion(region);
		sikuliUtil.keyboardPasteTextRegion(region, WritingContent);
	}

	@Step("点击发送信件") // allure的步骤显示
	public static void sendMailClick(SikuliUtil sikuliUtil) {
		logger.info("开始点击发送信件");
		String image = sikuliUtil.pickOneImage(WriteLettersPage.upSendButton,WriteLettersPage.upSendButton2);
		sikuliUtil.mouseClickImage(image, -50, 0, 2);
	}

	@Step("断言发送成功") // allure的步骤显示
	public static void assertSendMailSucc(SikuliUtil sikuliUtil) {
		logger.info("检查是否发送成功");
		String image = sikuliUtil.pickOneImage(WriteLettersPage.sendSucc,WriteLettersPage.sendSucc2);
		sikuliUtil.waitImage(image,20);
	}
}
