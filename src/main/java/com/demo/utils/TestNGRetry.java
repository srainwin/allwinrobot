package com.demo.utils;

import org.apache.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * @author XWR
 * @Description 测试用例失败重跑类retry抽象方法重写，应用于监听类IRetryAnalyzer对象的setRetryAnalyzer方法使用
 */
public class TestNGRetry implements IRetryAnalyzer {
	private static Logger logger = Logger.getLogger(TestNGRetry.class.getName());
	private static int retryCount = 1; // 定义重跑次数
	private static final int maxRetryCount = 3; // 定义最大重跑次数

	/* (non-Javadoc)
	 * @see org.testng.IRetryAnalyzer#retry(org.testng.ITestResult)
	 */
	@Override
	public boolean retry(ITestResult result) {
		try{
			if (retryCount <= maxRetryCount) {
				logger.info("将进行第" + retryCount + "次重跑");
				retryCount++;
				return true;
			}else{
				logger.info("已超过最大重跑次数，不再尝试");
				return false;
			}
		}catch(Exception e){
			logger.error("重跑发生异常",e);
			return false;
		}
	}
}