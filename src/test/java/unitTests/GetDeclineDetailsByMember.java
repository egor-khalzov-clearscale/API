package unitTests;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import resources.ReusableMethods;
import resources.base;

public class GetDeclineDetailsByMember extends base {
	
	static String aPIKey;
	static String companyId;
	static String clubId;
	static String format;

	@BeforeClass
	public void getData() {
		base.getPropertyData();
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = prop.getProperty("baseURI");
		
		aPIKey = prop.getProperty("X-Api-Key");
		companyId = prop.getProperty("X-CompanyId");
		clubId = prop.getProperty("X-Club1Id");
		
		format = prop.getProperty("format");
		
		
	}
	
	@Test (testName="Return - Declined",description="PBI:150150")
	public void returnDeclined() {
		
				String customerId = "251";//prop.getProperty("declineMemberId");
		
		Response res = 	
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.get("api/v3/member/getdeclinedetailsbymember?customerId="+customerId+"")
			.then()
//			    .log().all()
				.statusCode(200)
				.statusLine("HTTP/1.1 200 OK")
				.extract().response();
		
				JsonPath js = ReusableMethods.rawToJson(res);		
				
				Assert.assertTrue(res.getTime() >= 60L);
				Assert.assertEquals(js.getInt("Status"), 200);
	
	    		Assert.assertNotNull(js.getDouble("Result[0].Amount"));
	    		Assert.assertNotNull(js.getString("Result[0].Attendant"));
	    		Assert.assertNotNull(js.getString("Result[0].Code"));
	    		Assert.assertNotNull(js.getString("Result[0].CustomerId"));
	    		Assert.assertNotNull(js.getString("Result[0].Date"));
	    		Assert.assertTrue(js.getString("Result[0]").contains("FormOfPayment"));
	    		Assert.assertNotNull(js.getString("Result[0].Format"));
	    		Assert.assertNotNull(js.getString("Result[0].LateFee"));	    		
	    		Assert.assertEquals(js.getString("Result[0].CustomerId"), customerId);
	    		Assert.assertEquals(js.getString("Result[0].Code"), "MEMNOTFD");
	    		Assert.assertEquals(js.getString("Result[0].Format"), format);
	}
	
	@Test (testName="No History Found",description="PBI:150150")
	public void noHistoryFound() {
		
				String customerId = prop.getProperty("noFOPId");
		Response res = 
						
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.get("api/v3/member/getdeclinedetailsbymember?customerId="+customerId+"")
			.then()
//			    .log().all()
				.statusCode(400)
				.statusLine("HTTP/1.1 400 Bad Request")
				.extract().response();
		
				JsonPath js = ReusableMethods.rawToJson(res);		
		
				Assert.assertTrue(res.getTime() >= 60L);
				Assert.assertEquals(js.getInt("Status"), 204);
				Assert.assertEquals(js.getString("Message"), "No results returned");
	}
	
	@Test (testName="Member Not Found",description="PBI:150150")
	public void memberNotFound() {
		
				String customerId = "99999";
		Response res = 
						
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.get("api/v3/member/getdeclinedetailsbymember?customerId="+customerId+"")
			.then()
//			    .log().all()
				.statusCode(400)
				.statusLine("HTTP/1.1 400 Bad Request")
				.extract().response();
		
				JsonPath js = ReusableMethods.rawToJson(res);		
		
				Assert.assertTrue(res.getTime() >= 60L);
				Assert.assertEquals(js.getInt("Status"), 500);
				Assert.assertEquals(js.getString("Message"), "Customer not found");
	}
	
	
	
}
