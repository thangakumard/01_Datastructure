package api_test;

import static io.restassured.RestAssured.*;

import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.*;

/**
 * Hello world!
 *
 */
public class SampleGetRequest 
{
	@Test
    public void SampleRequest()
    {
    	requestSpecification_Sample_01();
    	responseSpecification_Sample_02();
    }
    
    private static void requestSpecification_Sample_01() {
    	RequestSpecification requestSpec = new  RequestSpecBuilder()
    			.setContentType("application/json")
    			.setBaseUri("http://api.zippopotam.us")
    			//.addQueryParam("author_id","5")
    			.build();
    				
    		given()
    			.spec(requestSpec)
    			.log().uri()
    		.when()
    			.get("/us/90210")
    		.then()
    			.statusCode(200)
    			.log().body();
    }
    

    private static void responseSpecification_Sample_02() {
    	RequestSpecification requestSpec = new  RequestSpecBuilder()
    			.setContentType("application/json")
    			.setBaseUri("http://api.zippopotam.us")
    			//.addQueryParam("author_id","5")
    			.build();
    	
    	ResponseSpecification responseSpec = new  ResponseSpecBuilder()
    			.expectStatusCode(200)
    			.expectContentType("application/json")
//    			.expectBody("size()", is(3))
//    			.expectBody("author_id", hasItem(5) )
    			.build();
    				
    		given()
    			.spec(requestSpec)
    			.log().uri()
    		.when()
    			.get("/us/90210")
    		.then()
    		.spec(responseSpec)
    		.log().body();
    			
    }

}
