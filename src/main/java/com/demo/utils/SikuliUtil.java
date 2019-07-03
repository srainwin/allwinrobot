package com.demo.utils;


import java.util.Iterator;

import org.apache.log4j.Logger;
import org.sikuli.basics.Settings;
import org.sikuli.script.App;
import org.sikuli.script.Button;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.testng.ITestContext;

/**
 * @author XWR
 * @Description 包装sikuli常用方法。
 * 				sikuli是基于OCR图像识别的框架，可用于windows、mac、Android的app操作，也可用于flash操作。
 * 				这里主要是用于辅助selenium难以定位的控件操作和flash操作。
 * 				可用sikuliX IDE调试获取图像x、y、w、h坐标
 */
public class SikuliUtil {
	public static Screen screen = null;
	public static Logger logger = Logger.getLogger(SikuliUtil.class.getName());
	
	/** 启动sikuli的Screen对象，以及指定ImagePath存放图像文件位置(sikuli所有操作的图像都会在这找)，testng的beforeclass使用 */
	public void launchScreen(String imageFolderPath){
		try{
			screen = new Screen();
			ImagePath.add(imageFolderPath);
			logger.info("成功启动屏幕图像识别器");
		}catch(Exception e){
			logger.error("启动屏幕图像识别器发生异常",e);
		}
	}
	
	/** 检查图像是否存在 */
	public boolean existsImage(String imagename, double imageTimeoutSecond){
		Match match = null;
		Pattern pattern = null;
		try{
			pattern = new Pattern("imagename");
			match = screen.exists(pattern, imageTimeoutSecond);
			if(match != null){
				logger.info("成功检查图像存在");
				return true;
			}else{
				logger.warn("成功检查图像不存在");
				return false;
			}
		}catch(Exception e){
			logger.error("检查图像是否存在发生异常",e);
			return false;
		}
	}
	
	/** 检查图像1是否包含子图像2 */
	public static boolean isContainText(Screen screen,String imagename1,String imagename2){
		Region region1 = null;
		Region region2 = null;
		boolean boo = false;
		try{
			region1 = screen.find(imagename1);
			region2 = screen.find(imagename2);
			boo = region1.contains(region2);
		}catch(Exception e){
			e.printStackTrace();
		}
		return boo;
	}
	
	/** 等待图像出现 */
	public Pattern waitImage(String imagename, double imageTimeoutSecond){
		Pattern pattern = null;
		Match matchImage = null;
		try{
			pattern = new Pattern("imagename");
			matchImage = screen.wait(pattern, imageTimeoutSecond);
			matchImage.highlight("red");
			logger.info("成功等待图像出现");
		}catch(Exception e){
			logger.error("等待图像出现发生异常",e);
		}
		return pattern;
	}
	
	/** 等待图像消失 */
	public Pattern waitVanishImage(String imagename, double imageTimeoutSecond){
		Pattern pattern = null;
		try{
			pattern = new Pattern("imagename");
			screen.waitVanish(pattern, imageTimeoutSecond);
			logger.info("成功等待图像消失");
		}catch(Exception e){
			logger.error("等待图像消失发生异常",e);
		}
		return pattern;
	}
	
	/** 查找图像  */
	public Match findImage(String imagename){
		Match matchImage = null;
		Pattern pattern = null;
		try{
			pattern = new Pattern(imagename);
			matchImage = screen.find(pattern); //相当于没有等待超时的wait()
			matchImage.highlight("red");
			logger.info("成功查找到图像");
		}catch(FindFailed e){
			logger.error("查找不到图像",e);
		}catch(Exception e){
			logger.error("查找图像发生异常",e);
		}
		return matchImage;
	}
	
	/** 查找多个相同图像,使用时可用增强for循环获取每个图像Match进行后续处理  */
	public Iterator<Match> findImages(String imagename){
		Iterator<Match> matchImages = null;
		Pattern pattern = null;
		try{
			pattern = new Pattern(imagename);
			matchImages = screen.findAll(pattern);
			while(matchImages.hasNext()){
				matchImages.next().highlight("red");
			}
			logger.info("成功查找到多个相同图像");
		}catch(FindFailed e){
			logger.error("查找不到多个相同图像",e);
		}catch(Exception e){
			logger.error("查找多个相同图像发生异常",e);
		}
		return matchImages;
	}

	/** 处理图像位置偏移量(精准图像到某个点) */
	public Pattern imageOffset(String imagename, int X, int Y,double imageTimeoutSecond){
		Pattern pattern = null;
		try{
			waitImage(imagename,imageTimeoutSecond);
			pattern = new Pattern("imagename");
			pattern.targetOffset(X, Y);
			logger.info("成功处理图像位置偏移量");
		}catch(Exception e){
			logger.error("处理图像位置偏移量发生异常",e);
		}
		return pattern;
	}
	
	/** 获取图像的上方指定高度的区域 */
	public Region getImageAbove(String imagename, double imageTimeoutSecond, int height){
		Region region = null;
		try{
			waitImage(imagename,imageTimeoutSecond);
			region = screen.getLastMatch().above(height);
			logger.info("成功获取图像的上方指定高度的区域");
		}catch(Exception e){
			logger.error("获取图像的上方指定高度的区域发生异常",e);
		}
		return region;
	}
	
	/** 获取图像的下方指定高度的区域 */
	public Region getImageBelow(String imagename, double imageTimeoutSecond, int height){
		Region region = null;
		try{
			waitImage(imagename,imageTimeoutSecond);
			region = screen.getLastMatch().below(height);
			logger.info("成功获取图像的下方指定高度的区域");
		}catch(Exception e){
			logger.error("获取图像的下方指定高度的区域发生异常",e);
		}
		return region;
	}
	
	/** 获取图像的左方指定宽度的区域 */
	public Region getImageLeft(String imagename, double imageTimeoutSecond, int width){
		Region region = null;
		try{
			waitImage(imagename,imageTimeoutSecond);
			region = screen.getLastMatch().left(width);
			logger.info("成功获取图像的左方指定高度的区域");
		}catch(Exception e){
			logger.error("获取图像的左方指定高度的区域发生异常",e);
		}
		return region;
	}
	
	/** 获取图像的右方指定宽度的区域 */
	public Region getImageRight(String imagename, double imageTimeoutSecond, int width){
		Region region = null;
		try{
			waitImage(imagename,imageTimeoutSecond);
			region = screen.getLastMatch().right(width);
			logger.info("成功获取图像的右方指定高度的区域");
		}catch(Exception e){
			logger.error("获取图像的右方指定高度的区域发生异常",e);
		}
		return region;
	}
	
	/** 指定区域(x,y,w,h)坐标截取图片保存 ,默认png格式*/
	public String saveRegionImage(String imagename, int X, int Y, int W, int H, ITestContext itestcontext){
		String imagepath = itestcontext.getCurrentXmlTest().getParameter("screenImageFolderPath");
		try{
			screen.capture(Region.create(X, Y, W, H)).save(imagepath, imagename);
			logger.info("成功截取区域图片，保存路径是：" + imagepath + "/" + imagename);
		}catch(Exception e){
			logger.error("截取区域图片发生异常",e);
		}
		return imagepath;
	}
	
	/** 截取屏幕图片保存 ,默认png格式*/
	public String saveScreenImage(String imagename, ITestContext itestcontext){
		String imagepath = itestcontext.getCurrentXmlTest().getParameter("screenImageFolderPath");
		try{
			screen.capture(Region.create(screen.x, screen.y, screen.w, screen.h)).save(imagepath, imagename);
			logger.info("成功截取区域图片，保存路径是：" + imagepath + "/" + imagename);
		}catch(Exception e){
			logger.error("截取区域图片发生异常",e);
		}
		return imagepath;
	}
	
	/** 获取两个图片TopLeft之间的区域*/
	public Region getRegionBetweenTwoImages(String imagename1, String imagename2){
		Region region = null;
		try{
			Pattern p1 = new Pattern(imagename1);
			Pattern p2 = new Pattern(imagename2);
			int x = screen.find(p1).getX();
			int y = screen.find(p1).getY();
			int x2 = screen.find(p2).getX();
			int y2 = screen.find(p2).getY();
			int w = x2-x;
			int h = y2-y;
			region = Region.create(x, y, w, h);
			logger.info("获取两个图片TopLeft之间的区域");
		}catch(Exception e){
			logger.error("获取两个图片TopLeft之间的区域发生异常",e);
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
			int x = screen.find(p1).getX();
			int y = screen.find(p1).getY();
			int x2 = screen.find(p2).getX();
			int y2 = screen.find(p2).getY();
			int w = x2-x;
			int h = y2-y;
			region = Region.create(x, y, w, h);
			screen.capture(region).save(imagepath, newImagename);
			logger.info("成功获取两个图片TopLeft之间的区域图片，保存路径是：" + imagepath + "/" + newImagename);
		}catch(Exception e){
			logger.error("获取两个图片TopLeft之间的区域图片发生异常",e);
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
			region = screen.find(imagename).getROI();
			// 获取区域中的文本
			text = region.text();
			logger.info("成功指定图像进行图像识别文字");
		}catch(Exception e){
			logger.error("指定图像进行图像识别文字发生异常",e);
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
		}
		return app2;
	}
	
	
	/** 鼠标移动到图像  */
	public Pattern mouseMoveImage(String imagename, double imageTimeoutSecond){
		Pattern pattern = null;
		try{
			pattern = waitImage(imagename,imageTimeoutSecond);
			screen.mouseMove(pattern);
			logger.info("成功鼠标移动到图像");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要移动到的图像",e);
		}catch(Exception e){
			logger.error("鼠标移动到图像发生异常",e);
		}
		return pattern;
	}
	
	/** 鼠标移动到图像（带图像位置偏移量）  */
	public Pattern mouseMoveImage(String imagename, int X, int Y, double imageTimeoutSecond){
		Pattern pattern = null;
		try{
			pattern = imageOffset(imagename, X, Y, imageTimeoutSecond);
			screen.mouseMove(pattern);
			logger.info("成功鼠标移动到图像");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要移动到的图像",e);
		}catch(Exception e){
			logger.error("鼠标移动到图像发生异常",e);
		}
		return pattern;
	}
	
	/** 鼠标点击图像  */
	public void mouseClickImage(String imagename, double imageTimeoutSecond){
		Pattern pattern = null;
		try{
			pattern = waitImage(imagename,imageTimeoutSecond);
			screen.click(pattern);
			logger.info("成功鼠标点击图像");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要点击的图像",e);
		}catch(Exception e){
			logger.error("鼠标点击图像发生异常",e);
		}
	}
	
	/** 鼠标点击图像（带图像位置偏移量）  */
	public void mouseClickImage(String imagename, int X, int Y, double imageTimeoutSecond){
		Pattern pattern = null;
		try{
			pattern = imageOffset(imagename, X, Y, imageTimeoutSecond);
			screen.click(pattern);
			logger.info("成功鼠标点击图像");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要点击的图像",e);
		}catch(Exception e){
			logger.error("鼠标点击图像发生异常",e);
		}
	}
	
	/** 鼠标延迟点击图像 */
	public void mouseDelayClickImage(String imagename, double imageTimeoutSecond, int delayTimeMillisMax1000){
		try{
			//先鼠标移动到图像处
			mouseMoveImage(imagename,imageTimeoutSecond);
			//然后进行延迟点击
			screen.delayClick(delayTimeMillisMax1000);
			logger.info("成功鼠标延迟点击图像");
		}catch(Exception e){
			logger.error("鼠标延迟点击图像发生异常",e);
		}
	}
	
	/** 鼠标延迟点击图像（带图像位置偏移量） */
	public void mouseDelayClickImage(String imagename, int X, int Y, double imageTimeoutSecond, int delayTimeMillisMax1000){
		try{
			//先鼠标移动到图像处
			mouseMoveImage(imagename,X,Y,imageTimeoutSecond);
			//然后进行延迟点击
			screen.delayClick(delayTimeMillisMax1000);
			logger.info("成功鼠标延迟点击图像");
		}catch(Exception e){
			logger.error("鼠标延迟点击图像发生异常",e);
		}
	}
	
	/** 鼠标双击图像 */
	public void mouseDoublieClickImage(String imagename, double imageTimeoutSecond){
		try{
			waitImage(imagename,imageTimeoutSecond);
			screen.doubleClick(imagename);
			logger.info("成功鼠标双击图像");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要双击的图像",e);
		}catch(Exception e){
			logger.error("鼠标双击图像发生异常",e);
		}
	}
	
	/** 鼠标双击图像（带图像位置偏移量） */
	public void mouseDoublieClickImage(String imagename, int X, int Y, double imageTimeoutSecond){
		Pattern pattern = null;
		try{
			pattern = imageOffset(imagename, X, Y, imageTimeoutSecond);
			screen.doubleClick(pattern);
			logger.info("成功鼠标双击图像");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要双击的图像",e);
		}catch(Exception e){
			logger.error("鼠标双击图像发生异常",e);
		}
	}
	
	/** 鼠标右击图像 */
	public void mouseRightClickImage(String imagename, double imageTimeoutSecond){
		try{
			waitImage(imagename,imageTimeoutSecond);
			screen.rightClick(imagename);
			logger.info("成功鼠标右击击图像");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要右击的图像",e);
		}catch(Exception e){
			logger.error("鼠标右击图像发生异常",e);
		}
	}
	
	/** 鼠标右击图像（带图像位置偏移量） */
	public void mouseRightClickImage(String imagename, int X, int Y, double imageTimeoutSecond){
		Pattern pattern = null;
		try{
			pattern = imageOffset(imagename, X, Y, imageTimeoutSecond);
			screen.rightClick(pattern);
			logger.info("成功鼠标右击击图像");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要右击的图像",e);
		}catch(Exception e){
			logger.error("鼠标右击图像发生异常",e);
		}
	}
	
	/** 鼠标点击图像达到指定时间后再释放左键  */
	public void mouseLeftClickForTimeImage(String imagename, double imageTimeoutSecond, long millis){
		try{
			// 先鼠标移动到图像处
			mouseMoveImage(imagename, imageTimeoutSecond);
			// 然后按下鼠标左键不释放
			screen.mouseDown(Button.LEFT);
			// 等待指定时间后释放左键
			Thread.sleep(millis);
			screen.mouseUp();
			logger.info("成功鼠标点击图像达到指定时间后再释放左键");
		}catch(Exception e){
			logger.error("鼠标点击图像达到指定时间后再释放左键发生异常",e);
		}
	}
	
	/** 鼠标右击图像达到指定时间后再释放右键  */
	public void mouseRightClickForTimeImage(String imagename, double imageTimeoutSecond, long millis){
		try{
			// 先鼠标移动到图像处
			mouseMoveImage(imagename, imageTimeoutSecond);
			// 然后按下鼠标左键不释放
			screen.mouseDown(Button.RIGHT);
			// 等待指定时间后释放右键
			Thread.sleep(millis);
			screen.mouseUp();
			logger.info("成功鼠标右击图像达到指定时间后再释放右键");
		}catch(Exception e){
			logger.error("鼠标右击图像达到指定时间后再释放右键发生异常",e);
		}
	}

	/** 鼠标中击图像达到指定时间后再释放中键  */
	public void mouseMiddleClickForTimeImage(String imagename, double imageTimeoutSecond, long millis){
		try{
			// 先鼠标移动到图像处
			mouseMoveImage(imagename, imageTimeoutSecond);
			// 然后按下鼠标中键不释放
			screen.mouseDown(Button.MIDDLE);
			// 等待指定时间后释放中键
			Thread.sleep(millis);
			screen.mouseUp();
			logger.info("成功鼠标中击图像达到指定时间后再释放中键");
		}catch(Exception e){
			logger.error("鼠标中击图像达到指定时间后再释放中键发生异常",e);
		}
	}
	
	/** 鼠标在滚动条图像中轮向上滚动指定次数  */
	public void mouseWheelUpForTimeImage(String imagename, double imageTimeoutSecond, int numberOfRolls){
		Pattern pattern = null;
		try{
			pattern = waitImage(imagename, imageTimeoutSecond);
			screen.wheel(pattern, Button.WHEEL_UP, numberOfRolls);
			logger.info("成功鼠标在滚动条图像中轮向上滚动指定次数");
		}catch(Exception e){
			logger.error("鼠标在滚动条图像中轮向上滚动指定次数发生异常",e);
		}
	}
	
	
	/** 鼠标在滚动条图像中轮向下滚动指定次数  */
	public void mouseWheelDownForTimeImage(String imagename, double imageTimeoutSecond, int numberOfRolls){
		Pattern pattern = null;
		try{
			pattern = waitImage(imagename, imageTimeoutSecond);
			screen.wheel(pattern, Button.WHEEL_DOWN, numberOfRolls);
			logger.info("成功鼠标在滚动条图像中轮向下滚动指定次数");
		}catch(Exception e){
			logger.error("鼠标在滚动条图像中轮向下滚动指定次数发生异常",e);
		}
	}
	
	/** 鼠标悬停在图像 */
	public void mouseHoverImage(String imagename, double imageTimeoutSecond){
		try{
			waitImage(imagename,imageTimeoutSecond);
			screen.hover(imagename);
			logger.info("成功鼠标悬停在图像");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要悬停在的图像",e);
		}catch(Exception e){
			logger.error("鼠标悬停在图像发生异常",e);
		}
	}
	
	/** 鼠标悬停在图像（带图像位置偏移量） */
	public void mouseHoverImage(String imagename, int X, int Y, double imageTimeoutSecond){
		Pattern pattern = null;
		try{
			pattern = imageOffset(imagename, X, Y, imageTimeoutSecond);
			screen.hover(pattern);
			logger.info("成功鼠标悬停在图像");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要悬停在的图像",e);
		}catch(Exception e){
			logger.error("鼠标悬停在图像发生异常",e);
		}
	}
	
	/** 鼠标拖拽图像 */
	public void mouseDragDropImages(String sourceImage, String targetImage, double imageTimeoutSecond){
		try{
			waitImage(sourceImage,imageTimeoutSecond);
			waitImage(targetImage,imageTimeoutSecond);
			screen.dragDrop(sourceImage, targetImage);
			logger.info("成功鼠标拖拽图像");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要拖拽的图像",e);
		}catch(Exception e){
			logger.error("鼠标拖拽图像发生异常",e);
		}
	}
	
	/** 鼠标拖拽图像（带图像位置偏移量） */
	public void mouseDragDropImages(String sourceImage, String targetImage, int X1, int Y1, int X2, int Y2, double imageTimeoutSecond){
		Pattern pattern1 = null;
		Pattern pattern2 = null;
		try{
			pattern1 = imageOffset(sourceImage, X1, Y1, imageTimeoutSecond);
			pattern2 = imageOffset(targetImage, X2, Y2, imageTimeoutSecond);
			screen.dragDrop(pattern1, pattern2);
			logger.info("成功鼠标拖拽图像");
		}catch(FindFailed e){
			logger.error("查找不到鼠标要拖拽的图像",e);
		}catch(Exception e){
			logger.error("鼠标拖拽图像发生异常",e);
		}
	}
	
	/** 键盘在图像中输入文字（图像前提是可输入框） */
	public void keyboardTypeTextImages(String imagename, String text, double imageTimeoutSecond){
		try{
			waitImage(imagename,imageTimeoutSecond);
			screen.type(imagename,text);
			logger.info("成功用键盘在图像中输入文字");
		}catch(Exception e){
			logger.error("键盘在图像中输入文字发生异常",e);
		}
	}
	
	/** 键盘在图像中粘贴文字（图像前提是可输入框） */
	public void keyboardPasteTextImages(String imagename, String text, double imageTimeoutSecond){
		try{
			waitImage(imagename,imageTimeoutSecond);
			screen.paste(text);
			logger.info("成功用键盘在图像中粘贴文字");
		}catch(Exception e){
			logger.error("键盘在图像中粘贴文字发生异常",e);
		}
	}
	
	/** 键盘在图像中输入CTRL+KEY组合按键,KEY可以是键盘任意按键 */
	public void keyboardTypeCtrlPlusKeyImages(String imagename, String key, double imageTimeoutSecond){
		try{
			waitImage(imagename,imageTimeoutSecond);
			screen.type(imagename, key, KeyModifier.CTRL);
			logger.info("成功用键盘在图像中输入CTRL+KEY组合按键");
		}catch(Exception e){
			logger.error("键盘在图像中输入CTRL+KEY组合按键发生异常",e);
		}
	}
	
	/** 键盘在图像中输入单个KEY按键,KEY是org.sikuli.script.Key.xxx:String */
	public void keyboardTypeKeyImages(String imagename, String key, double imageTimeoutSecond){
		try{
			waitImage(imagename,imageTimeoutSecond);
			screen.type(imagename,key);
			logger.info("成功用键盘在图像中输入单个KEY按键");
		}catch(Exception e){
			logger.error("键盘在图像中输入单个KEY按键发生异常",e);
		}
	}
	
	/** 键盘在图像中输入任意KEY1+KEY2组合按键，KEY1是org.sikuli.script.Key.xxx:String，KEY2是org.sikuli.script.KeyModifier.xxx:String */
	public void keyboardTypeTwoKeysImages(String imagename, String key, String keyModifier, double imageTimeoutSecond){
		try{
			waitImage(imagename,imageTimeoutSecond);
			screen.type(imagename, key, keyModifier);
			logger.info("成功用键盘在图像中输入任意KEY1+KEY2组合按键");
		}catch(Exception e){
			logger.error("键盘在图像中输入任意KEY1+KEY2组合按键发生异常",e);
		}
	}
	
	/** 按着某个键一定时间后再释放按键，key可以是org.sikuli.script.Key.xxx:String或者是org.sikuli.script.KeyModifier.xxx:String */
	public void keyboardPressKeyForTime(String key, long millis){
		try{
			// 按下某键不放
			screen.keyDown(key);
			// 按住等多少毫秒
			Thread.sleep(millis);
			// 时间到就释放按键
			screen.keyUp();
			logger.info("成功按着某个键一定时间后再释放按键");
		}catch(Exception e){
			logger.error("按着某个键一定时间后再释放按键发生异常",e);
		}
	}
	
	/** 按下KEY1+KEY2+KEY3三键组合，key可以是org.sikuli.script.Key.xxx:String或者是org.sikuli.script.KeyModifier.xxx:String */
	public void keyboardPressThreeKeys(String key1,String key2,String key3, long millis){
		try{
			// 连续按下三个键键不放
			screen.keyDown(key1);
			screen.keyDown(key2);
			screen.keyDown(key3);
			// 按住等多少毫秒
			Thread.sleep(millis);
			// 时间到就释放所有按键
			screen.keyUp();
			logger.info("成功按下KEY1+KEY2+KEY3三键组合");
		}catch(Exception e){
			logger.error("按下KEY1+KEY2+KEY3三键组合发生异常",e);
		}
	}

}
