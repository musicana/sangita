package myPIM;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import basePackage.GetBrowserInstance;
import pageObjects.Definition;
import pageObjects.Login;
import pageObjects.NavigateModule;
import pageObjects.Userfunctions;
import utils.Constants;
import utils.ExtentLoging;
import utils.ExtentReport;
import utils.Keywords;
@Listeners(utils.ListenerTest.class)
public class MTA_02_AddDeleteUserGroup extends GetBrowserInstance {


	GetBrowserInstance browser = new GetBrowserInstance(); // Creating object for GetBrowserInstance class.
	Login login = new Login(); // Creating object for Login class.
	Keywords keys = new Keywords(); // Creating object for Keywords class.
	NavigateModule selectmodule = new NavigateModule(); // Creating object for NavigateModule class.
	Userfunctions uf = new Userfunctions(); // Creating object for Userfunctions class.
	Definition def = new Definition(); // Cr

	String groupname = "testing@123";
	ExtentLoging el = new ExtentLoging();
	ExtentReports report;
	ExtentTest logger;
	String testStep;
	// ----------------- Variables -------------------- //
	String user = Constants.userName;
	String pwd = Constants.password;

	// -------------- Start Browser ------------------- //
	@BeforeClass
	public void openBrowser() {

		report = ExtentReport.getInstance();
		String testCaseName= this.getClass().getName();
		logger = report.createTest(testCaseName);

		driver = browser.getBrowser();
	}
	// -------------- Login Method ----------------- //
	@Test
	public void searchuser() throws Exception {

		try
		{
			testStep=" Search User group field";

			login.login(driver, user, pwd);
			el.passLog(testStep, report, logger);


			//-------------------------------Navigate to My Pim---------------//
			testStep="Navigate to my pim";
			selectmodule.navigateMyPIM();

			keys.pause(4);
			// keys.wait(driver, MyPIMadmin.definitionpage);
			selectmodule.navigateAdministration();
			Thread.sleep(4000);
			el.passLog(testStep, report, logger);
			

			testStep="add user group" ;
			uf.addUserGroup(driver, groupname);
		
		
			uf.deleteUserGroup(driver, groupname);
			el.passLog(testStep, report, logger);

		}

		catch(Exception e)
		{	
			el.updateTestStep(testStep, report, logger, driver);
			el.sendScreenShotOnFailure(testStep, report, logger, driver);
			Assert.fail("Failed at TestStep" + testStep + e);

		}
	}
	
	@AfterClass 
	public void closeBrowser()
	 { 
		login.logout(driver);
		driver.close(); }
	 

}
