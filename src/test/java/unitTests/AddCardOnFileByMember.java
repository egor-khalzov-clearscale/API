package unitTests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import payloads.MemberPL;
import resources.ReusableMethods;
import resources.base;
import resources.myActions;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AddCardOnFileByMember extends base {

    static String aPIKey;
    static String companyId;
    static String clubId;

    static String updateActiveAgreements;
    static String customerId;
    static String cardNumber;
    static String cardType;
    static String expirationMonth;
    static String expirationYear;
    static String cardHolderName;
    static String addressLine1;
    static String addressLine2;
    static String city;
    static String stateProvince;
    static String postalCode;

    static String addressIsSameAsMemberAddress;
    static String useInPos;
    static String setAsHouseAccount;

    static String newMemberId;

    @BeforeClass
    public void getData() {
        base.getPropertyData();
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = prop.getProperty("baseURI");

        aPIKey = prop.getProperty("X-Api-Key");
        companyId = prop.getProperty("X-CompanyId");
        clubId = prop.getProperty("X-Club1Id");

        updateActiveAgreements = "false";
        addressIsSameAsMemberAddress = "false";
        useInPos = "true";
        setAsHouseAccount = "true";
        customerId = prop.getProperty("changeCCMember1Id");

        cardNumber = prop.getProperty("changeCCMember1AccountNumber");
        cardType = prop.getProperty("changeCCMember1CardType");
        expirationMonth = prop.getProperty("changeCCMember1ExpirationMonth");
        expirationYear = prop.getProperty("changeCCMember1ExpirationYear");
        cardHolderName = prop.getProperty("changeCCMember1NameOnCard");
        addressLine1 = prop.getProperty("changeCCMember1AddressLine1");
        addressLine2 = prop.getProperty("changeCCMember1AddressLine2");
        city = prop.getProperty("changeCCMember1City");
        stateProvince = prop.getProperty("changeCCMember1StateProvince");
        postalCode = prop.getProperty("changeCCMember1PostalCode");
    }

    @Test(testName = "Card Added for New Member", description = "PBI:150278", priority = 1)
    public void cardAddedNewMember() {

        newMemberId = myActions.createMember(aPIKey, companyId, clubId);

        String customerId = newMemberId;
        int customerIdInt = Integer.parseInt(customerId);

        //checking that customer was created
        if (!(customerIdInt > 0)) {
            newMemberId = myActions.createMember(aPIKey, companyId, clubId);
        }

        given()
//				.log().all()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("X-Api-Key", aPIKey)
                .header("X-CompanyId", companyId)
                .header("X-ClubId", clubId)
                .when()
                .body(MemberPL.addCardOnFileByMemberCardNumberOnly(
                        updateActiveAgreements,
                        customerId,
                        cardNumber,
                        expirationMonth,
                        expirationYear,
                        cardHolderName,
                        addressIsSameAsMemberAddress,
                        addressLine1,
                        addressLine2,
                        city,
                        stateProvince,
                        postalCode,
                        useInPos,
                        setAsHouseAccount))
                .post("/api/v3/member/addcardonfilebymember")
                .then()
//				.log().all()
                .assertThat().statusCode(200);

        Response res = resources.myGets.getCardsOnFileByMember(aPIKey, companyId, clubId, customerId);

        JsonPath js = ReusableMethods.rawToJson(res);

        Assert.assertTrue(js.getString("Result[0].ExpirationDate").contains(expirationMonth));
        Assert.assertTrue(js.getString("Result[0].ExpirationDate").contains(expirationYear));
        Assert.assertEquals(js.getString("Result[0].NameOnCard"), cardHolderName);
        Assert.assertEquals(js.getString("Result[0].Address.AddressLine1"), addressLine1);
        Assert.assertEquals(js.getString("Result[0].Address.AddressLine2"), addressLine2);
        Assert.assertEquals(js.getString("Result[0].Address.City"), city);
        Assert.assertEquals(js.getString("Result[0].Address.PostalCode"), postalCode);
        Assert.assertEquals(js.getString("Result[0].Address.StateProvince"), stateProvince);
        Assert.assertEquals(js.getString("Result[0].CardType"), cardType);
        Assert.assertEquals(js.getBoolean("Result[0].IsHouseAccount"), true);
    }

    @Test(testName = "Second Card Added for New Member", dependsOnMethods = "cardAddedNewMember", description = "PBI:150278", priority = 2)
    public void secondCardAddedNewMember() {

        String customerId = newMemberId;

        given()
//				.log().all()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("X-Api-Key", aPIKey)
                .header("X-CompanyId", companyId)
                .header("X-ClubId", clubId)
                .when()
                .body(MemberPL.addCardOnFileByMemberCardNumberOnly(
                        updateActiveAgreements,
                        customerId,
                        cardNumber,
                        expirationMonth,
                        expirationYear,
                        cardHolderName,
                        addressIsSameAsMemberAddress,
                        addressLine1,
                        addressLine2,
                        city,
                        stateProvince,
                        postalCode,
                        useInPos,
                        setAsHouseAccount))
                .post("/api/v3/member/addcardonfilebymember")
                .then()
//				.log().all()
                .assertThat().statusCode(200);

        Response res = resources.myGets.getCardsOnFileByMember(aPIKey, companyId, clubId, customerId);

        JsonPath js = ReusableMethods.rawToJson(res);

        Assert.assertTrue(js.getString("Result[0].ExpirationDate").contains(expirationMonth));
        Assert.assertTrue(js.getString("Result[0].ExpirationDate").contains(expirationYear));
        Assert.assertEquals(js.getString("Result[0].NameOnCard"), cardHolderName);
        Assert.assertEquals(js.getString("Result[0].Address.AddressLine1"), addressLine1);
        Assert.assertEquals(js.getString("Result[0].Address.AddressLine2"), addressLine2);
        Assert.assertEquals(js.getString("Result[0].Address.City"), city);
        Assert.assertEquals(js.getString("Result[0].Address.PostalCode"), postalCode);
        Assert.assertEquals(js.getString("Result[0].Address.StateProvince"), stateProvince);
        Assert.assertEquals(js.getString("Result[0].CardType"), cardType);
        Assert.assertEquals(js.getBoolean("Result[0].IsHouseAccount"), false);
    }

    @Test(testName = "Card Added not House Acount", description = "PBI:150278")
    public void cardAddedNotHouseAcount() {

        String customerId = myActions.createMember(aPIKey, companyId, clubId);
        String setAsHouseAccount = "false";

        given()
//				.log().all()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("X-Api-Key", aPIKey)
                .header("X-CompanyId", companyId)
                .header("X-ClubId", clubId)
                .when()
                .body(MemberPL.addCardOnFileByMemberCardNumberOnly(
                        updateActiveAgreements,
                        customerId,
                        cardNumber,
                        expirationMonth,
                        expirationYear,
                        cardHolderName,
                        addressIsSameAsMemberAddress,
                        addressLine1,
                        addressLine2,
                        city,
                        stateProvince,
                        postalCode,
                        useInPos,
                        setAsHouseAccount))
                .post("/api/v3/member/addcardonfilebymember")
                .then()
//				.log().all()
                .assertThat()
                .statusCode(200);

        Response res = resources.myGets.getCardsOnFileByMember(aPIKey, companyId, clubId, customerId);

        JsonPath js = ReusableMethods.rawToJson(res);

        Assert.assertTrue(js.getString("Result[0].ExpirationDate").contains(expirationMonth));
        Assert.assertTrue(js.getString("Result[0].ExpirationDate").contains(expirationYear));
        Assert.assertEquals(js.getString("Result[0].NameOnCard"), cardHolderName);
        Assert.assertEquals(js.getString("Result[0].Address.AddressLine1"), addressLine1);
        Assert.assertEquals(js.getString("Result[0].Address.AddressLine2"), addressLine2);
        Assert.assertEquals(js.getString("Result[0].Address.City"), city);
        Assert.assertEquals(js.getString("Result[0].Address.PostalCode"), postalCode);
        Assert.assertEquals(js.getString("Result[0].Address.StateProvince"), stateProvince);
        Assert.assertEquals(js.getString("Result[0].CardType"), cardType);
        Assert.assertEquals(js.getString("Result[0].IsHouseAccount"), setAsHouseAccount);
    }

    @Test(testName = "Card Added and Agreement Updated", description = "PBI:150278", enabled = true)
    // Only enable this test to during regression cycle
    public void cardAddedAgreementUpdated() {

        String customerId = prop.getProperty("agreementMemberId");
        String updateActiveAgreements = "true";

        given()
//				.log().all()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("X-Api-Key", aPIKey)
                .header("X-CompanyId", companyId)
                .header("X-ClubId", clubId)
                .when()
                .body(MemberPL.addCardOnFileByMemberCardNumberOnly(
                        updateActiveAgreements,
                        customerId,
                        cardNumber,
                        expirationMonth,
                        expirationYear,
                        cardHolderName,
                        addressIsSameAsMemberAddress,
                        addressLine1,
                        addressLine2,
                        city,
                        stateProvince,
                        postalCode,
                        useInPos,
                        setAsHouseAccount))
                .post("/api/v3/member/addcardonfilebymember")
                .then()
//				.log().all()
                .assertThat().statusCode(200);
    }

    @Test(testName = "Customer Not Found", description = "PBI:150278")
    public void customerNotFound() {

        String customerId = "999999";

        given()
//				.log().all()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("X-Api-Key", aPIKey)
                .header("X-CompanyId", companyId)
                .header("X-ClubId", clubId)
                .when()
                .body(MemberPL.addCardOnFileByMemberCardNumberOnly(
                        updateActiveAgreements,
                        customerId,
                        cardNumber,
                        expirationMonth,
                        expirationYear,
                        cardHolderName,
                        addressIsSameAsMemberAddress,
                        addressLine1,
                        addressLine2,
                        city,
                        stateProvince,
                        postalCode,
                        useInPos,
                        setAsHouseAccount))
                .post("/api/v3/member/addcardonfilebymember")
                .then()
//				.log().all()
                .statusCode(500);
    }

    @Test(testName = "Customer Required", description = "PBI:150278")
    public void customerRequired() {

        String customerId = "";

        given()
//				.log().all()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("X-Api-Key", aPIKey)
                .header("X-CompanyId", companyId)
                .header("X-ClubId", clubId)
                .when()
                .body(MemberPL.addCardOnFileByMemberCardNumberOnly(
                        updateActiveAgreements,
                        customerId,
                        cardNumber,
                        expirationMonth,
                        expirationYear,
                        cardHolderName,
                        addressIsSameAsMemberAddress,
                        addressLine1,
                        addressLine2,
                        city,
                        stateProvince,
                        postalCode,
                        useInPos,
                        setAsHouseAccount))
                .post("/api/v3/member/addcardonfilebymember")
                .then()
//				.log().all()
                .statusCode(400)
                .statusLine("HTTP/1.1 400 Bad Request");
    }

    @Test(testName = "Card Number Required", description = "PBI:150278")
    public void cardNumberRequired() {

        String cardNumber = "";

        given()
//				.log().all()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("X-Api-Key", aPIKey)
                .header("X-CompanyId", companyId)
                .header("X-ClubId", clubId)
                .when()
                .body(MemberPL.addCardOnFileByMemberCardNumberOnly(
                        updateActiveAgreements,
                        customerId,
                        cardNumber,
                        expirationMonth,
                        expirationYear,
                        cardHolderName,
                        addressIsSameAsMemberAddress,
                        addressLine1,
                        addressLine2,
                        city,
                        stateProvince,
                        postalCode,
                        useInPos,
                        setAsHouseAccount))
                .post("/api/v3/member/addcardonfilebymember")
                .then()
//				.log().all()
                .statusCode(400)
                .statusLine("HTTP/1.1 400 Bad Request")
                .body("Status", equalTo(400))
                .body("Message", equalTo("The CardNumber field is required."));
    }

    @Test(testName = "Expiration Month Required", description = "PBI:150278")
    public void expirationMonthRequired() {

        String expirationMonth = prop.getProperty("NOF");

        given()
//				.log().all()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("X-Api-Key", aPIKey)
                .header("X-CompanyId", companyId)
                .header("X-ClubId", clubId)
                .when()
                .body(MemberPL.addCardOnFileByMemberCardNumberOnly(
                        updateActiveAgreements,
                        customerId,
                        cardNumber,
                        expirationMonth,
                        expirationYear,
                        cardHolderName,
                        addressIsSameAsMemberAddress,
                        addressLine1,
                        addressLine2,
                        city,
                        stateProvince,
                        postalCode,
                        useInPos,
                        setAsHouseAccount))
                .post("/api/v3/member/addcardonfilebymember")
                .then()
//				.log().all()
                .statusCode(400)
                .statusLine("HTTP/1.1 400 Bad Request");
    }

    @Test(testName = "Expiration Year Required", description = "PBI:150278")
    public void expirationYearRequired() {

        String expirationYear = prop.getProperty("NOF");

        given()
//				.log().all()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("X-Api-Key", aPIKey)
                .header("X-CompanyId", companyId)
                .header("X-ClubId", clubId)
                .when()
                .body(MemberPL.addCardOnFileByMemberCardNumberOnly(
                        updateActiveAgreements,
                        customerId,
                        cardNumber,
                        expirationMonth,
                        expirationYear,
                        cardHolderName,
                        addressIsSameAsMemberAddress,
                        addressLine1,
                        addressLine2,
                        city,
                        stateProvince,
                        postalCode,
                        useInPos,
                        setAsHouseAccount))
                .post("/api/v3/member/addcardonfilebymember")
                .then()
//				.log().all()
                .statusCode(400)
                .statusLine("HTTP/1.1 400 Bad Request");
    }

    @Test(testName = "Cardholder Name Required", description = "PBI:150278")
    public void cardholderNameRequired() {

        String cardHolderName = "";

        given()
//				.log().all()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("X-Api-Key", aPIKey)
                .header("X-CompanyId", companyId)
                .header("X-ClubId", clubId)
                .when()
                .body(MemberPL.addCardOnFileByMemberCardNumberOnly(
                        updateActiveAgreements,
                        customerId,
                        cardNumber,
                        expirationMonth,
                        expirationYear,
                        cardHolderName,
                        addressIsSameAsMemberAddress,
                        addressLine1,
                        addressLine2,
                        city,
                        stateProvince,
                        postalCode,
                        useInPos,
                        setAsHouseAccount))
                .post("/api/v3/member/addcardonfilebymember")
                .then()
//				.log().all()
                .statusCode(400)
                .statusLine("HTTP/1.1 400 Bad Request")
                .body("Status", equalTo(400))
                .body("Message", equalTo("The CardHolderName field is required."));
    }

    @Test(testName = "Address Line1 Required", description = "PBI:150278")
    public void addressLine1Required() {

        String addressLine1 = "";

        given()
//				.log().all()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("X-Api-Key", aPIKey)
                .header("X-CompanyId", companyId)
                .header("X-ClubId", clubId)
                .when()
                .body(MemberPL.addCardOnFileByMemberCardNumberOnly(
                        updateActiveAgreements,
                        customerId,
                        cardNumber,
                        expirationMonth,
                        expirationYear,
                        cardHolderName,
                        addressIsSameAsMemberAddress,
                        addressLine1,
                        addressLine2,
                        city,
                        stateProvince,
                        postalCode,
                        useInPos,
                        setAsHouseAccount))
                .post("/api/v3/member/addcardonfilebymember")
                .then()
//				.log().all()
                .statusCode(400)
                .statusLine("HTTP/1.1 400 Bad Request")
                .body("Status", equalTo(400))
                .body("Message", equalTo("The AddressLine1 field is required."));
    }

    @Test(testName = "City Required", description = "PBI:150278")
    public void cityRequired() {

        String city = "";

        given()
//				.log().all()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("X-Api-Key", aPIKey)
                .header("X-CompanyId", companyId)
                .header("X-ClubId", clubId)
                .when()
                .body(MemberPL.addCardOnFileByMemberCardNumberOnly(
                        updateActiveAgreements,
                        customerId,
                        cardNumber,
                        expirationMonth,
                        expirationYear,
                        cardHolderName,
                        addressIsSameAsMemberAddress,
                        addressLine1,
                        addressLine2,
                        city,
                        stateProvince,
                        postalCode,
                        useInPos,
                        setAsHouseAccount))
                .post("/api/v3/member/addcardonfilebymember")
                .then()
//				.log().all()
                .statusCode(400)
                .statusLine("HTTP/1.1 400 Bad Request")
                .body("Status", equalTo(400))
                .body("Message", equalTo("The City field is required."));
    }

    @Test(testName = "StateProvince Required", description = "PBI:150278")
    public void stateProvinceRequired() {

        String stateProvince = "";//prop.getProperty("NOF");

        ReusableMethods.myWait(250); // waiting to avoid 429 rate limitation error when calls run too quickly

        given()
//				.log().all()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("X-Api-Key", aPIKey)
                .header("X-CompanyId", companyId)
                .header("X-ClubId", clubId)
                .when()
                .body(MemberPL.addCardOnFileByMemberCardNumberOnly(
                        updateActiveAgreements,
                        customerId,
                        cardNumber,
                        expirationMonth,
                        expirationYear,
                        cardHolderName,
                        addressIsSameAsMemberAddress,
                        addressLine1,
                        addressLine2,
                        city,
                        stateProvince,
                        postalCode,
                        useInPos,
                        setAsHouseAccount))
                .post("/api/v3/member/addcardonfilebymember")
                .then()
//				.log().all()
                .statusCode(400)
                .statusLine("HTTP/1.1 400 Bad Request")
                .body("Status", equalTo(400))
                .body("Message", equalTo("The StateProvince field is required."));
    }

    @Test(testName = "Postal Code Required", description = "PBI:150278")
    public void postalCodeRequired() {

        String postalCode = "";

        ReusableMethods.myWait(250); // waiting to avoid 429 rate limitation error when calls run too quickly

        given()
//				.log().all()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("X-Api-Key", aPIKey)
                .header("X-CompanyId", companyId)
                .header("X-ClubId", clubId)
                .when()
                .body(MemberPL.addCardOnFileByMemberCardNumberOnly(
                        updateActiveAgreements,
                        customerId,
                        cardNumber,
                        expirationMonth,
                        expirationYear,
                        cardHolderName,
                        addressIsSameAsMemberAddress,
                        addressLine1,
                        addressLine2,
                        city,
                        stateProvince,
                        postalCode,
                        useInPos,
                        setAsHouseAccount))
                .post("/api/v3/member/addcardonfilebymember")
                .then()
//				.log().all()
                .statusCode(400)
                .statusLine("HTTP/1.1 400 Bad Request")
                .body("Status", equalTo(400))
                .body("Message", equalTo("The PostalCode field is required."));
    }
}






















