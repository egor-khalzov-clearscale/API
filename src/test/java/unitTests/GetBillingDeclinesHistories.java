package unitTests;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import resources.ReusableDates;
import resources.ReusableMethods;
import resources.base;

public class GetBillingDeclinesHistories extends base {
	
	static String aPIKey;
	static String companyId;
	static String clubId;
	static String startDate;
	static String endDate;
	static String minimumDeclinesCount;
	static String returnsType;

	@BeforeClass
	public void getData() {
		base.getPropertyData();
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = prop.getProperty("baseURI");
		
		aPIKey = prop.getProperty("X-Api-Key");
		companyId = prop.getProperty("X-CompanyId");
		clubId = prop.getProperty("X-Club1Id");
		
		startDate = ReusableDates.getCurrentDateMinusXYears(20);
		endDate = ReusableDates.getCurrentDate();
		minimumDeclinesCount = "0";
		returnsType = "All";
		
	}
	
	@Test (testName="All History Found",description="PBI:150328")
	public void allHistoryFound() {
		
		Response res = 	
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.get("/api/v3/reports/getbillingdeclineshistory?StartDate="+startDate+"&EndDate="+endDate+"&ReturnsType="+returnsType+"&ReturnsMinimumCount="+minimumDeclinesCount+"")
			.then()
			    .log().body()
				.statusCode(200)
				.statusLine("HTTP/1.1 200 OK")
				.extract().response();
		
				JsonPath js = ReusableMethods.rawToJson(res);		

				Assert.assertEquals(js.getInt("Status"), 200);
				
				Assert.assertNotNull(js.getString("Result[0].CustomerId"));
				Assert.assertNotNull(js.getString("Result[0].CustomerBarcodeId"));
				Assert.assertNotNull(js.getString("Result[0].CustomerName"));
				Assert.assertNotNull(js.getString("Result[0].ClubName"));
				Assert.assertNotNull(js.getString("Result[0].BillingDeclinesForCustomer[0].ReturnDate"));
				Assert.assertNotNull(js.getString("Result[0].BillingDeclinesForCustomer[0].Amount"));
				Assert.assertTrue(js.getString("Result[0].BillingDeclinesForCustomer[0]").contains("ReturnDescription"));
				Assert.assertNotNull(js.getString("Result[0].BillingDeclinesForCustomer[0].ReturnCode"));
				Assert.assertNotNull(js.getString("Result[0].BillingDeclinesForCustomer[0].DisplayCode"));
				Assert.assertNotNull(js.getString("Result[0].BillingDeclinesForCustomer[0].LateFeeAppliedAmount"));
				Assert.assertNotNull(js.getString("Result[0].BillingDeclinesForCustomer[0].Attendant"));
	}


	@Test (testName="Declines History Found",description="PBI:150328")
	public void declinesHistoryFound() {
		
				String returnsType = "Declines";
		
		Response res = 	
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.get("/api/v3/reports/getbillingdeclineshistory?StartDate="+startDate+"&EndDate="+endDate+"&ReturnsType="+returnsType+"&ReturnsMinimumCount="+minimumDeclinesCount+"")
			.then()
//			    .log().all()
				.statusCode(200)
				.statusLine("HTTP/1.1 200 OK")
				.extract().response();
		
				JsonPath js = ReusableMethods.rawToJson(res);		

				Assert.assertEquals(js.getInt("Status"), 200);
				
				Assert.assertNotNull(js.getString("Result[0].CustomerId"));
				Assert.assertNotNull(js.getString("Result[0].CustomerBarcodeId"));
				Assert.assertNotNull(js.getString("Result[0].CustomerName"));
				Assert.assertNotNull(js.getString("Result[0].ClubName"));
				Assert.assertNotNull(js.getString("Result[0].BillingDeclinesForCustomer[0].ReturnDate"));
				Assert.assertNotNull(js.getString("Result[0].BillingDeclinesForCustomer[0].Amount"));
				Assert.assertTrue(js.getString("Result[0].BillingDeclinesForCustomer[0]").contains("ReturnDescription"));
				Assert.assertNotNull(js.getString("Result[0].BillingDeclinesForCustomer[0].ReturnCode"));
				Assert.assertNotNull(js.getString("Result[0].BillingDeclinesForCustomer[0].DisplayCode"));
				Assert.assertNotNull(js.getString("Result[0].BillingDeclinesForCustomer[0].LateFeeAppliedAmount"));
				Assert.assertNotNull(js.getString("Result[0].BillingDeclinesForCustomer[0].Attendant"));
	}
	
	@Test (testName="History Found For Multiple Clubs",description="PBI:150328")
	public void historyFoundMultipleClubs() {
		
				String club2Id = prop.getProperty("club2Id");
		
		Response res = 	
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.get("/api/v3/reports/getbillingdeclineshistory?StartDate="+startDate+"&EndDate="+endDate+"&ClubIds="+clubId+"&ClubIds="+club2Id+"&ReturnsType="+returnsType+"&ReturnsMinimumCount="+minimumDeclinesCount+"")
			.then()
//			    .log().all()
				.statusCode(200)
				.statusLine("HTTP/1.1 200 OK")
				.extract().response();
		
				JsonPath js = ReusableMethods.rawToJson(res);		

				Assert.assertEquals(js.getInt("Status"), 200);
				
				Assert.assertNotNull(js.getString("Result[0].CustomerId"));
				Assert.assertNotNull(js.getString("Result[0].CustomerBarcodeId"));
				Assert.assertNotNull(js.getString("Result[0].CustomerName"));
				Assert.assertNotNull(js.getString("Result[0].ClubName"));
				Assert.assertNotNull(js.getString("Result[0].BillingDeclinesForCustomer[0].ReturnDate"));
				Assert.assertNotNull(js.getString("Result[0].BillingDeclinesForCustomer[0].Amount"));
				Assert.assertTrue(js.getString("Result[0].BillingDeclinesForCustomer[0]").contains("ReturnDescription"));
				Assert.assertNotNull(js.getString("Result[0].BillingDeclinesForCustomer[0].ReturnCode"));
				Assert.assertNotNull(js.getString("Result[0].BillingDeclinesForCustomer[0].DisplayCode"));
				Assert.assertNotNull(js.getString("Result[0].BillingDeclinesForCustomer[0].LateFeeAppliedAmount"));
				Assert.assertNotNull(js.getString("Result[0].BillingDeclinesForCustomer[0].Attendant"));
	}
	
	@Test (testName="History Found For All Clubs",description="PBI:150328")
	public void historyFoundAllClubs() {
				
		Response res = 	
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.get("/api/v3/reports/getbillingdeclineshistory?StartDate="+startDate+"&EndDate="+endDate+"&ReturnsType="+returnsType+"&ReturnsMinimumCount="+minimumDeclinesCount+"")
			.then()
//			    .log().all()
				.statusCode(200)
				.statusLine("HTTP/1.1 200 OK")
				.extract().response();
		
				JsonPath js = ReusableMethods.rawToJson(res);		

				Assert.assertEquals(js.getInt("Status"), 200);
				
				Assert.assertNotNull(js.getString("Result[0].CustomerId"));
				Assert.assertNotNull(js.getString("Result[0].CustomerBarcodeId"));
				Assert.assertNotNull(js.getString("Result[0].CustomerName"));
				Assert.assertNotNull(js.getString("Result[0].ClubName"));
				Assert.assertNotNull(js.getString("Result[0].BillingDeclinesForCustomer[0].ReturnDate"));
				Assert.assertNotNull(js.getString("Result[0].BillingDeclinesForCustomer[0].Amount"));
				Assert.assertTrue(js.getString("Result[0].BillingDeclinesForCustomer[0]").contains("ReturnDescription"));
				Assert.assertNotNull(js.getString("Result[0].BillingDeclinesForCustomer[0].ReturnCode"));
				Assert.assertNotNull(js.getString("Result[0].BillingDeclinesForCustomer[0].DisplayCode"));
				Assert.assertNotNull(js.getString("Result[0].BillingDeclinesForCustomer[0].LateFeeAppliedAmount"));
				Assert.assertNotNull(js.getString("Result[0].BillingDeclinesForCustomer[0].Attendant"));
	}
	
	@Test (testName="All History Not Found",description="PBI:150328")
	public void allHistoryNotFound() {
		
				String startDate = ReusableDates.getCurrentDateMinusXYears(12);
				String endDate = ReusableDates.getCurrentDateMinusXYears(11);
				
		Response res = 	
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.get("/api/v3/reports/getbillingdeclineshistory?StartDate="+startDate+"&EndDate="+endDate+"&ReturnsType="+returnsType+"&ReturnsMinimumCount="+minimumDeclinesCount+"")
			.then()
//			    .log().all()
				.statusCode(400)
				.statusLine("HTTP/1.1 400 Bad Request")
				.extract().response();
		
				JsonPath js = ReusableMethods.rawToJson(res);		

				Assert.assertEquals(js.getInt("Status"), 204);
				Assert.assertEquals(js.getString("Message"), "No billing declines returned");
	}
	
	@Test (testName="Corrections History Not Found",description="PBI:150328")
	public void correctionsHistoryNotFound() {
		
				String startDate = ReusableDates.getCurrentDateMinusXYears(12);
				String endDate = ReusableDates.getCurrentDateMinusXYears(11);
				String returnsType = "Corrections";
				
		Response res = 	
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.get("/api/v3/reports/getbillingdeclineshistory?StartDate="+startDate+"&EndDate="+endDate+"&ReturnsType="+returnsType+"&ReturnsMinimumCount="+minimumDeclinesCount+"")
			.then()
//			    .log().all()
				.statusCode(400)
				.statusLine("HTTP/1.1 400 Bad Request")
				.extract().response();
		
				JsonPath js = ReusableMethods.rawToJson(res);		

				Assert.assertEquals(js.getInt("Status"), 204);
				Assert.assertEquals(js.getString("Message"), "No billing declines returned");
	}
	
	@Test (testName="Declines History Not Found",description="PBI:150328")
	public void declinesHistoryNotFound() {
		
				String startDate = ReusableDates.getCurrentDateMinusXYears(12);
				String endDate = ReusableDates.getCurrentDateMinusXYears(11);
				String returnsType = "Declines";
				
		Response res = 	
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.get("/api/v3/reports/getbillingdeclineshistory?StartDate="+startDate+"&EndDate="+endDate+"&ReturnsType="+returnsType+"&ReturnsMinimumCount="+minimumDeclinesCount+"")
			.then()
//			    .log().all()
				.statusCode(400)
				.statusLine("HTTP/1.1 400 Bad Request")
				.extract().response();
		
				JsonPath js = ReusableMethods.rawToJson(res);		

				Assert.assertEquals(js.getInt("Status"), 204);
				Assert.assertEquals(js.getString("Message"), "No billing declines returned");
	}
	
	@Test (testName="No History Found Minimum Declines Count",description="PBI:150328")
	public void noHistoryFoundMinimumDeclinesCount() {
		
				String minimumDeclinesCount = "500";
				
		Response res = 	
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.get("/api/v3/reports/getbillingdeclineshistory?StartDate="+startDate+"&EndDate="+endDate+"&ReturnsType="+returnsType+"&ReturnsMinimumCount="+minimumDeclinesCount+"")
			.then()
//			    .log().all()
				.statusCode(400)
				.statusLine("HTTP/1.1 400 Bad Request")
				.extract().response();
		
				JsonPath js = ReusableMethods.rawToJson(res);		

				Assert.assertEquals(js.getInt("Status"), 204);
				Assert.assertEquals(js.getString("Message"), "No billing declines returned");
	}
	
	@Test (testName="Future Date Range",description="PBI:150328")
	public void futureDateRange() {
		
				String startDate = ReusableDates.getCurrentDatePlusXDays(100);
				String endDate = ReusableDates.getCurrentDatePlusXDays(101);
				
		Response res = 	
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.get("/api/v3/reports/getbillingdeclineshistory?StartDate="+startDate+"&EndDate="+endDate+"&ReturnsType="+returnsType+"&ReturnsMinimumCount="+minimumDeclinesCount+"")
			.then()
//			    .log().all()
				.statusCode(400)
				.statusLine("HTTP/1.1 400 Bad Request")
				.extract().response();
		
				JsonPath js = ReusableMethods.rawToJson(res);		

				Assert.assertEquals(js.getInt("Status"), 204);
				Assert.assertEquals(js.getString("Message"), "No billing declines returned");
	}
	
	@Test (testName="Invalid Minimum Declines Count",description="PBI:150328")
	public void invalidMinimumDeclinesCount() {
		
				String minimumDeclinesCount = prop.getProperty("NOF");
				
		Response res = 	
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.get("/api/v3/reports/getbillingdeclineshistory?StartDate="+startDate+"&EndDate="+endDate+"&ReturnsType="+returnsType+"&ReturnsMinimumCount="+minimumDeclinesCount+"")
			.then()
//			    .log().all()
				.statusCode(400)
				.statusLine("HTTP/1.1 400 Bad Request")
				.extract().response();
		
				JsonPath js = ReusableMethods.rawToJson(res);		

				Assert.assertEquals(js.getInt("Status"), 400);
				Assert.assertEquals(js.getString("Message"), "The value '"+minimumDeclinesCount+"' is not valid for ReturnsMinimumCount.");
	}
	
	@Test (testName="Invalid Request Type",description="PBI:150328")
	public void invalidRequestType() {
		
				String returnsType = "Approvals";// not a valid Request Type
				
		Response res = 	
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.get("/api/v3/reports/getbillingdeclineshistory?StartDate="+startDate+"&EndDate="+endDate+"&ReturnsType="+returnsType+"&ReturnsMinimumCount="+minimumDeclinesCount+"")
			.then()
//			    .log().all()
				.statusCode(400)
				.statusLine("HTTP/1.1 400 Bad Request")
				.extract().response();
		
				JsonPath js = ReusableMethods.rawToJson(res);		

				Assert.assertEquals(js.getInt("Status"), 400);
				Assert.assertEquals(js.getString("Message"), "The value '"+returnsType+"' is not valid for ReturnsType.");
	}
	
	@Test (testName="Start Date Required",description="PBI:150328")
	public void startDateRequired() {
		
				String startDate = prop.getProperty("NOFDate");
				
		Response res = 	
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.get("/api/v3/reports/getbillingdeclineshistory?StartDate="+startDate+"&EndDate="+endDate+"&ReturnsType="+returnsType+"&ReturnsMinimumCount="+minimumDeclinesCount+"")
			.then()
//			    .log().all()
				.statusCode(400)
				.statusLine("HTTP/1.1 400 Bad Request")
				.extract().response();
		
				JsonPath js = ReusableMethods.rawToJson(res);		

				Assert.assertEquals(js.getInt("Status"), 400);
				Assert.assertEquals(js.getString("Message"), "The value '"+startDate+"' is not valid for StartDate.");
	}
	
	@Test (testName="End Date Required",description="PBI:150328")
	public void endDateRequired() {
		
				String endDate = prop.getProperty("NOFDate");
				
		Response res = 	
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.get("/api/v3/reports/getbillingdeclineshistory?StartDate="+startDate+"&EndDate="+endDate+"&ReturnsType="+returnsType+"&ReturnsMinimumCount="+minimumDeclinesCount+"")
			.then()
//			    .log().all()
				.statusCode(400)
				.statusLine("HTTP/1.1 400 Bad Request")
				.extract().response();
		
				JsonPath js = ReusableMethods.rawToJson(res);		
				
				Assert.assertEquals(js.getInt("Status"), 400);
				Assert.assertEquals(js.getString("Message"), "The value '"+endDate+"' is not valid for EndDate.");
	}
	
	@Test (testName="End Date Prior to Start Date",description="PBI:150328")
	public void endDatePriorToStartDate() {
		
				String endDate = ReusableDates.getCurrentDateMinusXYears(50);
				
		Response res = 	
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.get("/api/v3/reports/getbillingdeclineshistory?StartDate="+startDate+"&EndDate="+endDate+"&ReturnsType="+returnsType+"&ReturnsMinimumCount="+minimumDeclinesCount+"")
			.then()
//			    .log().all()
				.statusCode(400)
				.statusLine("HTTP/1.1 400 Bad Request")
				.extract().response();
		
				JsonPath js = ReusableMethods.rawToJson(res);		

				Assert.assertEquals(js.getInt("Status"), 500);
				Assert.assertEquals(js.getString("Message"), "StartDate must be less than or equal to EndDate");
	}
	
	
	
	
}
