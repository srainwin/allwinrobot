package com.demo.cases.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import io.qameta.allure.Step;

public class BaiduDemo {
	@Test(description = "百度搜索")
	public void baidu() {
		System.setProperty("webdriver.chrome.driver", "../src/test/resources/driver/chrome/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		geturl(driver);
		sendKeys(driver);
		click(driver);
		driver.close();
		
	}
	
	@Step("输入百度网站")
	public void geturl(WebDriver driver){
		driver.get("http://www.baidu.com");
	}
	
	@Step("输入搜索内容")
	public void sendKeys(WebDriver driver){
		driver.findElement(By.id("kw")).sendKeys("selenium java");
	}
	
	@Step("点击搜索")
	public void click(WebDriver driver){
		driver.findElement(By.id("su")).click();
	}
}