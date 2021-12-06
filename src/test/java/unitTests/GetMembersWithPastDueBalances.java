package unitTests;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import resources.ReusableMethods;
import resources.base;

public class GetMembersWithPastDueBalances extends base{
	
		static String aPIKey;
		static String companyId;
		static String clubId;
		static String altCompanyId;
		static String asOfDate;
		static String daysPastDue;
		static String membershipTypeId;
		static String membershipType2Id;
		static String customerStatusId;

	@BeforeClass
	public void getData() {
		base.getPropertyData();
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = prop.getProperty("baseURI");
		
		aPIKey = prop.getProperty("X-Api-Key");
		companyId = prop.getProperty("X-CompanyId");//"101";
		altCompanyId = prop.getProperty("X-CompanyId");
		clubId = prop.getProperty("X-Club1Id");

		asOfDate = "2019-06-01";
//		asOfDate = ReusableDates.getCurrentDate();
		daysPastDue = "Days30";
		membershipTypeId = "3";
		membershipType2Id = "5";
		customerStatusId = "4";
	}
	
	@Test  (testName="Past Due 30 Days", description="PBI:150325")
	public void pastDue30Days() {
							
			Response res = 

					given()
//						.log().all()
						.header("accept", "application/json")
						.header("X-Api-Key", aPIKey)
						.header("X-CompanyId", companyId)
						.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/member/getmemberswithpastduebalances?DaysPastDue="+daysPastDue+"")
					.then()
//						.log().body()
						.assertThat().statusCode(200)
						.body("Status", equalTo(200))
						.body("Result[0]", hasKey("CustomerId"))
						.body("Result[0]", hasKey("CustomerBarcodeId"))
						.body("Result[0]", hasKey("HomePhone"))
						.body("Result[0]", hasKey("WorkPhone"))
						.body("Result[0]", hasKey("MobilePhone"))
						.body("Result[0]", hasKey("Email"))
						.body("Result[0]", hasKey("OpenPayments"))
						.body("Result[0]", hasKey("AccountBalance"))
						.body("Result[0]", hasKey("Past120"))
						.body("Result[0]", hasKey("Past90"))
						.body("Result[0]", hasKey("Past60"))
						.body("Result[0]", hasKey("Past30"))
						.body("Result[0]", hasKey("CurrentCharges"))
						.body("Result[0]", hasKey("MembershipType"))
						.body("Result[0]", hasKey("PreferredPhone"))
						.extract().response();
			
						JsonPath js = ReusableMethods.rawToJson(res);		
						
						Assert.assertTrue(res.getTime() >= 60L);
						Assert.assertTrue(js.getDouble("Result[0].Past30") > 0.00);
	}
	
	@Test  (testName="Past Due 60 Days", description="PBI:150325")
	public void pastDue60Days() {
		
					String daysPastDue = "Days60";
					
			Response res = 

					given()
//						.log().all()
						.header("accept", "application/json")
						.header("X-Api-Key", aPIKey)
						.header("X-CompanyId", companyId)
						.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/member/getmemberswithpastduebalances?DaysPastDue="+daysPastDue+"&BalancesAsOfDate="+asOfDate+"")
					.then()
//						.log().body()
						.assertThat().statusCode(200)
						.body("Status", equalTo(200))
						.body("Result[0]", hasKey("CustomerId"))
						.body("Result[0]", hasKey("CustomerBarcodeId"))
						.body("Result[0]", hasKey("HomePhone"))
						.body("Result[0]", hasKey("WorkPhone"))
						.body("Result[0]", hasKey("MobilePhone"))
						.body("Result[0]", hasKey("Email"))
						.body("Result[0]", hasKey("OpenPayments"))
						.body("Result[0]", hasKey("AccountBalance"))
						.body("Result[0]", hasKey("Past120"))
						.body("Result[0]", hasKey("Past90"))
						.body("Result[0]", hasKey("Past60"))
						.body("Result[0]", hasKey("Past30"))
						.body("Result[0]", hasKey("CurrentCharges"))
						.body("Result[0]", hasKey("MembershipType"))
						.body("Result[0]", hasKey("PreferredPhone"))
						.extract().response();
			
						JsonPath js = ReusableMethods.rawToJson(res);		
						
						Assert.assertTrue(res.getTime() >= 60L);
						Assert.assertTrue(js.getDouble("Result[0].Past60") > 0.00);
	}
	
	@Test  (testName="Past Due 90 Days", description="PBI:150325")
	public void pastDue90Days() {
		
						String daysPastDue = "Days90";
					
			Response res = 

					given()
//						.log().all()
						.header("accept", "application/json")
						.header("X-Api-Key", aPIKey)
						.header("X-CompanyId", companyId)
						.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/member/getmemberswithpastduebalances?DaysPastDue="+daysPastDue+"&BalancesAsOfDate="+asOfDate+"")
					.then()
//						.log().body()
						.assertThat().statusCode(200)
						.body("Status", equalTo(200))
						.body("Result[0]", hasKey("CustomerId"))
						.body("Result[0]", hasKey("CustomerBarcodeId"))
						.body("Result[0]", hasKey("HomePhone"))
						.body("Result[0]", hasKey("WorkPhone"))
						.body("Result[0]", hasKey("MobilePhone"))
						.body("Result[0]", hasKey("Email"))
						.body("Result[0]", hasKey("OpenPayments"))
						.body("Result[0]", hasKey("AccountBalance"))
						.body("Result[0]", hasKey("Past120"))
						.body("Result[0]", hasKey("Past90"))
						.body("Result[0]", hasKey("Past60"))
						.body("Result[0]", hasKey("Past30"))
						.body("Result[0]", hasKey("CurrentCharges"))
						.body("Result[0]", hasKey("MembershipType"))
						.body("Result[0]", hasKey("PreferredPhone"))
						.extract().response();
			
						JsonPath js = ReusableMethods.rawToJson(res);		
						
						Assert.assertTrue(res.getTime() >= 60L);
						Assert.assertTrue(js.getDouble("Result[0].Past90") > 0.00);
	}
	
	@Test  (testName="Past Due 120 Days", description="PBI:150325")
	public void pastDue120Days() {
		
						String daysPastDue = "Days120";
					
			Response res = 

					given()
//						.log().all()
						.header("accept", "application/json")
						.header("X-Api-Key", aPIKey)
						.header("X-CompanyId", companyId)
						.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/member/getmemberswithpastduebalances?DaysPastDue="+daysPastDue+"")
					.then()
//						.log().body()
						.assertThat().statusCode(200)
						.body("Status", equalTo(200))
						.body("Result[0]", hasKey("CustomerId"))
						.body("Result[0]", hasKey("CustomerBarcodeId"))
						.body("Result[0]", hasKey("HomePhone"))
						.body("Result[0]", hasKey("WorkPhone"))
						.body("Result[0]", hasKey("MobilePhone"))
						.body("Result[0]", hasKey("Email"))
						.body("Result[0]", hasKey("OpenPayments"))
						.body("Result[0]", hasKey("AccountBalance"))
						.body("Result[0]", hasKey("Past120"))
						.body("Result[0]", hasKey("Past90"))
						.body("Result[0]", hasKey("Past60"))
						.body("Result[0]", hasKey("Past30"))
						.body("Result[0]", hasKey("CurrentCharges"))
						.body("Result[0]", hasKey("MembershipType"))
						.body("Result[0]", hasKey("PreferredPhone"))
						.extract().response();
			
						JsonPath js = ReusableMethods.rawToJson(res);		
						
						Assert.assertTrue(res.getTime() >= 60L);
						Assert.assertTrue(js.getDouble("Result[0].Past120") > 0.00);
	}
	
	@Test  (testName="Multiple Clubs", description="PBI:150325")
	public void multipleClubs() {
		
						String club2Id = prop.getProperty("club2Id");
							
					given()
//						.log().all()
						.header("accept", "application/json")
						.header("X-Api-Key", aPIKey)
						.header("X-CompanyId", companyId)
						.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/member/getmemberswithpastduebalances?ClubIds="+clubId+"&ClubIds="+club2Id+"&DaysPastDue="+daysPastDue+"")
					.then()
//						.log().body()
						.assertThat().statusCode(200)
						.body("Status", equalTo(200))
						.body("Result[0]", hasKey("CustomerId"))
						.body("Result[0]", hasKey("CustomerBarcodeId"))
						.body("Result[0]", hasKey("HomePhone"))
						.body("Result[0]", hasKey("WorkPhone"))
						.body("Result[0]", hasKey("MobilePhone"))
						.body("Result[0]", hasKey("Email"))
						.body("Result[0]", hasKey("OpenPayments"))
						.body("Result[0]", hasKey("AccountBalance"))
						.body("Result[0]", hasKey("Past120"))
						.body("Result[0]", hasKey("Past90"))
						.body("Result[0]", hasKey("Past60"))
						.body("Result[0]", hasKey("Past30"))
						.body("Result[0]", hasKey("CurrentCharges"))
						.body("Result[0]", hasKey("MembershipType"))
						.body("Result[0]", hasKey("PreferredPhone"));
	}
	
	@Test  (testName="Single Customer Status", description="PBI:150325")
	public void singleCustomerStatus() {
							
					given()
//						.log().all()
						.header("accept", "application/json")
						.header("X-Api-Key", aPIKey)
						.header("X-CompanyId", companyId)
						.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/member/getmemberswithpastduebalances?"+customerStatusId+"=1&DaysPastDue="+daysPastDue+"")
					.then()
//						.log().body()
						.assertThat().statusCode(200)
						.body("Status", equalTo(200))
						.body("Result[0]", hasKey("CustomerId"))
						.body("Result[0]", hasKey("CustomerBarcodeId"))
						.body("Result[0]", hasKey("HomePhone"))
						.body("Result[0]", hasKey("WorkPhone"))
						.body("Result[0]", hasKey("MobilePhone"))
						.body("Result[0]", hasKey("Email"))
						.body("Result[0]", hasKey("OpenPayments"))
						.body("Result[0]", hasKey("AccountBalance"))
						.body("Result[0]", hasKey("Past120"))
						.body("Result[0]", hasKey("Past90"))
						.body("Result[0]", hasKey("Past60"))
						.body("Result[0]", hasKey("Past30"))
						.body("Result[0]", hasKey("CurrentCharges"))
						.body("Result[0]", hasKey("MembershipType"))
						.body("Result[0]", hasKey("PreferredPhone"));
	}
	
	@Test  (testName="Multiple Customer Statuses", description="PBI:150325")
	public void MultipleCustomerStatuses() {
		
						String customerStatus2Id = "1";
							
					given()
//						.log().all()
						.header("accept", "application/json")
						.header("X-Api-Key", aPIKey)
						.header("X-CompanyId", companyId)
						.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/member/getmemberswithpastduebalances?MemberStatusIds="+customerStatusId+"&MemberStatusIds="+customerStatus2Id+"&DaysPastDue="+daysPastDue+"")
					.then()
//						.log().body()
						.assertThat().statusCode(200)
						.body("Status", equalTo(200))
						.body("Result[0]", hasKey("CustomerId"))
						.body("Result[0]", hasKey("CustomerBarcodeId"))
						.body("Result[0]", hasKey("HomePhone"))
						.body("Result[0]", hasKey("WorkPhone"))
						.body("Result[0]", hasKey("MobilePhone"))
						.body("Result[0]", hasKey("Email"))
						.body("Result[0]", hasKey("OpenPayments"))
						.body("Result[0]", hasKey("AccountBalance"))
						.body("Result[0]", hasKey("Past120"))
						.body("Result[0]", hasKey("Past90"))
						.body("Result[0]", hasKey("Past60"))
						.body("Result[0]", hasKey("Past30"))
						.body("Result[0]", hasKey("CurrentCharges"))
						.body("Result[0]", hasKey("MembershipType"))
						.body("Result[0]", hasKey("PreferredPhone"));
	}
	
	@Test  (testName="Single Membership Type", description="PBI:150325")
	public void singleMembershipType() {
		
						String companyId = altCompanyId;
							
					given()
//						.log().all()
						.header("accept", "application/json")
						.header("X-Api-Key", aPIKey)
						.header("X-CompanyId", companyId)
						.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/member/getmemberswithpastduebalances?MembershipTypeIds="+membershipTypeId+"&BalancesAsOfDate="+asOfDate+"&DaysPastDue="+daysPastDue+"")
					.then()
//						.log().body()
						.assertThat().statusCode(200)
						.body("Status", equalTo(200))
						.body("Result[0]", hasKey("CustomerId"))
						.body("Result[0]", hasKey("CustomerBarcodeId"))
						.body("Result[0]", hasKey("HomePhone"))
						.body("Result[0]", hasKey("WorkPhone"))
						.body("Result[0]", hasKey("MobilePhone"))
						.body("Result[0]", hasKey("Email"))
						.body("Result[0]", hasKey("OpenPayments"))
						.body("Result[0]", hasKey("AccountBalance"))
						.body("Result[0]", hasKey("Past120"))
						.body("Result[0]", hasKey("Past90"))
						.body("Result[0]", hasKey("Past60"))
						.body("Result[0]", hasKey("Past30"))
						.body("Result[0]", hasKey("CurrentCharges"))
						.body("Result[0]", hasKey("MembershipType"))
						.body("Result[0]", hasKey("PreferredPhone"));
	}
	
	@Test  (testName="Multiple Membership Types", description="PBI:150325")
	public void multipleMembershipTypes() {
				
					
							
					given()
//						.log().all()
						.header("accept", "application/json")
						.header("X-Api-Key", aPIKey)
						.header("X-CompanyId", companyId)
						.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/member/getmemberswithpastduebalances?MembershipTypeIds="+membershipTypeId+"&MembershipTypeIds="+membershipType2Id+"&DaysPastDue="+daysPastDue+"")
						//.get("/api/v3/member/getmemberswithpastduebalances?MembershipTypeIds=3&MembershipTypeIds=5&DaysPastDue=Days30")
						
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.body("Status", equalTo(200))
						.body("Result[0]", hasKey("CustomerId"))
						.body("Result[0]", hasKey("CustomerBarcodeId"))
						.body("Result[0]", hasKey("HomePhone"))
						.body("Result[0]", hasKey("WorkPhone"))
						.body("Result[0]", hasKey("MobilePhone"))
						.body("Result[0]", hasKey("Email"))
						.body("Result[0]", hasKey("OpenPayments"))
						.body("Result[0]", hasKey("AccountBalance"))
						.body("Result[0]", hasKey("Past120"))
						.body("Result[0]", hasKey("Past90"))
						.body("Result[0]", hasKey("Past60"))
						.body("Result[0]", hasKey("Past30"))
						.body("Result[0]", hasKey("CurrentCharges"))
						.body("Result[0]", hasKey("MembershipType"))
						.body("Result[0]", hasKey("PreferredPhone"));
	}
	
	@Test  (testName="All Parameters", description="PBI:150325")
	public void allParameters() {
		
					String companyId = altCompanyId;
							
					given()
//						.log().all()
						.header("accept", "application/json")
						.header("X-Api-Key", aPIKey)
						.header("X-CompanyId", companyId)
						.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/member/getmemberswithpastduebalances?MembershipTypeIds="+membershipTypeId+"&MemberStatusIds="+customerStatusId+"&BalancesAsOfDate="+asOfDate+"&DaysPastDue="+daysPastDue+"")
					.then()
//						.log().body()
						.assertThat().statusCode(200)
						.body("Status", equalTo(200))
						.body("Result[0]", hasKey("CustomerId"))
						.body("Result[0]", hasKey("CustomerBarcodeId"))
						.body("Result[0]", hasKey("HomePhone"))
						.body("Result[0]", hasKey("WorkPhone"))
						.body("Result[0]", hasKey("MobilePhone"))
						.body("Result[0]", hasKey("Email"))
						.body("Result[0]", hasKey("OpenPayments"))
						.body("Result[0]", hasKey("AccountBalance"))
						.body("Result[0]", hasKey("Past120"))
						.body("Result[0]", hasKey("Past90"))
						.body("Result[0]", hasKey("Past60"))
						.body("Result[0]", hasKey("Past30"))
						.body("Result[0]", hasKey("CurrentCharges"))
						.body("Result[0]", hasKey("MembershipType"))
						.body("Result[0]", hasKey("PreferredPhone"));
	}
	
	@Test  (testName="Days Past Due Required - Null", description="PBI:150325")
	public void daysPastDueRequiredNull() {
		
						String daysPastDue = prop.getProperty("NOF"); 
							
					given()
//						.log().all()
						.header("accept", "application/json")
						.header("X-Api-Key", aPIKey)
						.header("X-CompanyId", companyId)
						.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/member/getmemberswithpastduebalances?DaysPastDue="+daysPastDue+"")
					.then()
//						.log().all()
						.assertThat().statusCode(400)
						.body("Status", equalTo(400))
						.body("Message", equalTo("The value 'null' is not valid for DaysPastDue."));
	}
	
	@Test  (testName="Days Past Due Required - Blank", description="PBI:150325")
	public void daysPastDueRequiredBlank() {
		
						String daysPastDue = "";
							
					given()
//						.log().all()
						.header("accept", "application/json")
						.header("X-Api-Key", aPIKey)
						.header("X-CompanyId", companyId)
						.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/member/getmemberswithpastduebalances?DaysPastDue="+daysPastDue+"")
					.then()
//						.log().all()
						.assertThat().statusCode(400)
						.body("Status", equalTo(400))
						.body("Message", equalTo("The value '' is invalid."));
	}
	
	@Test  (testName="Customer Status Not Found", description="PBI:150325")
	public void customerStatusNotFound() {
		
			// this call currently doesn't validate the customer status; PBI didn't require that validation
		
			String customerStatusId = "99999";
							
			Response res = 

					given()
//						.log().all()
						.header("accept", "application/json")
						.header("X-Api-Key", aPIKey)
						.header("X-CompanyId", companyId)
						.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/member/getmemberswithpastduebalances?"+customerStatusId+"=1&DaysPastDue="+daysPastDue+"")
					.then()
//						.log().body()
						.assertThat().statusCode(200)
						.body("Status", equalTo(200))
						.body("Result[0]", hasKey("CustomerId"))
						.body("Result[0]", hasKey("CustomerBarcodeId"))
						.body("Result[0]", hasKey("HomePhone"))
						.body("Result[0]", hasKey("WorkPhone"))
						.body("Result[0]", hasKey("MobilePhone"))
						.body("Result[0]", hasKey("Email"))
						.body("Result[0]", hasKey("OpenPayments"))
						.body("Result[0]", hasKey("AccountBalance"))
						.body("Result[0]", hasKey("Past120"))
						.body("Result[0]", hasKey("Past90"))
						.body("Result[0]", hasKey("Past60"))
						.body("Result[0]", hasKey("Past30"))
						.body("Result[0]", hasKey("CurrentCharges"))
						.body("Result[0]", hasKey("MembershipType"))
						.body("Result[0]", hasKey("PreferredPhone"))
						.extract().response();
			
						JsonPath js = ReusableMethods.rawToJson(res);		
						
						Assert.assertTrue(res.getTime() >= 60L);
						Assert.assertTrue(js.getDouble("Result[0].Past30") > 0.00);
	}
	
	
}
