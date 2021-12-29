package resources;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.testng.Assert;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import payloads.ChangeRequestPL;

public class ReusableMethods extends base {

	public static int loopCount = 0;

	public static XmlPath rawToXML(Response r) {
		String respon = r.asString();
		XmlPath x = new XmlPath(respon);
		return x;
	}

	public static JsonPath rawToJson(Response r) {

		String respon = r.asString();
		JsonPath x = new JsonPath(respon);
			myWait(1000);
		return x;
	}

	public static void main(String[] args) {
//		unenroll("236",250482,123762,227);
	}

	public static void unenroll(String companyId, int invoiceId, int enrollmentId, int customerId)
	{

		if(loopCount<5) // Counting loops so test will fail it unenroll fails 5 times
		{
			myWait(5000);
			base.getPropertyData();
			RestAssured.useRelaxedHTTPSValidation();
			RestAssured.baseURI = prop.getProperty("baseURI");

			given()
			.header("accept", prop.getProperty("accept"))
			.header("X-Api-Key", prop.getProperty("X-Api-Key"))
			.header("X-CompanyId", prop.getProperty("X-CompanyId"))
			.header("X-ClubId", prop.getProperty("X-Club1Id"))
				.when()
					.get("/api/v3/enrollmentcapability/deleteenrollment/"+companyId+"/"+enrollmentId+"")
					.then().log().body()
					;

				myWait(5000);
			given()
			.header("accept", prop.getProperty("accept"))
			.header("X-Api-Key", prop.getProperty("X-Api-Key"))
			.header("X-CompanyId", prop.getProperty("X-CompanyId"))
			.header("X-ClubId", prop.getProperty("X-Club1Id"))
				.when()
					.get("/api/v3/enrollmentcapability/deleteinvoice/"+companyId+"/"+invoiceId+"")
					.then().log().body()
					;

				myWait(5000);
			Response res =	given()
				.header("accept", prop.getProperty("accept"))
				.header("X-Api-Key", prop.getProperty("X-Api-Key"))
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", prop.getProperty("X-Club1Id"))
					.when()
						.get("/api/v3/classcourse/getclassesandcoursesbymember/"+customerId+"/2020-01-01/2200-01-01")
						.then().log().body()
						.extract().response();

				JsonPath js = rawToJson(res);

					if(res.statusCode() != 404)	// Message: "Nothing found"
					{
						loopCount++;
						System.out.println("-----------------");
						System.out.println("[INFO]: Retrying Unenroll");
						System.out.println("[INFO]: Item Description: "+js.getString("Result[0].ItemDescription"));
						System.out.println("[INFO]: Item Description: "+js.getString("Result[0].StartDateTime"));
						System.out.println("[INFO]: customerId: "+customerId);
						System.out.println("[INFO]: enrollmentId: "+enrollmentId);
						System.out.println("[INFO]: invoiceId: "+invoiceId);
						System.out.println("[INFO]: loopCount: "+loopCount);
						System.out.println("-----------------");
						unenroll(companyId, invoiceId, enrollmentId, customerId);
					}

			}
			else
	 		{
				System.out.println("----------------------------------");
				System.out.println("[ERROR] Retry Loop Count Exceeded");
				System.out.println("[ERROR] customerId: "+customerId);
				System.out.println("[ERROR] invoiceId: "+invoiceId);
				System.out.println("[ERROR] enrollmentId: "+enrollmentId);
				System.out.println("[ERROR] loopCount: "+loopCount);
				System.out.println("----------------------------------");
				loopCount = 0;
				Assert.fail("failing test because loopCount exceeded 5"); //failing test because loopCount exceeded 5
			}
			loopCount = 0;
//			return;
	}

	public static void deleteEnrollment(String companyId, int enrollmentId, int customerId)
	{

		if(loopCount<5)
		{
			myWait(2000);
			base.getPropertyData();
			RestAssured.useRelaxedHTTPSValidation();
			RestAssured.baseURI = prop.getProperty("baseURI");

			given()
			.header("accept", "application/json")
			.header("X-Api-Key", prop.getProperty("X-Api-Key"))
			.header("X-CompanyId", companyId)
			.header("X-ClubId", prop.getProperty("X-Club1Id"))
				.when()
					.get("/api/v3/enrollmentcapability/deleteenrollment/"+companyId+"/"+enrollmentId+"")
					.then()
					.assertThat().statusCode(200)
//					.log().body()
					;

				myWait(5000);

					if (deleteIfEnrolled(customerId))
					{
						loopCount++;
						System.out.println("[WARNING]----------------------------------------------");
						System.out.println("[WARNING] Retry Delete Enrollment");
						System.out.println("[WARNING] customerId: "+customerId);
						System.out.println("[WARNING] enrollmentId: "+enrollmentId);
						System.out.println("[WARNING] loopCount: "+loopCount);
						System.out.println("[WARNING]----------------------------------------------");
						deleteEnrollment(companyId, enrollmentId, customerId);
					}

			}
			else
	 		{
				System.out.println("[ERROR]------------------------------------------------");
				System.out.println("[ERROR] Retry Loop Count Exceeded");
				System.out.println("[ERROR] customerId: "+customerId);
				System.out.println("[ERROR] enrollmentId: "+enrollmentId);
				System.out.println("[ERROR] loopCount: "+loopCount);
				System.out.println("[ERROR]------------------------------------------------");
				loopCount = 0;
				Assert.fail("failing test because loopCount exceeded 5"); //failing test because loopCount exceeded 5
			}
			loopCount = 0;
//			return;
	}

	public static void deleteInvoice(String companyId, int invoiceId, int enrollmentId, int customerId)
	{

		if(loopCount<5)
		{
			myWait(2000);
			base.getPropertyData();
			RestAssured.useRelaxedHTTPSValidation();
			RestAssured.baseURI = prop.getProperty("baseURI");

			given()
			.header("accept", "application/json")
			.header("X-Api-Key", prop.getProperty("X-Api-Key"))
			.header("X-CompanyId", companyId)
			.header("X-ClubId", prop.getProperty("X-Club1Id"))
				.when()
					.get("/api/v3/enrollmentcapability/deleteinvoice/"+companyId+"/"+invoiceId+"")
					.then()
					.assertThat().statusCode(200)
//					.log().body()
					;

				myWait(5000);

					if (deleteIfEnrolled(customerId) == true)
					{
						loopCount++;
						System.out.println("[WARNING]----------------------------------------------");
						System.out.println("[WARNING] Retry Unenroll");
						System.out.println("[WARNING] customerId: "+customerId);
						System.out.println("[WARNING] invoiceId: "+invoiceId);
						System.out.println("[WARNING] Retry Count: "+loopCount);
						System.out.println("[WARNING]----------------------------------------------");
						unenroll(companyId, invoiceId, enrollmentId, customerId); // going to unenroll method so deleteEnrollment and deleteInvoice are both reran
					}
			}
			else
	 		{
				System.out.println("[ERROR]------------------------------------------------");
				System.out.println("[ERROR] Retry Loop Count Exceeded");
				System.out.println("[ERROR] customerId: "+customerId);
				System.out.println("[ERROR] enrollmentId: "+invoiceId);
				System.out.println("[ERROR] loopCount: "+loopCount);
				System.out.println("[ERROR]------------------------------------------------");
				loopCount = 0;
				Assert.fail("failing test because loopCount exceeded 5"); //failing test because loopCount exceeded 5
//				return;
			}
			loopCount = 0;
//			return;
	}

	public static boolean deleteIfEnrolled(int customerId){

		String sDateTimeNoOffset = ReusableDates.getCurrentDate();

		Response res =	given()
				.header("accept", prop.getProperty("accept"))
				.header("X-Api-Key", prop.getProperty("X-Api-Key"))
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", prop.getProperty("X-Club1Id"))
				.log().all()
					.when()
						.get("/api/v3/classcourse/getclassesandcoursesbymember/"+customerId+"/"+sDateTimeNoOffset+"/2200-01-01")
						.then()
						.extract().response();

		if (res.statusCode() != 404){
			JsonPath js = ReusableMethods.rawToJson(res);
			unenroll(prop.getProperty("X-CompanyId"),
					js.getInt("Result[0].InvoiceDetailId"),
					js.getInt("Result[0].Id"),
					js.getInt("Result[0].ForCustomerId"));
		}
		return false;//i'm to lazy to refactor
	}

	public static boolean hasAppointment(String customerId)
	{
		String sDateTimeNoOffset = ReusableDates.getCurrentDate();

		Response res = 			given()
//							.log().all()
				.header("accept", prop.getProperty("accept"))
				.header("X-Api-Key", prop.getProperty("X-Api-Key"))
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", prop.getProperty("X-Club1Id"))
				.queryParam(customerId)
					.when()
							.get("/api/v3/appointment/getappointmentsbymember/"+customerId+"/"+sDateTimeNoOffset+"/2200-01-01")
							.then()
							.extract().response();
					if(res.statusCode() != 404)
					{
						return true;
					}
					else
						{
							return false;
						}

	}

	public static int getCustomerId(int companyId, int clubId, String userName, String password) {

		int customerId;

		Response res = given()
				.header("X-Api-Key", prop.getProperty("X-Api-Key"))
//				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
//				.header("X-ClubId", prop.getProperty("X-Club1Id"))
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
				.when()
					.body("{"+
							  "\"Username\": \""+userName+"\","+
							  "\"Password\": \""+password+"\","+
							"}")
					.post("/api/v3/member/authenticatememberbyusercredentials").
				then()
				.body("Result.AuthenticationResult", equalTo("Success"))
				.extract().response();

				JsonPath js = rawToJson(res);

				customerId = js.getInt("Result.CustomerId");

				return customerId;
}

	public static void myWait(int duration)
		{
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public static String getMethodName() {

		String methodName = new Object() {}
		.getClass()
		.getEnclosingMethod()
		.getName();

		return methodName;

	}

	public static JsonPath getStoredAccountsResponse(String aPIKey, String companyId, String clubId, String customerId) {

		Response res =

		given()
			.header("accept", "application/json")
			.header("X-Api-Key", aPIKey)
			.header("X-CompanyId", companyId)
			.header("X-ClubId", clubId)
		.when()
			.get("/api/v3/financial/getstoredbankaccounts?customerId="+customerId)
		.then()
//			.log().all() // Show entire response in console
			.extract().response();

		JsonPath js = rawToJson(res);

		return js;

	}

	public static void setLastUpdateDateToday(String aPIKey, String companyId, String clubId, String customerId) {

		String fieldName = "Address2";
		String newValue = "Apt. B";
		String familyMemberCustomerIds = "";
		String submissionReasonDetail = "Test Submission Reason Details";
		String submissionReason = prop.getProperty("submissionReason");
		String signature = "null";

		// Change Request API is sent to set lastUpdateDate to today
			given()
		//		.log().all()
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(ChangeRequestPL.updatePersonalInformation(familyMemberCustomerIds, customerId, fieldName, newValue, submissionReason, submissionReasonDetail, signature))
				.post("/api/v3/changerequest/updatepersonalinformation").
			then();
	}
}