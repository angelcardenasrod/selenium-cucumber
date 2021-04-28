package org.framework.SeleniumCucumber.StepsDefinitions;

import org.openqa.selenium.WebDriver;

import org.apache.log4j.Logger;
import org.framework.SeleniumCucumber.Fuctions.CreateDriver;

import java.io.FileNotFoundException;
import java.io.IOException;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

	private static final Logger log = Logger.getLogger(Hooks.class);
	Scenario scenario = null;
	public static WebDriver driver;

	@Before
	/**
	 * Delete all cookies at the start of each scenario to avoid shared state
	 * between test
	 */
	public void initDriver(Scenario scenario) throws FileNotFoundException, IOException {
		log.info(
				"*******************************************************************************************************************************");
		log.info("[ Configuration ] - Initializing driver configuration");
		log.info(
				"*******************************************************************************************************************************");
		driver = CreateDriver.initConfig();

		log.info(
				"*******************************************************************************************************************************");
		log.info("[ Scenario ] - " + scenario.getName());
		log.info(
				"*******************************************************************************************************************************");

	}

	@After
	public void tearDown() {
		log.info(
				"*******************************************************************************************************************************");
		log.info("[ Driver Status ] - Clean and close the instance of the driver");
		log.info(
				"*******************************************************************************************************************************");
		driver.quit();
	}
}
