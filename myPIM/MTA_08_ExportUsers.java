package myPIM;

import org.openqa.selenium.By;
import org.testng.Assert;
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
public class MTA_08_ExportUsers extends GetBrowserInstance {
	GetBrowserInstance browser = new GetBrowserInstance(); // Creating object for GetBrowserInstance class.
	Login login = new Login(); // Creating object for Login class.
	Keywords keys = new Keywords(); // Creating object for Keywords class.
	NavigateModule selectmodule = new NavigateModule(); // Creating object for NavigateModule class.
	Userfunctions uf = new Userfunctions(); // Creating object for Userfunctions class.
	Definition def = new Definition(); // Cr


	String User = Constants.userName;
	String pwd = Constants.password;
	String username = "sy2";
	String password = "ww";
	String invaliduser = "bchjsbdcbdfewfqqw";
	String invalidcharacters = "!@#%$#%$%^%^&^*&$*";

	ExtentLoging el = new ExtentLoging();
	ExtentReports report;
	ExtentTest logger;
	String testStep;

	// -------------------------------------
	public By allUsersGroups = By.xpath("//span[@class='node-name' and contains(text(),'All users and user group')]");
	public By addusergroup = By.xpath("//div[@id='panel-usg']/administration-user-mngnt/div/div/span");

	public By exportExcel = By.xpath("//span[@class='fa-stack fa-lg user-mgmt-icon' and @title='Export Excel']");
	public By DownloadBtn = By.xpath("//a[text()='Download']");
	public By userSearch = By.xpath("//span[@class='clear-icon fa fa-times']");

	String groupname = "testing@123";
	String user = "test1";
	String newpass = "test1";
	String Email = "navneetkaurm@mindfiresolutions.com";

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
	
	
	
		testStep = " Add user group";
	
		uf.addUserGroup(driver,groupname);
		uf.addUser(driver,groupname, username, Email, password);
		uf.clearSearch(driver);
		uf.search(driver, groupname);
		uf.select(driver, groupname);
		el.passLog(testStep, report, logger);
		
		
		testStep = " export";
		keys.click(driver, exportExcel);
		keys.pause(10);
		keys.click(driver, DownloadBtn);
		
		testStep = " download";
		keys.pause(3);
		keys.downloadedFile(driver, Constants.Browser);
		el.passLog(testStep, report, logger);
		
		// login.logout(driver);

		}
		catch(Exception e)
		{	
			el.updateTestStep(testStep, report, logger, driver);
			el.sendScreenShotOnFailure(testStep, report, logger, driver);
			Assert.fail("Failed at TestStep" + testStep + e);

		}
	}
}
