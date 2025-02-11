package apitesting;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
public class PostRequest {
    @BeforeClass
    public void setup() {
        // Set base URL
        RestAssured.baseURI = "https://reqres.in/api";
    }
    @Test
    public void testCreateUser() {
        // Prepare JSON body for the POST request
        String requestBody = "{\n" +
                "    \"name\": \"John\",\n" +
                "    \"job\": \"Engineer\"\n" +
                "}";
        // Send POST request and get the response
        Response response = RestAssured
                //Response response refers to a variable that holds the HTTP response 
                //from the API request made by RestAssured. 
                //Here's a breakdown of what this means:
            .given()                       // 1
            .contentType(ContentType.JSON)  // 2
            .body(requestBody)              // 3
            .when()                         // 4
            .post("/users")                 // 5
            .then()                         // 6
            .extract().response();          // 7
        // Print the response body
        System.out.println("Response Body: " + response.getBody().asString());
        // Verify status code is 201 (Created)
        AssertJUnit.assertEquals(response.getStatusCode(), 201);
        // Verify that the response contains the "name" and "job" we sent in the request
        AssertJUnit.assertTrue(response.getBody().asString().contains("John"));
        AssertJUnit.assertTrue(response.getBody().asString().contains("Engineer"));
    }
}

