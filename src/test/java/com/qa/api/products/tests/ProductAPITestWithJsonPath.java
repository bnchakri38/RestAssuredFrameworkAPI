package com.qa.api.products.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.JsonPathValidatorUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductAPITestWithJsonPath extends BaseTest {

	@Test
	public void getProductTest() {

		Response response = restClient.get(BASE_URL_PRODUCTS, PRODUCTS_ENDPOINT, null, null, AuthType.NO_AUTH,
				ContentType.ANY);
		Assert.assertEquals(response.statusCode(), 200);

		String price = "$[?(@.price > 50)].price";
		String id = "$[?(@.price > 50)].id";
		String rate = "$[?(@.price > 50)].rating.rate";
		String count = "$[?(@.price > 50)].rating.count";

		List<Number> prices = JsonPathValidatorUtil.readlist(response, price);
		System.out.println(prices);
		
		List<Number> ids = JsonPathValidatorUtil.readlist(response, id);
		System.out.println(ids);
		
		List<Number> rates = JsonPathValidatorUtil.readlist(response, rate);
		System.out.println(rates);
		
		List<Number> counts = JsonPathValidatorUtil.readlist(response, count);
		System.out.println(counts);
		
		String idTitle = "$.[*].['id', 'title']";
		List<Map<String, Object>> idTitleList = JsonPathValidatorUtil.readListOfMaps (response, idTitle);
		System.out.println(idTitleList);
		
		String idTitleCategory = "$.[*].['id', 'title', 'category']";
		List<Map<String, Object>> idTitleCategoryList = JsonPathValidatorUtil.readListOfMaps (response, idTitleCategory);
		System.out.println(idTitleCategoryList);
		
		String jeweleryIdTitle = "$[?(@.category == 'jewelery')].['id', 'title']";
		List<Map<String, Object>> jeweleryIdTitleList = JsonPathValidatorUtil.readListOfMaps (response, jeweleryIdTitle);
		System.out.println(jeweleryIdTitleList);
		
		Double priceValue = JsonPathValidatorUtil.read(response, "min($[*].price)");
		System.out.println("Min Price: "+priceValue);
		
	}

}
