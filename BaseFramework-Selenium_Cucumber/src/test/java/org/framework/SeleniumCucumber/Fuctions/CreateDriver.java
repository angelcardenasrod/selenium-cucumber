package org.framework.SeleniumCucumber.Fuctions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class CreateDriver {
	//Logger
	private static final Logger log = Logger.getLogger(CreateDriver.class);
	//Attributes
	private static String browser;
	private static String os;
	private static String LogLevel;
	//private static CreateDriver instance;
	private static Properties prop = new Properties();
	
	/*
	private static CreateDriver getCreateDriverInstance() {
		if (instance == null) {
			instance = new CreateDriver();
		}
		
		return instance;
	}
	*/
	
	/**
	 * Class for initializing the configuration that Selenium needs to set driver
	 * @return Selenium Driver
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static WebDriver initConfig() throws FileNotFoundException, IOException {
		//Create object to return
		WebDriver driver;
		
		try {
			log.info("*******************************************************************************************************************************");
			log.info("[ POM Condiguration ] - Read the basic properties configuration from: test.properties");
			log.info("*******************************************************************************************************************************");
			prop.load(new FileReader("src/test/resources/test.properties"));
			//Read the properties to file test.properties
			browser = prop.getProperty("browser");
			os = prop.getProperty("os");
			LogLevel = prop.getProperty("LogLevel");
			
			
		} catch (IOException e) {
			log.error("Can not read test.properties");
			log.error("initCongig error", e);
		}
		
		log.info(" [POM Configuration ] - OS: "+ os+" | Browser: "+browser+" |");
		log.info(" [POM Configuration ] - Logger Level: "+ LogLevel);
		log.info("*******************************************************************************************************************************");
		
		/****** Load the driver ******/	
		driver = WebDriverFactory.getNewDriver(browser, os);
		
		return driver;
	}
}
