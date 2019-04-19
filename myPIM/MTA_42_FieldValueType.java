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
public class MTA_42_FieldValueType extends GetBrowserInstance {

	GetBrowserInstance browser = new GetBrowserInstance(); // Creating object for GetBrowserInstance class.
	Login login = new Login(); // Creating object for Login class.
	Keywords keys = new Keywords(); // Creating object for Keywords class.
	NavigateModule selectmodule = new NavigateModule(); // Creating object for NavigateModule class.
	Userfunctions uf = new Userfunctions(); // Creating object for Userfunctions class.
	Definition def = new Definition(); // Creating object for Definition class.

	// ------------------- Variables --------------------------- //
	String user = Constants.userName;
	String pwd = Constants.password;
	String groupname = "Test_aakash";
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
	String template = "Desktop"; // Other types : Tablet-Landscape , Tablet-Portrait
	String templateName = "Ash";
	String[] fieldNames = {
			// "TextField"
			"ContainerField"
			// ,"LinkField"
			// ,"DateField"
			// ,"NumberField"
	};
	String[] defName = { "aaaaa" };
	String textValueType = "single"; // "" / "single" / "multiple"
	String containerValueType = "list"; // "" / "single" / "multiple" / "list"
	String dateValueType = "date"; // "" / "date" / "time"
	int count = 1;
	String[] valueTypeName = { "1", "2" };
	String filePath = "C:\\Users\\Kodamasimham Akash\\Downloads\\jake the dog.png";

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

	// --------------------- Create Definition -------------------------- //
			testStep="creatDefinitonAddFields";
		

		def.addDefinition(groupname, defnamevalue, channel, language);
		keys.pause(2);
		// ----------------------- Working correctly for TextField. ------------------
		def.addNewField(textfieldvalue, "Text"); // ARGS: (String fieldname , String fieldType)
		keys.pause(2); // Field Type : "Text" / "Link" / "Date" / "Number"
		def.textFieldValueType(textfieldvalue, textValueType, count, valueTypeName); // ARGS: textFieldValueType( String
																						// FieldName , String ValueType
																						// , int MulitpleFieldCount ,
																						// String [] names)
		keys.pause(4);
		def.fieldChannelAndConstraint(textfieldvalue, "Excel", "Unique"); // ARGS: fieldChannelAndConstraint(String
																			// fieldName, String channel, String
																			// constraint)
		// ---------------------- Need to work on container ----------------------
		keys.pause(2);
		def.addContainerField(containerfieldvalue, "29jan"); // ARGS: addContainerField(String
																// containerfieldvalue,String mymediafilename)
		keys.pause(2);
		def.containerFieldValueType(containerfieldvalue, containerValueType, count); // ARGS:
																						// containerFieldValueType(String
																						// fieldName, String valueType,
																						// int count)
		keys.pause(4);
		def.fieldChannelAndConstraint(containerfieldvalue, "Excel", ""); // ARGS: fieldChannelAndConstraint(String
																			// fieldName, String channel, String
																			// constraint)

		// ---------------------- Working correctly for DateField --------------------
		// //
		keys.pause(2);
		def.addNewField(datefieldvalue, "Date"); // ARGS: (String fieldname , String fieldType)
		keys.pause(2);
		def.dateFieldValueType(datefieldvalue, dateValueType); // ARGS: textFieldValueType( String FieldName , String
																// ValueType , int MulitpleFieldCount , String [] names)
		keys.pause(4);
		def.fieldChannelAndConstraint(datefieldvalue, "Excel", "Unique"); // ARGS: fieldChannelAndConstraint(String
																			// fieldName, String channel, String
																			// constraint)
		// --------------------- There are no other Value Type for Number field in
		// definiton --------------- //
		keys.pause(2);
		def.addNewField(numberfieldvalue, "Number"); // ARGS: (String fieldname , String fieldType)
		keys.pause(4);
		def.fieldChannelAndConstraint(numberfieldvalue, "Excel", "Unique"); // ARGS: fieldChannelAndConstraint(String
																			// fieldName, String channel, String
																			// constraint)
		keys.pause(2);
	

	// --------------------------- Create Template ------------------------ //
		testStep=" createTemplate";
		
		def.nav_definition("Template");
		keys.pause(2);
		def.selectTemplate(template, templateName);
		keys.pause(2);
		def.dragDefFields(fieldNames, 0, 0, 0, "Definition");
		keys.pause(2);
		def.closeDef();
	

	// ---------------------- Giving template rights ----------------------- //
		testStep=" templateRights";
		
		uf.clearSearch(driver);
		uf.search(driver,username);
		uf.select(driver,username);
		keys.pause(2);
		def.definitionRigihts(defnamevalue, true, true, true, false);
		keys.pause(2);
		def.templateRights(defnamevalue, template, templateName);
	

	// --------------------- Assign Definition to the user
	// ---------------------------- //
		testStep=" assignDefinitionTouser";
		
		keys.pause(5);
		def.assignDefintion(defName);
		keys.pause(2);

		login.logout(driver);
		keys.pause(5);
	

	// -------------------- Admin Rights to the User --------------------------- //
		testStep=" adminRightstoUser";
		
		selectmodule.navigateTasks();
		selectmodule.userRights();
		uf.adminRights(driver,groupname, username);
		keys.pause(2);
		selectmodule.logout();
		keys.pause(5);
	

	// ---------------------- Login with the User Credentials ------------------ //
	testStep="loginWithUser";
		
		login.login(driver, username, password);
		selectmodule.navigateMyPIM();
		keys.pause(2);
	

	// -------------------- Add Record to the definition ------------------------ //
		testStep=" addRecord";
		
		uf.selectDefForRecord(driver,defnamevalue);
		keys.pause(2);
		uf.makeRecord(driver);
		keys.pause(2);
		uf.editRecordTextField(driver,textfieldvalue, "", textValueType, count); // ARGS: editRecordTextField(String fieldname,
																			// String content, String valueType, int
																			// count)
		keys.pause(4);
		uf.editRecordContainerField(driver,containerfieldvalue, filePath, containerValueType, count); // ARGS:
																								// editRecordContainerField(String
																								// fieldname,String
																								// filepath,String
																								// valueType,int
																								// count)editRecordContainerField(String
																								// fieldname,String
																								// filepath,String
																								// valueType,int count)
		keys.pause(4);
		uf.editRecordDateField(driver,datefieldvalue, dateValueType, "0"); // ARGS: editRecordDateField(String fieldname,String
																	// valueType ,int date)
		keys.pause(4);
		uf.editRecordNumberField(driver,numberfieldvalue, "1234"); // ARGS: editRecordDateField(String fieldname,String
															// valueType ,int date)
		keys.pause(4);
		uf.saveRecord(driver);
		keys.pause(5);
		uf.deleteRecord(driver);
		keys.pause(2);
		login.logout(driver);
		keys.pause(5);
	

	// ------------------- Delete Method ------------------------- //
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
	// ------------------ Close Browser -------------------------- //
	@AfterClass
	public void closeBrowser() {
		login.logout(driver);
		keys.pause(5);
		driver.close();
	}

}
