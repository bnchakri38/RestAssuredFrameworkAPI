package com.qa.api.gorest.tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateUserTestWithDataProvider extends BaseTest {
	
	String tokenId;
	
	@BeforeClass
	public void setUpToken() {
		tokenId = "19f98d9b819ddd2907f1a3e1033c44cae4b50f11f1967b9cd3b6435a7d906e13";
		ConfigManager.set("bearertoken", tokenId);
	}
	
	@DataProvider
	public Object[][] getUserData() {
		return new Object[][] {
			{"Priyanka", "female", "active"},
			{"Harika", "female", "active"},
			{"Ranjit", "male", "inactive"},
			{"Vinay", "male", "active"}
		};
	}
	
	@Test(dataProvider = "getUserData")
	public void createAUserTestWithPOJO(String name, String gender, String status) {
		
		User user = new User(null, name, StringUtils.getRandomEmailId(), gender, status);
		
		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.jsonPath().getString("name"), name);
		Assert.assertEquals(response.jsonPath().getString("gender"), gender);
		Assert.assertEquals(response.jsonPath().getString("status"), status);
		
		Assert.assertTrue(response.statusLine().contains("Created"));
		Assert.assertNotNull(response.jsonPath().getString("id"));
		ChainTestListener.log("Newly created User Id:"+ response.jsonPath().getString("id"));
	}
	
}
