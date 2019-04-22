package testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import basePackage.GetBrowserInstance;
import utils.ExtentLoging;
import utils.ExtentReport;

public class ok extends GetBrowserInstance {

	GetBrowserInstance browser = new GetBrowserInstance();
	ExtentLoging el = new ExtentLoging();
	ExtentReports report;
	ExtentTest logger;
	String testCaseName = "TestLogging";
	String testStep;
	@BeforeClass
	public void openBrowser() {

		report = ExtentReport.getInstance();
		String testCaseName= this.getClass().getName();
		logger = report.createTest(testCaseName);

		driver = browser.getBrowser();

	}
	
	@Test
	public void login() throws Exception {
		try {


			testStep = "Login";
			driver.get("https://www.facebook.com");

			el.passLog(testStep, report, logger);
	
		}
		catch(Exception e)
		{	

			el.updateTestStep(this.testStep, this.report, this.logger, driver);
			el.sendScreenShotOnFailure(this.testStep, this.report, this.logger, driver);
			Assert.fail("Failed at TestStep" + testStep + e);

		}

}


}
