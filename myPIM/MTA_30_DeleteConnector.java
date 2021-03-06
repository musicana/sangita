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
import pageObjects.NavigateModule;
import pageObjects.Userfunctions;
import utils.Constants;
import utils.ExtentLoging;
import utils.ExtentReport;
import basePackage.GetBrowserInstance;
import utils.Keywords;
@Listeners(utils.ListenerTest.class)
public class MTA_30_DeleteConnector extends GetBrowserInstance {

	GetBrowserInstance browser = new GetBrowserInstance(); // Creating object for GetBrowserInstance class.
	Login login = new Login(); // Creating object for Login class.
	Keywords keys = new Keywords(); // Creating object for Keywords class.
	NavigateModule selectmodule = new NavigateModule(); // Creating object for NavigateModule class.
	Userfunctions uf = new Userfunctions(); // Creating object for Userfunctions class.
	Definition def = new Definition(); // Creating object for Definition class.

	// ------------------ Variables ----------------------- //
	String user = Constants.userName;
	String pwd = Constants.password;
	String groupname = "Test_acnh";
	String username = "ahu";
	String password = "123456";
	String Email = "123@gmail.com";
	String defnamevalue = "ma";
	String channel = "Emnk";
	String language = "Deutsch";
	String subConnectionName = "Test_Coection9";
	String key = "123456";
	String secret = "123456";
	String url = "123456@gmail.com";
	String[] channels = { "Group", "Technical" };

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

			testStep=" addUsergroup";

			keys.pause(3);
			uf.addUserGroup(driver,groupname);
			uf.search(driver, groupname);
			uf.select(driver, groupname);


			el.passLog(testStep, report, logger);


			// ------------------- Adding Connector ----------------------------- //
			testStep=" addConnector ";

			def.addConnector(subConnectionName, key, secret, url); // ARGS: addConnector(String connectionName, String Key,
			// String Secret, String Url)
			keys.pause(2);
			el.passLog(testStep, report, logger);
			
			testStep=" Add channel";
			def.nav_Dashboard("Channel");
			keys.pause(2);
			el.passLog(testStep, report, logger);
			
			testStep=" Create channel";
			def.createChannel(channels, "WooCommerce", subConnectionName); // ARGS: createChannel(String [] channelNames,

			el.passLog(testStep, report, logger);

			// ------------------- Deleteing Connector --------------------------- //
			testStep="deleteConnector";

			def.deleteChannel(channels);
			el.passLog(testStep, report, logger);
			
			testStep=" delete connector ";
			
			keys.pause(2);
			def.nav_Dashboard("Connectors");
			keys.pause(2);
			def.deleteConnector(subConnectionName); // ARGS : deleteConnector ( String Connection )
			keys.pause(2);
			el.passLog(testStep, report, logger);
			
			
			testStep=" delete";
			uf.deleteUserGroup(driver,groupname);
			keys.pause(2);
			el.passLog(testStep, report, logger);
		}

		catch(Exception e)
		{	
			el.updateTestStep(testStep, report, logger, driver);
			el.sendScreenShotOnFailure(testStep, report, logger, driver);
			Assert.fail("Failed at TestStep" + testStep + e);

		}
	}
	// --------------------- Closing Browser ---------------------------- //
	@AfterClass
	public void closeBrowser() {
		//login.logout(driver);
	//	driver.close();
	}

}
