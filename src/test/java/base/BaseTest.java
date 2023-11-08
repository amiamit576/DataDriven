package base;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.DbManager;
import utilities.ExcelReader;


public class BaseTest {
	
	
	/*
	 * webdriver
	 * testng
	 * screenshot
	 * log4j
	 * Propertes
	 * Database 
	 * JavaMail,
	 * Extent
	 * listener
	 * Excel
	 * 
	 */
	public static WebDriver driver;
	//public static Logger log=Logger.getLogger(BaseTest.class.getName());
	public static Properties OR=new Properties();
	public static Properties Config=new Properties();
	public static FileInputStream fis;
	public static ExcelReader excel =new ExcelReader(".\\src\\test\\resources\\excel\\LoginTest.xlsx ");
	
	

	public static void click(String key) {

		if (key.endsWith("_XPATH")) {

			driver.findElement(By.xpath(OR.getProperty(key))).click();
		} else if (key.endsWith("_ID")) {

			driver.findElement(By.id(OR.getProperty(key))).click();
		} else if (key.endsWith("_CSS")) {

			driver.findElement(By.cssSelector(OR.getProperty(key))).click();
		}

		//log.info("Clicking on an Element: " + key);

	}

	
	public static boolean isElementPresent(String key) {

		try {
		if (key.endsWith("_XPATH")) {

			driver.findElement(By.xpath(OR.getProperty(key)));
		} else if (key.endsWith("_ID")) {

			driver.findElement(By.id(OR.getProperty(key)));
		} else if (key.endsWith("_CSS")) {

			driver.findElement(By.cssSelector(OR.getProperty(key)));
		}
		//log.info("Finding an Element: " + key);
		return true;
		}catch(Throwable t) {
			//log.info("Error while finding an Element: " + key+"--Error log is :"+t.getMessage());
			return false;
		}

		

	}
	
	
	public static void type(String key, String value) {

		if (key.endsWith("_XPATH")) {

			driver.findElement(By.xpath(OR.getProperty(key))).sendKeys(value);
		} else if (key.endsWith("_ID")) {

			driver.findElement(By.id(OR.getProperty(key))).sendKeys(value);;
		} else if (key.endsWith("_CSS")) {

			driver.findElement(By.cssSelector(OR.getProperty(key))).sendKeys(value);
		}

		//log.info("Typing in an Element: " + key+" entered the value as : "+value);

	}

	
	
	
	@BeforeSuite
	public void setUp() {
		if (driver==null) {
			
			PropertyConfigurator.configure(".\\src\\test\\resources\\properties\\log4j.properties");
			

			try {
				OR.load(fis);
				//log.info("Or properties loaded!!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		try {
			fis =new  FileInputStream(".\\src\\test\\resources\\properties\\Config.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Config.load(fis);
			//log.info("Config properties loaded!!!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (Config.getProperty("browser").equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver();
			//log.info("CHrome browser is Launched ");
		
	}
		else if(Config.getProperty("browser").equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver=new FirefoxDriver();
		//	log.info("Firefox browser is Launched ");
		
	}
		
		
		driver.get(Config.getProperty("testsiteurl"));
		//log.info("NAVIGATING  to url"+Config.getProperty("testsiteurl"));
		DbManager.setMysqlDbConnection();
		//log.info("Database  connectio established");
		driver.manage().window().maximize();
		//driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicit.wait")), TimeUnit.SECONDS);
		
		//WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		

	}
	@AfterSuite
	public void tearDown() {
		driver.quit();
		//log.info("test execution completed !!!");
	}

}
