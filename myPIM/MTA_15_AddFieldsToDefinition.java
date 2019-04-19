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
public class MTA_15_AddFieldsToDefinition extends GetBrowserInstance {

	GetBrowserInstance browser = new GetBrowserInstance(); // Creating object for GetBrowserInstance class.
	Login login = new Login(); // Creating object for Login class.
	Keywords keys = new Keywords(); // Creating object for Keywords class.
	NavigateModule selectmodule = new NavigateModule(); // Creating object for NavigateModule class.
	Userfunctions uf = new Userfunctions(); // Creating object for Userfunctions class.
	Definition def = new Definition(); // Creating object for Definition class.

	// ------------------- Variables --------------------------- //
	String user = Constants.userName;
	String pwd = Constants.password;
	String groupname = "Test_aakn";
	String username = "iiv";
	String password = "123456";
	String Email = "123@gmail.com";
	String defnamevalue = "mbc";
	String channel = "Excel";
	String language = "Deutsch";
	String textfieldvalue = "TextField1";
	String linkfieldvalue = "LinkField1";
	String containerfieldvalue = "ContainerField1";
	String datefieldvalue = "DateFeld1";
	String numberfieldvalue = "NumberFiemld";
	String mymediafilename = "13 febnew";

	// -------------------- Starting Browser ---------------------- //
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




			testStep="Add user group " ;
			uf.addUserGroup(driver,groupname);
			keys.pause(2);
			uf.addUser(driver,groupname, username, Email, password);

			uf.clearSearch(driver);
			keys.pause(2);

			el.passLog(testStep, report, logger);

			// ---------------- Add fields to the definition -------------------- //
			testStep = "  addFields ";


			def.addDefinition(groupname, defnamevalue, channel, language);
//			keys.wait(driver, MyPIMadmin.msg);
//			keys.assertText(driver, MyPIMadmin.actualmsg, MyPIMadmin.expectedgroupCreated);
//			keys.click(driver, MyPIMadmin.msg);
//			keys.pause(2);
			el.passLog(testStep, report, logger);
		
			testStep = "  Add new field";
			
			def.addNewField(textfieldvalue, "Text"); // Arguments for addNewField : (String fieldname , String fieldType)
			keys.pause(2); // Field Type : "Text" / "Link" / "Date" / "Number"
		
			el.passLog(testStep, report, logger);
			
			testStep = "  test 2 ";
			
//			def.addContainerField(containerfieldvalue, mymediafilename);
//			keys.pause(2);
			def.addNewField(linkfieldvalue, "Link");
			keys.pause(2);
			def.addNewField(datefieldvalue, "Date");
			keys.pause(2);
			def.addNewField(numberfieldvalue, "Number");
			keys.pause(2);
			def.closeDef();
			keys.pause(2);
			el.passLog(testStep, report, logger);

			// ----------- Delete method ------------------ //
			testStep = "delete" ; 


			def.deleteDef(defnamevalue);
			keys.pause(2);
			uf.clearSearch(driver);
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
	// --------------------- Closing Browser --------------------- //
	@AfterClass
	public void closeBrowser() {
	
		//driver.close();
	}

}
