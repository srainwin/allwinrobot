package com.demo.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

/**
 * @author XWR
 * @Description 测试用例重跑监听类
 * TestNG允许您在测试执行时修改所有注解（@Test,@DataProvider,@Factory等）的内容，
 * 可以通过重写IAnnotationTransformer，IAnnotationTransformer2 的方法来实现。
 * IAnnotationTransformer 只能用来修改 @Test 注解，
 * 如果需要修改其他 TestNG 的注解（比如@DataProvider, @Factory 以及 @Configuration），需要使用 IAnnotationTransformer2 监听器。
 */
public class TestNGRetryListener implements IAnnotationTransformer {
	
	/* 注解转换器,通过重写transform方法可以改写@Test注解的属性，如下改写了RetryAnalyzer属性*/
	@Override
	@SuppressWarnings("rawtypes") //忽略rawtype警告
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