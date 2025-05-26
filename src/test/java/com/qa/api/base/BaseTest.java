package com.qa.api.base;

import org.testng.annotations.BeforeTest;

import com.qa.api.client.RestClient;

public class BaseTest {
	
	protected RestClient restClient;

	/******************************** API Base URLs ********************************/
	protected final static String BASE_URL_GOREST = "https://gorest.co.in";
	protected final static String BASE_URL_CONTACTS = "https://thinking-tester-contact-list.herokuapp.com";
	
	
	
	/******************************** API EndPoints ********************************/
	protected final static String GOREST_USERS_ENDPOINT = "/public/v2/users";
	protected final static String CONTACTS_LOGIN_ENDPOINT = "/users/login";
	protected final static String CONTACTS_ENDPOINT = "/contacts";
	
	
	@BeforeTest
	public void setup() {
		restClient = new RestClient();
	}
}
