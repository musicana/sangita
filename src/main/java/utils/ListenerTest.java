package utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import basePackage.GetBrowserInstance;

public class ListenerTest implements ITestListener {
		WebDriver driver;
		GetBrowserInstance browser = new GetBrowserInstance();
		ExtentLoging el = new ExtentLoging();
		static File file;
		static SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy,HH.mm.ss");//dd/MM/yyyy
	    static Date now = new Date();
	    static String strDate = sdfDate.format(now);
	    static String filePath = System.getProperty("user.dir");
	    static String path= filePath+"\\Report\\Report-"+strDate+".html";
	    
	     

		public void onTestStart(ITestResult result) {
		
			//System.out.println("Test case started and details are:" + result.getName());
			System.out.println(result.getName()+" test case started");
		}

		public void onTestSuccess(ITestResult result) {

			System.out.println("Test case passed and details are:" + result.getName());
		}


				
		    public void onTestFailure(ITestResult result) 					
		    {		
		    System.out.println("The name of the testcase failed is :"+result.getName());					
		    }		
		

		public void onTestSkipped(ITestResult result) {

			System.out.println("Test case skipped and details are:" + result.getName());
		}

		public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

			System.out.println("Test case failed but within success percentage:" + result.getName());

		}

		public void onStart(ITestContext context) {

			System.out.println("Starting the execution of the test cases");
		     file=new File(path);
		     System.out.println(filePath);
		}

		public void onFinish(ITestContext context) {
			System.out.println("All the test cases have been executed");
//			driver=browser.openLocalBowser();
//			driver.get(path);
			//EmailReport.sendmail();
			//System.out.println("Email of report sent");
		}

}
