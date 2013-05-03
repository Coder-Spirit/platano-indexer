package com.bananity.settings;


// Bananity Classes
import com.bananity.constants.ErrorConstants;

// Java IO
import java.io.InputStreamReader;

// Java Utils
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Bean Setup
import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.ejb.Singleton;
import javax.annotation.PostConstruct;

// Concurrency Management
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.DependsOn;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;

// Log4j
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

// JSON
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;


/**
 *  This class loads the server settings from a .properties file
 *  The object follows the Singleton pattern.
 *
 *  @author  Andreu Correa Casablanca
 *  @version 0.4
 */
@Startup
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class SettingsLoaderBean {

	/**
	 *  Log4j reference
	 */
	private static Logger log;

	/**
	 * Settings data in JSON format
	 */
	private static Map settingsMap = null;

	/**
	 *
	 */
	@Lock(LockType.READ)
	@PostConstruct
	void init() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		PropertyConfigurator.configure(classLoader.getResource("log4j.properties"));
		log = Logger.getLogger(SettingsLoaderBean.class);

		/*
		try {
			log.info("LOADING conf.json");

			settingsMap = (Map)new JSONParser().parse(
				new InputStreamReader(classLoader.getResourceAsStream("conf.json")),
				new ContainerFactory () {
					public List creatArrayContainer () {
						return new ArrayList();
					}

					public Map createObjectContainer () {
						return new LinkedHashMap<String, Object>();
					}
				}
			);

			log.info("LOADED conf.json");
		} catch (Exception e) {

			log.error( "Unable to load settings file ( conf.properties )", e);
			System.exit(ErrorConstants.UNABLE_TO_LOAD_INDEX_SETTINGS);

		} finally {
			log.info("ENDING SettingsLoaderBean.init");
		}
		*/
	}

	@Lock(LockType.READ)
	public LinkedHashMap<String, Object> getCollectionSettings (String collName) throws Exception {
		/*
		LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();

		LinkedHashMap collectionMap = new LinkedHashMap<String, Object>();
		//
		
		//
		result.put("collection", collectionMap);

		LinkedHashMap storageMap = new LinkedHashMap<String, Object>();
		//
		result.put("storage", storageMap);
		*/

		//return result;

		return null;
	}
}
