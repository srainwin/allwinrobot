package com.demo.pagesteps;

import org.apache.log4j.Logger;

import com.demo.pages.FramePage;
import com.demo.pages.HomePage;
import com.demo.utils.SeleniumUtil;

import io.qameta.allure.Step;

/**
 * @author XWR
 * @Description 首页页面元素操作封装
 */
public class HomeSteps {
	static Logger logger = Logger.getLogger(HomeSteps.class.getName());

	@Step("点击切换首页tab") // allure的步骤显示
	public static void homepageTabClick(SeleniumUtil seleniumUtil) {
		logger.info("点击切换首页tab");
		seleniumUtil.click(HomePage.homepageTab);
	}

	@Step("获取首页Tab的问候名") // allure的步骤显示
	public static String getHomepageSign(SeleniumUtil seleniumUtil) {
		String text = "";
		logger.info("获取首页Tab-标志：问候名");
		text = seleniumUtil.getText(HomePage.homepageSign);
		return text;
	}

	@Step("断言获取首页Tab的问候名是否为“{1}”") // allure的步骤显示
	public static void assertHomepageSign(SeleniumUtil seleniumUtil, String expect) {
		String actual = HomeSteps.getHomepageSign(seleniumUtil);
		seleniumUtil.assertEquals(actual, expect);
		logger.info("成功断言首页Tab-标志：问候名");
	}

	@Step("点击首页总览快捷功能的未读邮件") // allure的步骤显示
	public static void overviewUnreadMailClick(SeleniumUtil seleniumUtil) {
		logger.info("点击首页总览快捷功能的未读邮件");
		seleniumUtil.click(HomePage.overviewUnreadMail);
	}

	@Step("获取总览未读邮件Tab的标题") // allure的步骤显示
	public static String getOverviewUnreadMailTab(SeleniumUtil seleniumUtil) {
		String text = "";
		logger.info("获取总览-未读邮件Tab-标题:未读邮件");
		text = seleniumUtil.getAttributeText(HomePage.overviewUnreadMailTab, "title");
		return text;
	}

	@Step("断言总览未读邮件Tab的标题是否为未读邮件") // allure的步骤显示
	public static void assertOverviewUnreadMailTab(SeleniumUtil seleniumUtil, String expect) {
		String actual = HomeSteps.getOverviewUnreadMailTab(seleniumUtil);
		seleniumUtil.assertEquals(actual, expect);
		logger.info("成功断言总览-未读邮件Tab-标题:未读邮件");
	}

	@Step("点击首页总览快捷功能的待办邮件") // allure的步骤显示
	public static void overviewTodoMailClick(SeleniumUtil seleniumUtil) {
		logger.info("点击首页总览快捷功能的待办邮件");
		seleniumUtil.click(HomePage.overviewTodoMail);
	}

	@Step("获取总览待办邮件Tab的标题") // allure的步骤显示
	public static String getOverviewTodoMailTab(SeleniumUtil seleniumUtil) {
		String text = "";
		logger.info("获取总览-待办邮件Tab-标题:待办邮件");
		text = seleniumUtil.getAttributeText(HomePage.overviewTodoMailTab, "title");
		return text;
	}

	@Step("断言总览待办邮件Tab的标题是否为待办邮件") // allure的步骤显示
	public static void assertOverviewTodoMailTab(SeleniumUtil seleniumUtil, String expect) {
		String actual = HomeSteps.getOverviewTodoMailTab(seleniumUtil);
		seleniumUtil.assertEquals(actual, expect);
		logger.info("成功断言总览-待办邮件Tab-标题:待办邮件");
	}

	@Step("点击首页总览快捷功能的联系人邮件") // allure的步骤显示
	public static void overviewContactMailClick(SeleniumUtil seleniumUtil) {
		logger.info("点击首页总览快捷功能的联系人邮件");
		seleniumUtil.click(HomePage.overviewContactMail);
	}

	@Step("获取总览联系人Tab的标题") // allure的步骤显示
	public static String getOverviewContactMailTab(SeleniumUtil seleniumUtil) {
		String text = "";
		logger.info("获取总览-联系人Tab-标题:联系人邮件");
		text = seleniumUtil.getAttributeText(HomePage.overviewContactMailTab, "title");
		return text;
	}

	@Step("断言总览联系人Tab的标题是否为联系人邮件") // allure的步骤显示
	public static void assertOverviewContactMailTab(SeleniumUtil seleniumUtil, String expect) {
		String actual = HomeSteps.getOverviewContactMailTab(seleniumUtil);
		seleniumUtil.assertEquals(actual, expect);
		logger.info("成功断言总览-联系人Tab-标题:联系人邮件");
	}

	@Step("点击首页总览快捷功能的积分") // allure的步骤显示
	public static String overviewJifen(SeleniumUtil seleniumUtil) {
		String currentHandle = "";
		currentHandle = seleniumUtil.getCurrentHandle();
		logger.info("点击首页总览快捷功能的积分");
		seleniumUtil.click(HomePage.overviewJifen);
		return currentHandle;
	}

	@Step("获取积分窗口标题") // allure的步骤显示
	public static String getOverviewJifenWindowTitle(SeleniumUtil seleniumUtil, String currentHandle) {
		String title = "";
		seleniumUtil.switchToAnotherHandle();
		title = seleniumUtil.getHandleTitle();
		logger.info("获取积分窗口标题");
		return title;
	}

	@Step("断言积分窗口标题") // allure的步骤显示
	public static void assertOverviewJifenWindowTitle(SeleniumUtil seleniumUtil, String expect, String currentHandle) {
		String actual = HomeSteps.getOverviewJifenWindowTitle(seleniumUtil, currentHandle);
		seleniumUtil.assertEquals(actual, expect);
		logger.info("成功断言积分窗口标题");
	}

	@Step("点击首页总览快捷功能的安全度") // allure的步骤显示
	public static void overviewSafetyDegreeClick(SeleniumUtil seleniumUtil) {
		logger.info("点击首页总览快捷功能的安全度");
		seleniumUtil.click(HomePage.overviewSafetyDegree);
	}

	@Step("获取总览安全度的标志") // allure的步骤显示
	public static String getOverviewSafetyDegreeSign(SeleniumUtil seleniumUtil) {
		String text = "";
		logger.info("获取总览-安全度-标志:邮箱健康指数");
		text = seleniumUtil.getText(HomePage.overviewSafetyDegreeSign);
		return text;
	}

	@Step("断言总览安全度的标志是否为邮箱健康指数") // allure的步骤显示
	public static void assertOverviewSafetyDegreeSign(SeleniumUtil seleniumUtil, String expect) {
		String actual = HomeSteps.getOverviewSafetyDegreeSign(seleniumUtil);
		seleniumUtil.assertEquals(actual, expect);
		logger.info("成功断言总览-安全度-标志:邮箱健康指数");
	}

	@Step("点击首页总览快捷功能的登录保护") // allure的步骤显示
	public static void overviewLoginProtectClick(SeleniumUtil seleniumUtil) {
		logger.info("点击首页总览快捷功能的登录保护");
		seleniumUtil.click(HomePage.overviewLoginProtect);
	}

	@Step("获取总览登录保护的标志") // allure的步骤显示
	public static String getOverviewLoginProtectSign(SeleniumUtil seleniumUtil) {
		String text = "";
		seleniumUtil.inFrame(FramePage.loginProtectFrame);
		logger.info("获取总览-登录保护-标志:邮箱登录二次验证");
		text = seleniumUtil.getText(HomePage.overviewLoginProtectSign);
		return text;
	}

	@Step("断言总览登录保护的标志是否为邮箱登录二次验证") // allure的步骤显示
	public static void assertOverviewLoginProtectSign(SeleniumUtil seleniumUtil, String expect) {
		String actual = HomeSteps.getOverviewLoginProtectSign(seleniumUtil);
		seleniumUtil.assertEquals(actual, expect);
		logger.info("成功断言总览-登录保护-标志:邮箱登录二次验证");
	}

	@Step("点击首页总览快捷功能的每日生活") // allure的步骤显示
	public static void overviewDailyLifeClick(SeleniumUtil seleniumUtil) {
		logger.info("点击首页总览快捷功能的每日生活");
		seleniumUtil.click(HomePage.overviewDailyLife);
	}

	@Step("获取总览每日生活Tab的标题") // allure的步骤显示
	public static String getOverviewDailyLifeTab(SeleniumUtil seleniumUtil) {
		String text = "";
		logger.info("获取总览-每日生活Tab-标题:严选每日推荐");
		text = seleniumUtil.getAttributeText(HomePage.overviewDailyLifeTab, "title");
		return text;
	}

	@Step("断言总览每日生活Tab的标题是否为严选每日推荐") // allure的步骤显示
	public static void assertOverviewDailyLifeTab(SeleniumUtil seleniumUtil, String expect) {
		String actual = HomeSteps.getOverviewDailyLifeTab(seleniumUtil);
		seleniumUtil.assertEquals(actual, expect);
		logger.info("成功断言总览-每日生活Tab-标题:严选每日推荐");
	}

	@Step("点击写信") // allure的步骤显示
	public static void writeLetterClick(SeleniumUtil seleniumUtil) {
		seleniumUtil.click(HomePage.writeLetters);
		logger.info("成功点击写信");
	}
}
