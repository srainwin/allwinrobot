package com.demo.utils;
import java.util.Properties;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author XWR
 * @Description log4j初始化配置
 */
public class LogConfiguration {
	
		/**
		 * @Description log4j初始化配置
		 * @param fileName
		 */
		public static void initLog(String fileName){
		    final String logFilePath  = "./result/logs/"+fileName+".log";  
			Properties prop = new Properties();
			prop.setProperty("log4j.rootLogger","info, toConsole, toFile");
			prop.setProperty("log4j.appender.file.encoding","UTF-8" );
			
			prop.setProperty("log4j.appender.toConsole","org.apache.log4j.ConsoleAppender");
			prop.setProperty("log4j.appender.toConsole.Target","System.out");
			prop.setProperty("log4j.appender.toConsole.layout","org.apache.log4j.PatternLayout ");
			prop.setProperty("log4j.appender.toConsole.layout.ConversionPattern","[%d{yyyy-MM-dd HH:mm:ss}] [%p] %m%n");
			
			prop.setProperty("log4j.appender.toFile", "org.apache.log4j.DailyRollingFileAppender");
			prop.setProperty("log4j.appender.toFile.file", logFilePath);
			prop.setProperty("log4j.appender.toFile.append", "false");
			prop.setProperty("log4j.appender.toFile.Threshold", "info");
			prop.setProperty("log4j.appender.toFile.layout", "org.apache.log4j.PatternLayout");
			prop.setProperty("log4j.appender.toFile.layout.ConversionPattern", "[%d{yyyy-MM-dd HH:mm:ss}] [%p] %m%n");
			
			PropertyConfigurator.configure(prop);
		}

}