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
public class MTA_32_AdminRightsToUser extends GetBrowserInstance {

	GetBrowserInstance browser = new GetBrowserInstance(); // Creating object for GetBrowserInstance class.
	Login login = new Login(); // Creating object for Login class.
	Keywords keys = new Keywords(); // Creating object for Keywords class.
	NavigateModule selectmodule = new NavigateModule(); // Creating object for NavigateModule class.
	Userfunctions uf = new Userfunctions(); // Creating object for Userfunctions class.
	Definition def = new Definition(); // Creating object for Definition class.

	// ------------------ Variables ------------------- //
	String user = Constants.userName;
	String pwd = Constants.password;
	String groupname = "Test_aakash";
	String username = "User1234";
	String password = "1234567";
	String Email = "123@gmail.com";

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


	// ------------------ Giving Admin rights ---------------------------- //
	testStep=" adminRights";
		
		selectmodule.navigateTasks();
		selectmodule.userRights();
		uf.adminRights(driver,groupname, username); // ARGS: adminRights( String UserGroupName, String UserName )
		keys.pause(2);
		selectmodule.logout();
		keys.pause(5);
	

	// ------------------- Login with user -------------------- //
	testStep="loginWithUser";
		
		login.login(driver, username,password);
		selectmodule.navigateMyPIM();
		keys.pause(2);
		el.passLog(testStep, report, logger);
		
		testStep=" logout";
		selectmodule.logout1();
		keys.pause(5);
		el.passLog(testStep, report, logger);

	// ------------------- Delete Method -------------------------- //
	testStep="deleteMethod";
		

		login.login(driver, user, pwd);
		selectmodule.navigateMyPIM();
		selectmodule.navigateAdministration();
		keys.pause(2);
	//	uf.deleteUserGroup(driver,groupname);
		keys.pause(2);
	
}

catch(Exception e)
{	
	el.updateTestStep(testStep, report, logger, driver);
	el.sendScreenShotOnFailure(testStep, report, logger, driver);
	Assert.fail("Failed at TestStep" + testStep + e);

}
}
	// --------------- Close browser -------------------- //
	@AfterClass
	public void closeBrowser() {
	
		driver.close();
	}
}