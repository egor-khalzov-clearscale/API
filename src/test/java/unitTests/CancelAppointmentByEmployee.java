package unitTests;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import java.util.concurrent.TimeUnit;

import io.restassured.RestAssured;
import resources.base;

public class CancelAppointmentByEmployee extends base {
	
	/* ** Additional test cases **
	 * 1. Cancel fees exist - currently fee not in response,
	 *  need a way to validate fee has been charged;
	 *  could wait for a call that has members balance,
	 *  then check balance before and after fee is charged
	*/
	/*
	 * See BookAppointmentByEmployee test case for the test that cancels an appointment by employee.
	*/
	

	static String aPIKey;
	static String companyId;
	static String clubId;
	
	@BeforeClass
	public void getData() {
		base.getPropertyData();
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = prop.getProperty("baseURI");
		
		aPIKey = prop.getProperty("X-Api-Key");
		companyId = prop.getProperty("X-CompanyId");
		clubId = prop.getProperty("X-Club1Id");
	}
	
	@Test (testName="ApptNotFound",description="PBI:141862")
	public void ApptNotFound() { 

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/appointment/cancelappointmentbyemployee/916375")
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Message", equalTo("Nothing found"));
	}
	
	@Test (testName="NotCancelled_ApptDatePast",description="PBI:141862")
	public void NotCancelled_ApptDatePast() { 

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/appointment/cancelappointmentbyemployee/10")
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Status", equalTo("Failed"))
						.body("Result.ConfirmationCode", nullValue())
						.body("Result", hasKey("Reason"))
						.body("Result.Reason", equalTo("TimeRestriction"));
	}
	
	@Test (testName="NotCancelled_ApptDatePast",description="PBI:141862")
	public void ApptAlreadyCancelled() { 

				given()
//						.log().all()
				.header("accept", "application/json")
				.header("X-Api-Key",aPIKey)
				.header("X-CompanyId", companyId)
				.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/appointment/cancelappointmentbyemployee/24932")
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Status", equalTo("Failed"))
						.body("Result.ConfirmationCode", nullValue())
						.body("Result", hasKey("Reason"))
						.body("Result.Reason", equalTo("AppointmentCancelled"));
	}
	
	
}
