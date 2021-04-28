package org.framework.SeleniumCucumber.StepsDefinitions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.framework.SeleniumCucumber.Fuctions.SeleniumFuctions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class StepsDefinitions {
	/****** Attributes ******/
	WebDriver driver;
	
	/****** log attribute ******/
	private static final Logger log = Logger.getLogger(StepsDefinitions.class);
	
	public StepsDefinitions() {
		driver = Hooks.driver;
	}
	//MIRAR VIDEO 17 PARA SABER COMO INSTANCIA ESTE ARCHIVO PROPERTIES.
	@Given("^I am in app main site$")
	public void iAmInAppMainSite() throws FileNotFoundException, IOException {
		Properties prop = new Properties();
		prop.load(new FileReader("src/test/resources/test.properties"));
		String url = prop.getProperty("MainAppUrlBase");
		log.info("Navigate to: "+url);
		driver.get(url);
	}
	
	@Then("^I load the DOM Information (.*)$")
	public void i_load_the_DOM_information(String fileJson) throws Exception {
		SeleniumFuctions.fileName = fileJson;
		SeleniumFuctions.readJson();
		log.info("Initializate file: " + fileJson);
	}
	
	@Then("^I do a click in element (.*)$")
	public void i_do_a_click_in_the_element(String element) throws Exception {
		 By SeleniumElement = SeleniumFuctions.getCompleteElement(element);
		 driver.findElement(SeleniumElement).click();
		 log.info("Click on element by: " + element);	
	}
	
	@And("^I set (.*) with text (.*)$")
	public void i_set_with_text (String element, String text) throws Exception {
		By SeleniumElement = SeleniumFuctions.getCompleteElement(element);
		driver.findElement(SeleniumElement).sendKeys(text);
	}
		  
	
	
	
}
