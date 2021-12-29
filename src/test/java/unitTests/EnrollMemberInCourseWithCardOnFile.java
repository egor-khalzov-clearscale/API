package unitTests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import payloads.ClassCoursePL;
import resources.ReusableMethods;
import resources.base;

import java.util.concurrent.TimeUnit;

public class EnrollMemberInCourseWithCardOnFile extends base {
	
			static String aPIKey;
			static String companyId;
			static String clubId;
			
			static Boolean enrollCustomerAsStandby 	= true;
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

	@Test (testName="Member Enrolled - Paid Course",description="PBI:146578")
	public void memberEnrolled_PaidCourse() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("alwaysAvailCoId");
				String displayedGrandTotal = prop.getProperty("alwaysAvailCoPrice");
				int accountId					= 1;
				Boolean enrollCustomerAsStandby = true;
				
				if (ReusableMethods.deleteIfEnrolled(customerId) == false) {

			Response res =	given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInCourseWithCardOnFile(customerId,courseId,displayedGrandTotal,accountId,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberincoursewithcardonfile")
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
						.time(lessThan(60L),TimeUnit.SECONDS)
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
	
	@Test (testName="Member Enrolled - Free Course",description="PBI:146578")
	public void memberEnrolled_FreeCourse() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("freeCoId");
				String displayedGrandTotal = prop.getProperty("freeCoPrice");
				int accountId = 1;
				Boolean enrollCustomerAsStandby = true;
				
				if (ReusableMethods.deleteIfEnrolled(customerId) == false) {

			Response res =	given()

				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInCourseWithCardOnFile(customerId,courseId,displayedGrandTotal,accountId,enrollCustomerAsStandby,onlineEnrollment))
					.post("/api/v3/classcourse/enrollmemberincoursewithcardonfile")
						.then()
//						.log().body()
						.assertThat().statusCode(200)
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
	
	@Test (testName="Member Enrolled - Free Course - Collections Member",description="PBI:146578")
	public void memberEnrolledFreeCourseCollectionsMember() {
		
				String c = prop.getProperty("collectionsId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("freeCoId");
				String displayedGrandTotal = prop.getProperty("freeCoPrice");
				int accountId = 1;
				Boolean enrollCustomerAsStandby = true;
				
				if (ReusableMethods.deleteIfEnrolled(customerId) == false) {

			Response res =	given()

				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInCourseWithCardOnFile(customerId,courseId,displayedGrandTotal,accountId,enrollCustomerAsStandby,onlineEnrollment))
					.post("/api/v3/classcourse/enrollmemberincoursewithcardonfile")
						.then()
//						.log().body()
						.assertThat().statusCode(200)
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

	@Test (testName="Member Enrolled On Standby",description="PBI:146578")
	public void memberEnrolledOnStandby() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("standbyCoId");
				String displayedGrandTotal = prop.getProperty("standbyCoPrice");
				int accountId = 1;
				Boolean enrollCustomerAsStandby = true;
				
				if (ReusableMethods.deleteIfEnrolled(customerId) == false) {

			Response res =	given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInCourseWithCardOnFile(customerId,courseId,displayedGrandTotal,accountId,enrollCustomerAsStandby,onlineEnrollment))
					.post("/api/v3/classcourse/enrollmemberincoursewithcardonfile")
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
	
	@Test (testName="Member Not Enrolled On Standby",description="PBI:146578")
	public void memberNotEnrolledOnStandby() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("standbyCoId");
				String displayedGrandTotal = prop.getProperty("standbyCoPrice");
				int accountId = 1;
				Boolean enrollCustomerAsStandby = false;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInCourseWithCardOnFile(customerId,courseId,displayedGrandTotal,accountId,enrollCustomerAsStandby,onlineEnrollment))
					.post("/api/v3/classcourse/enrollmemberincoursewithcardonfile")
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("Full"));
	}

	@Test (testName="Member Already Enrolled",description="PBI:146578")
	public void memberAlreadyEnrolled() {
		
				String c = prop.getProperty("standbyAId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("standbyCoId");
				String displayedGrandTotal = prop.getProperty("standbyCoPrice");
				int accountId = 1;
				Boolean enrollCustomerAsStandby = true;

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInCourseWithCardOnFile(customerId,courseId,displayedGrandTotal,accountId,enrollCustomerAsStandby,onlineEnrollment))
					.post("/api/v3/classcourse/enrollmemberincoursewithcardonfile")
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("CustomerAlreadyEnrolled"));
	}
	
	@Test (testName="Member Already On Standby",description="PBI:146578")
	public void memberAlreadyOnStandby() {
		
				String c = prop.getProperty("standbyBId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("standbyCoId");
				String displayedGrandTotal = prop.getProperty("standbyCoPrice");
				int accountId = 1;
				Boolean enrollCustomerAsStandby = true;

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInCourseWithCardOnFile(customerId,courseId,displayedGrandTotal,accountId,enrollCustomerAsStandby,onlineEnrollment))
					.post("/api/v3/classcourse/enrollmemberincoursewithcardonfile")
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("CustomerAlreadyOnStandby"));
	}
	
	@Test (testName="Course Not Available Online",description="PBI:146578", enabled = true)
	public void courseNotAvailableOnline() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("noWebCoId");
				String displayedGrandTotal = prop.getProperty("noWebCoPrice");
				int accountId					= 1;
				Boolean enrollCustomerAsStandby = true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInCourseWithCardOnFile(customerId,courseId,displayedGrandTotal,accountId,enrollCustomerAsStandby,onlineEnrollment))
					.post("/api/v3/classcourse/enrollmemberincoursewithcardonfile")
						.then()
//						.log().body()
						.assertThat()
						.body("Message", equalTo("ItemNotFound"))
						.statusCode(404);
	}
	
	@Test (testName="Course Ended",description="PBI:146578")
	public void courseEnded() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("endedCoId");
				String displayedGrandTotal = prop.getProperty("endedCoPrice");
				int accountId					= 1;
				Boolean enrollCustomerAsStandby = true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInCourseWithCardOnFile(customerId,courseId,displayedGrandTotal,accountId,enrollCustomerAsStandby,onlineEnrollment))
					.post("/api/v3/classcourse/enrollmemberincoursewithcardonfile")
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("EnrollmentNotAllowed - EnrollmentHasEnded"));
	}
	
	@Test (testName="Customer Not Found",description="PBI:146578")
	public void customerNotFound() {
		
				int customerId = 245000;
				String courseId = prop.getProperty("standbyCoId");
				String displayedGrandTotal = prop.getProperty("standbyCoPrice");
				int accountId = 1;
				Boolean enrollCustomerAsStandby = true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInCourseWithCardOnFile(customerId,courseId,displayedGrandTotal,accountId,enrollCustomerAsStandby,onlineEnrollment))
					.post("/api/v3/classcourse/enrollmemberincoursewithcardonfile")
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.body("Message", equalTo("CustomerNotFound"));
	}
	
	@Test (testName="Course Not Found",description="PBI:146578", enabled = true)
	public void courseNotFound() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String courseId = "99999";
				String displayedGrandTotal = prop.getProperty("alwaysAvailCoPrice");
				int accountId = 1;
				Boolean enrollCustomerAsStandby = true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInCourseWithCardOnFile(customerId,courseId,displayedGrandTotal,accountId,enrollCustomerAsStandby,onlineEnrollment))
					.post("/api/v3/classcourse/enrollmemberincoursewithcardonfile")
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.body("Message", equalTo("ItemNotFound"));
	}
	
	@Test (testName="Product Price Changed",description="PBI:146578")
	public void productPriceChanged() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("alwaysAvailCoId");
				String displayedGrandTotal 		= "10.01";
				int accountId					= 1;
				Boolean enrollCustomerAsStandby = true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInCourseWithCardOnFile(customerId,courseId,displayedGrandTotal,accountId,enrollCustomerAsStandby,onlineEnrollment))
					.post("/api/v3/classcourse/enrollmemberincoursewithcardonfile")
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("ProductPriceChanged"));
	}
	
	@Test (testName="Scheduling Conflict",description="PBI:146578", enabled = true)
	public void schedulingConflict() {
		
				String c = prop.getProperty("standbyCId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("standbyCoId");
				String displayedGrandTotal = prop.getProperty("standbyCoPrice");
				int accountId					= 1;
				Boolean enrollCustomerAsStandby = true;

				given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInCourseWithCardOnFile(customerId,courseId,displayedGrandTotal,accountId,enrollCustomerAsStandby,onlineEnrollment))
					.post("/api/v3/classcourse/enrollmemberincoursewithcardonfile")
						.then()
//						.log().all()
						.assertThat()
//						.body("AllowedToEnroll", equalTo(false))
						.body("Status", equalTo(400))
						.body("Message", equalTo("EnrollmentNotAllowed - MemberSchedulingConflict"));
	}
	
	@Test (testName="No FOP - Account Problem",description="PBI:146578")
	public void noFOP_AccountProblem() {
		
		String c = prop.getProperty("noFOPId");
		int customerId = Integer.parseInt(c);
		String courseId = prop.getProperty("alwaysAvailCoId");
		String displayedGrandTotal = prop.getProperty("alwaysAvailCoPrice");
				int accountId = 1;
				Boolean enrollCustomerAsStandby = true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInCourseWithCardOnFile(customerId,courseId,displayedGrandTotal,accountId,enrollCustomerAsStandby,onlineEnrollment))
					.post("/api/v3/classcourse/enrollmemberincoursewithcardonfile")
						.then()
//						.log().body()
						// this returns "Sequence contains no elements" because there is no card on file
						.assertThat().statusCode(500)
						.body("Message", containsString("Sequence contains no elements"));
	}
	
}
