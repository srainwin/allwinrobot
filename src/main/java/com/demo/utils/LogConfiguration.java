package com.demo.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.testng.ITestContext;

/**
 * @author XWR
 * @Description 初始化log4j日志工具类
 */
public class LogConfiguration {
	private static SimpleDateFormat sdf;
	private static String logdate;
	private static String logRootFolderPath;
	private static String logDateFolderPath;
	private static String logFilePath;

	/**
	 * @Description 初始化log4j日志(提供外部调用)
	 * @param fileName
	 */
	public static void initLog(String fileName,ITestContext itestcontext) {
		sdf = new SimpleDateFormat("yyyyMMdd");
		logdate = sdf.format(new Date());
		logRootFolderPath = "./result/logs";
		logDateFolderPath = logRootFolderPath + "/" + logdate;
		logFilePath = logDateFolderPath + "/" + fileName + ".log";
		//日志保留天数
		int keepLogDay = Integer.valueOf(itestcontext.getCurrentXmlTest().getParameter("keepLogDay"));
		//必要时创建logs目录
		File rootfile = new File(logRootFolderPath);
		if (!rootfile.exists()) {
			rootfile.mkdirs();
		}
		//清理日志与初始化
		File[] datefiles = rootfile.listFiles();
		if ( datefiles != null) {
			for(File logDateFolder : datefiles){
				//删除过期日志文件功能
				delOldLogs(logDateFolder ,keepLogDay);
			}
			//初始化log4j日志配置
			log4jconfig(logFilePath);
		}
	}

	/**
	 * @Description 删除过期日志文件功能
	 */
	private static void delOldLogs(File logDateFolder ,int keepLogDay) {
		try{
			Date date = new Date();
			date.setTime(date.getTime() - (long)(keepLogDay)*1000*60*60*24);
			String oldLogdate = sdf.format(date);
			if( Integer.valueOf(logDateFolder.getName()) < Integer.valueOf(oldLogdate) ){
				//日期小于限定日期的文件则调用删除文件功能
				delFile(logDateFolder);
				System.out.println("成功删除[" + logDateFolder.getName() + "]过期日志");
			}
		}catch(Exception e){
			System.out.println("删除过期日志发生异常");
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description 删除文件功能，需要递归因此独立一个方法出来
	 * @param file
	 */
	private static void delFile(File file){
		try{
		// File只能删文件和非空目录，非空目录要递归深度删除
		if (file.isFile()) {
			file.delete();// 文件删除
		} else {
			File[] files = file.listFiles();
			if (files == null) {
				file.delete();// 空子目录删除
			} else {
				for (int i = 0; i < files.length; i++) {
					delFile(files[i]);// 递归深度删除子孙文件和目录
				}
				file.delete();// 空父目录删除
			}
		}
		}catch(Exception e){
			System.out.println("删除文件发生异常");
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description 初始化log4j日志配置功能
	 */
	private static void log4jconfig(String filePath){
		try {
			Properties prop = new Properties();
			// 根日志记录器(logger)的级别定义为info并连接附加器命名为toConsole和toFile
			prop.setProperty("log4j.rootLogger", "info, toConsole, toFile");
			// 设置输出日志文件编码（可以控制乱码情况）
			prop.setProperty("log4j.appender.file.encoding", "UTF-8");

			// 设置toConsole附加器类型为控制台附加器，以及日志输出的目标位置、日志输出类型和格式化类型
			prop.setProperty("log4j.appender.toConsole", "org.apache.log4j.ConsoleAppender");
			prop.setProperty("log4j.appender.toConsole.Target", "System.out");
			prop.setProperty("log4j.appender.toConsole.layout", "org.apache.log4j.PatternLayout ");
			prop.setProperty("log4j.appender.toConsole.layout.ConversionPattern","[%d{yyyy-MM-dd HH:mm:ss}] [%p] %m%n");// %d日期格式、%p日志级别、%m%n日志消息

			// 设置toFile附加器类型为每日滚动文件附加器，以及日志输出的目标位置、是否追加日志、过滤日志级别、日志输出类型和格式化类型
			prop.setProperty("log4j.appender.toFile", "org.apache.log4j.DailyRollingFileAppender");
			prop.setProperty("log4j.appender.toFile.file", filePath);
			prop.setProperty("log4j.appender.toFile.append", "true");
			prop.setProperty("log4j.appender.toFile.Threshold", "info");
			prop.setProperty("log4j.appender.toFile.layout", "org.apache.log4j.PatternLayout");
			prop.setProperty("log4j.appender.toFile.layout.ConversionPattern", "[%d{yyyy-MM-dd HH:mm:ss}] [%p] %m%n");// %d日期格式、%p日志级别、%m%n日志消息

			// log4j的Properties配置类，使得创建的Properties对象在log4j中生效
			PropertyConfigurator.configure(prop);
			System.out.println("成功初始化log4j日志配置");
		} catch (Exception e) {
			System.out.println("初始化log4j日志发生异常");
			e.printStackTrace();
		}
	}
}
