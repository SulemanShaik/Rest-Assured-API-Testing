package AllBasics;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import org.testng.Assert;

import Files.Payload;
import Files.ReUsebleMethods;

public class Basics {
	public static void main(String[] args) {
		
		//Validate if Add place API is working as expected
		//Add Place -> update place with New Address -> Get place to validate if New Address is present in Response
		
		
		//given - all input details
		//when  - Submit the api  - resourse,http method
		//then  - validate the response
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		/*.body("{\r\n" + 
				"  \"location\": \r\n" + 
				"  {\r\n" + 
				"    \"lat\": -38.383494,\r\n" + 
				"    \"lng\": 33.427362\r\n" + 
				"  },\r\n" + 
				"  \"accuracy\": 50,\r\n" + 
				"  \"name\": \"Rahul Shetty\",\r\n" + 
				"  \"phone_number\": \"(+91) 983 893 3937\",\r\n" + 
				"  \"address\": \"29, side layout, cohen 09\",\r\n" + 
				"  \"types\": [\r\n" + 
				"    \"shoe park\",\r\n" + 
				"    \"shop\"\r\n" + 
				"  ],\r\n" + 
				"  \"website\": \"http://rahulshettyacademy.com\",\r\n" + 
				"  \"language\": \"French-IN\"\r\n" + 
				"}\r\n" + 
				"")*/
		.body(Payload.AddPlace())
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope",equalTo("APP"))
		.header("Server", equalTo("Apache/2.4.18 (Ubuntu)")).extract().asString();
		
		System.out.println(response);
		JsonPath js = new JsonPath(response);  //for parsing Json
		String placeid=js.getString("place_id");
		System.out.println(placeid);
		
		//Update Place
		
		String newAddress="Cape Town , Africa";
		given().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n" + 
				"   \r\n" + 
				"    \"place_id\": \""+placeid+"\",\r\n" + 
				"    \"address\" : \""+newAddress+"\",\r\n" + 
				"    \"key\"      : \"qaclick123\"\r\n" + 
				"   \r\n" + 
				"}")
		.when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		
		//Get Place
		
		String getPlaceResponse=given().log().all().queryParam("key", "qaclick123")
		.queryParam("palce_id", placeid)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		//JsonPath js1 = new JsonPath(getPlaceResponse);
		JsonPath js1=ReUsebleMethods.rawToJson(getPlaceResponse);
		String actualAddress=js1.getString("address");
		System.out.println(actualAddress);
		Assert.assertEquals(actualAddress, newAddress);
	}

}
