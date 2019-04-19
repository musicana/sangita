package myPIM;

import org.openqa.selenium.By;
import org.testng.Assert;
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
public class MTA_01_SearchUserGroupField extends GetBrowserInstance {


	GetBrowserInstance browser = new GetBrowserInstance(); // Creating object for GetBrowserInstance class.
	Login login = new Login(); // Creating object for Login class.
	Keywords keys = new Keywords(); // Creating object for Keywords class.
	NavigateModule selectmodule = new NavigateModule(); // Creating object for NavigateModule class.
	Userfunctions uf = new Userfunctions(); // Creating object for Userfunctions class.
	Definition def = new Definition(); // Cr

	public By actual = By.xpath(
			"//div[@id='panel-usg']/shared-user-and-group-tree/div[2]/tree-root/tree-viewport/div/div/tree-node-collection/div/tree-node/div/tree-node-children/div/tree-node-collection/div/tree-node/div/tree-node-children/div/tree-node-collection/div/tree-node/div/tree-node-wrapper/div/div/tree-node-content/span[2]");
	public By crossicon = By.xpath("//div[@id='user-search-container']/div/span[2]");
	public By blankusers = By
			.xpath("//div[@id='panel-usg']/shared-user-and-group-tree/div[2]/tree-root/tree-viewport/div");

	String User = Constants.userName;
	String pwd = Constants.password;
	String invaliduser = "bchjsbdcbdfewfqqw";
	String invalidcharacters = "!@#%$#%$%^%^&^*&$*";

	ExtentLoging el = new ExtentLoging();
	ExtentReports report;
	ExtentTest logger;
	String testStep;


	@BeforeClass
	public void openBrowser() {

		report = ExtentReport.getInstance();
		String testCaseName= this.getClass().getName();
		logger = report.createTest(testCaseName);

		driver = browser.getBrowser();
	}



	@Test
	public void searchuser() throws Exception {

		try
		{
			testStep=" Search User group field";

			login.login(driver, User, pwd);
			el.passLog(testStep, report, logger);


			//-------------------------------Navigate to My Pim---------------//
			testStep="Navigate to my pim";
			selectmodule.navigateMyPIM();

			keys.pause(4);
			// keys.wait(driver, MyPIMadmin.definitionpage);
			selectmodule.navigateAdministration();
			Thread.sleep(4000);
			el.passLog(testStep, report, logger);



			testStep = " Search user ";
			//	keys.wait(driver, MyPIMadmin.leftpane);
			uf.search(driver,"systemadmin"); // Searching with valid data.
			keys.click(driver, crossicon);
			el.passLog(testStep, report, logger);

			testStep = " compare ";
			uf.search(driver,invaliduser); // search with invalid user name.
			keys.textnotPresent(driver, blankusers, invaliduser);
			keys.click(driver, crossicon);
			el.passLog(testStep, report, logger);

			testStep= " inavlid";
			uf.search(driver,invalidcharacters); // search with invalid characters.
			keys.textnotPresent(driver, blankusers, invalidcharacters);
			keys.click(driver, crossicon);
			el.passLog(testStep, report, logger);

			login.logout(driver);
		}

		catch(Exception e)
		{	
			el.updateTestStep(testStep, report, logger, driver);
			el.sendScreenShotOnFailure(testStep, report, logger, driver);
			Assert.fail("Failed at TestStep" + testStep + e);

		}
	}
}

/*
 * @AfterClass public void tearDown() { if(driver!=null) { //driver.close();
 * driver.quit(); driver = null; } }
 */


