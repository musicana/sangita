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
public class MTA_45_TemplateDecorator extends GetBrowserInstance {

	GetBrowserInstance browser = new GetBrowserInstance(); // Creating object for GetBrowserInstance class.
	Login login = new Login(); // Creating object for Login class.
	Keywords keys = new Keywords(); // Creating object for Keywords class.
	NavigateModule selectmodule = new NavigateModule(); // Creating object for NavigateModule class.
	Userfunctions uf = new Userfunctions(); // Creating object for Userfunctions class.
	Definition def = new Definition(); // Creating object for Definition class.

	// ----------------- Variables ---------------------- //
	String user = Constants.userName;
	String pwd = Constants.password;
	String groupname = "Test_aakash";
	String username = "User14";
	String password = "1234567";
	String Email = "123@gmail.com";
	String defnamevalue = "aaa";
	String channel = "Excel";
	String language = "Deutsch";
	String textfieldvalue = "TextField";
	String templateType = "Desktop"; // Other types : Tablet-Landscape , Tablet-Portrait
	String templateName = "Ash";
	String[] defName = { "aam" };
	String[] fieldNames = { textfieldvalue };

	ExtentLoging el = new ExtentLoging();
	ExtentReports report;
	ExtentTest logger;
	String testStep;


//	@BeforeClass
//	public void openBrowser() {
//		driver = browser.getBrowser();
//
//		report = ExtentReport.getInstance();
//		String testCaseName= this.getClass().getName();
//		logger = report.createTest(testCaseName);
//	}

	
	// -------------- Start Browser ------------------- //
		@BeforeClass
		public void openBrowser() {

			report = ExtentReport.getInstance();
			String testCaseName= this.getClass().getName();
			logger = report.createTest(testCaseName);

			driver = browser.getBrowser();
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

	// ---------------- Create Link field and child definition -------------------
	// //
			testStep= "  createDefintion ";
		
		def.addDefinition(groupname, defnamevalue, channel, language);
		keys.pause(2);
		def.addNewField(textfieldvalue, "Text");
		keys.pause(4);
	//	def.fieldChannelAndConstraint(textfieldvalue, "Excel", "");
		keys.pause(2);
		el.passLog(testStep, report, logger);
	
	// -------------------- Navigate to Template ------------------------------ //
	testStep=" createTemplate";
		

		def.nav_definition("Template");
		keys.pause(2);
		def.selectTemplate(templateType, templateName);
		keys.pause(2);
		def.dragDefFields(fieldNames, 2, 2, 2, "Definition"); // ARGS: dragDefFields(String [] fieldNames, int
																// horizontalCount,int verticalCount, int labelCount,
																// String type)
		keys.pause(2);
		def.closeDef();
		el.passLog(testStep, report, logger);

	// --------------------- Assign Definition to the user
	// ---------------------------- //
	testStep="assignDefinitionTouser";
		
		uf.clearSearch(driver);
		uf.search(driver,username);
		uf.select(driver,username);
		keys.pause(5);
		def.assignDefintion(defName);
		keys.pause(2);
		el.passLog(testStep, report, logger);

	// ---------------------- Giving Template rights to the Definition
	// ---------------------- //
	testStep="templateRights";
		
		uf.clearSearch(driver);
		uf.search(driver,username);
		uf.select(driver,username);
		keys.pause(2);
		def.definitionRigihts(defnamevalue, true, true, true, true);
		keys.pause(2);
		def.templateRights(defnamevalue, templateType, templateName);
		el.passLog(testStep, report, logger);
	
	// ---------------------- Giving Admin rights to the User
	// ------------------------- //
	testStep=" adminRightstoUser";
		
		selectmodule.navigateTasks();
		selectmodule.userRights();
		uf.adminRights(driver,groupname, username);
		keys.pause(2);
		selectmodule.logout();
		keys.pause(5);
		el.passLog(testStep, report, logger);

	// -------------------- Login with the User Credentials -----------------------
	// //
	testStep=" loginWithUser";
		
		login.login(driver, username, password);
		selectmodule.navigateMyPIM();
		keys.pause(2);
	

	// ------------------- Adding Record --------------------------- //
		testStep="addRecord";
		
		uf.selectDefForRecord(driver,defnamevalue);
		keys.pause(2);
		uf.makeRecord(driver);
		keys.pause(2);
		uf.editRecordTextField(driver,textfieldvalue, "hello", "text", 0);
		keys.pause(2);
		uf.saveRecord(driver);
		keys.pause(5);
		uf.deleteRecord(driver);
		keys.pause(2);
		login.logout(driver);
		keys.pause(5);
	

	// --------------------- Delete Method ----------------------- //
	testStep=" deleteMethod";
		

		login.login(driver, user, pwd);
		selectmodule.navigateMyPIM();
		selectmodule.navigateAdministration();
		uf.search(driver,groupname);
		uf.select(driver,groupname);
		keys.pause(2);
		def.deleteDef(defnamevalue);
		keys.pause(5);
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

	// ------------------- Close Brwoser ----------------------- //
	@AfterClass
	public void closeBrowser() {
	//	driver.close();
	}

}
