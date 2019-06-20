package com.demo.pagesoperation;

import org.apache.log4j.Logger;

import com.demo.pages.FramePage;
import com.demo.pages.HomePage;
import com.demo.utils.SeleniumUtil;

import io.qameta.allure.Step;

/**
 * @author XWR
 * @Description 首页页面元素操作封装
 */
public class HomeOperation {
	static Logger logger = Logger.getLogger(HomeOperation.class.getName());
	
	/**
	 * @Description 切换首页tab
	 */
	@Step("点击切换首页tab")
	public static void homepageTabClick(SeleniumUtil seleniumUtil){
		try{
			logger.info("点击切换首页tab");
			seleniumUtil.click(HomePage.homepageTab);
		}catch(Exception e){
			logger.error("切换首页tab异常",e);
		}
	}
	
	/**
	 * @Description 获取首页Tab-标志：问候名
	 * @return
	 */
	public static String getHomepageSign (SeleniumUtil seleniumUtil){
		String text = "";
		try{
			logger.info("获取首页Tab-标志：问候名");
			text = seleniumUtil.getText(HomePage.homepageSign);
		}catch(Exception e){
			logger.error("获取首页Tab-标志：问候名异常",e);
		}
		return text;
	}
	
	/**
	 * @Description 断言首页Tab-标志：问候名
	 * @param seleniumUtil
	 * @param expect
	 */
	@Step("切换首页成功后判断首页Tab标志（问候名）是否正确")
	public static void assertHomepageSign(SeleniumUtil seleniumUtil, String expect ){
		try{
			String actual = HomeOperation.getHomepageSign(seleniumUtil);
			seleniumUtil.assertEquals(actual, expect);
			logger.info("成功断言首页Tab-标志：问候名");
		}catch(Exception e){
			logger.error("断言首页Tab-标志：问候名发生异常",e);
		}
	}
	
	/**
	 * @Description 跳转首页总览快捷功能的未读邮件
	 */
	public static void overviewUnreadMailClick(SeleniumUtil seleniumUtil){
		try{
			logger.info("点击首页总览快捷功能的未读邮件");
			seleniumUtil.click(HomePage.overviewUnreadMail);
		}catch(Exception e){
			logger.error("跳转首页总览快捷功能的未读邮件异常",e);
		}
	}
	
	/**
	 * @Description 获取总览-未读邮件Tab-标题:未读邮件
	 * @return
	 */
	public static String getOverviewUnreadMailTab (SeleniumUtil seleniumUtil){
		String text = "";
		try{
			logger.info("获取总览-未读邮件Tab-标题:未读邮件");
			text = seleniumUtil.getAttributeText(HomePage.overviewUnreadMailTab, "title");
		}catch(Exception e){
			logger.error("获取总览-未读邮件Tab-标题:未读邮件异常",e);
		}
		return text;
	}
	
	/**
	 * @Description 断言总览-未读邮件Tab-标题:未读邮件
	 * @param seleniumUtil
	 * @param expect
	 */
	public static void assertOverviewUnreadMailTab(SeleniumUtil seleniumUtil, String expect ){
		try{
			String actual = HomeOperation.getOverviewUnreadMailTab(seleniumUtil);
			seleniumUtil.assertEquals(actual, expect);
			logger.info("成功断言总览-未读邮件Tab-标题:未读邮件");
		}catch(Exception e){
			logger.error("断言总览-未读邮件Tab-标题:未读邮件发生异常",e);
		}
	}
	
	/**
	 * @Description 跳转首页总览快捷功能的待办邮件
	 */
	public static void overviewTodoMailClick(SeleniumUtil seleniumUtil){
		try{
			logger.info("点击首页总览快捷功能的待办邮件");
			seleniumUtil.click(HomePage.overviewTodoMail);
		}catch(Exception e){
			logger.error("跳转首页总览快捷功能的待办邮件异常",e);
		}
	}
	
	/**
	 * @Description 获取总览-待办邮件Tab-标题:待办邮件
	 * @return
	 */
	public static String getOverviewTodoMailTab (SeleniumUtil seleniumUtil){
		String text = "";
		try{
			logger.info("获取总览-待办邮件Tab-标题:待办邮件");
			text = seleniumUtil.getAttributeText(HomePage.overviewTodoMailTab, "title");
		}catch(Exception e){
			logger.error("获取总览-待办邮件Tab-标题:待办邮件异常",e);
		}
		return text;
	}
	
	/**
	 * @Description 断言总览-待办邮件Tab-标题:待办邮件
	 * @param seleniumUtil
	 * @param expect
	 */
	public static void assertOverviewTodoMailTab(SeleniumUtil seleniumUtil, String expect ){
		try{
			String actual = HomeOperation.getOverviewTodoMailTab(seleniumUtil);
			seleniumUtil.assertEquals(actual, expect);
			logger.info("成功断言总览-待办邮件Tab-标题:待办邮件");
		}catch(Exception e){
			logger.error("断言总览-待办邮件Tab-标题:待办邮件发生异常",e);
		}
	}
	
	/**
	 * @Description 跳转首页总览快捷功能的联系人邮件
	 */
	public static void overviewContactMailClick(SeleniumUtil seleniumUtil){
		try{
			logger.info("点击首页总览快捷功能的联系人邮件");
			seleniumUtil.click(HomePage.overviewContactMail);
		}catch(Exception e){
			logger.error("跳转首页总览快捷功能的联系人邮件异常",e);
		}
	}
	
	/**
	 * @Description 获取总览-联系人Tab-标题:联系人邮件
	 * @return
	 */
	public static String getOverviewContactMailTab (SeleniumUtil seleniumUtil){
		String text = "";
		try{
			logger.info("获取总览-联系人Tab-标题:联系人邮件");
			text = seleniumUtil.getAttributeText(HomePage.overviewContactMailTab, "title");
		}catch(Exception e){
			logger.error("获取总览-联系人Tab-标题:联系人邮件异常",e);
		}
		return text;
	}
	
	/**
	 * @Description 断言总览-联系人Tab-标题:联系人邮件
	 * @param seleniumUtil
	 * @param expect
	 */
	public static void assertOverviewContactMailTab(SeleniumUtil seleniumUtil, String expect ){
		try{
			String actual = HomeOperation.getOverviewContactMailTab(seleniumUtil);
			seleniumUtil.assertEquals(actual, expect);
			logger.info("成功断言总览-联系人Tab-标题:联系人邮件");
		}catch(Exception e){
			logger.error("断言总览-联系人Tab-标题:联系人邮件发生异常",e);
		}
	}
	
	/**
	 * @Description 跳转首页总览快捷功能的积分
	 * @return
	 */
	public static String overviewJifen(SeleniumUtil seleniumUtil){
		String currentHandle = "";
		try{
			currentHandle = seleniumUtil.getCurrentHandle();
			logger.info("点击首页总览快捷功能的积分");
			seleniumUtil.click(HomePage.overviewJifen);
		}catch(Exception e){
			logger.error("跳转首页总览快捷功能的积分异常",e);
		}
		return currentHandle;
	}
	
	/**
	 * @Description 获取积分窗口标题
	 * @param currentHandle
	 * @return
	 */
	public static String getOverviewJifenWindowTitle(SeleniumUtil seleniumUtil,String currentHandle){
		String title = "";
		try{
			seleniumUtil.switchToAnotherHandle();
			title = seleniumUtil.getHandleTitle();
		}catch(Exception e){
			logger.error("获取积分窗口标题异常",e);
		}
		return title;
	}
	
	/**
	 * @Description 断言积分窗口标题
	 * @param seleniumUtil
	 * @param expect
	 * @param currentHandle
	 */
	public static void assertOverviewJifenWindowTitle(SeleniumUtil seleniumUtil, String expect ,String currentHandle){
		try{
			String actual = HomeOperation.getOverviewJifenWindowTitle(seleniumUtil, currentHandle);
			seleniumUtil.assertEquals(actual, expect);
			logger.info("成功断言积分窗口标题");
		}catch(Exception e){
			logger.error("断言积分窗口标题发生异常",e);
		}
	}
	
	/**
	 * @Description 跳转首页总览快捷功能的安全度
	 */
	public static void overviewSafetyDegreeClick(SeleniumUtil seleniumUtil){
		try{
			logger.info("点击首页总览快捷功能的安全度");
			seleniumUtil.click(HomePage.overviewSafetyDegree);
		}catch(Exception e){
			logger.error("跳转首页总览快捷功能的安全度异常",e);
		}
	}
	
	/**
	 * @Description 获取总览-安全度-标志:邮箱健康指数
	 * @return
	 */
	public static String getOverviewSafetyDegreeSign (SeleniumUtil seleniumUtil){
		String text = "";
		try{
			logger.info("获取总览-安全度-标志:邮箱健康指数");
			text = seleniumUtil.getText(HomePage.overviewSafetyDegreeSign);
		}catch(Exception e){
			logger.error("获取总览-安全度-标志:邮箱健康指数异常",e);
		}
		return text;
	}
	
	/**
	 * @Description 断言总览-安全度-标志:邮箱健康指数
	 * @param seleniumUtil
	 * @param expect
	 */
	public static void assertOverviewSafetyDegreeSign(SeleniumUtil seleniumUtil, String expect){
		try{
			String actual = HomeOperation.getOverviewSafetyDegreeSign(seleniumUtil);
			seleniumUtil.assertEquals(actual, expect);
			logger.info("成功断言总览-安全度-标志:邮箱健康指数");
		}catch(Exception e){
			logger.error("断言总览-安全度-标志:邮箱健康指数发生异常",e);
		}
	}
	
	/**
	 * @Description 跳转首页总览快捷功能的登录保护
	 */
	public static void overviewLoginProtectClick(SeleniumUtil seleniumUtil){
		try{
			logger.info("点击首页总览快捷功能的登录保护");
			seleniumUtil.click(HomePage.overviewLoginProtect);
		}catch(Exception e){
			logger.error("跳转首页总览快捷功能的登录保护异常",e);
		}
	}
	
	/**
	 * @Description 获取总览-登录保护-标志:邮箱登录二次验证
	 * @return
	 */
	public static String getOverviewLoginProtectSign (SeleniumUtil seleniumUtil){
		String text = "";
		try{
			seleniumUtil.inFrame(FramePage.loginProtectFrame);
			logger.info("获取总览-登录保护-标志:邮箱登录二次验证");
			text = seleniumUtil.getText(HomePage.overviewLoginProtectSign);
		}catch(Exception e){
			logger.error("获取总览-登录保护-标志:邮箱登录二次验证异常",e);
		}
		return text;
	}
	
	/**
	 * @Description 断言总览-登录保护-标志:邮箱登录二次验证
	 * @param seleniumUtil
	 * @param expect
	 */
	public static void assertOverviewLoginProtectSign(SeleniumUtil seleniumUtil, String expect){
		try{
			String actual = HomeOperation.getOverviewLoginProtectSign(seleniumUtil);
			seleniumUtil.assertEquals(actual, expect);
			logger.info("成功断言总览-登录保护-标志:邮箱登录二次验证");
		}catch(Exception e){
			logger.error("断言总览-登录保护-标志:邮箱登录二次验证发生异常",e);
		}
	}
	
	/**
	 * @Description 跳转首页总览快捷功能的每日生活
	 */
	public static void overviewDailyLifeClick(SeleniumUtil seleniumUtil){
		try{
			logger.info("点击首页总览快捷功能的每日生活");
			seleniumUtil.click(HomePage.overviewDailyLife);
		}catch(Exception e){
			logger.error("跳转首页总览快捷功能的每日生活异常",e);
		}
	}
	
	/**
	 * @Description 获取总览-每日生活Tab-标题:严选每日推荐
	 * @return
	 */
	public static String getOverviewDailyLifeTab (SeleniumUtil seleniumUtil){
		String text = "";
		try{
			logger.info("获取总览-每日生活Tab-标题:严选每日推荐");
			text = seleniumUtil.getAttributeText(HomePage.overviewDailyLifeTab, "title");
		}catch(Exception e){
			logger.error("获取总览-每日生活Tab-标题:严选每日推荐异常",e);
		}
		return text;
	}
	
	/**
	 * @Description 断言总览-每日生活Tab-标题:严选每日推荐
	 * @param seleniumUtil
	 * @param expect
	 */
	public static void assertOverviewDailyLifeTab(SeleniumUtil seleniumUtil, String expect){
		try{
			String actual = HomeOperation.getOverviewDailyLifeTab(seleniumUtil);
			seleniumUtil.assertEquals(actual, expect);
			logger.info("成功断言总览-每日生活Tab-标题:严选每日推荐");
		}catch(Exception e){
			logger.error("断言总览-每日生活Tab-标题:严选每日推荐发生异常",e);
		}
	}
}
