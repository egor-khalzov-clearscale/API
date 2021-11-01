package archieve;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import resources.ReusableDates;
import resources.base;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetOnlineAvailableClassesByMember extends base {

    @BeforeClass
    public void getData() {
        base.getPropertyData();
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = prop.getProperty("baseURI");
    }

    @Test(testName = "Classes Found", description = "PBI:144256")
    public void classesFound() {

        String customerId = prop.getProperty("availableId");
        String startDateTime = ReusableDates.getCurrentDate();
        String endDateTime = ReusableDates.getCurrentDatePlusOneWeek();

        given()
//		.log().all()
                .header("accept", prop.getProperty("accept"))
                .header("X-Api-Key", prop.getProperty("X-Api-Key"))
                .header("X-CompanyId", prop.getProperty("X-CompanyId"))
                .header("X-ClubId", prop.getProperty("X-Club1Id"))
                .when()
                .get("/api/v3/classcourse/getonlineavailableclassesbymember/" + customerId + "/" + startDateTime + "/" + endDateTime)
                .then()
//		.log().body()
                .assertThat().statusCode(200)
//		.time(lessThan(60L),TimeUnit.SECONDS)
                .body("Result.ItemBarcodeId", anyOf(hasItem("alwaysAvailCl")))// Item is set to Allow Online Sales
                // using Employee context this is noWebCl is returned
                //.body("Result.ItemBarcodeId", not(anyOf(hasItem("noWebCl"))))// Item is set to NOT Allow Online Sales
                .body("Result.StartDateTime", not(empty()))
                .body("Result.SubstituteInstructorName", not(empty()))
                .body("Result.SubstituteInstructorId", not(empty()))
                .body("Result.ItemDescription", not(empty()))
                .body("Result.ItemId", not(empty()))
                .body("Result.ItemBarcodeId", not(empty()))
                .body("Result.LongDescription", not(empty()))
                .body("Result.DurationInMinutes", not(empty()))
                .body("Result.InstructorName", not(empty()))
                .body("Result.InstructorBarcodeId", not(empty()))
                .body("Result.ClubName", not(empty()))
                .body("Result.ClubNumber", not(empty()))
                .body("Result.CategoryDescription", not(empty()))
                .body("Result.EnrollmentCount", not(empty()))
                .body("Result.StandbyCount", not(empty()))
                .body("Result.MemberEnrollmentStatus", not(empty()))
                .body("Result.ClassCapacity", not(empty()))
                .body("Result.StandbyEnrollmentOnly", not(empty()))
                .body("Result.Price", not(empty()))
                .body("Result.AllowEnrollment", not(empty()))
                .body("Result.AllowServiceDueEnrollment", not(empty()))
                .body("Result.PackageEnrollmentAvailable", not(empty()))
                .body("Result.ServiceVisitId", not(empty()))
                .body("Result.PunchesRequired", not(empty()))
                .body("Result.PunchesRemaining", not(empty()))
                .body("Result.PackageName", not(empty()));
    }

    @Test(testName = "Class Not Found", description = "PBI:144256")
    public void classNotFound() {

        String customerId = prop.getProperty("availableId");
        String startDateTime = "2099-01-01";
        String endDateTime = "2100-01-01";

        given()
//						.log().all()
                .header("accept", prop.getProperty("accept"))
                .header("X-Api-Key", prop.getProperty("X-Api-Key"))
                .header("X-CompanyId", prop.getProperty("X-CompanyId"))
                .header("X-ClubId", prop.getProperty("X-Club1Id"))
                .when()
                .get("/api/v3/classcourse/getonlineavailableclassesbymember/" + customerId + "/" + startDateTime + "/" + endDateTime)
                .then()
//						.log().body()
                .assertThat().statusCode(404)
                .time(lessThan(60L), TimeUnit.SECONDS)
                .body("Message", equalTo("No available online classes found"));
    }

    @Test(testName = "Customer Not Found", description = "PBI:144256")
    public void customerNotFound() {

        int CustomerId = 22300;
        String StartDateTime = ReusableDates.getCurrentDate();
        String EndDateTime = ReusableDates.getCurrentDatePlusOneWeek();

        given()

                .header("accept", prop.getProperty("accept"))
                .header("X-Api-Key", prop.getProperty("X-Api-Key"))
                .header("X-CompanyId", prop.getProperty("X-CompanyId"))
                .header("X-ClubId", prop.getProperty("X-Club1Id"))
                .when()
                .get("/api/v3/classcourse/getonlineavailableclassesbymember/" + CustomerId + "/" + StartDateTime + "/" + EndDateTime)
                .then()
//						.log().body()
                .assertThat().statusCode(404)
                .body("Message", equalTo("Customer not found"));
    }
}
