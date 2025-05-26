package com.qa.api.products.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.Product;
import com.qa.api.utils.JsonUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductsAPITest extends BaseTest {

	@Test
	public void getProductsTest() {

		Response response = restClient.get(BASE_URL_PRODUCTS, PRODUCTS_ENDPOINT, null, null, AuthType.NO_AUTH,
				ContentType.ANY);
		Assert.assertEquals(response.statusCode(), 200);

		Product[] product = JsonUtils.deserialize(response, Product[].class);

		for (Product e : product) {
			System.out.println("ID: " + e.getId());
			System.out.println("Title: " + e.getTitle());
			System.out.println("Price: " + e.getPrice());
			System.out.println("Description: " + e.getDescription());
			System.out.println("Images: " + e.getImage());
			System.out.println("Categoty: " + e.getCategory());

			System.out.println("Rating-Rate: " + e.getRating().getRate());
			System.out.println("Rating-Count: " + e.getRating().getCount());

		}
	}

}
