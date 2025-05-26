package com.qa.api.gorest.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.JsonUtils;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetAUserWithDeserializationTest extends BaseTest {
	
String tokenId;
	
	@BeforeClass
	public void setUpToken() {
		tokenId = "19f98d9b819ddd2907f1a3e1033c44cae4b50f11f1967b9cd3b6435a7d906e13";
		ConfigManager.set("bearertoken", tokenId);
	}
	
	@Test
	public void createAUserTestWithPOJO() {
		 
		User user = new User(null, "Test", StringUtils.getRandomEmailId(), "male", "active");
		
		Response responsePost = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(responsePost.statusLine().contains("Created"));
		Assert.assertEquals(responsePost.jsonPath().getString("name"), "Test");
		Assert.assertNotNull(responsePost.jsonPath().getString("id"));
		
		String userId = responsePost.jsonPath().getString("id");
		
		Response responseGet = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(responseGet.statusLine().contains("OK"));
		
		User userResponse = JsonUtils.deserialize(responseGet, User.class);
		Assert.assertEquals(userResponse.getName(), user.getName());		
		
	}
	
	@Test
	public void getAllUserTest() {
		
		Response responseGet = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(responseGet.statusLine().contains("OK"));
		
		List<String> userName = responseGet.jsonPath().getList("name");
		
		int index = 0;
		User[] userResponse = JsonUtils.deserialize(responseGet, User[].class);
		for(User e : userResponse) {
			Assert.assertEquals(e.getName(), userName.get(index));
			index++;
		}
				
	}

}
