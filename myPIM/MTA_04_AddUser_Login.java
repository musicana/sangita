package myPIM;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import basePackage.GetBrowserInstance;
import pageObjects.Definition;
import pageObjects.Login;
import pageObjects.NavigateModule;
import pageObjects.Userfunctions;
import utils.Constants;
import utils.ExtentLoging;
import utils.ExtentReport;
import utils.Keywords;
@Listeners(utils.ListenerTest.class)
public class MTA_04_AddUser_Login extends GetBrowserInstance {


	GetBrowserInstance browser = new GetBrowserInstance(); // Creating object for GetBrowserInstance class.
	Login login = new Login(); // Creating object for Login class.
	Keywords keys = new Keywords(); // Creating object for Keywords class.
	NavigateModule selectmodule = new NavigateModule(); // Creating object for NavigateModule class.
	Userfunctions uf = new Userfunctions(); // Creating object for Userfunctions class.
	Definition def = new Definition(); // Cr


	// ----------------------------------------------------

	public By searchuser = By
			.xpath("//div[@id='panel-usg']/shared-user-and-group-tree/div[2]/tree-root/tree-viewport/div");
	public By closeBtn = By.xpath("//button[@class='btn btn-secondary' and contains(text(),'Close')]");
	public By myPIMicon = By.xpath("//div[@class='nav-option-active-overlay']/span");
	public By myTasksicon = By.xpath("//div[@class='menu-item mytasks']");
	public By ShowNavigation = By.xpath("//div[@class='navigation-bar-display-controller-button']");
	public By PortalMenuicon = By.xpath("//img[@class='portal2-menu-icon']");
	public By myWorkflowAdmin = By.xpath(
			"//a[@class='button-dropdown-item-11px-ffffff main_menu_item']/span[contains(text(),'myWorkflow� administration')]");
	public By searchuserField = By.xpath(".//input[@id='vit2print_search']");
	public By Username = By.xpath("//span[contains(text(),'test1')]");


	String groupname = "testing@123";
	String username = "test1";
	String password = "ww";
	String newpass = "mindfire";
	String Email = "er.sangitayadav@gmail.com";

	String User = Constants.userName;
	String pwd = Constants.password;
	String invaliduser = "bchjsbdcbdfewfqqw";
	String invalidcharacters = "!@#%$#%$%^%^&^*&$*";

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
			login.login(driver, User, pwd);
			el.passLog(testStep, report, logger);


			testStep="Navigate to my pim";
			selectmodule.navigateMyPIM();

			keys.pause(4);
			// keys.wait(driver, MyPIMadmin.definitionpage);
			selectmodule.navigateAdministration();
			Thread.sleep(4000);
			el.passLog(testStep, report, logger);




			testStep="Add user group " ;
			uf.addUserGroup(driver,groupname);
			keys.pause(2);
			uf.addUser(driver,groupname, username, Email, password);

			uf.clearSearch(driver);
			keys.pause(2);
			
			el.passLog(testStep, report, logger);


			testStep= "delete User";
		
			uf.deleteUserGroup(driver,groupname);
			// keys.click(driver, closeBtn);
			// Thread.sleep(10000);
			el.passLog(testStep, report, logger);
			//		List<WebElement> myList = driver.findElements(By.className("deletion-user-name"));
			//		for (int i = 0; i < myList.size(); i++) {
			//			System.out.println(myList.get(i).getText());

		}
		catch(Exception e)
		{	
			el.updateTestStep(testStep, report, logger, driver);
			el.sendScreenShotOnFailure(testStep, report, logger, driver);
			Assert.fail("Failed at TestStep" + testStep + e);

		}
	}
}


// WebElement element =
// driver.findElement(By.xpath("//ngb-modal-window//form//button"));
// System.out.println(element.isEnabled());
// element.click();
// System.out.println();
// keys.assertText(driver, Username, newuser);
// System.out.println(driver.findElement(By.xpath("//span[contains(text(),'test1')]")).getText());

// WebElement
// button=driver.findElement(By.xpath("//ngb-modal-window//span[contains(text(),'test1')]/ancestor::tr/descendant::button"));
// System.out.println(button.isEnabled());
// driver.findElement(By.xpath("//ngb-modal-window//span[contains(text(),'test1')]/ancestor::tr/descendant::span[1]")).click();
// driver.findElement(By.xpath("//span[contains(text(),'test1')]/ancestor::tr/descendant::span[1]")).click();
// keys.enterText(driver, searchuserField, newuser);



