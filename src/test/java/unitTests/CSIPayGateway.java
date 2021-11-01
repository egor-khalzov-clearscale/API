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
import payloads.FinancialPL;
import payloads.PackagePL;
import resources.ReusableDates;
import resources.ReusableMethods;
import resources.base;

public class CSIPayGateway extends base {
	
		 static String aPIKey;
		 static String companyId;
		 static String clubId;
		 
		 static String customerId;
		 static String employeeBarcodeId;
		 static Double amount;
		 static String effectiveDate;
		 static String paymentDescription;
		 static String paymentCategory;
	
		 static String cardNumber;
		 static String nameOnCard;
		 static String expirationDate;
		 static String month;
		 static String year;
		 static String securityCode;
		 static String addressLine1;
		 static String addressLine2;
		 static String city;
		 static String stateProvince;
		 static String state = stateProvince;
		 static String postalCode;
		 static String country;
		 
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
		
		customerId = prop.getProperty("takePayId");
		employeeBarcodeId = prop.getProperty("activeEmployeeBarcodeId");
		amount = 1.00;
		effectiveDate = ReusableDates.getCurrentDatePlusOneDay();
		paymentDescription = "Credit Card Payment Received";
		paymentCategory = prop.getProperty("paymentCategory1");
		
		cardNumber = prop.getProperty("MC1CardNumber");
		nameOnCard = prop.getProperty("MC1NameOnCard");
		expirationDate = ReusableDates.getCurrentDatePlusOneYear();
		month = prop.getProperty("MC1Month");
		year = prop.getProperty("MC1Year");
		securityCode = prop.getProperty("MC1SecurityCode");
		addressLine1 = prop.getProperty("MC1AddressLine1");
		addressLine2 = prop.getProperty("MC1AddressLine2");
		city = prop.getProperty("MC1City");
		stateProvince = prop.getProperty("MC1State");
		postalCode = prop.getProperty("MC1PostalCode");

	}
	
	@Test (testName="CSI Response - Approved",description="CSIPay Gateway")
	public void cSIResponseApproved() {
		
			Double amount = 6.50;
			//String companyId = "101";
			//cardNumber = "4111111111111111";

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
			.body(FinancialPL.takePaymentWithNewCreditCard_RequiredParametersOnly(cardNumber, nameOnCard, expirationDate, securityCode, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory))
				.post("/api/v3/financial/takepaymentwithnewcreditcardformember")
			.then()
				.assertThat()
//				.log().all()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(200))
				.body("ReceiptNumber", not(nullValue()));
	}
	
	@Test (testName="CSI Response - Declined",description="CSIPay Gateway")
	public void cSIResponseDeclined() {
		
			Double amount = 70.00;

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
			.body(FinancialPL.takePaymentWithNewCreditCard_RequiredParametersOnly(cardNumber, nameOnCard, expirationDate, securityCode, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory))
				.post("/api/v3/financial/takepaymentwithnewcreditcardformember")
			.then()
				.assertThat()
//				.log().all()
				.statusLine("HTTP/1.1 400 Bad Request")
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(409))
				.body("Message", equalTo("Credit Card Processing Failed"));
	}
	
	@Test (testName="CSI Response - Card Expired",description="CSIPay Gateway", enabled = true)
	public void cSIResponseCardExpired() {
		
			String expirationDate = ReusableDates.getCurrentDateMinusXYears(1);

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
			.body(FinancialPL.takePaymentWithNewCreditCard_RequiredParametersOnly(cardNumber, nameOnCard, expirationDate, securityCode, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory))
				.post("/api/v3/financial/takepaymentwithnewcreditcardformember")
			.then()
				.assertThat()
//				.log().all()
				.statusLine("HTTP/1.1 400 Bad Request")
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(409))
				.body("Message", equalTo("Credit Card Processing Failed"));
	}
	
	@Test (testName="Course Enrolled with New Card",description="CSIPay Gateway")
	public void courseEnrolledWithNewCard() {
		
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

	@Test (testName="Training Purchased with New Card - Approved",description="CSIPay Gateway")
	public void trainingPurchasedWithNewCardApproved() {
 
				String customerId = prop.getProperty("appointmentId");
				String itemId = prop.getProperty("paidTId");
				int quantity = 5;
				String dgt = prop.getProperty("paidTGrandTotal");
				double grandTotal = Double.parseDouble(dgt);
				double displayedGrandTotal = (grandTotal * quantity);
				
			given()
				
//				.log().all()
				.header("accept", "application/json")
				.header("Content-Type", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()				
				.body(PackagePL.PurchasePackageWithNewCreditCard(customerId,itemId,quantity,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode))
				.post("/api/v3/package/purchasepackagewithnewcreditcard")
			.then()
//				.log().all()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(200))
				.body("Result", equalTo("Success"));
	}

	@Test (testName="Training Purchased with New Card - Declined",description="CSIPay Gateway")
	public void trainingPurchasedWithNewCardDeclined() {
 
				String customerId = prop.getProperty("appointmentId");
				String itemId = prop.getProperty("paidTId");
				int quantity = 10;
				String dgt = prop.getProperty("paidTGrandTotal");
				double grandTotal = Double.parseDouble(dgt);
				double displayedGrandTotal = (grandTotal * quantity);
				
			given()
				
//				.log().all()
				.header("accept", "application/json")
				.header("Content-Type", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()				
				.body(PackagePL.PurchasePackageWithNewCreditCard(customerId,itemId,quantity,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode))
				.post("/api/v3/package/purchasepackagewithnewcreditcard")
			.then()
//				.log().all()
				.statusLine("HTTP/1.1 400 Bad Request")
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(404))
				.body("Message", equalTo("Credit Card Processing Failed"));
	}




}
