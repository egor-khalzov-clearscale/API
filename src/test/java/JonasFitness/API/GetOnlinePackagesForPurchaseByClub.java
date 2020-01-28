package JonasFitness.API;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.restassured.RestAssured;
import resources.base;

public class GetOnlinePackagesForPurchaseByClub extends base {
	
	@BeforeTest
	public void getData() {
		base.getPropertyData();
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = prop.getProperty("baseURI");
	}
	@Test (testName="PackagesFound",description="PBI:143537")
	public void PackagesFound() { 
		
		String member = prop.getProperty("activeMember1_CustomerId");
		String club = prop.getProperty("X-Club1Id");

				given()
//						.log().all()
				.header("accept", prop.getProperty("accept"))
				.header("X-Api-Key", prop.getProperty("X-Api-Key"))
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", prop.getProperty("X-Club1Id"))
					.when()
						.get("/api/v3/package/getonlinepackagesforpurchasebyclub/"+member+"/"+club)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
//						.time(lessThan(5L),TimeUnit.SECONDS)
						.body("Result[0].BasePrice", not(nullValue()))
						.body("Result[0].DaysUntilExpiration", not(nullValue()))
						.body("Result[0].ItemBarcodeId", not(nullValue()))
						.body("Result[0].ItemId", not(nullValue()))
						.body("Result[0].RedeemableClubs[0]", not(nullValue()))
						;
	}
	
	@Test (testName="PackageNotAllowed",description="PBI:143537")
	public void PackageNotAllowed() { 
		
		/* 	
		 * This package is not found because the item "PT Orientation" Id 75
		 * is not allowed for MSS (online) purchase
		*/
		
		String member = prop.getProperty("activeMember1_CustomerId");
		String club = prop.getProperty("X-Club1Id");
		
				given()
//						.log().all()
				.header("accept", prop.getProperty("accept"))
				.header("X-Api-Key", prop.getProperty("X-Api-Key"))
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", prop.getProperty("X-Club1Id"))
					.when()
						.get("/api/v3/package/getonlinepackagesforpurchasebyclub/"+member+"/"+club)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
//						.time(lessThan(5L),TimeUnit.SECONDS)
						.body("Result.ItemDescription", not(anyOf(hasItem("PT Orientation"))));
	}
	
	@Test (testName="Customer Not Found",description="PBI:143537")
	public void customerNotFound() { 
		
		int member = 236000;
		String club = prop.getProperty("X-Club1Id");

				given()
				.header("accept", prop.getProperty("accept"))
				.header("X-Api-Key", prop.getProperty("X-Api-Key"))
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", prop.getProperty("X-Club1Id"))
					.when()
						.get("/api/v3/package/getonlinepackagesforpurchasebyclub/"+member+"/"+club)
						.then()
//						.log().body()
						.assertThat().statusCode(500)
						.body("Message", equalTo("Internal server error - Customer Not Found"))
						;
	}
}
