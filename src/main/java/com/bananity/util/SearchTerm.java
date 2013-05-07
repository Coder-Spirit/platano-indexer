package com.bananity.util;


import com.bananity.text.TextNormalizer;
import com.bananity.util.serialization.IBsonSerializable;
import com.bananity.util.serialization.IJsonSerializable;
import com.bananity.util.serialization.JsonSerializer;


/**
 * This class encapsulates a lot of expanded info related with search terms (strings)
 * The expanded info is lazyly calculated
 *
 * @author Andreu Correa Casablanca
 */
public class SearchTerm implements IJsonSerializable, IBsonSerializable
{
	String text;

	SearchTermStrings lcFlattenStrings 	= null;
	SearchTermStrings lowerCaseStrings 	= null;
	SearchTermStrings originalStrings 	= null;

	// ---------------------------------------------------------------------------------------------

	public SearchTerm (final String text) {
		this.text = text;
	}

	// ---------------------------------------------------------------------------------------------

	/**
	 * Returns expanded info
	 */
	public SearchTermStrings getLcFlattenStrings () {
		if (lcFlattenStrings == null) {
			lcFlattenStrings = new SearchTermStrings(TextNormalizer.flattenText(text).toLowerCase());
		}

		return lcFlattenStrings;
	}

	/**
	 * Returns expanded info
	 */
	public SearchTermStrings getLowerCaseStrings () {
		if (lowerCaseStrings == null) {
			lowerCaseStrings = new SearchTermStrings(text.toLowerCase());
		}

		return lowerCaseStrings;
	}

	/**
	 * Returns expanded info
	 */
	public SearchTermStrings getOriginalStrings () {
		if (originalStrings == null) {
			originalStrings = new SearchTermStrings(text);
		}

		return originalStrings;
	}

	// ---------------------------------------------------------------------------------------------

	public int hashCode () {
		return text.hashCode();
	}

	public boolean equals (Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof SearchTerm) {
			return ((SearchTerm)o).text.equals(this.text);
		} else {
			return false;
		}
	}

	// ---------------------------------------------------------------------------------------------

	public String toString () {
		return text;
	}

	public String toJsonStr () {
		return JsonSerializer.StringToJsonString(text);
	}

	public Object toBsonCompatible () {
		return text;
	}

	// ---------------------------------------------------------------------------------------------

	/**
	 * In theory, this method isn't needed, only coded for security
	 */
	protected void finalize () throws Throwable {
		text 				= null;
		lcFlattenStrings 	= null;
		lowerCaseStrings 	= null;
		originalStrings 	= null;

		super.finalize();
	}
}