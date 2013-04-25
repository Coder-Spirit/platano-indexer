package com.bananity.util.serialization;


import com.bananity.text.TextNormalizer;

import java.util.Collection;
import java.util.Map;


/**
 * This class encapsulates static methods to serialize objects to JSON representations 
 *
 * @author 	Andreu Correa Casablanca
 * @see 	com.bananity.util.serialization.IJsonSerializable
 */
public class JsonSerializer {

	/**
	 * Generic method to serialize "any" object to a JSON string
	 *
	 * @param 	jsonObject 	Object to be serialized
	 * @return 				JSON string
	 */
	public static String ObjectToJsonString (final Object jsonObject) {
		// TODO : Add support to
		// - Character, char[], boolean[], int[], long[], float[], double[]

		if (jsonObject == null) {
			return "null";
		} else if (jsonObject instanceof Boolean) {
			return ((Boolean)jsonObject).toString();
		} else if (jsonObject instanceof Integer) {
			return ((Integer)jsonObject).toString();
		} else if (jsonObject instanceof Long) {
			return ((Long)jsonObject).toString();
		} else if (jsonObject instanceof Float) {
			return FloatToJsonString((Float)jsonObject);
		} else if (jsonObject instanceof Double) {
			return DoubleToJsonString((Double)jsonObject);
		} else if (jsonObject instanceof String) {
			return StringToJsonString((String)jsonObject);
		} else if (jsonObject instanceof Collection) {
			return CollectionToJsonString((Collection)jsonObject);
		} else if (jsonObject instanceof Object[]) {
			return ArrayToJsonString((Object[])jsonObject);
		} else if (jsonObject instanceof Map) {
			return MapToJsonString((Map<String, Object>)jsonObject);
		} else if (jsonObject instanceof IJsonSerializable) {
			return ((IJsonSerializable)jsonObject).toJsonStr();
		} else {
			return ""; // TODO : throw an Exception
		}
	}

	public static String FloatToJsonString (final Float flt) {
		if (flt.equals(Float.NaN) || flt.equals(Float.POSITIVE_INFINITY) || flt.equals(Float.NEGATIVE_INFINITY)) {
			return "null";
		} else {
			return flt.toString();
		}
	}

	public static String DoubleToJsonString (final Double dbl) {
		if (dbl.equals(Double.NaN) || dbl.equals(Double.POSITIVE_INFINITY) || dbl.equals(Double.NEGATIVE_INFINITY)) {
			return "null";
		} else {
			return dbl.toString();
		}
	}

	/**
	 * @return 	Escaped and quoted valid JSON string value
	 */
	public static String StringToJsonString (final String str) {
		return new StringBuilder()
			.append("\"")
			.append(TextNormalizer.escapeUnicode(
				str
					.replace("\"", "\\\"")
					.replace("\b", "\\b")
					.replace("\f", "\\f")
					.replace("\n", "\\n")
					.replace("\r", "\\r")
					.replace("\t", "\\t")
			))
			.append("\"")
			.toString();
	}

	/**
	 * Serializes collections as if were arrays to JSON strings
	 */
	public static String CollectionToJsonString (final Collection jsonCollection) {
		StringBuilder result = new StringBuilder("[");

		boolean isStart = true;
		for (Object item : jsonCollection) {
			if (isStart) {
				isStart = false;
			} else {
				result.append(",");
			}

			result.append(ObjectToJsonString(item));
		}

		return result.append("]").toString();
	}

	/**
	 * Serializes arrays to JSON strings
	 */
	public static String ArrayToJsonString (final Object[] jsonArray) {
		StringBuilder result = new StringBuilder("[");

		boolean isStart = true;
		for (Object item : jsonArray) {
			if (isStart) {
				isStart = false;
			} else {
				result.append(",");
			}

			result.append(ObjectToJsonString(item));
		}

		return result.append("]").toString();
	}

	/**
	 * Serializes maps to JSON Object values
	 */
	public static String MapToJsonString (final Map<String, Object> jsonMap) {
		StringBuilder result = new StringBuilder("{");

		boolean isStart = true;
		for (Map.Entry<String, Object> entry : jsonMap.entrySet()) {
			if (isStart) {
				isStart = false;
			} else {
				result.append(",");
			}

			result.append("\"").append(entry.getKey()).append("\":").append(ObjectToJsonString(entry.getValue()));
		}

		return result.append("}").toString();
	}
}
