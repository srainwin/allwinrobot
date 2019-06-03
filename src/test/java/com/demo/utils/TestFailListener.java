package com.demo.utils;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.demo.base.LoginBase;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;

/**
 * @author XWR
 * @Description 测试用例监听配置，失败用例截图功能
 */
/**
 * @author XWR
 * @Description 
 */
/**
 * @author XWR
 * @Description 
 */
public class TestFailListener extends TestListenerAdapter {

	/* (non-Javadoc)
	 * @see org.testng.TestListenerAdapter#onTestFailure(org.testng.ITestResult)
	 */
	@Override
	public void onTestFailure(ITestResult result) {
		//尝试takePhoto()方法无效，使用takePhoto2()方法
		takePhoto2();
	}

	/**
	 * @Description 截图方案1 无效
	 * @return
	 */
	@Attachment(value = "失败截图如下：", type = "image/png")
	public byte[] takePhoto() {
		byte[] screenshotAs = ((TakesScreenshot) LoginBase.driver).getScreenshotAs(OutputType.BYTES);
		return screenshotAs;
	}
	
	/**
	 * @Description 截图方案2 有效
	 */
	public void takePhoto2(){
		try{
			String screenName = String.valueOf(new Date().getTime()) + ".png";
			File dir = new File("./result/screenshot");
			if(!dir.exists()){
				dir.mkdirs();
			}
			String screenPath = dir.getAbsolutePath() + "/"+screenName;
			
			File srcFile = ((TakesScreenshot) LoginBase.driver).getScreenshotAs(OutputType.FILE);
			File destFile = new File(screenPath);
			FileUtils.copyFile(srcFile, destFile);
			
			Path content = Paths.get(screenPath);
			InputStream is = Files.newInputStream(content);
			Allure.addAttachment("失败用例截图", is);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}