package myPIM;

import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import pageObjects.Login;
import pageObjects.NavigateModule;
import pageObjects.Userfunctions;
import utils.Constants;
import utils.ExtentLoging;
import utils.ExtentReport;
import basePackage.GetBrowserInstance;
import utils.Keywords;
@Listeners(utils.ListenerTest.class)
public class MTA_11_ImportUserList extends GetBrowserInstance {

	GetBrowserInstance browser = new GetBrowserInstance();
	Login login = new Login();
	Keywords keys = new Keywords();
	NavigateModule selectmodule = new NavigateModule();
	Userfunctions uf = new Userfunctions();

	String groupname = "test@123";
	String FilePath = "C:\\Users\\Automation\\sangy.xls";

	ExtentLoging el = new ExtentLoging();
	ExtentReports report;
	ExtentTest logger;
	String testStep;
	String User = Constants.userName;
	String pwd = Constants.password;


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
			login.login(driver, User, pwd);
			el.passLog(testStep, report, logger);


			testStep="Navigate to my pim";
			selectmodule.navigateMyPIM();

			keys.pause(4);
			// keys.wait(driver, MyPIMadmin.definitionpage);
			selectmodule.navigateAdministration();
			Thread.sleep(4000);
			el.passLog(testStep, report, logger);



			uf.addUserGroup(driver,groupname);
			uf.search(driver,groupname);
			uf.select(driver,groupname);
			uf.importUserExcel(driver);
			driver.findElement(By.xpath("//input[@class='default-file-upload' ]")).sendKeys(FilePath);
			keys.pause(4);
			driver.findElement(By.xpath("(//i[@class='fa fa-upload'])[2]")).click();
			
			keys.pause(2);
			WebElement From1 = driver.findElement(By.xpath("//div[text()='User Name']"));
			WebElement To1 = driver
					.findElement(By.xpath("//label[contains(text(),'username')]/parent::div/following-sibling::div"));
			WebElement From2 = driver.findElement(By.xpath("//div[text()='Email Address']"));
			WebElement To2 = driver
					.findElement(By.xpath("//label[contains(text(),'email')]/parent::div/following-sibling::div"));
			WebElement From3 = driver.findElement(By.xpath("//div[text()='Name']"));
			WebElement To3 = driver
					.findElement(By.xpath("//label[contains(text(),'passwd')]/parent::div/following-sibling::div"));
			Actions Act = new Actions(driver);
			keys.pause(2);
			Action dragAndDrop = Act.clickAndHold(From1).moveToElement(To1).release(To1).build();
			keys.pause(2);
			dragAndDrop.perform();
			keys.pause(2);
			Action dragAndDrop1 = Act.clickAndHold(From2).moveToElement(To2).release(To2).build();
			keys.pause(2);
			dragAndDrop1.perform();
			keys.pause(2);
			Action dragAndDrop2 = Act.clickAndHold(From3).moveToElement(To3).release(To3).build();
			keys.pause(2);
			dragAndDrop2.perform();
			keys.pause(2);
			driver.findElement(By.xpath("//button[text()='Save']")).click();


		}
		catch(Exception e)
		{	
			el.updateTestStep(testStep, report, logger, driver);
			el.sendScreenShotOnFailure(testStep, report, logger, driver);
			Assert.fail("Failed at TestStep" + testStep + e);

		}
	}
}