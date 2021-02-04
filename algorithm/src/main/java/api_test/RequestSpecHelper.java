package api_test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.builder.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.*;

public class RequestSpecHelper {

	public static RequestSpecification request = null;

	public RequestSpecHelper() {

		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
		requestSpecBuilder.setBaseUri("http://localhost:3000/");
		requestSpecBuilder.setContentType(ContentType.JSON);
		RequestSpecification requestSpec = requestSpecBuilder.build();
		request = RestAssured.given().spec(requestSpec);
	}

	public static ResponseOptions<Response> GetRequest(String url) {

		try {
			return request.get(new URI(url));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ResponseOptions<Response> GetRequest_01(String url) {

		return request.get(url);
	}

	public static void GetRequestWithPathParam(String url, Map<String, String> pathParam) {

		request.pathParams(pathParam);

		try {
			request.get(new URI(url));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ResponseOptions<Response> PostRequest(String url, Map<String, String> pathParam,
			Map<String, String> body) {
		request.pathParams(pathParam);
		request.body(body);
		return request.post(url);

	}

	public static ResponseOptions<Response> PutRequest(String url, Map<String, String> pathParam,
			Map<String, String> body) {
		request.pathParams(pathParam);
		request.body(body);
		return request.put(url);

	}

	public static ResponseOptions<Response> DeleteRequest(String url, Map<String, String> pathParam) {
		request.pathParams(pathParam);
		return request.delete(url);
	}

}
