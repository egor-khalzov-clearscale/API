package unitTests;

import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import java.util.concurrent.TimeUnit;
import io.restassured.RestAssured;
import payloads.PackagePL;
import resources.base;

public class PurchasePackageWithNewCreditCard extends base{
	
	static String aPIKey;
	static String companyId;
	static String clubId;

	static String cardNumber;
	static String nameOnCard;
	static String month;
	static String year;
	static String securityCode;
	static String addressLine1;
	static String addressLine2;
	static String city;
	static String state;
	static String postalCode;

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
		addressLine1 = prop.getProperty("MC1AddressLine2");
		city = prop.getProperty("MC1City");
		state = prop.getProperty("MC1State");
		postalCode = prop.getProperty("MC1PostalCode");
		
	}
	
	@Test (testName="Paid Training",description="PBI:143541")
	public void paidTraining() {
 
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
	
	@Test (testName="Paid ServiceV",description="PBI:143541")
	public void paidServiceV() {
 
				String customerId = prop.getProperty("appointmentId");
				String itemId = prop.getProperty("paidServiceVId");
				int quantity = 3;
				String dgt = prop.getProperty("paidServiceVGrandTotal");
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
//				.log().body()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(200))
				.body("Result", equalTo("Success"));
	}
	
	@Test (testName="Paid Punchcard",description="PBI:143541")
	public void paidPunchcard() {
 
				String customerId = prop.getProperty("appointmentId");
				String itemId = prop.getProperty("paidPId");
				int quantity = 5;
				String dgt = prop.getProperty("paidPBasePrice");
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
//				.log().body()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(200))
				.body("Result", equalTo("Success"));
	}
	
	@Test (testName="Free Training",description="PBI:143541")
	public void freeTraining() {
 
				String customerId = prop.getProperty("appointmentId");
				String itemId = prop.getProperty("freeTId");
				int quantity = 10;
				String dgt = prop.getProperty("freeTPrice");
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
//				.log().body()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(200))
				.body("Result", equalTo("Success"));		
	}
	
	@Test (testName="Free Service",description="PBI:143541")
	public void freeService() {
 
				String customerId = prop.getProperty("appointmentId");
				String itemId = prop.getProperty("freeSVId");
				int quantity = 10;
				String dgt = prop.getProperty("freeSVPrice");
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
//				.log().body()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(200))
				.body("Result", equalTo("Success"));		
	}
	
	@Test (testName="Free Punchcard",description="PBI:143541")
	public void freePunchcard() {
 
				String customerId = prop.getProperty("appointmentId");
				String itemId = prop.getProperty("freePId");
				int quantity = 10;
				String dgt = prop.getProperty("freePPrice");
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
//				.log().body()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(200))
				.body("Result", equalTo("Success"));		
	}
	
	@Test (testName="Tier Pricing Package - Tier 1",description="PBI:143541")
	public void tierPricingPackage_Tier1() {
 
				String customerId = prop.getProperty("appointmentId");
				String itemId = prop.getProperty("tierPricingId");
				int quantity = 1;
				String dgt = prop.getProperty("tierPricingTier1Price");
				String tr = prop.getProperty("tierPricingClub1TaxRate");
				double grandTotal = Double.parseDouble(dgt);
				double taxRate = Double.parseDouble(tr);
				double calcTotal = (grandTotal * quantity);
				double calcTaxTotal =  (calcTotal * taxRate);
				double displayedGrandTotal = (calcTotal + calcTaxTotal);
				
			given()
				//.log().all()
				.header("accept", "application/json")
				.header("Content-Type", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.body(PackagePL.PurchasePackageWithNewCreditCard(customerId,itemId,quantity,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode))
				.post("/api/v3/package/purchasepackagewithnewcreditcard")
			.then()
				//.log().body()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(200))
				.body("Result", equalTo("Success"));
	}
	
	@Test (testName="Tier Pricing Package - Tier 2",description="PBI:143541")
	public void tierPricingPackage_Tier2() {
 
				String customerId = prop.getProperty("availableId");
				String itemId = prop.getProperty("tierPricingId");
				int quantity = 6;
				String dgt = prop.getProperty("tierPricingTier2Price");
				String tr = prop.getProperty("tierPricingClub1TaxRate");
				double grandTotal = Double.parseDouble(dgt);
				double taxRate = Double.parseDouble(tr);
				double calcTotal = (grandTotal * quantity);
				double calcTaxTotal =  (calcTotal * taxRate);
				double displayedGrandTotal = (calcTotal + calcTaxTotal);
				
				
			given()
				//.log().all()
				.header("accept", "application/json")
				.header("Content-Type", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.body(PackagePL.PurchasePackageWithNewCreditCard(customerId,itemId,quantity,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode))
				.post("/api/v3/package/purchasepackagewithnewcreditcard")
			.then()
				//.log().body()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(200))
				.body("Result", equalTo("Success"));		
	}
	
	@Test (testName="Taxed Item",description="PBI:143541")
	public void taxedItem() {
 
				String customerId = prop.getProperty("availableId");
				String itemId = prop.getProperty("taxSingleTId");
				int quantity = 5;
				String dgt = prop.getProperty("taxSingleTPrice");
				String tr = prop.getProperty("taxSingleTClub1TaxRate");
				double grandTotal = Double.parseDouble(dgt);
				double taxRate = Double.parseDouble(tr);
				double calcTotal = (grandTotal * quantity);
				double calcTaxTotal =  (calcTotal * taxRate);
				double displayedGrandTotal = (calcTotal + calcTaxTotal);
				
			given()
				//.log().all()
				.header("accept", "application/json")
				.header("Content-Type", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.body(PackagePL.PurchasePackageWithNewCreditCard(customerId,itemId,quantity,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode))
				.post("/api/v3/package/purchasepackagewithnewcreditcard")
			.then()
				//.log().body()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(200))
				.body("Result", equalTo("Success"));				
	}
	
	@Test (testName="Free Package",description="PBI:143541")
	public void freePackage() {
 
				String customerId = prop.getProperty("availableId");
				String itemId = prop.getProperty("freeTId");
				int quantity = 1;
				String dgt = prop.getProperty("freeTPrice");
				double grandTotal = Double.parseDouble(dgt);
				double displayedGrandTotal = (grandTotal * quantity);
				
			given()
				//.log().all()
				.header("accept", "application/json")
				.header("Content-Type", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.body(PackagePL.PurchasePackageWithNewCreditCard(customerId,itemId,quantity,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode))
				.post("/api/v3/package/purchasepackagewithnewcreditcard")
			.then()
				//.log().body()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(200))
				.body("Result", equalTo("Success"));		
	}
	
	@Test (testName="Quantity Zero",description="PBI:143541")
	public void quantityZero() {
 
				String customerId = prop.getProperty("availableId");
				String itemId = prop.getProperty("paidTId");
				int quantity = 0;
				String dgt = prop.getProperty("paidTGrandTotal");
				double displayedGrandTotal = Double.parseDouble(dgt);
				
			given()
				//.log().all()
				.header("accept", "application/json")
				.header("Content-Type", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.body(PackagePL.PurchasePackageWithNewCreditCard(customerId,itemId,quantity,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode))
				.post("/api/v3/package/purchasepackagewithnewcreditcard")
			.then()
				//.log().body()
				.statusCode(400)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(400))
				.body("Message", equalTo("NonZeroQuantityRequired"));			
	}
	
	@Test (testName="Optional Parameter - Address2",description="PBI:143541")
	public void optionalParameterAddress2() {
 
				String customerId = prop.getProperty("availableId");
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
				.body(PackagePL.PurchasePackageWithNewCreditCard_WithAddress2(customerId,itemId,quantity,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,addressLine2,city,state,postalCode))
				.post("/api/v3/package/purchasepackagewithnewcreditcard")
			.then()
//				.log().all()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(200))
				.body("Result", equalTo("Success"));
	}
	
	@Test (testName="Member Not Found",description="PBI:143541")
	public void memberNotFound() {
 
				String customerId = "99999";
				String itemId = prop.getProperty("paidTId");
				int quantity = 1;
				String dgt = prop.getProperty("paidTGrandTotal");
				double displayedGrandTotal = Double.parseDouble(dgt);
				
			given()
				//.log().all()
				.header("accept", "application/json")
				.header("Content-Type", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.body(PackagePL.PurchasePackageWithNewCreditCard(customerId,itemId,quantity,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode))
				.post("/api/v3/package/purchasepackagewithnewcreditcard")
			.then()
				//.log().body()
				.statusCode(404)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(404))
				.body("Message", equalTo("CustomerNotFound"));				
	}
	
	@Test (testName="Terminated Member",description="PBI:143541")
	public void terminatedMember() {
		
		// This restriction is not considered because the member is using a new card
 
				String customerId = prop.getProperty("terminatedId");
				String itemId = prop.getProperty("paidTId");
				int quantity = 1;
				String dgt = prop.getProperty("paidTGrandTotal");
				double displayedGrandTotal = Double.parseDouble(dgt);
				
			given()
				//.log().all()
				.header("accept", "application/json")
				.header("Content-Type", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.body(PackagePL.PurchasePackageWithNewCreditCard(customerId,itemId,quantity,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode))
				.post("/api/v3/package/purchasepackagewithnewcreditcard")
			.then()
				//.log().body()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(200))
				.body("Result", equalTo("Success"));		
	}
	
	@Test (testName="Collections Member",description="PBI:143541", enabled = true)
	public void collectionsMember() {
		
		// This restriction is not considered because the member is using a new card
 
				String customerId = prop.getProperty("collectionsId");
				String itemId = prop.getProperty("paidTId");
				int quantity = 1;
				String dgt = prop.getProperty("paidTGrandTotal");
				double displayedGrandTotal = Double.parseDouble(dgt);
				
			given()
				//.log().all()
				.header("accept", "application/json")
				.header("Content-Type", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.body(PackagePL.PurchasePackageWithNewCreditCard(customerId,itemId,quantity,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode))
				.post("/api/v3/package/purchasepackagewithnewcreditcard")
			.then()
				//.log().body()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(200))
				.body("Result", equalTo("Success"));			
	}
	
	@Test (testName="Frozen Member",description="PBI:143541")
	public void frozenMember() {
 
				String customerId = prop.getProperty("frozenId");
				String itemId = prop.getProperty("paidTId");
				int quantity = 1;
				String dgt = prop.getProperty("paidTGrandTotal");
				double displayedGrandTotal = Double.parseDouble(dgt);
				
			given()
				//.log().all()
				.header("accept", "application/json")
				.header("Content-Type", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.body(PackagePL.PurchasePackageWithNewCreditCard(customerId,itemId,quantity,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode))
				.post("/api/v3/package/purchasepackagewithnewcreditcard")
			.then()
				//.log().body()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(200))
				.body("Result", not(nullValue()));		
	}
	
	@Test (testName="Prospect",description="PBI:143541", enabled = true)
	public void prospect() {
 
				String customerId = prop.getProperty("prospectId");
				String itemId = prop.getProperty("paidTId");
				int quantity = 1;
				String dgt = prop.getProperty("paidTGrandTotal");
				double displayedGrandTotal = Double.parseDouble(dgt);
				
			given()
				//.log().all()
				.header("accept", "application/json")
				.header("Content-Type", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.body(PackagePL.PurchasePackageWithNewCreditCard(customerId,itemId,quantity,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode))
				.post("/api/v3/package/purchasepackagewithnewcreditcard")
			.then()
//				.log().body()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(200))
				.body("Result", equalTo("Success"));			
	}
	
	@Test (testName="Credit Limit Exceeded",description="PBI:143541", enabled = true)
	public void creditLimitExceeded() {
		
				// This restriction is not considered because the member is using a new card
 
				String customerId = prop.getProperty("creditLimitId");
				String itemId = prop.getProperty("paidTId");
				int quantity = 1;
				String dgt = prop.getProperty("paidTGrandTotal");
				double displayedGrandTotal = Double.parseDouble(dgt);
				
			given()
				//.log().all()
				.header("accept", "application/json")
				.header("Content-Type", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.body(PackagePL.PurchasePackageWithNewCreditCard(customerId,itemId,quantity,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode))
				.post("/api/v3/package/purchasepackagewithnewcreditcard")
			.then()
				//.log().body()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(200))
				.body("Result", equalTo("Success"));			
	}
	
	@Test (testName="Credit Limit Not Exceeded",description="PBI:143541")
	public void creditLimitNotExceeded() {
 
				String customerId = prop.getProperty("creditLimitId");
				String itemId = prop.getProperty("freeTId");
				int quantity = 1;
				String dgt = prop.getProperty("freeTPrice");
				double displayedGrandTotal = Double.parseDouble(dgt);
				
			given()
				//.log().all()
				.header("accept", "application/json")
				.header("Content-Type", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.body(PackagePL.PurchasePackageWithNewCreditCard(customerId,itemId,quantity,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode))
				.post("/api/v3/package/purchasepackagewithnewcreditcard")
			.then()
				//.log().body()
				.statusCode(200)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(200))
				.body("Result", not(nullValue()));		
	}
	
	@Test (testName="Item Not Found",description="PBI:143541")
	public void itemNotFound() {
 
				String customerId = prop.getProperty("availableId");
				String itemId = "99999";
				int quantity = 1;
				String dgt = prop.getProperty("freeTPrice");
				double displayedGrandTotal = Double.parseDouble(dgt);
				
			given()
				//.log().all()
				.header("accept", "application/json")
				.header("Content-Type", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.body(PackagePL.PurchasePackageWithNewCreditCard(customerId,itemId,quantity,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode))
				.post("/api/v3/package/purchasepackagewithnewcreditcard")
			.then()
//				.log().body()
				.statusCode(404)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(404))
				.body("Message", equalTo("ItemNotFound"));			
	}
	
	@Test (testName="Item Not Package",description="PBI:143541")
	public void itemNotPackage() {
 
				String customerId = prop.getProperty("availableId");
				String itemId = prop.getProperty("notServiceTypeVId");
				int quantity = 1;
				String dgt = prop.getProperty("freeTPrice");
				double displayedGrandTotal = Double.parseDouble(dgt);
				
			given()
				//.log().all()
				.header("accept", "application/json")
				.header("Content-Type", "application/json")
				.header("X-Api-Key", aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.body(PackagePL.PurchasePackageWithNewCreditCard(customerId,itemId,quantity,displayedGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode))
				.post("/api/v3/package/purchasepackagewithnewcreditcard")
			.then()
				//.log().body()
				.statusCode(404)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(404))
				.body("Message", equalTo("ItemNotFound"));			
	}
	
	@Test (testName="Product Price Changed",description="PBI:143541")
	public void productPriceChanged() {
 
				String customerId = prop.getProperty("availableId");
				String itemId = prop.getProperty("taxSingleTId");
				int quantity = 1;
				String dgt = prop.getProperty("taxSingleTPrice");//using base price (Grand Total - taxes)
				double displayedGrandTotal = Double.parseDouble(dgt);
				
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
//				.log().body()
				.statusCode(400)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(400))
				.body("Message", equalTo("ProductPriceChanged"));			
	}
	@Test (testName="Package Quantity Limit Exceeded",description="PBI:143541")
	public void packageQuantityLimitExceeded() {
				String customerId = prop.getProperty("availableId");
				String itemId = prop.getProperty("limit10PId");
				int quantity = 15;
				String dGT = prop.getProperty("limit10PBasePrice");
				double displayedGrandTotal = Double.parseDouble(dGT);
				double calcGrandTotal = (displayedGrandTotal * quantity);
				
			given()
				//.log().all()
				.header("accept", "application/json")
				.header("Content-Type", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
			.when()
				.body(PackagePL.PurchasePackageWithNewCreditCard(customerId,itemId,quantity,calcGrandTotal,cardNumber,nameOnCard,month,year,securityCode,addressLine1,city,state,postalCode))
				.post("/api/v3/package/purchasepackagewithnewcreditcard")
			.then()
	//			.log().body()
				.statusCode(400)
				.time(lessThan(60L),TimeUnit.SECONDS)
				.body("Status", equalTo(400))
	//			.body("Message", equalTo("InvoiceError - Missing quantity configuration"));
					.body("Message", startsWith("InvoiceError - "))
					.body("Message",containsString("The creator of this fault did not specify a Reason."));
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
