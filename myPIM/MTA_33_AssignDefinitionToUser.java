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
public class MTA_33_AssignDefinitionToUser extends GetBrowserInstance {


	GetBrowserInstance browser = new GetBrowserInstance();
	Login login = new Login(); // Creating object for Login class.
	Keywords keys = new Keywords(); // Creating object for Keywords class.
	NavigateModule selectmodule = new NavigateModule(); // Creating object for NavigateModule class.
	Userfunctions uf = new Userfunctions(); // Creating object for Userfunctions class.
	Definition def = new Definition(); // Creating object for Definition class.

	// ------------------ Variables ------------------- //
	String user = Constants.userName;
	String pwd = Constants.password;
	String groupname = "Test_aakcaa";
	String username = "User1c234";
	String password = "1234567";
	String Email = "123@gmail.com";
	String defnamevalue = "daaa";
	String channel = "Excel";
	String language = "Deutsch";
	String[] defName = { defnamevalue };

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

			// **********************************************************************************
			// //
			// ****************** Assign defintion to user and verify it by login as user
			// ****************** //
			// **********************************************************************************
			// //

			// --------------------- Assign Defintion to user
			// -------------------------------- //
			testStep=" assignDefinitionToUser";

			def.addDefinition(groupname, defnamevalue, channel, language);
			keys.pause(2);
			uf.clearSearch(driver);
			uf.search(driver,username);
			uf.select(driver,username);
		
			el.passLog(testStep, report, logger);
			
			testStep=" assignDefinitionToUser";
			def.assignDefintion(defName);
			keys.refresh(driver);
			keys.pause(2);
			el.passLog(testStep, report, logger);

			// ----------------------- Give Admins rights to the user
			// ------------------------------ //

			testStep="adminRights";

			def.getassignedDefname(); // Returns def.list with the names of the definition.
			keys.pause(2);
			selectmodule.navigateTasks();
			selectmodule.userRights();
			uf.adminRights(driver,groupname, username);
			keys.pause(2);
			selectmodule.logout();
			keys.pause(2);


			// ---------------- Verify the assigned defintions ---------------------------
			// //
			testStep=" verifyDefinition";


			login.login(driver, username, password);
			selectmodule.navigateMyPIM();
			keys.pause(2);
			def.verifyDefNameAssignedToUser(def.list); // Argument is def.list that is returned from
			// def.getassignedDefname();.
			login.logout(driver);
			keys.pause(2);
			// ------------------- Delete Method ------------------------------- //
			testStep="deleteMethod";


			login.login(driver, user, pwd);
			selectmodule.navigateMyPIM();
			selectmodule.navigateAdministration();
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
	// ------------------------ Close browser ------------------------------ //
	@AfterClass
	public void closeBrowser() {
	
	//	driver.close();
	}
}
