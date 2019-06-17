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

import io.qameta.allure.Allure;

/**
 * @author XWR
 * @Description 测试用例监听配置，失败用例截图功能
 */
public class TestFailListener extends TestListenerAdapter {

	/* (non-Javadoc)
	 * @see org.testng.TestListenerAdapter#onTestFailure(org.testng.ITestResult)
	 */
	@Override
	public void onTestFailure(ITestResult result) {
		takePhoto();
	}
	
	/**
	 * @Description 截图方法
	 */
	public void takePhoto(){
		try{
			String screenName = String.valueOf(new Date().getTime()) + ".png";
			File dir = new File("./result/screenshot");
			if(!dir.exists()){
				dir.mkdirs();
			}
			String screenPath = dir.getAbsolutePath() + "/"+screenName;
			
			File srcFile = ((TakesScreenshot) SeleniumUtil.driver).getScreenshotAs(OutputType.FILE);
			File destFile = new File(screenPath);
			FileUtils.copyFile(srcFile, destFile);
			
			Path content = Paths.get(screenPath);
			InputStream is = Files.newInputStream(content);
			Allure.addAttachment("失败用例截图", is);
		}catch(Exception e){
			System.out.println("失败用例截图发生异常");
			e.printStackTrace();
		}
	}
}