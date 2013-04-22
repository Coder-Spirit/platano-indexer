package com.bananity.util;


// Java Utils
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


/**
 * @author Andreu Correa Casablanca
 */
public class SearchSubstrings2
{
	private final String searchTerm;

	private ArrayList<String> originalTokens 		= null;

	private HashBag2<String> originalTextBag 		= null;
	private HashBag2<String> normalizedTextBag 		= null;
	private HashBag2<String> lcNormalizedTextBag 	= null;

	private HashMap<String, Integer> wordLengths 	= null;

	private HashMap<String, HashBag2<String>> originalWordsBags 	= null;
	private HashMap<String, HashBag2<String>> normalizedWordsBags 	= null;
	private HashMap<String, HashBag2<String>> lcNormalizedWordsBags = null;

	/**
	 *
	 */
	private int maxTokenLength = -1;

	/**
	 *
	 */
	private final int threshold;

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
		this.searchTerm = searchTerm;
		this.threshold 	= threshold;
	}

	/**
	 *
	 */
	public int getMaxTokenLength () {
		if (maxTokenLength == -1) {
			computeMaxTokenLength();
		}

		return maxTokenLength;
	}

	/**
	 *
	 */
	private void computeMaxTokenLength () {
		if (originalTokens == null) {
			originalTokens = tokenize(searchTerm, true);
		}

		for (String searchTermToken : originalTokens) {
			int tokenLength = searchTermToken.length();
			if (tokenLength > maxTokenLength) maxTokenLength = tokenLength;
		}
	}

	/**
	 *
	 */
	private ArrayList<String> tokenize (final String searchTerm, final boolean addWordPairs) {
		 final String[] tokensArray = searchTerm.split("\\s+");

		 ArrayList<String> finalTokens = new ArrayList<String>(Arrays.asList(tokensArray));

		 if (addWordPairs) {
		 	for (int i=0, n=tokensArray.length-1; i<n; i++) {
				finalTokens.add(
					new StringBuilder(tokensArray[i]).append(" ").append(tokensArray[i+1]).toString()
				);
			}
		 }

		 return finalTokens;
	}
}