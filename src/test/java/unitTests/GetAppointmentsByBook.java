package unitTests;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.lessThan;
import java.util.concurrent.TimeUnit;
import io.restassured.RestAssured;
import resources.ReusableDates;
import resources.base;

public class GetAppointmentsByBook extends base{
	
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
	
	@Test (testName="Appointments Found",description="PBI:132256")
	public void AppointmentsFound() {

		String resourceTypeId = prop.getProperty("demoBookId");
			int r = Integer.parseInt(resourceTypeId);
		String sDateTimeNoOffset = "2021-12-01";
		String eDateTimeNoOffset = "2025-01-01";

				given()
//						.log().all()
						.header("accept", "application/json")
						.header("X-Api-Key",aPIKey)
						.header("X-CompanyId", companyId)
						.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/appointment/getappointmentsbybook/"+resourceTypeId+"/"+sDateTimeNoOffset+"/"+eDateTimeNoOffset)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Result[0]", hasKey("AppointmentId"))
						.body("Result[0]", hasKey("AppointmentNotes"))
						.body("Result[0]", hasKey("CancellationDateTime"))
						.body("Result[0]", hasKey("ClubId"))
						.body("Result[0]", hasKey("ClubName"))
						.body("Result[0]", hasKey("DurationInMinutes"))
						.body("Result[0]", hasKey("ItemBarCodeId"))
						.body("Result[0]", hasKey("ItemDescription"))
						.body("Result[0]", hasKey("ItemLongDescription"))
						.body("Result[0]", hasKey("ProductCategoryDescription"))
						.body("Result[0]", hasKey("RecurringId"))
						.body("Result[0].ScheduledAppointmentBookDTOs[0]", hasKey("BookDescription"))
						.body("Result[0].ScheduledAppointmentBookDTOs[0].BookId", equalTo(r))
						.body("Result[0].ScheduledAppointmentBookDTOs[0].BookName", equalTo(prop.getProperty("demoBookName")))
						.body("Result[0].ScheduledAppointmentBookDTOs[0]", hasKey("ResourceTypeDescription"))
						.body("Result[0].ScheduledAppointmentBookDTOs[0]", hasKey("ResourceTypeId"))
						.body("Result[0].ScheduledAppointmentBookDTOs[0]", hasKey("ResourceTypeName"))
						.body("Result[0].ScheduledAppointmentMemberDTOs[0]", hasKey("BarcodeId"))
						.body("Result[0].ScheduledAppointmentMemberDTOs[0]", hasKey("CustomerId"))
						.body("Result[0].ScheduledAppointmentMemberDTOs[0]", hasKey("DisplayName"))
						.body("Result[0].ScheduledAppointmentMemberDTOs[0]", hasKey("Outcome"))
						.body("Result[0]", hasKey("ScheduledDateTime"))
						.body("Result[0]", hasKey("ScheduledInstanceType"))
						.body("Result[0]", hasKey("StartDateTime"));
	}
	
	@Test (testName="Past Appointments Found",description="PBI:132256")
	public void pastAppointmentsFound() {

				String resourceTypeId = prop.getProperty("pTBook3Id");
				String sDateTimeNoOffset = "2000-12-01";
				String eDateTimeNoOffset = "2021-01-01";
				

				given()
//						.log().all()
						.header("accept", "application/json")
						.header("X-Api-Key",aPIKey)
						.header("X-CompanyId", companyId)
						.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/appointment/getappointmentsbybook/"+resourceTypeId+"/"+sDateTimeNoOffset+"/"+eDateTimeNoOffset)
						.then()
//						.log().body()
						.assertThat().statusCode(200)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Result[0]", hasKey("AppointmentId"))
						.body("Result[0]", hasKey("AppointmentNotes"))
						.body("Result[0]", hasKey("CancellationDateTime"))
						.body("Result[0]", hasKey("ClubId"))
						.body("Result[0]", hasKey("ClubName"))
						.body("Result[0]", hasKey("DurationInMinutes"))
						.body("Result[0]", hasKey("ItemBarCodeId"))
						.body("Result[0]", hasKey("ItemDescription"))
						.body("Result[0]", hasKey("ItemLongDescription"))
						.body("Result[0]", hasKey("ProductCategoryDescription"))
						.body("Result[0]", hasKey("RecurringId"))
						.body("Result[0].ScheduledAppointmentBookDTOs[0]", hasKey("BookDescription"))
						.body("Result[0].ScheduledAppointmentBookDTOs[0]", hasKey("BookId"))
						.body("Result[0].ScheduledAppointmentBookDTOs[0]", hasKey("BookName"))
						.body("Result[0].ScheduledAppointmentBookDTOs[0]", hasKey("ResourceTypeDescription"))
						.body("Result[0].ScheduledAppointmentBookDTOs[0]", hasKey("ResourceTypeId"))
						.body("Result[0].ScheduledAppointmentBookDTOs[0]", hasKey("ResourceTypeName"))
						.body("Result[0].ScheduledAppointmentMemberDTOs[0]", hasKey("BarcodeId"))
						.body("Result[0].ScheduledAppointmentMemberDTOs[0]", hasKey("CustomerId"))
						.body("Result[0].ScheduledAppointmentMemberDTOs[0]", hasKey("DisplayName"))
						.body("Result[0].ScheduledAppointmentMemberDTOs[0]", hasKey("Outcome"))
						.body("Result[0]", hasKey("ScheduledDateTime"))
						.body("Result[0]", hasKey("ScheduledInstanceType"))
						.body("Result[0]", hasKey("StartDateTime"));
	}
	
	@Test (testName="Appointments Not Found",description="PBI:132256")
	public void appointmentsNotFound() {
		
		String resourceId = prop.getProperty("demoBookId");
		String sDateTimeNoOffset = "2125-11-13T00:00";
		String eDateTimeNoOffset = "2125-11-14T00:00";

				given()
//						.log().all()
						.header("accept", "application/json")
						.header("X-Api-Key",aPIKey)
						.header("X-CompanyId", companyId)
						.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/appointment/getappointmentsbybook/"+resourceId+"/"+sDateTimeNoOffset+"/"+eDateTimeNoOffset)
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Message", equalTo("Nothing found"));
	}
	
	@Test (testName="Invalid Date Range",description="PBI:132256")
	public void invalidDateRange() {
		
		String resourceId = prop.getProperty("demoBookId");
		String sDateTimeNoOffset = "2025-11-13T00:02";
		String eDateTimeNoOffset = "2025-11-13T00:01";

				given()
//						.log().all()
						.header("accept", "application/json")
						.header("X-Api-Key",aPIKey)
						.header("X-CompanyId", companyId)
						.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/appointment/getappointmentsbybook/"+resourceId+"/"+sDateTimeNoOffset+"/"+eDateTimeNoOffset)
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Message", equalTo("Invalid date range"));
	}
	
	@Test (testName="Resource Type Not Found",description="PBI:132256")
	public void resourceTypeNotFound() {

		String resourceTypeId = "99999";
		String sDateTimeNoOffset = ReusableDates.getCurrentDate();
		String eDateTimeNoOffset = ReusableDates.getCurrentDatePlusOneDay();

				given()
//						.log().all()
						.header("accept", "application/json")
						.header("X-Api-Key",aPIKey)
						.header("X-CompanyId", companyId)
						.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/appointment/getappointmentsbybook/"+resourceTypeId+"/"+sDateTimeNoOffset+"/"+eDateTimeNoOffset)
						.then()
//						.log().body()
						.assertThat().statusCode(404)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Message", equalTo("Nothing found"));
	}
	
	@Test (testName="Resource Type Required",description="PBI:132256")
	public void resourceTypeRequired() {

		String resourceTypeId = prop.getProperty("NOTdemoBookId");
		String sDateTimeNoOffset = ReusableDates.getCurrentDate();
		String eDateTimeNoOffset = ReusableDates.getCurrentDatePlusOneDay();

				given()
//						.log().all()
						.header("accept", "application/json")
						.header("X-Api-Key",aPIKey)
						.header("X-CompanyId", companyId)
						.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/appointment/getappointmentsbybook/"+resourceTypeId+"/"+sDateTimeNoOffset+"/"+eDateTimeNoOffset)
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Message", equalTo("The value 'null' is not valid for BookId."));
	}
	
	@Test (testName="Start Date Required",description="PBI:132256")
	public void startDateRequired() {

		String resourceTypeId = prop.getProperty("demoBookId");
		String sDateTimeNoOffset = prop.getProperty("nullValue");
		String eDateTimeNoOffset = ReusableDates.getCurrentDatePlusOneDay();

				given()
//						.log().all()
						.header("accept", "application/json")
						.header("X-Api-Key",aPIKey)
						.header("X-CompanyId", companyId)
						.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/appointment/getappointmentsbybook/"+resourceTypeId+"/"+sDateTimeNoOffset+"/"+eDateTimeNoOffset)
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Message", equalTo("The value 'null' is not valid for StartDateTime."));
	}
	
	@Test (testName="End Date Required",description="PBI:132256")
	public void endDateRequired() {

		String resourceTypeId = prop.getProperty("demoBookId");
		String sDateTimeNoOffset = ReusableDates.getCurrentDate();
		String eDateTimeNoOffset = prop.getProperty("nullValue");

				given()
//						.log().all()
						.header("accept", "application/json")
						.header("X-Api-Key",aPIKey)
						.header("X-CompanyId", companyId)
						.header("X-ClubId", clubId)
					.when()
						.get("/api/v3/appointment/getappointmentsbybook/"+resourceTypeId+"/"+sDateTimeNoOffset+"/"+eDateTimeNoOffset)
						.then()
//						.log().body()
						.assertThat().statusCode(400)
						.time(lessThan(60L),TimeUnit.SECONDS)
						.body("Message", equalTo("The value 'null' is not valid for EndDateTime."));
	}
	
}
