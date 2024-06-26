package api_test;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;


public class APITest {
    @Test
    public void testGetRequest() {
        Response response = RestAssuredSingleton.get("/posts/1");
        assertEquals(200, response.getStatusCode());
        assertEquals(1, response.jsonPath().getInt("userId"));
        assertEquals(1, response.jsonPath().getInt("id"));
        assertNotNull(response.jsonPath().getString("title"));
    }

    @Test
    public void testPostRequest() {
        String requestBody = "{ \"title\": \"foo\", \"body\": \"bar\", \"userId\": 1 }";
        Response response = RestAssuredSingleton.post("/posts", requestBody, null);

        assertEquals(201, response.getStatusCode());
        assertEquals("foo", response.jsonPath().getString("title"));
        assertEquals("bar", response.jsonPath().getString("body"));
        assertEquals(1, response.jsonPath().getInt("userId"));
    }
}
