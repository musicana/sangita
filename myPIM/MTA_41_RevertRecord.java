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
public class MTA_41_RevertRecord extends GetBrowserInstance {

	GetBrowserInstance browser = new GetBrowserInstance(); // Creating object for GetBrowserInstance class.
	Login login = new Login(); // Creating object for Login class.
	Keywords keys = new Keywords(); // Creating object for Keywords class.
	NavigateModule selectmodule = new NavigateModule(); // Creating object for NavigateModule class.
	Userfunctions uf = new Userfunctions(); // Creating object for Userfunctions class.
	Definition def = new Definition(); // Creating object for Definition class.

	// ------------------- Variables --------------------------- //
	String user = Constants.userName;
	String pwd = Constants.password;
	String groupname = "Test_akfash";
	String username = "User1234";
	String password = "1234567";
	String Email = "123@gmail.com";
	String defnamevalue = "afaaa";
	String channel = "Excel";
	String language = "Deutsch";
	String textfieldvalue = "TextFifld";
	String linkfieldvalue = "LinkFifeld";
	String containerfieldvalue = "ContaifnerField";
	String datefieldvalue = "DateFfield";
	String numberfieldvalue = "NumberfField";
	String template = "Desktop"; // Other types : Tablet-Landscape , Tablet-Portrait
	String templateName = "Asch";
	String[] fieldNames = { "TextFieldf"
			// ,"ContainerField"
			// ,"LinkField"
			, "DateField", "NumberField" };
	String[] defName = { "aaaaa" };
	String filepath = "C:\\sangy.jpg";

	String text1 = "Hello";
	String text2 = "Yellow";
	String date1 = "25";
	String date2 = "12";
	String number1 = "1234";
	String number2 = "4321";

	String[] compareFields = { text1, date1, number1 };


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
			testStep= " creatDefinitonAddFields";
		
		
	
		def.addDefinition(groupname, defnamevalue, channel, language);
		
		def.addNewField(textfieldvalue, "Text"); // Arguments for addNewField : (String fieldname , String fieldType)
		keys.pause(2); // Field Type : "Text" / "Link" / "Date" / "Number"
		el.passLog(testStep, report, logger);


		
		
		
		testStep=" Filed and channel constraints";
		
		def.fieldChannelAndConstraint(textfieldvalue, "Excel", "Unique"); // ARGS: fieldChannelAndConstraint(String
		el.passLog(testStep, report, logger);													// fieldName, String channel, String
																			// constraint)
		keys.pause(2);
		// def.addContainerField(containerfieldvalue, "3april"); // Arguments for
		// addContainerField : (String fieldname, String mediaFileName )
		// keys.pause(4);
		// def.fieldChannelAndConstraint(containerfieldvalue, "Excel", "Required");
		// keys.pause(4);
		// def.addNewField(linkfieldvalue,"Link");
		// keys.pause(2);
		
		testStep= " Add new filed 1 ";
		def.addNewField(datefieldvalue, "Date");
		keys.pause(4);
		def.fieldChannelAndConstraint(datefieldvalue, "Excel", "Unique");
		keys.pause(2);
		def.addNewField(numberfieldvalue, "Number");
		keys.pause(4);
		def.fieldChannelAndConstraint(numberfieldvalue, "Excel", "Unique");
		keys.pause(2);
	
	// ------------------ Enabling Show History ------------------- //
		testStep=" enableHistory";
		
		def.nav_definition("Options");
		keys.pause(2);
		def.optionsDefintion(false, false, false,
				true); /*
						 * ARGS: optionsDefintion( boolean ExternalReadOnly , boolean SaveDataAsImported
						 * , boolean AllowHardReturns , boolean EnableHistory )
						 */
		keys.pause(2);
	

	// -------------------- Create Template ------------------------------ //
		testStep=" createTemplate";
		
		def.nav_definition("Template");
		keys.pause(2);
		def.selectTemplate(template, templateName); // ARGS: selectTemplate ( String TemplateType , String TemplateName
													// )
		keys.pause(2);
		def.dragDefFields(fieldNames, 0, 0, 0, "Definition"); // ARGS : dragFields (String [] FieldNames , int
																// HorizontalLine , int VerticalLine , int Labels ,
																// String Type)
																// Type : "Definition" / "Link
		keys.pause(2);
		def.closeDef();
	

	// ---------------------- Giving Template rights to the Definition
	// ---------------------- //
		testStep="templateRights";
		
		uf.clearSearch(driver);
		uf.search(driver,username);
		uf.select(driver,username);
		keys.pause(2);
		def.definitionRigihts(defnamevalue, true, true, true,
				true); /*
						 * ARGS: definitionRights( String defName , boolean insertCheckbox , boolean
						 * editCheckBox , boolean deleteCheckbox , boolean historyCheckBox )
						 */
		keys.pause(2);
		def.templateRights(defnamevalue, template, templateName); // ARGS : templateRights(String defName , String
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
		selectmodule.logout();
		keys.pause(5);
	

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
		uf.editRecordTextField(driver,textfieldvalue, text1, "text", 0); // ARGS: editRecordTextField( String FieldName, String
																	// Content, String ValueType, int
																	// CountOfMultipleTextFields)
		keys.pause(2);
		// uf.editRecordContainerField(containerfieldvalue, filepath,""); // ARGS:
		// editRecordContainerField( String FieldName , String FieldPath , String
		// FieldValueType )
		// keys.pause(8);
		uf.editRecordDateField(driver,datefieldvalue, "date", date1); // ARGS: editRecordDateField( String FieldName , String
																// ValueType , int Date )
		keys.pause(2);
		uf.editRecordNumberField(driver,numberfieldvalue, number1); // ARGS: editRecordNumberField(String FieldName, int
																// Number)
		// keys.pause(2);
		uf.saveRecord(driver);
		keys.pause(2);
		uf.editRecord(driver,1);
		keys.pause(5);
		uf.editRecordTextField(driver,textfieldvalue, text2, "text", 0);
		keys.pause(2);
		// uf.editRecordContainerField(containerfieldvalue, filepath);
		// keys.pause(8);
		uf.editRecordDateField(driver,datefieldvalue, "date", date2);
		keys.pause(2);
		uf.editRecordNumberField(driver,numberfieldvalue, number2);
		// keys.pause(2);
		uf.saveRecord(driver);
		keys.pause(5);
	

	// -------------------- Revert History ------------------------ //
	testStep="revertHistory";
		
		uf.revertRecord(driver,1, 1, 1, fieldNames, compareFields); // ARGS: revertRecord( int RecordNumber, int RevertHistory
																// )
		keys.pause(5);
		keys.click(driver, uf.closeBtn);
		keys.pause(5);
		uf.deleteRecord(driver);
		keys.pause(2);
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
	@AfterTest
	public void closeBrowser() {
		
		//driver.close();
	}

}
