package unitTests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import payloads.ClassCoursePL;
import resources.ReusableMethods;
import resources.base;

public class EnrollMemberInClassWithCardOnFile extends base {
	
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

	@Test (testName="Member Enrolled - Paid Class",description="PBI:146577")
	public void memberEnrolled_PaidClass() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("alwaysAvailClId");
				String classOccurrence = prop.getProperty("alwaysAvailClOccurrence");
				String displayedGrandTotal = prop.getProperty("alwaysAvailClPrice");
				int accountId = 1;
				Boolean enrollCustomerAsStandby = true;
				
				if (ReusableMethods.isEnrolled(customerId) == false) {

			Response res =	given()
//				.log().all()
				.header("accept", "application/json")
				.header("Content-Type", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithCardOnFile(customerId,classId,classOccurrence, displayedGrandTotal,accountId,enrollCustomerAsStandby,onlineEnrollment))
						.post("/api/v3/classcourse/enrollmemberinclasswithcardonfile")
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
	
	@Test (testName="Member Enrolled - Free Class",description="PBI:146577")
	public void memberEnrolled_FreeClass() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("freeClId");
				String classOccurrence = prop.getProperty("freeClOccurrence");
				String displayedGrandTotal = prop.getProperty("freeClPrice");
				int accountId = 1;
				Boolean enrollCustomerAsStandby 	= true;
				
				if (ReusableMethods.isEnrolled(customerId) == false) {

			Response res =	given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithCardOnFile(customerId,classId,classOccurrence, displayedGrandTotal,accountId,enrollCustomerAsStandby,onlineEnrollment))
					.post("/api/v3/classcourse/enrollmemberinclasswithcardonfile")
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
	
	@Test (testName="Member Enrolled - Free Class - Collections Member",description="PBI:146577")
	public void memberEnrolled_FreeClassCollectionsMember() {
		
				String c = prop.getProperty("collectionsId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("freeClId");
				String classOccurrence = prop.getProperty("freeClOccurrence");
				String displayedGrandTotal = prop.getProperty("freeClPrice");
				int accountId = 1;
				Boolean enrollCustomerAsStandby = true;
				
				if (ReusableMethods.isEnrolled(customerId) == false) {

			Response res =	given()

				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body("{" + 
							"  \"CustomerId\": "+customerId+"," + 
							"  \"ItemId\": \""+classId+"\"," + 
							"  \"ClassOccurrence\": \""+classOccurrence+"\"," + 
							"  \"DisplayedGrandTotal\": "+displayedGrandTotal+"," + 
							"  \"AccountId\": \""+accountId+"\"," + 
							"  \"EnrollCustomerAsStandBy\": \""+enrollCustomerAsStandby+"\"," +
							"  \"OnlineEnrollment\": \""+onlineEnrollment+"\""+
							"}")
					.post("/api/v3/classcourse/enrollmemberinclasswithcardonfile")
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
	
	@Test (testName="Member Enrolled On Standby",description="PBI:146577")
	public void memberEnrolledOnStandby() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("standbyClId");
				String classOccurrence = prop.getProperty("standbyClOccurrence");
				String displayedGrandTotal = prop.getProperty("standbyClPrice");
				int accountId = 1;
				Boolean enrollCustomerAsStandby = true;
				
				if (ReusableMethods.isEnrolled(customerId) == false) {

			Response res =	given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body("{" + 
							"  \"CustomerId\": "+customerId+"," + 
							"  \"ItemId\": \""+classId+"\"," + 
							"  \"ClassOccurrence\": \""+classOccurrence+"\"," + 
							"  \"DisplayedGrandTotal\": "+displayedGrandTotal+"," + 
							"  \"AccountId\": \""+accountId+"\"," + 
							"  \"EnrollCustomerAsStandBy\": \""+enrollCustomerAsStandby+"\"," +
							"  \"OnlineEnrollment\": \""+onlineEnrollment+"\""+
							"}")
					.post("/api/v3/classcourse/enrollmemberinclasswithcardonfile")
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
	
	@Test (testName="Member Not Enrolled On Standby",description="PBI:146577")
	public void memberNotEnrolledOnStandby() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("standbyClId");
				String classOccurrence = prop.getProperty("standbyClOccurrence");
				String displayedGrandTotal = prop.getProperty("standbyClPrice");
				int accountId					= 1;
				Boolean enrollCustomerAsStandby 	= false;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithCardOnFile(customerId,classId,classOccurrence, displayedGrandTotal,accountId,enrollCustomerAsStandby,onlineEnrollment))
					.post("/api/v3/classcourse/enrollmemberinclasswithcardonfile")
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("Full"));
	}

	@Test (testName="Member Already Enrolled",description="PBI:146577")
	public void memberAlreadyEnrolled() {
		
				String c = prop.getProperty("standbyCId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("standbyClId");
				String classOccurrence = prop.getProperty("standbyClOccurrence");
				String displayedGrandTotal = prop.getProperty("standbyClPrice");
				int accountId					= 1;
				Boolean enrollCustomerAsStandby 	= true;

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithCardOnFile(customerId,classId,classOccurrence, displayedGrandTotal,accountId,enrollCustomerAsStandby,onlineEnrollment))
					.post("/api/v3/classcourse/enrollmemberinclasswithcardonfile")
						.then()
//						.log().all()
						.assertThat().statusCode(400)
						.body("Message", equalTo("CustomerAlreadyEnrolled"));
	}
	
	@Test (testName="Member Already On Standby",description="PBI:146577")
	public void memberAlreadyOnStandby() {
		
				String c = prop.getProperty("standbyDId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("standbyClId");
				String classOccurrence = prop.getProperty("standbyClOccurrence");
				String displayedGrandTotal = prop.getProperty("standbyClPrice");
				int accountId					= 1;
				Boolean enrollCustomerAsStandby 	= true;

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithCardOnFile(customerId,classId,classOccurrence, displayedGrandTotal,accountId,enrollCustomerAsStandby,onlineEnrollment))
					.post("/api/v3/classcourse/enrollmemberinclasswithcardonfile")
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("CustomerAlreadyOnStandby"));
	}
	
	@Test (testName="Class Not Available Online",description="PBI:146577", enabled = true)
	public void classNotAvailableOnline() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("noWebClId");
				String classOccurrence = prop.getProperty("noWebClOccurrence");
				String displayedGrandTotal = prop.getProperty("noWebClPrice");
				int accountId					= 1;
				Boolean enrollCustomerAsStandby 	= true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithCardOnFile(customerId,classId,classOccurrence, displayedGrandTotal,accountId,enrollCustomerAsStandby,onlineEnrollment))
					.post("/api/v3/classcourse/enrollmemberinclasswithcardonfile")
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.body("Message", equalTo("ItemNotFound"));
	}
	
	@Test (testName="Class Ended",description="PBI:146577")
	public void classEnded() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("endedClId");
				String classOccurrence = prop.getProperty("endedClOccurrence");
				String displayedGrandTotal = prop.getProperty("endedClPrice");
				int accountId					= 1;
				Boolean enrollCustomerAsStandby 	= true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithCardOnFile(customerId,classId,classOccurrence, displayedGrandTotal,accountId,enrollCustomerAsStandby,onlineEnrollment))
					.post("/api/v3/classcourse/enrollmemberinclasswithcardonfile")
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("EnrollmentNotAllowed - EnrollmentHasEnded"));
	}
	
	@Test (testName="Customer Not Found",description="PBI:146577")
	public void customerNotFound() {
		
				int customerId 			= 245000;
				String classId = prop.getProperty("standbyClId");
				String classOccurrence = prop.getProperty("standbyClOccurrence");
				String displayedGrandTotal = prop.getProperty("standbyClPrice");
				int accountId					= 1;
				Boolean enrollCustomerAsStandby 	= true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithCardOnFile(customerId,classId,classOccurrence, displayedGrandTotal,accountId,enrollCustomerAsStandby,onlineEnrollment))
					.post("/api/v3/classcourse/enrollmemberinclasswithcardonfile")
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.body("Message", equalTo("CustomerNotFound"));
	}
	
	@Test (testName="Class Not Found",description="PBI:146577", enabled = true)
	public void classNotFound() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = "99999";
				String classOccurrence = prop.getProperty("alwaysAvailClOccurrence");
				String displayedGrandTotal = prop.getProperty("alwaysAvailClPrice");
				int accountId					= 1;
				Boolean enrollCustomerAsStandby 	= true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithCardOnFile(customerId,classId,classOccurrence, displayedGrandTotal,accountId,enrollCustomerAsStandby,onlineEnrollment))
					.post("/api/v3/classcourse/enrollmemberinclasswithcardonfile")
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.body("Message", equalTo("ItemNotFound"));
	}
	
	@Test (testName="Class Occurrence Not Found",description="PBI:146577")
	public void classOccurrenceNotFound() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("alwaysAvailClId");
				String classOccurrence 	= "2122-12-06";
				String displayedGrandTotal = prop.getProperty("alwaysAvailClPrice");
				int accountId					= 1;
				Boolean enrollCustomerAsStandby 	= true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithCardOnFile(customerId,classId,classOccurrence, displayedGrandTotal,accountId,enrollCustomerAsStandby,onlineEnrollment))
					.post("/api/v3/classcourse/enrollmemberinclasswithcardonfile")
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.body("Message", equalTo("ItemNotFound"));
	}
	
	@Test (testName="Product Price Changed",description="PBI:146577")
	public void productPriceChanged() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("alwaysAvailClId");
				String classOccurrence = prop.getProperty("alwaysAvailClOccurrence");
				String displayedGrandTotal 		= "10.01";
				int accountId					= 1;
				Boolean enrollCustomerAsStandby 	= true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithCardOnFile(customerId,classId,classOccurrence, displayedGrandTotal,accountId,enrollCustomerAsStandby,onlineEnrollment))
					.post("/api/v3/classcourse/enrollmemberinclasswithcardonfile")
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("ProductPriceChanged"));
	}
	
	@Test (testName="Scheduling Conflict",description="PBI:146577", enabled = true)
	public void schedulingConflict() {
		
				String c = prop.getProperty("standbyAId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("standbyClId");
				String classOccurrence = prop.getProperty("standbyClOccurrence");
				String displayedGrandTotal = prop.getProperty("standbyClPrice");
				int accountId					= 1;
				Boolean enrollCustomerAsStandby 	= true;

				given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithCardOnFile(customerId,classId,classOccurrence, displayedGrandTotal,accountId,enrollCustomerAsStandby,onlineEnrollment))
					.post("/api/v3/classcourse/enrollmemberinclasswithcardonfile")
						.then()
//						.log().all()
						.assertThat()
//						.body("AllowedToEnroll", equalTo(false))
						.body("Status", equalTo(400))
						.body("Message", equalTo("EnrollmentNotAllowed - MemberSchedulingConflict"));
	}
	
	@Test (testName="No FOP - Account Problem",description="PBI:146577")
	public void noFOP_AccountProblem() {
		
		String c = prop.getProperty("noFOPId");
		int customerId = Integer.parseInt(c);
		String classId = prop.getProperty("alwaysAvailClId");
		String classOccurrence = prop.getProperty("alwaysAvailClOccurrence");
		String displayedGrandTotal = prop.getProperty("alwaysAvailClPrice");
				int accountId = 1;
				Boolean enrollCustomerAsStandby 	= true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
					.when()
					.body(ClassCoursePL.EnrollMemberInClassWithCardOnFile(customerId,classId,classOccurrence, displayedGrandTotal,accountId,enrollCustomerAsStandby,onlineEnrollment))
					.post("/api/v3/classcourse/enrollmemberinclasswithcardonfile")
						.then()
//						.log().body()
						// this returns "Sequence contains no elements" because there is no card on file
						.assertThat().statusCode(500)
						.body("Message", containsString("Sequence contains no elements"));
	}
	
}
