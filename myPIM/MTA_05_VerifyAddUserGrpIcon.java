package myPIM;

import org.testng.Assert;
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
public class MTA_05_VerifyAddUserGrpIcon extends GetBrowserInstance {

	GetBrowserInstance browser = new GetBrowserInstance(); // Creating object for GetBrowserInstance class.
	Login login = new Login(); // Creating object for Login class.
	Keywords keys = new Keywords(); // Creating object for Keywords class.
	NavigateModule selectmodule = new NavigateModule(); // Creating object for NavigateModule class.
	Userfunctions uf = new Userfunctions(); // Creating object for Userfunctions class.
	Definition def = new Definition(); // Cr

	String User = Constants.userName;
	String pwd = Constants.password;
	String groupname = "Test_Automation";

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
	public void VerifyAddUserGroupIcon() throws Exception {

		try
		{
			testStep=" Search User group field";

			login.login(driver, Constants.userName, Constants.password);
			//	keys.wait(driver, MyPIMadmin.taskpage);
			el.passLog(testStep, report, logger);


			testStep = " navigate to administration" ;
			selectmodule.navigateMyPIM();
			// keys.wait(driver, MyPIMadmin.definitionpage);
			selectmodule.navigateAdministration();
			Thread.sleep(3000);
			el.passLog(testStep, report, logger);


			testStep="add user grouop";

			uf.addUserGroup(driver, groupname);
			el.passLog(testStep, report, logger);
			
			testStep= " Delete user group";
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
}