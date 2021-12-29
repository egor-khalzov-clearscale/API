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

public class EnrollMemberInClassWithRecurringDues extends base {
	
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

	@Test (testName="Member Enrolled - Paid Class",description="PBI:154259", enabled = true)
	public void memberEnrolledPaidClass() { 
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);				
				String classId = prop.getProperty("recurringClId");
				String classOccurrence = prop.getProperty("recurringClOccurrence");
				Boolean enrollCustomerAsStandby = true;
				
				if (ReusableMethods.deleteIfEnrolled(customerId) == false) {
		
			Response res =	given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberinclasswithrecurringdues/"+customerId+"/"+classId+"/"+classOccurrence+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
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
	
	@Test (testName="Member Enrolled - Free Class",description="PBI:154259")
	public void memberEnrolledFreeClass() { 
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);				
				String classId = prop.getProperty("freeClId");
				String classOccurrence = prop.getProperty("freeClOccurrence");
				Boolean enrollCustomerAsStandby = true;
				
				if (ReusableMethods.deleteIfEnrolled(customerId) == false) {

			Response res =	given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberinclasswithrecurringdues/"+customerId+"/"+classId+"/"+classOccurrence+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
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
	
	@Test (testName="Member Enrolled - Free Class - Collections Member",description="PBI:154259")
	public void memberEnrolledFreeClassCollectionsMember() { 
		
				String c = prop.getProperty("collectionsId");
				int customerId = Integer.parseInt(c);				
				String classId = prop.getProperty("freeClId");
				String classOccurrence = prop.getProperty("freeClOccurrence");
				Boolean enrollCustomerAsStandby = true;
				
				if (ReusableMethods.deleteIfEnrolled(customerId) == false) {

			Response res =	given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberinclasswithrecurringdues/"+customerId+"/"+classId+"/"+classOccurrence+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
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
	
	@Test (testName="Member Enrolled On Standby",description="PBI:154259")
	public void memberEnrolledOnStandby() { 
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);				
				String classId = prop.getProperty("standbyClId");
				String classOccurrence = prop.getProperty("standbyClOccurrence");
				Boolean enrollCustomerAsStandby = true;
				
				if (ReusableMethods.deleteIfEnrolled(customerId) == false) {

			Response res =	given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberinclasswithrecurringdues/"+customerId+"/"+classId+"/"+classOccurrence+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
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
				}
				else{
					Assert.fail("failing test because isEnrolled() == true"); //failing test because isEnrolled() == true
				}
	} 
	
	@Test (testName="Member Already Enrolled",description="PBI:154259")
	public void memberAlreadyEnrolled() { 
		
				String c = prop.getProperty("standbyCId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("standbyClId");
				String classOccurrence = prop.getProperty("standbyClOccurrence");
				Boolean enrollCustomerAsStandby = true;

				given()

				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberinclasswithrecurringdues/"+customerId+"/"+classId+"/"+classOccurrence+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("CustomerAlreadyEnrolled"));
	}
	
	@Test (testName="Member Already On Standby",description="PBI:154259")
	public void memberAlreadyOnStandby() { 
		
				String c = prop.getProperty("standbyDId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("standbyClId");
				String classOccurrence = prop.getProperty("standbyClOccurrence");
				Boolean enrollCustomerAsStandby = true;

				given()

				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberinclasswithrecurringdues/"+customerId+"/"+classId+"/"+classOccurrence+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("CustomerAlreadyOnStandby"));
	}
	
	@Test (testName="Member Not Enrolled On Standby",description="PBI:154259")
	public void memberNotEnrolledOnStandby() { 
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("standbyClId");
				String classOccurrence = prop.getProperty("standbyClOccurrence");
				String enrollCustomerAsStandBy = "false";

				given()

				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberinclasswithrecurringdues/"+customerId+"/"+classId+"/"+classOccurrence+"/"+enrollCustomerAsStandBy+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("Full"));
	} 
	
	@Test (testName="Online Sales Not Allowed - Member Context",description="PBI:154259", enabled = true)
	public void onlineSalesNotAllowedMember() { 
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("noWebClId");
				String classOccurrence = prop.getProperty("noWebClOccurrence");
				String enrollCustomerAsStandBy = "false";

				given()

				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberinclasswithrecurringdues/"+customerId+"/"+classId+"/"+classOccurrence+"/"+enrollCustomerAsStandBy+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.body("Message", equalTo("ItemNotFound"));
	} 
	
	@Test (testName="Class Ended",description="PBI:154259")
	public void classEnded() { 
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("endedClId");
				String classOccurrence = prop.getProperty("endedClOccurrence");
				String enrollCustomerAsStandBy = "false";

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberinclasswithrecurringdues/"+customerId+"/"+classId+"/"+classOccurrence+"/"+enrollCustomerAsStandBy+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("EnrollmentNotAllowed - EnrollmentHasEnded"));
	} 
	
	@Test (testName="Customer Not Found",description="PBI:154259")
	public void customerNotFound() { 
		
				int customerId 			= 245000;
				String classId = prop.getProperty("alwaysAvailClId");
				String classOccurrence = prop.getProperty("alwaysAvailClOccurrence");
				String enrollCustomerAsStandBy = "false";

				given()

				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberinclasswithrecurringdues/"+customerId+"/"+classId+"/"+classOccurrence+"/"+enrollCustomerAsStandBy+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.body("Message", equalTo("CustomerNotFound"));
	} 
	
	@Test (testName="Class Not Found",description="PBI:154259", enabled = true)
	public void classNotFound() { 
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				int classId = 99999;
				String classOccurrence = prop.getProperty("alwaysAvailClOccurrence");
				String enrollCustomerAsStandBy = "false";
		
				given()

				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberinclasswithrecurringdues/"+customerId+"/"+classId+"/"+classOccurrence+"/"+enrollCustomerAsStandBy+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.body("Message", equalTo("ItemNotFound"));
	} 
	
	@Test (testName="Occurrence Not Found",description="PBI:154259")
	public void occurrenceNotFound() { 
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("alwaysAvailClId");
				String classOccurrence 	= "2122-12-06";
				Boolean enrollCustomerAsStandby = true;
		
				given()

				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberinclasswithrecurringdues/"+customerId+"/"+classId+"/"+classOccurrence+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.body("Message", equalTo("ItemNotFound"));
	} 
	
	@Test (testName="Recurring Dues Not Accepted",description="PBI:154259", enabled = true)
	public void recurringDuesNotAccepted() { 
		
				String customerId = prop.getProperty("availableId");
				String classId = prop.getProperty("noPunchClId");
				String classOccurrence = prop.getProperty("noPunchClOccurrence");
				String enrollCustomerAsStandBy = "false";

				given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberinclasswithrecurringdues/"+customerId+"/"+classId+"/"+classOccurrence+"/"+enrollCustomerAsStandBy+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("Class or customer configuration does not allow recurring dues enrollment"))
						;
	}

	
}
