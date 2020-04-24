package AllBasics;

import Files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		
		JsonPath js = new JsonPath(Payload.CoursePrice());
		
		//Print No of courses
		int count =js.getInt("courses.size()");
		System.out.println(count);
		
		//Print Purchase Amount
		int totalAmount=js.getInt("dashboard.purchaseamount");
		System.out.println(totalAmount);
		
		//Print Title of the first course
		String firstCourseTitle=js.get("courses[0].title");
		System.out.println(firstCourseTitle);
		
		//Print All course titles and their respective prices
		for(int i=0;i<count;i++) {
			System.out.println(js.get("courses["+i+"].title"));
			System.out.println(js.get("courses["+i+"].price").toString()); //We Can To String To PRint string data
			
		}
		
		//Print no of copies sold by Java Course
		
		System.out.println("Print no of copies sold by Java Course");
		for(int i=0;i<count;i++)
		{
			String courseTitles=js.get("courses["+i+"].title");
			if(courseTitles.equalsIgnoreCase("java")) 
			{
				int copies=js.get("courses["+i+"].copies");
				System.out.println(copies);
				break;
			}
		}
		

	}

}
