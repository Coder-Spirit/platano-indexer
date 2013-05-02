package com.bananity.util.serialization;


import com.mongodb.BasicDBObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


/**
 * 
 *
 * @author 	Andreu Correa Casablanca
 * @see 	com.bananity.util.serialization.IBsonSerializable
 */
public class BsonSerializer {

	/**
	 * 
	 *
	 * @param 	jsonObject 	Object to be serialized
	 * @return 				JSON string
	 */
	public static Object ObjectToBsonCompatible (final Object o) {
		if (o instanceof String || o instanceof Boolean || o instanceof Integer || o instanceof Long || o instanceof Float || o instanceof Double) {
			return o;
		} else if (o instanceof IBsonSerializable) {
			return ((IBsonSerializable)o).toBsonCompatible();
		} else if (o instanceof Collection) {
			return CollectionToBsonCompatible((Collection)o);
		} else if (o instanceof Object[]) {
			return ArrayToBsonCompatible((Object[])o);
		} else if (o instanceof Map) {
			return MapToBsonCompatible((Map<String, Object>)o);
		} else {
			return null; // TODO : throw an Exception
		}
	}

	/**
	 * 
	 */
	public static ArrayList CollectionToBsonCompatible(final Collection baseCollection) {
		ArrayList result = new ArrayList();

		for (Object item : baseCollection) {
			result.add(ObjectToBsonCompatible(item));
		}

		return result;
	}

	/**
	 * 
	 */
	public static ArrayList ArrayToBsonCompatible (final Object[] baseArray) {
		ArrayList result = new ArrayList();

		for (Object item : baseArray) {
			result.add(ObjectToBsonCompatible(item));
		}

		return result;
	}

	/**
	 * 
	 */
	public static BasicDBObject MapToBsonCompatible (Map<String, Object> baseMap) {
		BasicDBObject result = new BasicDBObject();

		for (Map.Entry<String, Object> entry : baseMap.entrySet()) {
			result.put(entry.getKey(), ObjectToBsonCompatible(entry.getValue()));
		}

		return result;
	}
}
