package com.qa.api.gorest.tests;

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

public class DeleteUserTest extends BaseTest {
	
	String tokenId;
	
	@BeforeClass
	public void setUpToken() {
		tokenId = "19f98d9b819ddd2907f1a3e1033c44cae4b50f11f1967b9cd3b6435a7d906e13";
		ConfigManager.set("bearertoken", tokenId);
	}
	
	@Test
	public void deleteAUserTest() {
	
		String emailId = StringUtils.getRandomEmailId();
		
	// Random EmailID Approach with Lombok 
			User user = User.builder()
								.name("Chakri")
								.email(emailId)
								.gender("male")
								.status("active")
									.build();
			
			// 1. Post:
			Response responsePost = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);

			Assert.assertEquals(responsePost.jsonPath().getString("name"), "Chakri");
			Assert.assertTrue(responsePost.statusLine().contains("Created"));
			Assert.assertNotNull(responsePost.jsonPath().getString("id"));
		
//				// Fetch the ID:
				String userId = responsePost.jsonPath().getString("id");
				System.out.println("Created User ID: "+ userId);
			
			// 2. Get the user using the above created user userId 
			Response responseGet = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
			
			Assert.assertTrue(responseGet.statusLine().contains("OK"));
			Assert.assertEquals(responseGet.jsonPath().getString("id"), userId);
			
			// 3. Delete the user using the same userId
			Response responsePut = restClient.delete(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
			Assert.assertTrue(responsePut.statusLine().contains("No Content"));

			// 4. Get the updated user name and status using the same userId
			responseGet = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
			Assert.assertEquals(responseGet.statusCode(), 404);
			Assert.assertTrue(responseGet.statusLine().contains("Not Found"));
			Assert.assertEquals(responseGet.jsonPath().getString("message"), "Resource not found");
			
	}

}
