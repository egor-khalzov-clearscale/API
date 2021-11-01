package unitTests;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import java.util.concurrent.TimeUnit;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import payloads.BusinessIntelligencePL;
import resources.ReusableMethods;
import resources.base;
import resources.myGets;

public class Vita_SetProjectVitaConfiguration extends base {
	
	static String aPIKey;
	static String companyId;
	static String clubId;
	static String clubName;
	static String additionalClub1Id;
	static String timeFrame;
	static String timeFrameUnits;
	static String isActivated;
	static String clubIsSelected;

	@BeforeClass
	public void getData() {
		base.getPropertyData();
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = prop.getProperty("baseURI");
		
		aPIKey = prop.getProperty("X-Api-Key");
		companyId = prop.getProperty("X-CompanyId");
		clubId = prop.getProperty("X-Club1Id");
		clubName = prop.getProperty("club1Name");
		additionalClub1Id = prop.getProperty("X-Club2Id");
		timeFrame = "1";
		timeFrameUnits = "1";
		isActivated = "true";
		clubIsSelected = "true";
		
	}
	
	@Test (testName="Update All Parameters Test 1",description="PBI:164440")
	public void updateAllParametersTest1() {
		
				String clubIsSelected = "false";
				String timeFrame = "5";
				String timeFrameUnits = "5";
				String isActivated = "false";
				
			given()
				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(BusinessIntelligencePL.SetBusinessIntelligenceConfiguration(clubId, clubName, clubIsSelected, timeFrame, timeFrameUnits, isActivated))
				.post("api/v3/businessintelligence/setbusinessintelligenceconfiguration")
			.then()
			    .log().all()
			    .time(lessThan(60L),TimeUnit.SECONDS)
				.statusCode(200)
				.statusLine("HTTP/1.1 200 OK")
				.body("Status", equalTo(200));

				// ** Validate field was updated correctly
				
		Response res	=	myGets.getProjectConfiguration(aPIKey, companyId, clubId);
	
				JsonPath js = ReusableMethods.rawToJson(res);
				
				Assert.assertEquals(js.getString("Result.ClubSelection[0].ClubId"), clubId);
				Assert.assertEquals(js.getString("Result.ClubSelection[0].ClubName"), clubName);
				Assert.assertEquals(js.getString("Result.ClubSelection[0].IsSelected"), clubIsSelected);
				Assert.assertEquals(js.getString("Result.DataStorageTimeframe"), timeFrame);
				Assert.assertEquals(js.getString("Result.DataStorageTimeframeUnits"), timeFrameUnits);
				Assert.assertEquals(js.getString("Result.IsActivated"), isActivated);	
	}	
	
	@Test (testName="Update All Parameters Test 2",description="PBI:164440")
	public void updateAllParametersTest2() {
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(BusinessIntelligencePL.SetBusinessIntelligenceConfiguration(clubId, clubName, clubIsSelected, timeFrame, timeFrameUnits, isActivated))
				.post("api/v3/businessintelligence/setbusinessintelligenceconfiguration")
			.then()
//			    .log().all()
			    .time(lessThan(60L),TimeUnit.SECONDS)
				.statusCode(200)
				.statusLine("HTTP/1.1 200 OK")
				.body("Status", equalTo(200));

				// ** Validate field was updated correctly
				
		Response res	=	myGets.getProjectConfiguration(aPIKey, companyId, clubId);
	
				JsonPath js = ReusableMethods.rawToJson(res);
			
				Assert.assertEquals(js.getString("Result.ClubSelection[0].ClubId"), clubId);
				Assert.assertEquals(js.getString("Result.ClubSelection[0].ClubName"), clubName);
				Assert.assertEquals(js.getString("Result.ClubSelection[0].IsSelected"), clubIsSelected);
				Assert.assertEquals(js.getString("Result.DataStorageTimeframe"), timeFrame);
				Assert.assertEquals(js.getString("Result.DataStorageTimeframeUnits"), timeFrameUnits);
				Assert.assertEquals(js.getString("Result.IsActivated"), isActivated);
	}	
	
	@Test (testName="Set All Parameters Multiple Clubs",description="PBI:164440")
	public void setAllParametersMultipleClubs() {
		
				String club2Id = prop.getProperty("club2Id");
				String club2Name = prop.getProperty("club2Name");
				String club2IsSelected = "true";
				String clubIsSelected = "false";
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(BusinessIntelligencePL.SetBusinessIntelligenceConfiguration_MultipleClubs(clubId, clubName, clubIsSelected, club2Id, club2Name, club2IsSelected, timeFrame, timeFrameUnits, isActivated))
				.post("api/v3/businessintelligence/setbusinessintelligenceconfiguration")
			.then()
//			    .log().all()
			    .time(lessThan(60L),TimeUnit.SECONDS)
				.statusCode(200)
				.statusLine("HTTP/1.1 200 OK")
				.body("Status", equalTo(200));

				// ** Validate field was updated correctly
				
		Response res	=	myGets.getProjectConfiguration(aPIKey, companyId, clubId);
	
				JsonPath js = ReusableMethods.rawToJson(res);
			
				Assert.assertEquals(js.getString("Result.ClubSelection[0].ClubId"), clubId);
				Assert.assertEquals(js.getString("Result.ClubSelection[0].ClubName"), clubName);
				Assert.assertEquals(js.getString("Result.ClubSelection[0].IsSelected"), clubIsSelected);
				Assert.assertEquals(js.getString("Result.ClubSelection[1].ClubId"), club2Id);
				Assert.assertEquals(js.getString("Result.ClubSelection[1].ClubName"), club2Name);
				Assert.assertEquals(js.getString("Result.ClubSelection[1].IsSelected"), club2IsSelected);
	}	
	
	@Test (testName="Company Is Activated",priority = 2, description="PBI:164440")
	public void companyIsActivated() {
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(BusinessIntelligencePL.SetBusinessIntelligenceConfiguration(clubId, clubName, clubIsSelected, timeFrame, timeFrameUnits, isActivated))
				.post("api/v3/businessintelligence/setbusinessintelligenceconfiguration")
			.then()
//			    .log().all()
			    .time(lessThan(60L),TimeUnit.SECONDS)
				.statusCode(200)
				.statusLine("HTTP/1.1 200 OK")
				.body("Status", equalTo(200));

				// ** Validate field was updated correctly
				
		Response res	=	myGets.getProjectConfiguration(aPIKey, companyId, clubId);
	
				JsonPath js = ReusableMethods.rawToJson(res);
			
				Assert.assertEquals(js.getString("Result.IsActivated"), isActivated);
	}	
	
	@Test (testName="Company Is Deactivated",priority = 1, description="PBI:164440")
	public void companyIsDeactivated() {
		
				String isActivated = "false";
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(BusinessIntelligencePL.SetBusinessIntelligenceConfiguration(clubId, clubName, clubIsSelected, timeFrame, timeFrameUnits, isActivated))
				.post("api/v3/businessintelligence/setbusinessintelligenceconfiguration")
			.then()
//			    .log().all()
			    .time(lessThan(60L),TimeUnit.SECONDS)
				.statusCode(200)
				.statusLine("HTTP/1.1 200 OK")
				.body("Status", equalTo(200));

				// ** Validate field was updated correctly
				
		Response res	=	myGets.getProjectConfiguration(aPIKey, companyId, clubId);
	
				JsonPath js = ReusableMethods.rawToJson(res);
				
				Assert.assertEquals(js.getBoolean("Result.ClubSelection[0].IsSelected"), false); //this is set to false when company is deactivated
				Assert.assertEquals(js.getString("Result.IsActivated"), isActivated);
	}	
	
	@Test (testName="Set Time Frame",description="PBI:164440")
	public void setTimeFrame() {
		
				String timeFrame = "7";
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(BusinessIntelligencePL.SetBusinessIntelligenceConfiguration(clubId, clubName, clubIsSelected, timeFrame, timeFrameUnits, isActivated))
				.post("api/v3/businessintelligence/setbusinessintelligenceconfiguration")
			.then()
//			    .log().all()
			    .time(lessThan(60L),TimeUnit.SECONDS)
				.statusCode(200)
				.statusLine("HTTP/1.1 200 OK")
				.body("Status", equalTo(200));

				// ** Validate field was updated correctly
				
		Response res	=	myGets.getProjectConfiguration(aPIKey, companyId, clubId);
	
				JsonPath js = ReusableMethods.rawToJson(res);
			
				Assert.assertEquals(js.getString("Result.DataStorageTimeframe"), timeFrame);
	}	
	
	@Test (testName="Set Time Frame Units",description="PBI:164440")
	public void setTimeFrameUnits() {
		
				// ** NOTE: only 0 and 1 are valid timeFrameUnits; 0=days, 1=months **
		
				String timeFrameUnits = "0";
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(BusinessIntelligencePL.SetBusinessIntelligenceConfiguration(clubId, clubName, clubIsSelected, timeFrame, timeFrameUnits, isActivated))
				.post("api/v3/businessintelligence/setbusinessintelligenceconfiguration")
			.then()
//			    .log().all()
			    .time(lessThan(60L),TimeUnit.SECONDS)
				.statusCode(200)
				.statusLine("HTTP/1.1 200 OK")
				.body("Status", equalTo(200));

				// ** Validate field was updated correctly
				
		Response res	=	myGets.getProjectConfiguration(aPIKey, companyId, clubId);
	
				JsonPath js = ReusableMethods.rawToJson(res);
			
				Assert.assertEquals(js.getString("Result.DataStorageTimeframeUnits"), timeFrameUnits);
	}	
	
	@Test (testName="Club Is Not Selected",description="PBI:164440")
	public void clubIsNotSelected() {
		
				String clubIsSelected = "false";
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(BusinessIntelligencePL.SetBusinessIntelligenceConfiguration(clubId, clubName, clubIsSelected, timeFrame, timeFrameUnits, isActivated))
				.post("api/v3/businessintelligence/setbusinessintelligenceconfiguration")
			.then()
//			    .log().all()
			    .time(lessThan(60L),TimeUnit.SECONDS)
				.statusCode(200)
				.statusLine("HTTP/1.1 200 OK")
				.body("Status", equalTo(200));

				// ** Validate field was updated correctly
				
		Response res	=	myGets.getProjectConfiguration(aPIKey, companyId, clubId);
	
				JsonPath js = ReusableMethods.rawToJson(res);
			
				Assert.assertEquals(js.getString("Result.ClubSelection[0].IsSelected"), clubIsSelected);
	}	
	
	@Test (testName="Club Is Selected",description="PBI:164440")
	public void clubIsSelected() {
		
				String clubIsSelected = "true";
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(BusinessIntelligencePL.SetBusinessIntelligenceConfiguration(clubId, clubName, clubIsSelected, timeFrame, timeFrameUnits, isActivated))
				.post("api/v3/businessintelligence/setbusinessintelligenceconfiguration")
			.then()
//			    .log().all()
			    .time(lessThan(60L),TimeUnit.SECONDS)
				.statusCode(200)
				.statusLine("HTTP/1.1 200 OK")
				.body("Status", equalTo(200));

				// ** Validate field was updated correctly
				
		Response res	=	myGets.getProjectConfiguration(aPIKey, companyId, clubId);
	
				JsonPath js = ReusableMethods.rawToJson(res);
			
				Assert.assertEquals(js.getString("Result.ClubSelection[0].IsSelected"), clubIsSelected);
	}	
	
	@Test (testName="Club Id Null",description="PBI:164440")
	public void clubIdNull() {
		
				String clubIdNull = prop.getProperty("NOF");
				
				Response res = 
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				//.body(BusinessIntelligencePL.SetBusinessIntelligenceConfiguration(clubId, clubName, clubIsSelected, timeFrame, timeFrameUnits, isActivated))
				.body("{\r\n" + 
						"  \"BusinessIntelligenceConfigurationDto\": {\r\n" + 
						"    \"ClubSelection\": [\r\n" + 
						"      {\r\n" + 
						"        \"ClubId\": "+clubIdNull+",\r\n" + 
						"        \"ClubName\": \""+clubName+"\",\r\n" + 
						"        \"IsSelected\": "+clubIsSelected+"\r\n" + 
						"      }\r\n" + 
						"    ],\r\n" +
						"    \"DataStorageTimeframe\": "+timeFrame+",\r\n" + 
						"    \"DataStorageTimeframeUnits\": "+timeFrameUnits+",\r\n" + 
						"    \"IsActivated\": "+isActivated+"\r\n" + 
						"  }\r\n" + 
						"}")
				.post("api/v3/businessintelligence/setbusinessintelligenceconfiguration")
			.then()
			    .log().all()
			    .time(lessThan(60L),TimeUnit.SECONDS)
				.statusCode(400)
				.statusLine("HTTP/1.1 400 Bad Request")
				.body("Status", equalTo(400))
				.extract().response();
	
				JsonPath js = ReusableMethods.rawToJson(res);
			
				Assert.assertTrue(js.getString("Message").contains("Error converting value {null}"));
	}

	@Test (testName="Club Is Selected Null",description="PBI:164440")
	public void clubIsSelectedNull() {
		
				String clubIsSelected = prop.getProperty("NOF");
				
				Response res = 
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(BusinessIntelligencePL.SetBusinessIntelligenceConfiguration(clubId, clubName, clubIsSelected, timeFrame, timeFrameUnits, isActivated))
				.post("api/v3/businessintelligence/setbusinessintelligenceconfiguration")
			.then()
//			    .log().all()
			    .time(lessThan(60L),TimeUnit.SECONDS)
				.statusCode(400)
				.statusLine("HTTP/1.1 400 Bad Request")
				.body("Status", equalTo(400))
				.extract().response();
	
				JsonPath js = ReusableMethods.rawToJson(res);
			
				Assert.assertTrue(js.getString("Message").contains("Error converting value {null}"));
	}
	
	@Test (testName="Time Frame Null",description="PBI:164440")
	public void timeFrameNull() {
		
				String timeFrame = prop.getProperty("NOF");
				
				Response res = 
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(BusinessIntelligencePL.SetBusinessIntelligenceConfiguration(clubId, clubName, clubIsSelected, timeFrame, timeFrameUnits, isActivated))
				.post("api/v3/businessintelligence/setbusinessintelligenceconfiguration")
			.then()
//			    .log().all()
			    .time(lessThan(60L),TimeUnit.SECONDS)
				.statusCode(400)
				.statusLine("HTTP/1.1 400 Bad Request")
				.body("Status", equalTo(400))
				.extract().response();
	
				JsonPath js = ReusableMethods.rawToJson(res);
			
				Assert.assertTrue(js.getString("Message").contains("Error converting value {null}"));
	}
	
	@Test (testName="Time Frame Units Null",description="PBI:164440")
	public void timeFrameUnitsNull() {
		
				String timeFrameUnits = prop.getProperty("NOF");
				
				Response res = 
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(BusinessIntelligencePL.SetBusinessIntelligenceConfiguration(clubId, clubName, clubIsSelected, timeFrame, timeFrameUnits, isActivated))
				.post("api/v3/businessintelligence/setbusinessintelligenceconfiguration")
			.then()
//			    .log().all()
			    .time(lessThan(60L),TimeUnit.SECONDS)
				.statusCode(400)
				.statusLine("HTTP/1.1 400 Bad Request")
				.body("Status", equalTo(400))
				.extract().response();
	
				JsonPath js = ReusableMethods.rawToJson(res);
			
				Assert.assertTrue(js.getString("Message").contains("Error converting value {null}"));
	}
	
	@Test (testName="Is Activated UnitsNull",description="PBI:164440")
	public void isActivatedNull() {
		
				String isActivated = prop.getProperty("NOF");
				
				Response res = 
				
			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(BusinessIntelligencePL.SetBusinessIntelligenceConfiguration(clubId, clubName, clubIsSelected, timeFrame, timeFrameUnits, isActivated))
				.post("api/v3/businessintelligence/setbusinessintelligenceconfiguration")
			.then()
//			    .log().all()
			    .time(lessThan(60L),TimeUnit.SECONDS)
				.statusCode(400)
				.statusLine("HTTP/1.1 400 Bad Request")
				.body("Status", equalTo(400))
				.extract().response();
	
				JsonPath js = ReusableMethods.rawToJson(res);
			
				Assert.assertTrue(js.getString("Message").contains("Error converting value {null}"));
	}
}
