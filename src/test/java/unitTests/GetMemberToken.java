package unitTests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import payloads.MemberPL;
import resources.ReusableMethods;
import resources.base;

public class GetMemberToken extends base {
	
	static String aPIKey;
	static String companyId;
	static String clubId;
	static String token;

	@BeforeClass
	public void getData() {
		base.getPropertyData();
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = prop.getProperty("baseURI");
		
		aPIKey = prop.getProperty("X-Api-Key");
		companyId = prop.getProperty("X-CompanyId");
		clubId = prop.getProperty("X-Club1Id");
	}
	
	@Test (priority=1,testName="Get Token",description="PBI:TBD")
	 
	public void getToken() {
		
		String barcodeId = prop.getProperty("availableUserName");
		String expirationTimeSpan = "00:00:01";

		Response res =
				
			given()
//				.log().all()
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(MemberPL.getMemberToken(barcodeId, expirationTimeSpan))
				.post("/api/v3/member/getcustomertoken").
			then()
//				.log().all()
				.assertThat().statusCode(200)
				.extract().response();	
		
			JsonPath js = ReusableMethods.rawToJson(res);
			String token = js.get("Result");
			
			Assert.assertEquals(String.valueOf(token.charAt(0)), "@");
			Assert.assertEquals(String.valueOf(token.charAt(1)), "5");
			Assert.assertEquals(String.valueOf(token.charAt(2)), "4");
			Assert.assertEquals(String.valueOf(token.charAt(3)), "4");
			Assert.assertEquals(String.valueOf(token.charAt(4)), "B");
			Assert.assertEquals(String.valueOf(token.charAt(5)), "4");
			Assert.assertEquals(String.valueOf(token.charAt(13)), "@");

		Assert.assertEquals( 14,token.length());
			
	}
	
	@Test (priority=2,testName="Get New Token",description="PBI:TBD")
	public void getNewToken() {
		
		// This test validates token returned is different from previous test
		
		String barcodeId = prop.getProperty("availableUserName");
		String expirationTimeSpan = "00:00:01";

		Response res =
				
			given()
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(MemberPL.getMemberToken(barcodeId, expirationTimeSpan))
				.post("/api/v3/member/getcustomertoken").
			then()
//				.log().all()
				.assertThat().statusCode(200)
				.extract().response();	
		
			JsonPath js = ReusableMethods.rawToJson(res);
			String newToken = js.get("Result");
			
			Assert.assertNotEquals(token, newToken);
			
	}

}
