package unitTests;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import java.util.concurrent.TimeUnit;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import payloads.ClassCoursePL;
import resources.ReusableMethods;
import resources.base;

public class EnrollMemberInClassWithNewCreditCard extends base {
	
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

	@Test (testName="Member Enrolled - Paid Class",description="PBI:146579")
	public void memberEnrolled_PaidClass() {

				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("alwaysAvailClId");
				String classOccurrence = prop.getProperty("alwaysAvailClOccurrence");
				String displayedGrandTotal = prop.getProperty("alwaysAvailClPrice");
				
				if (ReusableMethods.deleteIfEnrolled(customerId) == false) {

			Response res =	given()
//					.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
						.body(ClassCoursePL.EnrollMemberInClassWithNewCreditCard(customerId,classId,classOccurrence,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
							.post("/api/v3/classcourse/enrollmemberinclasswithnewcreditcard")
							.then()
						.assertThat()
	//						.log().all()
							.statusCode(200)
							.time(lessThan(60L),TimeUnit.SECONDS)
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
	
	@Test (testName="Member Enrolled - Free Class",description="PBI:146579")
	public void memberEnrolled_FreeClass() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("freeClId");
				String classOccurrence = prop.getProperty("freeClOccurrence");
				String displayedGrandTotal = prop.getProperty("freeClPrice");
				
				if (ReusableMethods.deleteIfEnrolled(customerId) == false) {

			Response res =	given()

				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
						.body(ClassCoursePL.EnrollMemberInClassWithNewCreditCard(customerId,classId,classOccurrence,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberinclasswithnewcreditcard")
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
	
	@Test (testName="Member Enrolled - Free Class - Collections Member",description="PBI:146579")
	public void memberEnrolled_FreeClassCollectionsMember() {
		
				String c = prop.getProperty("collectionsId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("freeClId");
				String classOccurrence = prop.getProperty("freeClOccurrence");
				String displayedGrandTotal = prop.getProperty("freeClPrice");
				
				if (ReusableMethods.deleteIfEnrolled(customerId) == false) {

			Response res =	given()

				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithNewCreditCard(customerId,classId,classOccurrence,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberinclasswithnewcreditcard")
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
	
	@Test (testName="Member Enrolled On Standby",description="PBI:146579")
	public void memberEnrolledOnStandby() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("standbyClId");
				String classOccurrence = prop.getProperty("standbyClOccurrence");
				String displayedGrandTotal = prop.getProperty("standbyClPrice");
				
				if (ReusableMethods.deleteIfEnrolled(customerId) == false) {

			Response res =	given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithNewCreditCard(customerId,classId,classOccurrence,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberinclasswithnewcreditcard")
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
	
	@Test (testName="Member Not Enrolled On Standby",description="PBI:146579")
	public void memberNotEnrolledOnStandby() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("standbyClId");
				String classOccurrence = prop.getProperty("standbyClOccurrence");
				String displayedGrandTotal = prop.getProperty("standbyClPrice");
				Boolean enrollCustomerAsStandby 	= false;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithNewCreditCard(customerId,classId,classOccurrence,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberinclasswithnewcreditcard")
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("Full"));
	}
	
	@Test (testName="Member Already Enrolled",description="PBI:146579")
	public void memberAlreadyEnrolled() {
		
				String c = prop.getProperty("standbyCId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("standbyClId");
				String classOccurrence = prop.getProperty("standbyClOccurrence");
				String displayedGrandTotal = prop.getProperty("standbyClPrice");

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithNewCreditCard(customerId,classId,classOccurrence,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberinclasswithnewcreditcard")
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("CustomerAlreadyEnrolled"));
	}
	
	@Test (testName="Member Already On Standby",description="PBI:146579")
	public void memberAlreadyOnStandby() {
		
				String c = prop.getProperty("standbyDId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("standbyClId");
				String classOccurrence = prop.getProperty("standbyClOccurrence");
				String displayedGrandTotal = prop.getProperty("standbyClPrice");

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithNewCreditCard(customerId,classId,classOccurrence,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberinclasswithnewcreditcard")
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("CustomerAlreadyOnStandby"));
	}
	
	@Test (testName="Card Expired",description="PBI:146579", enabled = true)
	public void cardExpired() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("alwaysAvailClId");
				String classOccurrence = prop.getProperty("alwaysAvailClOccurrence");
				String displayedGrandTotal = prop.getProperty("alwaysAvailClPrice");
				String year = "2019";

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithNewCreditCard(customerId,classId,classOccurrence,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberinclasswithnewcreditcard")
						.then()
//						.log().all()
						.assertThat().statusCode(400)
						.body("Message", equalTo("Credit Card Processing Failed"));
	}
	
	@Test (testName="Online Sales Not Allowed - Member Context",description="PBI:146579", enabled = true)
	public void onlineSalesNotAllowedMember() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("noWebClId");
				String classOccurrence = prop.getProperty("noWebClOccurrence");
				String displayedGrandTotal = prop.getProperty("noWebClPrice");

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
						.body(ClassCoursePL.EnrollMemberInClassWithNewCreditCard(customerId,classId,classOccurrence,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberinclasswithnewcreditcard")
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.body("Message", equalTo("ItemNotFound"));
	}
	
	@Test (testName="Class Ended",description="PBI:146579")
	public void classEnded() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("endedClId");
				String classOccurrence = prop.getProperty("endedClOccurrence");
				String displayedGrandTotal = prop.getProperty("endedClPrice");

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
						.body(ClassCoursePL.EnrollMemberInClassWithNewCreditCard(customerId,classId,classOccurrence,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberinclasswithnewcreditcard")
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("EnrollmentNotAllowed - EnrollmentHasEnded"));
	}
	
	@Test (testName="Customer Not Found",description="PBI:146579")
	public void customerNotFound() {
		
				int customerId 					= 248000;
				String classId = prop.getProperty("alwaysAvailClId");
				String classOccurrence = prop.getProperty("alwaysAvailClOccurrence");
				String displayedGrandTotal = prop.getProperty("alwaysAvailClPrice");

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
						.body(ClassCoursePL.EnrollMemberInClassWithNewCreditCard(customerId,classId,classOccurrence,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberinclasswithnewcreditcard")
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.body("Message", equalTo("CustomerNotFound"));
	}
	
	@Test (testName="Card Number Invalid",description="PBI:146579")
	public void cardNumberInvalid() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("alwaysAvailClId");
				String classOccurrence = prop.getProperty("alwaysAvailClOccurrence");
				String displayedGrandTotal = prop.getProperty("alwaysAvailClPrice");
				String cardNumber				= "5454545454545400";

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
						.body(ClassCoursePL.EnrollMemberInClassWithNewCreditCard(customerId,classId,classOccurrence,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberinclasswithnewcreditcard")
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("Credit Card Processing Failed"));
	}
	
	@Test (testName="Card Number Length Incorrect",description="PBI:146579")
	public void cardNumberLengthIncorrect() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("alwaysAvailClId");
				String classOccurrence = prop.getProperty("alwaysAvailClOccurrence");
				String displayedGrandTotal = prop.getProperty("alwaysAvailClPrice");
				String cardNumber				= "54545454545454540000";

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
						.body(ClassCoursePL.EnrollMemberInClassWithNewCreditCard(customerId,classId,classOccurrence,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberinclasswithnewcreditcard")
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("Credit Card Processing Failed"));
	}
	
	@Test (testName="Class Not Found",description="PBI:146579", enabled = true)
	public void classNotFound() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = "99999";
				String classOccurrence = prop.getProperty("alwaysAvailClOccurrence");
				String displayedGrandTotal = prop.getProperty("alwaysAvailClPrice");

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithNewCreditCard(customerId,classId,classOccurrence,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberinclasswithnewcreditcard")
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.body("Message", equalTo("ItemNotFound"));
	}
	
	@Test (testName="Class Occurrence Not Found",description="PBI:146579")
	public void classOccurrenceNotFound() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("alwaysAvailClId");
				String classOccurrence = "2225-01-01";
				String displayedGrandTotal = prop.getProperty("alwaysAvailClPrice");

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithNewCreditCard(customerId,classId,classOccurrence,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberinclasswithnewcreditcard")
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.body("Message", equalTo("ItemNotFound"));
	}
	
	@Test (testName="Product Price Changed",description="PBI:146579")
	public void productPriceChanged() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("alwaysAvailClId");
				String classOccurrence = prop.getProperty("alwaysAvailClOccurrence");
				String displayedGrandTotal = "10.01";

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithNewCreditCard(customerId,classId,classOccurrence,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberinclasswithnewcreditcard")
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("ProductPriceChanged"));
	}
	
	@Test (testName="Missing Member Name On Card",description="PBI:146579")
	public void missingMemberNameOnCard() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("alwaysAvailClId");
				String classOccurrence = prop.getProperty("alwaysAvailClOccurrence");
				String displayedGrandTotal = prop.getProperty("alwaysAvailClPrice");
				String cardNumber = prop.getProperty("MC1CardNumber");
				String nameOnCard = "";

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithNewCreditCard(customerId,classId,classOccurrence,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberinclasswithnewcreditcard")
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("The NameOnCard field is required."));
	}
	
	@Test (testName="Missing Card Number",description="PBI:146579")
	public void missingCardNumber() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("alwaysAvailClId");
				String classOccurrence = prop.getProperty("alwaysAvailClOccurrence");
				String displayedGrandTotal = prop.getProperty("alwaysAvailClPrice");
				String cardNumber = "";

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithNewCreditCard(customerId,classId,classOccurrence,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberinclasswithnewcreditcard")
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("The CardNumber field is required."));
	}
	
	@Test (testName="Missing Security Code",description="PBI:146579")
	public void missingSecurityCode() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("alwaysAvailClId");
				String classOccurrence = prop.getProperty("alwaysAvailClOccurrence");
				String displayedGrandTotal = prop.getProperty("alwaysAvailClPrice");
				String securityCode = "";

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithNewCreditCard(customerId,classId,classOccurrence,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberinclasswithnewcreditcard")
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("The SecurityCode field is required."));
	}
	
	@Test (testName="Missing Address Line1",description="PBI:146579")
	public void missingAddressLine1() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("alwaysAvailClId");
				String classOccurrence = prop.getProperty("alwaysAvailClOccurrence");
				String displayedGrandTotal = prop.getProperty("alwaysAvailClPrice");
				String addressLine1	= "";

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithNewCreditCard(customerId,classId,classOccurrence,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberinclasswithnewcreditcard")
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("The AddressLine1 field is required."));
	}
	
	@Test (testName="Missing City",description="PBI:146579")
	public void missingCity() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("alwaysAvailClId");
				String classOccurrence = prop.getProperty("alwaysAvailClOccurrence");
				String displayedGrandTotal = prop.getProperty("alwaysAvailClPrice");
				String city = "";
				
				ReusableMethods.myWait(200);

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithNewCreditCard(customerId,classId,classOccurrence,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberinclasswithnewcreditcard")
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("The City field is required."));
	}
	
	@Test (testName="Missing StateProvince",description="PBI:146579")
	public void missingStateProvince() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("alwaysAvailClId");
				String classOccurrence = prop.getProperty("alwaysAvailClOccurrence");
				String displayedGrandTotal = prop.getProperty("alwaysAvailClPrice");
				String state = "";

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithNewCreditCard(customerId,classId,classOccurrence,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberinclasswithnewcreditcard")
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("The StateProvince field is required."));
	}
	
	@Test (testName="Missing Postal Code",description="PBI:146579")
	public void missingPostalCode() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("alwaysAvailClId");
				String classOccurrence = prop.getProperty("alwaysAvailClOccurrence");
				String displayedGrandTotal = prop.getProperty("alwaysAvailClPrice");
				String postalCode = "";
				
				ReusableMethods.myWait(200);
				
				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithNewCreditCard(customerId,classId,classOccurrence,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberinclasswithnewcreditcard")
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("The PostalCode field is required."));
	}
	
}
