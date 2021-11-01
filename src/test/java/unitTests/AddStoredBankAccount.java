package unitTests;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import payloads.FinancialPL;
import resources.ReusableMethods;
import resources.base;

public class AddStoredBankAccount extends base {
	
	String aPIKey;
	String companyId;
	String clubId;
	static String customerId;
	static String routingNumber;
	
	static String bankAccountNumber;
	static String bankRoutingNumber;
	static String accountHolderName;
	static String bankAccountType;
	static String isBusinessAccount;
	static String setAsHouseAccount;
	static String updateActiveAgreements;

	@BeforeClass
	public void getData() {
		base.getPropertyData();
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = prop.getProperty("baseURI");
		
		aPIKey = prop.getProperty("X-Api-Key");
		companyId = prop.getProperty("X-CompanyId");
		clubId = prop.getProperty("X-Club1Id");
		
		customerId = prop.getProperty("changeFOPId");
		bankRoutingNumber = prop.getProperty("routingNumber");
		bankAccountNumber = prop.getProperty("accountNumber");
		accountHolderName = prop.getProperty("fullName");
		bankAccountType = "Checking";
		isBusinessAccount = "false";
		setAsHouseAccount = "false";
		updateActiveAgreements = "false";
	}
	
	@Test (testName="Add Checking Account", description="PBI:180170")
	public void addCheckingAccount() {

		Response res = 
				
				given()
//					.log().all()
					.header("accept", "application/json")
					.header("X-Api-Key", aPIKey)
					.header("X-CompanyId", companyId)
					.header("X-ClubId", clubId)
					.header("Content-Type", "application/json")
				.when()
					.body(FinancialPL.addStoredBankAccounts(customerId,bankAccountNumber,bankRoutingNumber,accountHolderName,bankAccountType,isBusinessAccount,setAsHouseAccount, updateActiveAgreements))
					.post("/api/v3/financial/addstoredbankaccount")
				.then()
//					.log().body()
					.extract().response();
				
				JsonPath js = ReusableMethods.rawToJson(res);
				
					Assert.assertEquals(res.statusCode(), 200);
					Assert.assertEquals(js.getInt("Status"), 200);
					Assert.assertNull(js.getString("Messages"));
					Assert.assertTrue(js.getInt("AutoApprovedConfirmationNumber") > 0);
					Assert.assertNull(js.getString("PendingConfirmationNumber"));
					
					JsonPath storedAccountsResponse = ReusableMethods.getStoredAccountsResponse(aPIKey, companyId, clubId, customerId);

					Assert.assertTrue(storedAccountsResponse.getString("Results.BankAccountType.CurrentValue").contains(bankAccountType));
				
	}
	
	@Test (testName="Add Savings Account", description="PBI:180170")
	public void addSavingsAccount() {
		
		String bankAccountType = "Savings";

		Response res = 
				
				given()
//					.log().all()
					.header("accept", "application/json")
					.header("X-Api-Key", aPIKey)
					.header("X-CompanyId", companyId)
					.header("X-ClubId", clubId)
					.header("Content-Type", "application/json")
				.when()
				.body(FinancialPL.addStoredBankAccounts(customerId,bankAccountNumber,bankRoutingNumber,accountHolderName,bankAccountType,isBusinessAccount,setAsHouseAccount, updateActiveAgreements))
					.post("/api/v3/financial/addstoredbankaccount")
				.then()
//					.log().body()
					.extract().response();
				
				JsonPath js = ReusableMethods.rawToJson(res);
				
					Assert.assertEquals(res.statusCode(), 200);
					Assert.assertEquals(js.getInt("Status"), 200);
					Assert.assertNull(js.getString("Messages"));
					Assert.assertTrue(js.getInt("AutoApprovedConfirmationNumber") > 0);
					Assert.assertNull(js.getString("PendingConfirmationNumber"));
					
					JsonPath storedAccountsResponse = ReusableMethods.getStoredAccountsResponse(aPIKey, companyId, clubId, customerId);

					Assert.assertTrue(storedAccountsResponse.getString("Results.BankAccountType.CurrentValue").contains(bankAccountType));
	}
	
	@Test (testName="Add Account Update Active Agreements", description="PBI:180170")
	public void addAccountUpdateActiveAgreements() {
		
		String updateActiveAgreements = "true";

		Response res = 
				
				given()
//					.log().all()
					.header("accept", "application/json")
					.header("X-Api-Key", aPIKey)
					.header("X-CompanyId", companyId)
					.header("X-ClubId", clubId)
					.header("Content-Type", "application/json")
				.when()
				.body(FinancialPL.addStoredBankAccounts(customerId,bankAccountNumber,bankRoutingNumber,accountHolderName,bankAccountType,isBusinessAccount,setAsHouseAccount, updateActiveAgreements))
					.post("/api/v3/financial/addstoredbankaccount")
				.then()
//					.log().body()
					.extract().response();
				
				JsonPath js = ReusableMethods.rawToJson(res);
				
					Assert.assertEquals(res.statusCode(), 200);
					Assert.assertEquals(js.getInt("Status"), 200);
					Assert.assertNull(js.getString("Messages"));
					Assert.assertTrue(js.getInt("AutoApprovedConfirmationNumber") > 0);
					Assert.assertNull(js.getString("PendingConfirmationNumber"));
	}
	
	@Test (testName="Add Business Account", description="PBI:180170")
	public void addBusinessAccount() {
		
		String isBusinessAccount = "true";

		Response res = 
				
				given()
//					.log().all()
					.header("accept", "application/json")
					.header("X-Api-Key", aPIKey)
					.header("X-CompanyId", companyId)
					.header("X-ClubId", clubId)
					.header("Content-Type", "application/json")
				.when()
				.body(FinancialPL.addStoredBankAccounts(customerId,bankAccountNumber,bankRoutingNumber,accountHolderName,bankAccountType,isBusinessAccount,setAsHouseAccount, updateActiveAgreements))
					.post("/api/v3/financial/addstoredbankaccount")
				.then()
//					.log().body()
					.extract().response();
				
				JsonPath js = ReusableMethods.rawToJson(res);
				
					Assert.assertEquals(res.statusCode(), 200);
					Assert.assertEquals(js.getInt("Status"), 200);
					Assert.assertNull(js.getString("Messages"));
					Assert.assertTrue(js.getInt("AutoApprovedConfirmationNumber") > 0);
					Assert.assertNull(js.getString("PendingConfirmationNumber"));
					
					JsonPath storedAccountsResponse = ReusableMethods.getStoredAccountsResponse(aPIKey, companyId, clubId, customerId);

					Assert.assertTrue(storedAccountsResponse.getString("Results.IsBusiness.CurrentValue").contains(isBusinessAccount));
	}
	
	@Test (testName="Add House Account", description="PBI:180170")
	public void addHouseAccount() {
		
		String setAsHouseAccount = "true";

		Response res = 
				
				given()
//					.log().all()
					.header("accept", "application/json")
					.header("X-Api-Key", aPIKey)
					.header("X-CompanyId", companyId)
					.header("X-ClubId", clubId)
					.header("Content-Type", "application/json")
				.when()
				.body(FinancialPL.addStoredBankAccounts(customerId,bankAccountNumber,bankRoutingNumber,accountHolderName,bankAccountType,isBusinessAccount,setAsHouseAccount, updateActiveAgreements))
					.post("/api/v3/financial/addstoredbankaccount")
				.then()
//					.log().body()
					.extract().response();
				
				JsonPath js = ReusableMethods.rawToJson(res);
				
					Assert.assertEquals(res.statusCode(), 200);
					Assert.assertEquals(js.getInt("Status"), 200);
					Assert.assertNull(js.getString("Messages"));
					Assert.assertTrue(js.getInt("AutoApprovedConfirmationNumber") > 0);
					Assert.assertNull(js.getString("PendingConfirmationNumber"));
					
					JsonPath storedAccountsResponse = ReusableMethods.getStoredAccountsResponse(aPIKey, companyId, clubId, customerId);

					Assert.assertTrue(storedAccountsResponse.getString("Results.IsHouseAccount.CurrentValue").contains(setAsHouseAccount));
	}

	@Test (testName="Customer Not Found", description="PBI:180170")
	public void customerNotFound() {
		
		String customerId = "999999";

		Response res = 
				
				given()
//					.log().all()
					.header("accept", "application/json")
					.header("X-Api-Key", aPIKey)
					.header("X-CompanyId", companyId)
					.header("X-ClubId", clubId)
					.header("Content-Type", "application/json")
				.when()
				.body(FinancialPL.addStoredBankAccounts(customerId,bankAccountNumber,bankRoutingNumber,accountHolderName,bankAccountType,isBusinessAccount,setAsHouseAccount, updateActiveAgreements))
					.post("/api/v3/financial/addstoredbankaccount")
				.then()
//					.log().body()
					.extract().response();
				
				JsonPath js = ReusableMethods.rawToJson(res);
				
					Assert.assertEquals(res.statusCode(), 500);
					Assert.assertEquals(js.getInt("Status"), 500);
					Assert.assertTrue(js.getString("Message").contains("Sequence contains no elements"));
				
	}
	
	@Test (testName="Bank Account Number Missing", description="PBI:180170")
	public void bankAccountNumberMissing() {
		
		String bankAccountNumber = "";

		Response res = 
				
				given()
//					.log().all()
					.header("accept", "application/json")
					.header("X-Api-Key", aPIKey)
					.header("X-CompanyId", companyId)
					.header("X-ClubId", clubId)
					.header("Content-Type", "application/json")
				.when()
				.body(FinancialPL.addStoredBankAccounts(customerId,bankAccountNumber,bankRoutingNumber,accountHolderName,bankAccountType,isBusinessAccount,setAsHouseAccount, updateActiveAgreements))
					.post("/api/v3/financial/addstoredbankaccount")
				.then()
//					.log().body()
					.extract().response();
				
				JsonPath js = ReusableMethods.rawToJson(res);
				
					Assert.assertEquals(res.statusCode(), 400);
					Assert.assertEquals(js.getInt("Status"), 400);
					Assert.assertEquals(js.getString("Message"),"The BankAccountNumber field is required.");
				
	}
	
	@Test (testName="Bank Routing Number Missing", description="PBI:180170")
	public void bankRoutingNumberMissing() {
		
		String bankRoutingNumber = "";

		Response res = 
				
				given()
//					.log().all()
					.header("accept", "application/json")
					.header("X-Api-Key", aPIKey)
					.header("X-CompanyId", companyId)
					.header("X-ClubId", clubId)
					.header("Content-Type", "application/json")
				.when()
				.body(FinancialPL.addStoredBankAccounts(customerId,bankAccountNumber,bankRoutingNumber,accountHolderName,bankAccountType,isBusinessAccount,setAsHouseAccount, updateActiveAgreements))
					.post("/api/v3/financial/addstoredbankaccount")
				.then()
//					.log().body()
					.extract().response();
				
				JsonPath js = ReusableMethods.rawToJson(res);
				
					Assert.assertEquals(res.statusCode(), 400);
					Assert.assertEquals(js.getInt("Status"), 400);
					Assert.assertEquals(js.getString("Message"),"The BankRoutingNumber field is required.");
				
	}
	
	@Test (testName="Account Holder Name Missing", description="PBI:180170")
	public void accountHolderNameMissing() {
		
		String accountHolderName = "";

		Response res = 
				
				given()
//					.log().all()
					.header("accept", "application/json")
					.header("X-Api-Key", aPIKey)
					.header("X-CompanyId", companyId)
					.header("X-ClubId", clubId)
					.header("Content-Type", "application/json")
				.when()
				.body(FinancialPL.addStoredBankAccounts(customerId,bankAccountNumber,bankRoutingNumber,accountHolderName,bankAccountType,isBusinessAccount,setAsHouseAccount, updateActiveAgreements))
					.post("/api/v3/financial/addstoredbankaccount")
				.then()
//					.log().body()
					.extract().response();
				
				JsonPath js = ReusableMethods.rawToJson(res);
				
					Assert.assertEquals(res.statusCode(), 400);
					Assert.assertEquals(js.getInt("Status"), 400);
					Assert.assertEquals(js.getString("Message"),"The AccountHolderName field is required.");
				
	}

}
