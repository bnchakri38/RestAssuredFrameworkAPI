package com.qa.api.gorest.tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateUserTest extends BaseTest {
	
	String tokenId;
	
	@BeforeClass
	public void setUpToken() {
		tokenId = "19f98d9b819ddd2907f1a3e1033c44cae4b50f11f1967b9cd3b6435a7d906e13";
		ConfigManager.set("bearertoken", tokenId);
	}
	
	@Test
	public void createAUserTestWithPOJO() {
		
		// 1. Direct Approach
//		User user = new User("Narayana", "nb001@gmail.com", "male", "active");
		
		// 2. Random EmailID Approach 
		User user = new User(null, "Chakri", StringUtils.getRandomEmailId(), "male", "active");
		
		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		
		Assert.assertTrue(response.statusLine().contains("Created"));
		Assert.assertNotNull(response.jsonPath().getString("id"));
	}
	
	@Test
	public void createAUserTestWithJSONString() {
		
		String userJson = "{\n"
				+ "  \"name\": \"Narayana\",\n"
				+ "  \"email\": \"nb002@gmail.com\",\n"
				+ "  \"gender\": \"male\",\n"
				+ "  \"status\": \"active\"\n"
				+ "}";
		
		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, userJson, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		
		Assert.assertTrue(response.statusLine().contains("Created"));
		Assert.assertNotNull(response.jsonPath().getString("id"));
	}
	
	@Test
	public void createAUserTestWithJSONStringAndRandomEmail() {
		
		String emailId = StringUtils.getRandomEmailId();
		
		String userJson = "{\n"
				+ "  \"name\": \"Narayana\",\n"
				+ "  \"email\": \""+ emailId +"\",\n"
				+ "  \"gender\": \"male\",\n"
				+ "  \"status\": \"active\"\n"
				+ "}";
		
		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, userJson, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		
		Assert.assertTrue(response.statusLine().contains("Created"));
		Assert.assertNotNull(response.jsonPath().getString("id"));
	}
	
	@Test
	public void createAUserTestWithFileObj() {
		
		File userFile = new File(".\\src\\test\\resources\\jsons\\user.json");
		
		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, userFile, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		
		Assert.assertTrue(response.statusLine().contains("Created"));
		Assert.assertNotNull(response.jsonPath().getString("id"));
	}
	
	@Test
	public void createAUserTestWithFileObjAndRandomEmail() {
		
		String tokenId = "19f98d9b819ddd2907f1a3e1033c44cae4b50f11f1967b9cd3b6435a7d906e13";
		ConfigManager.set("bearertoken", tokenId);
		
		String emailId = StringUtils.getRandomEmailId();
		String rawJson;
		
		try {
			rawJson = new String(Files.readAllBytes(Paths.get("./src/test/resources/jsons/users.json")));
			String updatedJson = rawJson.replace("{{email}}", emailId);
			
			Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, updatedJson, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
			
			Assert.assertTrue(response.statusLine().contains("Created"));
			Assert.assertNotNull(response.jsonPath().getString("id"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
