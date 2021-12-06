package unitTests;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import resources.ReusableMethods;
import resources.base;

public class GetAgreementCategories extends base {
	
	String companyId;
	String valueAssertions;
	
	String agreementCategory1Id;
	String agreementCategory1Description;
	String agreementCategory2Id;
	String agreementCategory2Description;
	String agreementCategory3Id;
	String agreementCategory3Description;
	String agreementCategory4Id;
	String agreementCategory4Description;
	String agreementCategory5Id;
	String agreementCategory5Description;
	String agreementCategory6Id;
	String agreementCategory6Description;

	@BeforeClass
	public void getData() {
		base.getPropertyData();
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = prop.getProperty("baseURI");
		
		valueAssertions = prop.getProperty("valueAssertions");
		
		agreementCategory1Id = prop.getProperty("agreementCategory1Id");
		agreementCategory1Description = prop.getProperty("agreementCategory1Description");
		agreementCategory2Id = prop.getProperty("agreementCategory2Id");
		agreementCategory2Description = prop.getProperty("agreementCategory2Description");
		agreementCategory3Id = prop.getProperty("agreementCategory3Id");
		agreementCategory3Description = prop.getProperty("agreementCategory3Description");
		agreementCategory4Id = prop.getProperty("agreementCategory4Id");
		agreementCategory4Description = prop.getProperty("agreementCategory4Description");
		agreementCategory5Id = prop.getProperty("agreementCategory5Id");
		agreementCategory5Description = prop.getProperty("agreementCategory5Description");
		agreementCategory6Id = prop.getProperty("agreementCategory6Id");
		agreementCategory6Description = prop.getProperty("agreementCategory6Description");
	}
	
	@Test (testName="Agreement Categories Found",description="PBI: 179763")
	public void agreementCategoriesFound() {
		
				Response res = 

				given()
//						.log().all()
						.header("accept", "application/json")
						.header("X-Api-Key", prop.getProperty("X-Api-Key"))
						.header("X-CompanyId", prop.getProperty("X-CompanyId"))
						.header("X-ClubId", prop.getProperty("X-Club1Id"))
					.when()
						.get("/api/v3/agreement/getagreementcategories")
					.then()
//						.log().body()
						.assertThat()
						.statusCode(200)
						.extract().response();
				
						JsonPath js = ReusableMethods.rawToJson(res);

						Assert.assertTrue(res.getTime() >= 60L);
						
						Assert.assertNotNull(js.getString("Result[0].Id"));
						Assert.assertNotNull(js.getString("Result[0].Description"));
						
					if (valueAssertions.equals("true")) {
							// Note: this response is in Alphabetical Order by Description
						Assert.assertEquals(agreementCategory1Id, js.getString("Result[1].Id"));
						Assert.assertEquals(agreementCategory1Description, js.getString("Result[1].Description"));
						Assert.assertEquals(agreementCategory2Id, js.getString("Result[0].Id"));
						Assert.assertEquals(agreementCategory2Description, js.getString("Result[0].Description"));
						Assert.assertEquals(agreementCategory3Id, js.getString("Result[2].Id"));
						Assert.assertEquals(agreementCategory3Description, js.getString("Result[2].Description"));
						Assert.assertEquals(agreementCategory4Id, js.getString("Result[3].Id"));
						Assert.assertEquals(agreementCategory4Description, js.getString("Result[3].Description"));
						Assert.assertEquals(agreementCategory5Id, js.getString("Result[5].Id"));
						Assert.assertEquals(agreementCategory5Description, js.getString("Result[5].Description"));
						Assert.assertEquals(agreementCategory6Id, js.getString("Result[4].Id"));
						Assert.assertEquals(agreementCategory6Description, js.getString("Result[4].Description"));
						}			

	}		
	
	@Test (testName="Inactive Club",description="PBI: 179763")
	public void inactiveClub() {
		
				String clubId = "9";

				given()
//						.log().all()
						.header("accept", "application/json")
						.header("X-Api-Key", prop.getProperty("X-Api-Key"))
						.header("X-CompanyId", prop.getProperty("X-CompanyId"))
						.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/agreement/getagreementcategories")
					.then()
//						.log().body()
						.assertThat()
						.statusCode(401)
						.body("Message", equalTo("Invalid authorization credentials (Inactive Club)"));
						}			

	@Test (testName="Not Permitted",description="PBI: 179763")
	public void notPermitted() {
		
				String clubId = "8";

				given()
//						.log().all()
						.header("accept", "application/json")
						.header("X-Api-Key", prop.getProperty("X-Api-Key"))
						.header("X-CompanyId", prop.getProperty("X-CompanyId"))
						.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/agreement/getagreementcategories")
					.then()
//						.log().body()
						.assertThat()
						.statusCode(405)
						.body("Message", equalTo("Caller is requesting a service that either does not exist or to which caller does not have authority to execute"));
						}	
}
