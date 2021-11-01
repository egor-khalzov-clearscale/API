package unitTests;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import resources.ReusableMethods;
import resources.base;

public class GetPackagesForPurchaseByClub extends base {
	
	static String aPIKey;
	static String companyId;
	static String clubId;
	static String onlinePurchase;
	static String customerId;
	static String onlinePackageId;
	static String noOnlinePackageId;

	@BeforeClass
	public void getData() {
		base.getPropertyData();
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = prop.getProperty("baseURI");
		
		aPIKey = prop.getProperty("X-Api-Key");
		companyId = prop.getProperty("X-CompanyId");
		clubId = prop.getProperty("X-Club1Id");
		
		customerId = prop.getProperty("availableId");
		onlinePackageId = prop.getProperty("paidTId");
		noOnlinePackageId = prop.getProperty("noWebTId");
		onlinePurchase = "false"; // includes all packages
		
	}
	
	@Test (testName="Online Packages Included",description="PBI:160287")
	public void onlinePackagesIncluded() {
		
		Response res = 	
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.get("/api/v3/package/getpackagesforpurchasebyclub/"+customerId+"?OnlinePurchase="+onlinePurchase+"")
			.then()
//			    .log().body()
				.assertThat().statusCode(200)
				.extract().response();
		
				JsonPath js = ReusableMethods.rawToJson(res);		
				
				Assert.assertTrue(res.getTime() >= 60L);
				
				Assert.assertTrue(js.getString("Result.AssociatedSessionDtos").contains("BasePrice"));
				Assert.assertTrue(js.getString("Result.AssociatedSessionDtos").contains("ClubName"));
				Assert.assertTrue(js.getString("Result.AssociatedSessionDtos").contains("ItemBarcodeId"));
				Assert.assertTrue(js.getString("Result.AssociatedSessionDtos").contains("ItemDescription"));
				Assert.assertTrue(js.getString("Result.AssociatedSessionDtos").contains("SessionType"));
				
				Assert.assertTrue(js.getString("Result").contains("BasePrice"));
				Assert.assertTrue(js.getString("Result").contains("CategoryDescription"));
				Assert.assertTrue(js.getString("Result").contains("DaysUntilExpiration"));
				Assert.assertTrue(js.getString("Result").contains("ItemBarcodeId"));
				Assert.assertTrue(js.getString("Result").contains("ItemDescription"));
				Assert.assertTrue(js.getString("Result").contains("ItemId"));
				Assert.assertTrue(js.getString("Result").contains("LongDescription"));
				Assert.assertTrue(js.getString("Result").contains("RedeemableClubs"));
				
				Assert.assertTrue(js.getString("Result.PriceRangeDtos").contains("EndRange"));
				Assert.assertTrue(js.getString("Result.PriceRangeDtos").contains("PricePerUnit"));
				Assert.assertTrue(js.getString("Result.PriceRangeDtos").contains("StartRange"));
				
				Assert.assertTrue(js.getString("Result.ItemId").contains(noOnlinePackageId));
				Assert.assertTrue(js.getString("Result.ItemId").contains(onlinePackageId));
	}
	
	@Test (testName="Online Packages Excluded",description="PBI:160287")
	public void onlinePackagesExcluded() { 
		
				String onlinePurchase = "true";
		
		Response res = 	
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.get("/api/v3/package/getpackagesforpurchasebyclub/"+customerId+"?OnlinePurchase="+onlinePurchase+"")
			.then()
//			    .log().body()
				.assertThat().statusCode(200)
				.extract().response();
		
				JsonPath js = ReusableMethods.rawToJson(res);		
				
				Assert.assertTrue(res.getTime() >= 60L);
				
				Assert.assertTrue(js.getString("Result.AssociatedSessionDtos").contains("BasePrice"));
				Assert.assertTrue(js.getString("Result.AssociatedSessionDtos").contains("ClubName"));
				Assert.assertTrue(js.getString("Result.AssociatedSessionDtos").contains("ItemBarcodeId"));
				Assert.assertTrue(js.getString("Result.AssociatedSessionDtos").contains("ItemDescription"));
				Assert.assertTrue(js.getString("Result.AssociatedSessionDtos").contains("SessionType"));
				
				Assert.assertTrue(js.getString("Result").contains("BasePrice"));
				Assert.assertTrue(js.getString("Result").contains("CategoryDescription"));
				Assert.assertTrue(js.getString("Result").contains("DaysUntilExpiration"));
				Assert.assertTrue(js.getString("Result").contains("ItemBarcodeId"));
				Assert.assertTrue(js.getString("Result").contains("ItemDescription"));
				Assert.assertTrue(js.getString("Result").contains("ItemId"));
				Assert.assertTrue(js.getString("Result").contains("LongDescription"));
				Assert.assertTrue(js.getString("Result").contains("RedeemableClubs"));
				
				Assert.assertTrue(js.getString("Result.PriceRangeDtos").contains("EndRange"));
				Assert.assertTrue(js.getString("Result.PriceRangeDtos").contains("PricePerUnit"));
				Assert.assertTrue(js.getString("Result.PriceRangeDtos").contains("StartRange"));
				
				Assert.assertFalse(js.getString("Result.ItemId").contains(noOnlinePackageId)); // Excluded
				Assert.assertTrue(js.getString("Result.ItemId").contains(onlinePackageId));
	}
	
	@Test (testName="Package Not Found",description="PBI:160287")
	public void packageNotFound() { 
		
				String notAvailPackageId = prop.getProperty("notAvailPackageId");
		
		Response res = 	
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.get("/api/v3/package/getpackagesforpurchasebyclub/"+customerId+"?OnlinePurchase="+onlinePurchase+"")
			.then()
//			    .log().body()
				.assertThat().statusCode(200)
				.extract().response();
		
				JsonPath js = ReusableMethods.rawToJson(res);		
				
				Assert.assertTrue(res.getTime() >= 60L);
				
				Assert.assertFalse(js.getString("Result.ItemId").contains(notAvailPackageId)); // Not available that club
	}
	
	@Test (testName="Customer Not Found",description="PBI:160287")
	public void customerNotFound() {
		
				String customerId = "999999";
		
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.get("/api/v3/package/getpackagesforpurchasebyclub/"+customerId+"?OnlinePurchase="+onlinePurchase+"")
			.then()
//			    .log().body()
				.assertThat().statusCode(500)
				.body("Message",contains("Internal server error - Customer Not Found"));
			}
}
