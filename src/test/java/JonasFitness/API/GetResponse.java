package JonasFitness.API;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import resources.ReusableMethods;
import resources.base;
import resources.resources;

public class GetResponse extends base {

	@BeforeTest
	public void getData() throws IOException {
		base.getPropertyData();
	}

	@Test
	public void Test1() {
		RestAssured.baseURI = prop.getProperty("hostG");
		Response res = 
				
				given().
					param("location", "40.128354, -83.017916").
					param("radius", "500").
					param("key", prop.getProperty("keyG")).
					param("type", "bar").

				when().get(resources.getnearbyDataJSON()).

				then().

				extract().response();

		// ** Used to get entire response **
		String responseString = res.asString();
		System.out.println(responseString);

		// ** Used to get specific element from response **
		JsonPath js = ReusableMethods.rawToJson(res);
		String name = js.get("results[0].name");
		System.out.println(name);
	}

}
