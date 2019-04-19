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
public class MTA_24_CreateNewChannel_VerifyItInDefinition extends GetBrowserInstance {

	GetBrowserInstance browser = new GetBrowserInstance(); // Creating object for GetBrowserInstance class.
	Login login = new Login(); // Creating object for Login class.
	Keywords keys = new Keywords(); // Creating object for Keywords class.
	NavigateModule selectmodule = new NavigateModule(); // Creating object for NavigateModule class.
	Userfunctions uf = new Userfunctions(); // Creating object for Userfunctions class.
	Definition def = new Definition(); // Creating object for Definition class.

	// ------------------------ Variables ------------------------- //
	String user = Constants.userName;
	String pwd = Constants.password;
	String groupname = "Test_aakashio";
	String username = "ashi";
	String newpass = "123456";
	String Email = "123@gmail.com";
	String defnamevalue = "aaaaa";
	String channel = "Excel";
	String language = "Deutsch";
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
			//  Step 1
			testStep = "Login to Application";
			login.login(driver, user, pwd);
			el.passLog(testStep, report, logger);


			testStep="Navigate to my pim";
			selectmodule.navigateMyPIM();

			keys.pause(4);
			// keys.wait(driver, MyPIMadmin.definitionpage);
			selectmodule.navigateAdministration();
			Thread.sleep(4000);
			el.passLog(testStep, report, logger);



			// ----------- Add User group and User ---------------//
			testStep=" addUser";

			keys.pause(3);
			uf.addUserGroup(driver,groupname);
			keys.pause(3);
			uf.addUser(driver,groupname, username, Email, newpass);
			keys.pause(3);
			uf.clearSearch(driver);



			testStep="createChannelAndVerify";

			def.nav_Dashboard("Channel");
			keys.pause(2);
			def.createChannel(channels, "Excel", ""); // ARGS: createChannel (String [] Channels, String Connector,String
			// SubConnector)
			keys.pause(2);

			def.nav_Dashboard("Definition");
			keys.pause(2);
			def.addDefinition(groupname, defnamevalue, channel, language);
			keys.pause(2);
			def.nav_definition("Channel");
			keys.pause(2);
			def.addChannel(channels);
			keys.pause(2);
			def.verifyChannel(channels); // ARGS: addChannel ( String [] Channels ) * Adds all the channel in the array *
			// keys.pause(2);
			// def.addAllChannel(); //Adds all the channels
			keys.pause(2);
			def.minusChannel(channels); // ARGS: minusChannel( String [] Channels ) * Removes all the selected channels
			// from the array *
			keys.pause(2);
			def.closeDef();
			keys.pause(2);


			// --------------------- Delete Method ------------------- //
//			testStep="deleteMethod";
//
//			def.nav_Dashboard("Channel");
//			keys.pause(2);
//			def.deleteChannel(channels);
//			keys.pause(2);
//			def.nav_Dashboard("Definition");
//			keys.pause(2);
//			def.deleteDef(defnamevalue);
//			keys.pause(2);
//			uf.clearSearch(driver);
//			uf.deleteUserGroup(driver,groupname);
//			keys.pause(2);
		}
	
	
	catch(Exception e)
	{	
		el.updateTestStep(testStep, report, logger, driver);
		el.sendScreenShotOnFailure(testStep, report, logger, driver);
		Assert.fail("Failed at TestStep" + testStep + e);

	}
	}
		// ------------------ Close Browser ------------------- //
		@AfterClass
		public void closeBrowser() {
			login.logout(driver);
			keys.pause(2);
			driver.close();
		}
	}
