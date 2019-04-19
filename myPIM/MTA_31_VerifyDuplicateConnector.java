package myPIM;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import pageObjects.Definition;
import pageObjects.Login;
import pageObjects.MyPIMadmin;
import pageObjects.NavigateModule;
import pageObjects.Userfunctions;
import utils.Constants;
import utils.ExtentLoging;
import utils.ExtentReport;
import basePackage.GetBrowserInstance;
import utils.Keywords;
@Listeners(utils.ListenerTest.class)
public class MTA_31_VerifyDuplicateConnector extends GetBrowserInstance {

	GetBrowserInstance browser = new GetBrowserInstance(); // Creating object for GetBrowserInstance class.
	Login login = new Login(); // Creating object for Login class.
	Keywords keys = new Keywords(); // Creating object for Keywords class.
	NavigateModule selectmodule = new NavigateModule(); // Creating object for NavigateModule class.
	Userfunctions uf = new Userfunctions(); // Creating object for Userfunctions class.
	Definition def = new Definition(); // Creating object for Definition class.

	// ----------------- Variables --------------------- //
	String user = Constants.userName;
	String pwd = Constants.password;
	String groupname = "Test_aakashmi";
	String username = "ash";
	String password = "123456";
	String Email = "123@gmail.com";
	String defnamevalue = "aaaa";
	String channel = "Excel";
	String language = "Deutsch";
	String subConnectionName = "Test_Connection";
	String key1 = "123456";
	String secret1 = "123456";
	String url1 = "123456@gmail.com";
	String key2 = "654321";
	String secret2 = "654321";
	String url2 = "654321@gmail.com";


	ExtentLoging el = new ExtentLoging();
	ExtentReports report;
	ExtentTest logger;
	String testStep;


	@BeforeClass
	public void openBrowser() {
		driver = browser.getBrowser();

		report = ExtentReport.getInstance();
		String testCaseName= this.getClass().getName();
		logger = report.createTest(testCaseName);
	}

	@Test
	public void AddUserGroup() throws Exception
	{

		try {
			//  Step 1 -----------------------Login----------------------------//
			testStep = "Login to Application";
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


			//------------------------------Add usergroup and user-------------//

			testStep="Add user group " ;
			uf.addUserGroup(driver,groupname);
			keys.pause(2);
			uf.addUser(driver,groupname, username, Email, password);

			uf.clearSearch(driver);
			keys.pause(2);

			el.passLog(testStep, report, logger);


			// ------------ Verifying user should not be able to add Duplicate Connector
			// ----------------- //
			testStep="duplicateConnector";

			def.nav_Dashboard("Connectors");
			keys.pause(2);
			def.addConnector(subConnectionName, key1, secret1, url1); // ARGS: addConnector(String connectionName, String
			// Key, String Secret, String Url)
			keys.pause(2);
			def.addConnector(subConnectionName, key2, secret2, url2); // ARGS: addConnector(String connectionName, String
			// Key, String Secret, String Url)
			keys.pause(2);
//			keys.assertText(driver, MyPIMadmin.actualmsg, MyPIMadmin.duplicateConnector);
//			keys.click(driver, MyPIMadmin.msg);
			keys.pause(4);
			keys.click(driver, def.CloseBtn);
			keys.pause(2);


			// ---------------------------- Delete Method ------------------------------//
			testStep=" deleteMethod";

			def.deleteConnector(subConnectionName); // ARGS : deleteConnector ( String Connection )
			keys.pause(2);
			uf.deleteUserGroup(driver,groupname);
			keys.pause(2);

		}

		catch(Exception e)
		{	
			el.updateTestStep(testStep, report, logger, driver);
			el.sendScreenShotOnFailure(testStep, report, logger, driver);
			Assert.fail("Failed at TestStep" + testStep + e);

		}

	}
	// ------------- Closing Browser ----------------- //
	@AfterClass
	public void closeBrowser() {
		login.logout(driver);
		driver.close();
	}

}
