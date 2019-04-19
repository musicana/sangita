package myPIM;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
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
public class MTA_14_DeleteDefinition extends GetBrowserInstance {

	GetBrowserInstance browser = new GetBrowserInstance(); // Creating object for GetBrowserInstance class.
	Login login = new Login(); // Creating object for Login class.
	Keywords keys = new Keywords(); // Creating object for Keywords class.
	NavigateModule selectmodule = new NavigateModule(); // Creating object for NavigateModule class.
	Userfunctions uf = new Userfunctions(); // Creating object for Userfunctions class.
	Definition def = new Definition(); // Creating object for Definition class.

	// ---------------- Variables -------------------- //
	String user = Constants.userName;
	String pwd = Constants.password;
	String groupname = "Test_aakasho";
	String username = "ashi";
	String newpass = "123456";
	String Email = "123@gmail.com";
	String defnamevalue = "aab";
	String channel = "Excel";
	String language = "Deutsch";

	// -------------- Starting Browser ---------------- //
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


			testStep=" Usergroup";
			uf.addUserGroup(driver,groupname);
			uf.search(driver,groupname);
			uf.select(driver,groupname);

			uf.clearSearch(driver);
			// ------------------- Delete Definition Method ------------------------ //
			testStep="deleteDefinition ";

			def.addDefinition(groupname, defnamevalue, channel, language);
			// keys.wait(driver, MyPIMadmin.msg);
			keys.pause(3);
			//	keys.assertText(driver, MyPIMadmin.actualmsg, MyPIMadmin.expectedgroupCreated);
			// keys.click(driver, MyPIMadmin.msg);
			keys.pause(2);
			def.closeDef();
			keys.pause(2);
			def.deleteDef(defnamevalue);
			// keys.wait(driver, MyPIMadmin.msg);
			keys.pause(3);
			//keys.assertText(driver, MyPIMadmin.actualmsg, MyPIMadmin.expectedDefdelete);
			// keys.click(driver, MyPIMadmin.msg);
			keys.pause(3);
			// uf.clearSearch();
			// uf.deleteUserGroup(groupname);
			keys.pause(2);
			login.logout(driver);
			keys.pause(2);
		}
		catch(Exception e)
		{	
			el.updateTestStep(testStep, report, logger, driver);
			el.sendScreenShotOnFailure(testStep, report, logger, driver);
			Assert.fail("Failed at TestStep" + testStep + e);

		}
	}
	// ------------ Closing Browser -------------------- //
	@AfterTest
	public void closeBrowser() {
		// login.logout(driver);
		// keys.pause(2);
		driver.close();
	}

}
