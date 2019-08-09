package com.demo.utils;


import java.util.Iterator;

import org.apache.log4j.Logger;
import org.sikuli.basics.Settings;
import org.sikuli.script.App;
import org.sikuli.script.Button;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.sikuli.vnc.VNCScreen;
import org.testng.Assert;
import org.testng.ITestContext;

/**
 * @author XWR
 * @Description 包装sikuli常用方法。
 * 				sikuli是基于OCR图像识别的框架，可用于windows、mac、Android的app操作，也可用于flash操作。
 * 				这里主要是用于辅助selenium难以定位的控件操作和flash操作。
 * 				可用sikuliX IDE调试获取图像x、y、w、h坐标
 */
public class SikuliUtil {
	public static Logger logger = Logger.getLogger(SikuliUtil.class.getName());
	public static Screen screen = null;
	public static VNCScreen vncscreen = null;
	
	/** 启动sikuli的Screen对象，以及指定ImagePath存放图像文件位置(sikuli所有操作的图像都会在这找)，testng的beforeclass使用 */
	public void launchScreen(String sikuliImageFolderPath){
		try{
			logger.info("本地服务器屏幕准备中");
			screen = new Screen();
			ImagePath.add(sikuliImageFolderPath);
			logger.info("成功启动本地服务器屏幕图像识别器");
		}catch(Exception e){
			logger.error("启动本地服务器屏幕图像识别器发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 启动sikuli的VNCScreen对象 */
	public void launchVNCScreen(String vncIP, String vncPassword, String sikuliImageFolderPath){
		try{
			logger.info("远程服务器屏幕准备中");
			vncscreen = VNCScreen.start(vncIP, 5900, vncPassword, 10, 100000);
			ImagePath.add(sikuliImageFolderPath);
			logger.info("成功启动远程服务器屏幕图像识别器");
		}catch(Exception e){
			logger.error("启动远程服务器屏幕图像识别器发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 关闭sikuli vnc连接 */
	public void closeVNC(){
		try{
			vncscreen.close();
			vncscreen.stop();
			logger.info("成功关闭sikuli vnc连接");
		}catch(Exception e){
			logger.error("关闭sikuli vnc连接发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 从图像集里挑选一个符合匹配的图像
	 *  场景之一：同一处图像在不同渲染屏幕[如不同远程服务器]时容易findfailde，需要用此方法并提供多个同一处却不同渲染的图像来迭代匹配 */
	public String pickOneImage(String... images){
		for(String image:images){
			int i = 0;
			 try{
				 i++;
				 logger.info("正在查找图库集的“" + image + "”图像");
				 Pattern p = new Pattern(image);
				 (screen == null? vncscreen:screen).wait(p);
				 logger.info("图库集的“" + image + "”图像已匹配上");
				 return image;
			 }catch(FindFailed e){
				 if(i<images.length){
					 logger.warn("图库集的“" + image + "”图像未匹配上，换下一张图匹配");
				 }else{
					 logger.error("图库集的所有图像均未能匹配上",e);
				 }
				 continue;
			 }
		 }
		 return null;
	}
	
	
	/** 检查图像是否存在 */
	public boolean existsImage(String imagename, double imageTimeoutSecond){
		Match match = null;
		Pattern pattern = null;
		try{
			pattern = new Pattern(imagename);
			match = (screen == null? vncscreen:screen).exists(pattern, imageTimeoutSecond);
			if(match != null){
				logger.info("成功检查图像存在");
				return true;
			}else{
				logger.warn("成功检查图像不存在");
				return false;
			}
		}catch(Exception e){
			logger.error("检查图像是否存在发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
			return false;
		}
	}
	
	/** 检查图像1是否包含子图像2 */
	public boolean isContains(String imagename1,String imagename2){
		Region region1 = null;
		Region region2 = null;
		boolean boo = false;
		try{
			region1 = (screen == null? vncscreen:screen).find(imagename1);
			region2 = (screen == null? vncscreen:screen).find(imagename2);
			boo = region1.contains(region2);
			logger.info("成功检查图像1是否包含子图像2");
		}catch(Exception e){
			logger.error("检查图像1是否包含子图像发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
		return boo;
	}
	
	/** 获取图像中内部图像的区域 */
	public Region getInnerRegion(String outterImage,String innerImage){
		Match match1 = null;
		Match match2 = null;
		Region region = null;
		try{
			match1 = (screen == null? vncscreen:screen).find(outterImage);
			match2 = match1.find(innerImage);
			region = new Region(match2);
		}catch(Exception e){
			logger.error("获取图像中内部图像的区域发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
		return region;
	}
	
	/** 等待图像出现 */
	public Pattern waitImage(String imagename, double imageTimeoutSecond){
		Pattern pattern = null;
		Match matchImage = null;
		try{
			pattern = new Pattern(imagename);
			matchImage = (screen == null? vncscreen:screen).wait(pattern, imageTimeoutSecond);
			matchImage.highlight("red");
			logger.info("成功等待图像出现");
		}catch(Exception e){
			logger.error("等待图像出现发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
		return pattern;
	}
	
	/** 等待图像消失 */
	public Pattern waitVanishImage(String imagename, double imageTimeoutSecond){
		Pattern pattern = null;
		boolean boo = false;
		try{
			pattern = new Pattern(imagename);
			boo = (screen == null? vncscreen:screen).waitVanish(pattern, imageTimeoutSecond);
			if(boo){
				logger.info("成功等待图像消失");
			}else{
				logger.info("等待图像超时仍未消失");
				//由testng的失败断言来控制用例运行是否失败
				Assert.fail();
			}
		}catch(Exception e){
			logger.error("等待图像消失发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
		return pattern;
	}
	
	/** 查找图像  */
	public Match findImage(String imagename){
		Match matchImage = null;
		Pattern pattern = null;
		try{
			pattern = new Pattern(imagename);
			matchImage = (screen == null? vncscreen:screen).find(pattern); //相当于没有等待超时的wait()
			matchImage.highlight("red");
			logger.info("成功查找到图像");
		}catch(FindFailed e){
			logger.error("查找不到图像",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}catch(Exception e){
			logger.error("查找图像发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
		return matchImage;
	}
	
	/** 查找多个相同图像,使用时可用增强for循环获取每个图像Match进行后续处理  */
	public Iterator<Match> findImages(String imagename){
		Iterator<Match> matchImages = null;
		Pattern pattern = null;
		try{
			pattern = new Pattern(imagename);
			matchImages = (screen == null? vncscreen:screen).findAll(pattern);
			while(matchImages.hasNext()){
				matchImages.next().highlight("red");
			}
			logger.info("成功查找到多个相同图像");
		}catch(FindFailed e){
			logger.error("查找不到多个相同图像",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}catch(Exception e){
			logger.error("查找多个相同图像发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
		return matchImages;
	}
	
	/** 查找文字，不支持中文和标点符号，查找的文字最好不要跟其他文字拼接在一起  */
	public Match findText(String text, double millisecondTimeout){
		Match matchText = null;
		try{
			matchText = (screen == null? vncscreen:screen).findText(text, millisecondTimeout);
			logger.info("成功查找到文字");
		}catch(Exception e){
			logger.error("查找文字发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
		return matchText;
	}
	
	/** 查找多处相同文字，不支持中文和标点符号，查找的文字最好不要跟其他文字拼接在一起  */
	public Iterator<Match> findTexts(String text){
		Iterator<Match> matchTexts = null;
		try{
			matchTexts = (screen == null? vncscreen:screen).findAllText(text);
			logger.info("成功查找到文字");
		}catch(Exception e){
			logger.error("查找文字发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
		return matchTexts;
	}
	
	/** 查找文字并移动鼠标到文字那  */
	public Match findTextMove(String text, double secondTimeout){
		Match matchText = null;
		try{
			matchText = (screen == null? vncscreen:screen).findText(text, secondTimeout);
			(screen == null? vncscreen:screen).mouseMove(matchText);
			logger.info("成功查找文字并移动鼠标到文字那");
		}catch(Exception e){
			logger.error("查找文字并移动鼠标到文字那异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
		return matchText;
	}
	
	/** 获取图像的上方指定高度的区域 */
	public Region getImageAbove(String imagename, double imageTimeoutSecond, int height){
		Region region = null;
		try{
			waitImage(imagename,imageTimeoutSecond);
			region = (screen == null? vncscreen:screen).getLastMatch().above(height);
			logger.info("成功获取图像的上方指定高度的区域");
		}catch(Exception e){
			logger.error("获取图像的上方指定高度的区域发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
		return region;
	}
	
	/** 获取图像的下方指定高度的区域 */
	public Region getImageBelow(String imagename, double imageTimeoutSecond, int height){
		Region region = null;
		try{
			waitImage(imagename,imageTimeoutSecond);
			region = (screen == null? vncscreen:screen).getLastMatch().below(height);
			logger.info("成功获取图像的下方指定高度的区域");
		}catch(Exception e){
			logger.error("获取图像的下方指定高度的区域发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
		return region;
	}
	
	/** 获取图像的左方指定宽度的区域 */
	public Region getImageLeft(String imagename, double imageTimeoutSecond, int width){
		Region region = null;
		try{
			waitImage(imagename,imageTimeoutSecond);
			region = (screen == null? vncscreen:screen).getLastMatch().left(width);
			logger.info("成功获取图像的左方指定宽度的区域");
		}catch(Exception e){
			logger.error("获取图像的左方指定宽度的区域发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
		return region;
	}
	
	/** 获取图像的右方指定宽度的区域 */
	public Region getImageRight(String imagename, double imageTimeoutSecond, int width){
		Region region = null;
		try{
			waitImage(imagename,imageTimeoutSecond);
			region = (screen == null? vncscreen:screen).getLastMatch().right(width);
			logger.info("成功获取图像的右方指定宽度的区域");
		}catch(Exception e){
			logger.error("获取图像的右方指定宽度的区域发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
		return region;
	}
	
	/** 指定区域(x,y,w,h)坐标截取图片保存 ,默认png格式*/
	public String saveRegionImage(String imagename, int X, int Y, int W, int H, ITestContext itestcontext){
		String imagepath = itestcontext.getCurrentXmlTest().getParameter("screenImageFolderPath");
		try{
			(screen == null? vncscreen:screen).capture(Region.create(X, Y, W, H)).save(imagepath, imagename);
			logger.info("成功截取区域图片，保存路径是：" + imagepath + "/" + imagename);
		}catch(Exception e){
			logger.error("截取区域图片发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
		return imagepath;
	}
	
	/** 截取屏幕图片保存 ,默认png格式*/
	public String saveScreenImage(String imagename, ITestContext itestcontext){
		String imagepath = itestcontext.getCurrentXmlTest().getParameter("screenImageFolderPath");
		try{
			(screen == null? vncscreen:screen).capture(Region.create((screen == null? vncscreen:screen).x, (screen == null? vncscreen:screen).y, (screen == null? vncscreen:screen).w, (screen == null? vncscreen:screen).h)).save(imagepath, imagename);
			logger.info("成功截取区域图片，保存路径是：" + imagepath + "/" + imagename);
		}catch(Exception e){
			logger.error("截取区域图片发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
		return imagepath;
	}
	
	/** 获取两个图片TopLeft之间的区域*/
	public Region getRegionBetweenTwoImages(String imagename1, String imagename2){
		Region region = null;
		try{
			Pattern p1 = new Pattern(imagename1);
			Pattern p2 = new Pattern(imagename2);
			int x = (screen == null? vncscreen:screen).find(p1).getX();
			int y = (screen == null? vncscreen:screen).find(p1).getY();
			int x2 = (screen == null? vncscreen:screen).find(p2).getX();
			int y2 = (screen == null? vncscreen:screen).find(p2).getY();
			int w = x2-x;
			int h = y2-y;
			region = Region.create(x, y, w, h);
			logger.info("获取两个图片TopLeft之间的区域");
		}catch(Exception e){
			logger.error("获取两个图片TopLeft之间的区域发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
		return region;
	}
	
	/** 获取两个图片TopLeft之间的区域图片 ,默认png格式*/
	public String getImageBetweenTwoImages(String imagename1, String imagename2, String newImagename, ITestContext itestcontext){
		String imagepath = itestcontext.getCurrentXmlTest().getParameter("screenImageFolderPath");
		Region region = null;
		try{
			Pattern p1 = new Pattern(imagename1);
			Pattern p2 = new Pattern(imagename2);
			int x = (screen == null? vncscreen:screen).find(p1).getX();
			int y = (screen == null? vncscreen:screen).find(p1).getY();
			int x2 = (screen == null? vncscreen:screen).find(p2).getX();
			int y2 = (screen == null? vncscreen:screen).find(p2).getY();
			int w = x2-x;
			int h = y2-y;
			region = Region.create(x, y, w, h);
			(screen == null? vncscreen:screen).capture(region).save(imagepath, newImagename);
			logger.info("成功获取两个图片TopLeft之间的区域图片，保存路径是：" + imagepath + "/" + newImagename);
		}catch(Exception e){
			logger.error("获取两个图片TopLeft之间的区域图片发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
		return newImagename;
	}
	
	/** 指定区域(x,y,w,h)坐标进行图像文字识别，(X, Y)是屏幕像素起始位置, (W, H)从屏幕像素起始位置开始算宽和高,单位都是像素点。注意：官网介绍识别度低，比较适合类似于字母数字的识别 */
	public String getText(int X, int Y, int W, int H){
		Region region = null;
		String text = null;
		try{
			// 首先settings里面开启OCR功能
			Settings.OcrTextSearch = true;
			Settings.OcrTextRead = true;
			// 建立region
			region = Region.create(X, Y, W, H);
			region.highlight("red");
			// 获取区域中的文本
			text = region.text();
			logger.info("成功指定区域(x,y,w,h)坐标进行图像文字识别");
		}catch(Exception e){
			logger.error("指定区域(x,y,w,h)坐标进行图像文字识别发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
		return text;
	}
	
	/** 指定图像进行图像识别文字。注意：官网介绍识别度低，比较适合类似于字母数字的识别  */
	public String getText(String imagename){
		Region region = null;
		String text = null;
		try{
			// 首先settings里面开启OCR功能
			Settings.OcrTextSearch = true;
			Settings.OcrTextRead = true;
			// 建立region
			region = (screen == null? vncscreen:screen).find(imagename).getROI();
			// 获取区域中的文本
			text = region.text();
			logger.info("成功指定图像进行图像识别文字");
		}catch(Exception e){
			logger.error("指定图像进行图像识别文字发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
		return text;
	}
	
	/** 打开本地应用程序 */
	public App openApp(String appPath){
		App app = null;
		try{
			app = App.open(appPath);
			app.focus();
			logger.info("成功打开本地应用程序");
		}catch(Exception e){
			logger.error("打开本地应用程序发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
		return app;
	}
	
	/** 关闭本地应用程序 */
	public void closeApp(App app){
		try{
			app.close();
			logger.info("成功关闭本地应用程序");
		}catch(Exception e){
			logger.error("关闭本地应用程序发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 切换本地已打开的其他应用程序 */
	public App switchApp(App app){
		App app2 = null;
		try{
			app2 = switchApp(app);
			logger.info("成功切换本地应用程序");
		}catch(Exception e){
			logger.error("切换本地应用程序发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
		return app2;
	}
	
	/** 鼠标移动到区域  */
	public Region mouseMoveRegion(Region region){
		try{
			(screen == null? vncscreen:screen).mouseMove(region);
			logger.info("成功鼠标移动到区域 ");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要移动到的区域 ",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}catch(Exception e){
			logger.error("鼠标移动到区域 发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
		return region;
	}
	
	/** 鼠标移动到图像  */
	public Pattern mouseMoveImage(String imagename, double imageTimeoutSecond){
		Pattern pattern = null;
		try{
			pattern = waitImage(imagename,imageTimeoutSecond);
			(screen == null? vncscreen:screen).mouseMove(pattern);
			logger.info("成功鼠标移动到图像");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要移动到的图像",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}catch(Exception e){
			logger.error("鼠标移动到图像发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
		return pattern;
	}
	
	/** 鼠标移动到图像（带图像位置偏移量）  */
	public Pattern mouseMoveImage(String imagename, int X, int Y, double imageTimeoutSecond){
		Pattern pattern = null;
		try{
			pattern = waitImage(imagename,imageTimeoutSecond);
			(screen == null? vncscreen:screen).mouseMove(pattern.targetOffset(X, Y));
			logger.info("成功鼠标移动到图像");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要移动到的图像",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}catch(Exception e){
			logger.error("鼠标移动到图像发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
		return pattern;
	}
	
	/** 鼠标点击区域  */
	public void mouseClickRegion(Region region){
		try{
			(screen == null? vncscreen:screen).click(region);
			logger.info("成功鼠标点击区域");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要点击的区域",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}catch(Exception e){
			logger.error("鼠标点击区域发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 鼠标点击图像  */
	public void mouseClickImage(String imagename, double imageTimeoutSecond){
		Pattern pattern = null;
		try{
			pattern = waitImage(imagename,imageTimeoutSecond);
			(screen == null? vncscreen:screen).click(pattern);
			logger.info("成功鼠标点击图像");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要点击的图像",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}catch(Exception e){
			logger.error("鼠标点击图像发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 鼠标点击图像（带图像位置偏移量）  */
	public void mouseClickImage(String imagename, int X, int Y, double imageTimeoutSecond){
		Pattern pattern = null;
		try{
			pattern = waitImage(imagename,imageTimeoutSecond);
			(screen == null? vncscreen:screen).click(pattern.targetOffset(X, Y));
			logger.info("成功鼠标点击图像");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要点击的图像",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}catch(Exception e){
			logger.error("鼠标点击图像发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 鼠标延迟点击区域 */
	public void mouseDelayClickRegion(Region region, int delayTimeMillisMax1000){
		try{
			//先鼠标移动到区域处
			mouseMoveRegion(region);
			//然后进行延迟点击
			(screen == null? vncscreen:screen).delayClick(delayTimeMillisMax1000);
			logger.info("成功鼠标延迟点击区域");
		}catch(Exception e){
			logger.error("鼠标延迟点击区域发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 鼠标延迟点击图像 */
	public void mouseDelayClickImage(String imagename, double imageTimeoutSecond, int delayTimeMillisMax1000){
		try{
			//先鼠标移动到图像处
			mouseMoveImage(imagename,imageTimeoutSecond);
			//然后进行延迟点击
			(screen == null? vncscreen:screen).delayClick(delayTimeMillisMax1000);
			logger.info("成功鼠标延迟点击图像");
		}catch(Exception e){
			logger.error("鼠标延迟点击图像发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 鼠标延迟点击图像（带图像位置偏移量） */
	public void mouseDelayClickImage(String imagename, int X, int Y, double imageTimeoutSecond, int delayTimeMillisMax1000){
		try{
			//先鼠标移动到图像处
			mouseMoveImage(imagename,X,Y,imageTimeoutSecond);
			//然后进行延迟点击
			(screen == null? vncscreen:screen).delayClick(delayTimeMillisMax1000);
			logger.info("成功鼠标延迟点击图像");
		}catch(Exception e){
			logger.error("鼠标延迟点击图像发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 鼠标双击区域 */
	public void mouseDoublieClickRegion(Region region){
		try{
			(screen == null? vncscreen:screen).doubleClick(region);
			logger.info("成功鼠标双击区域");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要双击的区域",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}catch(Exception e){
			logger.error("鼠标双击区域发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 鼠标双击图像 */
	public void mouseDoublieClickImage(String imagename, double imageTimeoutSecond){
		try{
			waitImage(imagename,imageTimeoutSecond);
			(screen == null? vncscreen:screen).doubleClick(imagename);
			logger.info("成功鼠标双击图像");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要双击的图像",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}catch(Exception e){
			logger.error("鼠标双击图像发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 鼠标双击图像（带图像位置偏移量） */
	public void mouseDoublieClickImage(String imagename, int X, int Y, double imageTimeoutSecond){
		Pattern pattern = null;
		try{
			pattern = waitImage(imagename,imageTimeoutSecond);
			(screen == null? vncscreen:screen).doubleClick(pattern.targetOffset(X, Y));
			logger.info("成功鼠标双击图像");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要双击的图像",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}catch(Exception e){
			logger.error("鼠标双击图像发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 鼠标右击区域*/
	public void mouseRightClickRegion(Region region){
		try{
			(screen == null? vncscreen:screen).rightClick(region);
			logger.info("成功鼠标右击击区域");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要右击的区域",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}catch(Exception e){
			logger.error("鼠标右击区域发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 鼠标右击图像 */
	public void mouseRightClickImage(String imagename, double imageTimeoutSecond){
		try{
			waitImage(imagename,imageTimeoutSecond);
			(screen == null? vncscreen:screen).rightClick(imagename);
			logger.info("成功鼠标右击击图像");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要右击的图像",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}catch(Exception e){
			logger.error("鼠标右击图像发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 鼠标右击图像（带图像位置偏移量） */
	public void mouseRightClickImage(String imagename, int X, int Y, double imageTimeoutSecond){
		Pattern pattern = null;
		try{
			pattern = waitImage(imagename,imageTimeoutSecond);
			(screen == null? vncscreen:screen).rightClick(pattern.targetOffset(X, Y));
			logger.info("成功鼠标右击击图像");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要右击的图像",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}catch(Exception e){
			logger.error("鼠标右击图像发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 鼠标点击区域达到指定时间后再释放左键  */
	public void mouseLeftClickForTimeRegion(Region region, long millis){
		try{
			// 先鼠标移动到区域处
			mouseMoveRegion(region);
			// 然后按下鼠标左键不释放
			(screen == null? vncscreen:screen).mouseDown(Button.LEFT);
			// 等待指定时间后释放左键
			Thread.sleep(millis);
			(screen == null? vncscreen:screen).mouseUp();
			logger.info("成功鼠标点击区域达到指定时间后再释放左键");
		}catch(Exception e){
			logger.error("鼠标点击区域达到指定时间后再释放左键发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 鼠标点击图像达到指定时间后再释放左键  */
	public void mouseLeftClickForTimeImage(String imagename, double imageTimeoutSecond, long millis){
		try{
			// 先鼠标移动到图像处
			mouseMoveImage(imagename, imageTimeoutSecond);
			// 然后按下鼠标左键不释放
			(screen == null? vncscreen:screen).mouseDown(Button.LEFT);
			// 等待指定时间后释放左键
			Thread.sleep(millis);
			(screen == null? vncscreen:screen).mouseUp();
			logger.info("成功鼠标点击图像达到指定时间后再释放左键");
		}catch(Exception e){
			logger.error("鼠标点击图像达到指定时间后再释放左键发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 鼠标右击区域达到指定时间后再释放右键  */
	public void mouseRightClickForTimeRegion(Region region, long millis){
		try{
			// 先鼠标移动到区域处
			mouseMoveRegion(region);
			// 然后按下鼠标左键不释放
			(screen == null? vncscreen:screen).mouseDown(Button.RIGHT);
			// 等待指定时间后释放右键
			Thread.sleep(millis);
			(screen == null? vncscreen:screen).mouseUp();
			logger.info("成功鼠标右击区域达到指定时间后再释放右键");
		}catch(Exception e){
			logger.error("鼠标右击区域达到指定时间后再释放右键发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 鼠标右击图像达到指定时间后再释放右键  */
	public void mouseRightClickForTimeImage(String imagename, double imageTimeoutSecond, long millis){
		try{
			// 先鼠标移动到图像处
			mouseMoveImage(imagename, imageTimeoutSecond);
			// 然后按下鼠标左键不释放
			(screen == null? vncscreen:screen).mouseDown(Button.RIGHT);
			// 等待指定时间后释放右键
			Thread.sleep(millis);
			(screen == null? vncscreen:screen).mouseUp();
			logger.info("成功鼠标右击图像达到指定时间后再释放右键");
		}catch(Exception e){
			logger.error("鼠标右击图像达到指定时间后再释放右键发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}

	/** 鼠标中击区域达到指定时间后再释放中键  */
	public void mouseMiddleClickForTimeRegion(Region region, long millis){
		try{
			// 先鼠标移动到区域处
			mouseMoveRegion(region);
			// 然后按下鼠标中键不释放
			(screen == null? vncscreen:screen).mouseDown(Button.MIDDLE);
			// 等待指定时间后释放中键
			Thread.sleep(millis);
			(screen == null? vncscreen:screen).mouseUp();
			logger.info("成功鼠标中击区域达到指定时间后再释放中键");
		}catch(Exception e){
			logger.error("鼠标中击区域达到指定时间后再释放中键发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}

	/** 鼠标中击图像达到指定时间后再释放中键  */
	public void mouseMiddleClickForTimeImage(String imagename, double imageTimeoutSecond, long millis){
		try{
			// 先鼠标移动到图像处
			mouseMoveImage(imagename, imageTimeoutSecond);
			// 然后按下鼠标中键不释放
			(screen == null? vncscreen:screen).mouseDown(Button.MIDDLE);
			// 等待指定时间后释放中键
			Thread.sleep(millis);
			(screen == null? vncscreen:screen).mouseUp();
			logger.info("成功鼠标中击图像达到指定时间后再释放中键");
		}catch(Exception e){
			logger.error("鼠标中击图像达到指定时间后再释放中键发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 鼠标中轮向上滚动指定次数  */
	public void mouseWheelUpForTime(int numberOfRolls){
		try{
			(screen == null? vncscreen:screen).wheel(Button.WHEEL_UP, numberOfRolls);
			logger.info("成功鼠标中轮向上滚动指定次数");
		}catch(Exception e){
			logger.error("鼠标中轮向上滚动指定次数发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 鼠标在滚动条区域中轮向上滚动指定次数  */
	public void mouseWheelUpForTimeRegion(Region region, int numberOfRolls){
		try{
			(screen == null? vncscreen:screen).wheel(region, Button.WHEEL_UP, numberOfRolls);
			logger.info("成功鼠标在滚动条区域中轮向上滚动指定次数");
		}catch(Exception e){
			logger.error("鼠标在滚动条区域中轮向上滚动指定次数发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 鼠标在滚动条图像中轮向上滚动指定次数  */
	public void mouseWheelUpForTimeImage(String imagename, double imageTimeoutSecond, int numberOfRolls){
		Pattern pattern = null;
		try{
			pattern = waitImage(imagename, imageTimeoutSecond);
			(screen == null? vncscreen:screen).wheel(pattern, Button.WHEEL_UP, numberOfRolls);
			logger.info("成功鼠标在滚动条图像中轮向上滚动指定次数");
		}catch(Exception e){
			logger.error("鼠标在滚动条图像中轮向上滚动指定次数发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 鼠标中轮向下滚动指定次数  */
	public void mouseWheelDownForTime(int numberOfRolls){
		try{
			(screen == null? vncscreen:screen).wheel(Button.WHEEL_DOWN, numberOfRolls);
			logger.info("成功鼠标中轮向下滚动指定次数 ");
		}catch(Exception e){
			logger.error("鼠标中轮向下滚动指定次数发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 鼠标在滚动条区域中轮向下滚动指定次数  */
	public void mouseWheelDownForTimeRegion(Region region, int numberOfRolls){
		try{
			(screen == null? vncscreen:screen).wheel(region, Button.WHEEL_DOWN, numberOfRolls);
			logger.info("成功鼠标在滚动条区域中轮向下滚动指定次数");
		}catch(Exception e){
			logger.error("鼠标在滚动条区域中轮向下滚动指定次数发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 鼠标在滚动条图像中轮向下滚动指定次数  */
	public void mouseWheelDownForTimeImage(String imagename, double imageTimeoutSecond, int numberOfRolls){
		Pattern pattern = null;
		try{
			pattern = waitImage(imagename, imageTimeoutSecond);
			(screen == null? vncscreen:screen).wheel(pattern, Button.WHEEL_DOWN, numberOfRolls);
			logger.info("成功鼠标在滚动条图像中轮向下滚动指定次数");
		}catch(Exception e){
			logger.error("鼠标在滚动条图像中轮向下滚动指定次数发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 鼠标悬停在区域 */
	public void mouseHoverRegion(Region region){
		try{
			(screen == null? vncscreen:screen).hover(region);
			logger.info("成功鼠标悬停在区域");
		}catch(Exception e){
			logger.error("鼠标悬停在区域发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 鼠标悬停在图像 */
	public void mouseHoverImage(String imagename, double imageTimeoutSecond){
		try{
			waitImage(imagename,imageTimeoutSecond);
			(screen == null? vncscreen:screen).hover(imagename);
			logger.info("成功鼠标悬停在图像");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要悬停在的图像",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}catch(Exception e){
			logger.error("鼠标悬停在图像发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 鼠标悬停在图像（带图像位置偏移量） */
	public void mouseHoverImage(String imagename, int X, int Y, double imageTimeoutSecond){
		Pattern pattern = null;
		try{
			pattern = waitImage(imagename,imageTimeoutSecond);
			(screen == null? vncscreen:screen).hover(pattern.targetOffset(X, Y));
			logger.info("成功鼠标悬停在图像");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要悬停在的图像",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}catch(Exception e){
			logger.error("鼠标悬停在图像发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 鼠标拖拽区域 */
	public void mouseDragDropRegion(String sourcRegion, String targetRegion){
		try{
			(screen == null? vncscreen:screen).dragDrop(sourcRegion, targetRegion);
			logger.info("成功鼠标拖拽区域");
		}catch(Exception e){
			logger.error("鼠标拖拽区域发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 鼠标拖拽图像 */
	public void mouseDragDropImages(String sourceImage, String targetImage, double imageTimeoutSecond){
		try{
			waitImage(sourceImage,imageTimeoutSecond);
			waitImage(targetImage,imageTimeoutSecond);
			(screen == null? vncscreen:screen).dragDrop(sourceImage, targetImage);
			logger.info("成功鼠标拖拽图像");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要拖拽的图像",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}catch(Exception e){
			logger.error("鼠标拖拽图像发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 鼠标拖拽图像（带图像位置偏移量） */
	public void mouseDragDropImages(String sourceImage, String targetImage, int X1, int Y1, int X2, int Y2, double imageTimeoutSecond){
		Pattern pattern1 = null;
		Pattern pattern2 = null;
		try{
			pattern1 = waitImage(sourceImage,imageTimeoutSecond);
			pattern2 = waitImage(targetImage,imageTimeoutSecond);
			(screen == null? vncscreen:screen).dragDrop(pattern1.targetOffset(X1, Y1), pattern2.targetOffset(X2, Y2));
			logger.info("成功鼠标拖拽图像");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要拖拽的图像",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}catch(Exception e){
			logger.error("鼠标拖拽图像发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 键盘在区域中写文字（区域前提是可输入框） */
	public void keyboardWriteTextRegion(Region region, String text){
		try{
			region.write(text);
			logger.info("成功用键盘在区域中写文字");
		}catch(Exception e){
			logger.error("键盘在区域中写文字发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 键盘在图像中写文字（图像前提是可输入框） */
	public void keyboardWriteTextImages(String imagename, String text, double imageTimeoutSecond){
		try{
			waitImage(imagename,imageTimeoutSecond);
			(screen == null? vncscreen:screen).type(imagename,text);
			logger.info("成功用键盘在图像中写文字");
		}catch(Exception e){
			logger.error("键盘在图像中写文字发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 键盘在区域中输入文字（区域前提是可输入框） */
	public void keyboardTypeTextRegion(Region region, String text){
		try{
			(screen == null? vncscreen:screen).type(region,text);
			logger.info("成功用键盘在区域中输入文字");
		}catch(Exception e){
			logger.error("键盘在区域中输入文字发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 键盘在图像中输入文字（图像前提是可输入框） */
	public void keyboardTypeTextImages(String imagename, String text, double imageTimeoutSecond){
		try{
			waitImage(imagename,imageTimeoutSecond);
			(screen == null? vncscreen:screen).type(imagename,text);
			logger.info("成功用键盘在图像中输入文字");
		}catch(Exception e){
			logger.error("键盘在图像中输入文字发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 键盘在区域中粘贴文字，不受输入法影响（区域前提是可输入框） */
	public void keyboardPasteTextRegion(Region region, String text){
		try{
//			System.out.println("当前系统编码是"+System.getProperty("sun.jnu.encoding"));
			(screen == null? vncscreen:screen).paste(region,new String(text.getBytes("UTF-8"),"UTF-8"));
			logger.info("成功用键盘在区域中粘贴文字");
		}catch(Exception e){
			logger.error("键盘在区域中粘贴文字发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 键盘在图像中粘贴文字，不受输入法影响（图像前提是可输入框） */
	public void keyboardPasteTextImages(String imagename, String text, double imageTimeoutSecond){
		try{
			waitImage(imagename,imageTimeoutSecond);
			(screen == null? vncscreen:screen).paste(imagename,new String(text.getBytes("UTF-8"),"UTF-8"));
			logger.info("成功用键盘在图像中粘贴文字");
		}catch(Exception e){
			logger.error("键盘在图像中粘贴文字发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 键盘在区域中输入CTRL+KEY组合按键,KEY可以是键盘任意按键 */
	public void keyboardTypeCtrlPlusKeyRegion(Region region, String key){
		try{
			(screen == null? vncscreen:screen).type(region, key, KeyModifier.CTRL);
			logger.info("成功用键盘在区域中输入CTRL+KEY组合按键");
		}catch(Exception e){
			logger.error("键盘在区域中输入CTRL+KEY组合按键发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 键盘在图像中输入CTRL+KEY组合按键,KEY可以是键盘任意按键的名字且不区分大小写 */
	public void keyboardTypeCtrlPlusKeyImages(String imagename, String key, double imageTimeoutSecond){
		try{
			waitImage(imagename,imageTimeoutSecond);
			(screen == null? vncscreen:screen).type(imagename, key, KeyModifier.CTRL);
			logger.info("成功用键盘在图像中输入CTRL+KEY组合按键");
		}catch(Exception e){
			logger.error("键盘在图像中输入CTRL+KEY组合按键发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 键盘在屏幕中输入单个KEY按键,KEY是org.sikuli.script.Key.xxx:String */
	public void keyboardTypeKeyScreen(String key){
		try{
			(screen == null? vncscreen:screen).type(key);
			logger.info("成功用键盘在屏幕中输入单个KEY按键");
		}catch(Exception e){
			logger.error("键盘在屏幕中输入单个KEY按键发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 键盘在区域中输入单个KEY按键,KEY是org.sikuli.script.Key.xxx:String */
	public void keyboardTypeKeyRegion(Region region, String key){
		try{
			(screen == null? vncscreen:screen).type(region,key);
			logger.info("成功用键盘在区域中输入单个KEY按键");
		}catch(Exception e){
			logger.error("键盘在区域中输入单个KEY按键发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 键盘在图像中输入单个KEY按键,KEY是org.sikuli.script.Key.xxx:String */
	public void keyboardTypeKeyImages(String imagename, String key, double imageTimeoutSecond){
		try{
			waitImage(imagename,imageTimeoutSecond);
			(screen == null? vncscreen:screen).type(imagename,key);
			logger.info("成功用键盘在图像中输入单个KEY按键");
		}catch(Exception e){
			logger.error("键盘在图像中输入单个KEY按键发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 键盘在屏幕中输入任意KEY1+KEY2组合按键，KEY2任意不区分大小写的按键名字或是org.sikuli.script.Key.xxx:String，KEY1是org.sikuli.script.KeyModifier.xxx:int */
	public void keyboardTypeTwoKeysRegion(String key, int keyModifier){
		try{
			(screen == null? vncscreen:screen).type(key, keyModifier);
			logger.info("成功用键盘在屏幕中输入任意KEY1+KEY2组合按键");
		}catch(Exception e){
			logger.error("键盘在屏幕中输入任意KEY1+KEY2组合按键发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 键盘在区域中输入任意KEY1+KEY2组合按键，KEY2任意不区分大小写的按键名字或是org.sikuli.script.Key.xxx:String，KEY1是org.sikuli.script.KeyModifier.xxx:int */
	public void keyboardTypeTwoKeysRegion(Region region, String key, int keyModifier){
		try{
			(screen == null? vncscreen:screen).type(region, key, keyModifier);
			logger.info("成功用键盘在区域中输入任意KEY1+KEY2组合按键");
		}catch(Exception e){
			logger.error("键盘在区域中输入任意KEY1+KEY2组合按键发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 键盘在图像中输入任意KEY1+KEY2组合按键，KEY2任意不区分大小写的按键名字或是org.sikuli.script.Key.xxx:String，KEY1是org.sikuli.script.KeyModifier.xxx:int */
	public void keyboardTypeTwoKeysImages(String imagename, String key, int keyModifier, double imageTimeoutSecond){
		try{
			waitImage(imagename,imageTimeoutSecond);
			(screen == null? vncscreen:screen).type(imagename, key, keyModifier);
			logger.info("成功用键盘在图像中输入任意KEY1+KEY2组合按键");
		}catch(Exception e){
			logger.error("键盘在图像中输入任意KEY1+KEY2组合按键发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 键盘在屏幕中按下KEY1+KEY2+KEY3三键组合，key可以是任意不区分大小写按键名字或者是org.sikuli.script.Key.xxx:String */
	public void keyboardPressThreeKeys(String key1,String key2,String key3, long millis){
		try{
			// 连续按下三个键键不放
			(screen == null? vncscreen:screen).keyDown(key1);
			(screen == null? vncscreen:screen).keyDown(key2);
			(screen == null? vncscreen:screen).keyDown(key3);
			// 按住等多少毫秒
			Thread.sleep(millis);
			// 时间到就释放所有按键
			(screen == null? vncscreen:screen).keyUp();
			logger.info("成功键盘在屏幕中按下KEY1+KEY2+KEY3三键组合");
		}catch(Exception e){
			logger.error("键盘在屏幕中按下KEY1+KEY2+KEY3三键组合发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/** 按着某个键一定时间后再释放按键，key可以是org.sikuli.script.Key.xxx:String */
	public void keyboardPressKeyForTime(String key, long millis){
		try{
			// 按下某键不放
			(screen == null? vncscreen:screen).keyDown(key);
			// 按住等多少毫秒
			Thread.sleep(millis);
			// 时间到就释放按键
			(screen == null? vncscreen:screen).keyUp();
			logger.info("成功按着某个键一定时间后再释放按键");
		}catch(Exception e){
			logger.error("按着某个键一定时间后再释放按键发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			Assert.fail();
		}
	}
	
	/**
	 * @Description 复制excel首行某字段下方的单元格内容并粘贴到目标图像处
	 * （为解决vncscreen情况下paste方法缺陷而产生的输入文字新方案，经测试vncscreen情况下findtext无效，本方案需要用到findtext，马勒戈壁）
	 * @param testdataName 与@test方法的参数(测试数据)名字一样的
	 * @param excelPath 测试数据excel文件的完整路径（路径不可含中文）
	 * @param inputImage 目标输入框的图像名字
	 */
	public void pasteFromExcel(String testdataName, String excelPath, String inputImage){
		try{
			// 按键win+r弹出“运行”窗口
			(screen == null? vncscreen:screen).type("r", KeyModifier.WIN);
			// “运行”窗口光标默认已在“打开(O)”输入框中，直接输入测试数据excel文件路径，并回车打开excel文件
			(screen == null? vncscreen:screen).type("a", KeyModifier.CTRL);
			(screen == null? vncscreen:screen).type(Key.BACKSPACE);
			(screen == null? vncscreen:screen).write(excelPath);
			(screen == null? vncscreen:screen).type(Key.ENTER);
			// 根据测试数据excel文件的设计，sheet首行英文字段为测试数据的名字，英文字段名字是与@test方法的测试数据参数名字一样的，直接根据名字找它在屏幕中的位置
			Match mat = (screen == null? vncscreen:screen).findText(testdataName,5);
			// 找到测试数据英文字段后双击其下方的单元格并全选内容后按键ctrl+c复制
			Region reg = new Region(mat.getCenter().getX(), (new Double(mat.getCenter().getY()+mat.getH())).intValue());
			(screen == null? vncscreen:screen).mouseMove(reg);
			(screen == null? vncscreen:screen).click(reg);
			(screen == null? vncscreen:screen).type(Key.F2);
			keyboardPressThreeKeys(Key.CTRL,Key.SHIFT,Key.HOME, 200);
			(screen == null? vncscreen:screen).type("c", KeyModifier.CTRL);
			Thread.sleep(2000);
			// 按键alt+f4后回车保存关闭excel
			(screen == null? vncscreen:screen).type(Key.F4, KeyModifier.ALT);
			(screen == null? vncscreen:screen).type(Key.ENTER);
			// 查找输入框的图像位置并点击输入框
			mouseClickImage(inputImage, 5000);
			// 按键ctrl+v粘贴
			(screen == null? vncscreen:screen).type("v", KeyModifier.CTRL);
			logger.info("成功复制excel首行某字段下方的单元格内容并粘贴到目标图像处");
		}catch(Exception e){
			logger.error("复制excel首行某字段下方的单元格内容并粘贴到目标图像处发生异常",e);
			//由testng的失败断言来控制用例运行是否失败
			(screen == null? vncscreen:screen).type(Key.F4, KeyModifier.ALT);
			(screen == null? vncscreen:screen).type(Key.ENTER);
			Assert.fail();
		}
	}

}
