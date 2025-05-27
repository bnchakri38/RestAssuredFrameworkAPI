package com.qa.api.client;

import java.io.File;
import java.util.Base64;
import java.util.Map;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.constants.AuthType;
import com.qa.api.exceptions.APIExceptions;
import com.qa.api.manager.ConfigManager;

import static io.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RestClient {
	
	// Define Response Specs:
	private ResponseSpecification responseSpec200 = expect().statusCode(200);
	private ResponseSpecification responseSpec201 = expect().statusCode(201);
	private ResponseSpecification responseSpec204 = expect().statusCode(204);
	private ResponseSpecification responseSpec400 = expect().statusCode(400);
	private ResponseSpecification responseSpec200or201 = expect().statusCode(anyOf(equalTo(200), equalTo(201)));
	private ResponseSpecification responseSpec200or404 = expect().statusCode(anyOf(equalTo(200), equalTo(404)));
	

	private RequestSpecification setupRequest(String baseUrl, AuthType authType, ContentType contentType) {
		
		ChainTestListener.log("API Base URL: "+ baseUrl);
		ChainTestListener.log("AuthType: "+ authType);
		ChainTestListener.log("ContentType: "+ contentType);
		
		RequestSpecification request = RestAssured.given().log().all()
													.baseUri(baseUrl)
													.contentType(contentType)
													.accept(contentType);

		switch (authType) {
		case BEARER_TOKEN:
			request.header("Authorization", "Bearer " + ConfigManager.get("bearertoken"));
			break;
		case BASIC_AUTH:
			request.header("Authorization", "Basic "+ generateBasicAuthToken());
			break;
		case API_KEY:
			request.header("x-api-key", "api key");
			break;
		case NO_AUTH:
			System.out.println("Authorization is not required...");
			break;
		default:
			System.out.println("This Authorization is not supported. So, please pass the right AuthType...");
			throw new APIExceptions("===INVALID_AUTH===");
		}

		return request;
	}
	
	private String generateBasicAuthToken() {
		String credentials = ConfigManager.get("basicauthusername") + ":" + ConfigManager.get("basicauthpassword");
		//admin:admin --> "YWRtaW46YWRtaW4=" (base64 encoded value)
		return Base64.getEncoder().encodeToString(credentials.getBytes());
	}

	private void applyParams(RequestSpecification request, Map<String, String> queryParams, Map<String, String> pathParams) {
		ChainTestListener.log("Query Params:"+ queryParams);
		if (queryParams != null) {
			request.queryParams(queryParams);
		}
		
		ChainTestListener.log("Path Params:"+ pathParams);
		if (pathParams != null) {
			request.pathParams(pathParams);
		}
	}
	
	// CRUD Operations:
	
	// Get:
	
	/**
	 * This method is used to call GET APIs
	 * @param baseUrl
	 * @param endPoint
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return it returns the GET API call response
	 */
	public Response get(String baseUrl, String endPoint, 
					Map<String, String> queryParams, 
					Map<String, String> pathParams,
					AuthType authType,
					ContentType contentType) {
		RequestSpecification request = setupRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		Response response = request.get(endPoint).then().spec(responseSpec200or404).extract().response();
		
		System.out.println("Response Status Code: "+ response.statusCode());
		response.prettyPrint();		
		
		return response;
	}
	
	// Post: 
		// <T> indicates ANY Type (Body)
	
	public <T>Response post(String baseUrl, String endPoint, T body,
			Map<String, String> queryParams, 
			Map<String, String> pathParams,
			AuthType authType,
			ContentType contentType) {
	
		RequestSpecification request = setupRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		
		Response response = request.body(body)
										.post(endPoint)
											.then()
												.spec(responseSpec200or201)
												.extract()
													.response();
		
		System.out.println("Response Status Code: "+ response.statusCode());		
		response.prettyPrint();

		return response;
	}
	
	public Response post(String baseUrl, String endPoint, File filePath,
			Map<String, String> queryParams, 
			Map<String, String> pathParams,
			AuthType authType,
			ContentType contentType) {
	
		RequestSpecification request = setupRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		
		Response response = request.body(filePath)
										.post(endPoint)
											.then()
												.spec(responseSpec200or201)
												.extract()
													.response();
		
		System.out.println("Response Status Code: "+ response.statusCode());		
		response.prettyPrint();
		
		return response;
	}
	
	public Response post(String baseUrl, String endPoint,
							String clientId, String clientSecret, String grantType, 
							ContentType contentType) {
		
		Response response = RestAssured.given()
											.contentType(contentType)
											.formParam("grant_type", grantType)
											.formParam("client_id", clientId)
											.formParam("client_secret", clientSecret)
											.when()
												.post(baseUrl+endPoint);
				
		System.out.println("Response Status Code: "+ response.statusCode());	
		response.prettyPrint();
		return response;
	}
	
	public <T>Response put(String baseUrl, String endPoint, T body,
			Map<String, String> queryParams, 
			Map<String, String> pathParams,
			AuthType authType,
			ContentType contentType) {
	
		RequestSpecification request = setupRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		
		Response response = request.body(body)
										.put(endPoint)
											.then()
												.spec(responseSpec200)
												.extract()
													.response();
		
		response.prettyPrint();
		System.out.println("Response Status Code: "+ response.statusCode());
		return response;
	}
	
	public <T>Response patch(String baseUrl, String endPoint, T body,
			Map<String, String> queryParams, 
			Map<String, String> pathParams,
			AuthType authType,
			ContentType contentType) {
	
		RequestSpecification request = setupRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		
		Response response = request.body(body)
										.post(endPoint)
											.then()
												.spec(responseSpec200)
												.extract()
													.response();
		
		response.prettyPrint();
		System.out.println("Response Status Code: "+ response.statusCode());
		return response;
	}
	
	public Response delete(String baseUrl, String endPoint,
			Map<String, String> queryParams, 
			Map<String, String> pathParams,
			AuthType authType,
			ContentType contentType) {
	
		RequestSpecification request = setupRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		
		Response response = request.delete(endPoint)
											.then()
												.spec(responseSpec204)
												.extract()
													.response();
		
		response.prettyPrint();
		System.out.println("Response Status Code: "+ response.statusCode());
		return response;
	}

}
