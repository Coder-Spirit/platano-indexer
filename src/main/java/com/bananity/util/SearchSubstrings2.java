package com.bananity.util;


// 


/**
 * @author Andreu Correa Casablanca
 */
public class SearchSubstrings2
{
	/**
	 *
	 */
	private int maxTokenLength = 0;

	/**
	 *
	 */
	private static final int THRESHOLD = 2;

	/**
	 *
	 */
	public SearchSubstrings2 (final String searchTerm) {
		this(searchTerm, THRESHOLD);
	}

	/**
	 *
	 */
	public SearchSubstrings2 (final String searchTerm, int threshold) {
		String[] searchTermTokens = searchTerm.split("\\s+");

		for (String searchTermToken : searchTermTokens) {
			maxTokenLength = Math.max(maxTokenLength, searchTermToken.length());
		}
	}

	/**
	 *
	 */
	public int getMaxTokenLength () {
		return maxTokenLength;
	}
}