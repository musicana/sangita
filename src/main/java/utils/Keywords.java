package utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Keywords {

	@SuppressWarnings("unused")
	private static final String WebElement = null;
	WebDriver driver;
	Actions action;
	Robot robot;

	String downloadpathChrome = "C:\\Users\\Sangeeta Yadav\\Downloads";

	/**
	 * Method to check if actual contains expected.
	 * @param driver
	 * @param actual, type: By
	 * @param expected, type: String
	 */
	public void assertText(WebDriver driver, By actual, String expected){

		String Actual = driver.findElement(actual).getText();
		Actual.contains(expected);
		System.out.println("it  is expected");
	}

	public void assertText(WebDriver driver, String actual, String expected){
		actual.contentEquals(expected);
		System.out.println("Expected text is shown");
	}

	//Method to hover over an element
	public void hover(WebDriver driver , By locator) {

		Actions    action = new Actions(driver);
		WebElement we     = driver.findElement(locator);
		action.moveToElement(we).perform();
		//action.click().build().perform();
	}

	//Method to clear text in any text field
	public void clearText(WebDriver driver, By element) {
		driver.findElement(element).clear();
	}


	/*
	 *This functions use to click an element.
	 *This function directly click.
	 *Parameter which we need to pass is web driver and a locator type By.
	 */
	public void click(WebDriver driver, By element)
	{
		waitVisible(driver, element);
		driver.findElement(element).click();

	}


	//Method to compare to list values
	public void compareListvalues(List<String> list1, List<String> list2)
	{

		System.out.println(list1.containsAll(list2));
	}


	public void containsText(WebDriver driver, By actual, String expected)
	{
		String actualText = driver.findElement(actual).getText();
		Assert.assertTrue(actualText.contains(expected));;	
	}


	public void containsText(WebDriver driver, String actual, String expected)
	{
		actual.contains(expected);
	}

	// Method to double Click on any WebElement
	public void doubleClick(WebDriver driver, By element) {
		WebElement doubleClick = driver.findElement(element);

		Actions act = new Actions(driver);
		act.doubleClick(doubleClick).perform();

	}

	//@SuppressWarnings("deprecation")
	public String downloadedFile(WebDriver driver, String browser) {
		String fileName;
		if(browser.matches("chrome")){
			File getLatestFile = getLatestFilefromDir(downloadpathChrome);
			fileName = getLatestFile.getName();
			Assert.assertTrue(fileName.startsWith("ExportUser"));
			System.out.println(fileName);
			return fileName;
		}
		else {
			System.out.println("Not handled for chrome yet");
			return "nothing";
		}

	}

	//----------------drag and drop -------------------

	public void dragAndDropElement(WebElement dragFrom, WebElement dragTo ,int xOffset) throws Exception {

		Robot robot = new Robot();
		robot.setAutoDelay(50);

		//Fullscreen page so selenium coordinates work
		//	        robot.keyPress(KeyEvent.VK_F11);
		pause(2);

		//Get size of elements
		Dimension fromSize = dragFrom.getSize();
		Dimension toSize = dragTo.getSize();
		//
		//	        //Get centre distance
		int xCentreFrom = fromSize.width / 2;
		int yCentreFrom = fromSize.height / 2;
		int xCentreTo = toSize.width / 2;
		int yCentreTo = toSize.height / 2;

		//Get x and y of WebElement to drag to
		Point toLocation = dragTo.getLocation();
		Point fromLocation = dragFrom.getLocation();

		//Make Mouse coordinate centre of element
		toLocation.x += xOffset + xCentreTo;
		toLocation.y += yCentreTo;
		fromLocation.x += xCentreFrom;
		fromLocation.y += yCentreFrom;

		//Move mouse to drag from location
		robot.mouseMove(fromLocation.x, fromLocation.y+100);

		//Click and drag
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		pause(2);

		//Drag events require more than one movement to register
		//Just appearing at destination doesn't work so move halfway first
		robot.mouseMove(((toLocation.x - fromLocation.x) / 2) + fromLocation.x, ((toLocation.y
				- fromLocation.y) / 2) + fromLocation.y);

		//Move to final position
		robot.mouseMove(toLocation.x+100, toLocation.y);
		pause(2);

		//Drop
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}

	public static void dragAndDropByLocator(WebDriver driver, By cat1, By cat2)  {
		WebElement element = driver.findElement(By.xpath(".//span[text()='cat1']/..")); 
		WebElement target = driver.findElement(By.xpath(".//span[text()='cat2']"));
		//element.click();

		/* Actions act1 = new Actions(driver);
		  act1.moveToElement(element);
		  act1.build().perform();*/

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		Actions act = new Actions(driver);
		/*act.dragAndDrop(element, target);*/
		act.clickAndHold(element);
		act.moveToElement(target, 0, +120);
		act.release(target);
		act.build().perform();
	}


	public void dragAndDropUsingRobot(WebDriver driver, By locator1,By locator2) throws AWTException {

		Robot robot = new Robot();
		System.out.println(driver.findElement(locator1).getLocation());
		System.out.println(driver.findElement(locator2).getLocation());

		robot.mouseMove(550,350);
		pause(2);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		pause(2);
		robot.mouseMove(550,400);
		pause(2);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		pause(5);

	}


	public void dragAndDropTemplateDecorator(WebElement dragFrom, WebElement dragTo ,int xOffset ,int yOffset) throws Exception {

		Robot robot = new Robot();
		robot.setAutoDelay(50);

		//Fullscreen page so selenium coordinates work
		//        robot.keyPress(KeyEvent.VK_F11);
		pause(2);

		//Get size of elements
		Dimension fromSize = dragFrom.getSize();
		Dimension toSize = dragTo.getSize();
		//
		//        //Get centre distance
		int xCentreFrom = fromSize.width / 2;
		int yCentreFrom = fromSize.height / 2;
		int xCentreTo = toSize.width / 2;
		int yCentreTo = toSize.height / 2;

		//Get x and y of WebElement to drag to
		Point toLocation = dragTo.getLocation();
		Point fromLocation = dragFrom.getLocation();

		//Make Mouse coordinate centre of element
		toLocation.x += xOffset + xCentreTo;
		toLocation.y +=yCentreTo;
		fromLocation.x += xCentreFrom;
		fromLocation.y += yCentreFrom;

		//Move mouse to drag from location
		robot.mouseMove(fromLocation.x, fromLocation.y+100);

		//Click and drag
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		pause(2);

		//Drag events require more than one movement to register
		//Just appearing at destination doesn't work so move halfway first
		robot.mouseMove(((toLocation.x - fromLocation.x) / 2) + fromLocation.x, ((toLocation.y
				- fromLocation.y) / 2) + fromLocation.y);

		//Move to final position
		robot.mouseMove(toLocation.x+100, toLocation.y + yOffset);
		pause(2);

		//Drop
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}


	//Method to enter text in any text field.
	public void enterText(WebDriver driver, By element , String text)
	{

		waitVisible(driver, element);
		driver.findElement(element).sendKeys(text);

	}

	private File getLatestFilefromDir(String downloadpathFirefox2) {

		File dir = new File(downloadpathChrome);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return null;
		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];
			}
		}
		return lastModifiedFile;
	}





	/**
	 * Method to match actual and expected text.
	 * @param driver
	 * @param actual, type: By
	 * @param expected, type: By
	 */
	public void matchtext(WebDriver driver, By actual, By expected){
		String Actual = driver.findElement(actual).getText();
		String Expected = driver.findElement(expected).getText();
		Actual.matches(Expected);
	}

	//Navigate to given URL.
	public void navigate(WebDriver driver, String URL)
	{
		driver.get(URL);
	}

	//Method to pause the execution flow.
	public void pause(int i){
		try {
			i*=1000;
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	//Function to refresh current page.
	public void refresh(WebDriver driver) {
		driver.navigate().refresh();
	}

	//Function to generate random dummy text.
	public String textgenerator(int x){
		Long.toHexString(Double.doubleToLongBits(Math.random())); 
		UUID.randomUUID().toString(); 
		RandomStringUtils.randomAlphabetic(x);
		return toString();
	}

	public void textnotPresent(WebDriver driver, By actual, String expected){
		String Actual = driver.findElement(actual).getText();
		if(Actual.isEmpty()){
			System.out.println(expected+" donot matches with "+actual);
		}
	}


	//Methods for explicit wait.

	public void wait(WebDriver driver, By element)
	{
		WebDriverWait wait = new WebDriverWait(driver, 80);
		wait.until(ExpectedConditions.visibilityOfElementLocated(element));
	}



	public void waitClickable(WebDriver driver, By element)
	{

		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(element)));

	}


	public void waitVisible(WebDriver driver, By element)
	{

		WebDriverWait wait = new WebDriverWait(driver, 80);
		wait.until(ExpectedConditions.visibilityOfElementLocated(element));
	
	}

	public static void dragAndDrop(WebDriver driver2, By cat1, By cat2) {
		// TODO Auto-generated method stub

	}
	public static void dragUsingJSExec(WebDriver driver, String xPath, String attribute, String value ) throws Exception{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath(xPath));
		js.executeScript("arguments[0].setAttribute('"+attribute+"', '"+value+"')",element);
	}


	WebElement element = null;


	/*
	 *This functions open the URL which you pass as
	 *a parameter.We need to pass driver and url as a string
	 */
	public void getUrl(WebDriver driver ,String URL){
		driver.get(URL);
	}


	/*
	 *This functions use to find a attribute value which is present in DOM.
	 *It returns a String value
	 */
	public String getAttributeValue(WebDriver driver, By locator, String attributeKey) {
		return driver.findElement(locator).getAttribute(attributeKey);
	}



	/*
	 *This functions use to verify the downloaded file is present or not.
	 *It returns a boolean value as true or false.
	 *We need to provide a file dir path as a parameter
	 *And if file is present are deleting the same file
	 */
	public boolean isFileDownloaded(String downloadPath, String fileName) {
		File dir = new File(downloadPath);
		File[] dirContents = dir.listFiles();

		for (int i = 0; i < dirContents.length; i++) {
			System.out.println(downloadPath);
			System.out.println(dirContents[i].getName());
			if (dirContents[i].getName().equals(fileName)) {

				// File has been found, it can now be deleted:
				dirContents[i].delete();

				return true;
			}
		}
		return false;
	}


	/*
	 *This functions use to click an element.
	 *This function wait first for elementy to be clickable
	 *Parameter which we need to pass is web driver and a locator type By
	 */
	public void waitClick(WebDriver driver ,By locator){
		waitElementUntilClickable(driver, locator);
		driver.findElement(locator).click();
	}



	/*
	 *This functions use to click an element.
	 *This function click element by java script executor.
	 *Parameter which we need to pass is web driver and a locator type By.
	 */
	public void clickByjs(WebDriver driver,By locator) {
		WebElement ele = driver.findElement(locator);
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		exe.executeScript("arguments[0].click();", ele);
	}



	/*
	 *This functions use to clear an element/field.
	 *Parameter which we need to pass is web driver and a locator type By.
	 */
	public void findElementClear(WebDriver driver ,By locator){
		waitElementUntilDisplayed(driver, locator);
		driver.findElement(locator).clear();
	}



	/*
	 *This functions use to send a string data an element/field.
	 *This function will wait for element to be displayed
	 *Parameter which we need to pass is web driver and a locator type By.
	 */
	public void findElementSendKey(WebDriver driver ,By locator , String sendkeyData){//==Find element and send keys====>
		waitElementUntilDisplayed(driver, locator);
		driver.findElement(locator).sendKeys(sendkeyData);
	}


	/*
	 *This functions use to send a Keyboad keys an element/field.
	 *This function will wait for element to be displayed
	 *Parameter which we need to pass is web driver and  Keys.
	 */
	public void findElementSendKey(WebDriver driver, By locator, Keys keys) {	
		waitElementUntilDisplayed(driver, locator);
		driver.findElement(locator).sendKeys(keys);
	}



	/*
	 *This functions use to find an element in DOM.
	 *And return a webelement.
	 */
	public WebElement findEelement(WebDriver driver ,By locator){
		waitElementUntilDisplayed(driver, locator);
		return	driver.findElement(locator);
	}



	/*
	 *This functions use to find an element in DOM.
	 *If element is present in DOM it will return true
	 *otherwise return false
	 */
	public  boolean isElementPresent(WebDriver driver ,By locator){
		try {
			waitElementUntilDisplayed(driver, locator);
			return true;
		}catch(Exception e) {
			return false;
		}

	}


	/*
	 *This functions use to find an element in DOM.
	 *If element is present in DOM it will retur true
	 *otherwise return false
	 *The difference is you can pass time as a parameter
	 *i.e how much time you want to wait
	 */
	public  boolean isElementPresent(WebDriver driver ,By locator,int timmer){
		try {
			waitElementUntilDisplayed(driver, locator,timmer);
			return true;
		}catch(Exception e) {
			//System.out.println(locator + " Not found");
			System.out.println(e);
			return false;
		}

	}


	/*
	 *This functions use to find an element and gets its innerHTML string value
	 *And return as a string value
	 */
	public String getText(WebDriver driver, By locator){
		waitElementUntilDisplayed(driver, locator);
		return	driver.findElement(locator).getText();	
	}

	//Function to get text from web element.
	public String getText1(WebDriver driver, By locator){
		waitElementUntilDisplayed(driver, locator);
		String elementText = driver.findElement(locator).getText();
		return elementText;

	}

	public int findElementsCount(WebDriver driver, By locator) {
		List<WebElement> listOfElement = driver.findElements(locator);
		return listOfElement.size();
	}

	public void waitElementUntilDisplayed(WebDriver driver ,By locator){//===Wait for element until displayed==>
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public void waitElementUntilDisplayed(WebDriver driver ,By locator, int timer){//===Wait for element until displayed==>
		WebDriverWait wait = new WebDriverWait(driver,timer);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}


	public void waitElementUntilClickable(WebDriver driver ,By locator){//===Wait for element until displayed==>
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public void waitUntillInvisible(WebDriver driver,By locator){
		WebDriverWait wait = new WebDriverWait(driver,120);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	public void waitUntillInvisible(WebDriver driver,By locator,int timmer){
		WebDriverWait wait = new WebDriverWait(driver,timmer);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	public void waitUntillElementCount(WebDriver driver,By locator,int number){
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.numberOfElementsToBe(locator, number));
	}


	//================Switch to Frames via String/Integer/default======>
	public void SwitchToframe(WebDriver driver,String frame){
		driver.switchTo().frame(frame);
	}
	public void SwitchToframe(WebDriver driver, int  frame){
		driver.switchTo().frame(frame);
	}
	public void switchToDefault(WebDriver driver){
		driver.switchTo().defaultContent();
	}


	//===================Scroll functions=============================================>
	public void scrollDownScreen(WebDriver driver){
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("window.scrollBy(0,500)", "");
	}

	public void scrollBottomOfthePage(WebDriver driver)
	{
		((JavascriptExecutor) driver)
		.executeScript("window.scrollTo(0, document.body.scrollHeight)");

	}

	public void scrollUpScreen(WebDriver driver){
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("window.scrollBy(0,-500)", "");
	}

	public void ScrollToElementLocation(WebDriver driver,By locator){	
		String location =driver.findElement(locator).getLocation().toString();
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("window.scrollTo"+location+"", "");
	}
	//================================================================>

	public void threadsleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void falseFailure(WebDriver driver){
		//this is only for failing condition 
		driver.findElement(By.xpath(".//input[just an error]"));
	}

	public void falseFailure(WebDriver driver,String testStep){
		//this is only for failing condition 
		System.out.println("This is failed by false failure in testStep-->"+testStep);
		driver.findElement(By.xpath(".//input[just an error]"));
	}

	public String getAttributeByJse(WebDriver driver, By locator, String attributeName){

		JavascriptExecutor je = (JavascriptExecutor) driver;
		String attribute = je.executeScript("return arguments[0].getAttribute('" + attributeName + "')",driver.findElement(locator)).toString();
		return attribute;

	}

	public void setAttributeByJse(WebDriver driver, By locator, String AttributeName,String AttributeValue ){	
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("arguments[0].setAttribute('" + AttributeName + "','" + AttributeValue + "')",driver.findElement(locator));

	}

	public String getElementPropertyByJse(WebDriver driver, By locator, String propertyName){

		JavascriptExecutor je = (JavascriptExecutor) driver;
		String attribute = je.executeScript("return arguments[0]."+ propertyName,driver.findElement(locator)).toString();
		return attribute;

	}


	//--------------------------Wait for elements until displayed-------------------------------//
	public void waitDisplayed(WebDriver driver ,By locator, int count){

		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.numberOfElementsToBe(locator,count));
	}
	public void uploadFileByRobotAfterClickBrowseButton(WebDriver driver, String filePath) throws AWTException{
		StringSelection ss = new StringSelection(filePath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		// imitate mouse events like ENTER, CTRL+C, CTRL+V
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	public void uploadFile(WebDriver driver, String path) {
		String fileName;
		if(path.matches(".MP4")){

		}
		else {
			System.out.println("Only mp4 and .Mov files are supoorted");

		}

	}

	public void clickByAction(WebDriver driver , By locator) {

		Actions    action = new Actions(driver);
		WebElement we     = driver.findElement(locator);
		action.moveToElement(we).perform();
		action.click().build().perform();
	}


	public void sendKeysByAction(WebDriver driver, By locator, String data){
		Actions    action = new Actions(driver);
		WebElement we     = driver.findElement(locator);
		action.moveToElement(we);
		action.click();
		action.sendKeys(data);
		action.build().perform();

	}



	/*This function should be used to select dropdown
	 * following arguments are required.
	 * @elementIdentifier: Type of element we require to wait
	 * @elementValue: Value of that element
	 * @dropdownType: Dropdown argument
	 * @dropDownValue: value to select
	 */	

	//---------------------- Dropdown--------------------------//
	public void selectFromDropDown(WebDriver driver, By locator, String dropdownType, String dropDownValue)
	{	

		WebElement element = findEelement(driver, locator);
		Select dropdown = new Select(element);
		//select based on the type
		if(dropdownType.equalsIgnoreCase("Text"))
			dropdown.selectByVisibleText(dropDownValue);
		else if(dropdownType.equalsIgnoreCase("Value"))
			dropdown.selectByValue(dropDownValue);
		else if(dropdownType.equalsIgnoreCase("Index"))			
			dropdown.selectByIndex(Integer.parseInt(dropDownValue));
	}

	//---------------------Accept Alert --------------------------//

	public void acceptAlert(WebDriver driver){
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}


	public void printCaseInfo(String packageName, String release, String environment, String requirement, String testCase, int i){
		System.out.println("############## TESTDATA INFO ################");
		System.out.println("TestData Line running: " + i);
		System.out.println("TestCase running: " + testCase);
		System.out.println("Package: " + packageName);
		System.out.println("Release: " + release);
		System.out.println("Environment: " + environment);
		System.out.println("Requirement: " + requirement);
		System.out.println("#############################################");
	}

	public String getDate(int daysToAdd, String formatDate) throws ParseException{
		String timeStamp = new SimpleDateFormat(formatDate).format(Calendar.getInstance().getTime());
		return timeStamp;
	}

	public String getHour(int hoursToAdd, String format, int yearsToAdd, int daysToAdd) throws ParseException{
		Calendar cal = Calendar.getInstance(); // creates calendar
		cal.setTime(new Date()); // sets calendar time/date
		cal.add(Calendar.HOUR_OF_DAY, hoursToAdd); // adds one hour
		cal.add(Calendar.YEAR, yearsToAdd);
		cal.add(Calendar.DAY_OF_MONTH, daysToAdd);
		Date hour = cal.getTime();
		DateFormat df = new SimpleDateFormat(format);
		String reportDate = df.format(hour);
		return  reportDate;
	}

	public void waitUntilPageLoad(final WebDriver driver){
		WebDriverWait wait1 = new WebDriverWait(driver, 90);
		wait1.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver wdriver) {
				return ((JavascriptExecutor) driver).executeScript(
						"return document.readyState"
						).equals("complete");
			}
		});
	}

	public void dismissAlert(WebDriver driver){

		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	public String isAlertPresent(WebDriver driver) {

		String presentFlag = "";

		try {

			// Check the presence of alert
			Alert alert = driver.switchTo().alert();
			// Alert present; set the flag
			presentFlag = "TRUE";
			// if present consume the alert
			alert.accept();
			//( Now, click on ok or cancel button )

		} catch (NoAlertPresentException ex) {
			// Alert not present
			ex.printStackTrace();
		}
		System.out.println("ALERT");
		return presentFlag;
	}

	public List<WebElement> findElements(WebDriver driver, By locator) {//==Find elements and return List<WebElement>==>
		waitElementUntilDisplayed(driver, locator);
		List<WebElement> listOfElement = driver.findElements(locator);
		return listOfElement;
	}

	
	public List<WebElement> findElements1(WebDriver driver, By locator,String Name) {
	List<WebElement> myElements =driver.findElements(By.cssSelector("li[class*='rlbItem']"));

	for(WebElement li : myElements) 
	{
	     System.out.println(li.getText());
	     System.out.println();

	     for (int i=0; i<Name.length(); i++)
	     {
	        if(li.getText().equalsIgnoreCase(Name))
	        {
	            //Clicks on the matched webelement    
	            li.click();
	        }
	      }
	}
	return myElements;
	}
	


	/*This function will take format type like "dd/mm/yy" etc and the expected future date
	 * as input and will return the date in a desired format as output
	 * */
	public static String getFutureDate(WebDriver driver, String format, int futureDays){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
		LocalDate localDate = LocalDate.now();  
		return dtf.format(localDate.plusDays(futureDays));
	}

	/*This function will take format type like "dd/mm/yy" etc and the expected Past
	 * as input and will return the date in a desired format as output
	 * */
	public String getPastDate(WebDriver driver, String format, int futureDays){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
		LocalDate localDate = LocalDate.now();
		return dtf.format(localDate.minusDays(futureDays));
	}

	public void waitForWindow(WebDriver driver)
			throws InterruptedException {
		//wait until number of window handles become 2 or until 6 seconds are completed.
		int timecount = 1;
		do {
			driver.getWindowHandles();
			Thread.sleep(200);
			timecount++;
			if (timecount > 30) {
				break;
			}
		} while (driver.getWindowHandles().size() != 2);

	}

	public void switchToModalDialog(WebDriver driver, String parent) { 
		//Switch to Modal dialog
		if (driver.getWindowHandles().size() == 2) {
			for (String window : driver.getWindowHandles()) {
				if (!window.equals(parent)) {
					driver.switchTo().window(window);
					System.out.println("Modal dialog found");
					break;
				}
			}
		}
	}
}
