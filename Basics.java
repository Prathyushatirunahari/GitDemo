import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import Files.payload;

public class Basics {

	public static void main(String[] args) {
		//validate if Add place API is working as expected
		
		
		//given=all input details
		//when=submit the API:resource and http method
		//Then=validate the response
		
		
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		
	String response=	given().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(payload.AddPlace()).when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope",equalTo("APP"))
		.header("server","Apache/2.4.41 (Ubuntu)").extract().response().asString();
		
		
	System.out.println(response);
		
	
	JsonPath js= new JsonPath(response);
	String placeId=js.getString("place_id");
	System.out.println(placeId);
	String newAddress="70 winter walk, USA";
	
	given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
	.body("{\r\n"
			+ "\"place_id\":\""+placeId+"\"\",\r\n"
			+ "\"address\":\""+newAddress+"\"\",\r\n"
			+ "\"key\":\"qaclick123\"\r\n"
			+ "}").
	when().put("maps/api/place/update/json")
	.then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address sucessfully updated"));
	
	//ADD PLACE=> PLACE WITH NEW ADDRESS->GET PLACE TO VALIDATE IF NEW ADRESS IS PRESENT OR NORT
	given().queryParam("key", "qaclick123").queryParam("place_id",placeId)
	.when().get("maps/api/place/update/json").
	}

}
