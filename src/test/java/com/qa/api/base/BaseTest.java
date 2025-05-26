package com.qa.api.base;

import org.testng.annotations.BeforeTest;

import com.qa.api.client.RestClient;

public class BaseTest {
	
	protected RestClient restClient;

	/******************************** API Base URLs ********************************/
	protected final static String BASE_URL_GOREST = "https://gorest.co.in";
	protected final static String BASE_URL_CONTACTS = "https://thinking-tester-contact-list.herokuapp.com";
	protected final static String BASE_URL_REQRES = "https://reqres.in";
	protected final static String BASE_URL_BASIC_AUTH = "https://the-internet.herokuapp.com";
	protected final static String BASE_URL_PRODUCTS = "https://fakestoreapi.com";
	
	
	
	/******************************** API EndPoints ********************************/
	protected final static String GOREST_USERS_ENDPOINT = "/public/v2/users";
	protected final static String CONTACTS_LOGIN_ENDPOINT = "/users/login";
	protected final static String CONTACTS_ENDPOINT = "/contacts";
	protected final static String REQRES_ENDPOINT = "/api/users";
	protected final static String BASIC_AUTH_ENDPOINT = "/basic_auth";
	protected final static String PRODUCTS_ENDPOINT = "/products";
	
	@BeforeTest
	public void setup() {
		restClient = new RestClient();
	}
}
