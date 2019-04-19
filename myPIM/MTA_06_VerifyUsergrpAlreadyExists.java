package myPIM;

import org.openqa.selenium.By;
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
public class MTA_06_VerifyUsergrpAlreadyExists extends GetBrowserInstance {
	GetBrowserInstance browser = new GetBrowserInstance(); // Creating object for GetBrowserInstance class.
	Login login = new Login(); // Creating object for Login class.
	Keywords keys = new Keywords(); // Creating object for Keywords class.
	NavigateModule selectmodule = new NavigateModule(); // Creating object for NavigateModule class.
	Userfunctions uf = new Userfunctions(); // Creating object for Userfunctions class.
	Definition def = new Definition(); // Cr

	String User = Constants.userName;
	String pwd = Constants.password;
	String groupname = "Test_Automation";

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
	public void UsrGrpAlreadyexists() {
		
		login.login(driver, Constants.userName, Constants.password);
		keys.wait(driver, MyPIMadmin.taskpage);
		selectmodule.navigateMyPIM();
		keys.pause(12);
		// keys.wait(driver, MyPIMadmin.definitionpage);
		selectmodule.navigateAdministration();
		keys.pause(4);
		keys.wait(driver, MyPIMadmin.leftpane);
		// driver.findElement(By.xpath("(//div[@class='node-content-wrapper'])[1]")).click();
		keys.click(driver, MyPIMadmin.allUsersGroups);
		keys.pause(1);
		keys.click(driver, uf.addusergroup);
		keys.enterText(driver, uf.groupnamefield, groupname);
		keys.click(driver, uf.savebutton);
		keys.pause(1);
		keys.assertText(driver, MyPIMadmin.actualmsg, MyPIMadmin.expectedgroupCreated);
		keys.pause(3);
		keys.refresh(driver);
		keys.click(driver, MyPIMadmin.allUsersGroups);
		keys.pause(2);
		keys.click(driver, uf.addusergroup);
		keys.enterText(driver, uf.groupnamefield, groupname);
		keys.click(driver, uf.savebutton);
		keys.assertText(driver, MyPIMadmin.actualmsg, MyPIMadmin.usererrormessage);
		keys.pause(3);
		uf.search(driver,groupname);
		// Thread.sleep(2000);
		driver.findElement(By.xpath("//span[@class='node-name' and contains(text(), '" + groupname + "')]")).click();
		keys.pause(2);
		keys.click(driver, MyPIMadmin.deleteGroup);
		// Thread.sleep(2000);
		keys.click(driver, MyPIMadmin.deleteButton);
		keys.pause(1);
		keys.assertText(driver, MyPIMadmin.deleteconfirmation, MyPIMadmin.expectedgroupDeleted);
		login.logout(driver);

	}
}
