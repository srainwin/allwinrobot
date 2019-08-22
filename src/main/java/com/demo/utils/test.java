package com.demo.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.jayway.jsonpath.JsonPath;

public class test {

	public static void main(String[] args) throws IOException {
		File jsonfile = new File(System.getProperty("user.dir")+"/src/main/java/com/demo/pages/HomePage.json");
		List<String> ll = JsonPath.read(jsonfile, "$..overviewUnreadMailTab[1]");
		System.out.println(ll.get(0));
	}

}
