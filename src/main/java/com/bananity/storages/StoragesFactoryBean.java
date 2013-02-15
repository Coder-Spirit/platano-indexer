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

@Startup
@Singleton
@DependsOn({"StorageConstantsBean"})
//@AccessTimeout(value=10000)
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class StoragesFactoryBean {

	private final static String MONGO_TYPE = "mongo";

	private static Logger log;

	private IIndexStorage storage;

	@EJB
	private StorageConstantsBean scB;

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

	@Lock(LockType.READ)
		public IIndexStorage getIndexStorage() {
			return storage;
		}
}
