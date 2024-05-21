package api_test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class RestAssuredSingleton {

    private static RestAssuredSingleton instance;
    private RequestSpecification requestSpecification;

    // Private constructor to prevent instantiation
    private RestAssuredSingleton() {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri("https://jsonplaceholder.typicode.com");
        builder.addHeader("Content-Type", "application/json");
        builder.log(io.restassured.filter.log.LogDetail.ALL);
        requestSpecification = builder.build();
    }

    // Public method to provide access to the singleton instance
    public static RestAssuredSingleton getInstance() {
        if (instance == null) {
            synchronized (RestAssuredSingleton.class) {
                if (instance == null) {
                    instance = new RestAssuredSingleton();
                }
            }
        }
        return instance;
    }

    // Method to get the configured RequestSpecification
    public RequestSpecification getRequestSpecification() {
        return requestSpecification;
    }

    // Static method for GET request
    public static Response get(String url) {
        return given()
                .spec(getInstance().getRequestSpecification())
                .when()
                .get(url)
                .then()
                .extract()
                .response();
    }

    // Static method for POST request
    public static Response post(String url, String body) {
        return given()
                .spec(getInstance().getRequestSpecification())
                .body(body)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }
}

