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
import pageObjects.MyPIMadmin;
import pageObjects.NavigateModule;
import pageObjects.Userfunctions;
import utils.Constants;
import utils.ExtentLoging;
import utils.ExtentReport;
import basePackage.GetBrowserInstance;
import utils.Keywords;
@Listeners(utils.ListenerTest.class)
public class MTA_36_AddRecordToDefinition extends GetBrowserInstance {

	GetBrowserInstance browser = new GetBrowserInstance(); // Creating object for GetBrowserInstance class.
	Login login = new Login(); // Creating object for Login class.
	Keywords keys = new Keywords(); // Creating object for Keywords class.
	NavigateModule selectmodule = new NavigateModule(); // Creating object for NavigateModule class.
	Userfunctions uf = new Userfunctions(); // Creating object for Userfunctions class.
	Definition def = new Definition(); // Creating object for Definition class.

	// ------------------- Variables --------------------------- //
	String user = Constants.userName;
	String pwd = Constants.password;
	String groupname = "Test_nakash";
	String username = "User1234";
	String password = "1234567";
	String Email = "123@gmail.com";
	String defnamevalue = "aaaaa";
	String channel = "Excel";
	String language = "Deutsch";
	String textfieldvalue = "TextField";
	String linkfieldvalue = "LinkField";
	String containerfieldvalue = "ContainerField";
	String datefieldvalue = "DateField";
	String numberfieldvalue = "NumberField";
	String templateType = "Desktop"; // Other types : Tablet-Landscape , Tablet-Portrait
	String templateName = "Ash";
	String[] fieldNames = { "TextField", "ContainerField", "LinkField", "DateField", "NumberField" };
	String[] defName = { "aaaaa" };
	String filepath = "C:\\sangy.jpg";


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


	// ---------------- Create Definition and add Fields ------------------------ //
			testStep=" creatDefinitonAddFields";
		
		def.addDefinition(groupname, defnamevalue, channel, language);
		keys.pause(2);
		def.addNewField(textfieldvalue, "Text"); // ARGS: (String fieldname , String fieldType)
		keys.pause(2); // Field Type : "Text" / "Link" / "Date" / "Number" keys.pause(4);
		def.fieldChannelAndConstraint(textfieldvalue, "Excel", "Unique"); // ARGS: fieldChannelAndConstraint(String
																			// fieldName, String channel, String
																			// constraint)
		keys.pause(2); // Channel : "Excel" Constraint: "Unique" / "Required" / "Key"
		def.addContainerField(containerfieldvalue, "3april");
		keys.pause(4);
		def.fieldChannelAndConstraint(containerfieldvalue, "Excel", "Required");
		keys.pause(4);
		def.addNewField(linkfieldvalue, "Link");
		keys.pause(4);
		def.fieldChannelAndConstraint(numberfieldvalue, "Excel", "");
		keys.pause(2);
		def.addNewField(datefieldvalue, "Date");
		keys.pause(4);
		def.fieldChannelAndConstraint(datefieldvalue, "Excel", "Unique");
		keys.pause(2);
		def.addNewField(numberfieldvalue, "Number");
		keys.pause(4);
		def.fieldChannelAndConstraint(numberfieldvalue, "Excel", "Unique");
		keys.pause(2);
	

	// -------------------- Create Template ------------------------------ //
		testStep=" createTemplate";
		
		def.nav_definition("Template");
		keys.pause(2);
		def.selectTemplate(templateType, templateName); // ARGS: selectTemplate ( String TemplateType , String
														// TemplateName )
		keys.pause(2);
		def.dragDefFields(fieldNames, 0, 0, 0, "Defintion"); // ARGS : dragFields (String [] FieldNames , int
																// HorizontalLine , int VerticalLine , int Labels ,
																// String Type)
																// Type : "Definition" / "Link
		keys.pause(2);
		def.closeDef();
	

	// ---------------------- Giving Template rights to the Definition
	// ---------------------- //
		testStep=" templateRights";
		
		uf.clearSearch(driver);
		uf.search(driver,username);
		uf.select(driver,username);
		keys.pause(2);
		def.definitionRigihts(defnamevalue, true, true, true,
				false); /*
						 * ARGS: definitionRights( String defName , boolean insertCheckbox , boolean
						 * editCheckBox , boolean deleteCheckbox , boolean historyCheckBox )
						 */
		keys.pause(2);
		def.templateRights(defnamevalue, templateType, templateName); // ARGS : templateRights(String defName , String
																		// templateType , String NameofTemplate)
	

	// ---------------------- Assigning definiton to the user
	// ------------------------- //
		testStep=" assignDefinitionTouser";
		
		keys.pause(5);
		def.assignDefintion(defName);
		keys.pause(2);
	

	// ---------------------- Giving Admin rights to the User
	// ------------------------- //
	testStep=" adminRightstoUser";
		
		selectmodule.navigateTasks();
		selectmodule.userRights();
		uf.adminRights(driver,groupname, username);
		keys.pause(2);
	

	// -------------------- Login with the User Credentials -----------------------
	// //
		testStep=" loginWithUser";
		
		selectmodule.logout();
		keys.pause(5);
		login.login(driver, username, password);
		selectmodule.navigateMyPIM();
		keys.pause(2);
	
	// ------------------- Adding Record --------------------------- //
		testStep="addRecord";
		
		uf.selectDefForRecord(driver,defnamevalue);
		keys.pause(2);
		uf.makeRecord(driver);
		keys.pause(2);
		uf.editRecordTextField(driver,textfieldvalue, "hello", "", 0); // ARGS: editRecordTextField( String FieldName, String
																// Content, String ValueType, int
																// CountOfMultipleTextFields)
		keys.pause(2);
		uf.editRecordContainerField(driver,containerfieldvalue, filepath, "", 0); // ARGS: editRecordContainerField( String
																			// FieldName , String FieldPath , String
																			// FieldValueType )
		keys.pause(8);
		uf.editRecordDateField(driver,datefieldvalue, "date", "25"); // ARGS: editRecordDateField( String FieldName , String
																// ValueType , int Date )
		keys.pause(2);
		uf.editRecordNumberField(driver,numberfieldvalue, "1234"); // ARGS: editRecordNumberField(String FieldName, int Number)
		keys.pause(2);
		uf.saveRecord(driver);
		keys.pause(5);
		// uf.deleteRecord();
		// keys.pause(2);
		login.logout(driver);
		keys.pause(5);
	

	// --------------------- Delete Method ----------------------- //
		testStep="deleteMethod";
		
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
