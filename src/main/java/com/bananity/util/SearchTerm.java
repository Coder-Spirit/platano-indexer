package com.bananity.util;


import com.bananity.text.TextNormalizer;


public class SearchTerm
{
	final String text;

	SearchTermStrings lcFlattenStrings 	= null;
	SearchTermStrings lowerCaseStrings 	= null;
	SearchTermStrings originalStrings 	= null;

	public SearchTerm (final String text) {
		this.text = text;
	}

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
}