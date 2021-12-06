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

public class UpdateStoredBankAccount extends base {
	
	String aPIKey;
	String companyId;
	String clubId;
	static String customerId;
	static String accountId;
	static String routingNumber;
	static String bankAccountNumber;
	static String bankRoutingNumber;
	static String accountHolderName;
	static String bankAccountType;
	static String isBusinessAccount;
	static String setAsHouseAccount;
	static String agreementNumbers;
	static String agreementNumbers2;
	static String updateActiveAgreements;

	@BeforeClass
	public void getData() {
		base.getPropertyData();
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = prop.getProperty("baseURI");
		
		aPIKey = prop.getProperty("X-Api-Key");
		companyId = prop.getProperty("X-CompanyId");
		clubId = prop.getProperty("X-Club1Id");
		accountId = "3";
		customerId = prop.getProperty("changeFOPId");
		bankRoutingNumber = prop.getProperty("routingNumber");
		bankAccountNumber = prop.getProperty("accountNumber");
		accountHolderName = prop.getProperty("fullName");
		bankAccountType = "Checking";
		isBusinessAccount = "false";
		setAsHouseAccount = "";
		agreementNumbers = prop.getProperty("agreementToAddUpdate");
		agreementNumbers2 = prop.getProperty("agreementToAddUpdate2");
		updateActiveAgreements = "false";
	}
	
	@Test (testName="Update Checking Account", description="PBI:180171")
	public void updateCheckingAccount() {

		Response res = 
				
				given()
//					.log().all()
					.header("accept", "application/json")
					.header("X-Api-Key", aPIKey)
					.header("X-CompanyId", companyId)
					.header("X-ClubId", clubId)
					.header("Content-Type", "application/json")
				.when()
					.body(FinancialPL.updateStoredBankAccounts(customerId,accountId, bankAccountNumber,bankRoutingNumber,accountHolderName,bankAccountType,isBusinessAccount,setAsHouseAccount, updateActiveAgreements))
					.post("/api/v3/financial/updatestoredbankaccount")
				.then()
//					.log().body()
					.extract().response();
				
				JsonPath js = ReusableMethods.rawToJson(res);
				
					Assert.assertEquals(res.statusCode(), 200);
					Assert.assertEquals(js.getInt("Status"), 200);
					Assert.assertNull(js.getString("Messages"));
					Assert.assertFalse(js.getString("AutoApprovedConfirmationNumbers").isBlank());
					Assert.assertNull(js.getString("PendingConfirmationNumber"));
					
					JsonPath storedAccountsResponse = ReusableMethods.getStoredAccountsResponse(aPIKey, companyId, clubId, customerId);

					Assert.assertTrue(storedAccountsResponse.getString("Results.BankAccountType.CurrentValue").contains(bankAccountType));
				
	}
	
	@Test (testName="Update Savings Account", description="PBI:180171")
	public void updateSavingsAccount() {
		
		String bankAccountType = "Savings";
		String accountId = "1";

		Response res = 
				
				given()
//					.log().all()
					.header("accept", "application/json")
					.header("X-Api-Key", aPIKey)
					.header("X-CompanyId", companyId)
					.header("X-ClubId", clubId)
					.header("Content-Type", "application/json")
				.when()
				.body(FinancialPL.updateStoredBankAccounts(customerId,accountId, bankAccountNumber,bankRoutingNumber,accountHolderName,bankAccountType,isBusinessAccount,setAsHouseAccount, updateActiveAgreements))
					.post("/api/v3/financial/updatestoredbankaccount")
				.then()
//					.log().body()
					.extract().response();
				
				JsonPath js = ReusableMethods.rawToJson(res);
				
					Assert.assertEquals(res.statusCode(), 200);
					Assert.assertEquals(js.getInt("Status"), 200);
					Assert.assertNull(js.getString("Messages"));
					Assert.assertFalse(js.getString("AutoApprovedConfirmationNumbers").isBlank());
					Assert.assertNull(js.getString("PendingConfirmationNumber"));
					
					JsonPath storedAccountsResponse = ReusableMethods.getStoredAccountsResponse(aPIKey, companyId, clubId, customerId);

					Assert.assertTrue(storedAccountsResponse.getString("Results.BankAccountType.CurrentValue").contains(bankAccountType));
	}
	
	@Test (testName="Update Business Account", description="PBI:180171")
	public void updateBusinessAccount() {
		
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
				.body(FinancialPL.updateStoredBankAccounts(customerId,accountId, bankAccountNumber,bankRoutingNumber,accountHolderName,bankAccountType,isBusinessAccount,setAsHouseAccount, updateActiveAgreements))
					.post("/api/v3/financial/updatestoredbankaccount")
				.then()
//					.log().body()
					.extract().response();
				
				JsonPath js = ReusableMethods.rawToJson(res);
				
					Assert.assertEquals(res.statusCode(), 200);
					Assert.assertEquals(js.getInt("Status"), 200);
					Assert.assertNull(js.getString("Messages"));
					Assert.assertFalse(js.getString("AutoApprovedConfirmationNumbers").isBlank());
					Assert.assertNull(js.getString("PendingConfirmationNumber"));
					
					JsonPath storedAccountsResponse = ReusableMethods.getStoredAccountsResponse(aPIKey, companyId, clubId, customerId);

					Assert.assertTrue(storedAccountsResponse.getString("Results.IsBusiness.CurrentValue").contains(isBusinessAccount));
	}
	
	@Test (testName="Update House Account", description="PBI:180171")
	public void updateHouseAccount() {
		
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
				.body(FinancialPL.updateStoredBankAccounts(customerId,accountId, bankAccountNumber,bankRoutingNumber,accountHolderName,bankAccountType,isBusinessAccount,setAsHouseAccount, updateActiveAgreements))
					.post("/api/v3/financial/updatestoredbankaccount")
				.then()
//					.log().body()
					.extract().response();
				
				JsonPath js = ReusableMethods.rawToJson(res);
				
					Assert.assertEquals(res.statusCode(), 200);
					Assert.assertEquals(js.getInt("Status"), 200);
					Assert.assertNull(js.getString("Messages"));
					Assert.assertFalse(js.getString("AutoApprovedConfirmationNumbers").isBlank());
					Assert.assertNull(js.getString("PendingConfirmationNumber"));
					
					JsonPath storedAccountsResponse = ReusableMethods.getStoredAccountsResponse(aPIKey, companyId, clubId, customerId);

					Assert.assertTrue(storedAccountsResponse.getString("Results.IsHouseAccount.CurrentValue").contains(setAsHouseAccount));
	}

	@Test (testName="Update Bank Account Number", description="PBI:180171")
	public void updateBankAccountNumber() {
		
		String bankAccountNumber = "4099999997";

		Response res = 
				
				given()
//					.log().all()
					.header("accept", "application/json")
					.header("X-Api-Key", aPIKey)
					.header("X-CompanyId", companyId)
					.header("X-ClubId", clubId)
					.header("Content-Type", "application/json")
				.when()
				.body(FinancialPL.updateStoredBankAccounts(customerId,accountId, bankAccountNumber,bankRoutingNumber,accountHolderName,bankAccountType,isBusinessAccount,setAsHouseAccount, updateActiveAgreements))
					.post("/api/v3/financial/updatestoredbankaccount")
				.then()
//					.log().body()
					.extract().response();
				
				JsonPath js = ReusableMethods.rawToJson(res);
				
				Assert.assertEquals(res.statusCode(), 200);
				Assert.assertEquals(js.getInt("Status"), 200);
				Assert.assertNull(js.getString("Messages"));
				Assert.assertFalse(js.getString("AutoApprovedConfirmationNumbers").isBlank());
				Assert.assertNull(js.getString("PendingConfirmationNumber"));
				
	}
	
	@Test (testName="Update Bank Routing Number", description="PBI:180171")
	public void updateBankRoutingNumber() {
		
		String bankRoutingNumber = "067812345";

		Response res = 
				
				given()
//					.log().all()
					.header("accept", "application/json")
					.header("X-Api-Key", aPIKey)
					.header("X-CompanyId", companyId)
					.header("X-ClubId", clubId)
					.header("Content-Type", "application/json")
				.when()
				.body(FinancialPL.updateStoredBankAccounts(customerId,accountId, bankAccountNumber,bankRoutingNumber,accountHolderName,bankAccountType,isBusinessAccount,setAsHouseAccount, updateActiveAgreements))
					.post("/api/v3/financial/updatestoredbankaccount")
				.then()
//					.log().body()
					.extract().response();
				
				JsonPath js = ReusableMethods.rawToJson(res);
				
				Assert.assertEquals(res.statusCode(), 200);
				Assert.assertEquals(js.getInt("Status"), 200);
				Assert.assertNull(js.getString("Messages"));
				Assert.assertFalse(js.getString("AutoApprovedConfirmationNumbers").isBlank());
				Assert.assertNull(js.getString("PendingConfirmationNumber"));
				
	}
	
	@Test (testName="Update Account Holder Name", description="PBI:180171")
	public void updateAccountHolderName() {
		
		String accountHolderName = "John Doe";

		Response res = 
				
				given()
//					.log().all()
					.header("accept", "application/json")
					.header("X-Api-Key", aPIKey)
					.header("X-CompanyId", companyId)
					.header("X-ClubId", clubId)
					.header("Content-Type", "application/json")
				.when()
				.body(FinancialPL.updateStoredBankAccounts(customerId,accountId, bankAccountNumber,bankRoutingNumber,accountHolderName,bankAccountType,isBusinessAccount,setAsHouseAccount, updateActiveAgreements))
					.post("/api/v3/financial/updatestoredbankaccount")
				.then()
//					.log().body()
					.extract().response();
				
				JsonPath js = ReusableMethods.rawToJson(res);
				
				Assert.assertEquals(res.statusCode(), 200);
				Assert.assertEquals(js.getInt("Status"), 200);
				Assert.assertNull(js.getString("Messages"));
				Assert.assertFalse(js.getString("AutoApprovedConfirmationNumbers").isBlank());
				Assert.assertNull(js.getString("PendingConfirmationNumber"));
				
	}
	
	@Test (testName="Update Agreements", description="PBI:180171")
	public void updateAgreements() {
		
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
				.body(FinancialPL.updateStoredBankAccounts(customerId,accountId, bankAccountNumber,bankRoutingNumber,accountHolderName,bankAccountType,isBusinessAccount,setAsHouseAccount, updateActiveAgreements))
					.post("/api/v3/financial/updatestoredbankaccount")
				.then()
//					.log().body()
					.extract().response();
				
				JsonPath js = ReusableMethods.rawToJson(res);
				
				Assert.assertEquals(res.statusCode(), 200);
				Assert.assertEquals(js.getInt("Status"), 200);
				Assert.assertNull(js.getString("Messages"));
				Assert.assertFalse(js.getString("AutoApprovedConfirmationNumbers").isBlank());
				Assert.assertNull(js.getString("PendingConfirmationNumber"));
				
	}
	
	@Test (testName="Update Agreements to AccountId 2", description="PBI:180171")
	public void updateAgreementsToAccountId3() {
		
		String accountId = "3";
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
				.body(FinancialPL.updateStoredBankAccounts(customerId,accountId, bankAccountNumber,bankRoutingNumber,accountHolderName,bankAccountType,isBusinessAccount,setAsHouseAccount, updateActiveAgreements))
					.post("/api/v3/financial/updatestoredbankaccount")
				.then()
//					.log().body()
					.extract().response();
				
				JsonPath js = ReusableMethods.rawToJson(res);
				
				Assert.assertEquals(res.statusCode(), 200);
				Assert.assertEquals(js.getInt("Status"), 200);
				Assert.assertNull(js.getString("Messages"));
				Assert.assertFalse(js.getString("AutoApprovedConfirmationNumbers").isBlank());
				Assert.assertNull(js.getString("PendingConfirmationNumber"));
				
	}
	
	@Test (testName="No Form of Payment", description="PBI:180171")
	public void noFOP() {

		String customerId = "247";
		
		Response res = 
				
				given()
//					.log().all()
					.header("accept", "application/json")
					.header("X-Api-Key", aPIKey)
					.header("X-CompanyId", companyId)
					.header("X-ClubId", clubId)
					.header("Content-Type", "application/json")
				.when()
				.body(FinancialPL.updateStoredBankAccounts(customerId,accountId, bankAccountNumber,bankRoutingNumber,accountHolderName,bankAccountType,isBusinessAccount,setAsHouseAccount, updateActiveAgreements))
					.post("/api/v3/financial/updatestoredbankaccount")
				.then()
//					.log().body()
					.extract().response();
				
				JsonPath js = ReusableMethods.rawToJson(res);
				
				Assert.assertEquals(res.statusCode(), 500);
				Assert.assertEquals(js.getInt("Status"), 500);
				Assert.assertTrue(js.getString("Message").contains("Sequence contains no elements"));
				
	}
	
	@Test (testName="Bank Not Found", description="PBI:180171")
	public void bankNotFound() {
		
		String bankRoutingNumber = "123456789";

		Response res = 
				
				given()
//					.log().all()
					.header("accept", "application/json")
					.header("X-Api-Key", aPIKey)
					.header("X-CompanyId", companyId)
					.header("X-ClubId", clubId)
					.header("Content-Type", "application/json")
				.when()
				.body(FinancialPL.updateStoredBankAccounts(customerId,accountId, bankAccountNumber,bankRoutingNumber,accountHolderName,bankAccountType,isBusinessAccount,setAsHouseAccount, updateActiveAgreements))
					.post("/api/v3/financial/updatestoredbankaccount")
				.then()
//					.log().body()
					.extract().response();
				
				JsonPath js = ReusableMethods.rawToJson(res);
				
				Assert.assertEquals(res.statusCode(), 400);
				Assert.assertEquals(js.getInt("Status"), 400);
				Assert.assertEquals(js.getString("Message"), "NoChanges,BankDoesNotExist");
				
	}

	@Test (testName="Customer Not Found", description="PBI:180171")
	public void customerNotFound() {
		
		String customerId = "999999";
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
				.body(FinancialPL.updateStoredBankAccounts(customerId,accountId, bankAccountNumber,bankRoutingNumber,accountHolderName,bankAccountType,isBusinessAccount,setAsHouseAccount, updateActiveAgreements))
					.post("/api/v3/financial/addstoredbankaccount")
				.then()
//					.log().body()
					.extract().response();
				
				JsonPath js = ReusableMethods.rawToJson(res);
				
					Assert.assertEquals(res.statusCode(), 500);
					Assert.assertEquals(js.getInt("Status"), 500);
					Assert.assertTrue(js.getString("Message").contains("Sequence contains no elements"));
				
	}
	

}
