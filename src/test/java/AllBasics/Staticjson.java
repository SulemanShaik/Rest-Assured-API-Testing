package AllBasics;

import static io.restassured.RestAssured.given;

import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.google.common.io.Files;

import Files.Payload;
import Files.ReUsebleMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Staticjson {
	
	@Test
	public void addBook()
	{
		
		String resp=RestAssured.baseURI="http://216.10.245.166";
		given().log().all().header("Content_Type","application/json")
		//.body(GenerateStringfromResource("F:\\Addbooks.json"))
		.when()
		.post("/Libraby/Addbook.php")
		.then().assertThat().statusCode(200).extract().response().asString();
		JsonPath js=ReUsebleMethods.rawToJson(resp);
		String id=js.get("ID");
		System.out.println(id);
	}
	
	/*public static String GenerateStringfromResource(String path) 
	{
	
		return new String(Files.readAllBytes(Paths.get(path)));
		
	}
*/
}
