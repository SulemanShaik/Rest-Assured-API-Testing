package AllBasics;

import org.testng.annotations.Test;

import Files.Payload;
import io.restassured.path.json.JsonPath;
import junit.framework.Assert;

public class SumValidation {
	
	@Test
    public void sumOfCourses() 
    {
		int sum=0;
		JsonPath js = new JsonPath(Payload.CoursePrice());
		int count=js.getInt("courses.size()");
		for(int i=0;i<count;i++)
		{
			int price=js.getInt("courses["+i+"].price");
			int copies=js.getInt("courses["+i+"].copies");
			
			
			int amount=price*copies;
			System.out.println(amount);
			sum=sum+amount;
			System.out.println(sum);
			int purchaseamount=js.getInt("dashboard.purchaseamount");
			Assert.assertEquals(purchaseamount,sum);
			
		}
    }
}
