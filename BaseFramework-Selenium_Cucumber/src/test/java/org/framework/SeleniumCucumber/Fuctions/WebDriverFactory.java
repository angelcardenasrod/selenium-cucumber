package org.framework.SeleniumCucumber.Fuctions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverFactory {
	// Logger
	private static final Logger log = Logger.getLogger(WebDriverFactory.class);

	// Attributes
	private static Properties prop = new Properties();
	private static String resourceFolder;

	//private static WebDriverFactory instance;

	private static String chromeDriver = "CHROME";
	private static String firefoxDriver = "FIREFOX";

	private static String winOS = "WINDOWS";
	private static String linuxOS = "LINUX";
	private static String macOS = "MAC";

	/**
	 * Method to return the driver according to the browser and the OS requested in the properties file 
	 * @param browser
	 * @param os
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static WebDriver getNewDriver(String browser, String os) throws FileNotFoundException, IOException {
		WebDriver driver;
		prop.load(new FileReader("src/test/resources/test.properties"));
		resourceFolder = prop.getProperty("resourcesFolder");
		
		/****** Chrome Driver ******/
		if (chromeDriver.equalsIgnoreCase(browser)) {
			
			/****** Operative System to chrome driver ******/
			if (winOS.equalsIgnoreCase(os)) {
				System.setProperty("webdriver.chrome.driver", resourceFolder + os + "/chromedriver.exe");
			} else if (linuxOS.equalsIgnoreCase(os)) {
				System.setProperty("webdriver.chrome.driver", resourceFolder + os + "/chromedriver");
			} else if (macOS.equalsIgnoreCase(os)) {
				System.setProperty("webdriver.chrome.driver", resourceFolder + os + "/chromedriver");
			}

			driver = new ChromeDriver();
			
			/****** Firefox Driver ******/
		} else if (firefoxDriver.equalsIgnoreCase(browser)) {

			/****** Operative System to Firefox driver ******/
			if (winOS.equalsIgnoreCase(os)) {
				System.setProperty("webdriver.gecko.driver", resourceFolder + os + "/geckodriver.exe");
			} else if (linuxOS.equalsIgnoreCase(os)) {
				System.setProperty("webdriver.gecko.driver", resourceFolder + os + "/geckodriver");
			} else if (macOS.equalsIgnoreCase(os)) {
				System.setProperty("webdriver.gecko.driver", resourceFolder + os + "/geckodriver");
			}
			driver = new FirefoxDriver();
		}else {
			/****** The Driver is not selected ******/
			log.error("The driver is not selected properly, invalid name: "+browser+", "+os);
			return null;
		}
		driver.manage().window().maximize();
		return driver;
	}
}
