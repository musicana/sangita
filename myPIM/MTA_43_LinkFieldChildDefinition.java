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
import pageObjects.NavigateModule;
import pageObjects.Userfunctions;
import utils.Constants;
import utils.ExtentLoging;
import utils.ExtentReport;
import basePackage.GetBrowserInstance;
import utils.Keywords;
@Listeners(utils.ListenerTest.class)
public class MTA_43_LinkFieldChildDefinition extends GetBrowserInstance {

	GetBrowserInstance browser = new GetBrowserInstance(); // Creating object for GetBrowserInstance class.
	Login login = new Login(); // Creating object for Login class.
	Keywords keys = new Keywords(); // Creating object for Keywords class.
	NavigateModule selectmodule = new NavigateModule(); // Creating object for NavigateModule class.
	Userfunctions uf = new Userfunctions(); // Creating object for Userfunctions class.
	Definition def = new Definition(); // Creating object for Definition class.{

	// --------------- Variables --------------------- //
	String user = Constants.userName;
	String pwd = Constants.password;
	String groupname = "Test_aakash";
	String username = "User1234";
	String password = "1234567";
	String Email = "123@gmail.com";
	String defnamevalue = "aaaaa";
	String channel = "Excel";
	String language = "Deutsch";
	String linkfieldvalue = "LinkField";
	String textfieldvalue = "TextField";
	String template = "Desktop"; // Other types : Tablet-Landscape , Tablet-Portrait
	String linkTemplateType = "Desktop"; // Other types : Tablet-Landscape , Tablet-Portrait
	String templateName = "Ash";
	String linkTemplateName = "Link Template";
	String[] childNames = { "field1,Container"
			// ,"field1,Text"
			// ,"field2,Date"
			// ,"field3,Number"
	};
	String[] defName = { "aaaaa" };
	String[] fieldValueType = { "single"
			// "text"
			// ,"date"
			// ,"number"
	};


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
			testStep=" createLinkField";
		
		def.addDefinition(groupname, defnamevalue, channel, language);
		keys.pause(2);
		def.addNewField(linkfieldvalue, "Link"); // ARGS: addNewField(String fieldvalue , String fieldtype)
		keys.pause(4);
		def.linkDefinition(linkfieldvalue, childNames, fieldValueType, linkTemplateType, linkTemplateName); // ARGS:
																											// linkDefinition(String
																											// linkFieldName,String
																											// []
																											// childFields,
																											// String []
																											// childFieldValueType,
																											// String
																											// templateType,
																											// String
																											// templateName)
		keys.pause(2);
		def.closeDef();
		keys.pause(2);
	
	// --------------------- Assign Definition to the user
	// ---------------------------- //
	testStep=" assignDefinitionTouser";
		
		uf.clearSearch(driver);
		uf.search(driver,username);
		uf.select(driver,username);
		keys.pause(5);
		def.assignDefintion(defName);
		keys.pause(2);
	

	// ------------------ Delete Method ---------------------- //
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
	// -------------- Close browser ----------------------- //
	@AfterTest
	public void closeBrowser() {
		login.logout(driver);
		keys.pause(5);
		driver.close();
	}

}
