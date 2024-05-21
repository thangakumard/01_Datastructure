package api_test;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class APITestJsonSchemaValidator {
    @BeforeSuite
    public static void setup() {
        baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void testGetRequestSchemaValidation() {
        given()
                .when()
                .get("/posts/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/post-schema.json"));
    }
}

