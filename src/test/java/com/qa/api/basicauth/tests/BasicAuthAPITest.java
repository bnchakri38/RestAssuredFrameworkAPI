package com.qa.api.basicauth.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BasicAuthAPITest extends BaseTest {
	
	@BeforeClass
	public void setUpBasicAuth() {
		ConfigManager.set("basicauthusername", "admin");
		ConfigManager.set("basicauthpassword", "admin");
	}
	
	@Test
	public void basicAUthTest() {
		
		Response response = restClient.get(BASE_URL_BASIC_AUTH, BASIC_AUTH_ENDPOINT, null, null, AuthType.BASIC_AUTH, ContentType.ANY);
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertTrue(response.getBody().asString().contains("Congratulations! You must have the proper credentials."));
		
	}

}
