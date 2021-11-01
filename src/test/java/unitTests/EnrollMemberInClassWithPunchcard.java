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

public class EnrollMemberInClassWithPunchcard extends base {
	
	static String aPIKey;
	static String companyId;
	static String clubId;
	
	public static Boolean onlineEnrollment = true;
	
	@BeforeClass
	public void getData() {
		base.getPropertyData();
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = prop.getProperty("baseURI");
		
		aPIKey = prop.getProperty("X-Api-Key");
		companyId = prop.getProperty("X-CompanyId");
		clubId = prop.getProperty("X-Club1Id");
	}
	
	@Test (testName="Member Enrolled - Paid Class Already Started",description="PBI:147808", enabled = true)
	public void memberEnrolledPaidClassStarted() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);				
				String classId = prop.getProperty("punchClId");
				String classOccurrence = prop.getProperty("punchClOccurrence");
				Boolean enrollCustomerAsStandby = true;
				
				if (ReusableMethods.isEnrolled(customerId) == false) {

			Response res =	given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberinclasswithpunchcard/"+customerId+"/"+classId+"/"+classOccurrence+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
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
	
		@Test (testName="Member Enrolled - Paid Class Not Started",description="PBI:147808")
	public void memberEnrolledPaidClassNotStarted() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);				
				String classId = prop.getProperty("notStartedClId");
				String classOccurrence = prop.getProperty("notStartedClOccurrence");
				Boolean enrollCustomerAsStandby = true;
			
				if (ReusableMethods.isEnrolled(customerId) == false) {

			Response res =	given()
//					.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
					.get("/api/v3/classcourse/enrollmemberinclasswithpunchcard/"+customerId+"/"+classId+"/"+classOccurrence+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
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
		
	@Test (testName="Member Enrolled On Standby",description="PBI:147808")
	public void memberEnrolledOnStandby() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				
				String classId = prop.getProperty("standbyClId");
				String classOccurrence = prop.getProperty("standbyClOccurrence");
				Boolean enrollCustomerAsStandby = true;
				
				if (ReusableMethods.isEnrolled(customerId) == false) {

			Response res =	given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
					.get("/api/v3/classcourse/enrollmemberinclasswithpunchcard/"+customerId+"/"+classId+"/"+classOccurrence+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
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
	
	@Test (testName="Member Enrolled - Free Class",description="PBI:147808")
	public void memberEnrolledFreeClass() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				
				String classId = prop.getProperty("freeClId");
				String classOccurrence = prop.getProperty("freeClOccurrence");
				Boolean enrollCustomerAsStandby = true;
				
				if (ReusableMethods.isEnrolled(customerId) == false) {

			Response res =	given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
					.get("/api/v3/classcourse/enrollmemberinclasswithpunchcard/"+customerId+"/"+classId+"/"+classOccurrence+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
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
	
	@Test (testName="Member Enrolled - Free Class - Collections Member",description="PBI:147808", enabled = true)
	public void memberEnrolledFreeClassCollectionsMember() {
		
				// this test will fail if the member didn't have enough punchcards nor is their membership setup to use recurring dues
				// So even though the class/course is free, the member must still have punchcards or a membership that accepts recurring dues. (See bug# 157917)
		
				String c = prop.getProperty("collectionsId");
				int customerId = Integer.parseInt(c);
				
				String classId = prop.getProperty("freeClId");
				String classOccurrence = prop.getProperty("freeClOccurrence");
				Boolean enrollCustomerAsStandby = true;
				
				if (ReusableMethods.isEnrolled(customerId) == false) {

			Response res =	given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
					.get("/api/v3/classcourse/enrollmemberinclasswithpunchcard/"+customerId+"/"+classId+"/"+classOccurrence+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
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
	
	@Test (testName="Not Enough Punches",description="PBI:147808")
	public void notEnoughPunches() {
		
				String c = prop.getProperty("noPunchesId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("alwaysAvailClId");
				String classOccurrence = prop.getProperty("alwaysAvailClOccurrence");
				Boolean enrollCustomerAsStandby = true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberinclasswithpunchcard/"+customerId+"/"+classId+"/"+classOccurrence+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(400)
//						.body("Message", equalTo("NotEnoughPunches"));
						.body("Message", equalTo("Class or customer configuration does not allow punchcard enrollment"));
		}
	
	@Test (testName="Punchcard Not Allowed",description="PBI:147808", enabled = true)
	public void punchcardNotAllowed() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("noPunchClId");
				String classOccurrence = prop.getProperty("noPunchClOccurrence");
				Boolean enrollCustomerAsStandby = true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberinclasswithpunchcard/"+customerId+"/"+classId+"/"+classOccurrence+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("Class or customer configuration does not allow punchcard enrollment"));
		}
	
	@Test (testName="Class Full - Standby Not Allowed",description="PBI:147808")
	public void standbyNotAllowed() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				String classId = prop.getProperty("standbyClId");
				String classOccurrence = prop.getProperty("standbyClOccurrence");
				String enrollCustomerAsStandby = "false";

				given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberinclasswithpunchcard/"+customerId+"/"+classId+"/"+classOccurrence+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.body("Message", equalTo("Full"));
		}
	
	@Test (testName="Class Not Found",description="PBI:147808", enabled = true)
	public void classNotFound() {
		
				String c = prop.getProperty("availableId");
				int customerId = Integer.parseInt(c);
				int classId = 99999;
				String classOccurrence = prop.getProperty("alwaysAvailClOccurrence");
				Boolean enrollCustomerAsStandby = true;

				given()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/classcourse/enrollmemberinclasswithpunchcard/"+customerId+"/"+classId+"/"+classOccurrence+"/"+enrollCustomerAsStandby+"/"+onlineEnrollment)
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.body("Message", equalTo("ItemNotFound"));
		}
	} 
