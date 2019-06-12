package com.demo.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

/**
 * @author XWR
 * @Description 测试用例重跑监听类
 */
public class TestRetryListener implements IAnnotationTransformer {

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		IRetryAnalyzer retry = annotation.getRetryAnalyzer();
		if (retry == null) {
			// 添加自定义的重跑类TestNGRetry.class
			annotation.setRetryAnalyzer(TestNGRetry.class);
		}
	}
}