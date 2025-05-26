package com.qa.api.utils;

public class StringUtils {
	
	public static String getRandomEmailId() {
		String emailId = "test"+System.currentTimeMillis()+"@gmail.com";
		return emailId;
	}

}
