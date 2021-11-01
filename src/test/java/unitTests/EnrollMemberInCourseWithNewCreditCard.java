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
import payloads.ClassCoursePL;
import resources.ReusableMethods;
import resources.base;

public class EnrollMemberInCourseWithNewCreditCard extends base {
	
		static String aPIKey;
		static String companyId;
		static String clubId;
	
		static String cardNumber;
		static String nameOnCard;
		static String month;
		static String year;
		static String securityCode;
		static String addressLine1;
		static String city;
		static String state;
		static String postalCode;
		
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
		
		cardNumber = prop.getProperty("MC1CardNumber");
		nameOnCard = prop.getProperty("MC1NameOnCard");
		month = prop.getProperty("MC1Month");
		year = prop.getProperty("MC1Year");
		securityCode = prop.getProperty("MC1SecurityCode");
		addressLine1 = prop.getProperty("MC1AddressLine1");
		city = prop.getProperty("MC1City");
		state = prop.getProperty("MC1State");
		postalCode = prop.getProperty("MC1PostalCode");
	}
	 
	@Test (testName="Member Enrolled - Paid Course",description="PBI:146580")
	public void memberEnrolledPaidCourse() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("alwaysAvailCoId");
				String displayedGrandTotal = prop.getProperty("alwaysAvailCoPrice");
				
				if (ReusableMethods.isEnrolled(customerId) == false) {

			Response res =	given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInCourseWithNewCreditCard(customerId,courseId,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberincoursewithnewcreditcard")
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
	
	@Test (testName="Member Enrolled - Free Course",description="PBI:146580")
	public void memberEnrolledFreeCourse() {

				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("freeCoId");
				String displayedGrandTotal = prop.getProperty("freeCoPrice");
				
				if (ReusableMethods.isEnrolled(customerId) == false) {

			Response res =	given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInCourseWithNewCreditCard(customerId,courseId,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberincoursewithnewcreditcard")
						.then()
//						.log().body()
						.assertThat().statusCode(200)
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
	
	@Test (testName="Member Enrolled - Free Course - Collections Member",description="PBI:146580")
	public void memberEnrolledFreeCourseCollectionsMember() {
		
				String c = prop.getProperty("collectionsId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("freeCoId");
				String displayedGrandTotal = prop.getProperty("freeCoPrice");
				
				if (ReusableMethods.isEnrolled(customerId) == false) {

			Response res =	given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInCourseWithNewCreditCard(customerId,courseId,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberincoursewithnewcreditcard")
						.then()
//						.log().body()
						.assertThat().statusCode(200)
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
	
	@Test (testName="Member Enrolled On Standby",description="PBI:146580")
	public void memberEnrolledOnStandby() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("standbyCoId");
				String displayedGrandTotal = prop.getProperty("standbyCoPrice");
				
				if (ReusableMethods.isEnrolled(customerId) == false) {

			Response res =	given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInCourseWithNewCreditCard(customerId,courseId,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberincoursewithnewcreditcard")
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
	
	@Test (testName="Member Not Enrolled On Standby",description="PBI:146580")
	public void memberNotEnrolledOnStandby() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("standbyCoId");
				String displayedGrandTotal = prop.getProperty("standbyCoPrice");
				Boolean enrollCustomerAsStandby = false;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInCourseWithNewCreditCard(customerId,courseId,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberincoursewithnewcreditcard")
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("Full"));
	}
	
	@Test (testName="Card Expired",description="PBI:146580", enabled = true)
	public void cardExpired() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("alwaysAvailCoId");
				String displayedGrandTotal = prop.getProperty("alwaysAvailCoPrice");
				String year = "2019";

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInCourseWithNewCreditCard(customerId,courseId,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberincoursewithnewcreditcard")
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("Credit Card Processing Failed"));
	}
	
	@Test (testName="Card Number Invalid",description="PBI:146580")
	public void cardNumberInvalid() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("alwaysAvailCoId");
				String displayedGrandTotal = prop.getProperty("alwaysAvailCoPrice");
				String cardNumber = "5454545454545400";

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInCourseWithNewCreditCard(customerId,courseId,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberincoursewithnewcreditcard")
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("Credit Card Processing Failed"));
	}
	
	@Test (testName="Card Number Length Incorrect",description="PBI:146580")
	public void cardNumberLengthIncorrect() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String courseId = prop.getProperty("alwaysAvailCoId");
				String displayedGrandTotal = prop.getProperty("alwaysAvailCoPrice");
				String cardNumber = "54545454545454540000";

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInCourseWithNewCreditCard(customerId,courseId,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberincoursewithnewcreditcard")
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("Credit Card Processing Failed"));
	}
	
	@Test (testName="Course Not Found",description="PBI:146580", enabled = true)
	public void courseNotFound() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String courseId = "99999";
				String displayedGrandTotal = prop.getProperty("alwaysAvailCoPrice");

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInCourseWithNewCreditCard(customerId,courseId,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberincoursewithnewcreditcard")
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.body("Message", equalTo("ItemNotFound"));
	}
	

	
}
