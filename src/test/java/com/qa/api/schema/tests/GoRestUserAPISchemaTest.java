package com.qa.api.schema.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.SchemaValidator;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GoRestUserAPISchemaTest extends BaseTest {
	
	String tokenId;
	
	@BeforeClass
	public void setUpToken() {
		tokenId = "19f98d9b819ddd2907f1a3e1033c44cae4b50f11f1967b9cd3b6435a7d906e13";
		ConfigManager.set("bearertoken", tokenId);
	}
	
	@Test
	public void getUsersAPISchemaTest() {
		
		Response response = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.ANY);
		
		Assert.assertTrue(SchemaValidator.validateSchema(response, "schema/getuserschema.json"));
	}
	
	@Test
	public void createUserAPISchemaTest() {
		
		User user = User.builder()
							.name("Testing")
							.email(StringUtils.getRandomEmailId())
							.gender("Male")
							.status("active")
								.build();
		
		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		
		Assert.assertTrue(SchemaValidator.validateSchema(response, "schema/createuserschema.json"));
	}
}
