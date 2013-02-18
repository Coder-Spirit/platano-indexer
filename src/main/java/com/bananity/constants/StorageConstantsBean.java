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


/**
 *  This class loads the server settings from a .properties file
 *  The object follows the Singleton pattern.
 *
 *  @author  Andreu Correa Casablanca
 *  @version 0.4
 */
@Startup
@Singleton
//@DependsOn({})
//@AccessTimeout(value=10000)
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class StorageConstantsBean {

	/**
	 *  Log4j reference
	 */
	private static Logger log;

	////////////////////////////////////////
	//              Constants             //
	////////////////////////////////////////

	/**
	 *  Constant used to reference the default settings for a specified parameter
	 */
	public final static String DEFAULT = "default";

	////////////////////////////////////////
	//      General Storage Settings      //
	////////////////////////////////////////

	/**
	 *  Storage type name (only 'mongo' supported at this moment)
	 */
	private String 		STORAGE_TYPE;

	/**
	 *  List of indexed collections names
	 */
	private String[] 	INDEXED_COLLECTIONS;

	// INDEX OPTIONS
	/**
	 *  Maximum number of indexed items per token
	 *  Greater values give us more accuracy, but lower performance too.
	 *  Lower values give us better performance, but less accuracy too.
	 */
	private int 		TOKEN_ENTRY_SIZE;


	////////////////////////////////////////
	//           Mongo Settings           //
	////////////////////////////////////////

	// MONGO CONNECTION
	/**
	 *  Associative list of mongo hosts (by 'internal' name), internal_host_name -> address,port
	 */
	private HashMap<String, ArrayList<String>> MONGO_HOSTS;

	/**
	 *  Associative list of mongo shards (by 'internal' name), internal_shard_name -> internal_host_name,...,internal_host_name
	 */
	private HashMap<String, ArrayList<String>> MONGO_SHARDS;

	/**
	 *  Associative list of mongo dbs (by 'internal' name), internal_db_name -> internal_[host/shard]_name,mongo_db 
	 */
	private HashMap<String, ArrayList<String>> MONGO_DB;

	// COLLECTION MAPPINGS
	/**
	 * Mapping from index collections to mongo collections, collection -> internal_db_name,mongo_collection
	 */
	private HashMap<String, ArrayList<String>> MONGO_MAPPINGS;

	// MONGO TUNING
	/**
	 *  Associative list of 'maximum connections per host (or shard)' (by host or shard internal name)
	 */
	private HashMap<String, ArrayList<String>> MONGO_CONS_PER_HOST;

	/**
	 *  Associative list of 'mongo connect timeout per host (or shard)' (by host or shard internal name)
	 */
	private HashMap<String, ArrayList<String>> MONGO_CONNECT_TIMEOUT;

	/**
	 *  Associative list of 'mongo socket timeout per host (or shard)' (by host or shard internal name)
	 */
	private HashMap<String, ArrayList<String>> MONGO_SOCKET_TIMEOUT;


	////////////////////////////////////////
	//           Cache Settings           //
	////////////////////////////////////////

	/**
	 *  Associative list of tokens cache sizes (by collection name, measured in bytes)
	 *  The used type is for convenience (it's not the 'correct' type, but it's easier to use with 'readConfLine' method)
	 */
	private HashMap<String, ArrayList<String>> TOKENS_CACHE_SIZE;

	/**
	 *  Associative list of results cache sizes (by collection name, measured in bytes)
	 *  The used type is for convenience (it's not the 'correct' type, but it's easier to use with 'readConfLine' method)
	 */
	private HashMap<String, ArrayList<String>> RESULTS_CACHE_SIZE;

	/**
	 *  This method initialized the logger reference and load the settings from conf.properties
	 */
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

	/**
	 * The same as readConfLine but forcing 'strict' parameter to 'true'.
	 *
	 * @see com.bananity.constants.StorageConstantsBean#readConfLine
	 */
	@Lock(LockType.WRITE)
		private static HashMap<String, ArrayList<String>> readConfLine (String confLine) throws Exception {
			return readConfLine(confLine, true);
		}

	/**
	 *  This method reads a text line and transforms it into an associative list, which values are lists of strings
	 *
	 *  @param 	confLine 	text line with settings data
	 *  @param 	strict 		this value specifies if the settings data must exist or not
	 *  @return 			Extracted data (as an associative list with list values) from conf line
	 */
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

	/**
	 *  @see 	com.bananity.constants.StorageConstantsBean#STORAGE_TYPE
	 *  @return Storage type name
	 */
	@Lock(LockType.READ)
		public String getStorageType () {
			return STORAGE_TYPE;
		}

	/**
	 *  @see 	com.bananity.constants.StorageConstantsBean#INDEXED_COLLECTIONS
	 *  @return Indexed Collection Names
	 */
	@Lock(LockType.READ)
		public String[] getIndexedCollections () {
			return INDEXED_COLLECTIONS;
		}

	// INDEX OPTIONS
	/**
	 *  @see 	com.bananity.constants.StorageConstantsBean#TOKEN_ENTRY_SIZE
	 *  @return Token Entry Size
	 */
	@Lock(LockType.READ)
		public int 		getTokenEntrySize () {
			return TOKEN_ENTRY_SIZE;
		}


	////////////////////////////////////////
	//           Mongo Settings           //
	////////////////////////////////////////

	// MONGO CONNECTION
	/**
	 *  @see 	com.bananity.constants.StorageConstantsBean#MONGO_HOSTS
	 *  @return Mongo Hosts data
	 */
	@Lock(LockType.READ)
		public HashMap<String, ArrayList<String>> getMongoHosts () {
			return MONGO_HOSTS;
		}

	/**
	 *  @see 	com.bananity.constants.StorageConstantsBean#MONGO_SHARDS
	 *  @return Mongo Shards data
	 */
	@Lock(LockType.READ)
		public HashMap<String, ArrayList<String>> getMongoShards () {
			return MONGO_SHARDS;
		}

	/**
	 *  @see 	com.bananity.constants.StorageConstantsBean#MONGO_DB
	 *  @return Mongo DBs data
	 */
	@Lock(LockType.READ)
		public HashMap<String, ArrayList<String>> getMongoDB () {
			return MONGO_DB;
		}

	// COLLECTION MAPPINGS
	/**
	 *  @see 	com.bananity.constants.StorageConstantsBean#MONGO_MAPPINGS
	 *  @return Mongo Mappings data
	 */
	@Lock(LockType.READ)
		public HashMap<String, ArrayList<String>> getMongoMappings () {
			return MONGO_MAPPINGS;
		}

	// MONGO TUNING
	/**
	 *  @see 	com.bananity.constants.StorageConstantsBean#MONGO_CONS_PER_HOST
	 *  @return Mongo Connections per host data
	 */
	@Lock(LockType.READ)
		public HashMap<String, ArrayList<String>> getMongoConsPerHost () {
			return MONGO_CONS_PER_HOST;
		}

	/**
	 *  @see 	com.bananity.constants.StorageConstantsBean#MONGO_CONNECT_TIMEOUT
	 *  @return Mongo Connection timeouts data
	 */
	@Lock(LockType.READ)
		public HashMap<String, ArrayList<String>> getMongoConnectTimeout() {
			return MONGO_CONNECT_TIMEOUT;
		}

	/**
	 *  @see 	com.bananity.constants.StorageConstantsBean#MONGO_SOCKET_TIMEOUT
	 *  @return Mongo Socket timeouts data
	 */
	@Lock(LockType.READ)
		public HashMap<String, ArrayList<String>> getMongoSocketTimeout () {
			return MONGO_SOCKET_TIMEOUT;
		}

	////////////////////////////////////////
	//           Cache Settings           //
	////////////////////////////////////////

	/**
	 *  @see 	com.bananity.constants.StorageConstantsBean#TOKENS_CACHE_SIZE
	 *  @return Tokens Cache Sizes data
	 */
	@Lock(LockType.READ)
		public HashMap<String, ArrayList<String>> getTokensCacheSize () {
			return TOKENS_CACHE_SIZE;
		}

	/**
	 *  @see 	com.bananity.constants.StorageConstantsBean#RESULTS_CACHE_SIZE
	 *  @return Results Cache Sizes data
	 */
	@Lock(LockType.READ)
		public HashMap<String, ArrayList<String>> getResultsCacheSize () {
			return RESULTS_CACHE_SIZE;
		}
}
