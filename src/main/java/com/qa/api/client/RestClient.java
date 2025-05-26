package com.qa.api.client;

import java.util.Map;

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
	

	public RequestSpecification setupRequest(String baseUrl, AuthType authType, ContentType contentType) {

		RequestSpecification request = RestAssured.given().log().all()
													.baseUri(baseUrl)
													.contentType(contentType)
													.accept(contentType);

		switch (authType) {
		case BEARER_TOKEN:
			request.header("Authorization", "Bearer " + ConfigManager.get("bearertoken"));
			break;
		case OAUTH2:
			request.header("Authorization", "Bearer " + "oauth2 token");
			break;
		case BASIC_AUTH:
			request.header("Authorization", "Basic " + "==basicauth token==");
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

	public void applyParams(RequestSpecification request, Map<String, String> queryParams, Map<String, String> pathParams) {

		if (queryParams != null) {
			request.queryParams(queryParams);
		}
		
		if (pathParams != null) {
			request.pathParams(pathParams);
		}
	}
	
	// CRUD Operations:
	
	// Get:
	
	public Response get(String baseUrl, String endPoint, 
					Map<String, String> queryParams, 
					Map<String, String> pathParams,
					AuthType authType,
					ContentType contentType) {
		RequestSpecification request = setupRequest(baseUrl, authType, contentType);
		applyParams(request, queryParams, pathParams);
		Response response = request.get(endPoint).then().spec(responseSpec200or404).extract().response();
		
		response.prettyPrint();
		
		return response;
								
	}

}
