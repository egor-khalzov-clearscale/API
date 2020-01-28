package Utilities;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import resources.base;

public class EnrollMemberInCourseOnAccount extends base {
	
	@BeforeTest
	public void getData() {
		base.getPropertyData();
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = prop.getProperty("baseURI");
	}
	

	@Test (testName="Member Enrolled",description="PBI:143589")
	public void memberEnrolled() {
		
				int customerId = 248;
				String courseBarcodeId = "alwaysAvailCo";
				String displayedCoursePrice = "100.00";
				String enrollCustomerAsStandby = "true";

				given()
//						.log().all()
				.header("accept", prop.getProperty("accept"))
				.header("X-Api-Key", prop.getProperty("X-Api-Key"))
				.header("X-CompanyId", prop.getProperty("X-CompanyId"))
				.header("X-ClubId", prop.getProperty("X-Club1Id"))
					.when()
					.get("/api/v3/classcourse/enrollmemberincourseonaccount/"+customerId+"/"+courseBarcodeId+"/"+displayedCoursePrice+"/"+enrollCustomerAsStandby)
						.then()
						.log().body();
	}
	
}
