package com.demo.utils;

import org.apache.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class TestNGRetry implements IRetryAnalyzer {
	private static Logger logger = Logger.getLogger(TestNGRetry.class.getName());
	private static int retryCount = 1; // 定义重跑次数
	private static final int maxRetryCount = 3; // 定义最大重跑次数

	@Override
	public boolean retry(ITestResult result) {
		if (retryCount <= maxRetryCount) {
			logger.info("第" + retryCount + "次重跑");
			retryCount++;
			return true;
		}
		return false;
	}
}
