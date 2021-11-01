package unitTests;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import resources.ReusableDates;
import resources.ReusableMethods;
import resources.base;

public class GetCustomers extends base{
	
	String aPIKey;
	String basePath;
	String companyId;
	String clubId;
	String activeOnly;
	String lastUpdateDate;
	int page;
	int pageSize;
	String customerId;
	String fieldName;
	String newValue;
	String submissionReasonDetail;
	String submissionReason;

	@BeforeClass
	public void getData() {
		base.getPropertyData();
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = prop.getProperty("baseURI");
		aPIKey = prop.getProperty("X-Api-Key");
		
		companyId = prop.getProperty("X-CompanyId");
		clubId = prop.getProperty("club1Id");
		
		activeOnly = "true";
		lastUpdateDate = ReusableDates.getCurrentDate();
		page = 1;
		pageSize = 50;
		
		customerId = prop.getProperty("changeId");
	}
	
	@Test  (testName="Required Fields Only", description="User Story:182454")
	public void requiredFieldsOnly() {

		 Response res =  given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", prop.getProperty("X-Api-Key"))
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", prop.getProperty("X-Club1Id"))
			.when()
				.get("/api/v3/member/getcustomers?ClubId="+clubId+"&ActiveOnly="+activeOnly+"")
			.then()
//				.log().body()
				.body("Customers[0]", hasKey("Address1"))
	       		.body("Customers[0]", hasKey("Address2"))
	       		.body("Customers[0]", hasKey("AllowCharges"))
	       		.body("Customers[0]", hasKey("BarcodeId"))
	       		.body("Customers[0]", hasKey("CellPhone"))
	       		.body("Customers[0]", hasKey("City"))
	       		.body("Customers[0]", hasKey("CreditLimit"))
	       		.body("Customers[0]", hasKey("CurrentBalance"))
	       		.body("Customers[0]", hasKey("DataExchangeID"))
	       		.body("Customers[0]", hasKey("DateOfBirth"))
	       		.body("Customers[0]", hasKey("DisplayName"))
	       		.body("Customers[0]", hasKey("Email"))
	       		.body("Customers[0]", hasKey("EmailContactConsent"))
	       		.body("Customers[0]", hasKey("EmergencyContact"))
	       		.body("Customers[0]", hasKey("EmergencyPhone"))
	       		.body("Customers[0]", hasKey("Employer"))
	       		.body("Customers[0]", hasKey("FirstName"))
	       		.body("Customers[0]", hasKey("Gender"))
	       		.body("Customers[0]", hasKey("GroupName"))
	       		.body("Customers[0]", hasKey("GroupNumber"))
	       		.body("Customers[0]", hasKey("HomeClubName"))
	       		.body("Customers[0]", hasKey("HomeClubNumber"))
	       		.body("Customers[0]", hasKey("HomePhoneContactConsent"))
	       		.body("Customers[0]", hasKey("Id"))
	       		.body("Customers[0]", hasKey("Interests"))
	       		.body("Customers[0]", hasKey("JoinDate"))
	       		.body("Customers[0]", hasKey("Last30DaysCheckinCount"))
	       		.body("Customers[0]", hasKey("Last90DaysCheckinCount"))
	       		.body("Customers[0]", hasKey("LastName"))
	       		.body("Customers[0]", hasKey("LastVisitDate"))
	       		.body("Customers[0]", hasKey("MaritalStatus"))
	       		.body("Customers[0]", hasKey("MarketingSourceDescription"))
	       		.body("Customers[0]", hasKey("MarketingSourceID"))
	       		.body("Customers[0]", hasKey("MemberType"))
	       		.body("Customers[0]", hasKey("MembershipExpires"))
	       		.body("Customers[0]", hasKey("MembershipType"))
	       		.body("Customers[0]", hasKey("MiddleInitial"))
	       		.body("Customers[0]", hasKey("MobilePhoneContactConsent"))
	       		.body("Customers[0]", hasKey("PreferredPhone"))
	       		.body("Customers[0]", hasKey("ReferringCustomerId"))
	       		.body("Customers[0]", hasKey("SalespersonID"))
	       		.body("Customers[0]", hasKey("SalespersonName"))
	       		.body("Customers[0]", hasKey("SourceID"))
	       		.body("Customers[0]", hasKey("State"))
	       		.body("Customers[0]", hasKey("Status"))
	       		.body("Customers[0]", hasKey("StatusDate"))
	       		.body("Customers[0]", hasKey("StatusID"))
	       		.body("Customers[0]", hasKey("StatusReason"))
	       		.body("Customers[0]", hasKey("Title"))
	       		.body("Customers[0]", hasKey("Title"))
	       		.body("Customers[0]", hasKey("UserEntry1"))
	       		.body("Customers[0]", hasKey("UserEntry2"))
	       		.body("Customers[0]", hasKey("UserEntry3"))
	       		.body("Customers[0]", hasKey("UserEntry4"))
	       		.body("Customers[0]", hasKey("UserEntry5"))
	       		.body("Customers[0]", hasKey("UserEntry6"))
	       		.body("Customers[0]", hasKey("UserEntry7"))
	       		.body("Customers[0]", hasKey("UserEntry8"))
	       		.body("Customers[0]", hasKey("UserEntry9"))
	       		.body("Customers[0]", hasKey("UserEntry10"))
	       		.body("Customers[0]", hasKey("UserEntry11"))
	       		.body("Customers[0]", hasKey("UserEntry12"))
	       		.body("Customers[0]", hasKey("UserEntry13"))
	       		.body("Customers[0]", hasKey("UserEntry14"))
	       		.body("Customers[0]", hasKey("UserEntry15"))
	       		.body("Customers[0]", hasKey("UserEntry16"))
	       		.body("Customers[0]", hasKey("WorkPhone"))
	       		.body("Customers[0]", hasKey("WorkPhoneContactConsent"))
	       		.body("Customers[0]", hasKey("ZipCode"))
				.statusCode(200)
				.extract().response();
		 
		 	JsonPath js = ReusableMethods.rawToJson(res);
		 		
		 	Assert.assertFalse(js.getString("Customers.Address1").isBlank());
       		Assert.assertFalse(js.getString("Customers.Address2").isBlank());
       		Assert.assertFalse(js.getString("Customers.AllowCharges").isBlank());
       		Assert.assertFalse(js.getString("Customers.BarcodeId").isBlank());
       		Assert.assertFalse(js.getString("Customers.CellPhone").isBlank());
       		Assert.assertFalse(js.getString("Customers.City").isBlank());
       		Assert.assertFalse(js.getString("Customers.CreditLimit").isBlank());
       		Assert.assertFalse(js.getString("Customers.CurrentBalance").isBlank());
       		Assert.assertFalse(js.getString("Customers.DataExchangeID").isBlank());
       		Assert.assertFalse(js.getString("Customers.DateOfBirth").isBlank());
       		Assert.assertFalse(js.getString("Customers.DisplayName").isBlank());
       		Assert.assertFalse(js.getString("Customers.Email").isBlank());
       		Assert.assertFalse(js.getString("Customers.EmailContactConsent").isBlank());
       		Assert.assertFalse(js.getString("Customers.EmergencyContact").isBlank());
       		Assert.assertFalse(js.getString("Customers.EmergencyPhone").isBlank());
       		Assert.assertFalse(js.getString("Customers.Employer").isBlank());
       		Assert.assertFalse(js.getString("Customers.FirstName").isBlank());
       		Assert.assertFalse(js.getString("Customers.Gender").isBlank());
       		Assert.assertFalse(js.getString("Customers.GroupName").isBlank());
       		Assert.assertFalse(js.getString("Customers.GroupNumber").isBlank());
       		Assert.assertFalse(js.getString("Customers.HomeClubName").isBlank());
       		Assert.assertTrue(js.getString("Customers.HomeClubNumber").contains(clubId));
       		Assert.assertFalse(js.getString("Customers.HomePhoneContactConsent").isBlank());
       		Assert.assertFalse(js.getString("Customers.Id").isBlank());
       		Assert.assertFalse(js.getString("Customers.Interests").isBlank());
       		Assert.assertFalse(js.getString("Customers.JoinDate").isBlank());
       		Assert.assertFalse(js.getString("Customers.Last30DaysCheckinCount").isBlank());
       		Assert.assertFalse(js.getString("Customers.Last90DaysCheckinCount").isBlank());
       		Assert.assertFalse(js.getString("Customers.LastName").isBlank());
       		Assert.assertFalse(js.getString("Customers.LastVisitDate").isBlank());
       		Assert.assertFalse(js.getString("Customers.MaritalStatus").isBlank());
       		Assert.assertFalse(js.getString("Customers.MarketingSourceDescription").isBlank());
       		Assert.assertFalse(js.getString("Customers.MarketingSourceID").isBlank());
       		Assert.assertFalse(js.getString("Customers.MemberType").isBlank());
       		Assert.assertFalse(js.getString("Customers.MembershipExpires").isBlank());
       		Assert.assertFalse(js.getString("Customers.MembershipType").isBlank());
       		Assert.assertFalse(js.getString("Customers.MiddleInitial").isBlank());
       		Assert.assertFalse(js.getString("Customers.MobilePhoneContactConsent").isBlank());
       		Assert.assertFalse(js.getString("Customers.PreferredPhone").isBlank());
       		Assert.assertFalse(js.getString("Customers.ReferringCustomerId").isBlank());
       		Assert.assertFalse(js.getString("Customers.SalespersonID").isBlank());
       		Assert.assertFalse(js.getString("Customers.SalespersonName").isBlank());
       		Assert.assertFalse(js.getString("Customers.SourceID").isBlank());
       		Assert.assertFalse(js.getString("Customers.State").isBlank());
       		Assert.assertFalse(js.getString("Customers.Status").isBlank());
       		Assert.assertFalse(js.getString("Customers.StatusDate").isBlank());
       		Assert.assertFalse(js.getString("Customers.StatusID").isBlank());
       		Assert.assertFalse(js.getString("Customers.StatusReason").isBlank());
       		Assert.assertFalse(js.getString("Customers.Title").isBlank());
       		Assert.assertFalse(js.getString("Customers.Title").isBlank());
       		Assert.assertFalse(js.getString("Customers.UserEntry1").isBlank());
       		Assert.assertFalse(js.getString("Customers.UserEntry2").isBlank());
       		Assert.assertFalse(js.getString("Customers.UserEntry3").isBlank());
       		Assert.assertFalse(js.getString("Customers.UserEntry4").isBlank());
       		Assert.assertFalse(js.getString("Customers.UserEntry5").isBlank());
       		Assert.assertFalse(js.getString("Customers.UserEntry6").isBlank());
       		Assert.assertFalse(js.getString("Customers.UserEntry7").isBlank());
       		Assert.assertFalse(js.getString("Customers.UserEntry8").isBlank());
       		Assert.assertFalse(js.getString("Customers.UserEntry9").isBlank());
       		Assert.assertFalse(js.getString("Customers.UserEntry10").isBlank());
       		Assert.assertFalse(js.getString("Customers.UserEntry11").isBlank());
       		Assert.assertFalse(js.getString("Customers.UserEntry12").isBlank());
       		Assert.assertFalse(js.getString("Customers.UserEntry13").isBlank());
       		Assert.assertFalse(js.getString("Customers.UserEntry14").isBlank());
       		Assert.assertFalse(js.getString("Customers.UserEntry15").isBlank());
       		Assert.assertFalse(js.getString("Customers.UserEntry16").isBlank());
       		Assert.assertFalse(js.getString("Customers.WorkPhone").isBlank());
       		Assert.assertFalse(js.getString("Customers.WorkPhoneContactConsent").isBlank());
       		Assert.assertFalse(js.getString("Customers.ZipCode").isBlank());

       		Assert.assertTrue(js.getInt("PagingInfo.Page") > 0);
       		Assert.assertTrue(js.getInt("PagingInfo.PageSize") > 0);
       		Assert.assertTrue(js.getInt("PagingInfo.TotalPages") > 0);
       		Assert.assertTrue(js.getInt("PagingInfo.TotalRecords") > 0);
	}
	
	@Test  (testName="All Parameters", description="User Story:182454")
	public void allParameters() {
		
		ReusableMethods.setLastUpdateDateToday(aPIKey, companyId, clubId, customerId);
		
		Response res =  given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", prop.getProperty("X-Api-Key"))
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", prop.getProperty("X-Club1Id"))
			.when()
				.get("/api/v3/member/getcustomers?ClubId="+clubId+"&ActiveOnly="+activeOnly+"&LastUpdateDate="+lastUpdateDate+"&Paging.Page="+page+"&Paging.PageSize="+pageSize+"")
			.then()
//				.log().body()
				.statusCode(200)
				.extract().response();
		
		JsonPath js = ReusableMethods.rawToJson(res);
		
			Assert.assertTrue(res.getTime() >= 60L);
			
       		Assert.assertTrue(js.getString("Customers.HomeClubNumber").contains(clubId));
       		
       		Assert.assertTrue(js.getInt("PagingInfo.Page") == page);
       		Assert.assertTrue(js.getInt("PagingInfo.PageSize") > 0);
       		Assert.assertTrue(js.getInt("PagingInfo.TotalPages") > 0);
       		Assert.assertTrue(js.getInt("PagingInfo.TotalRecords") > 0);
			
			
	}

	@Test  (testName="Active Customers Only Found", description="User Story:182454")
	public void activeCustomersOnlyFound() {

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", prop.getProperty("X-Api-Key"))
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", prop.getProperty("X-Club1Id"))
			.when()
				.get("/api/v3/member/getcustomers?ClubId="+clubId+"&ActiveOnly="+activeOnly+"")
			.then()
//				.log().body()
				.body("Customers.Status", not(anyOf(hasItem("Terminate"))))
				.statusCode(200);
	}
	
	@Test  (testName="All Customers  Found", description="User Story:182454")
	public void allCustomersFound() {
		
		String activeOnly = "false";

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", prop.getProperty("X-Api-Key"))
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", prop.getProperty("X-Club1Id"))
			.when()
				.get("/api/v3/member/getcustomers?ClubId="+clubId+"&ActiveOnly="+activeOnly+"&Paging.PageSize=5000")
			.then()
//				.log().body()
				.body("Customers.Status", anyOf(hasItem("Terminate")))
				.statusCode(200);
	}

	@Test  (testName="Club 2 Only", description="User Story:182454")
	public void club2Only() {
		
		String clubId = "1";
		String club2Id = "2";

		Response res =  given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", prop.getProperty("X-Api-Key"))
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", prop.getProperty("X-Club1Id"))
			.when()
				.get("/api/v3/member/getcustomers?ClubId="+club2Id+"&ActiveOnly="+activeOnly+"")
			.then()
//				.log().body()
				.statusCode(200)
				.extract().response();
		
		JsonPath js = ReusableMethods.rawToJson(res);
		
	       		Assert.assertTrue(js.getString("Customers.HomeClubNumber").contains(club2Id));
	       		Assert.assertFalse(js.getString("Customers.HomeClubNumber").contains(clubId));
			    
	}
	
	@Test  (testName="No Customers Found", description="User Story:182454")
	public void noCustomersFound() {
		
		String lastUpdateDate = "2100-01-01";

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", prop.getProperty("X-Api-Key"))
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", prop.getProperty("X-Club1Id"))
			.when()
				.get("/api/v3/member/getcustomers?ClubId="+clubId+"&ActiveOnly="+activeOnly+"&LastUpdateDate="+lastUpdateDate+"&Paging.Page="+page+"&Paging.PageSize="+pageSize+"")
			.then()
//				.log().body()
				.statusCode(400)
				.body("Status", equalTo(204))
			    .body("Message", equalTo("No results returned."));
	}
	
	@Test  (testName="Page Size", description="User Story:182454")
	public void pageSize() {
		
		String lastUpdateDate = "1900-01-01T00:00:00";
		int pageSize = 1;

		Response res =  given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", prop.getProperty("X-Api-Key"))
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", prop.getProperty("X-Club1Id"))
			.when()
				.get("/api/v3/member/getcustomers?ClubId="+clubId+"&ActiveOnly="+activeOnly+"&LastUpdateDate="+lastUpdateDate+"&Paging.Page="+page+"&Paging.PageSize="+pageSize+"")
			.then()
//				.log().body()
				.statusCode(200)
				.extract().response();
		
		JsonPath js = ReusableMethods.rawToJson(res);
		
	       		Assert.assertTrue(js.getInt("PagingInfo.Page") == page);
	       		Assert.assertTrue(js.getInt("PagingInfo.PageSize") == pageSize);
	       		Assert.assertTrue(js.getInt("PagingInfo.TotalPages") > 0);
	       		Assert.assertTrue(js.getInt("PagingInfo.TotalRecords") > 0);
			    
	}

	
	@Test  (testName="Page Count", description="User Story:182454")
	public void pageCount() {
		
		String lastUpdateDate = "1900-01-01T00:00:00";
		int pageSize = 10;
		int page = 2;

		Response res =  given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", prop.getProperty("X-Api-Key"))
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", prop.getProperty("X-Club1Id"))
			.when()
				.get("/api/v3/member/getcustomers?ClubId="+clubId+"&ActiveOnly="+activeOnly+"&LastUpdateDate="+lastUpdateDate+"&Paging.Page="+page+"&Paging.PageSize="+pageSize+"")
			.then()
//				.log().body()
				.statusCode(200)
				.extract().response();
		
		JsonPath js = ReusableMethods.rawToJson(res);
			    
	       		Assert.assertTrue(js.getInt("PagingInfo.Page") == page);
	       		Assert.assertTrue(js.getInt("PagingInfo.PageSize") == pageSize);
	       		Assert.assertTrue(js.getInt("PagingInfo.TotalPages") > 0);
	       		Assert.assertTrue(js.getInt("PagingInfo.TotalRecords") > 0);
	}
	
}
