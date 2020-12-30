package org.framework.SeleniumCucumber.StepsDefinitions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.Given;

public class StepsDefinitions {
	/****** Attributes ******/
	WebDriver driver;
	
	/****** log attribute ******/
	private static final Logger log = LogManager.getLogger(StepsDefinitions.class);
	
	public StepsDefinitions() {
		driver = Hooks.driver;
	}
	//MIRAR VIDEO 17 PARA SABER COMO INSTANCIA ESTE ARCHIVO PROPERTIES.
	@Given("^I am in app main site")
	public void iAmInAppMainSite() throws FileNotFoundException, IOException {
		Properties prop = new Properties();
		prop.load(new FileReader("src/test/resources/test.properties"));
		String url = prop.getProperty("MainAppUrlBase");
		log.info("Navigate to: "+url);
		driver.get(url);
	}
	
	@Given("^I go to site(.*)")
	public void i_go_to_the_site(String url) {
		log.info("Navigate to: "+url);
		driver.get(url);
	}
	
}
