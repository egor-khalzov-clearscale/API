package unitTests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import resources.ReusableMethods;
import resources.base;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GetMemberActions extends base {

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

    @Test(testName = "Member Actions Found", description = "PBI:165577")
    public void memberActionsFound() {

        Response res =

                given()
//						.log().all()
                        .header("accept", "application/json")
                        .header("X-Api-Key", aPIKey)
                        .header("X-CompanyId", companyId)
                        .header("X-ClubId", clubId)
                        .when()
                        .get("/api/v3/member/getMemberActions")
                        .then()
//						.log().all()
                        .assertThat()
                        .statusCode(200)
                        .body("Status", equalTo(200))
                        .extract().response();

        JsonPath js = ReusableMethods.rawToJson(res);

        Assert.assertTrue(res.getTime() >= 60L);
        Assert.assertNotNull(js.getString("Result[0].ActionDescription"));
        Assert.assertEquals(js.getInt("Result[0].MemberActionId"), 4);
    }

    @Test(testName = "Action Is Nursery Plan Default", description = "PBI:165577")
    public void actionIsNurseryPlanDefault() {

        Response res =

                given()
//						.log().all()
                        .header("accept", "application/json")
                        .header("X-Api-Key", aPIKey)
                        .header("X-CompanyId", companyId)
                        .header("X-ClubId", clubId)
                        .when()
                        .get("/api/v3/member/getMemberActions")
                        .then()
//						.log().body()
                        .assertThat()
                        .statusCode(200)
                        .body("Status", equalTo(200))
                        .extract().response();

        JsonPath js = ReusableMethods.rawToJson(res);

        Assert.assertTrue(res.getTime() >= 60L);
        Assert.assertTrue(js.getString("Result[4].IsNurseryDefault").contentEquals("true"));
    }

    @Test(testName = "Action Is Not Nursery Plan Default", description = "PBI:165577")
    public void actionIsNotNurseryPlanDefault() {

        Response res =

                given()
//						.log().all()
                        .header("accept", "application/json")
                        .header("X-Api-Key", aPIKey)
                        .header("X-CompanyId", companyId)
                        .header("X-ClubId", clubId)
                        .when()
                        .get("/api/v3/member/getMemberActions")
                        .then()
//						.log().body()
                        .assertThat()
                        .statusCode(200)
                        .body("Status", equalTo(200))
                        .extract().response();

        JsonPath js = ReusableMethods.rawToJson(res);

        Assert.assertTrue(res.getTime() >= 60L);
        Assert.assertTrue(js.getString("Result[1].IsNurseryDefault").contentEquals("false"));
    }


}
