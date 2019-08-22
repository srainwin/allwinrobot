package com.demo.utils;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.jayway.jsonpath.JsonPath;

public class JsonPageParser {
	private File jsonfile;
	private Logger logger;

	public JsonPageParser(String jsonfileName) {
		this.logger = Logger.getLogger(JsonPageParser.class.getName());
		this.jsonfile = new File(System.getProperty("user.dir") + "/../src/main/java/com/demo/pages/" + jsonfileName);
	}

	public By getElementLocator(String locatorName){
		By locator = null;
		try{
			List<String> locatorTypeList = JsonPath.read(jsonfile, "$.." + locatorName + "[1]");
			List<String> locatorValueList = JsonPath.read(jsonfile, "$.." + locatorName + "[2]");
			String locatorType = locatorTypeList.get(0).toLowerCase();
			String locatorValue = locatorValueList.get(0);

			switch (locatorType) {
			//id、name、className、tagName、linkText、partialLinkText、xpath、cssSelector、imageName
			case "id":
				locator = By.id(locatorValue);
				break;
			case "name":
				locator = By.name(locatorValue);
				break;
			case "classname":
				locator = By.className(locatorValue);
				break;
			case "tagname":
				locator = By.tagName(locatorValue);
				break;
			case "linktext":
				locator = By.linkText(locatorValue);
				break;
			case "partiallinktext":
				locator = By.partialLinkText(locatorValue);
				break;
			case "xpath":
				locator = By.xpath(locatorValue);
				break;
			case "cssselector":
				locator = By.cssSelector(locatorValue);
				break;
			}
		}catch(Exception e){
			logger.error("获取元素定位值发生异常",e);
		}
		return locator;
	}
	
	public String getImageLocator(String locatorName) {
		try{
			List<String> locatorTypeList = JsonPath.read(jsonfile, "$.." + locatorName + "[1]");
			List<String> locatorValueList = JsonPath.read(jsonfile, "$.." + locatorName + "[2]");
			String locatorType = locatorTypeList.get(0).toLowerCase();
			String locatorValue = locatorValueList.get(0);
			if("imagename".equals(locatorType)){
				return locatorValue;
			}else{
				logger.warn("定位的类型非imagename，请检查！");
				return null;
			}
		}catch(Exception e){
			logger.error("获取图像定位值发生异常", e);
			return null;
		}
	}
}
