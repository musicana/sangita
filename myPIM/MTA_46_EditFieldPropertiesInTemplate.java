package myPIM;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
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
public class MTA_46_EditFieldPropertiesInTemplate extends GetBrowserInstance {

	GetBrowserInstance browser = new GetBrowserInstance(); // Creating object for GetBrowserInstance class.
	Login login = new Login(); // Creating object for Login class.
	Keywords keys = new Keywords(); // Creating object for Keywords class.
	NavigateModule selectmodule = new NavigateModule(); // Creating object for NavigateModule class.
	Userfunctions uf = new Userfunctions(); // Creating object for Userfunctions class.
	Definition def = new Definition(); // Creating object for Definition class.

	// ---------------- Variables ---------------------------- //
	String user = Constants.userName;
	String pwd = Constants.password;
	String groupname = "Test_ash";
	String username = "User123c";
	String password = "1234567";
	String Email = "123@gmail.com";
	String defnamevalue = "aa1";
	String channel = "Excel";
	String language = "Deutsch";
	String textfieldvalue = "TextField";
	String containerfieldvalue = "ContainerField";
	String linkfieldvalue = "LinkField";
	String datefieldvalue = "DateField";
	String numberfieldvalue = "NumberField";
	String templateType = "Desktop";
	String templateName = "Ash";
	String[] defName = { "aaaaa" };
	String[] fieldNames = { textfieldvalue, containerfieldvalue, linkfieldvalue, datefieldvalue, numberfieldvalue };


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


	// ---------------- Create Link field and child definition -------------------
	// //
			testStep=" createDefinition";
		
		def.addDefinition(groupname, defnamevalue, channel, language);
		keys.pause(2);
		def.addNewField(textfieldvalue, "Text");
		keys.pause(4);
		def.addNewField(linkfieldvalue, "Link");
		keys.pause(4);
		def.addNewField(datefieldvalue, "Date");
		keys.pause(4);
		def.addNewField(numberfieldvalue, "Number");
		// def.fieldChannelAndConstraint(textfieldvalue,"Excel","");
		keys.pause(2);
	

	// -------------------- Navigate to Template ------------------------------ //
	testStep=" createTemplate";
		
		def.nav_definition("Template");
		keys.pause(2);
		def.selectTemplate(templateType, templateName);
		keys.pause(2);
		def.dragDefFields(fieldNames, 0, 0, 0, "Definition");
		keys.pause(2);
		keys.click(driver, def.crossicon);
//		def.editTemplateField(templateName, fieldNames);
//		keys.pause(3);
		
		//def.closeDef();
		
	

	// --------------------- Delete Method ----------------------- //
	testStep=" deleteMethod";
	
		uf.clearSearch(driver);
		uf.search(driver,groupname);
		uf.select(driver,groupname);
		keys.pause(2);
		def.deleteDef(defnamevalue);
		keys.pause(5);
		uf.clearSearch(driver);
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
	// ------------------- Close Brwoser ----------------------- //
	@AfterClass
	public void closeBrowser() {
		
	//	driver.close();
	}

}