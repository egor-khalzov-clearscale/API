package JonasFitness.API;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.lessThan;


import java.util.concurrent.TimeUnit;

import io.restassured.RestAssured;
import resources.base;

public class GetMember extends base{

	@BeforeClass
	public void getData() {
		base.getPropertyData();
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = prop.getProperty("baseURI");
	}
	
	@Test  (testName="MemberFound - Active HOH", description="PBI:124934")
	public void MemberFoundActiveHOH() {
		
		String member = prop.getProperty("availableId");

					given()
//						.log().all()
						.header("accept", prop.getProperty("accept"))
						.header("X-Api-Key", prop.getProperty("X-Api-Key"))
						.header("X-CompanyId", prop.getProperty("X-CompanyId"))
						.header("X-ClubId", prop.getProperty("X-Club1Id"))
					.when()
						.get("/api/v3/member/getmember/"+member)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.time(lessThan(5L),TimeUnit.SECONDS)
					    .body("Result", hasKey("Address"))
					    .body("Result.Address", hasKey("AddressLine1"))
					    .body("Result.Address", hasKey("AddressLine2"))
					    .body("Result.Address", hasKey("City"))
					    .body("Result.Address", hasKey("Country"))
					    .body("Result.Address", hasKey("PostalCode"))
					    .body("Result.Address", hasKey("StateProvince"))
					    .body("Result", hasKey("DateOfBirth"))
					    .body("Result", hasKey("DriversLicenseNumber"))
					    .body("Result", hasKey("EmailAddress"))
					    .body("Result", hasKey("EmailContactConsent"))
					    .body("Result", hasKey("EmergencyContactName"))
					    .body("Result", hasKey("EmergencyContactPhoneNumber"))
					    .body("Result.EmergencyContactPhoneNumber", hasKey("Extension"))
					    .body("Result.EmergencyContactPhoneNumber", hasKey("Number"))
					    .body("Result.EmergencyContactPhoneNumber", hasKey("PhoneType"))
					    .body("Result", hasKey("HeadOfHousehold"))
					    .body("Result", hasKey("HomeClubNumber"))
					    .body("Result", hasKey("HomePhoneContactConsent"))
					    .body("Result.HomePhoneNumber", hasKey("Extension"))
					    .body("Result.HomePhoneNumber", hasKey("Number"))
					    .body("Result.HomePhoneNumber", hasKey("PhoneType"))
					    .body("Result", hasKey("Interests"))
					    .body("Result", hasKey("MemberExpireDate"))
					    .body("Result", hasKey("MemberID"))
					    .body("Result", hasKey("MobilePhoneContactConsent"))
					    .body("Result.MobilePhoneNumber", hasKey("Extension"))
					    .body("Result.MobilePhoneNumber", hasKey("Number"))
					    .body("Result.MobilePhoneNumber", hasKey("PhoneType"))
					    .body("Result", hasKey("Name"))
					    .body("Result.Name", hasKey("DisplayName"))
					    .body("Result.Name", hasKey("FirstName"))
					    .body("Result.Name", hasKey("LastName"))
					    .body("Result.Name", hasKey("MiddleInitial"))
					    .body("Result.Name", hasKey("PreferredName"))
					    .body("Result.PreferredPhoneNumber", hasKey("Extension"))
					    .body("Result.PreferredPhoneNumber", hasKey("Number"))
					    .body("Result.PreferredPhoneNumber", hasKey("PhoneType"))
					    .body("Result", hasKey("PreferredPhoneType"))
					    .body("Result", hasKey("RestrictMemberFromSearch"))
					    .body("Result", hasKey("ValidBarcode"))
					    .body("Result", hasKey("WorkPhoneContactConsent"))
					    .body("Result", hasKey("WorkPhoneNumber"))
					    .body("Result.WorkPhoneNumber", hasKey("Extension"))
					    .body("Result.WorkPhoneNumber", hasKey("Number"))
					    .body("Result.WorkPhoneNumber", hasKey("PhoneType"));
//					** Assert values **					
//					.body("Result.Address.AddressLine1", equalTo("7965 N High St"))
//					.body("Result.Address.AddressLine2", equalTo("Ste 360"))
	}
	
	@Test  (testName="MemberFound - Account Locked", description="PBI:124934")
	public void MemberFoundAccountLocked() {
		
		int member = 232;

					given()

						.header("accept", prop.getProperty("accept"))
						.header("X-Api-Key", prop.getProperty("X-Api-Key"))
						.header("X-CompanyId", prop.getProperty("X-CompanyId"))
						.header("X-ClubId", prop.getProperty("X-Club1Id"))
					.when()
						.get("/api/v3/member/getmember/"+member)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
					    .body("Result", hasKey("Address"))
					    .body("Result.Address", hasKey("AddressLine1"))
					    .body("Result.Address", hasKey("AddressLine2"))
					    .body("Result.Address", hasKey("City"))
					    .body("Result.Address", hasKey("Country"))
					    .body("Result.Address", hasKey("PostalCode"))
					    .body("Result.Address", hasKey("StateProvince"))
					    .body("Result", hasKey("DateOfBirth"))
					    .body("Result", hasKey("DriversLicenseNumber"))
					    .body("Result", hasKey("EmailAddress"))
					    .body("Result", hasKey("EmailContactConsent"))
					    .body("Result", hasKey("EmergencyContactName"))
					    .body("Result", hasKey("EmergencyContactPhoneNumber"))
					    .body("Result.EmergencyContactPhoneNumber", hasKey("Extension"))
					    .body("Result.EmergencyContactPhoneNumber", hasKey("Number"))
					    .body("Result.EmergencyContactPhoneNumber", hasKey("PhoneType"))
					    .body("Result", hasKey("HeadOfHousehold"))
					    .body("Result", hasKey("HomeClubNumber"))
					    .body("Result", hasKey("HomePhoneContactConsent"))
					    .body("Result.HomePhoneNumber", hasKey("Extension"))
					    .body("Result.HomePhoneNumber", hasKey("Number"))
					    .body("Result.HomePhoneNumber", hasKey("PhoneType"))
					    .body("Result", hasKey("Interests"))
					    .body("Result", hasKey("MemberExpireDate"))
					    .body("Result", hasKey("MemberID"))
					    .body("Result", hasKey("MobilePhoneContactConsent"))
					    .body("Result.MobilePhoneNumber", hasKey("Extension"))
					    .body("Result.MobilePhoneNumber", hasKey("Number"))
					    .body("Result.MobilePhoneNumber", hasKey("PhoneType"))
					    .body("Result", hasKey("Name"))
					    .body("Result.Name", hasKey("DisplayName"))
					    .body("Result.Name", hasKey("FirstName"))
					    .body("Result.Name", hasKey("LastName"))
					    .body("Result.Name", hasKey("MiddleInitial"))
					    .body("Result.Name", hasKey("PreferredName"))
					    .body("Result.PreferredPhoneNumber", hasKey("Extension"))
					    .body("Result.PreferredPhoneNumber", hasKey("Number"))
					    .body("Result.PreferredPhoneNumber", hasKey("PhoneType"))
					    .body("Result", hasKey("PreferredPhoneType"))
					    .body("Result", hasKey("RestrictMemberFromSearch"))
					    .body("Result", hasKey("ValidBarcode"))
					    .body("Result", hasKey("WorkPhoneContactConsent"))
					    .body("Result", hasKey("WorkPhoneNumber"))
					    .body("Result.WorkPhoneNumber", hasKey("Extension"))
					    .body("Result.WorkPhoneNumber", hasKey("Number"))
					    .body("Result.WorkPhoneNumber", hasKey("PhoneType"))
						;
	}
	
	@Test  (testName="MemberFound - Terminated Member", description="PBI:124934")
	public void MemberFoundTerminatedMember() {
		
		int member = 249;

					given()

						.header("accept", prop.getProperty("accept"))
						.header("X-Api-Key", prop.getProperty("X-Api-Key"))
						.header("X-CompanyId", prop.getProperty("X-CompanyId"))
						.header("X-ClubId", prop.getProperty("X-Club1Id"))
					.when()
						.get("/api/v3/member/getmember/"+member)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
					    .body("Result", hasKey("Address"))
					    .body("Result.Address", hasKey("AddressLine1"))
					    .body("Result.Address", hasKey("AddressLine2"))
					    .body("Result.Address", hasKey("City"))
					    .body("Result.Address", hasKey("Country"))
					    .body("Result.Address", hasKey("PostalCode"))
					    .body("Result.Address", hasKey("StateProvince"))
					    .body("Result", hasKey("DateOfBirth"))
					    .body("Result", hasKey("DriversLicenseNumber"))
					    .body("Result", hasKey("EmailAddress"))
					    .body("Result", hasKey("EmailContactConsent"))
					    .body("Result", hasKey("EmergencyContactName"))
					    .body("Result", hasKey("EmergencyContactPhoneNumber"))
					    .body("Result.EmergencyContactPhoneNumber", hasKey("Extension"))
					    .body("Result.EmergencyContactPhoneNumber", hasKey("Number"))
					    .body("Result.EmergencyContactPhoneNumber", hasKey("PhoneType"))
					    .body("Result", hasKey("HeadOfHousehold"))
					    .body("Result", hasKey("HomeClubNumber"))
					    .body("Result", hasKey("HomePhoneContactConsent"))
					    .body("Result.HomePhoneNumber", hasKey("Extension"))
					    .body("Result.HomePhoneNumber", hasKey("Number"))
					    .body("Result.HomePhoneNumber", hasKey("PhoneType"))
					    .body("Result", hasKey("Interests"))
					    .body("Result", hasKey("MemberExpireDate"))
					    .body("Result", hasKey("MemberID"))
					    .body("Result", hasKey("MobilePhoneContactConsent"))
					    .body("Result.MobilePhoneNumber", hasKey("Extension"))
					    .body("Result.MobilePhoneNumber", hasKey("Number"))
					    .body("Result.MobilePhoneNumber", hasKey("PhoneType"))
					    .body("Result", hasKey("Name"))
					    .body("Result.Name", hasKey("DisplayName"))
					    .body("Result.Name", hasKey("FirstName"))
					    .body("Result.Name", hasKey("LastName"))
					    .body("Result.Name", hasKey("MiddleInitial"))
					    .body("Result.Name", hasKey("PreferredName"))
					    .body("Result.PreferredPhoneNumber", hasKey("Extension"))
					    .body("Result.PreferredPhoneNumber", hasKey("Number"))
					    .body("Result.PreferredPhoneNumber", hasKey("PhoneType"))
					    .body("Result", hasKey("PreferredPhoneType"))
					    .body("Result", hasKey("RestrictMemberFromSearch"))
					    .body("Result", hasKey("ValidBarcode"))
					    .body("Result", hasKey("WorkPhoneContactConsent"))
					    .body("Result", hasKey("WorkPhoneNumber"))
					    .body("Result.WorkPhoneNumber", hasKey("Extension"))
					    .body("Result.WorkPhoneNumber", hasKey("Number"))
					    .body("Result.WorkPhoneNumber", hasKey("PhoneType"))
						;
	}
	
	@Test  (testName="MemberFound - Prospect Member", description="PBI:124934")
	public void MemberFoundProspectMember() {
		
		int member = 228;

					given()

						.header("accept", prop.getProperty("accept"))
						.header("X-Api-Key", prop.getProperty("X-Api-Key"))
						.header("X-CompanyId", prop.getProperty("X-CompanyId"))
						.header("X-ClubId", prop.getProperty("X-Club1Id"))
					.when()
						.get("/api/v3/member/getmember/"+member)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
					    .body("Result", hasKey("Address"))
					    .body("Result.Address", hasKey("AddressLine1"))
					    .body("Result.Address", hasKey("AddressLine2"))
					    .body("Result.Address", hasKey("City"))
					    .body("Result.Address", hasKey("Country"))
					    .body("Result.Address", hasKey("PostalCode"))
					    .body("Result.Address", hasKey("StateProvince"))
					    .body("Result", hasKey("DateOfBirth"))
					    .body("Result", hasKey("DriversLicenseNumber"))
					    .body("Result", hasKey("EmailAddress"))
					    .body("Result", hasKey("EmailContactConsent"))
					    .body("Result", hasKey("EmergencyContactName"))
					    .body("Result", hasKey("EmergencyContactPhoneNumber"))
					    .body("Result.EmergencyContactPhoneNumber", hasKey("Extension"))
					    .body("Result.EmergencyContactPhoneNumber", hasKey("Number"))
					    .body("Result.EmergencyContactPhoneNumber", hasKey("PhoneType"))
					    .body("Result", hasKey("HeadOfHousehold"))
					    .body("Result", hasKey("HomeClubNumber"))
					    .body("Result", hasKey("HomePhoneContactConsent"))
					    .body("Result.HomePhoneNumber", hasKey("Extension"))
					    .body("Result.HomePhoneNumber", hasKey("Number"))
					    .body("Result.HomePhoneNumber", hasKey("PhoneType"))
					    .body("Result", hasKey("Interests"))
					    .body("Result", hasKey("MemberExpireDate"))
					    .body("Result", hasKey("MemberID"))
					    .body("Result", hasKey("MobilePhoneContactConsent"))
					    .body("Result.MobilePhoneNumber", hasKey("Extension"))
					    .body("Result.MobilePhoneNumber", hasKey("Number"))
					    .body("Result.MobilePhoneNumber", hasKey("PhoneType"))
					    .body("Result", hasKey("Name"))
					    .body("Result.Name", hasKey("DisplayName"))
					    .body("Result.Name", hasKey("FirstName"))
					    .body("Result.Name", hasKey("LastName"))
					    .body("Result.Name", hasKey("MiddleInitial"))
					    .body("Result.Name", hasKey("PreferredName"))
					    .body("Result.PreferredPhoneNumber", hasKey("Extension"))
					    .body("Result.PreferredPhoneNumber", hasKey("Number"))
					    .body("Result.PreferredPhoneNumber", hasKey("PhoneType"))
					    .body("Result", hasKey("PreferredPhoneType"))
					    .body("Result", hasKey("RestrictMemberFromSearch"))
					    .body("Result", hasKey("ValidBarcode"))
					    .body("Result", hasKey("WorkPhoneContactConsent"))
					    .body("Result", hasKey("WorkPhoneNumber"))
					    .body("Result.WorkPhoneNumber", hasKey("Extension"))
					    .body("Result.WorkPhoneNumber", hasKey("Number"))
					    .body("Result.WorkPhoneNumber", hasKey("PhoneType"))
						;
	}
	
	@Test  (testName="MemberFound - Collections Member", description="PBI:124934")
	public void MemberFoundCollectionsMember() {
		
		int member = 227;

					given()

						.header("accept", prop.getProperty("accept"))
						.header("X-Api-Key", prop.getProperty("X-Api-Key"))
						.header("X-CompanyId", prop.getProperty("X-CompanyId"))
						.header("X-ClubId", prop.getProperty("X-Club1Id"))
					.when()
						.get("/api/v3/member/getmember/"+member)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
					    .body("Result", hasKey("Address"))
					    .body("Result.Address", hasKey("AddressLine1"))
					    .body("Result.Address", hasKey("AddressLine2"))
					    .body("Result.Address", hasKey("City"))
					    .body("Result.Address", hasKey("Country"))
					    .body("Result.Address", hasKey("PostalCode"))
					    .body("Result.Address", hasKey("StateProvince"))
					    .body("Result", hasKey("DateOfBirth"))
					    .body("Result", hasKey("DriversLicenseNumber"))
					    .body("Result", hasKey("EmailAddress"))
					    .body("Result", hasKey("EmailContactConsent"))
					    .body("Result", hasKey("EmergencyContactName"))
					    .body("Result", hasKey("EmergencyContactPhoneNumber"))
					    .body("Result.EmergencyContactPhoneNumber", hasKey("Extension"))
					    .body("Result.EmergencyContactPhoneNumber", hasKey("Number"))
					    .body("Result.EmergencyContactPhoneNumber", hasKey("PhoneType"))
					    .body("Result", hasKey("HeadOfHousehold"))
					    .body("Result", hasKey("HomeClubNumber"))
					    .body("Result", hasKey("HomePhoneContactConsent"))
					    .body("Result.HomePhoneNumber", hasKey("Extension"))
					    .body("Result.HomePhoneNumber", hasKey("Number"))
					    .body("Result.HomePhoneNumber", hasKey("PhoneType"))
					    .body("Result", hasKey("Interests"))
					    .body("Result", hasKey("MemberExpireDate"))
					    .body("Result", hasKey("MemberID"))
					    .body("Result", hasKey("MobilePhoneContactConsent"))
					    .body("Result.MobilePhoneNumber", hasKey("Extension"))
					    .body("Result.MobilePhoneNumber", hasKey("Number"))
					    .body("Result.MobilePhoneNumber", hasKey("PhoneType"))
					    .body("Result", hasKey("Name"))
					    .body("Result.Name", hasKey("DisplayName"))
					    .body("Result.Name", hasKey("FirstName"))
					    .body("Result.Name", hasKey("LastName"))
					    .body("Result.Name", hasKey("MiddleInitial"))
					    .body("Result.Name", hasKey("PreferredName"))
					    .body("Result.PreferredPhoneNumber", hasKey("Extension"))
					    .body("Result.PreferredPhoneNumber", hasKey("Number"))
					    .body("Result.PreferredPhoneNumber", hasKey("PhoneType"))
					    .body("Result", hasKey("PreferredPhoneType"))
					    .body("Result", hasKey("RestrictMemberFromSearch"))
					    .body("Result", hasKey("ValidBarcode"))
					    .body("Result", hasKey("WorkPhoneContactConsent"))
					    .body("Result", hasKey("WorkPhoneNumber"))
					    .body("Result.WorkPhoneNumber", hasKey("Extension"))
					    .body("Result.WorkPhoneNumber", hasKey("Number"))
					    .body("Result.WorkPhoneNumber", hasKey("PhoneType"))
						;
	}
	
	@Test  (testName="MemberFound - Frozen Member", description="PBI:124934")
	public void MemberFoundFrozenMember() {
		
		int member = 250;

					given()

						.header("accept", prop.getProperty("accept"))
						.header("X-Api-Key", prop.getProperty("X-Api-Key"))
						.header("X-CompanyId", prop.getProperty("X-CompanyId"))
						.header("X-ClubId", prop.getProperty("X-Club1Id"))
					.when()
						.get("/api/v3/member/getmember/"+member)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
					    .body("Result", hasKey("Address"))
					    .body("Result.Address", hasKey("AddressLine1"))
					    .body("Result.Address", hasKey("AddressLine2"))
					    .body("Result.Address", hasKey("City"))
					    .body("Result.Address", hasKey("Country"))
					    .body("Result.Address", hasKey("PostalCode"))
					    .body("Result.Address", hasKey("StateProvince"))
					    .body("Result", hasKey("DateOfBirth"))
					    .body("Result", hasKey("DriversLicenseNumber"))
					    .body("Result", hasKey("EmailAddress"))
					    .body("Result", hasKey("EmailContactConsent"))
					    .body("Result", hasKey("EmergencyContactName"))
					    .body("Result", hasKey("EmergencyContactPhoneNumber"))
					    .body("Result.EmergencyContactPhoneNumber", hasKey("Extension"))
					    .body("Result.EmergencyContactPhoneNumber", hasKey("Number"))
					    .body("Result.EmergencyContactPhoneNumber", hasKey("PhoneType"))
					    .body("Result", hasKey("HeadOfHousehold"))
					    .body("Result", hasKey("HomeClubNumber"))
					    .body("Result", hasKey("HomePhoneContactConsent"))
					    .body("Result.HomePhoneNumber", hasKey("Extension"))
					    .body("Result.HomePhoneNumber", hasKey("Number"))
					    .body("Result.HomePhoneNumber", hasKey("PhoneType"))
					    .body("Result", hasKey("Interests"))
					    .body("Result", hasKey("MemberExpireDate"))
					    .body("Result", hasKey("MemberID"))
					    .body("Result", hasKey("MobilePhoneContactConsent"))
					    .body("Result.MobilePhoneNumber", hasKey("Extension"))
					    .body("Result.MobilePhoneNumber", hasKey("Number"))
					    .body("Result.MobilePhoneNumber", hasKey("PhoneType"))
					    .body("Result", hasKey("Name"))
					    .body("Result.Name", hasKey("DisplayName"))
					    .body("Result.Name", hasKey("FirstName"))
					    .body("Result.Name", hasKey("LastName"))
					    .body("Result.Name", hasKey("MiddleInitial"))
					    .body("Result.Name", hasKey("PreferredName"))
					    .body("Result.PreferredPhoneNumber", hasKey("Extension"))
					    .body("Result.PreferredPhoneNumber", hasKey("Number"))
					    .body("Result.PreferredPhoneNumber", hasKey("PhoneType"))
					    .body("Result", hasKey("PreferredPhoneType"))
					    .body("Result", hasKey("RestrictMemberFromSearch"))
					    .body("Result", hasKey("ValidBarcode"))
					    .body("Result", hasKey("WorkPhoneContactConsent"))
					    .body("Result", hasKey("WorkPhoneNumber"))
					    .body("Result.WorkPhoneNumber", hasKey("Extension"))
					    .body("Result.WorkPhoneNumber", hasKey("Number"))
					    .body("Result.WorkPhoneNumber", hasKey("PhoneType"))
						;
	}
	
	@Test  (testName="MemberNotFound", description="PBI:124934")
	public void MemberNotFound() {
		
		String member = prop.getProperty("availableId");

					given()
//						.log().all()
						.header("accept", prop.getProperty("accept"))
						.header("X-Api-Key", prop.getProperty("X-Api-Key"))
						.header("X-CompanyId", prop.getProperty("X-CompanyId"))
						.header("X-ClubId", prop.getProperty("X-Club1Id"))
					.when()
						.get("/api/v3/member/getmember/9"+member)
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.time(lessThan(5L),TimeUnit.SECONDS)
					    .body("Message", equalTo("Nothing found"));
	}
}
