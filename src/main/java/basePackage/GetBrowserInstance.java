package basePackage;


import java.io.File;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import utils.Constants;



public class GetBrowserInstance  {

	public static WebDriver driver;

	//Gets browser name from Constants file for which test needs to be executed.
	String browserName = Constants.Browser;

	//Checks which browser needs to be invoked.


	public WebDriver getBrowser(){


		if(browserName.equalsIgnoreCase("chrome")){
			return chrome();
		}
		else if(browserName.equalsIgnoreCase("firefox")){
			return firefox();
		}
		else if(browserName.equalsIgnoreCase("IE")){
			return ie();
		}
		else if (browserName.equalsIgnoreCase("phantomjs")){
			return phantom();
		}
		else{
			System.out.println("Enter Correct Browser name");
			return null;
		}
	}


	private WebDriver phantom() {

		File src = new File(System.getProperty("user.dir")+"\\drivers\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
		System.setProperty("phantomjs.binary.path", src.getAbsolutePath());
		//WebDriver driver = new PhantomJSDriver();
		return driver;
	}
	//Function to launch InternetExplorer.
	private WebDriver ie() {

		return null;
	}
	//Function to launch Firefox.
	private WebDriver firefox() {

		System.setProperty("webdriver.firefox.marionette", System.getProperty("user.dir")+"\\Drivers\\geckodriver.exe");


		//	FirefoxProfile firefoxProfile = new FirefoxProfile();
		//	firefoxProfile.setPreference("browser.private.browsing.autostart",true);
		WebDriver driver = new FirefoxDriver();
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability("marionette",true);
		return driver;


	}
	//Function to launch Chrome.
	private WebDriver chrome() {

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		//	DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		options.addArguments("incognito");
		options.addArguments("disable-extensions");
		options.addArguments("--start-maximized");

		options.addArguments("disable-infobars");
		options.setCapability(ChromeOptions.CAPABILITY, options);


		options.addArguments("--verbose");
		options.addArguments("--whitelisted-ips=''");
		




		WebDriver driver = new ChromeDriver(options);

		options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,UnexpectedAlertBehaviour.IGNORE);
		return driver;



	}
	//	@BeforeClass
	//	public void openBrowser()
	//	{
	//		driver=getBrowser();
	//	}
	//
	//	@AfterClass
	//	public void close()
	//	{
	//		//driver.quit();
	//	}



}