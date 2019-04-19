package myPIM;
//import java.io.File;

//import java.util.Collection;
//import java.util.Collections;
import java.util.List;

//import org.apache.poi.hssf.usermodel.HSSFWorkbook;

//import org.apache.poi.ss.usermodel.Row;

//import org.apache.poi.ss.usermodel.Sheet;

//import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import pageObjects.Login;
import pageObjects.MyPIMadmin;
import pageObjects.NavigateModule;
import pageObjects.Userfunctions;
import utils.Constants;
import utils.ExtentLoging;
import utils.ExtentReport;
import basePackage.GetBrowserInstance;
import utils.Keywords;
import utils.ReadExcel;
@Listeners(utils.ListenerTest.class)
public class MTA_10_Verify_Excel_data extends GetBrowserInstance {


	GetBrowserInstance browser = new GetBrowserInstance(); // Creating object for GetBrowserInstance class.
	Login login = new Login(); // Creating object for Login class.
	Keywords keys = new Keywords();
	NavigateModule selectmodule = new NavigateModule(); 
	Userfunctions uf = new Userfunctions();
	String User = Constants.userName;
	String pwd = Constants.password;

	ExtentLoging el = new ExtentLoging();
	ExtentReports report;
	ExtentTest logger;
	String testStep;

	
	
	ReadExcel rd = new ReadExcel();
	String FilePath = "C:\\Users\\Automation";
	String FileName = "ExportUser1518431259.xls";

	String SheetName = "Main Sheet";
	int ColNo = 0;

	String groupname = "testing@123";
	String user = "test1";
	String newpass = "test1";
	String Email = "navneetkaurm@mindfiresolutions.com";

	String user2 = "test2";
	String newpass2 = "test2";

	public By exportExcel = By.xpath("//span[@class='fa-stack fa-lg user-mgmt-icon' and @title='Export Excel']");
	public By DownloadBtn = By.xpath("//a[@class='btn btn-primary' and contains(text(),'Download')]");
	By UserGroup = By.xpath("//span[@class='node-name' and contains(text(),'" + groupname + "')]");



	@BeforeClass
	public void openBrowser() {
		driver = browser.getBrowser();

		report = ExtentReport.getInstance();
		String testCaseName= this.getClass().getName();
		logger = report.createTest(testCaseName);
	}

	@Test
	public void searchuser() throws Exception {

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

			testStep = "Add user group";
			uf.addUserGroup(driver,groupname);
			keys.pause(2);
			uf.addUser(driver,groupname, user, Email, newpass);
			uf.clearSearch(driver);
			uf.addUser(driver,groupname, user2, Email, newpass2);
			keys.pause(2);
			uf.clearSearch(driver);
			keys.pause(2);

			el.passLog(testStep, report, logger);

			testStep=" user " ;
			List<String> Userlist = uf.fetchUsers(driver,groupname);

			keys.wait(driver, MyPIMadmin.leftpane);
			uf.search(driver,groupname);
			keys.click(driver, UserGroup);
			keys.click(driver, exportExcel);
			keys.pause(10);
			keys.click(driver, DownloadBtn);

			keys.pause(2);
			List<String> ExcelUsersList = rd.readParticularColumn(FilePath, FileName, SheetName, ColNo);

			System.out.println("List are " + Userlist.equals(ExcelUsersList));
			uf.clearSearch(driver);
			uf.deleteUserGroup(driver,groupname);
			el.passLog(testStep, report, logger);

		}

		catch(Exception e)
		{	
			el.updateTestStep(testStep, report, logger, driver);
			el.sendScreenShotOnFailure(testStep, report, logger, driver);
			Assert.fail("Failed at TestStep" + testStep + e);

		}}
}
