package unitTests;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import resources.base;

public class GetStoredBankAccount extends base {
	
	static String aPIKey;
	static String companyId;
	static String clubId;
	static String customerId;
	static String bankName;
	static String routingNumber;
	static String truncatedAccountNumber;

	@BeforeClass
	public void getData() {
		base.getPropertyData();
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = prop.getProperty("baseURI");
		
		aPIKey = prop.getProperty("X-Api-Key");
		companyId = prop.getProperty("X-CompanyId");
		clubId = prop.getProperty("X-Club1Id");
		
		bankName = prop.getProperty("bankName");
		routingNumber = prop.getProperty("routingNumber");
		truncatedAccountNumber = prop.getProperty("truncatedAccountNumber");
	}
	
	@Test (testName="Checking Account", description="PBI:180172")
	public void checkingAccount() {
		
				String member = prop.getProperty("checkingId");
				String memberName = prop.getProperty("checkingName");

				given()
//					.log().all()
					.header("accept", "application/json")
					.header("X-Api-Key", aPIKey)
					.header("X-CompanyId", companyId)
					.header("X-ClubId", clubId)
				.when()
					.get("/api/v3/financial/getstoredbankaccounts?customerId="+member)
				.then()
//						.log().body()
						.assertThat().statusCode(200)
						.body("Status", equalTo(200))
						.body("Results[0].AccountId.CurrentValue", equalTo("1"))
						.body("Results[0].AccountId.PendingChange", equalTo(false))
						.body("Results[0].BankAccountType.CurrentValue", equalTo("Checking"))
						.body("Results[0].BankAccountType.PendingChange", equalTo(false))
						.body("Results[0].BankName.CurrentValue", equalTo(bankName))
						.body("Results[0].BankName.PendingChange", equalTo(false))
						.body("Results[0].BillingName.CurrentValue", equalTo(memberName))
						.body("Results[0].BillingName.PendingChange", equalTo(false))
						.body("Results[0].IsBusiness.CurrentValue", equalTo(false))
						.body("Results[0].IsBusiness.PendingChange", equalTo(false))
						.body("Results[0].IsHouseAccount.CurrentValue", equalTo(false))
						.body("Results[0].IsHouseAccount.PendingChange", equalTo(false))
						.body("Results[0].RoutingNumber.CurrentValue", equalTo(routingNumber))
						.body("Results[0].RoutingNumber.PendingChange", equalTo(false))
						.body("Results[0].TruncatedAccountNumber.CurrentValue", equalTo(truncatedAccountNumber))
						.body("Results[0].TruncatedAccountNumber.PendingChange", equalTo(false));
	}
	
	@Test (testName="Savings Account", description="PBI:180172")
	public void savingsAccount() {
		
				String member = prop.getProperty("savingsId");
				String memberName = prop.getProperty("savingsName");

				given()
//					.log().all()
					.header("accept", "application/json")
					.header("X-Api-Key", aPIKey)
					.header("X-CompanyId", companyId)
					.header("X-ClubId", clubId)
				.when()
					.get("/api/v3/financial/getstoredbankaccounts?customerId="+member)
				.then()
//						.log().body()
						.assertThat().statusCode(200)
						.body("Status", equalTo(200))
						.body("Results[0].AccountId.CurrentValue", equalTo("2"))
						.body("Results[0].AccountId.PendingChange", equalTo(false))
						.body("Results[0].BankAccountType.CurrentValue", equalTo("Savings"))
						.body("Results[0].BankAccountType.PendingChange", equalTo(false))
						.body("Results[0].BankName.PendingChange", equalTo(false))
						.body("Results[0].BillingName.CurrentValue", equalTo(memberName))
						.body("Results[0].BillingName.PendingChange", equalTo(false))
						.body("Results[0].IsBusiness.CurrentValue", equalTo(false))
						.body("Results[0].IsBusiness.PendingChange", equalTo(false))
						.body("Results[0].IsHouseAccount.CurrentValue", equalTo(false))
						.body("Results[0].IsHouseAccount.PendingChange", equalTo(false))
						.body("Results[0].RoutingNumber.PendingChange", equalTo(false))
						.body("Results[0].TruncatedAccountNumber.PendingChange", equalTo(false));
	}

	@Test (testName="No Bank Account", description="PBI:180172")
	public void noBankAccount() {
		
				String member = prop.getProperty("noFOPId");

				given()
//					.log().all()
					.header("accept", "application/json")
					.header("X-Api-Key", aPIKey)
					.header("X-CompanyId", companyId)
					.header("X-ClubId", clubId)
				.when()
					.get("/api/v3/financial/getstoredbankaccounts?customerId="+member)
				.then()
//						.log().all()
						.assertThat().statusCode(200)
						.body("Status", equalTo(204))
						.body("Messages[0]", equalTo("No stored bank accounts found."));
	}

}
