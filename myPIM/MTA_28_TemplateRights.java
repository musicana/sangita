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
public class MTA_28_TemplateRights extends GetBrowserInstance {

	GetBrowserInstance browser = new GetBrowserInstance(); // Creating object for GetBrowserInstance class.
	Login login = new Login(); // Creating object for Login class.
	Keywords keys = new Keywords(); // Creating object for Keywords class.
	NavigateModule selectmodule = new NavigateModule(); // Creating object for NavigateModule class.
	Userfunctions uf = new Userfunctions(); // Creating object for Userfunctions class.
	Definition def = new Definition(); // Creating object for Definition class.

	// --------------- Variables ------------------------- //
	String user = Constants.userName;
	String pwd = Constants.password;
	String groupname = "Test_aakash";
	String username = "ash";
	String newpass = "123456";
	String Email = "123@gmail.com";
	String defnamevalue = "aac";
	String channel = "Excel";
	String language = "Deutsch";
	String textfieldvalue = "TextField";
	String linkfieldvalue = "LinkField";
	String containerfieldvalue = "ContainerField";
	String datefieldvalue = "DateField";
	String numberfieldvalue = "NumberField";
	String templateType = "Desktop"; // Other types : Tablet-Landscape , Tablet-Portrait
	String templateName = "Ash";
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






			// ------------- Add usergroup and user -------------- //
			testStep="Add user";
			keys.pause(3);
			uf.addUserGroup(driver,groupname);
			keys.pause(3);
			uf.addUser(driver,groupname, username, Email, newpass);
			keys.pause(3);
			uf.clearSearch(driver);
			el.passLog(testStep, report, logger);

			// ------------------ Create Definiiton ------------------- //
			testStep=" createDefinition";

			def.addDefinition(groupname, defnamevalue, channel, language);
			keys.pause(2);
			def.addNewField(textfieldvalue, "Text");
			keys.pause(4);
			el.passLog(testStep, report, logger);
			
			testStep=" Add containerfield";
			
			
			
			
			
			def.addContainerField(containerfieldvalue, "3april");
//			keys.pause(4);
//			def.addNewField(linkfieldvalue, "Link");
//			keys.pause(4);
//			def.addNewField(datefieldvalue, "Date");
//			keys.pause(4);
//			def.addNewField(numberfieldvalue, "Number");
//			el.passLog(testStep, report, logger);


			// -------------------- Navigate to Template ------------------------------ //
			testStep=" createTemplate";

			def.nav_definition("Template");
			keys.pause(2);
			def.selectTemplate(templateType, templateName); // ARGS : selectTemplate ( String TemplateType , String
			// TemplateName )
			keys.pause(2);
			def.dragDefFields(fieldNames, 0, 0, 0, "Definition"); // ARGS : dragFields (String [] FieldNames , int
			// HorizontalLine , int VerticalLine , int Labels ,
			// String Type)
			// Type : "Definition" / "Link:
			keys.pause(2);
			def.closeDef();
			keys.pause(5);


			// --------------- Giving Template rights to the usergroup ----------------- //
			testStep="templateRight";

			uf.clearSearch(driver);
			uf.search(driver,username);
			uf.select(driver,username);
			keys.pause(2);
			def.templateRights(defnamevalue, templateType, templateName); // ARGS : templateRights(String defName , String
			// templateType , String NameofTemplate)


			// ---------------- Delete definition and usergroup ---------------------- //
			testStep=" deleteMethod";

			keys.pause(2);
			def.deleteDef(defnamevalue);
			keys.pause(2);
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
		// -------------------- Closing Browser ------------------------------ //
	@AfterClass
		public void closeBrowser() {
			
		//	driver.close();
		}

	}
