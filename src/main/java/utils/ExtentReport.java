package utils;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
public class ExtentReport {

	private static ExtentReports report = null;

	private ExtentReport() {

	}

	// initialize the report and return it to the classes
	public static ExtentReports getInstance() {

		//so that threads are executed one by one
		synchronized(ExtentReports.class) {
			if (report == null) {
				ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(ListenerTest.path);
				String configFileDir = System.getProperty("user.dir");
				htmlReporter.loadXMLConfig(new File(configFileDir+"\\config.xml"));
				htmlReporter.setAppendExisting(true);
				htmlReporter.config().setChartVisibilityOnOpen(true);
				htmlReporter.config().setDocumentTitle("AutomationTesting.in Demo Report");
				htmlReporter.config().setReportName("JointMedia Automation testing Report");
				htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
				htmlReporter.config().setTheme(Theme.DARK);
				htmlReporter.config().setEncoding("utf-8");
				
				report = new ExtentReports();
				report.attachReporter(htmlReporter);
				report.setSystemInfo("OS", System.getProperty("os.name"));
				report.setSystemInfo("Laptop Model", "Lenovo Thinkpad");
				report.setSystemInfo("Host Name", "Mindfire");
				report.setSystemInfo("User Name", "Sangeeta Yadav");
				report.setSystemInfo("Environment", "QA");

			}

			
			return report;
		}
	}
}
