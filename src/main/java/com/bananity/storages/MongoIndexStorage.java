package com.bananity.storages;


import com.bananity.constants.StorageConstantsBean;
import com.bananity.util.HashBag;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.bson.types.ObjectId;


public class MongoIndexStorage implements IIndexStorage
{
	private HashMap<String, MongoClient> 	mcs;
	private HashMap<String, DB> 			dbs;
	private HashMap<String, DBCollection> 	collections;

	private StorageConstantsBean scB;

	public MongoIndexStorage (StorageConstantsBean scB) throws Exception {
		mcs 		= new HashMap<String, MongoClient>();
		dbs 		= new HashMap<String, DB>();
		collections = new HashMap<String, DBCollection>();

		this.scB = scB;

		if (scB.getMongoShards().size() > 0) {
			loadShards();
		} else {
			loadHosts();
		}

		loadDBs();

		loadCollections();
	}

	private void loadShards () throws Exception {
		HashSet<String> loadedHosts = new HashSet<String>();

		for (Map.Entry<String, ArrayList<String>> shardEntry : scB.getMongoShards().entrySet()) {
			loadShard(shardEntry.getKey());
			loadedHosts.addAll(shardEntry.getValue());
		}

		for (String hostName : scB.getMongoHosts().keySet()) {
			if (!loadedHosts.contains(hostName)) {
				loadHost(hostName);
			}
		}
	}

	private void loadShard (String shardName) throws Exception {
		ArrayList<String> shardSrc = scB.getMongoShards().get(shardName);

		ArrayList<String> consPerHostSrc	= scB.getMongoConsPerHost().get(shardName);
		ArrayList<String> connectTimeoutSrc	= scB.getMongoConnectTimeout().get(shardName);
		ArrayList<String> socketTimeoutSrc 	= scB.getMongoSocketTimeout().get(shardName);

		// Loading shard hosts
		if (shardSrc == null || shardSrc.size() == 0) {
			throw new Exception("¡Shard settings not defined for ("+shardName+")!");
		}

		ArrayList<ServerAddress> addresses = new ArrayList<ServerAddress>();

		for (String hostName : shardSrc) {
			ArrayList<String> hostAddrSrc = scB.getMongoHosts().get(hostName);

			if (hostAddrSrc == null || hostAddrSrc.size() != 2) {
				throw new Exception("¡No address found for \""+hostName+"\" host!");
			}

			addresses.add(new ServerAddress(
				hostAddrSrc.get(0),
				Integer.parseInt(hostAddrSrc.get(1))
			));
		}

		// Loading shard tuning settings
		if (consPerHostSrc == null || consPerHostSrc.size() != 1) {
			consPerHostSrc = scB.getMongoConsPerHost().get(StorageConstantsBean.DEFAULT);

			if (consPerHostSrc == null) {
				throw new Exception("¡Number of connections per host not defined!");
			}
		}

		if (connectTimeoutSrc == null || connectTimeoutSrc.size() != 1) {
			connectTimeoutSrc = scB.getMongoConnectTimeout().get(StorageConstantsBean.DEFAULT);

			if (connectTimeoutSrc == null) {
				throw new Exception("¡Connection timeout not defined!");
			}
		}

		if (socketTimeoutSrc == null || socketTimeoutSrc.size() != 1) {
			socketTimeoutSrc = scB.getMongoSocketTimeout().get(StorageConstantsBean.DEFAULT);

			if (socketTimeoutSrc == null) {
				throw new Exception("¡Socket timeout not defined!");
			}
		}

		int consPerHost 	= Integer.parseInt(consPerHostSrc.get(0));
		int connectTimeout 	= Integer.parseInt(connectTimeoutSrc.get(0));
		int socketTimeout 	= Integer.parseInt(socketTimeoutSrc.get(0));

		MongoClient mc = new MongoClient(
			addresses,
			new MongoClientOptions.Builder()
				.socketKeepAlive(true)
				.autoConnectRetry(true)
				.connectionsPerHost(consPerHost)
				.connectTimeout(connectTimeout)
				.socketTimeout(socketTimeout)
				.writeConcern(WriteConcern.UNACKNOWLEDGED)
				.build()
		);

		mcs.put(shardName, mc);
	}

	private void loadHosts () throws Exception {
		for (String hostName : scB.getMongoHosts().keySet()) {
			loadHost(hostName);
		}
	}

	private void loadHost (String hostName) throws Exception {
		ArrayList<String> hostAddrSrc		= scB.getMongoHosts().get(hostName);
		
		ArrayList<String> consPerHostSrc	= scB.getMongoConsPerHost().get(hostName);
		ArrayList<String> connectTimeoutSrc	= scB.getMongoConnectTimeout().get(hostName);
		ArrayList<String> socketTimeoutSrc 	= scB.getMongoSocketTimeout().get(hostName);

		// Loading host address
		if (hostAddrSrc == null || hostAddrSrc.size() != 2) {
			throw new Exception("¡No address found for \""+hostName+"\" host!");
		}

		ServerAddress addr = new ServerAddress(
			hostAddrSrc.get(0),
			Integer.parseInt(hostAddrSrc.get(1))
		);

		// Loading host tuning settings
		if (consPerHostSrc == null || consPerHostSrc.size() != 1) {
			consPerHostSrc = scB.getMongoConsPerHost().get(StorageConstantsBean.DEFAULT);

			if (consPerHostSrc == null) {
				throw new Exception("¡Number of connections per host not defined!");
			}
		}

		if (connectTimeoutSrc == null || connectTimeoutSrc.size() != 1) {
			connectTimeoutSrc = scB.getMongoConnectTimeout().get(StorageConstantsBean.DEFAULT);

			if (connectTimeoutSrc == null) {
				throw new Exception("¡Connection timeout not defined!");
			}
		}

		if (socketTimeoutSrc == null || socketTimeoutSrc.size() != 1) {
			socketTimeoutSrc = scB.getMongoSocketTimeout().get(StorageConstantsBean.DEFAULT);

			if (socketTimeoutSrc == null) {
				throw new Exception("¡Socket timeout not defined!");
			}
		}

		int consPerHost 	= Integer.parseInt(consPerHostSrc.get(0));
		int connectTimeout 	= Integer.parseInt(connectTimeoutSrc.get(0));
		int socketTimeout 	= Integer.parseInt(socketTimeoutSrc.get(0));

		MongoClient mc = new MongoClient(
			addr,
			new MongoClientOptions.Builder()
				.socketKeepAlive(true)
				.autoConnectRetry(true)
				.connectionsPerHost(consPerHost)
				.connectTimeout(connectTimeout)
				.socketTimeout(socketTimeout)
				.writeConcern(WriteConcern.UNACKNOWLEDGED)
				.build()
		);

		mcs.put(hostName, mc);
	}

	private void loadDBs () throws Exception {
		for (String dbName : scB.getMongoDB().keySet()) {
			loadDB(dbName);
		}
	}

	private void loadDB (String dbName) throws Exception {
		ArrayList<String> dbSrc	= scB.getMongoShards().get(dbName);

		if (dbSrc == null) {
			dbSrc = scB.getMongoDB().get(dbName);
		}

		if (dbSrc == null || dbSrc.size() != 2) {
			throw new Exception("¡DB settings not defined for ("+dbName+")!");
		}

		MongoClient mc = mcs.get(dbSrc.get(0));
		
		if (mc == null) {
			throw new Exception("¡Impossible to load DB settings ("+dbName+")!");
		}		

		DB db = mc.getDB(dbSrc.get(1));

		if (db == null) {
			throw new Exception("¡Impossible to open DB ("+dbName+")!");
		}

		dbs.put(dbName, db);
	}

	private void loadCollections () throws Exception {
		for (String collName : scB.getIndexedCollections()) {
			loadCollection(collName);
		}
	}

	private void loadCollection (String collName) throws Exception {
		ArrayList<String> collSrc = scB.getMongoMappings().get(collName);

		if (collSrc == null || collSrc.size() != 2) {
			throw new Exception("¡Impossible to load collection mapping ("+collName+")!");
		}

		DB db = dbs.get(collSrc.get(0));

		if (db == null) {
			throw new Exception("¡Impossible to load collection ("+collName+") because DB ("+collSrc.get(0)+") is not open!");
		}

		DBCollection coll = db.getCollection(collSrc.get(1));

		if (coll == null) {
			throw new Exception("¡Impossible to open collection ("+collName+")!");
		}

		collections.put(collName, coll);
	}

	public ArrayList<String> findSubToken (String collName, String subToken) throws Exception {
		DBCollection coll = collections.get(collName);

		if (coll == null) throw new Exception("¡Inexistent Mongo Collection ("+collName+")!");

		ArrayList<String> result = new ArrayList<String>(scB.getTokenEntrySize());

		DBObject mongoResult = coll.findOne(new BasicDBObject("_id", subToken));

		if (mongoResult == null) {
			return result;	
		}

		BasicDBList aliasFromDB = (BasicDBList)mongoResult.get("tl");
		if (aliasFromDB != null && aliasFromDB.size() > 0) {
			for (Object aliasObject : aliasFromDB) {
				result.add((String)aliasObject);
			}
		}
		
		return result;
	}

	public void insert (String collName, String subToken, Collection<String> items) throws Exception {
		DBCollection coll = collections.get(collName);

		if (coll == null) throw new Exception("¡Inexistent Mongo Collection ("+collName+")!");

		coll.update(
			new BasicDBObject("_id", subToken),
			new BasicDBObject("$set", new BasicDBObject("tl", items)),
			true, false
		);
	}
}