package AllBasics;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Files.Payload;
import Files.ReUsebleMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {
	
	@Test(dataProvider="BooksData1")
	public void addBook(String isbn,String aisle)
	{
		
		String resp=RestAssured.baseURI="http://216.10.245.166";
		given().log().all().header("Content_Type","application/json")
		.body(Payload.Addbook(isbn,aisle))
		.when().post("Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		JsonPath js=ReUsebleMethods.rawToJson(resp);
		String id=js.get("ID");
		System.out.println(id);
	}
	
	@DataProvider(name="BooksData1")
	
	public Object [][] getData()
	{
		//array- collection of elements
		//Multidimensional array-collection of arrays
		return new Object[][] {{"acdf","5964"},{"fgjhj","2314"},{"nkkdj","428"}};
	}
	

}
