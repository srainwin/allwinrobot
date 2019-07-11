package com.demo.pages;

import org.openqa.selenium.By;

/**
 * @author XWR
 * @Description 126邮箱写信图像定位封装
 */
public class WriteLettersPage {
	/** 收件人 */
	public static final String addressee = "收件人.png";
	
	/** 主题 */
	public static final String theme = "主题.png";
	
	/** 添加附件 */
//	public static final String addAttachments = "添加附件.png";
	public static final By addAttachments = By.cssSelector("[id$='_attachBrowser']");
	
	/** 上传完成 */
//	public static final By attachProgress100 = By.xpath("//div[ends-with(@id,'_attachProgress') and contains(text(),'100%')]");
	public static final By attachProgressOK = By.xpath("//span[text()='上传完成']");
	
	/** 附件提示 */
	public static final String appendixTips = "附件提示.png";
	
	/** 附件提示关闭 */
	public static final String appendixTipsClose = "附件提示关闭.png";
	
	/** 写信内容框 */
	public static final String writingContent = "写信内容框.png";
	
	/** 上方发送按钮 */
	public static final String upSendButton = "上方发送按钮.png";
	
	/** 发送成功标志 */
	public static final String sendSucc = "发送成功.png";
}
