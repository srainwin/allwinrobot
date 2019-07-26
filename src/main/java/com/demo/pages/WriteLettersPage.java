package com.demo.pages;

import org.openqa.selenium.By;

/**
 * @author XWR
 * @Description 126邮箱写信图像定位封装
 */
public class WriteLettersPage {
	/** 收件人 */
	public static final String addressee = "收件人node1.png";
	public static final String addressee2 = "收件人node2.png";
	
	/** 主题 */
	public static final String theme = "主题node1.png";
	public static final String theme2 = "主题node2.png";
	
	/** 添加附件 
	 * 上传附件用autoit，不用图像识别*/
//	public static final String addAttachments = "添加附件node1.png";
//	public static final String addAttachments = "添加附件node2.png";
	public static final By addAttachments = By.cssSelector("[id$='_attachBrowser']");
	
	/** 上传完成 */
//	public static final By attachProgress100 = By.xpath("//div[ends-with(@id,'_attachProgress') and contains(text(),'100%')]");
	public static final By attachProgressOK = By.xpath("//span[text()='上传完成']");
	
	/** 附件提示 */
	public static final String appendixTips = "附件提示node1.png";
	public static final String appendixTips2 = "附件提示node2.png";
	
	/** 附件提示关闭 */
	public static final String appendixTipsClose = "附件提示关闭node1.png";
	public static final String appendixTipsClose2 = "附件提示关闭node2.png";
	
	/** 写信内容框 */
	public static final String writingContent = "写信内容框node1.png";
	public static final String writingContent2 = "写信内容框node2.png";
	
	/** 上方发送按钮 */
	public static final String upSendButton = "上方发送按钮node1.png";
	public static final String upSendButton2 = "上方发送按钮node2.png";
	
	/** 发送成功标志 */
	public static final String sendSucc = "发送成功node1.png";
	public static final String sendSucc2 = "发送成功node2.png";
}
