package com.qa.api.contacts.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.ContactCredentials;
import com.qa.api.pojo.Contacts;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ContactsAPITest extends BaseTest{
	
	private String tokenId;
	
	@BeforeMethod
	public void getToken() {
		
		ContactCredentials credentials = ContactCredentials.builder()
																.email("naveenanimation20@gmail.com")
																.password("test@123")
																.build();
		
		Response response = restClient.post(BASE_URL_CONTACTS, CONTACTS_LOGIN_ENDPOINT, credentials, null, null, AuthType.NO_AUTH, ContentType.JSON);
		Assert.assertEquals(response.statusCode(), 200);
		
		tokenId = response.jsonPath().getString("token");
		System.out.println("Contacts Login JWT Token: "+ tokenId);
		
		ConfigManager.set("bearertoken", tokenId);
	}
	
	@Test
	public void getAllContactsTest() {
				
		Response response = restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.statusCode(), 200);
		
	}
	
	@Test
	public void createAContactTest() {
		
		Contacts contactsJson = Contacts.builder()
											.firstName("Bunny")
											.lastName("Arjun")
											.birthdate("1999-01-10")
											.email("bunny.arjun@gmail.com")
											.phone("9990001122")
											.street1("1st, Main Street")
											.street2("BTM Layout")
											.city("Bangalore")
											.stateProvince("Karnataka")
											.postalCode("560037")
											.country("India")
												.build();
		
		Response responsePost = restClient.post(BASE_URL_CONTACTS, CONTACTS_ENDPOINT, contactsJson, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		
		Assert.assertEquals(responsePost.statusCode(), 201);
		Assert.assertEquals(responsePost.jsonPath().getString("firstName"), "Bunny");
		Assert.assertTrue(responsePost.statusLine().contains("Created"));
		Assert.assertNotNull(responsePost.jsonPath().getString("_id"));
		
		String contactId = responsePost.jsonPath().getString("_id");
		System.out.println("Contact ID: "+contactId);
		
		Response responseGet = restClient.get(BASE_URL_CONTACTS, CONTACTS_ENDPOINT+"/"+contactId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responseGet.statusCode(), 200);
		Assert.assertTrue(responseGet.statusLine().contains("OK"));
		Assert.assertEquals(responseGet.jsonPath().getString("_id"), contactId);
	}
	
}