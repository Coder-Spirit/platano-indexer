package com.bananity.util.serialization;

/**
 * Interface to indicate that an object is serializable to JSON 
 *
 * @author Andreu Correa Casablanca
 * @see com.bananity.util.serialization.JsonSerializer
 */
public interface IJsonSerializable
{
	/**
	 * @return 	JSON representation of the object
	 */
	public String toJsonStr ();
}