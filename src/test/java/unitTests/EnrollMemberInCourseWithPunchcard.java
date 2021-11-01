package unitTests;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.lessThan;
import java.util.concurrent.TimeUnit;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import resources.ReusableMethods;
import resources.base;

public class EnrollMemberInCourseWithPunchcard extends base {
	
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

	@Test (testName="Member Enrolled - Paid Course Already Started",description="PBI:147820")
	public void memberEnrolledPaidCourseStarted() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);				
				String courseId = prop.getProperty("punchCoId");
				Boolean enrollCustomerAsStandby = true;
				
				if (ReusableMethods.isEnrolled(customerId) == false) {

			Response res =	given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberincoursewithpunchcard/"+customerId+"/"+courseId+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.time(lessThan(60L),TimeUnit.SECONDS)
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
				}
				else{
					Assert.fail("failing test because isEnrolled() == true"); //failing test because isEnrolled() == true
				}
	}
	 
	@Test (testName="Member Enrolled - Paid Course Not Started",description="PBI:147820")
	public void memberEnrolledPaidCourseNotStarted() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);				
				String courseId = prop.getProperty("notStartedCoId");
				Boolean enrollCustomerAsStandby = true;
				
				if (ReusableMethods.isEnrolled(customerId) == false) {

			Response res =	given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberincoursewithpunchcard/"+customerId+"/"+courseId+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
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
				}
				else{
					Assert.fail("failing test because isEnrolled() == true"); //failing test because isEnrolled() == true
				}
	}

	@Test (testName="Member Enrolled On Standby",description="PBI:147820")
	public void memberEnrolledOnStandby() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);				
				String courseId = prop.getProperty("standbyCoId");
				Boolean enrollCustomerAsStandby = true;
				
				if (ReusableMethods.isEnrolled(customerId) == false) {

			Response res =	given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberincoursewithpunchcard/"+customerId+"/"+courseId+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.body("Result.Enrolled", equalTo(false))
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
				}
				else{
					Assert.fail("failing test because isEnrolled() == true"); //failing test because isEnrolled() == true
				}
	}
	
	@Test (testName="Member Enrolled - Free Course",description="PBI:147820")
	public void memberEnrolledFreeCourse() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);				
				String courseId = prop.getProperty("freeCoId");
				Boolean enrollCustomerAsStandby = true;
				
				if (ReusableMethods.isEnrolled(customerId) == false) {

			Response res =	given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberincoursewithpunchcard/"+customerId+"/"+courseId+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.time(lessThan(60L),TimeUnit.SECONDS)
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
				}
				else{
					Assert.fail("failing test because isEnrolled() == true"); //failing test because isEnrolled() == true
				}
	}
	
	@Test (testName="Member Enrolled - Free Course - Collections Member",description="PBI:147820", enabled = true)
	public void memberEnrolledFreeCourseCollectionsMember() {
		
		// this test will fail if the member didn't have enough punchcards nor is their membership setup to use recurring dues
		// So even though the class/course is free, the member must still have punchcards or a membership that accepts recurring dues. (See bug# 157917)
		
				String c = prop.getProperty("collectionsId");
				int customerId = Integer.parseInt(c);				
				String courseId = prop.getProperty("freeCoId");
				Boolean enrollCustomerAsStandby = true;
				
				if (ReusableMethods.isEnrolled(customerId) == false) {

			Response res =	given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberincoursewithpunchcard/"+customerId+"/"+courseId+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
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
				}
				else{
					Assert.fail("failing test because isEnrolled() == true"); //failing test because isEnrolled() == true
				}
	}
	
	@Test (testName="Not Enough Punches",description="PBI:147820")
	public void notEnoughPunches() {
		
				String c = prop.getProperty("noPunchesId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("alwaysAvailCoId");
				Boolean enrollCustomerAsStandby = true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberincoursewithpunchcard/"+customerId+"/"+courseId+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(400)
//						.body("Message", equalTo("NotEnoughPunches"));
						.body("Message", equalTo("Course or customer configuration does not allow punchcard enrollment"));
		}
	
	@Test (testName="Punchcard Not Allowed",description="PBI:147820")
	public void punchcardNotAllowed() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("noPunchCoId");
				Boolean enrollCustomerAsStandby = true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
					.get("/api/v3/classcourse/enrollmemberincoursewithpunchcard/"+customerId+"/"+courseId+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("Course or customer configuration does not allow punchcard enrollment"))
						;
		}
	
	@Test (testName="Course Full - Standby Not Allowed",description="PBI:147820")
	public void standbyNotAllowed() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("standbyCoId");
				String enrollCustomerAsStandby = "false";

				given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
					.get("/api/v3/classcourse/enrollmemberincoursewithpunchcard/"+customerId+"/"+courseId+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("Full"));
		}
	
	@Test (testName="Course Not Found",description="PBI:147820", enabled = true)
	public void courseNotFound() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				
				int courseId = 99999;
				Boolean enrollCustomerAsStandby = true;

			given()

				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberincoursewithpunchcard/"+customerId+"/"+courseId+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.body("Message", equalTo("ItemNotFound"));	
	}
		
	}
