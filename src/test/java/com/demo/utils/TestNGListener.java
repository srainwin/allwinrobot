package com.demo.utils;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;

/**
 * @author XWR
 * @Description 测试用例监听配置，主要是失败用例截图功能
 */
public class TestNGListener extends TestListenerAdapter {

	/* 每次测试成功时调用*/
	@Override
	public void onTestSuccess(ITestResult result) {
		super.onTestSuccess(result);
		System.out.println(result.getName() + " Success");
	}

	/* 每次测试失败时调用*/
	@Override
	public void onTestFailure(ITestResult result) {
		super.onTestFailure(result);
		System.out.println(result.getName() + " Failure");
		//截图
		takePhoto();
	}

	/* 每次跳过测试时调用*/
	@Override
	public void onTestSkipped(ITestResult result) {
		super.onTestSkipped(result);
		System.out.println(result.getName() + " Skipped");
		//截图
		takePhoto();
	}

	/* 每次调用测试之前调用*/
	@Override
	public void onTestStart(ITestResult result) {
		super.onTestStart(result);
		System.out.println(result.getName() + " Start");
	}

	/* 在测试类被实例化之后调用，并在调用任何配置方法之前调用*/
	@Override
	public void onStart(ITestContext testContext) {
		super.onStart(testContext);
	}

	/* 在所有测试运行之后调用，并且所有的配置方法都被调用*/
	@Override
	public void onFinish(ITestContext testContext) {
		super.onFinish(testContext);
		//生成report时，移除重跑的结果，避免report生成多余用例数
		Iterator<ITestResult> listOfFailedTests = testContext.getFailedTests().getAllResults().iterator();
		while (listOfFailedTests.hasNext()) {
			ITestResult failedTest = (ITestResult) listOfFailedTests.next();
			ITestNGMethod method = failedTest.getMethod();
			if (testContext.getFailedTests().getResults(method).size() > 1) {
				listOfFailedTests.remove();
			} else {
				if (testContext.getPassedTests().getResults(method).size() > 0) {
					listOfFailedTests.remove();
				}
			}
		}
	}

	/**
	 * @Description 截图方法,并添加到Allure测试报告中
	 */
	public void takePhoto() {
		try {
			String screenName = String.valueOf(new Date().getTime()) + ".png";
			File dir = new File("./result/screenshot");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String screenPath = dir.getAbsolutePath() + "/" + screenName;

			File srcFile = ((TakesScreenshot) SeleniumUtil.driver).getScreenshotAs(OutputType.FILE);
			File destFile = new File(screenPath);
			FileUtils.copyFile(srcFile, destFile);

			//添加截图到allure测试报告中
			Path content = Paths.get(screenPath);
			InputStream is = Files.newInputStream(content);
			Allure.addAttachment("失败用例截图", is);
			System.out.println("成功进行失败用例截图，截图保存路径是：" + destFile.getAbsolutePath());
		} catch (Exception e) {
			System.out.println("失败用例截图发生异常");
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description @Attachment没效果，暂未找出原因
	 * @return
	 */
	@Attachment(value = "失败截图如下：", type = "image/png")
	public byte[] takePhoto2() {
		byte[] screenshotAs = ((TakesScreenshot) SeleniumUtil.driver).getScreenshotAs(OutputType.BYTES);
		return screenshotAs;
	}
}