package org.framework.SeleniumCucumber.Fuctions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.framework.SeleniumCucumber.StepsDefinitions.Hooks;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import junit.framework.Assert;

public class SeleniumFuctions {

	/**
	 * Log attributte
	 */
	private static final Logger log = Logger.getLogger(SeleniumFuctions.class);

	/**
	 * Instance of driver
	 */
	static WebDriver driver;

	public SeleniumFuctions() {
		driver = Hooks.driver;
	}

	/**
	 * Page path files json
	 */
	public static String pageFilePath = "src/test/resources/Page/";
	public static String fileName = "";

	/**
	 * Scenario Data
	 */
	public static Map<String, String> scenaryData = new HashMap<>();
	/**
	 * Variable enviroment for choose a enviroment
	 */
	public static String enviroment = "";

	/**
	 * test properties config
	 */
	private static Properties prop = new Properties();

	/**
	 * Attributes for the framework that searches the elements in the JSON file
	 */
	public static String GetFieldBy;
	public static String ValueToFind;

	/**
	 * Method for return the JSON file
	 * 
	 * @return object JSON file
	 * @throws Exception
	 */
	public static Object readJson() throws Exception {
		FileReader reader = new FileReader(pageFilePath + fileName);
		Object readerParse = new Object();
		try {
			if (reader != null) {
				JSONParser filePageJson = new JSONParser();
				readerParse = filePageJson.parse(reader);
			}
		} catch (FileNotFoundException | NullPointerException e) {
			log.error("ReadEntity: File does not exist " + fileName);
			readerParse = null;
		}
		return readerParse;
	}

	/**
	 * 
	 * @param element
	 * @return Entity by which we will look at the DOM and perform the actions in it
	 * @throws Exception
	 */
	public static JSONObject readEntity(String element) throws Exception {
		JSONObject entity = null;
		JSONObject fileJson = (JSONObject) readJson();
		entity = (JSONObject) fileJson.get(element);

		return entity;
	}

	/**
	 * @author Ángel
	 * @param element
	 * @return elements based on the value of the "class" attribute
	 * @throws Exception
	 */
	public static By getCompleteElement(String element) throws Exception {
		By result = null;
		JSONObject entity = readEntity(element);

		GetFieldBy = (String) entity.get("GetFieldBy");
		ValueToFind = (String) entity.get("ValueToFind");

		if ("className".equalsIgnoreCase(GetFieldBy)) {
			result = By.className(ValueToFind);
		} else if ("cssSelector".equalsIgnoreCase(GetFieldBy)) {
			result = By.cssSelector(ValueToFind);
		} else if ("id".equalsIgnoreCase(GetFieldBy)) {
			result = By.id(ValueToFind);
		} else if ("linkText".equalsIgnoreCase(GetFieldBy)) {
			result = By.linkText(ValueToFind);
		} else if ("name".equalsIgnoreCase(GetFieldBy)) {
			result = By.name(ValueToFind);
		} else if ("tagName".equalsIgnoreCase(GetFieldBy)) {
			result = By.tagName(ValueToFind);
		} else if ("xpath".equalsIgnoreCase(GetFieldBy)) {
			result = By.xpath(ValueToFind);
		}

		return result;
	}

	/**
	 * 
	 * @param property
	 * @return String with the property that contains the JSON files
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String readProperty(String property) throws FileNotFoundException, IOException {
		prop.load(new FileReader("src/test/resources/test.properties"));
		return prop.getProperty(property);
	}

	/**
	 * Save or update the variables by enviroment config
	 * 
	 * @param key
	 * @param text
	 */
	public static void saveInScenario(String key, String text) {
		if (!scenaryData.containsKey(key)) {
			scenaryData.put(key, text);
			log.info(String.format("Save as Scenario Context key: %s with value: %s", key, text));
		} else {
			scenaryData.replace(key, text);
			log.info(String.format("Update Scenario Context key: %s with value: %s", key, text));
		}
	}

	/**
	 * @throws IOException
	 * @throws FileNotFoundException
	 * 
	 */
	public static void retriveTestData(String parameter) throws FileNotFoundException, IOException {
		enviroment = readProperty("enviroment");
		try {
			saveInScenario(parameter, readProperty(parameter + "." + enviroment));
			System.out.println(scenaryData.get(parameter));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void iSetElementWithKeyValue(String element, String key) throws Exception {
		By seleniumElement = getCompleteElement(element);
		boolean exist = scenaryData.containsKey(key);
		if (exist) {
			String text = scenaryData.get(key);
			driver.findElement(seleniumElement).sendKeys(text);
			log.info(String.format("The given Key don´t exist in this context", element, text));
		}else {
			Assert.assertTrue(exist);
		}
	}

}
