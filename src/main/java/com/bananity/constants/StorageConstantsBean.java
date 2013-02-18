 package com.bananity.constants;


// Java utils
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

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

// Timeouts
//import javax.ejb.AccessTimeout;
//import javax.ejb.ConcurrentAccessTimeoutException;

// Log4j
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;


@Startup
@Singleton
//@DependsOn({})
//@AccessTimeout(value=10000)
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class StorageConstantsBean {

	private static Logger log;

	////////////////////////////////////////
	//              Constants             //
	////////////////////////////////////////

	public final static String DEFAULT = "default";

	////////////////////////////////////////
	//      General Storage Settings      //
	////////////////////////////////////////

	private String 		STORAGE_TYPE;

	private String[] 	INDEXED_COLLECTIONS;

	// INDEX OPTIONS
	private int 		TOKEN_ENTRY_SIZE;


	////////////////////////////////////////
	//           Mongo Settings           //
	////////////////////////////////////////

	// MONGO CONNECTION
	private HashMap<String, ArrayList<String>> MONGO_HOSTS;
	private HashMap<String, ArrayList<String>> MONGO_SHARDS;
	private HashMap<String, ArrayList<String>> MONGO_DB;

	// COLLECTION MAPPINGS
	private HashMap<String, ArrayList<String>> MONGO_MAPPINGS;

	// MONGO TUNING
	private HashMap<String, ArrayList<String>> MONGO_CONS_PER_HOST;
	private HashMap<String, ArrayList<String>> MONGO_CONNECT_TIMEOUT;
	private HashMap<String, ArrayList<String>> MONGO_SOCKET_TIMEOUT;


	////////////////////////////////////////
	//           Cache Settings           //
	////////////////////////////////////////

	private HashMap<String, ArrayList<String>> TOKENS_CACHE_SIZE;
	private HashMap<String, ArrayList<String>> RESULTS_CACHE_SIZE;

	@Lock(LockType.WRITE)
	@PostConstruct
		void init() throws Exception {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			PropertyConfigurator.configure(classLoader.getResource("log4j.properties"));
			log = Logger.getLogger(StorageConstantsBean.class);

			Properties prop = new Properties();

			try {
				log.info("LOADING conf.properties");
				prop.load( Thread.currentThread().getContextClassLoader().getResourceAsStream("conf.properties") );

				////////////////////////////////////////
				//      General Storage Settings      //
				////////////////////////////////////////

				STORAGE_TYPE 				= prop.getProperty("storage_type");

				INDEXED_COLLECTIONS 		= prop.getProperty("indexed_collections").split("\\s*,\\s*");

				// INDEX OPTIONS
				TOKEN_ENTRY_SIZE 			= Integer.parseInt( prop.getProperty("token_entry_size") );

				////////////////////////////////////////
				//           Mongo Settings           //
				////////////////////////////////////////
				
				// MONGO CONNECTION
				MONGO_HOSTS 				= readConfLine( prop.getProperty("mongo_hosts") );
				MONGO_SHARDS 				= readConfLine( prop.getProperty("mongo_shards"), false );
				MONGO_DB 					= readConfLine( prop.getProperty("mongo_db") );

				// COLLECTION MAPPINGS
				MONGO_MAPPINGS 				= readConfLine( prop.getProperty("mongo_mappings") );

				// MONGO TUNING
				MONGO_CONS_PER_HOST 		= readConfLine( prop.getProperty("mongo_cons_per_host") );
				MONGO_CONNECT_TIMEOUT 		= readConfLine( prop.getProperty("mongo_connect_timeout") );
				MONGO_SOCKET_TIMEOUT 		= readConfLine( prop.getProperty("mongo_socket_timeout") );

				////////////////////////////////////////
				//           Cache Settings           //
				////////////////////////////////////////

				TOKENS_CACHE_SIZE 			= readConfLine( prop.getProperty("tokens_cache_size") );
				RESULTS_CACHE_SIZE 			= readConfLine( prop.getProperty("results_cache_size") );

				log.info("LOADED conf.properties");
			} catch (Exception e) {
				
				log.error( "Unable to load settings file ( conf.properties ) : " + e.getMessage() );
				e.printStackTrace();

				System.exit(ErrorConstants.UNABLE_TO_LOAD_INDEX_SETTINGS);
			} finally {
				log.info("ENDING StorageConstantsBean.init");
			}
		}

	@Lock(LockType.WRITE)
		private static HashMap<String, ArrayList<String>> readConfLine (String confLine) throws Exception {
			return readConfLine(confLine, true);
		}

	@Lock(LockType.WRITE)
		private static HashMap<String, ArrayList<String>> readConfLine (String confLine, boolean strict) throws Exception {
			HashMap<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();

			if (confLine != null && confLine.length() > 0) {
				for ( String entry : confLine.split("\\s*,\\s*") ) {
					String[] entryParts = entry.split(":");

					ArrayList<String> entryData = new ArrayList<String>();

					for (int i=1, n=entryParts.length; i<n; i++) {
						entryData.add(entryParts[i]);
					}

					result.put(entryParts[0], entryData);
				}
			} else if (strict) {
				throw new Exception("¡Required confLine not found!");
			}

			return result;
		}

	////////////////////////////////////////
	//      General Storage Settings      //
	////////////////////////////////////////

	@Lock(LockType.READ)
		public String getStorageType () {
			return STORAGE_TYPE;
		}

	@Lock(LockType.READ)
		public String[] getIndexedCollections () {
			return INDEXED_COLLECTIONS;
		}

	// INDEX OPTIONS
	@Lock(LockType.READ)
		public int 		getTokenEntrySize () {
			return TOKEN_ENTRY_SIZE;
		}


	////////////////////////////////////////
	//           Mongo Settings           //
	////////////////////////////////////////

	// MONGO CONNECTION
	@Lock(LockType.READ)
		public HashMap<String, ArrayList<String>> getMongoHosts () {
			return MONGO_HOSTS;
		}

	@Lock(LockType.READ)
		public HashMap<String, ArrayList<String>> getMongoShards () {
			return MONGO_SHARDS;
		}

	@Lock(LockType.READ)
		public HashMap<String, ArrayList<String>> getMongoDB () {
			return MONGO_DB;
		}

	// COLLECTION MAPPINGS
	@Lock(LockType.READ)
		public HashMap<String, ArrayList<String>> getMongoMappings () {
			return MONGO_MAPPINGS;
		}

	// MONGO TUNING
	@Lock(LockType.READ)
		public HashMap<String, ArrayList<String>> getMongoConsPerHost () {
			return MONGO_CONS_PER_HOST;
		}

	@Lock(LockType.READ)
		public HashMap<String, ArrayList<String>> getMongoConnectTimeout() {
			return MONGO_CONNECT_TIMEOUT;
		}

	@Lock(LockType.READ)
		public HashMap<String, ArrayList<String>> getMongoSocketTimeout () {
			return MONGO_SOCKET_TIMEOUT;
		}

	////////////////////////////////////////
	//           Cache Settings           //
	////////////////////////////////////////

	@Lock(LockType.READ)
		public HashMap<String, ArrayList<String>> getTokensCacheSize () {
			return TOKENS_CACHE_SIZE;
		}

	@Lock(LockType.READ)
		public HashMap<String, ArrayList<String>> getResultsCacheSize () {
			return RESULTS_CACHE_SIZE;
		}
}
