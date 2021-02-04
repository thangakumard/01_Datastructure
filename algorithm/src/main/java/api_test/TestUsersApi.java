package api_test;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;


public class TestUsersApi {

	
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
}
