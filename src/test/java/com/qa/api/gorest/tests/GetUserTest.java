package com.qa.api.gorest.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


@Epic("Epic 100: GoRest - Get User API Feature")
@Story("US 100: Feature GoRestAPI - get user api")
public class GetUserTest extends BaseTest {
	
	@Description("Getting all the Users...")
	@Owner("Chakri")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void getAllUsersTest() {
		ChainTestListener.log("Get All Users API Test");
		Response response = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(response.getStatusLine().contains("OK"));
	}
	
	@Description("Getting all the Users...")
	@Owner("Chakri")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void getAllUsersWithQueryParamsTest() {
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("name", "chakri");
		queryParams.put("status", "active");
		
		Response response = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, queryParams, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(response.statusLine().contains("OK"));
	}
	
	@Description("Getting the User...")
	@Owner("Chakri")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void getAUserWithStringSingleUserIDTest() {
		String userId = "7795599";
		
		Response response = restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(response.statusLine().contains("OK"));
		Assert.assertEquals(response.jsonPath().getString("id"), userId);
	}

}
