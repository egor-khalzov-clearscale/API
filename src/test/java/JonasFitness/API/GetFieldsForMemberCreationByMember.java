package JonasFitness.API;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.lessThan;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.restassured.RestAssured;
import resources.base;

public class GetFieldsForMemberCreationByMember extends base {
	
// !!! Assertions are split into 2 tests to resolve a compile error 'java.lang.StackOverflowError'.

	@BeforeTest
	public void getData() throws IOException {
		base.getPropertyData();
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = prop.getProperty("baseURI");
	}
	
	@Test (testName="Fields Found",description="PBI:147804")
	public void fieldsFound() { 
		
/*
 * this call uses the fields configured for JOL
 */

				given()
//						.log().all()
				.header("accept", prop.getProperty("accept"))
				.header("X-Api-Key", prop.getProperty("X-Api-Key"))
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", prop.getProperty("X-Club1Id"))
					.when()
						.get("/api/v3/member/getfieldsformembercreationbymember")
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.time(lessThan(5L),TimeUnit.SECONDS)
						
						.body("Result[0].FieldName", equalTo("Title"))
						.body("Result[0].Description", equalTo("Title"))
						.body("Result[0].DisplayValue", equalTo("Title"))
						.body("Result[0].Required", equalTo(false))
						.body("Result[0].ValueMustBeInDefinedList", equalTo(true))
						.body("Result[0]", hasKey("Values"))
						.body("Result[0].MaximumLength", equalTo(10))
						.body("Result[0].DataType", equalTo("string"))

						.body("Result[1].FieldName", equalTo("FirstName"))
						.body("Result[1].Description", equalTo("First Name"))
						.body("Result[1].DisplayValue", equalTo("First Name"))
						.body("Result[1].Required", equalTo(true))
						.body("Result[1].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[1]", hasKey("Values"))
						.body("Result[1].MaximumLength", equalTo(0))
						.body("Result[1].DataType", equalTo("string"))
						
						.body("Result[2].FieldName", equalTo("MI"))
						.body("Result[2].Description", equalTo("M.I."))
						.body("Result[2].DisplayValue", equalTo("M.I."))
						.body("Result[2].Required", equalTo(false))
						.body("Result[2].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[2]", hasKey("Values"))
						.body("Result[2].MaximumLength", equalTo(0))
						.body("Result[2].DataType", equalTo("string"))
						
						.body("Result[3].FieldName", equalTo("LastName"))
						.body("Result[3].Description", equalTo("Last Name"))
						.body("Result[3].DisplayValue", equalTo("Last Name"))
						.body("Result[3].Required", equalTo(true))
						.body("Result[3].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[3]", hasKey("Values"))
						.body("Result[3].MaximumLength", equalTo(0))
						.body("Result[3].DataType", equalTo("string"))
								
						.body("Result[4].FieldName", equalTo("Address1"))
						.body("Result[4].Description", equalTo("Address 1"))
						.body("Result[4].DisplayValue", equalTo("Address 1"))
						.body("Result[4].Required", equalTo(true))
						.body("Result[4].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[4]", hasKey("Values"))
						.body("Result[4].MaximumLength", equalTo(0))
						.body("Result[4].DataType", equalTo("string"))
						
						.body("Result[5].FieldName", equalTo("Address2"))
						.body("Result[5].Description", equalTo("Address 2"))
						.body("Result[5].DisplayValue", equalTo("Address 2"))
						.body("Result[5].Required", equalTo(false))
						.body("Result[5].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[5]", hasKey("Values"))
						.body("Result[5].MaximumLength", equalTo(0))
						.body("Result[5].DataType", equalTo("string"))
						
						.body("Result[6].FieldName", equalTo("City"))
						.body("Result[6].Description", equalTo("City"))
						.body("Result[6].DisplayValue", equalTo("City"))
						.body("Result[6].Required", equalTo(true))
						.body("Result[6].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[6]", hasKey("Values"))
						.body("Result[6].MaximumLength", equalTo(0))
						.body("Result[6].DataType", equalTo("string"))
						
						.body("Result[7].FieldName", equalTo("State"))
						.body("Result[7].Description", equalTo("State"))
						.body("Result[7].DisplayValue", equalTo("State"))
						.body("Result[7].Required", equalTo(true))
						.body("Result[7].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[7]", hasKey("Values"))
						.body("Result[7].MaximumLength", equalTo(0))
						.body("Result[7].DataType", equalTo("string"))
						
						.body("Result[8].FieldName", equalTo("Zipcode"))
						.body("Result[8].Description", equalTo("Zip Code"))
						.body("Result[8].DisplayValue", equalTo("Zip Code"))
						.body("Result[8].Required", equalTo(true))
						.body("Result[8].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[8]", hasKey("Values"))
						.body("Result[8].MaximumLength", equalTo(0))
						.body("Result[8].DataType", equalTo("string"))
						
						.body("Result[9].FieldName", equalTo("HomePhone"))
						.body("Result[9].Description", equalTo("Home Phone"))
						.body("Result[9].DisplayValue", equalTo("Home Phone"))
						.body("Result[9].Required", equalTo(false))
						.body("Result[9].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[9]", hasKey("Values"))
						.body("Result[9].MaximumLength", equalTo(0))
						.body("Result[9].DataType", equalTo("string"))

						.body("Result[10].FieldName", equalTo("WorkPhone"))
						.body("Result[10].Description", equalTo("Work Phone"))
						.body("Result[10].DisplayValue", equalTo("Work Phone"))
						.body("Result[10].Required", equalTo(false))
						.body("Result[10].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[10]", hasKey("Values"))
						.body("Result[10].MaximumLength", equalTo(0))
						.body("Result[10].DataType", equalTo("string"))
						
						.body("Result[11].FieldName", equalTo("CellPhone"))
						.body("Result[11].Description", equalTo("Mobile Phone"))
						.body("Result[11].DisplayValue", equalTo("Mobile Phone"))
						.body("Result[11].Required", equalTo(true))
						.body("Result[11].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[11]", hasKey("Values"))
						.body("Result[11].MaximumLength", equalTo(0))
						.body("Result[11].DataType", equalTo("string"))
						
						.body("Result[12].FieldName", equalTo("Email"))
						.body("Result[12].Description", equalTo("Email Address"))
						.body("Result[12].DisplayValue", equalTo("Email Address"))
						.body("Result[12].Required", equalTo(true))
						.body("Result[12].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[12]", hasKey("Values"))
						.body("Result[12].MaximumLength", equalTo(0))
						.body("Result[12].DataType", equalTo("string"))
						
						.body("Result[13].FieldName", equalTo("SendEmailStatements"))
						.body("Result[13].Description", equalTo("Pref Statement Format"))
						.body("Result[13].DisplayValue", equalTo("Pref Statement Format"))
						.body("Result[13].Required", equalTo(false))
						.body("Result[13].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[13]", hasKey("Values"))
						.body("Result[13].MaximumLength", equalTo(0))
						.body("Result[13].DataType", equalTo("string"))
						
						.body("Result[14].FieldName", equalTo("EmergencyContact"))
						.body("Result[14].Description", equalTo("Emergency Contact"))
						.body("Result[14].DisplayValue", equalTo("Emergency Contact"))
						.body("Result[14].Required", equalTo(false))
						.body("Result[14].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[14]", hasKey("Values"))
						.body("Result[14].MaximumLength", equalTo(0))
						.body("Result[14].DataType", equalTo("string"))
						
						.body("Result[15].FieldName", equalTo("EmergencyPhone"))
						.body("Result[15].Description", equalTo("Emergency Phone"))
						.body("Result[15].DisplayValue", equalTo("Emergency Phone"))
						.body("Result[15].Required", equalTo(false))
						.body("Result[15].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[15]", hasKey("Values"))
						.body("Result[15].MaximumLength", equalTo(0))
						.body("Result[15].DataType", equalTo("string"))
						
						.body("Result[16].FieldName", equalTo("DateOfBirth"))
						.body("Result[16].Description", equalTo("Birth Date"))
						.body("Result[16].DisplayValue", equalTo("Birth Date"))
						.body("Result[16].Required", equalTo(true))
						.body("Result[16].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[16]", hasKey("Values"))
						.body("Result[16].MaximumLength", equalTo(0))
						.body("Result[16].DataType", equalTo("string"))
						
						.body("Result[17].FieldName", equalTo("DriverLicNum"))
						.body("Result[17].Description", equalTo("Driver's License Number"))
						.body("Result[17].DisplayValue", equalTo("Driver's License Number"))
						.body("Result[17].Required", equalTo(false))
						.body("Result[17].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[17]", hasKey("Values"))
						.body("Result[17].MaximumLength", equalTo(0))
						.body("Result[17].DataType", equalTo("string"))
						
						.body("Result[18].FieldName", equalTo("SSNum"))
						.body("Result[18].Description", equalTo("Social Security Number"))
						.body("Result[18].DisplayValue", equalTo("Social Security Number"))
						.body("Result[18].Required", equalTo(false))
						.body("Result[18].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[18]", hasKey("Values"))
						.body("Result[18].MaximumLength", equalTo(0))
						.body("Result[18].DataType", equalTo("string"))
						
						.body("Result[19].FieldName", equalTo("Gender"))
						.body("Result[19].Description", equalTo("Gender"))
						.body("Result[19].DisplayValue", equalTo("Gender"))
						.body("Result[19].Required", equalTo(false))
						.body("Result[19].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[19]", hasKey("Values"))
						.body("Result[19].MaximumLength", equalTo(0))
						.body("Result[19].DataType", equalTo("string"))
						
						.body("Result[20].FieldName", equalTo("Promotion"))
						.body("Result[20].Description", equalTo("Promotion"))
						.body("Result[20].DisplayValue", equalTo("Promotion"))
						.body("Result[20].Required", equalTo(false))
						.body("Result[20].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[20]", hasKey("Values"))
						.body("Result[20].MaximumLength", equalTo(15))
						.body("Result[20].DataType", equalTo("string"))
						
						.body("Result[21].FieldName", equalTo("SourceID"))
						.body("Result[21].Description", equalTo("Source"))
						.body("Result[21].DisplayValue", equalTo("Source"))
						.body("Result[21].Required", equalTo(false))
						.body("Result[21].ValueMustBeInDefinedList", equalTo(true))
						.body("Result[21].Values[0].Id", equalTo(1))
						.body("Result[21].Values[0].Value", equalTo("Drive By"))
						.body("Result[21].Values[1].Id", equalTo(2))
						.body("Result[21].Values[1].Value", equalTo("Onsite Search"))
						.body("Result[21].Values[2].Id", equalTo(3))
						.body("Result[21].Values[2].Value", equalTo("Friend"))
						.body("Result[21].Values[3].Id", equalTo(4))
						.body("Result[21].Values[3].Value", equalTo("Newspaper"))
						.body("Result[21].Values[4].Id", equalTo(5))
						.body("Result[21].Values[4].Value", equalTo("Social Media"))
						.body("Result[21].Values[5].Id", equalTo(6))
						.body("Result[21].Values[5].Value", equalTo("Community Event"))
						.body("Result[21].Values[6].Id", equalTo(7))
						.body("Result[21].Values[6].Value", equalTo("Other"))
						.body("Result[21].MaximumLength", equalTo(0))
						.body("Result[21].DataType", equalTo("string"));
	}
	@Test (testName="Fields Found - Part 2",description="PBI:147804")
	public void fieldsFound2() { 

				given()
//						.log().all()
				.header("accept", prop.getProperty("accept"))
				.header("X-Api-Key", prop.getProperty("X-Api-Key"))
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", prop.getProperty("X-Club1Id"))
					.when()
						.get("/api/v3/member/getfieldsformembercreationbymember")
						.then()
						.assertThat().statusCode(200)
						
						.body("Result[22].FieldName", equalTo("UserEntry1"))
						.body("Result[22].Description", equalTo("Custom Text 1"))
						.body("Result[22].DisplayValue", equalTo("Custom Text 1"))
						.body("Result[22].Required", equalTo(false))
						.body("Result[22].ValueMustBeInDefinedList", equalTo(true))
						.body("Result[22]", hasKey("Values"))
						.body("Result[22].MaximumLength", equalTo(40))
						.body("Result[22].DataType", equalTo("string"))
						
						.body("Result[23].FieldName", equalTo("UserEntry2"))
						.body("Result[23].Description", equalTo("Custom Text 2"))
						.body("Result[23].DisplayValue", equalTo("Custom Text 2"))
						.body("Result[23].Required", equalTo(false))
						.body("Result[23].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[23]", hasKey("Values"))
						.body("Result[23].MaximumLength", equalTo(40))
						.body("Result[23].DataType", equalTo("string"))
						
						.body("Result[24].FieldName", equalTo("UserEntry3"))
						.body("Result[24].Description", equalTo("Custom Text 3"))
						.body("Result[24].DisplayValue", equalTo("Custom Text 3"))
						.body("Result[24].Required", equalTo(false))
						.body("Result[24].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[24]", hasKey("Values"))
						.body("Result[24].MaximumLength", equalTo(40))
						.body("Result[24].DataType", equalTo("string"))
						
						.body("Result[25].FieldName", equalTo("UserEntry4"))
						.body("Result[25].Description", equalTo("Custom Text 4"))
						.body("Result[25].DisplayValue", equalTo("Custom Text 4"))
						.body("Result[25].Required", equalTo(false))
						.body("Result[25].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[25]", hasKey("Values"))
						.body("Result[25].MaximumLength", equalTo(40))
						.body("Result[25].DataType", equalTo("string"))
						
						.body("Result[26].FieldName", equalTo("UserEntry5"))
						.body("Result[26].Description", equalTo("Custom Text 5"))
						.body("Result[26].DisplayValue", equalTo("Custom Text 5"))
						.body("Result[26].Required", equalTo(false))
						.body("Result[26].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[26]", hasKey("Values"))
						.body("Result[26].MaximumLength", equalTo(40))
						.body("Result[26].DataType", equalTo("string"))
						
						.body("Result[27].FieldName", equalTo("UserEntry6"))
						.body("Result[27].Description", equalTo("Custom Text 6"))
						.body("Result[27].DisplayValue", equalTo("Custom Text 6"))
						.body("Result[27].Required", equalTo(false))
						.body("Result[27].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[27]", hasKey("Values"))
						.body("Result[27].MaximumLength", equalTo(40))
						.body("Result[27].DataType", equalTo("string"))
						
						.body("Result[28].FieldName", equalTo("UserEntry7"))
						.body("Result[28].Description", equalTo("Custom Text 7"))
						.body("Result[28].DisplayValue", equalTo("Custom Text 7"))
						.body("Result[28].Required", equalTo(false))
						.body("Result[28].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[28]", hasKey("Values"))
						.body("Result[28].MaximumLength", equalTo(40))
						.body("Result[28].DataType", equalTo("string"))
						
						.body("Result[29].FieldName", equalTo("UserEntry8"))
						.body("Result[29].Description", equalTo("Custom Text 8"))
						.body("Result[29].DisplayValue", equalTo("Custom Text 8"))
						.body("Result[29].Required", equalTo(false))
						.body("Result[29].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[29]", hasKey("Values"))
						.body("Result[29].MaximumLength", equalTo(40))
						.body("Result[29].DataType", equalTo("string"))
						
						.body("Result[30].FieldName", equalTo("UserEntry9"))
						.body("Result[30].Description", equalTo("Custom Text 9"))
						.body("Result[30].DisplayValue", equalTo("Custom Text 9"))
						.body("Result[30].Required", equalTo(false))
						.body("Result[30].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[30]", hasKey("Values"))
						.body("Result[30].MaximumLength", equalTo(40))
						.body("Result[30].DataType", equalTo("string"))
						
						.body("Result[31].FieldName", equalTo("UserEntry10"))
						.body("Result[31].Description", equalTo("Custom Text 10"))
						.body("Result[31].DisplayValue", equalTo("Custom Text 10"))
						.body("Result[31].Required", equalTo(false))
						.body("Result[31].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[31]", hasKey("Values"))
						.body("Result[31].MaximumLength", equalTo(40))
						.body("Result[31].DataType", equalTo("string"))

						.body("Result[32].FieldName", equalTo("UserEntry11"))
						.body("Result[32].Description", equalTo("Custom Text 11"))
						.body("Result[32].DisplayValue", equalTo("Custom Text 11"))
						.body("Result[32].Required", equalTo(false))
						.body("Result[32].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[32]", hasKey("Values"))
						.body("Result[32].MaximumLength", equalTo(40))
						.body("Result[32].DataType", equalTo("string"))
						
						.body("Result[33].FieldName", equalTo("UserEntry12"))
						.body("Result[33].Description", equalTo("Custom Text 12"))
						.body("Result[33].DisplayValue", equalTo("Custom Text 12"))
						.body("Result[33].Required", equalTo(false))
						.body("Result[33].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[33]", hasKey("Values"))
						.body("Result[33].MaximumLength", equalTo(40))
						.body("Result[33].DataType", equalTo("string"))
						
						.body("Result[34].FieldName", equalTo("UserEntry13"))
						.body("Result[34].Description", equalTo("Custom Text 13"))
						.body("Result[34].DisplayValue", equalTo("Custom Text 13"))
						.body("Result[34].Required", equalTo(false))
						.body("Result[34].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[34]", hasKey("Values"))
						.body("Result[34].MaximumLength", equalTo(40))
						.body("Result[34].DataType", equalTo("string"))
						
						.body("Result[35].FieldName", equalTo("UserEntry14"))
						.body("Result[35].Description", equalTo("Custom Text 14"))
						.body("Result[35].DisplayValue", equalTo("Custom Text 14"))
						.body("Result[35].Required", equalTo(false))
						.body("Result[35].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[35]", hasKey("Values"))
						.body("Result[35].MaximumLength", equalTo(40))
						.body("Result[35].DataType", equalTo("string"))
						
						.body("Result[36].FieldName", equalTo("UserEntry15"))
						.body("Result[36].Description", equalTo("Custom Text 15"))
						.body("Result[36].DisplayValue", equalTo("Custom Text 15"))
						.body("Result[36].Required", equalTo(false))
						.body("Result[36].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[36]", hasKey("Values"))
						.body("Result[36].MaximumLength", equalTo(40))
						.body("Result[36].DataType", equalTo("string"))
						
						.body("Result[37].FieldName", equalTo("UserEntry16"))
						.body("Result[37].Description", equalTo("Conv Membership Type"))
						.body("Result[37].DisplayValue", equalTo("Conv Membership Type"))
						.body("Result[37].Required", equalTo(false))
						.body("Result[37].ValueMustBeInDefinedList", equalTo(false))
						.body("Result[37]", hasKey("Values"))
						.body("Result[37].MaximumLength", equalTo(40))
						.body("Result[37].DataType", equalTo("string")); 
	}
}
