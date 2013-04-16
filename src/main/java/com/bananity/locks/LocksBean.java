package com.bananity.locks;


// Java Utils
import java.util.HashMap;

// Java Concurrency Management
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

// Bean Setup
import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.ejb.Singleton;
import javax.annotation.PostConstruct;

// JBoss Concurrency Management
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;

// Log4j
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;


/**
 * @author Andreu Correa Casablanca
 */
@Startup
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class LocksBean {

	/**
	 * Log4j reference
	 */
	private static Logger log;

	/**
	 * Internal lock, used to update collection and token's locks
	 */
	private ReentrantReadWriteLock 	internalLock 		= null;
	private ReadLock 				internalReadLock 	= null;
	private WriteLock 				internalWriteLock 	= null;

	/**
	 * Locks over Collections
	 */
	private HashMap<String, ReentrantReadWriteLock> collectionLocks = null;

	/**
	 *
	 */
	@PostConstruct
	void init() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		PropertyConfigurator.configure(classLoader.getResource("log4j.properties"));
		log = Logger.getLogger(LocksBean.class);

		internalLock 		= new ReentrantReadWriteLock();
		internalReadLock 	= internalLock.readLock();
		internalWriteLock 	 = internalLock.writeLock();

		collectionLocks = new HashMap<String, ReentrantReadWriteLock>();
	}

	/**
	 *
	 */
	private ReentrantReadWriteLock getCollectionReeentrantLock (String collName) {
		internalReadLock.lock();
		ReentrantReadWriteLock collectionLock = collectionLocks.get(collName);

		if (collectionLock == null) {
			internalReadLock.unlock();
			internalWriteLock.lock();
			
			collectionLock = collectionLocks.get(collName);
			if (collectionLock == null) {
				collectionLock = new ReentrantReadWriteLock();
				collectionLocks.put(collName, collectionLock);
			}

			internalWriteLock.unlock();
		} else {
			internalReadLock.unlock();
		}

		return collectionLock;
	}

	/**
	 *
	 */
	public ReadLock getCollectionReadLock (String collName) {
		return getCollectionReeentrantLock(collName).readLock();
	}

	/**
	 *
	 */
	public WriteLock getCollectionWriteLock (String collName) {
		return getCollectionReeentrantLock(collName).writeLock();
	}

	/**
	 *
	 */
	public ReadLock getTokenReadLock (String collName, String token) {
		// TODO : Implement a real token lock
		return getCollectionReadLock(collName);
	}

	/**
	 *
	 */
	public WriteLock getTokenWriteLock (String collName, String token) {
		// TODO : Implement a real token lock
		return getCollectionWriteLock(collName);
	}
}
