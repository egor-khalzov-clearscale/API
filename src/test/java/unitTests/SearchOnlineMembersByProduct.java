package unitTests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.util.concurrent.TimeUnit;

import io.restassured.RestAssured;
import resources.base;

public class SearchOnlineMembersByProduct extends base {	
	
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
	
	@Test (testName="FirstName",description="PBI:139723")
	public void searchOnlineMembers_FirstName() {
		
				String member = prop.getProperty("availableId");
				String associatedClub = prop.getProperty("club1Id");
				String trainingId = prop.getProperty("selectableResourceTrainingId");
				String fName = prop.getProperty("memberSearchFName");

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/member/searchonlinemembersbyproduct/"+member+"/"+fName+"/"+associatedClub+"/"+trainingId)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Result[0]",  hasKey("AddressLine1"))
						.body("Result[0]",  hasKey("AddressLine2"))
						.body("Result[0]",  hasKey("Id"))
						.body("Result[0]",  hasKey("CellPhone"))
						.body("Result[0]",  hasKey("City"))
						.body("Result[0]",  hasKey("Country"))
						.body("Result[0]",  hasKey("DisplayName"))
						.body("Result[0]",  hasKey("EmailAddress"))
						.body("Result[0]",  hasKey("FirstName"))
						.body("Result[0].FirstName", equalTo(fName))
						.body("Result[0]",  hasKey("HomePhone"))
						.body("Result[0]",  hasKey("Id"))
						.body("Result[0]",  hasKey("LastName"))
						.body("Result[0]",  hasKey("MiddleInitial"))
						.body("Result[0]",  hasKey("PostalCode"))
						.body("Result[0]",  hasKey("PreferredName"))
						.body("Result[0]",  hasKey("PreferredPhoneType"))
						.body("Result[0]",  hasKey("StateProvince"))
						.body("Result[0]",  hasKey("WorkPhone"));
	}
	
	@Test (testName="LastName",description="PBI:139723")
	public void searchOnlineMembers_LastName() {
		
				String member = prop.getProperty("availableId");
				String associatedClub = prop.getProperty("club1Id");
				String trainingId = prop.getProperty("selectableResourceTrainingId");
				String lName = prop.getProperty("memberSearchLName");

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/member/searchonlinemembersbyproduct/"+member+"/"+lName+"/"+associatedClub+"/"+trainingId)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Result[0]",  hasKey("AddressLine1"))
						.body("Result[0]",  hasKey("AddressLine2"))
						.body("Result[0]",  hasKey("Id"))
						.body("Result[0]",  hasKey("CellPhone"))
						.body("Result[0]",  hasKey("City"))
						.body("Result[0]",  hasKey("Country"))
						.body("Result[0]",  hasKey("DisplayName"))
						.body("Result[0]",  hasKey("EmailAddress"))
						.body("Result[0]",  hasKey("FirstName"))
						.body("Result[0].LastName", equalTo(lName))
						.body("Result[0]",  hasKey("HomePhone"))
						.body("Result[0]",  hasKey("Id"))
						.body("Result[0]",  hasKey("LastName"))
						.body("Result[0]",  hasKey("MiddleInitial"))
						.body("Result[0]",  hasKey("PostalCode"))
						.body("Result[0]",  hasKey("PreferredName"))
						.body("Result[0]",  hasKey("PreferredPhoneType"))
						.body("Result[0]",  hasKey("StateProvince"))
						.body("Result[0]",  hasKey("WorkPhone"));
	}
	
	@Test (testName="LastFirstName",description="PBI:139723")
	public void searchOnlineMembers_LastFirstName() {
		
				String customerId = prop.getProperty("availableId");
				String associatedClub = prop.getProperty("club1Id");
				String trainingId = prop.getProperty("selectableResourceTrainingId"); 
				String fName = prop.getProperty("memberSearchFName");
				String lName = prop.getProperty("memberSearchLName");

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/member/searchonlinemembersbyproduct/"+customerId+"/"+lName+" "+fName+"/"+associatedClub+"/"+trainingId)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Result[0]",  hasKey("AddressLine1"))
						.body("Result[0]",  hasKey("AddressLine2"))
						.body("Result[0]",  hasKey("Id"))
						.body("Result[0]",  hasKey("CellPhone"))
						.body("Result[0]",  hasKey("City"))
						.body("Result[0]",  hasKey("Country"))
						.body("Result[0]",  hasKey("DisplayName"))
						.body("Result[0]",  hasKey("EmailAddress"))
						.body("Result[0]",  hasKey("FirstName"))
						.body("Result[0]",  hasKey("HomePhone"))
						.body("Result[0]",  hasKey("Id"))
						.body("Result[0]",  hasKey("LastName"))
						.body("Result[0]",  hasKey("MiddleInitial"))
						.body("Result[0]",  hasKey("PostalCode"))
						.body("Result[0]",  hasKey("PreferredName"))
						.body("Result[0]",  hasKey("PreferredPhoneType"))
						.body("Result[0]",  hasKey("StateProvince"))
						.body("Result[0]",  hasKey("WorkPhone"));
	}
	
	@Test (testName="FirstLastName",description="PBI:139723")
	public void searchOnlineMembers_FirstLastName() {
		
				String customerId = prop.getProperty("availableId");
				String associatedClub = prop.getProperty("club1Id");
				String trainingId = prop.getProperty("selectableResourceTrainingId"); 
				String fName = prop.getProperty("memberSearchFName");
				String lName = prop.getProperty("memberSearchLName");

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/member/searchonlinemembersbyproduct/"+customerId+"/"+fName+" "+lName+"/"+associatedClub+"/"+trainingId)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Result[0]",  hasKey("AddressLine1"))
						.body("Result[0]",  hasKey("AddressLine2"))
						.body("Result[0]",  hasKey("Id"))
						.body("Result[0]",  hasKey("CellPhone"))
						.body("Result[0]",  hasKey("City"))
						.body("Result[0]",  hasKey("Country"))
						.body("Result[0]",  hasKey("DisplayName"))
						.body("Result[0]",  hasKey("EmailAddress"))
						.body("Result[0]",  hasKey("FirstName"))
						.body("Result[0]",  hasKey("HomePhone"))
						.body("Result[0]",  hasKey("Id"))
						.body("Result[0]",  hasKey("LastName"))
						.body("Result[0]",  hasKey("MiddleInitial"))
						.body("Result[0]",  hasKey("PostalCode"))
						.body("Result[0]",  hasKey("PreferredName"))
						.body("Result[0]",  hasKey("PreferredPhoneType"))
						.body("Result[0]",  hasKey("StateProvince"))
						.body("Result[0]",  hasKey("WorkPhone"));
	}
	
	@Test (testName="HomePhoneWithDashes",description="PBI:139723")
	public void searchOnlineMembers_HomePhoneWithDashes() {
		
				String customerId = prop.getProperty("availableId");
				String associatedClub = prop.getProperty("club1Id");
				String trainingId = prop.getProperty("selectableResourceTrainingId"); 
				String hPhoneD = prop.getProperty("memberSearchHPhoneD");

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/member/searchonlinemembersbyproduct/"+customerId+"/"+hPhoneD+"/"+associatedClub+"/"+trainingId)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Result[0]",  hasKey("AddressLine1"))
						.body("Result[0]",  hasKey("AddressLine2"))
						.body("Result[0]",  hasKey("Id"))
						.body("Result[0]",  hasKey("CellPhone"))
						.body("Result[0]",  hasKey("City"))
						.body("Result[0]",  hasKey("Country"))
						.body("Result[0]",  hasKey("DisplayName"))
						.body("Result[0]",  hasKey("EmailAddress"))
						.body("Result[0]",  hasKey("FirstName"))
						.body("Result[0].HomePhone", equalTo("6142001000"))
						.body("Result[0]",  hasKey("HomePhone"))
						.body("Result[0]",  hasKey("Id"))
						.body("Result[0]",  hasKey("LastName"))
						.body("Result[0]",  hasKey("MiddleInitial"))
						.body("Result[0]",  hasKey("PostalCode"))
						.body("Result[0]",  hasKey("PreferredName"))
						.body("Result[0]",  hasKey("PreferredPhoneType"))
						.body("Result[0]",  hasKey("StateProvince"))
						.body("Result[0]",  hasKey("WorkPhone"));
	}
	
	@Test (testName="HomePhoneNoDashes",description="PBI:139723")
	public void searchOnlineMembers_HomePhoneNoDashes() {
		
		String customerId = prop.getProperty("memberSearchCustomerId");
		String associatedClub = prop.getProperty("club1Id");
		String trainingId = prop.getProperty("selectableResourceTrainingId"); 
		String hPhone = prop.getProperty("memberSearchHPhone");

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/member/searchonlinemembersbyproduct/"+customerId+"/"+hPhone+"/"+associatedClub+"/"+trainingId)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Result[0]",  hasKey("AddressLine1"))
						.body("Result[0]",  hasKey("AddressLine2"))
						.body("Result[0]",  hasKey("Id"))
						.body("Result[0]",  hasKey("CellPhone"))
						.body("Result[0]",  hasKey("City"))
						.body("Result[0]",  hasKey("Country"))
						.body("Result[0]",  hasKey("DisplayName"))
						.body("Result[0]",  hasKey("EmailAddress"))
						.body("Result[0]",  hasKey("FirstName"))
						.body("Result[0].HomePhone", equalTo(hPhone))
						.body("Result[0]",  hasKey("HomePhone"))
						.body("Result[0]",  hasKey("Id"))
						.body("Result[0]",  hasKey("LastName"))
						.body("Result[0]",  hasKey("MiddleInitial"))
						.body("Result[0]",  hasKey("PostalCode"))
						.body("Result[0]",  hasKey("PreferredName"))
						.body("Result[0]",  hasKey("PreferredPhoneType"))
						.body("Result[0]",  hasKey("StateProvince"))
						.body("Result[0]",  hasKey("WorkPhone"));
	}
	
	@Test (testName="MobilePhoneWithDashes",description="PBI:139723")
	public void searchOnlineMembers_MobilePhoneWithDashes() {
		
		String customerId = prop.getProperty("availableId");
		String associatedClub = prop.getProperty("club1Id");
		String trainingId = prop.getProperty("selectableResourceTrainingId"); 
		String mPhoneD = prop.getProperty("memberSearchMPhoneD");
		String mPhone = prop.getProperty("memberSearchMPhone");

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/member/searchonlinemembersbyproduct/"+customerId+"/"+mPhoneD+"/"+associatedClub+"/"+trainingId)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Result[0]",  hasKey("AddressLine1"))
						.body("Result[0]",  hasKey("AddressLine2"))
						.body("Result[0]",  hasKey("Id"))
						.body("Result[0]",  hasKey("CellPhone"))
						.body("Result[0]",  hasKey("City"))
						.body("Result[0]",  hasKey("Country"))
						.body("Result[0]",  hasKey("DisplayName"))
						.body("Result[0]",  hasKey("EmailAddress"))
						.body("Result[0]",  hasKey("FirstName"))
						.body("Result[0].CellPhone", equalTo(mPhone))
						.body("Result[0]",  hasKey("HomePhone"))
						.body("Result[0]",  hasKey("Id"))
						.body("Result[0]",  hasKey("LastName"))
						.body("Result[0]",  hasKey("MiddleInitial"))
						.body("Result[0]",  hasKey("PostalCode"))
						.body("Result[0]",  hasKey("PreferredName"))
						.body("Result[0]",  hasKey("PreferredPhoneType"))
						.body("Result[0]",  hasKey("StateProvince"))
						.body("Result[0]",  hasKey("WorkPhone"));
	}
	
	@Test (testName="MobilePhoneNoDashes",description="PBI:139723")
	public void searchOnlineMembers_MobilePhoneNoDashes() {
		
		String customerId = prop.getProperty("availableId");
		String associatedClub = prop.getProperty("club1Id");
		String trainingId = prop.getProperty("selectableResourceTrainingId"); 
		String mPhone = prop.getProperty("memberSearchMPhone");

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/member/searchonlinemembersbyproduct/"+customerId+"/"+mPhone+"/"+associatedClub+"/"+trainingId)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Result[0]",  hasKey("AddressLine1"))
						.body("Result[0]",  hasKey("AddressLine2"))
						.body("Result[0]",  hasKey("Id"))
						.body("Result[0]",  hasKey("CellPhone"))
						.body("Result[0]",  hasKey("City"))
						.body("Result[0]",  hasKey("Country"))
						.body("Result[0]",  hasKey("DisplayName"))
						.body("Result[0]",  hasKey("EmailAddress"))
						.body("Result[0]",  hasKey("FirstName"))
						.body("Result[0].CellPhone", equalTo(mPhone))
						.body("Result[0]",  hasKey("HomePhone"))
						.body("Result[0]",  hasKey("Id"))
						.body("Result[0]",  hasKey("LastName"))
						.body("Result[0]",  hasKey("MiddleInitial"))
						.body("Result[0]",  hasKey("PostalCode"))
						.body("Result[0]",  hasKey("PreferredName"))
						.body("Result[0]",  hasKey("PreferredPhoneType"))
						.body("Result[0]",  hasKey("StateProvince"))
						.body("Result[0]",  hasKey("WorkPhone"));
	}
	
	@Test (testName="WorkPhone",description="PBI:139723")
	public void searchOnlineMembers_WorkPhone() {
		
				String customerId = prop.getProperty("availableId");
				String associatedClub = prop.getProperty("club1Id");
				String trainingId = prop.getProperty("selectableResourceTrainingId"); 
				String wPhone = prop.getProperty("memberSearchWPhoneD");

				given()

				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/member/searchonlinemembersbyproduct/"+customerId+"/"+wPhone+"/"+associatedClub+"/"+trainingId)
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Message", equalTo("Nothing found"));
	}
	
	@Test (testName="Email",description="PBI:139723")
	public void searchOnlineMembers_Email() {
		
				String customerId = prop.getProperty("availableId");
				String associatedClub = prop.getProperty("club1Id");
				String trainingId = prop.getProperty("selectableResourceTrainingId");
				String email = prop.getProperty("memberSearcheMail");

				given()

				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/member/searchonlinemembersbyproduct/"+customerId+"/"+email+"/"+associatedClub+"/"+trainingId)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Result[0]",  hasKey("AddressLine1"))
						.body("Result[0]",  hasKey("AddressLine2"))
						.body("Result[0]",  hasKey("Id"))
						.body("Result[0]",  hasKey("CellPhone"))
						.body("Result[0]",  hasKey("City"))
						.body("Result[0]",  hasKey("Country"))
						.body("Result[0]",  hasKey("DisplayName"))
						.body("Result[0]",  hasKey("EmailAddress"))
						.body("Result[0]",  hasKey("FirstName"))
						.body("Result[0].EmailAddress", equalTo(email))
						.body("Result[0]",  hasKey("HomePhone"))
						.body("Result[0]",  hasKey("Id"))
						.body("Result[0]",  hasKey("LastName"))
						.body("Result[0]",  hasKey("MiddleInitial"))
						.body("Result[0]",  hasKey("PostalCode"))
						.body("Result[0]",  hasKey("PreferredName"))
						.body("Result[0]",  hasKey("PreferredPhoneType"))
						.body("Result[0]",  hasKey("StateProvince"))
						.body("Result[0]",  hasKey("WorkPhone"));
	}
	
	@Test (testName="Product Not Found",description="PBI:139723")
	public void productNotFound() {
		
		String customerId = prop.getProperty("availableId");
		String associatedClub = prop.getProperty("club1Id");
		String trainingId = "99999";
		String email = prop.getProperty("memberSearcheMail");

				given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
					.get("/api/v3/member/searchonlinemembersbyproduct/"+customerId+"/"+email+"/"+associatedClub+"/"+trainingId)
						.then()
//						.log().body()
						.assertThat().statusCode(500)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Message", containsString("Sequence contains no elements"));
	}
	
	@Test (testName="Customer Not Found",description="PBI:139723", enabled = true)
	public void customerNotFound() {
	
		String customerId = "99999";
		String associatedClub = prop.getProperty("club1Id");
		String trainingId = prop.getProperty("selectableResourceTrainingId");
		String email = "notreal@email.com";

				given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
					.get("/api/v3/member/searchonlinemembersbyproduct/"+customerId+"/"+email+"/"+associatedClub+"/"+trainingId)
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Message", equalTo("Nothing found"));
	}
	
	@Test (testName="Customer Required",description="PBI:139723")
	public void customerRequired() {
		
		String customerId = prop.getProperty("NOTavailableId");
		String associatedClub = prop.getProperty("club1Id");
		String trainingId = prop.getProperty("selectableResourceTrainingId");
		String email = prop.getProperty("memberSearcheMail");

				given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
					.get("/api/v3/member/searchonlinemembersbyproduct/"+customerId+"/"+email+"/"+associatedClub+"/"+trainingId)
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Message", equalTo("The value 'null' is not valid for CustomerId."));
	}
	
	@Test (testName="Search Parameter Null",description="PBI:139723")
	public void searchParameterNull() {
		
				String customerId = prop.getProperty("availableId");
				String associatedClub = prop.getProperty("club1Id");
				String trainingId = prop.getProperty("selectableResourceTrainingId");
				String searchParameter = prop.getProperty("nullValue");

				given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
					.get("/api/v3/member/searchonlinemembersbyproduct/"+customerId+"/"+searchParameter+"/"+associatedClub+"/"+trainingId)
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Message", equalTo ("Nothing found"));
	}
	
	@Test (testName="Training Required",description="PBI:139723")
	public void trainingRequired() {
		
				String customerId = prop.getProperty("availableId");
				String associatedClub = prop.getProperty("club1Id");
				String trainingId = prop.getProperty("NOTselectableResourceTrainingId");
				String fName = prop.getProperty("memberSearchFName");

				given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
					.get("/api/v3/member/searchonlinemembersbyproduct/"+customerId+"/"+fName+"/"+associatedClub+"/"+trainingId)
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Message", equalTo("The value 'null' is not valid for AppointmentItemId."));
	}
	
	@Test (testName="Associated Club Required",description="PBI:139723")
	public void associatedClubRequired() {
		
				String customerId = prop.getProperty("availableId");
				String associatedClub = prop.getProperty("NOTclub1Id");
				String trainingId = prop.getProperty("selectableResourceTrainingId");
				String fName = prop.getProperty("memberSearchFName");

				given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
					.get("/api/v3/member/searchonlinemembersbyproduct/"+customerId+"/"+fName+"/"+associatedClub+"/"+trainingId)
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Message", equalTo("The value 'null' is not valid for AppointmentClubId."));
	}

}
