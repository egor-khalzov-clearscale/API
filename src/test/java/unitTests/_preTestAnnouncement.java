package unitTests;

import org.testng.annotations.BeforeSuite;

import resources.ReusableDates;
import resources.base;

public class _preTestAnnouncement extends base {

//	public static void main(String[] args) {
		@BeforeSuite
		public void getPreTestAnnouncement() {

			base.getPropertyData();
			
			System.out.println("---------------------------------------------");
			System.out.println("[INFO] "+prop.getProperty("product")+"- Automated Testing Suite");
			System.out.println("[INFO] Test Environment: "+prop.getProperty("environment"));
			System.out.println("[INFO] Starting at "+ReusableDates.getCurrentDate());
			System.out.println("---------------------------------------------");
	}

}
