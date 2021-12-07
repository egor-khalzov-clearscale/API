package unitTests;

import com.google.common.collect.ImmutableMap;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import resources.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import static resources.ReusableMethods.rawToJson;

public class GetFieldsForProspectCreationByEmployee extends base {

// !!! Assertions are split into 2 tests to resolve a compile error 'java.lang.StackOverflowError'.

    private void assertion(HashMap<String, Object> map, String data, Boolean required, Boolean valueMustBeInDefinedList, Integer length) {
        Assert.assertEquals(map.get("Description").toString(), data, "Description should be");
        Assert.assertEquals(map.get("DisplayValue").toString(), data, "DisplayValue should be");
        Assert.assertEquals(map.get("ValueMustBeInDefinedList").toString(),
                valueMustBeInDefinedList.toString());
        Assert.assertEquals(map.get("Required").toString(), required.toString(), "Should be required=" + required);
        Assert.assertEquals(map.get("MaximumLength").toString(), length.toString(), "Length should be " + length);
        Assert.assertEquals(map.get("DataType").toString(), "string");
    }

    private void assertionDate(HashMap<String, Object> map, String data, Boolean required, Boolean valueMustBeInDefinedList, Integer length) {
        Assert.assertEquals(map.get("Description").toString(), data, "Description should be");
        Assert.assertEquals(map.get("DisplayValue").toString(), data, "DisplayValue should be");
        Assert.assertEquals(map.get("ValueMustBeInDefinedList").toString(),
                valueMustBeInDefinedList.toString());
        Assert.assertEquals(map.get("Required").toString(), required.toString(), "Should be required=" + required);
        Assert.assertEquals(map.get("MaximumLength").toString(), length.toString(), "Length should be " + length);
        Assert.assertEquals(map.get("DataType").toString(), "datetime");
    }

    private void assertionCurr(HashMap<String, Object> map, String data, Boolean required, Boolean valueMustBeInDefinedList, Integer length) {
        Assert.assertEquals(map.get("Description").toString(), data, "Description should be");
        Assert.assertEquals(map.get("DisplayValue").toString(), data, "DisplayValue should be");
        Assert.assertEquals(map.get("ValueMustBeInDefinedList").toString(),
                valueMustBeInDefinedList.toString());
        Assert.assertEquals(map.get("Required").toString(), required.toString(), "Should be required=" + required);
        Assert.assertEquals(map.get("MaximumLength").toString(), length.toString(), "Length should be " + length);
        Assert.assertEquals(map.get("DataType").toString(), "currency");
    }

    private ImmutableMap<String, ? extends Serializable> valueMap(int i, String athletic) {
        return ImmutableMap.of("Id", Integer.valueOf(i), "Value", athletic);
    }

    @BeforeClass
    public void getData() {
        base.getPropertyData();
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = prop.getProperty("baseURI");
    }

    @Test(testName = "Fields Found", description = "PBI:147788")
    public void fieldsFound() {

        Response accept = given()
//						.log().all()
                .header("accept", "application/json")
                .header("X-Api-Key", prop.getProperty("X-Api-Key"))
                .header("X-CompanyId", prop.getProperty("X-CompanyId"))
                .header("X-ClubId", prop.getProperty("X-Club1Id"))
                .when()
                .get("/api/v3/prospect/getfieldsforprospectcreationbyemployee")
                .then()
//						.log().body()
                .assertThat().statusCode(200)
                .time(lessThan(60L), TimeUnit.SECONDS)
                .extract().response();
        var json = rawToJson(accept);

        for (Object linkmap : json.getList("Result")) {
            var map = (LinkedHashMap<String, Object>) linkmap;
            ArrayList<LinkedHashMap> values;
            switch (map.get("FieldName").toString()) {
                case "Title":
                    assertion(map, "Title", false, true, 10);
                    break;
                case "FirstName":
                    assertion(map, "First Name", true, false, 0);
                    break;
                case "MI":
                    assertion(map, "M.I.", false, false, 0);
                    break;
                case "LastName":
                    assertion(map, "Last Name", true, false, 0);
                    break;
                case "Preferred":
                    assertion(map, "Preferred Name", false, false, 0);
                    break;
                case "Address1":
                    assertion(map, "Address 1", false, false, 0);
                    break;
                case "Address2":
                    assertion(map, "Address 2", false, false, 0);
                    break;
                case "City":
                    assertion(map, "City", false, false, 0);
                    break;
                case "State":
                    assertion(map, "State", false, false, 0);
                    break;
                case "Zipcode":
                    assertion(map, "Zip Code", false, false, 0);
                    break;
                case "HomePhone":
                    assertion(map, "Home Phone", false, false, 0);
                    break;
                case "WorkPhone":
                    assertion(map, "Work Phone", false, false, 0);
                    break;
                case "CellPhone":
                    assertion(map, "Mobile Phone", true, false, 0);
                    break;
                case "Email":
                    assertion(map, "Email Address", true, false, 0);
                    break;
                case "SendEmailStatements":
                    assertion(map, "Pref Statement Format", false, false, 0);
                    break;
                case "EmergencyContact":
                    assertion(map, "Emergency Contact", false, false, 0);
                    break;
                case "EmergencyPhone":
                    assertion(map, "Emergency Phone", false, false, 0);
                    break;
                case "DateOfBirth":
                    assertion(map, "Birth Date", false, false, 0);
                    break;
                case "DriverLicNum":
                    assertion(map, "Driver's License Number", false, false, 0);
                    break;
                case "SSNum":
                    assertion(map, "Social Security Number", false, false, 0);
                    break;
                case "Gender":
                    assertion(map, "Gender", false, false, 0);
                    break;
                case "TrainerID":
                    assertion(map, "Trainer", false, false, 0);
                    break;
                case "Promotion":
                    assertion(map, "Promotion", false, false, 15);
                    break;
                case "SourceID":
                    assertion(map, "Source", false, true, 0);
                    values = ((ArrayList<LinkedHashMap>) ((LinkedHashMap) linkmap).get("Values"));
                    Assert.assertTrue(values.contains(valueMap(1, "Drive By")));
                    Assert.assertTrue(values.contains(valueMap(2, "Onsite Search")));
                    Assert.assertTrue(values.contains(valueMap(3, "Friend")));
                    Assert.assertTrue(values.contains(valueMap(4, "Newspaper")));
                    Assert.assertTrue(values.contains(valueMap(5, "Social Media")));
                    Assert.assertTrue(values.contains(valueMap(6, "Community Event")));
                    Assert.assertTrue(values.contains(valueMap(7, "Other")));
                    break;
                case "Occupation":
                    Assert.assertEquals(map.get("Description").toString(), "Occupation", "Description should be");
                    Assert.assertEquals(map.get("DisplayValue").toString(), "Occupation of Member", "DisplayValue should be");
                    Assert.assertEquals(map.get("ValueMustBeInDefinedList").toString(),
                            "false");
                    Assert.assertEquals(map.get("Required").toString(), "false", "Should be required=false");
                    Assert.assertEquals(map.get("MaximumLength").toString(), 30 + "", "Length should be " + 30);
                    Assert.assertEquals(map.get("DataType").toString(), "string");
                    break;
                case "Employer":
                    assertion(map, "Employer", false, false, 0);
                    break;
                case "IncomeChoice":
                    assertion(map, "Income", false, true, 0);
                    values = ((ArrayList<LinkedHashMap>) ((LinkedHashMap) linkmap).get("Values"));
                    Assert.assertTrue(values.contains(valueMap(1, "")));
                    Assert.assertTrue(values.contains(valueMap(2, "")));
                    Assert.assertTrue(values.contains(valueMap(3, "")));
                    Assert.assertTrue(values.contains(valueMap(4, "")));
                    break;
                case "Login":
                    assertion(map, "Online Log In", false, false, 0);
                    break;
                case "GroupID":
                    assertion(map, "Group Name", false, true, 0);
                    values = ((ArrayList<LinkedHashMap>) ((LinkedHashMap) linkmap).get("Values"));
                    Assert.assertTrue(values.contains(valueMap(1, "Silver Sneakers")));
                    Assert.assertTrue(values.contains(valueMap(2, "Boeing")));
                    Assert.assertTrue(values.contains(valueMap(3, "Microsoft")));
                    Assert.assertTrue(values.contains(valueMap(4, "Jeep")));
                    Assert.assertTrue(values.contains(valueMap(5, "Safeway")));
                    Assert.assertTrue(values.contains(valueMap(6, "Dodge")));
                    break;
                case "InsuranceID":
                    assertion(map, "Insurance ID", false, false, 0);
                    break;
                case "InsuranceRegistrationDate":
                    assertion(map, "Insurance Registration", false, false, 0);
                    break;
            }
        }
    }

    @Test(testName = "Fields Found - Part 2", description = "PBI:147788")
    public void fieldsFound2() {

		Response accept = given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key", prop.getProperty("X-Api-Key"))
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", prop.getProperty("X-Club1Id"))
				.when()
				.get("/api/v3/prospect/getfieldsforprospectcreationbyemployee")
				.then()
//						.log().body()
				.assertThat().statusCode(200).extract().response();
		var json = rawToJson(accept);

        for (Object linkmap : json.getList("Result")) {
            var map = (LinkedHashMap<String, Object>) linkmap;
            ArrayList<LinkedHashMap> values;
            switch (map.get("FieldName").toString()) {
                case "UserEntry1":
                    assertion(map, "Custom Text 1", false, true, 40);
                    break;
                case "UserEntry2":
                    assertion(map, "Custom Text 2", false, false, 40);
                    break;
                case "UserEntry3":
                    assertion(map, "Custom Text 3", false, false, 40);
                    break;
                case "UserEntry4":
                    assertion(map, "Custom Text 4", false, false, 40);
                    break;
                case "UserEntry5":
                    assertion(map, "Custom Text 5", false, false, 40);
                    break;
                case "UserEntry6":
                    assertion(map, "Custom Text 6", false, false, 40);
                    break;
                case "UserEntry7":
                    assertion(map, "Custom Text 7", false, false, 40);
                    break;
                case "UserEntry8":
                    assertion(map, "Custom Text 8", false, false, 40);
                    break;
                case "UserEntry9":
                    assertion(map, "Custom Text 9", false, false, 40);
                    break;
                case "UserEntry10":
                    assertion(map, "Custom Text 10", false, false, 40);
                    break;
                case "UserEntry11":
                    assertion(map, "Custom Text 11", false, false, 40);
                    break;
                case "UserEntry12":
                    assertion(map, "Custom Text 12", false, false, 40);
                    break;
                case "UserEntry13":
                    assertion(map, "Custom Text 13", false, false, 40);
                    break;
                case "UserEntry14":
                    assertion(map, "Custom Text 14", false, false, 40);
                    break;
                case "UserEntry15":
                    assertion(map, "Custom Text 15", false, false, 40);
                    break;
                case "UserEntry16":
                    assertion(map, "Conv Membership Type", false, false, 40);
                    break;
                case "UserDate1":
                    assertionDate(map, "Custom Date 1", false, false, 10);
                    break;
                case "UserDate2":
                    assertionDate(map, "Custom Date 2", false, false, 10);
                    break;
                case "UserDate3":
                    assertionDate(map, "Custom Date 3", false, false, 10);
                    break;
                case "UserDate4":
                    assertionDate(map, "Custom Date 4", false, false, 10);
                    break;
                case "UserAmount1":
                    assertionCurr(map, "Custom Amount 1", false, false, 10);
                    break;
                case "UserAmount2":
                    assertionCurr(map, "Custom Amount 2", false, false, 10);
                    break;
                case "UserAmount3":
                    assertionCurr(map, "Custom Amount 3", false, false, 10);
                    break;
                case "UserAmount4":
                    assertionCurr(map, "Custom Amount 4", false, false, 10);
                    break;
                case "CategoryID":
                    assertion(map, "Membership Type", true, true, 0);
                    values = ((ArrayList<LinkedHashMap>) ((LinkedHashMap) linkmap).get("Values"));
                    Assert.assertTrue(values.contains(valueMap(2, "Prospect")));
                    break;
                case "PreferredPhone":
                    assertion(map, "Preferred Phone Number", false, false, 0);
                    break;
                case "CreditLimit":
                    assertion(map, "Credit Limit Value", false, false, 0);
                    break;
                case "ReferringCustomerID":
                    assertion(map, "Referred By", false, false, 0);
                    break;
                case "Date":
                    assertion(map, "Referral Date", false, false, 0);
                    break;
                case "InterestID":
                    assertion(map, "Interests", false, true, 0);
                    values = ((ArrayList<LinkedHashMap>) ((LinkedHashMap) linkmap).get("Values"));
                    Assert.assertTrue(values.contains(valueMap(1, "Build Muscle/Strength")));
                    Assert.assertTrue(values.contains(valueMap(5, "Group Fitness")));
                    Assert.assertTrue(values.contains(valueMap(4, "Maintain Health")));
                    Assert.assertTrue(values.contains(valueMap(2, "Sports Performance")));
                    Assert.assertTrue(values.contains(valueMap(3, "Weight Loss")));
                    break;
                case "GeneralNotes":
                    assertion(map, "Notes", false, false, 0);
                    break;
                case "ClubNumber":
                    assertion(map, "Home Club", false, true, 0);
                    values = ((ArrayList<LinkedHashMap>) ((LinkedHashMap) linkmap).get("Values"));
                    Assert.assertTrue(values.contains(valueMap(1, "Jonas Sports-Plex")));
                    Assert.assertTrue(values.contains(valueMap(2, "Studio Jonas")));
                    Assert.assertTrue(values.contains(valueMap(3, "Jonas Health and Wellness")));
                    Assert.assertTrue(values.contains(valueMap(4, "Jonas Fitness")));
                    break;
                case "StageId":
                    assertion(map, "Stage", false, false, 0);
                    break;
                case "Priority":
                    assertion(map, "Priority", false, true, 0);
                    values = ((ArrayList<LinkedHashMap>) ((LinkedHashMap) linkmap).get("Values"));
                    Assert.assertTrue(values.contains(valueMap(1, "Priority 0")));
                    break;
                case "SalespersonID":
                    assertion(map, "Salesperson", false, false, 0);
                    break;
                case "MedicalNote":
                    assertion(map, "Medical Note", false, true, 60);
                    break;
                case "BarcodeID":
                    assertion(map, "Member Id", true, false, 0);
                    break;
            }
        }
    }
}
