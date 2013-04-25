package com.bananity.util.serialization;


/**
 * Interface to indicate that an object is serializable to JSON 
 *
 * @author Andreu Correa Casablanca
 * @see com.bananity.util.serialization.JsonSerializer
 */
public interface IBsonSerializable
{
	/**
	 * @return 	Compatible Bson Object
	 */
	public Object toBsonCompatible ();
}