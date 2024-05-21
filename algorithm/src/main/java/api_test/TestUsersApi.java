package api_test;

import cucumber.runtime.junit.Assertions;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;


public class TestUsersApi {

	/***
	 * Packages for prcessing JSON
	 * 	1.Gson
	 * 	2.Jackson
	 * 	3.Json (org.json)
	 * 	4.JSON.Simple
	 */
	
	public static ResponseOptions<Response> response;
	
	@BeforeTest
	public void initialize() {
		RequestSpecHelper restAssuredHelper = new RequestSpecHelper();
	}
	
	@Test
	public void testGetUsers() {
		response = RequestSpecHelper.GetRequest_01("/users/");
		User[] users = response.getBody().as(User[].class);
		assertThat(users[0].firstName, equalTo("John"));
	}

	@Test
	public void testPostUsers() {
		JSONObject requestHeaders = new JSONObject();
		JSONObject requestBody = new JSONObject();
		requestBody.put("user_id", "1");
		requestBody.put("user_name", "thanga");

		RequestSpecHelper.PostRequest("/users/",requestHeaders,requestBody);
	}
}
