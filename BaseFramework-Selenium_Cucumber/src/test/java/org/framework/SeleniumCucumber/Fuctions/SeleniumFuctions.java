package org.framework.SeleniumCucumber.Fuctions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;

public class SeleniumFuctions {
	
	/**
	 * Log attributte
	 */
	private static final Logger log = Logger.getLogger(SeleniumFuctions.class);
	
	/**
	 * Page path files json
	 */
	public static String pageFilePath = "src/test/resources/Page/";
	public static String fileName = "";
	
	/**
	 * Scenario Data
	 */
	public Map<String, String> scenaryData = new HashMap<>();
	
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
	public static JSONObject readEntity (String element) throws Exception {
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
	public static By getCompleteElement (String element) throws Exception {
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
	public static String readProperty (String property) throws FileNotFoundException, IOException {
		prop.load(new FileReader("src/test/resources/test.properties"));
		return prop.getProperty(property);
	}
	
	/**
	 * Save or update the variables by enviroment config
	 * @param key
	 * @param text
	 */
	public void saveInScenario(String key, String text) {
		if (!this.scenaryData.containsKey(key)) {
			this.scenaryData.put(key, text);
			log.info(String.format("Save as Scenario Context key: %s with value: %s", key, text));
		} else {
			this.scenaryData.replace(key, text);
			log.info(String.format("Update Scenario Context key: %s with value: %s", key, text));
		}
	}

}
