package com.demo.pagesteps;

import org.apache.log4j.Logger;
import org.sikuli.script.Region;

import com.demo.utils.JsonPageParser;
import com.demo.utils.SeleniumUtil;
import com.demo.utils.SikuliUtil;

import io.qameta.allure.Step;

/**
 * @author XWR
 * @Description 126邮箱写信页面图像操作封装
 */
public class WriteLettersSteps {
	static Logger logger = Logger.getLogger(WriteLettersSteps.class.getName());
	static JsonPageParser writeLettersPage = new JsonPageParser("WriteLettersPage.json");

	@Step("填写收件人") // allure的步骤显示
	public static void inputAddressee(SikuliUtil sikuliUtil, String addressee) {
		logger.info("开始填写收件人");
		String image = sikuliUtil.pickOneImage(writeLettersPage.getImageLocator("addressee1"),writeLettersPage.getImageLocator("addressee2"));
		Region region = sikuliUtil.getImageRight(image, 2, 20);
		sikuliUtil.keyboardPasteTextRegion(region, addressee);
	}

	@Step("填写主题") // allure的步骤显示
	public static void inputTheme(SikuliUtil sikuliUtil, String theme) {
		logger.info("开始填写主题");
		String image = sikuliUtil.pickOneImage(writeLettersPage.getImageLocator("theme1"),writeLettersPage.getImageLocator("theme2"));
		Region region = sikuliUtil.getImageRight(image, 2, 20);
		sikuliUtil.keyboardPasteTextRegion(region, theme);
	}

	@Step("关闭附件提示") // allure的步骤显示
	public static void closeAppendixTips(SikuliUtil sikuliUtil) {
		logger.info("开始关闭附件提示");
		String image = sikuliUtil.pickOneImage(writeLettersPage.getImageLocator("appendixTips1"),writeLettersPage.getImageLocator("appendixTips2"));
		Region region = null;
		if("appendixTips1".equals(image)){
			region = sikuliUtil.getInnerRegion(image, writeLettersPage.getImageLocator("appendixTipsClose1"));
		}else{
			region = sikuliUtil.getInnerRegion(image, writeLettersPage.getImageLocator("appendixTipsClose2"));
		}
		sikuliUtil.mouseClickRegion(region);
	}

	@Step("添加附件并上传") // allure的步骤显示
	public static void addAttachments(SeleniumUtil seleniumUtil, String exeName, String autoitFolderPath) {
		logger.info("开始添加附件并上传");
		seleniumUtil.uploadFile(writeLettersPage.getElementLocator("addAttachments"), exeName, autoitFolderPath);
		seleniumUtil.findElementByWait(15, writeLettersPage.getElementLocator("attachProgressOK"));
	}

	@Step("填写写信内容") // allure的步骤显示
	public static void inputWritingContent(SikuliUtil sikuliUtil, String WritingContent) {
		logger.info("开始填写写信内容");
		String image = sikuliUtil.pickOneImage(writeLettersPage.getImageLocator("writingContent1"),writeLettersPage.getImageLocator("writingContent2"));
		Region region = sikuliUtil.getImageBelow(image, 2, 30);
		sikuliUtil.mouseClickRegion(region);
		sikuliUtil.keyboardPasteTextRegion(region, WritingContent);
	}

	@Step("点击发送信件") // allure的步骤显示
	public static void sendMailClick(SikuliUtil sikuliUtil) {
		logger.info("开始点击发送信件");
		String image = sikuliUtil.pickOneImage(writeLettersPage.getImageLocator("upSendButton1"),writeLettersPage.getImageLocator("upSendButton2"));
		sikuliUtil.mouseClickImage(image, -55, 0, 2);
	}

	@Step("断言发送成功") // allure的步骤显示
	public static void assertSendMailSucc(SikuliUtil sikuliUtil) {
		logger.info("检查是否发送成功");
		String image = sikuliUtil.pickOneImage(writeLettersPage.getImageLocator("sendSucc1"),writeLettersPage.getImageLocator("sendSucc2"));
		sikuliUtil.waitImage(image,20);
	}
}
