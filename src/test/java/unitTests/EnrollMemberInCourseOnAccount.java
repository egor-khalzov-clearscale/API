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

public class EnrollMemberInCourseOnAccount extends base {
	
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
	
	@Test (testName="Member Enrolled - Paid Course Already Started",description="PBI:143589")
	public void memberEnrolledPaidCourseStarted() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);				
				String courseId = prop.getProperty("alwaysAvailCoId");
				String displayedGrandTotal = prop.getProperty("alwaysAvailCoPrice");
				Boolean enrollCustomerAsStandby = true;
				
				if (ReusableMethods.deleteIfEnrolled(customerId) == false) {

			Response res =	given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
					.get("/api/v3/classcourse/enrollmemberincourseonaccount/"+customerId+"/"+courseId+"/"+displayedGrandTotal+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
						.then()
//						.log().all()
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
	
		@Test (testName="Member Enrolled - Paid Course Not Started",description="PBI:143589")
	public void memberEnrolledPaidCourseNotStarted() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				
				String courseId = prop.getProperty("notStartedCoId");
				String displayedGrandTotal = prop.getProperty("notStartedCoPrice");
				Boolean enrollCustomerAsStandby = true;
				
				if (ReusableMethods.deleteIfEnrolled(customerId) == false) {

			Response res =	given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
					.get("/api/v3/classcourse/enrollmemberincourseonaccount/"+customerId+"/"+courseId+"/"+displayedGrandTotal+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
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
		
	@Test (testName="Member Enrolled On Standby",description="PBI:143589")
	public void memberEnrolledOnStandby() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				
				String courseId = prop.getProperty("standbyCoId");
				String displayedGrandTotal = prop.getProperty("standbyCoPrice");
				Boolean enrollCustomerAsStandby = true;
				
				if (ReusableMethods.deleteIfEnrolled(customerId) == false) {

			Response res =	given()

				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
					.get("/api/v3/classcourse/enrollmemberincourseonaccount/"+customerId+"/"+courseId+"/"+displayedGrandTotal+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
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
	
	@Test (testName="Member Enrolled - Free Course",description="PBI:143589")
	public void memberEnrolledFreeCourse() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				
				String courseId = prop.getProperty("freeCoId");
				String displayedGrandTotal = prop.getProperty("freeCoPrice");
				Boolean enrollCustomerAsStandby = true;
				
				if (ReusableMethods.deleteIfEnrolled(customerId) == false) {

			Response res =	given()

				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
					.get("/api/v3/classcourse/enrollmemberincourseonaccount/"+customerId+"/"+courseId+"/"+displayedGrandTotal+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
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
	
	@Test (testName="Member Enrolled - Free Course - Collections Member",description="PBI:143589")
	public void memberEnrolledFreeCourseCollectionsMember() {
		
				String c = prop.getProperty("collectionsId");
				int customerId = Integer.parseInt(c);
				
				String courseId = prop.getProperty("freeCoId");
				String displayedGrandTotal = prop.getProperty("freeCoPrice");
				Boolean enrollCustomerAsStandby = true;
				
				if (ReusableMethods.deleteIfEnrolled(customerId) == false) {

			Response res =	given()

				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
					.get("/api/v3/classcourse/enrollmemberincourseonaccount/"+customerId+"/"+courseId+"/"+displayedGrandTotal+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
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
	
	@Test (testName="No FOP - Account Problem",description="PBI:143589") // failed to create invoice because member's billing info not setup
	public void noFOP_AccountProblem() {
		
				String c = prop.getProperty("noFOPId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("alwaysAvailCoId");
				String displayedGrandTotal = prop.getProperty("alwaysAvailCoPrice");
				Boolean enrollCustomerAsStandby = true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberincourseonaccount/"+customerId+"/"+courseId+"/"+displayedGrandTotal+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("Account Problem"));
	}
	
	@Test (testName="Member Already Enrolled",description="PBI:143589")
	public void memberAlreadyEnrolled() {
		
				String c = prop.getProperty("standbyAId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("standbyCoId");
				String displayedGrandTotal = prop.getProperty("standbyCoPrice");
				Boolean enrollCustomerAsStandby = true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberincourseonaccount/"+customerId+"/"+courseId+"/"+displayedGrandTotal+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("CustomerAlreadyEnrolled"));
	}
	
	@Test (testName="Product Price Changed",description="PBI:143589")
	public void productPriceChanged() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("alwaysAvailCoId");
				String displayedGrandTotal = "10.01";
				Boolean enrollCustomerAsStandby = true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberincourseonaccount/"+customerId+"/"+courseId+"/"+displayedGrandTotal+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("ProductPriceChanged"));
	}
	
	@Test (testName="Member Already On Standby",description="PBI:143589")
	public void memberAlreadyOnStandby() {
		
				String c = prop.getProperty("standbyBId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("standbyCoId");
				String displayedGrandTotal = prop.getProperty("standbyCoPrice");
				Boolean enrollCustomerAsStandby = true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberincourseonaccount/"+customerId+"/"+courseId+"/"+displayedGrandTotal+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("CustomerAlreadyOnStandby"));
	}
	
	@Test (testName="Member Not Enrolled On Standby",description="PBI:143589")
	public void memberNotEnrolledOnStandby() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("standbyCoId");
				String displayedGrandTotal = prop.getProperty("standbyCoPrice");
				String enrollCustomerAsStandby = "false";

				given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberincourseonaccount/"+customerId+"/"+courseId+"/"+displayedGrandTotal+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("Full"));
	}
	
	@Test (testName="Customer Not Found",description="PBI:143589")
	public void customerNotFound() {
		
				int customerId 			= 245000;
				String courseId = prop.getProperty("alwaysAvailCoId");
				String displayedGrandTotal = prop.getProperty("alwaysAvailCoPrice");
				Boolean enrollCustomerAsStandby = true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberincourseonaccount/"+customerId+"/"+courseId+"/"+displayedGrandTotal+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.body("Message", equalTo("CustomerNotFound"));
	}
	
	@Test (testName="Course Not Found",description="PBI:143589", enabled = true)
	public void courseNotFound() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				int courseId = 99999;
				String displayedGrandTotal = prop.getProperty("alwaysAvailCoPrice");
				Boolean enrollCustomerAsStandby = true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberincourseonaccount/"+customerId+"/"+courseId+"/"+displayedGrandTotal+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.body("Message", equalTo("ItemNotFound"));
	}
	
	@Test (testName="Course Not Available Online - Member",description="PBI:143589", enabled = false)
	public void courseNotAvailableOnlineMember() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("noWebCoId");
				String displayedGrandTotal = prop.getProperty("noWebCoPrice");
				Boolean enrollCustomerAsStandby = true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberincourseonaccount/"+customerId+"/"+courseId+"/"+displayedGrandTotal+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("EnrollmentNotAllowed - NotAllowed"));
	}
	
	@Test (testName="Course Not Available Online - Employee",description="PBI:143589", enabled = false)
	public void courseNotAvailableOnlineEmployee() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				Boolean enrollCustomerAsStandby = true;
				
				Boolean onlineEnrollment = false;
				String courseId = prop.getProperty("noWebCoId");
				String displayedGrandTotal = prop.getProperty("noWebCoPrice");

				Response res = given()
				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberincourseonaccount/"+customerId+"/"+courseId+"/"+displayedGrandTotal+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
						.then()
						.log().body()
						.extract().response();
				
				JsonPath js = ReusableMethods.rawToJson(res);
						int enrollmentId = js.getInt("Result.EnrollmentId");
						int invoiceId = js.getInt("Result.InvoiceId");	
						
						if (res.statusCode() == 200) {
							ReusableMethods.deleteEnrollment(companyId, enrollmentId, customerId);
							ReusableMethods.deleteInvoice(companyId, enrollmentId, invoiceId, customerId);
						}

	}
	
	@Test (testName="Enrollment Not Open",description="PBI:143589", enabled = true)
	public void enrollmentNotOpen() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("neverAvailCoId");
				String displayedGrandTotal = prop.getProperty("neverAvailCoPrice");
				Boolean enrollCustomerAsStandby = true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberincourseonaccount/"+customerId+"/"+courseId+"/"+displayedGrandTotal+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("EnrollmentNotAllowed - EnrollmentNotOpen"));
	}
	
	@Test (testName="Enrollment Not Allowed - Terminated Member",description="PBI:143589", enabled = true)
	public void enrollmentNotAllowedTerminatedMember() {
		
				String c = prop.getProperty("terminatedId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("alwaysAvailCoId");
				String displayedGrandTotal = prop.getProperty("alwaysAvailCoPrice");
				Boolean enrollCustomerAsStandby = true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
					.get("/api/v3/classcourse/enrollmemberincourseonaccount/"+customerId+"/"+courseId+"/"+displayedGrandTotal+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
					.then()
//					.log().body()
					.assertThat().statusCode(400)
					.body("Message", equalTo("EnrollmentNotAllowed - NotAllowed"));
	}
	
	@Test (testName="Enrollment Not Allowed - Collections Member",description="PBI:143589")
	public void enrollmentNotAllowedCollectionsMember() {
		
				String c = prop.getProperty("collectionsId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("alwaysAvailCoId");
				String displayedGrandTotal = prop.getProperty("alwaysAvailCoPrice");
				Boolean enrollCustomerAsStandby = true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
					.get("/api/v3/classcourse/enrollmemberincourseonaccount/"+customerId+"/"+courseId+"/"+displayedGrandTotal+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
					.then()
//					.log().body()
					.assertThat().statusCode(400)
					.body("Message", equalTo("Account Problem"));
	}
	
	@Test (testName="Enrollment Not Allowed - Frozen Member",description="PBI:143589", enabled = true)
	public void enrollmentNotAllowedFrozenMember() {
		
				String c = prop.getProperty("frozenId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("alwaysAvailCoId");
				String displayedGrandTotal = prop.getProperty("alwaysAvailCoPrice");
				Boolean enrollCustomerAsStandby = true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
					.get("/api/v3/classcourse/enrollmemberincourseonaccount/"+customerId+"/"+courseId+"/"+displayedGrandTotal+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
					.then()
//					.log().body()
					.assertThat().statusCode(400)
					.body("Message", equalTo("EnrollmentNotAllowed - MemberFrozen"));
	}
	
	@Test (testName="Enrollment Not Allowed - Prospect Member",description="PBI:143589")
	public void enrollmentNotAllowedProspectMember() {
		
				String c = prop.getProperty("prospectId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("alwaysAvailCoId");
				String displayedGrandTotal = prop.getProperty("alwaysAvailCoPrice");
				Boolean enrollCustomerAsStandby = true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
					.get("/api/v3/classcourse/enrollmemberincourseonaccount/"+customerId+"/"+courseId+"/"+displayedGrandTotal+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
					.then()
//					.log().body()
					.assertThat().statusCode(400)
					.body("Message", equalTo("EnrollmentNotAllowed - NotAllowed"));
	}
	
	@Test (testName="Credit Limited Exceeded",description="PBI:143589", enabled = true)
	public void creditLimitedExceeded() {
		
				String c = prop.getProperty("creditLimitId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("noAlternatePaymentCoId");
				String displayedGrandTotal = prop.getProperty("noAlternatePaymentCoPrice");
				Boolean enrollCustomerAsStandby = true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
					.get("/api/v3/classcourse/enrollmemberincourseonaccount/"+customerId+"/"+courseId+"/"+displayedGrandTotal+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
					.then()
//					.log().body()
					.assertThat().statusCode(400)
					.body("Message", equalTo("Account Problem"));
	}
	
	@Test (testName="Course Enrollment Closed",description="PBI:143589")
	public void courseEnrollmentClosed() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("closedCoId");
				String displayedGrandTotal = prop.getProperty("closedCoPrice");
				Boolean enrollCustomerAsStandby = true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
					.get("/api/v3/classcourse/enrollmemberincourseonaccount/"+customerId+"/"+courseId+"/"+displayedGrandTotal+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
					.then()
//					.log().body()
					.assertThat().statusCode(400)
					.body("Message", equalTo("EnrollmentNotAllowed - EnrollmentHasClosed"));
	}
	
	@Test (testName="Course Ended",description="PBI:143589")
	public void courseEnded() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("endedCoId");
				String displayedGrandTotal = prop.getProperty("endedCoPrice");
				Boolean enrollCustomerAsStandby = true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
					.get("/api/v3/classcourse/enrollmemberincourseonaccount/"+customerId+"/"+courseId+"/"+displayedGrandTotal+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
					.then()
//					.log().body()
					.assertThat().statusCode(400)
					.body("Message", equalTo("EnrollmentNotAllowed - EnrollmentHasEnded"));
	}
}
