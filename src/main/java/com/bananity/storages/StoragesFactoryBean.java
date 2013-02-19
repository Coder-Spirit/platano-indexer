package com.bananity.storages;

import com.bananity.constants.ErrorConstants;
import com.bananity.constants.StorageConstantsBean;

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
import javax.ejb.AccessTimeout;
import javax.ejb.ConcurrentAccessTimeoutException;

// Log4j
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

/**
 *  Storages Factory
 *
 *  @author 	Andreu Correa Casablanca
 *  @version 	0.4
 *
 *  @see 		com.bananity.storages.IIndexStorage
 *  @see 		com.bananity.storages.MongoIndexStorage
 */
@Startup
@Singleton
@DependsOn({"StorageConstantsBean"})
//@AccessTimeout(value=10000)
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class StoragesFactoryBean {

	/**
	 *  Storage Type Constant
	 */
	private final static String MONGO_TYPE = "mongo";

	/**
	 *  Log4j reference
	 */
	private static Logger log;

	/**
	 *  Storage reference
	 */
	private IIndexStorage storage;

	/**
	 *  Storage Constants reference
	 */
	@EJB
	private StorageConstantsBean scB;

	/**
	 *  Initializes the logger and the storage
	 */
	@Lock(LockType.WRITE)
	@PostConstruct
		void init() {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			PropertyConfigurator.configure(classLoader.getResource("log4j.properties"));
			log = Logger.getLogger(StoragesFactoryBean.class);

			try {
				if (scB.getStorageType().equals(MONGO_TYPE)) {
					storage = new MongoIndexStorage(scB);
				} else {
					log.error("Storage type specified in `conf.properties` is not supported ( "+scB.getStorageType()+" )");
					System.exit(ErrorConstants.INCORRECT_SETTINGS_STORAGE_TYPE);
				}
			} catch (Exception e) {
				log.error("Unable to stablish storage connection : "+e.getMessage()+", cause: "+e.getCause());
				e.printStackTrace();
				System.exit(ErrorConstants.UNABLE_TO_CREATE_STORAGE_CONNECTION);
			}
		}

	/**
	 *  @return Storage Reference (similar to Singleton: one instance)
	 */
	@Lock(LockType.READ)
		public IIndexStorage getIndexStorage() {
			return storage;
		}
}
