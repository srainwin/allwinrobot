package com.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author XWR
 * @Description 登录126邮箱页面元素定位封装
 */
public class LoginPage {
	private static WebElement element = null;

	
	/**
	 * @Description 输入url
	 * @param driver
	 * @return
	 */
	public static WebDriver url(WebDriver driver,String baseurl){
		driver.get(baseurl);
		return driver;
	}
	
	/**
	 * @Description iframe
	 * @param driver
	 * @return
	 */
	public static WebDriver loginFrame(WebDriver driver){
		element = driver.findElement(By.cssSelector("iframe[id^='x-URS-iframe']"));
		//element = driver.findElement(By.xpath("//iframe[starts-with(@id,'x-URS-iframe')]"));
		driver = driver.switchTo().frame(element);
		return driver;
	}
	
	/**
	 * @Description 退出iframe
	 * @param driver
	 * @return
	 */
	public static WebDriver OutFrame(WebDriver driver){
		driver = driver.switchTo().defaultContent();
		return driver;
	}

	/**
	 * @Description 选择账号登陆方式
	 * @param driver
	 * @return
	 */
	public static WebElement loginByNormal(WebDriver driver){
		element = driver.findElement(By.cssSelector("#lbNormal"));
		return element;
	}
	
	/**
	 * @Description 用户名输入框
	 * @param driver
	 * @return
	 */
	public static WebElement loginUsername(WebDriver driver){
		element = driver.findElement(By.cssSelector(".dlemail"));
		return element;
	}

	/**
	 * @Description 密码输入框
	 * @param driver
	 * @return
	 */
	public static WebElement loginPassword(WebDriver driver){
		element = driver.findElement(By.cssSelector(".dlpwd"));
		return element;
	}

	/**
	 * @Description 登陆按钮
	 * @param driver
	 * @return
	 */
	public static WebElement loginButton(WebDriver driver){
		element = driver.findElement(By.className("u-loginbtn"));
		return element;
	}

	/**
	 * @Description 当前用户名信息
	 * @param driver
	 * @return
	 */
	public static WebElement loginCurrentUser(WebDriver driver){
		element = driver.findElement(By.id("spnUid"));
		return element;
	}

	/**
	 * @Description 退出按钮
	 * @param driver
	 * @return
	 */
	public static WebElement logoutButton(WebDriver driver){
		element = driver.findElement(By.linkText("退出"));
		return element;
	}

	/**
	 * @Description 登陆错误提示信息
	 * @param driver
	 * @return
	 */
	public static WebElement loginErrorInfo(WebDriver driver){
		element = driver.findElement(By.cssSelector(".ferrorhead"));
		return element;
	}
}
