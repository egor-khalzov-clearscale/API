package archieve;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import resources.base;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetAllPackagesForPurchaseByClub extends base {

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

    @Test(testName = "PackageFound", description = "PBI:143540")
    public void packageFound() {

        String customerId = prop.getProperty("availableId");

        given()
//						.log().all()
                .header("accept", "application/json")
                .header("X-Api-Key", aPIKey)
                .header("X-CompanyId", companyId)
                .header("X-ClubId", clubId)
                .when()
                .get("/api/v3/package/getallpackagesforpurchasebyclub/" + customerId)
                .then()
//					    .log().body()
                .assertThat().statusCode(200)
                .time(lessThan(60L), TimeUnit.SECONDS)
                .body("Result[0]", hasKey("BasePrice"))
                .body("Result[0]", hasKey("CategoryDescription"))
                .body("Result[0]", hasKey("DaysUntilExpiration"))
                .body("Result[0]", hasKey("ItemId"))
                .body("Result[0]", hasKey("ItemDescription"))
                .body("Result[0]", hasKey("ItemId"))
                .body("Result[0]", hasKey("LongDescription"))
                .body("Result[0]", hasKey("PriceRangeDtos"))
                .body("Result[0]", hasKey("PriceRangeDtos"));
    }

    @Test(testName = "OnlineNotAllowed_PackageFound", description = "PBI:143540")
    public void onlineNotAllowed_PackageFound() {

        String customerId = prop.getProperty("availableId");

        given()
//						.log().all()
                .header("accept", "application/json")
                .header("X-Api-Key", aPIKey)
                .header("X-CompanyId", companyId)
                .header("X-ClubId", clubId)
                .when()
                .get("/api/v3/package/getallpackagesforpurchasebyclub/" + customerId)
                .then()
//					.log().body()
                .assertThat().statusCode(200)
//				        .time(lessThan(60L),TimeUnit.SECONDS)
                .body("Result[0]", hasKey("ItemDescription"))
                .body("Result.ItemDescription", anyOf(hasItem("Day Pass")));// assertion that a package that is not allowed for MSS purchase is contained in the response
        // use same package as negative test in getOnlinePackage...
    }

    @Test(testName = "PackageNotFound", description = "PBI:143540")
    public void packageNotFound() {

        String customerId = prop.getProperty("availableId");

        given()
//						.log().all()
                .header("accept", "application/json")
                .header("X-Api-Key", aPIKey)
                .header("X-CompanyId", companyId)
                .header("X-ClubId", clubId)
                .when()
                .get("/api/v3/package/getallpackagesforpurchasebyclub/" + customerId)
                .then()
//						.log().body()
                .assertThat().statusCode(200)
                .time(lessThan(60L), TimeUnit.SECONDS)
                .body("Result[0]", hasKey("ItemDescription"))
                .body("Result.ItemDescription", anyOf(not(hasItem("Not A Real Package"))));// assertion that a specific package that is NOT available at club is NOT found
    }

    @Test(testName = "Customer Not Found", description = "PBI:143540")
    public void customerNotFound() {

        int customerId = 99999;

        given()
                .header("accept", "application/json")
                .header("X-Api-Key", aPIKey)
                .header("X-CompanyId", companyId)
                .header("X-ClubId", clubId)
                .when()
                .get("/api/v3/package/getallpackagesforpurchasebyclub/" + customerId)
                .then()
//					    .log().body()
                .assertThat().statusCode(500)
                .body("Message", equalTo("Internal server error - Customer Not Found"));
    }
}
