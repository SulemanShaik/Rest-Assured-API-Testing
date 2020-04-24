package serilization;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;


public class serilizationTest {

	public static void main(String[] args) {

		
		AddPlace p=  new AddPlace();
		p.setAccuracy(50);
		p.setAddress("29, side layout, cohen 11");
		p.setLanguage("French-IN");
		p.setPhone_number("9966585640");
		p.setWebsite("https://rahulshettyacademy.com");
		p.setName("Galaxy mansion");
		ArrayList<String>myList= new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		p.setTypes(myList);
		Location l = new Location();
		l.setLat(-38.383494);
		l.setLan( 33.427362);
		p.setLocation(l);
	
		RestAssured.baseURI="https://rahulshettyacademy.com";
	   Response res=given().log().all().queryParam("key", "qaclick123")
		.body(p)
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().response();
		String responseString=res.asString();
		System.out.println(responseString);
		
		
		
		
	}

}
