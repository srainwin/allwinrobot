package com.demo.cases.login;

import org.sikuli.script.ImagePath;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.testng.annotations.Test;
public class SikuliDemo3 {

	@Test
	public void containText() throws Exception {
		ImagePath.add("D:\\snc\\workspace2\\sikuli\\image");
		Screen s = new Screen();
		System.out.println(isContainText(s,"QQ截图20190627112154.png","QQ截图20190628172006.png"));
		System.out.println("123");
	}

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
}


































