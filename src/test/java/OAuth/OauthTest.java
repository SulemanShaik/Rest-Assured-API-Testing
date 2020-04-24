package OAuth;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation; 

public class OauthTest {

	public static void main(String[] args) throws InterruptedException {
		
		String [] courseTitles= {"Selenium Webdriver java","Cypress","Protractor"};
		//System.setProperty("webdriver.chrome.driver", "")
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&");
		
		driver.findElement(By.id("identifierId")).sendKeys("sulemansk.info@gmail.com");
		driver.findElement(By.xpath("//*[@id=\"identifierNext\"]/span/span")).click();
		driver.findElement(By.xpath("//*[@id=\"password\"]/div[1]/div/div[1]/input")).sendKeys("aahil@786");
		Thread.sleep(3000);
		//String url=driver.getCurrentUrl();
		
		String url="https://rahulshettyacademy.com/getCourse.php?code=4%2FxwEbcR53jExfoh-SnaNZ5WII3Oaipow9wAT2fQITjH-dCpNasWsbs88A7cC34DTxQTeZY4mp_cPeRIXxBFt_gYA&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent#";
		String partialcode=url.split("code=")[1];
		String code=partialcode.split("&scope")[0];
		System.out.println(code);
		
		String accessTokenResponse=given().urlEncodingEnabled(false)
		.queryParams("code",code)
		.queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_sccret","erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type","authorization_code")
		.when().log().all()
		.post("https://www.googleapis.com/oauth2/v4/token").asString();
		JsonPath js= new JsonPath(accessTokenResponse);
		       String  accessToken=js.getString("access_token");
           
		/*String response=given().queryParam("access_token", accessToken)
		.when().log().all()
		.get("https://rahulshettyacademy.com/getCourse.php").asString();
		System.out.println(response);*/
		       
		       //POJO Classes
   GetCourse gc=given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
				.when()
				.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		        
		        System.out.println(gc.getLinkedIn());
		        System.out.println(gc.getInstructor());
		        //to get the price of SoapUI WebServices testing 
		        System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		        
		        //TO get the price dynamically
		        List<Api>apiCourses=gc.getCourses().getApi();
		        
		        for (int i = 0; i < apiCourses.size(); i++)
		        {
		        	if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI WebServices testing "));
		        	{
		        		System.out.println(apiCourses.get(i).getPrice());
		        	}
				}
		        
		        //get the courses of web automation
		        
		        ArrayList<String>al=new ArrayList<String>();
		          List<WebAutomation>w=gc.getCourses().getWebAutomation();
		          for (int j = 0; j < w.size(); j++) 
		          {
					al.add(w.get(j).getCourseTitle());
							
				  }
		          
		          List<String> expectedList=Arrays.asList(courseTitles);
		        
		        Assert.assertTrue(al.equals(expectedList));
		        
		        
		        
		       
		
		
	}

}
