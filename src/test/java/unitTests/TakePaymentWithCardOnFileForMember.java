package unitTests;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import java.util.concurrent.TimeUnit;
import io.restassured.RestAssured;
import payloads.FinancialPL;
import resources.ReusableDates;
import resources.ReusableMethods;
import resources.base;

public class TakePaymentWithCardOnFileForMember extends base {
	
		 static String aPIKey;
		 static String companyId;
		 static String clubId;

		 static String customerId;
		 static String accountId;
		 static String employeeBarcodeId;
		 static String amount;
		 static String effectiveDate;
		 static String paymentDescription;
		 static String paymentCategory;
		 static int invoiceId;
		 
	
	@BeforeClass

	public void getData() {
		base.getPropertyData();
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = prop.getProperty("baseURI");
		
		aPIKey = prop.getProperty("X-Api-Key");
		companyId = prop.getProperty("X-CompanyId");
		clubId = prop.getProperty("X-Club1Id");
		
		customerId = prop.getProperty("takePayId");
		accountId = "4";
		employeeBarcodeId = prop.getProperty("activeEmployeeBarcodeId");
		effectiveDate = ReusableDates.getCurrentDatePlusOneDay();
		amount = "1.01";
		paymentCategory = prop.getProperty("paymentCategory1");
		paymentDescription = "ENTER ANY TEXT HERE";

	}
	
	@Test (testName="Without Invoice",description="PBI:150192")
	public void withoutInvoice() {
		
		String customerId = "248"; // changing member Available Auto to lower balance that was raised by other tests
		String amount = "5.00";
		String accountId = "11";

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(FinancialPL.takePaymentWithCardOnFileForMemberWithoutInvoice(accountId, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory))
				.post("api/v3/financial/takepaymentwithcreditcardonfileformember")
			.then()
				.assertThat()
//				.log().all()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(200))
				.body("ReceiptNumber", not(nullValue()));
	}

	@Test (testName="With Invoice",description="PBI:150192")
	public void withInvoice() {

				int invoiceId = 47080;

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(FinancialPL.takePaymentWithCardOnFileForMemberWithInvoice(accountId, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory, invoiceId))
				.post("api/v3/financial/takepaymentwithcreditcardonfileformember")
			.then()
				.assertThat()
//				.log().all()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(200))
				.body("ReceiptNumber", not(nullValue()));
	}
	
	@Test (testName="Multiple Cards On File",description="PBI:150192")
	public void multipleCardsOnFile() {
		
				String accountId = "5";				

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(FinancialPL.takePaymentWithCardOnFileForMemberWithoutInvoice(accountId, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory))
				.post("api/v3/financial/takepaymentwithcreditcardonfileformember")
			.then()
				.assertThat()
//				.log().all()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(200))
				.body("ReceiptNumber", not(nullValue()));
	}
	
	@Test (testName="Member In Collections",description="PBI:150192")
	public void memberInCollections() {
		
				String customerId = prop.getProperty("collectionsId");
				String accountId = "2";

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(FinancialPL.takePaymentWithCardOnFileForMemberWithoutInvoice(accountId, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory))
				.post("api/v3/financial/takepaymentwithcreditcardonfileformember")
			.then()
				.assertThat()
//				.log().all()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(200))
				.body("ReceiptNumber", not(nullValue()));
	}
	
	@Test (testName="Frozen Member",description="PBI:150192")
	public void frozenMember() {
		
				String customerId = prop.getProperty("frozenId");
				String accountId = "2";

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(FinancialPL.takePaymentWithCardOnFileForMemberWithoutInvoice(accountId, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory))
				.post("api/v3/financial/takepaymentwithcreditcardonfileformember")
			.then()
				.assertThat()
//				.log().all()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(200))
				.body("ReceiptNumber", not(nullValue()));
	}
	
	@Test (testName="Effective Date In Past",description="PBI:150192")
	public void effectiveDateInPast() {
		
				String effectiveDate = "2020-01-01T14:03:31.672Z";

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(FinancialPL.takePaymentWithCardOnFileForMemberWithoutInvoice(accountId, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory))
				.post("api/v3/financial/takepaymentwithcreditcardonfileformember")
			.then()
				.assertThat()
//				.log().all()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(200))
				.body("ReceiptNumber", not(nullValue()));
	}
	
	@Test (testName="Draft Limit Exceeded",description="PBI:150192")
	public void draftLimitExceeded() {

				String customerId = prop.getProperty("ccLimitId");
				String accountId = "2";
				String amount = "1.50";

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(FinancialPL.takePaymentWithCardOnFileForMemberWithoutInvoice(accountId, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory))
				.post("api/v3/financial/takepaymentwithcreditcardonfileformember")
			.then()
				.assertThat()
//				.log().all()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(200))
				.body("ReceiptNumber", not(nullValue()));
	}
	
	@Test (testName="Employee Not Found",description="PBI:150192")
	public void employeeNotFound() {

				String employeeBarcodeId = "99999";

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(FinancialPL.takePaymentWithCardOnFileForMemberWithoutInvoice(accountId, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory))
				.post("api/v3/financial/takepaymentwithcreditcardonfileformember")
			.then()
				.assertThat()
//				.log().all()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(200))
				.body("ReceiptNumber", not(nullValue()));
	}
	
	@Test (testName="Account Not Credit Card",description="PBI:150192")
	public void accountNotCreditCard() {

				String accountId = "1";

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(FinancialPL.takePaymentWithCardOnFileForMemberWithoutInvoice(accountId, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory))
				.post("api/v3/financial/takepaymentwithcreditcardonfileformember")
			.then()
				.assertThat()
//				.log().all()
				.statusCode(500)
				.time(lessThan(60L),TimeUnit.SECONDS);
	}
	
	@Test (testName="Credit Card Expired",description="PBI:150192", enabled = true)
	public void creditCardExpired() {

				String customerId = prop.getProperty("expiredCCId");
				String accountId = "2";

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(FinancialPL.takePaymentWithCardOnFileForMemberWithoutInvoice(accountId, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory))
				.post("api/v3/financial/takepaymentwithcreditcardonfileformember")
			.then()
				.assertThat()
//				.log().all()
				.statusCode(400)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(409))
				.body("Message", equalTo("Credit Card Processing Failed"));
	}
	
	@Test (testName="No FOP On File",description="PBI:150192")
	public void noFOPOnFile() {

				String customerId = prop.getProperty("noFOPId");
				String accountId = "1";

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(FinancialPL.takePaymentWithCardOnFileForMemberWithoutInvoice(accountId, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory))
				.post("api/v3/financial/takepaymentwithcreditcardonfileformember")
			.then()
				.assertThat()
//				.log().all()
				.statusCode(500)
				.time(lessThan(60L),TimeUnit.SECONDS);
	}
	
	@Test (testName="Invoice Not Found",description="PBI:150192")
	public void invoiceNotFound() {
		
				int invoiceId = 99999999;

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(FinancialPL.takePaymentWithCardOnFileForMemberWithInvoice(accountId, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory, invoiceId))
				.post("api/v3/financial/takepaymentwithcreditcardonfileformember")
			.then()
				.assertThat()
//				.log().all()
				.statusCode(500)
				.time(lessThan(60L),TimeUnit.SECONDS);
	}
	
	@Test (testName="Customer Not Found",description="PBI:150192")
	public void customerNotFound() {

				String customerId = "99999";
				
				ReusableMethods.myWait(500); // waiting to avoid 429 rate limitation error when calls run too quickly

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(FinancialPL.takePaymentWithCardOnFileForMemberWithoutInvoice(accountId, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory))
				.post("api/v3/financial/takepaymentwithcreditcardonfileformember")
			.then()
				.assertThat()
//				.log().all()
				.statusCode(500)
				.time(lessThan(60L),TimeUnit.SECONDS);
	}
	
	@Test (testName="Customer Required Null",description="PBI:150192")
	public void customerRequiredNull() {

				String customerId = prop.getProperty("NOT FOUND");// null value sent

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(FinancialPL.takePaymentWithCardOnFileForMemberWithoutInvoice(accountId, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory))
				.post("api/v3/financial/takepaymentwithcreditcardonfileformember")
			.then()
				.assertThat()
//				.log().all()
				.statusCode(400)
				.time(lessThan(60L),TimeUnit.SECONDS);
	}
	
	@Test (testName="Customer Required Blank",description="PBI:150192")
	public void customerRequiredBlank() {

				String customerId = "";

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(FinancialPL.takePaymentWithCardOnFileForMemberWithoutInvoice(accountId, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory))
				.post("api/v3/financial/takepaymentwithcreditcardonfileformember")
			.then()
				.assertThat()
//				.log().all()
				.statusCode(400)
				.time(lessThan(60L),TimeUnit.SECONDS);
	}
	
	@Test (testName="Employee Required Null",description="PBI:150192")
	public void employeeRequiredNull() {
		
				String employeeBarcodeId = prop.getProperty("NOT FOUND");// null value sent

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(FinancialPL.takePaymentWithCardOnFileForMemberWithoutInvoice(accountId, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory))
				.post("api/v3/financial/takepaymentwithcreditcardonfileformember")
			.then()
				.assertThat()
//				.log().all()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS);
	}
	
	@Test (testName="Employee Required Blank",description="PBI:150192")
	public void employeeRequiredBlank() {
		
				String employeeBarcodeId = "";

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(FinancialPL.takePaymentWithCardOnFileForMemberWithoutInvoice(accountId, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory))
				.post("api/v3/financial/takepaymentwithcreditcardonfileformember")
			.then()
				.assertThat()
//				.log().all()
				.statusCode(400)
				.time(lessThan(60L),TimeUnit.SECONDS);
	}
	
	@Test (testName="Account Not Found",description="PBI:150192")
	public void accountNotFound() {

				String accountId = "99";

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(FinancialPL.takePaymentWithCardOnFileForMemberWithoutInvoice(accountId, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory))
				.post("api/v3/financial/takepaymentwithcreditcardonfileformember")
			.then()
				.assertThat()
//				.log().all()
				.statusCode(500)
				.time(lessThan(60L),TimeUnit.SECONDS);
	}
	
	@Test (testName="Account Required Null",description="PBI:150192")
	public void accountRequiredNull() {

				String accountId = prop.getProperty("NOT FOUND");

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(FinancialPL.takePaymentWithCardOnFileForMemberWithoutInvoice(accountId, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory))
				.post("api/v3/financial/takepaymentwithcreditcardonfileformember")
			.then()
				.assertThat()
//				.log().all()
				.statusCode(400)
				.time(lessThan(60L),TimeUnit.SECONDS);
	}
	
	@Test (testName="Account Required Blank",description="PBI:150192")
	public void accountRequiredBlank() {

				String accountId = "";

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(FinancialPL.takePaymentWithCardOnFileForMemberWithoutInvoice(accountId, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory))
				.post("api/v3/financial/takepaymentwithcreditcardonfileformember")
			.then()
				.assertThat()
//				.log().all()
				.statusCode(400)
				.time(lessThan(60L),TimeUnit.SECONDS);
	}
	
	@Test (testName="Amount Zero",description="PBI:150192", enabled = true)
	public void amountZero() {

				String amount = "0.00";

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(FinancialPL.takePaymentWithCardOnFileForMemberWithoutInvoice(accountId, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory))
				.post("api/v3/financial/takepaymentwithcreditcardonfileformember")
			.then()
				.assertThat()
//				.log().all()
				.statusCode(400)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(409))
				.body("Message", equalTo("Credit Card Processing Failed"));
	}
	
	@Test (testName="Amount Required Null",description="PBI:150192")
	public void amountRequiredNull() {

				String amount = prop.getProperty("NOT FOUND");

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(FinancialPL.takePaymentWithCardOnFileForMemberWithoutInvoice(accountId, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory))
				.post("api/v3/financial/takepaymentwithcreditcardonfileformember")
			.then()
				.assertThat()
//				.log().all()
				.statusCode(400)
				.time(lessThan(60L),TimeUnit.SECONDS);
	}
	
	@Test (testName="Amount Required Blank",description="PBI:150192")
	public void amountRequiredBlank() {

				String amount = "";

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(FinancialPL.takePaymentWithCardOnFileForMemberWithoutInvoice(accountId, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory))
				.post("api/v3/financial/takepaymentwithcreditcardonfileformember")
			.then()
				.assertThat()
//				.log().all()
				.statusCode(400)
				.time(lessThan(60L),TimeUnit.SECONDS);
	}
	
	@Test (testName="Payment Description Not Required",description="PBI:150192")
	public void paymentDescriptionNotRequired() {

				String paymentDescription = "";

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(FinancialPL.takePaymentWithCardOnFileForMemberWithoutInvoice(accountId, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory))
				.post("api/v3/financial/takepaymentwithcreditcardonfileformember")
			.then()
				.assertThat()
//				.log().all()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS);
	}
	
	@Test (testName="Payment Category Not Required",description="PBI:150192")
	public void paymentCategoryNotRequired() {

				String paymentCategory = "";

			given()
//				.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
				.header("Content-Type", "application/json")
			.when()
				.body(FinancialPL.takePaymentWithCardOnFileForMemberWithoutInvoice(accountId, customerId, employeeBarcodeId, clubId, amount, effectiveDate, paymentDescription, paymentCategory))
				.post("api/v3/financial/takepaymentwithcreditcardonfileformember")
			.then()
				.assertThat()
//				.log().all()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS);
	}

}
