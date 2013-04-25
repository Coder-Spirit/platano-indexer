package com.bananity.util;


import com.bananity.text.TextNormalizer;
import com.bananity.util.serialization.IBsonSerializable;
import com.bananity.util.serialization.IJsonSerializable;
import com.bananity.util.serialization.JsonSerializer;


public class SearchTerm implements IJsonSerializable, IBsonSerializable
{
	final String text;

	SearchTermStrings lcFlattenStrings 	= null;
	SearchTermStrings lowerCaseStrings 	= null;
	SearchTermStrings originalStrings 	= null;

	// ---------------------------------------------------------------------------------------------

	public SearchTerm (final String text) {
		this.text = text;
	}

	// ---------------------------------------------------------------------------------------------

	public SearchTermStrings getLcFlattenStrings () {
		if (lcFlattenStrings == null) {
			lcFlattenStrings = new SearchTermStrings(TextNormalizer.flattenText(text).toLowerCase());
		}

		return lcFlattenStrings;
	}

	public SearchTermStrings getLowerCaseStrings () {
		if (lowerCaseStrings == null) {
			lowerCaseStrings = new SearchTermStrings(text.toLowerCase());
		}

		return lowerCaseStrings;
	}

	public SearchTermStrings getOriginalStrings () {
		if (originalStrings == null) {
			originalStrings = new SearchTermStrings(text);
		}

		return lowerCaseStrings;
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
}