package functionalTests;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import resources.ReusableDates;
import resources.base;
import resources.myActions;
import resources.myGets;

public class ScheduleAppointment extends base {

    //	************************************
//	*** VARIABLES FROM GETS ***	
    public static String customerId;
    public static int appointmentId;
    public static int bookId;
    public static String occurrence;
    public static String result;
//	************************************

    //	*** TEST INPUT DATA ***
    public static String username = "available";
    public static String password = "Testing1!";
    public static String sDateTimeNoOffset = ReusableDates.getCurrentDate();
    public static String eDateTimeNoOffset = ReusableDates.getCurrentDatePlusOneWeek();
    static int itemId = 215;
//	************************************

    @Test(priority = 1, testName = "ScheduleAppointment-Step 1", description = "Feature: 138921, PBI: 139602")
    public static void validateCustomerId() {

        customerId = myGets.getCustomerId(username, password);
//		System.out.println("customerId: "+customerId);
    }

    @Test(priority = 2, testName = "ScheduleAppointment-Step 2", description = "Feature: 138921, PBI: 138984")
    public static void getOccurrence() {

        occurrence = myGets.getavailableappointments_Occurrence(customerId, sDateTimeNoOffset, eDateTimeNoOffset, itemId);
//		System.out.println("occurrence: "+occurrence);
    }

    @Test(priority = 3, testName = "ScheduleAppointment-Step 3", description = "Feature: 138921, PBI: 138984")
    public static void getBookId() {

        bookId = myGets.getavailableappointments_BookId(customerId, sDateTimeNoOffset, eDateTimeNoOffset, itemId);
//		System.out.println("bookId: "+bookId);
    }

    @Test(priority = 4, testName = "ScheduleAppointment-Step 4", description = "Feature: 124004, PBI: 127168")
    public static void bookAppt() {

        appointmentId = myActions.bookappointmentbymember(occurrence, customerId, bookId, itemId);
//		System.out.println("appointmentId: "+appointmentId);
    }

    @Test(priority = 5, testName = "ScheduleAppointment-Step 5", description = "Feature: 139407, PBI: 141862")
    public static void apptCancelled() {

        result = myActions.cancelappointmentbyemployee(appointmentId);
//		System.out.println("result: "+result);
    }

    @BeforeClass
    public void getData() {
        base.getPropertyData();
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = prop.getProperty("baseURI");
    }

}
