package com.demo.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

/**
 * @author XWR
 * @Description 测试用例重跑监听类
 */
public class TestRetryListener implements IAnnotationTransformer {

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		IRetryAnalyzer iRetryAnalyzer = annotation.getRetryAnalyzer();
		if (iRetryAnalyzer == null) {
			// 添加自定义的重跑类TestNGRetry.class
			annotation.setRetryAnalyzer(TestNGRetry.class); //传入参数是下方类名，下面的类重写了iRetryAnalyzer对象的retry抽象方法
		}
	}
}

/**
 * @author XWR
 * @Description 测试用例失败重跑类retry抽象方法重写，应用于监听类使用
 */
class TestNGRetry implements IRetryAnalyzer {
	private static Logger logger = Logger.getLogger(TestNGRetry.class.getName());
	private static int retryCount = 1; // 定义重跑次数
	private static final int maxRetryCount = 3; // 定义最大重跑次数

	@Override
	public boolean retry(ITestResult result) {
		if (retryCount <= maxRetryCount) {
			logger.info("第" + retryCount + "次重跑");
			retryCount++;
			return true;
		}else{
			logger.info("已超过最大重跑次数，不再尝试");
			return false;
		}
	}
}