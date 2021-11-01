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
import resources.ReusableMethods;
import resources.base;

public class GetCourseDetailsByMember extends base{
	
	String aPIKey;
	String companyId;
	String clubId;
	Boolean onlineEnrollment = true;

	@BeforeClass
	public void getData() {
		base.getPropertyData();
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = prop.getProperty("baseURI");
		
		aPIKey = prop.getProperty("X-Api-Key");
		companyId = prop.getProperty("X-CompanyId");
		clubId = prop.getProperty("X-Club1Id");
	}
	
	@Test (testName="Online Sale Allowed",description="PBI:143545")
	public void onlineSaleAllowed() {
 
		String customerId = prop.getProperty("availableId");
		String courseId = prop.getProperty("alwaysAvailCoId");

				Response res = given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/getcoursedetailsbymember/"+customerId+"/"+courseId+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.extract().response();
				
						JsonPath js = ReusableMethods.rawToJson(res);
						
						Assert.assertNotNull(js.getString("Result.BarcodeId"));
						Assert.assertNotNull(js.getInt("Result.Capacity"));
						Assert.assertNotNull(js.getString("Result.Category.Description"));
						Assert.assertNotNull(js.getInt("Result.Category.Id"));
						Assert.assertNotNull(js.getString("Result.ClubId"));
						Assert.assertNotNull(js.getString("Result.DaysOfWeek"));
						Assert.assertNotNull(js.getString("Result.Description"));
						Assert.assertNotNull(js.getString("Result.Duration"));
						Assert.assertNotNull(js.getString("Result.EndDate"));
						Assert.assertNotNull(js.getString("Result.EnrolledCount"));
						Assert.assertNotNull(js.getString("Result.EnrollmentEligibilities[0].CanEnrollUsingPunches"));
						Assert.assertNotNull(js.getString("Result.EnrollmentEligibilities[0].CanEnrollUsingServiceDue"));
						Assert.assertNotNull(js.getString("Result.EnrollmentEligibilities[0].CustomerDisplayName"));
						Assert.assertNotNull(js.getString("Result.EnrollmentEligibilities[0].CustomerId"));
						Assert.assertNotNull(js.getString("Result.EnrollmentEligibilities[0].CustomerPunchCountForPackageItem"));
						Assert.assertNotNull(js.getString("Result.EnrollmentEligibilities[0].EnrollmentEligibilityStatus"));
						Assert.assertNotNull(js.getString("Result.EnrollmentEligibilities[0].EnrollmentStatus"));
						Assert.assertNotNull(js.getDouble("Result.EnrollmentEligibilities[0].Price"));
						Assert.assertNotNull(js.getString("Result.Instructor.BarcodeId"));
						Assert.assertNotNull(js.getString("Result.Instructor.DisplayName"));
						Assert.assertNotNull(js.getString("Result.Instructor.EmployeeId"));
						Assert.assertNotNull(js.getString("Result.IsFull"));
						Assert.assertNotNull(js.getString("Result.ItemEnrollmentEligibility"));
						Assert.assertNotNull(js.getString("Result.ItemId"));
						Assert.assertNotNull(js.getString("Result.Name"));
						Assert.assertNotNull(js.getString("Result.PackagePaymentConfiguration.PackageId"));
						Assert.assertNotNull(js.getString("Result.PackagePaymentConfiguration.PackageName"));
						Assert.assertNotNull(js.getString("Result.PackagePaymentConfiguration.PunchesRequired"));
						Assert.assertNotNull(js.getString("Result.StartDate"));
						Assert.assertNotNull(js.getString("Result.StartTime"));
						
						Assert.assertEquals(js.getString("Result.EnrollmentEligibilities[0].CustomerId"), customerId);
						Assert.assertEquals(js.getString("Result.ItemId"), courseId);				
	}
	
	@Test (testName="Online Sale Not Allowed - Member",description="PBI:143545", enabled = true)
	
	public void onlineSaleNotAllowed_MemberContext() {

				String customerId = prop.getProperty("availableId");
				String courseId = prop.getProperty("noWebCoId");

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/getcoursedetailsbymember/"+customerId+"/"+courseId+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.statusCode(404)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Message", equalTo("Course not found"));
	}
	
	@Test (testName="Online Sale Not Allowed - Employee",description="PBI:143545", enabled = true)
	public void onlineSaleNotAllowed_EmployeeContext() {

				String customerId = prop.getProperty("availableId");
				String courseId = prop.getProperty("noWebCoId");
				Boolean onlineEnrollment = false;

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/getcoursedetailsbymember/"+customerId+"/"+courseId+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.statusCode(200)
						.time(lessThan(60L),TimeUnit.SECONDS);
	}
	
	@Test (testName="Invalid CourseID",description="PBI:143545")
	public void invalidCourseID() {
 
		String customerId = prop.getProperty("standbyAId");
		String courseId = "99999";

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/getcoursedetailsbymember/"+customerId+"/"+courseId+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat()
						.statusCode(404)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Message", equalTo("Course not found"));
	}
	
	@Test (testName="Null CourseID",description="PBI:143545")
	public void nullCourseId() {
 
				String customerId = prop.getProperty("availableId");
				String courseId = prop.getProperty("NOTalwaysAvailCoId");

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/getcoursedetailsbymember/"+customerId+"/"+courseId+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(400)						
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Message", equalTo("The value 'null' is not valid for ItemId."));
	}
	
	@Test (testName="Null CustomerID",description="PBI:143545")
	public void nullCustomerId() {
 
				String customerId = prop.getProperty("NOTavailableId");
				String courseId = prop.getProperty("alwaysAvailCoId");

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/getcoursedetailsbymember/"+customerId+"/"+courseId+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(400)						
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Message", equalTo("The value 'null' is not valid for CustomerId."));
	}
 
	@Test (testName="Class ID Used",description="PBI:143545", enabled = true)
	
	public void classIDUsed() {
 
		String customerId = prop.getProperty("availableId");
		String courseId = prop.getProperty("alwaysAvailClId");

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/getcoursedetailsbymember/"+customerId+"/"+courseId+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Message", equalTo("Course not found"));
	}
	
	@Test (testName="Training ID Used",description="PBI:143545")
	public void trainingIDUsed() {
 
		String customerId = prop.getProperty("availableId");
		String courseId = prop.getProperty("freeTId");

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/getcoursedetailsbymember/"+customerId+"/"+courseId+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Message", equalTo("Course not found"));
	}
	
	@Test (testName="InvalidCustomerId", description="PBI:143545")
	public void invalidCustomerId() {
	
		int customerId = 99999;
		String courseId = prop.getProperty("alwaysAvailClId");

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
					.get("/api/v3/classcourse/getcoursedetailsbymember/"+customerId+"/"+courseId+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Message", equalTo("Customer not found"));;
	}
	
	@Test (testName="Enrollment Not Open",description="PBI:143545", enabled = true)
	public void enrollmentNotOpen() {
		

				String customerId = prop.getProperty("availableId");
				String courseId = prop.getProperty("neverAvailCoId");

				given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
					.get("/api/v3/classcourse/getcoursedetailsbymember/"+customerId+"/"+courseId+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.body("Result.EnrollmentEligibilities[0].EnrollmentEligibilityStatus", equalTo("EnrollmentNotOpen"));
	}
}
