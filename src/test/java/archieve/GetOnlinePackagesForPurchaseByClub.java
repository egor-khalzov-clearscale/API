package archieve;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import resources.base;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetOnlinePackagesForPurchaseByClub extends base {

    @BeforeClass
    public void getData() {
        base.getPropertyData();
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = prop.getProperty("baseURI");
    }

    @Test(testName = "PackagesFound", description = "PBI:143537")
    public void PackagesFound() {

        String customerId = prop.getProperty("availableId");

        given()
//						.log().all()
                .header("accept", "application/json")
                .header("X-Api-Key", prop.getProperty("X-Api-Key"))
                .header("X-CompanyId", prop.getProperty("X-CompanyId"))
                .header("X-ClubId", prop.getProperty("X-Club1Id"))
                .when()
                .get("/api/v3/package/getonlinepackagesforpurchasebyclub/" + customerId)
                .then()
//						.log().body()
                .assertThat().statusCode(200)
                .time(lessThan(60L), TimeUnit.SECONDS)
                .body("Result[0].BasePrice", not(nullValue()))
                .body("Result[0].DaysUntilExpiration", not(nullValue()))
                .body("Result[0].ItemId", not(nullValue()))
                .body("Result[0].ItemId", not(nullValue()))
                .body("Result[0].RedeemableClubs[0]", not(nullValue()));
    }

    @Test(testName = "PackageNotAllowed", description = "PBI:143537")
    public void PackageNotAllowed() {

        /*
         * This package is not found because the item "noWebClass"
         * is not allowed for MSS (online) purchase
         */

        String customerId = prop.getProperty("availableId");

        given()
//						.log().all()
                .header("accept", "application/json")
                .header("X-Api-Key", prop.getProperty("X-Api-Key"))
                .header("X-CompanyId", prop.getProperty("X-CompanyId"))
                .header("X-ClubId", prop.getProperty("X-Club1Id"))
                .when()
                .get("/api/v3/package/getonlinepackagesforpurchasebyclub/" + customerId)
                .then()
//						.log().body()
                .assertThat().statusCode(200)
                .time(lessThan(60L), TimeUnit.SECONDS)
                .body("Result.ItemDescription", not(anyOf(hasItem("noWebClass"))));
    }

    @Test(testName = "Customer Not Found", description = "PBI:143537")
    public void customerNotFound() {

        int customerId = 99999;

        given()
                .header("accept", "application/json")
                .header("X-Api-Key", prop.getProperty("X-Api-Key"))
                .header("X-CompanyId", prop.getProperty("X-CompanyId"))
                .header("X-ClubId", prop.getProperty("X-Club1Id"))
                .when()
                .get("/api/v3/package/getonlinepackagesforpurchasebyclub/" + customerId)
                .then()
//						.log().body()
                .assertThat().statusCode(500)
                .time(lessThan(60L), TimeUnit.SECONDS)
                .body("Message", equalTo("Internal server error - Customer Not Found"));
    }
}
