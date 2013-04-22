package com.bananity.util;


// Java Utils
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;


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
	private final static int THRESHOLD = 2;

	/**
	 *
	 */
	private final static Pattern spacePattern = Pattern.compile("\\s+");

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
	public HashBag2<String> getOriginalTextBag () {
		if (originalTextBag == null) {
			computeOriginalTextBag();
		}

		return originalTextBag;
	}

	private void computeOriginalTextBag () {
		if (originalTokens == null) {
			originalTokens = tokenize(searchTerm, true);
		}

		originalWordsBags = new HashMap<String, HashBag2<String>>();

		ArrayList<String> allSubtokens = new ArrayList<String>();

		for (String token : originalTokens) {
			ArrayList<String> wordSubtokens = getAllSubstrings(token, Math.min(token.length(), threshold)); // Alternative for threshold : "(maxTokenLength>1)?threshold:1"

			allSubtokens.addAll(wordSubtokens);
			originalWordsBags.put(token, new HashBag2<String>(wordSubtokens));
		}

		originalTextBag = new HashBag2<String>(allSubtokens);
	}

	/**
	 *
	 */
	private void computeMaxTokenLength () {
		if (originalTokens == null) {
			originalTokens 	= tokenize(searchTerm, true);
			wordLengths 	= new HashMap<String, Integer>();
		}

		for (String token : originalTokens) {
			int tokenLength = token.length();

			wordLengths.put(token, tokenLength);
			if (tokenLength > maxTokenLength) maxTokenLength = tokenLength;
		}
	}

	/**
	 *
	 */
	private static ArrayList<String> getAllSubstrings(String token, int minLength) {
		int l = token.length();
		ArrayList<String> t = new ArrayList<String>();

		if (l < minLength) {
			return t;
		}

		--minLength;

		for ( int c = 0; c < l; c++ ) {
			for ( int r = minLength; r < l - c; r++ ){
				t.add( token.substring( c, c + r + 1 ) );
			}
		}

		return t;
	}

	/**
	 *
	 */
	private static ArrayList<String> tokenize (final String searchTerm, final boolean addWordPairs) {
		 final String[] tokensArray = spacePattern.split(searchTerm);

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