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
public class TestNGRetryListener implements IAnnotationTransformer {
	
	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		try{
			IRetryAnalyzer iRetryAnalyzer = annotation.getRetryAnalyzer();
			if (iRetryAnalyzer == null) {
				// 添加自定义的重跑类TestNGRetry.class
				annotation.setRetryAnalyzer(TestNGRetry.class); //传入参数是一个重写了iRetryAnalyzer对象的retry抽象方法的类名
			}
		}catch(Exception e){
			System.out.println("监听发生异常");
			e.printStackTrace();
		}
	}
}