/***
 * @author xwr
 * @Description 由测试用例继承此基类。启动浏览器和关闭浏览器，以及提供测试数据。
*/
package com.demo.base;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.os.WindowsUtils;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import com.demo.utils.LogConfiguration;
import com.demo.utils.SeleniumUtil;
import com.demo.utils.SikuliUtil;

public class LoginBase {
	// seleniumUtil对象的driver成员变量最终会通过BeforeClass中运行launchBrowser成员方法获取对应浏览器驱动，所有用例继承LoginBase都使用这个seleniumUtil对象中的driver成员变量
	protected static SeleniumUtil seleniumUtil = new SeleniumUtil();
	// sikuliUtil对象的Screen成员变量最终会通过BeforeClass中运行launchScreen成员方法获取屏幕，所有用例继承LoginBase都使用这个sikuliUtil对象中的Screen成员变量
	protected static SikuliUtil sikuliUtil = new SikuliUtil();

	protected String browserName;
	protected String testurl;
	protected int pageLoadTimeout;
	protected String cookiesConfigFilePath;
	protected String testDataFilePath;
	protected String sikuliImageFolderPath;
	protected String screenImageFolderPath;
	protected String autoitFolderPath;
	protected String driverConfigFilePath;
	protected String isRemote;
	protected String huburl;
	protected String isVNC;
	protected String vncPassword;
	

	static Logger logger = Logger.getLogger(LoginBase.class.getName());

	/**
	 * @Description 在BeforeClass的setup前清理本地机残留的浏览器程序和driver进程，远程机暂不支持清理
	 * @param itestcontext
	 */
	@BeforeSuite
	public void setupCleanup(ITestContext itestcontext) {
		killDriver2(itestcontext);
	}

	/**
	 * @Description 在AfterClass的teardown后清理本地机残留的浏览器程序和driver进程，远程机暂不支持清理
	 * @param itestcontext
	 */
	@AfterSuite
	public void teardownCleanup(ITestContext itestcontext) {
		killDriver2(itestcontext);
	}

	/**
	 * @Description 启动浏览器
	 * @param itestcontext
	 */
	@BeforeClass
	public void setup(ITestContext itestcontext) {
		try {
			// initLog的参数filename与继承本类的测试类名相同，this指向调用这个setup()方法的测试类
			LogConfiguration.initLog(this.getClass().getSimpleName(), itestcontext);
			logger.info("正启动浏览器");

			// 给共享数据赋值，供本类或任意继承本类的@Test用例使用（itestcontext是测试的上下文，包含很多信息，包括TestNG配置文件中的参数信息）
			// 原本打算在@Test的用例方法传入itestcontext参数的，但@Test的用例使用了@dataProvider后运行会检查出参数个数不一致异常，因为多了itestcontext参数，所以决定把itestcontext获取共享数据放到@BeforeClass中进行
			cookiesConfigFilePath = itestcontext.getCurrentXmlTest().getParameter("cookiesConfigFilePath");
			testDataFilePath = itestcontext.getCurrentXmlTest().getParameter("testDataFilePath");
			browserName = itestcontext.getCurrentXmlTest().getParameter("browserName");
			testurl = itestcontext.getCurrentXmlTest().getParameter("testurl");
			pageLoadTimeout = Integer.parseInt(itestcontext.getCurrentXmlTest().getParameter("pageLoadTimeout"));
			sikuliImageFolderPath = itestcontext.getCurrentXmlTest().getParameter("sikuliImageFolderPath");
			screenImageFolderPath = itestcontext.getCurrentXmlTest().getParameter("screenImageFolderPath");
			autoitFolderPath = itestcontext.getCurrentXmlTest().getParameter("autoitFolderPath");
			driverConfigFilePath = itestcontext.getCurrentXmlTest().getParameter("driverConfigFilePath");
			isRemote = itestcontext.getCurrentXmlTest().getParameter("isRemote");
			huburl = itestcontext.getCurrentXmlTest().getParameter("huburl");
			isVNC = itestcontext.getCurrentXmlTest().getParameter("isVNC");
			vncPassword = itestcontext.getCurrentXmlTest().getParameter("vncPassword");

			// 启动本地或者远程的某款浏览器
			seleniumUtil.launchBrowser(browserName, driverConfigFilePath, isRemote, huburl, pageLoadTimeout);
			
			// 启动sikuli屏幕操作器
			if(isVNC.equals("true")){
				// VNC
				sikuliUtil.launchVNCScreen(seleniumUtil.getGridIP(huburl), vncPassword, sikuliImageFolderPath);
			}else{
				// local
				sikuliUtil.launchScreen(sikuliImageFolderPath);
			}

			logger.info(browserName + "浏览器启动成功!");
		} catch (Exception e) {
			logger.error(browserName + "浏览器不能正常工作，请检查是不是被手动关闭或者其他原因", e);
		}
	}

	/**
	 * @Description 关闭浏览器之类的善后工作
	 */
	@AfterClass
	public void teardown() {
		try {
			// 关闭浏览器
			seleniumUtil.quit();
			// 当开启VNC时关闭VNC连接
			if( isVNC.equals("true") ){
				sikuliUtil.closeVNC();
			}
		} catch (Exception e) {
			logger.error("浏览器等善后工作发送异常，无法关闭", e);
		}
	}

	/**
	 * @Description 使用apache poi读取excel来提供测试数据
	 * @return
	 */
	@DataProvider(name = "testdata")
	public Object[][] testData() {
		try {
			// 数据文件路径，一个测试类对应一个excel数据文件，数据文件名与测试类名相同
			String dataname = this.getClass().getSimpleName();
			String datapath = testDataFilePath + "/" + dataname + ".xlsx";
			// 获取工作簿
			InputStream is = new FileInputStream(datapath);
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			// 获取工作表
			XSSFSheet sheet = workbook.getSheetAt(0);
			if (sheet == null) {
				workbook.close();
				return null;
			}
			// 获取工作表总行数，注意：首行行号是0
			int rowcount = (sheet.getLastRowNum() - sheet.getFirstRowNum()) + 1;
			List<Object[]> list = new ArrayList<Object[]>();
			// 遍历获取每行记录，除了第一行，第一行是数据列名称，所以rownum不从0开始
			for (int rownum = 1; rownum < rowcount; rownum++) {
				XSSFRow row = sheet.getRow(rownum);
				if (row == null) {
					continue;
				}
				// 获取每行记录的总列数
				int cellcount = row.getLastCellNum();
				Object[] cell = new Object[cellcount];
				// 遍历获取每行记录的每一列
				for (int cellnum = 0; cellnum < cellcount; cellnum++) {
					XSSFCell xssfcell = row.getCell(cellnum);
					if (xssfcell == null) {
						cell[cellnum] = new String("");
						continue;
					}
					// 调用getValue方法处理xssfcell获取的多种类型值转化为string类型，并放到一个object一维数组中
					cell[cellnum] = getCellValue(xssfcell);
				}
				// 存储测试数据，第n行所有列数据存放到lsit列表中第n个Object[]对象
				list.add(cell);
			}
			workbook.close();
			// List<Object[]>数据转化为Object[][]数据，list的总数就是object二维数组中的一维数组总数
			int listcount = list.size();
			Object[][] result = new Object[listcount][];
			// 将list中的object一维数组成为object二维数组中的一维数组
			for (int i = 0; i < listcount; i++) {
				result[i] = list.get(i);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * @Description 用于testData()方法里处理cell的多种类型值转化为string类型
	 * @return
	 */
	private String getCellValue(XSSFCell xssfcell) {
		if (xssfcell.getCellType() == CellType.BOOLEAN) {
			return String.valueOf(xssfcell.getBooleanCellValue());
		} else if (xssfcell.getCellType() == CellType.NUMERIC) {
			return String.valueOf(xssfcell.getNumericCellValue());
		} else {
			return String.valueOf(xssfcell.getStringCellValue());
		}
	}

	/**
	 * @Description 用于beforesuite和aftersuite清理服务器上的浏览器driver进程
	 */
	@SuppressWarnings("unused") //忽略never use警告
	private void killDriver(ITestContext itestcontext) {
		try {
			browserName = itestcontext.getCurrentXmlTest().getParameter("browserName");
			if (browserName.equalsIgnoreCase("ie")) {
				// 杀遗留旧进程
				WindowsUtils.killByName("iexplore.exe");
				WindowsUtils.killByName("IEDriverServer.exe");

			} else if (browserName.equalsIgnoreCase("chrome")) {
				// 杀遗留旧进程
				WindowsUtils.killByName("chrome.exe");
				WindowsUtils.killByName("chromedriver.exe");

			} else if (browserName.equalsIgnoreCase("firefox")) {
				// 杀遗留旧进程
				WindowsUtils.killByName("firefox.exe");
				WindowsUtils.killByName("geckodriver.exe");

			} else if (browserName.equalsIgnoreCase("ghost")) {
				// 杀遗留旧进程
				WindowsUtils.killByName("phantomjs.exe");
			} else {
				logger.warn(browserName + "浏览器不支持，支持ie、chrome、firefox和ghost，将默认使用chrome浏览器进行");
				// 杀遗留旧进程
				WindowsUtils.killByName("chrome.exe");
				WindowsUtils.killByName("chromedriver.exe");
			}
		} catch (Exception e) {
			logger.error("清理driver进程发生异常", e);
		}
	}
	
	/**
	 * @Description 用于beforesuite和aftersuite清理服务器上的浏览器driver进程
	 */
	private void killDriver2(ITestContext itestcontext) {
		try {
			browserName = itestcontext.getCurrentXmlTest().getParameter("browserName");
			Runtime rn = Runtime.getRuntime();
			if (browserName.equalsIgnoreCase("ie")) {
				// 杀遗留旧进程
				rn.exec("taskkill /t /f /im iexplore.exe");
				rn.exec("taskkill /t /f /im IEDriverServer.exe");

			} else if (browserName.equalsIgnoreCase("chrome")) {
				// 杀遗留旧进程
				rn.exec("taskkill /t /f /im chrome.exe");
				rn.exec("taskkill /t /f /im chromedriver.exe");

			} else if (browserName.equalsIgnoreCase("firefox")) {
				// 杀遗留旧进程
				rn.exec("taskkill /t /f /im firefox.exe");
				rn.exec("taskkill /t /f /im geckodriver.exe");

			} else if (browserName.equalsIgnoreCase("ghost")) {
				// 杀遗留旧进程
				rn.exec("taskkill /t /f /im phantomjs.exe");
			} else {
				System.out.println(browserName + "浏览器不支持，支持ie、chrome、firefox和ghost，将默认使用chrome浏览器进行");
				// 杀遗留旧进程
				rn.exec("taskkill /t /f /im chrome.exe");
				rn.exec("taskkill /t /f /im chromedriver.exe");
			}
		} catch (Exception e) {
			logger.error("清理driver进程发生异常", e);
		}
	}
}
