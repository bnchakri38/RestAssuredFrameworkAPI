package com.qa.api.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

	private static Properties properties = new Properties();

	static {
		
		// maven clean install -Denv=dev/qa/uat/stage/prod
		// maven clean install -> if env is not given, then run the testcases on the qa env by default
		// env -> environment variable(System)
		
		String envName = System.getProperty("env", "prod");
		System.out.println("Running tests on env: "+ envName);
		String fileName = "config_" + envName + ".properties"; // config_qa.properties
		System.out.println(fileName);
		
		// Reflection concept
		InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(fileName);

		if (input != null) {
			try {
				properties.load(input);
				System.out.println("Properties==> "+ properties);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String get(String key) {
		return properties.getProperty(key);
	}

	public static void set(String key, String value) {
		properties.setProperty(key, value);
	}

}
