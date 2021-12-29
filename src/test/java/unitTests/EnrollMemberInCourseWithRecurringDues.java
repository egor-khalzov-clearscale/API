package unitTests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import resources.ReusableMethods;
import resources.base;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class EnrollMemberInCourseWithRecurringDues extends base {

    static String aPIKey;
    static String companyId;
    static String clubId;
    static Boolean onlineEnrollment = true;

    @BeforeClass
    public void getData() {
        base.getPropertyData();
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = prop.getProperty("baseURI");

        aPIKey = prop.getProperty("X-Api-Key");
        companyId = prop.getProperty("X-CompanyId");
        clubId = prop.getProperty("X-Club1Id");
    }

    @Test(testName = "Member Enrolled - Paid Course", description = "PBI:154260", enabled = true)
    public void memberEnrolledPaidCourse() {

        String c = prop.getProperty("availableId");
        int customerId = Integer.parseInt(c);

        String courseId = prop.getProperty("recurringCoId");
        Boolean enrollCustomerAsStandby = true;

        if (ReusableMethods.deleteIfEnrolled(customerId) == false) {

            Response res = given()
//						.log().all()
                    .header("accept", "application/json")
                    .header("X-Api-Key", aPIKey)
                    .header("X-CompanyId", companyId)
                    .header("X-ClubId", clubId)
                    .when()
                    .get("/api/v3/classcourse/enrollmemberincoursewithrecurringdues/" + customerId + "/" + courseId + "/" + enrollCustomerAsStandby + "/" + onlineEnrollment)
                    .then()
//						.log().body()
                    .assertThat().statusCode(200)
                    .time(lessThan(60L), TimeUnit.SECONDS)
                    .body("Result.Enrolled", equalTo(true))
                    .body("Result.EnrollmentStatus", equalTo("Enrolled"))
                    .body("Result.CustomerId", equalTo(customerId))
                    .body("Result.FirstName", not(nullValue()))
                    .body("Result.LastName", not(nullValue()))
                    .body("Result", hasKey("MiddleInitial"))
                    .body("Result.DisplayName", not(nullValue()))
                    .body("Result.PreferredName", not(nullValue()))
                    .extract().response();

            JsonPath js = ReusableMethods.rawToJson(res);
            int enrollmentId = js.getInt("Result.EnrollmentId");
            int invoiceId = js.getInt("Result.InvoiceId");

            if (res.statusCode() == 200) {
                ReusableMethods.deleteEnrollment(companyId, enrollmentId, customerId);
                ReusableMethods.deleteInvoice(companyId, enrollmentId, invoiceId, customerId);
            }
        } else {
            Assert.fail("failing test because isEnrolled() == true"); //failing test because isEnrolled() == true
        }
    }

    @Test(testName = "Member Enrolled - Free Course", description = "PBI:154260")
    public void memberEnrolledFreeCourse() {

        String c = prop.getProperty("availableId");
        int customerId = Integer.parseInt(c);

        String courseId = prop.getProperty("freeCoId");
        Boolean enrollCustomerAsStandby = true;

        if (ReusableMethods.deleteIfEnrolled(customerId) == false) {

            Response res = given()
//						.log().all()
                    .header("accept", "application/json")
                    .header("X-Api-Key", aPIKey)
                    .header("X-CompanyId", companyId)
                    .header("X-ClubId", clubId)
                    .when()
                    .get("/api/v3/classcourse/enrollmemberincoursewithrecurringdues/" + customerId + "/" + courseId + "/" + enrollCustomerAsStandby + "/" + onlineEnrollment)
                    .then()
//						.log().body()
                    .assertThat().statusCode(200)
                    .body("Result.Enrolled", equalTo(true))
                    .body("Result.EnrollmentStatus", equalTo("Enrolled"))
                    .body("Result.CustomerId", equalTo(customerId))
                    .body("Result.FirstName", not(nullValue()))
                    .body("Result.LastName", not(nullValue()))
                    .body("Result", hasKey("MiddleInitial"))
                    .body("Result.DisplayName", not(nullValue()))
                    .body("Result.PreferredName", not(nullValue()))
                    .extract().response();

            JsonPath js = ReusableMethods.rawToJson(res);
            int enrollmentId = js.getInt("Result.EnrollmentId");
            int invoiceId = js.getInt("Result.InvoiceId");

            if (res.statusCode() == 200) {
                ReusableMethods.deleteEnrollment(companyId, enrollmentId, customerId);
                ReusableMethods.deleteInvoice(companyId, enrollmentId, invoiceId, customerId);
            }
        } else {
            Assert.fail("failing test because isEnrolled() == true"); //failing test because isEnrolled() == true
        }
    }

    @Test(testName = "Member Enrolled - Free Course - Collections Member", description = "PBI:154260")
    public void memberEnrolledFreeCourseCollectionsMember() {

        String c = prop.getProperty("collectionsId");
        int customerId = Integer.parseInt(c);

        String courseId = prop.getProperty("freeCoId");
        boolean enrollCustomerAsStandby = true;

        if (!ReusableMethods.deleteIfEnrolled(customerId)) {

            Response res = given()

                    .header("accept", "application/json")
                    .header("X-Api-Key", aPIKey)
                    .header("X-CompanyId", companyId)
                    .header("X-ClubId", clubId)
                    .when()
                    .get("/api/v3/classcourse/enrollmemberincoursewithrecurringdues/" + customerId + "/" + courseId + "/" + enrollCustomerAsStandby + "/" + onlineEnrollment)
                    .then()
//						.log().body()
                    .assertThat().statusCode(200)
                    .body("Result.Enrolled", equalTo(true))
                    .body("Result.EnrollmentStatus", equalTo("Enrolled"))
                    .body("Result.CustomerId", equalTo(customerId))
                    .body("Result.FirstName", not(nullValue()))
                    .body("Result.LastName", not(nullValue()))
                    .body("Result", hasKey("MiddleInitial"))
                    .body("Result.DisplayName", not(nullValue()))
                    .body("Result.PreferredName", not(nullValue()))
                    .extract().response();

            JsonPath js = ReusableMethods.rawToJson(res);
            int enrollmentId = js.getInt("Result.EnrollmentId");
            int invoiceId = js.getInt("Result.InvoiceId");

            if (res.statusCode() == 200) {
                ReusableMethods.deleteEnrollment(companyId, enrollmentId, customerId);
                ReusableMethods.deleteInvoice(companyId, enrollmentId, invoiceId, customerId);
            }
        } else {
			Assert.fail("failing test because isEnrolled() == true"); //failing test because isEnrolled() == true
        }
    }

    @Test(testName = "Member Enrolled On Standby", description = "PBI:154260")
    public void memberEnrolledOnStandby() {

        String c = prop.getProperty("availableId");
        int customerId = Integer.parseInt(c);

        String courseId = prop.getProperty("standbyCoId");
        Boolean enrollCustomerAsStandby = true;

        if (ReusableMethods.deleteIfEnrolled(customerId) == false) {

            Response res = given()

                    .header("accept", "application/json")
                    .header("X-Api-Key", aPIKey)
                    .header("X-CompanyId", companyId)
                    .header("X-ClubId", clubId)
                    .when()
                    .get("/api/v3/classcourse/enrollmemberincoursewithrecurringdues/" + customerId + "/" + courseId + "/" + enrollCustomerAsStandby + "/" + onlineEnrollment)
                    .then()
//						.log().body()
                    .assertThat().statusCode(200)
                    .body("Result.EnrollmentStatus", equalTo("StandBy"))
                    .body("Result.CustomerId", equalTo(customerId))
                    .body("Result.FirstName", not(nullValue()))
                    .body("Result.LastName", not(nullValue()))
                    .body("Result", hasKey("MiddleInitial"))
                    .body("Result.DisplayName", not(nullValue()))
                    .body("Result.PreferredName", not(nullValue()))
                    .extract().response();

            JsonPath js = ReusableMethods.rawToJson(res);
            int enrollmentId = js.getInt("Result.EnrollmentId");
            int invoiceId = js.getInt("Result.InvoiceId");

            if (res.statusCode() == 200) {
                ReusableMethods.deleteEnrollment(companyId, enrollmentId, customerId);
                ReusableMethods.deleteInvoice(companyId, enrollmentId, invoiceId, customerId);
            }
        } else {
            Assert.fail("failing test because isEnrolled() == true"); //failing test because isEnrolled() == true
        }
    }

    @Test(testName = "Member Already Enrolled", description = "PBI:154260")
    public void memberAlreadyEnrolled() {

        String c = prop.getProperty("standbyAId");
        int customerId = Integer.parseInt(c);
        String courseId = prop.getProperty("standbyCoId");
        Boolean enrollCustomerAsStandby = true;


        given()
//						.log().all()
                .header("accept", "application/json")
                .header("X-Api-Key", aPIKey)
                .header("X-CompanyId", companyId)
                .header("X-ClubId", clubId)
                .when()
                .get("/api/v3/classcourse/enrollmemberincoursewithrecurringdues/" + customerId + "/" + courseId + "/" + enrollCustomerAsStandby + "/" + onlineEnrollment)
                .then()
//						.log().body()
                .assertThat().statusCode(400)
                .body("Message", equalTo("CustomerAlreadyEnrolled"));
    }

    @Test(testName = "Member Already Enrolled On Standby", description = "PBI:154260")
    public void memberAlreadyEnrolledOnStandby() {

        String c = prop.getProperty("standbyBId");
        int customerId = Integer.parseInt(c);
        String courseId = prop.getProperty("standbyCoId");
        Boolean enrollCustomerAsStandby = true;

        given()

                .header("accept", "application/json")
                .header("X-Api-Key", aPIKey)
                .header("X-CompanyId", companyId)
                .header("X-ClubId", clubId)
                .when()
                .get("/api/v3/classcourse/enrollmemberincoursewithrecurringdues/" + customerId + "/" + courseId + "/" + enrollCustomerAsStandby + "/" + onlineEnrollment)
                .then()
//						.log().body()
                .assertThat().statusCode(400)
                .body("Message", equalTo("CustomerAlreadyOnStandby"));
    }

    @Test(testName = "Member Enrolled Not On Standby", description = "PBI:154260")
    public void memberEnrolledNotOnStandby() {

        String c = prop.getProperty("availableId");
        int customerId = Integer.parseInt(c);
        String courseId = prop.getProperty("standbyCoId");
        String enrollCustomerAsStandBy = "false";

        given()
//						.log().all()
                .header("accept", "application/json")
                .header("X-Api-Key", aPIKey)
                .header("X-CompanyId", companyId)
                .header("X-ClubId", clubId)
                .when()
                .get("/api/v3/classcourse/enrollmemberincoursewithrecurringdues/" + customerId + "/" + courseId + "/" + enrollCustomerAsStandBy + "/" + onlineEnrollment)
                .then()
//						.log().body()
                .assertThat().statusCode(400)
                .body("Message", equalTo("Full"));
    }

    @Test(testName = "Course Not Available Online", description = "PBI:154260", enabled = true)
    public void courseNotAvailableOnline() {

        String c = prop.getProperty("availableId");
        int customerId = Integer.parseInt(c);
        String courseId = prop.getProperty("noWebCoId");
        String enrollCustomerAsStandBy = "false";

        given()
//						.log().all()
                .header("accept", "application/json")
                .header("X-Api-Key", aPIKey)
                .header("X-CompanyId", companyId)
                .header("X-ClubId", clubId)
                .when()
                .get("/api/v3/classcourse/enrollmemberincoursewithrecurringdues/" + customerId + "/" + courseId + "/" + enrollCustomerAsStandBy + "/" + onlineEnrollment)
                .then()
//						.log().body()
                .assertThat().statusCode(404)
                .body("Message", equalTo("ItemNotFound"));
    }

    @Test(testName = "Course Ended", description = "PBI:154260")
    public void courseEnded() {

        String c = prop.getProperty("availableId");
        int customerId = Integer.parseInt(c);
        String courseId = prop.getProperty("endedCoId");
        String enrollCustomerAsStandBy = "false";

        given()
//						.log().all()
                .header("accept", "application/json")
                .header("X-Api-Key", aPIKey)
                .header("X-CompanyId", companyId)
                .header("X-ClubId", clubId)
                .when()
                .get("/api/v3/classcourse/enrollmemberincoursewithrecurringdues/" + customerId + "/" + courseId + "/" + enrollCustomerAsStandBy + "/" + onlineEnrollment)
                .then()
//						.log().body()
                .assertThat().statusCode(400)
                .body("Message", equalTo("EnrollmentNotAllowed - EnrollmentHasEnded"));
    }

    @Test(testName = "Customer Not Found", description = "PBI:154260")
    public void customerNotFound() {

        int customerId = 248000;
        String courseId = prop.getProperty("alwaysAvailCoId");
        String enrollCustomerAsStandBy = "false";

        given()
//						.log().all()
                .header("accept", "application/json")
                .header("X-Api-Key", aPIKey)
                .header("X-CompanyId", companyId)
                .header("X-ClubId", clubId)
                .when()
                .get("/api/v3/classcourse/enrollmemberincoursewithrecurringdues/" + customerId + "/" + courseId + "/" + enrollCustomerAsStandBy + "/" + onlineEnrollment)
                .then()
//						.log().body()
                .assertThat().statusCode(404)
                .body("Message", equalTo("CustomerNotFound"));
    }

    @Test(testName = "Course Not Found", description = "PBI:154260", enabled = true)
    public void courseNotFound() {

        String c = prop.getProperty("availableId");
        int customerId = Integer.parseInt(c);
        int courseId = 99999;
        String enrollCustomerAsStandBy = "false";

        given()
//						.log().all()
                .header("accept", "application/json")
                .header("X-Api-Key", aPIKey)
                .header("X-CompanyId", companyId)
                .header("X-ClubId", clubId)
                .when()
                .get("/api/v3/classcourse/enrollmemberincoursewithrecurringdues/" + customerId + "/" + courseId + "/" + enrollCustomerAsStandBy + "/" + onlineEnrollment)
                .then()
//						.log().body()
                .assertThat().statusCode(404)
                .body("Message", equalTo("ItemNotFound"));
    }

    @Test(testName = "Recurring Dues Not Accepted", description = "PBI:154260")
    public void recurringDuesNotAccepted() {

        String c = prop.getProperty("availableId");
        int customerId = Integer.parseInt(c);
        String courseId = prop.getProperty("noPunchCoId");
        Boolean enrollCustomerAsStandby = true;

        given()
//						.log().all()
                .header("accept", "application/json")
                .header("X-Api-Key", aPIKey)
                .header("X-CompanyId", companyId)
                .header("X-ClubId", clubId)
                .when()
                .get("/api/v3/classcourse/enrollmemberincoursewithrecurringdues/" + customerId + "/" + courseId + "/" + enrollCustomerAsStandby + "/" + onlineEnrollment)
                .then()
//						.log().body()
                .assertThat().statusCode(400)
                .body("Message", equalTo("Course or customer configuration does not allow recurring dues enrollment"));
    }


}
