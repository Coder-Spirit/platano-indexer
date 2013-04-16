package com.bananity.locks;

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

@Startup
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class LocksBean {

	private static Logger log;

	@PostConstruct
	void init() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		PropertyConfigurator.configure(classLoader.getResource("log4j.properties"));
		log = Logger.getLogger(LocksBean.class);
	}

	ReadLock getCollectionReadLock (String collName) {

	}

	WriteLock getCollectionWriteLock (String collName) {

	}

	ReadLock getTokenReadLock (String collName, String token) {

	}

	WriteLock getTokenWriteLock (String collName, String token) {
		
	}
}
