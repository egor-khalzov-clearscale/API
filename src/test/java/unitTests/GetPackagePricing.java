package unitTests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import resources.ReusableMethods;
import resources.base;

public class GetPackagePricing extends base {
	
	static String aPIKey;
	static String companyId;
	static String clubId;
	
	@BeforeClass
	public void getData() {
		base.getPropertyData();
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = prop.getProperty("baseURI");
		
		aPIKey = prop.getProperty("X-Api-Key");
		companyId = prop.getProperty("X-CompanyId");
		clubId = prop.getProperty("X-Club1Id");
	}
	
	@Test (testName="Item Found - Single Tax",description="PBI:155660")
	public void itemFound_SingleTax() { 
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String i = prop.getProperty("taxSingleTId");
				int itemId = Integer.parseInt(i);
				int quantity = 1;

		Response res =	given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/package/getpackagepricing/"+customerId+"/"+itemId+"/"+quantity)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Result.CanPlaceOnAccount", equalTo(true))
						.body("Result.PriceDetails[0].CorrelationId", not(nullValue()))
						.body("Result.PriceDetails[0].CustomerId", equalTo(customerId))
						.body("Result.PriceDetails[0].IsTaxed", equalTo(true))
						.body("Result.PriceDetails[0].ItemId", equalTo(itemId))
						.body("Result.TaxDetails[0].TaxItemId", equalTo(4))
						.extract().response();
		
					JsonPath js = ReusableMethods.rawToJson(res);
						Assert.assertEquals(js.getDouble("Result.GrandTotal"), 10.25);
						Assert.assertEquals(js.getDouble("Result.PriceDetails[0].Price"), 10.0);
						Assert.assertEquals(js.getDouble("Result.SubTotal"), 10.0);
						Assert.assertEquals(js.getDouble("Result.Tax"), 0.25);
						Assert.assertEquals(js.getDouble("Result.TaxDetails[0].TaxAmount"), 0.25);
	}
	
	@Test (testName="Item Found - Multiple Taxes",description="PBI:155660")
	public void itemFound_MultipleTaxes() { 
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String i = prop.getProperty("taxmultipleTId");
				int itemId = Integer.parseInt(i);
				int quantity = 1;

		Response res =	given()

				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/package/getpackagepricing/"+customerId+"/"+itemId+"/"+quantity)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.body("Result.CanPlaceOnAccount", equalTo(true))
						.body("Result.PriceDetails[0].CorrelationId", not(nullValue()))
						.body("Result.PriceDetails[0].CustomerId", equalTo(customerId))
						.body("Result.PriceDetails[0].IsTaxed", equalTo(true))
						.body("Result.PriceDetails[0].ItemId", equalTo(itemId))
						.body("Result.TaxDetails[0].TaxItemId", equalTo(4))
						.body("Result.TaxDetails[1].TaxItemId", equalTo(5))
						.body("Result.TaxDetails[2].TaxItemId", equalTo(6))
						.body("Result.TaxDetails[3].TaxItemId", equalTo(7))
						.extract().response();
						
					JsonPath js = ReusableMethods.rawToJson(res);
						Assert.assertEquals(js.getDouble("Result.GrandTotal"), 11.3);
						Assert.assertEquals(js.getDouble("Result.PriceDetails[0].Price"), 10.0);
						Assert.assertEquals(js.getDouble("Result.SubTotal"), 10.0);
						Assert.assertEquals(js.getDouble("Result.Tax"), 1.3);
						Assert.assertEquals(js.getDouble("Result.TaxDetails[0].TaxAmount"), 0.25);
						Assert.assertEquals(js.getDouble("Result.TaxDetails[1].TaxAmount"), 0.3);						
						Assert.assertEquals(js.getDouble("Result.TaxDetails[2].TaxAmount"), 0.4);						
						Assert.assertEquals(js.getDouble("Result.TaxDetails[3].TaxAmount"), 0.35);
	}
	
	@Test (testName="Item Found - Club 2",description="PBI:155660")
	public void itemFound_Club2() { 
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String i = prop.getProperty("taxSingleTId");
				int itemId = Integer.parseInt(i);
				int quantity = 1;

		Response res =	given()

				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", prop.getProperty("X-Club2Id"))
					.when()
						.get("/api/v3/package/getpackagepricing/"+customerId+"/"+itemId+"/"+quantity)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Result.CanPlaceOnAccount", equalTo(true))
						.body("Result.PriceDetails[0].CorrelationId", not(nullValue()))
						.body("Result.PriceDetails[0].CustomerId", equalTo(customerId))
						.body("Result.PriceDetails[0].IsTaxed", equalTo(true))
						.body("Result.PriceDetails[0].ItemId", equalTo(itemId))
						.body("Result.TaxDetails[0].TaxItemId", equalTo(4))
						.extract().response();
		
					JsonPath js = ReusableMethods.rawToJson(res);
						Assert.assertEquals(js.getDouble("Result.GrandTotal"), 10.29);
						Assert.assertEquals(js.getDouble("Result.PriceDetails[0].Price"), 10.0);
						Assert.assertEquals(js.getDouble("Result.SubTotal"), 10.0);
						Assert.assertEquals(js.getDouble("Result.Tax"), 0.29);
						Assert.assertEquals(js.getDouble("Result.TaxDetails[0].TaxAmount"), 0.29);
	}
	
	@Test (testName="Paid Training",description="PBI:155660")
	public void paidTraining() { 
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String i = prop.getProperty("paidTId");
				int itemId = Integer.parseInt(i);
				int quantity = 1;

		Response res = given()
//						
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/package/getpackagepricing/"+customerId+"/"+itemId+"/"+quantity)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.body("Result.CanPlaceOnAccount", equalTo(true))
						.body("Result.PriceDetails[0].CorrelationId", not(nullValue()))
						.body("Result.PriceDetails[0].CustomerId", equalTo(customerId))
						.body("Result.PriceDetails[0].IsTaxed", equalTo(false))
						.body("Result.PriceDetails[0].ItemId", equalTo(itemId))
						.extract().response();
			
					JsonPath js = ReusableMethods.rawToJson(res);
						Assert.assertEquals(js.getDouble("Result.GrandTotal"), 10.0);
						Assert.assertEquals(js.getDouble("Result.PriceDetails[0].Price"), 10.0);
						Assert.assertEquals(js.getDouble("Result.SubTotal"), 10.0);
						Assert.assertEquals(js.getDouble("Result.Tax"), 0.0);
	}
	
	@Test (testName="Paid Service",description="PBI:155660")
	public void paidService() { 
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String i = prop.getProperty("paidServiceVId");
				int itemId = Integer.parseInt(i);
				int quantity = 5;

		Response res = given()
//						
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/package/getpackagepricing/"+customerId+"/"+itemId+"/"+quantity)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.body("Result.CanPlaceOnAccount", equalTo(true))
						.body("Result.PriceDetails[0].CorrelationId", not(nullValue()))
						.body("Result.PriceDetails[0].CustomerId", equalTo(customerId))
						.body("Result.PriceDetails[0].IsTaxed", equalTo(false))
						.body("Result.PriceDetails[0].ItemId", equalTo(itemId))
						.extract().response();
			
					JsonPath js = ReusableMethods.rawToJson(res);
						Assert.assertEquals(js.getDouble("Result.GrandTotal"), 75.0);
						Assert.assertEquals(js.getDouble("Result.PriceDetails[0].Price"), 75.0);
						Assert.assertEquals(js.getDouble("Result.SubTotal"), 75.0);
						Assert.assertEquals(js.getDouble("Result.Tax"), 0.0);
	}
	
	@Test (testName="Paid Punchcard",description="PBI:155660", enabled = true)
	public void paidPunchcard() { 
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String i = prop.getProperty("paidPId");
				int itemId = Integer.parseInt(i);
				int quantity = 1;

		Response res = given()
//				.log().all()	
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/package/getpackagepricing/"+customerId+"/"+itemId+"/"+quantity)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.body("Result.CanPlaceOnAccount", equalTo(true))
						.body("Result.PriceDetails[0].CorrelationId", not(nullValue()))
						.body("Result.PriceDetails[0].CustomerId", equalTo(customerId))
						.body("Result.PriceDetails[0].IsTaxed", equalTo(false))
						.body("Result.PriceDetails[0].ItemId", equalTo(itemId))
						.extract().response();
			
					JsonPath js = ReusableMethods.rawToJson(res);
						Assert.assertEquals(js.getDouble("Result.GrandTotal"), 10.0);
						Assert.assertEquals(js.getDouble("Result.PriceDetails[0].Price"), 10.0);
						Assert.assertEquals(js.getDouble("Result.SubTotal"), 10.0);
						Assert.assertEquals(js.getDouble("Result.Tax"), 0.0);
	}
	
	@Test (testName="Free Training",description="PBI:155660")
	public void freeTraining() { 
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String i = prop.getProperty("freeTId");
				int itemId = Integer.parseInt(i);
				int quantity = 1;

		Response res =	given()

				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/package/getpackagepricing/"+customerId+"/"+itemId+"/"+quantity)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.body("Result.CanPlaceOnAccount", equalTo(true))
						.body("Result.PriceDetails[0].CorrelationId", not(nullValue()))
						.body("Result.PriceDetails[0].CustomerId", equalTo(customerId))
						.body("Result.PriceDetails[0].IsTaxed", equalTo(false))
						.body("Result.PriceDetails[0].ItemId", equalTo(itemId))
						.extract().response();
				
					JsonPath js = ReusableMethods.rawToJson(res);
						Assert.assertEquals(js.getDouble("Result.GrandTotal"), 0.0);
						Assert.assertEquals(js.getDouble("Result.PriceDetails[0].Price"), 0.0);
						Assert.assertEquals(js.getDouble("Result.SubTotal"), 0.0);
						Assert.assertEquals(js.getDouble("Result.Tax"), 0.0);
	}
	
	@Test (testName="Free Service",description="PBI:155660")
	public void freeService() { 
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String i = prop.getProperty("freeSVId");
				int itemId = Integer.parseInt(i);
				int quantity = 1;

		Response res =	given()

				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/package/getpackagepricing/"+customerId+"/"+itemId+"/"+quantity)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.body("Result.CanPlaceOnAccount", equalTo(true))
						.body("Result.PriceDetails[0].CorrelationId", not(nullValue()))
						.body("Result.PriceDetails[0].CustomerId", equalTo(customerId))
						.body("Result.PriceDetails[0].IsTaxed", equalTo(false))
						.body("Result.PriceDetails[0].ItemId", equalTo(itemId))
						.extract().response();
				
					JsonPath js = ReusableMethods.rawToJson(res);
						Assert.assertEquals(js.getDouble("Result.GrandTotal"), 0.0);
						Assert.assertEquals(js.getDouble("Result.PriceDetails[0].Price"), 0.0);
						Assert.assertEquals(js.getDouble("Result.SubTotal"), 0.0);
						Assert.assertEquals(js.getDouble("Result.Tax"), 0.0);
	}
	
	@Test (testName="Free Punchcard",description="PBI:155660")
	public void freePunchard() { 
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String i = prop.getProperty("freePId");
				int itemId = Integer.parseInt(i);
				int quantity = 1;

		Response res =	given()

				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/package/getpackagepricing/"+customerId+"/"+itemId+"/"+quantity)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.body("Result.CanPlaceOnAccount", equalTo(true))
						.body("Result.PriceDetails[0].CorrelationId", not(nullValue()))
						.body("Result.PriceDetails[0].CustomerId", equalTo(customerId))
						.body("Result.PriceDetails[0].IsTaxed", equalTo(false))
						.body("Result.PriceDetails[0].ItemId", equalTo(itemId))
						.extract().response();
				
					JsonPath js = ReusableMethods.rawToJson(res);
						Assert.assertEquals(js.getDouble("Result.GrandTotal"), 0.0);
						Assert.assertEquals(js.getDouble("Result.PriceDetails[0].Price"), 0.0);
						Assert.assertEquals(js.getDouble("Result.SubTotal"), 0.0);
						Assert.assertEquals(js.getDouble("Result.Tax"), 0.0);
	}
	
	@Test (testName="Tier pricing - Tier 1",description="PBI:155660")
	public void tierPricingTier1() { 
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String i = prop.getProperty("tierPricingId");
				int itemId = Integer.parseInt(i);
				int quantity = 1;

		Response res =	given()

				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/package/getpackagepricing/"+customerId+"/"+itemId+"/"+quantity)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.body("Result.CanPlaceOnAccount", equalTo(true))
						.body("Result.PriceDetails[0].CorrelationId", not(nullValue()))
						.body("Result.PriceDetails[0].CustomerId", equalTo(customerId))
						.body("Result.PriceDetails[0].IsTaxed", equalTo(true))
						.body("Result.PriceDetails[0].ItemId", equalTo(itemId))
						.extract().response();
				
					JsonPath js = ReusableMethods.rawToJson(res);
						Assert.assertEquals(js.getDouble("Result.GrandTotal"), 10.25);
						Assert.assertEquals(js.getDouble("Result.PriceDetails[0].Price"), 10.00);
						Assert.assertEquals(js.getDouble("Result.SubTotal"), 10.00);
						Assert.assertEquals(js.getDouble("Result.Tax"), 0.25);
	}
	
	@Test (testName="Tier pricing - Tier 2",description="PBI:155660")
	public void tierPricingTier2() { 
		
		String c = prop.getProperty("availableId");
		int customerId = Integer.parseInt(c);
		String i = prop.getProperty("tierPricingId");
		int itemId = Integer.parseInt(i);
		int quantity = 7;

		Response res =	given()

				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/package/getpackagepricing/"+customerId+"/"+itemId+"/"+quantity)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.body("Result.CanPlaceOnAccount", equalTo(true))
						.body("Result.PriceDetails[0].CorrelationId", not(nullValue()))
						.body("Result.PriceDetails[0].CustomerId", equalTo(customerId))
						.body("Result.PriceDetails[0].IsTaxed", equalTo(true))
						.body("Result.PriceDetails[0].ItemId", equalTo(itemId))
						.extract().response();
				
					JsonPath js = ReusableMethods.rawToJson(res);
						Assert.assertEquals(js.getDouble("Result.GrandTotal"), 64.58);
						Assert.assertEquals(js.getDouble("Result.PriceDetails[0].Price"), 63.0);
						Assert.assertEquals(js.getDouble("Result.SubTotal"), 63.0);
						Assert.assertEquals(js.getDouble("Result.Tax"), 1.58);
	}
	
	@Test (testName="Tier pricing - Tier 3",description="PBI:155660")
	public void tierPricingTier3() { 
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String i = prop.getProperty("tierPricingId");
				int itemId = Integer.parseInt(i);
				int quantity = 12;

		Response res =	given()

				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/package/getpackagepricing/"+customerId+"/"+itemId+"/"+quantity)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.body("Result.CanPlaceOnAccount", equalTo(true))
						.body("Result.PriceDetails[0].CorrelationId", not(nullValue()))
						.body("Result.PriceDetails[0].CustomerId", equalTo(customerId))
						.body("Result.PriceDetails[0].IsTaxed", equalTo(true))
						.body("Result.PriceDetails[0].ItemId", equalTo(itemId))
						.extract().response();
				
					JsonPath js = ReusableMethods.rawToJson(res);
						Assert.assertEquals(js.getDouble("Result.GrandTotal"), 98.4);
						Assert.assertEquals(js.getDouble("Result.PriceDetails[0].Price"), 96.0);
						Assert.assertEquals(js.getDouble("Result.SubTotal"), 96.0);
						Assert.assertEquals(js.getDouble("Result.Tax"), 2.4);
	}
	
	@Test (testName="Item Not Found",description="PBI:155660")
	public void itemNotFound() { 
		
		String customerId = prop.getProperty("availableId");
		int itemId = 99999;
		int quantity = 1;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/package/getpackagepricing/"+customerId+"/"+itemId+"/"+quantity)
						.then()
//						.log().body()
//						.assertThat().statusCode(404)
//						.body("Message", equalTo("Package not found"));
						.assertThat().statusCode(500)
						.body("Message", containsString("The creator of this fault did not specify a Reason."));
	}
	
	@Test (testName="Customer Not Found",description="PBI:155660")
	public void customerNotFound() { 
		
				int customerId = 248000;
				String i = prop.getProperty("taxSingleTId");
				int itemId = Integer.parseInt(i);
				int quantity = 1;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/package/getpackagepricing/"+customerId+"/"+itemId+"/"+quantity)
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.body("Message", equalTo("Customer not found"));
	}

	@Test (testName="Missing Quantity Configuration",description="PBI:155660")
	public void missingQuantityConfiguration() { 
		
		String c = prop.getProperty("availableId");
		int customerId = Integer.parseInt(c);
		String i = prop.getProperty("taxSingleTId");
		int itemId = Integer.parseInt(i);
		int quantity = 0;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/package/getpackagepricing/"+customerId+"/"+itemId+"/"+quantity)
						.then()
//						.log().body()
//						.assertThat().statusCode(400)
//						.body("Message", equalTo("Missing quantity configuration"));
						.assertThat().statusCode(500)
						.body("Message", containsString("The creator of this fault did not specify a Reason."));
	}

	
	@Test (testName="Club Pricing - Club 1",description="PBI:155660", enabled = true)
	public void clubPricingClub1() { 
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String i = prop.getProperty("paidSVClubPriceId");
				int itemId = Integer.parseInt(i);
				int quantity = 1;

		Response res = given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/package/getpackagepricing/"+customerId+"/"+itemId+"/"+quantity)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Result.CanPlaceOnAccount", equalTo(true))
						.body("Result.PriceDetails[0].CorrelationId", not(nullValue()))
						.body("Result.PriceDetails[0].CustomerId", equalTo(customerId))
						.body("Result.PriceDetails[0].IsTaxed", equalTo(false))
						.body("Result.PriceDetails[0].ItemId", equalTo(itemId))
						.extract().response();
		
					JsonPath js = ReusableMethods.rawToJson(res);
						Assert.assertEquals(js.getDouble("Result.GrandTotal"), 10.0);
						Assert.assertEquals(js.getDouble("Result.PriceDetails[0].Price"), 10.0);
						Assert.assertEquals(js.getDouble("Result.SubTotal"), 10.0);
						Assert.assertEquals(js.getDouble("Result.Tax"), 0.0);
	}
	
	@Test (testName="Club Pricing - Club 2",description="PBI:155660", enabled = true)
	public void clubPricingClub2() { 
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String i = prop.getProperty("paidSVClubPriceId");
				int itemId = Integer.parseInt(i);
				int quantity = 1;

		Response res = given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", prop.getProperty("X-Club2Id"))
					.when()
						.get("/api/v3/package/getpackagepricing/"+customerId+"/"+itemId+"/"+quantity)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Result.CanPlaceOnAccount", equalTo(true))
						.body("Result.PriceDetails[0].CorrelationId", not(nullValue()))
						.body("Result.PriceDetails[0].CustomerId", equalTo(customerId))
						.body("Result.PriceDetails[0].IsTaxed", equalTo(false))
						.body("Result.PriceDetails[0].ItemId", equalTo(itemId))
						.extract().response();
		
					JsonPath js = ReusableMethods.rawToJson(res);
						Assert.assertEquals(js.getDouble("Result.GrandTotal"), 11.0);
						Assert.assertEquals(js.getDouble("Result.PriceDetails[0].Price"), 11.0);
						Assert.assertEquals(js.getDouble("Result.SubTotal"), 11.0);
						Assert.assertEquals(js.getDouble("Result.Tax"), 0.0);
	}
	
	@Test (testName="Club Pricing - Club 3",description="PBI:155660", enabled = true)
	public void clubPricingClub3() { 
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String i = prop.getProperty("paidSVClubPriceId");
				int itemId = Integer.parseInt(i);
				int quantity = 1;

		Response res = given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", prop.getProperty("X-Club3Id"))
					.when()
						.get("/api/v3/package/getpackagepricing/"+customerId+"/"+itemId+"/"+quantity)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Result.CanPlaceOnAccount", equalTo(true))
						.body("Result.PriceDetails[0].CorrelationId", not(nullValue()))
						.body("Result.PriceDetails[0].CustomerId", equalTo(customerId))
						.body("Result.PriceDetails[0].IsTaxed", equalTo(false))
						.body("Result.PriceDetails[0].ItemId", equalTo(itemId))
						.extract().response();
		
					JsonPath js = ReusableMethods.rawToJson(res);
						Assert.assertEquals(js.getDouble("Result.GrandTotal"), 12.0);
						Assert.assertEquals(js.getDouble("Result.PriceDetails[0].Price"), 12.0);
						Assert.assertEquals(js.getDouble("Result.SubTotal"), 12.0);
						Assert.assertEquals(js.getDouble("Result.Tax"), 0.0);
	}
	
	@Test (testName="Package Quantity Limit Exceeded",description="PBI:155660", enabled = true)
	public void packageQuantityLimitExceeded() { 
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String i = prop.getProperty("limit10PId");
				int itemId = Integer.parseInt(i);
				int quantity = 15;

		given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", prop.getProperty("X-Club3Id"))
					.when()
						.get("/api/v3/package/getpackagepricing/"+customerId+"/"+itemId+"/"+quantity)
						.then()
//						.log().body()
						.assertThat().statusCode(500)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Status", equalTo(500))
//						.body("Message", equalTo("Missing quantity configuration"));
						.body("Message", containsString("The creator of this fault did not specify a Reason."));
	}
	
}
