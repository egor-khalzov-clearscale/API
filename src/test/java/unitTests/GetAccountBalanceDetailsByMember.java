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

public class GetAccountBalanceDetailsByMember extends base {
	
			static String aPIKey;
			static String companyId;
			static String clubId;
			static String altCompanyId;
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
	
	@Test (testName="History Found - No Declines", description="PBI:149846", enabled = true)
	
	public void historyFoundNoDeclines() {
		
		String customerId = prop.getProperty("noFOPId");
		
		Response res =
		
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("Content-Type", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.get("/api/v3/member/getaccountbalancedetailsbymember?customerId="+customerId+"")
			.then()
//				.log().all()
				.assertThat()
				.statusCode(200)
				.extract().response();
				JsonPath js = ReusableMethods.rawToJson(res);
				
				Assert.assertTrue(res.getTime() >= 60L);
				
				Assert.assertEquals(js.getString("Result.CustomerId"),customerId);
				Assert.assertNotNull(js.getString("Result.Name"));
				Assert.assertNotNull(js.getString("Result.CustomerBarcodeId"));
				Assert.assertNotNull(js.getString("Result.BaseDate"));
				Assert.assertNotNull(js.getString("Result.CurrentCharges"));
				Assert.assertNotNull(js.getString("Result.Past30"));
				Assert.assertNotNull(js.getString("Result.Past60"));
				Assert.assertNotNull(js.getString("Result.Past90"));
				Assert.assertNotNull(js.getString("Result.Past120"));
				Assert.assertNotNull(js.getString("Result.FutureCharges"));
				Assert.assertNotNull(js.getString("Result.UnappliedPayments"));
				Assert.assertNotNull(js.getString("Result.TotalDue"));
				Assert.assertNotNull(js.getString("Result.CurrentBalance"));
				Assert.assertNotNull(js.getString("Result.AccountValue"));
				
				Assert.assertNull(js.getString("Result.LastDeclineDate"));
				Assert.assertNull(js.getString("Result.DeclineAmount"));
				Assert.assertNull(js.getString("Result.ReturnCode"));
				Assert.assertNull(js.getString("Result.DeclineReason"));
				Assert.assertNull(js.getString("Result.FormOfPayment"));
				
				Assert.assertTrue(js.getString("Result").contains("LastDeclineDate"));
				Assert.assertTrue(js.getString("Result").contains("DeclineAmount"));
				Assert.assertTrue(js.getString("Result").contains("ReturnCode"));
				Assert.assertTrue(js.getString("Result").contains("DeclineReason"));
				Assert.assertTrue(js.getString("Result").contains("FormOfPayment"));
	}

	
	@Test (testName="Customer Not Found", description="PBI:149846")
	public void customerNotFound() {
		
				String customerId = "99999";
		
		Response res = 
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("Content-Type", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.get("/api/v3/member/getaccountbalancedetailsbymember?customerId="+customerId+"")
			.then()
//				.log().all()
				.statusCode(500)
				.extract().response();
			
				JsonPath js = ReusableMethods.rawToJson(res);
				
				Assert.assertTrue(js.getString("Message").contains("CustomerId '"+customerId+"' not found"));
	}
	
	
	
	
	
	
	
	
}






















