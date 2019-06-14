package com.demo.pagesoperation;

import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.demo.pages.HomePage_BAK;

/**
 * @author XWR
 * @Description 首页页面元素操作封装
 */
public class HomeOperation_BAK {
	static Logger logger = Logger.getLogger(HomeOperation_BAK.class.getName());
	
	/**
	 * @Description 切换首页tab
	 * @param driver
	 */
	public static void homepageTabClick(WebDriver driver){
		try{
			logger.info("点击切换首页tab");
			HomePage_BAK.homepageTab(driver).click();
		}catch(Exception e){
			logger.error("切换首页tab异常",e);
		}
	}
	
	/**
	 * @Description 获取首页Tab-标志：问候名
	 * @param driver
	 * @return
	 */
	public static String gethomepageSign (WebDriver driver){
		String text = "";
		try{
			logger.info("获取首页Tab-标志：问候名");
			text = HomePage_BAK.homepageSign(driver).getText();
		}catch(Exception e){
			logger.error("获取首页Tab-标志：问候名异常",e);
		}
		return text;
	}
	
	/**
	 * @Description 跳转首页总览快捷功能的未读邮件
	 * @param driver
	 */
	public static void overviewUnreadMailClick(WebDriver driver){
		try{
			logger.info("点击首页总览快捷功能的未读邮件");
			HomePage_BAK.overviewUnreadMail(driver).click();
		}catch(Exception e){
			logger.error("跳转首页总览快捷功能的未读邮件异常",e);
		}
	}
	
	/**
	 * @Description 获取总览-未读邮件Tab-标题:未读邮件
	 * @param driver
	 * @return
	 */
	public static String getOverviewUnreadMailTab (WebDriver driver){
		String text = "";
		try{
			logger.info("获取总览-未读邮件Tab-标题:未读邮件");
			text = HomePage_BAK.overviewUnreadMailTab(driver).getAttribute("title");
		}catch(Exception e){
			logger.error("获取总览-未读邮件Tab-标题:未读邮件异常",e);
		}
		return text;
	}
	
	/**
	 * @Description 跳转首页总览快捷功能的待办邮件
	 * @param driver
	 */
	public static void overviewTodoMailClick(WebDriver driver){
		try{
			logger.info("点击首页总览快捷功能的待办邮件");
			HomePage_BAK.overviewTodoMail(driver).click();
		}catch(Exception e){
			logger.error("跳转首页总览快捷功能的待办邮件异常",e);
		}
	}
	
	/**
	 * @Description 获取总览-待办邮件Tab-标题:待办邮件
	 * @param driver
	 * @return
	 */
	public static String getOverviewTodoMailTab (WebDriver driver){
		String text = "";
		try{
			logger.info("获取总览-待办邮件Tab-标题:待办邮件");
			text = HomePage_BAK.overviewTodoMailTab(driver).getAttribute("title");
		}catch(Exception e){
			logger.error("获取总览-待办邮件Tab-标题:待办邮件异常",e);
		}
		return text;
	}
	
	/**
	 * @Description 跳转首页总览快捷功能的联系人邮件
	 * @param driver
	 */
	public static void overviewContactMailClick(WebDriver driver){
		try{
			logger.info("点击首页总览快捷功能的联系人邮件");
			HomePage_BAK.overviewContactMail(driver).click();
		}catch(Exception e){
			logger.error("跳转首页总览快捷功能的联系人邮件异常",e);
		}
	}
	
	/**
	 * @Description 获取总览-联系人Tab-标题:联系人邮件
	 * @param driver
	 * @return
	 */
	public static String getOverviewContactMailTab (WebDriver driver){
		String text = "";
		try{
			logger.info("获取总览-联系人Tab-标题:联系人邮件");
			text = HomePage_BAK.overviewContactMailTab(driver).getAttribute("title");
		}catch(Exception e){
			logger.error("获取总览-联系人Tab-标题:联系人邮件异常",e);
		}
		return text;
	}
	
	/**
	 * @Description 跳转首页总览快捷功能的积分
	 * @param driver
	 * @return
	 */
	public static String overviewJifen(WebDriver driver){
		String currentHandle = "";
		try{
			currentHandle = HomePage_BAK.windowHandle(driver);
			logger.info("点击首页总览快捷功能的积分");
			HomePage_BAK.overviewJifen(driver).click();
		}catch(Exception e){
			logger.error("跳转首页总览快捷功能的积分异常",e);
		}
		return currentHandle;
	}
	
	/**
	 * @Description 获取积分窗口标题
	 * @param driver
	 * @param currentHandle
	 * @return
	 */
	public static String getOverviewJifenWindowTitle(WebDriver driver,String currentHandle){
		String title = "";
		try{
			Set<String> handles = HomePage_BAK.windowHandles(driver);
			for(String handle :handles){
				if(handle.equals(currentHandle) == false){
					logger.info("切换积分窗口");
					driver.switchTo().window(handle);
					Thread.sleep(1000);
					logger.info("获取积分窗口标题");
					title = HomePage_BAK.windowTitile(driver);
				}
			}
		}catch(Exception e){
			logger.error("获取积分窗口标题异常",e);
		}
		return title;
	}
	
	/**
	 * @Description 跳转首页总览快捷功能的安全度
	 * @param driver
	 */
	public static void overviewSafetyDegreeClick(WebDriver driver){
		try{
			logger.info("点击首页总览快捷功能的安全度");
			HomePage_BAK.overviewSafetyDegree(driver).click();
		}catch(Exception e){
			logger.error("跳转首页总览快捷功能的安全度异常",e);
		}
	}
	
	/**
	 * @Description 获取总览-安全度-标志:邮箱健康指数
	 * @param driver
	 * @return
	 */
	public static String getOverviewSafetyDegreeSign (WebDriver driver){
		String text = "";
		try{
			logger.info("获取总览-安全度-标志:邮箱健康指数");
			text = HomePage_BAK.overviewSafetyDegreeSign(driver).getText();
		}catch(Exception e){
			logger.error("获取总览-安全度-标志:邮箱健康指数异常",e);
		}
		return text;
	}
	
	/**
	 * @Description 跳转首页总览快捷功能的登录保护
	 * @param driver
	 */
	public static void overviewLoginProtectClick(WebDriver driver){
		try{
			logger.info("点击首页总览快捷功能的登录保护");
			HomePage_BAK.overviewLoginProtect(driver).click();
		}catch(Exception e){
			logger.error("跳转首页总览快捷功能的登录保护异常",e);
		}
	}
	
	/**
	 * @Description 获取总览-登录保护-标志:邮箱登录二次验证
	 * @param driver
	 * @return
	 */
	public static String getOverviewLoginProtectSign (WebDriver driver){
		String text = "";
		try{
			driver = HomePage_BAK.loginProtectFrame(driver);
			logger.info("获取总览-登录保护-标志:邮箱登录二次验证");
			text = HomePage_BAK.overviewLoginProtectSign(driver).getText();
		}catch(Exception e){
			logger.error("获取总览-登录保护-标志:邮箱登录二次验证异常",e);
		}
		return text;
	}
	
	/**
	 * @Description 跳转首页总览快捷功能的每日生活
	 * @param driver
	 */
	public static void overviewDailyLifeClick(WebDriver driver){
		try{
			logger.info("点击首页总览快捷功能的每日生活");
			HomePage_BAK.overviewDailyLife(driver).click();
		}catch(Exception e){
			logger.error("跳转首页总览快捷功能的每日生活异常",e);
		}
	}
	
	/**
	 * @Description 获取总览-每日生活Tab-标题:严选每日推荐
	 * @param driver
	 * @return
	 */
	public static String getOverviewDailyLifeTab (WebDriver driver){
		String text = "";
		try{
			logger.info("获取总览-每日生活Tab-标题:严选每日推荐");
			text = HomePage_BAK.overviewDailyLifeTab(driver).getAttribute("title");
		}catch(Exception e){
			logger.error("获取总览-每日生活Tab-标题:严选每日推荐异常",e);
		}
		return text;
	}
	
}
