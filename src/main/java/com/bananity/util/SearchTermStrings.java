package com.bananity.util;


// Java Utils
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * @author Andreu Correa Casablanca
 */
public class SearchTermStrings
{
	/**
	 *
	 */
	private final static int THRESHOLD = 2;

	/**
	 *
	 */
	private final static Pattern spacePattern = Pattern.compile("\\s+");

	// ---------------------------------------------------------------------------------------------

	/**
	 *
	 */
	private final String text;

	/**
	 *
	 */
	private ArrayList<String> 					tokens 			= null;

	/**
	 *
	 */
	private HashBag2<String> 					textBag 		= null;

	/**
	 *
	 */
	private HashMap<String, Integer> 			wordsLengths 	= null;

	/**
	 *
	 */
	private HashMap<String, HashBag2<String>> 	wordsBags 		= null;

	/**
	 *
	 */
	private int maxTokenLength = -1;

	/**
	 *
	 */
	private final int threshold;

	// ---------------------------------------------------------------------------------------------

	/**
	 *
	 */
	public SearchTermStrings (final String searchTerm) {
		this(searchTerm, THRESHOLD);
	}

	/**
	 *
	 */
	public SearchTermStrings (final String searchTerm, final int threshold) {
		text 			= searchTerm;
		this.threshold 	= threshold;
	}

	// ---------------------------------------------------------------------------------------------

	/**
	 *
	 */
	public int getMaxTokenLength () {
		if (maxTokenLength == -1) {
			computeTokensLengths();
		}

		return maxTokenLength;
	}

	/**
	 *
	 */
	public HashBag2<String> getTextBag () {
		if (textBag == null) {
			computeBags();
		}

		return textBag;
	}

	/**
	 *
	 */
	public HashSet<String> getUniqueByLength(final int threshold) {
		if (wordsLengths == null) {
			computeTokensLengths();
		}

		if (wordsBags == null) {
			computeBags();
		}

		HashSet<String> result = new HashSet<String>();

		for (Map.Entry<String, Integer> e : wordsLengths.entrySet()) {
			if (e.getValue() <= threshold) {
				result.add( e.getKey() );
			} else {
				for (Map.Entry<String, Integer> wbE : wordsBags.get(e.getKey())) {
					if (wbE.getKey().length() >= threshold) {
						result.add(wbE.getKey());
					}
				}
			}
		}

		return result;
	}

	/**
	 *
	 */
	public String getText () {
		return text;
	}

	// ---------------------------------------------------------------------------------------------

	/**
	 *
	 */
	private void computeBags () {
		if (tokens == null) {
			computeTokens();
		}

		wordsBags = new HashMap<String, HashBag2<String>>();

		ArrayList<String> allSubtokens = new ArrayList<String>();

		for (String token : tokens) {
			ArrayList<String> wordSubtokens = getAllSubstrings(token, Math.min(token.length(), threshold)); // Alternative for threshold : "(maxTokenLength>1)?threshold:1"

			allSubtokens.addAll(wordSubtokens);
			wordsBags.put(token, new HashBag2<String>(wordSubtokens));
		}

		textBag = new HashBag2<String>(allSubtokens);
	}

	/**
	 *
	 */
	private void computeTokensLengths () {
		if (tokens == null) {
			computeTokens();
		}

		wordsLengths = new HashMap<String, Integer>();

		for (String token : tokens) {
			int tokenLength = token.length();

			wordsLengths.put(token, tokenLength);
			if (tokenLength > maxTokenLength) maxTokenLength = tokenLength;
		}
	}

	/**
	 *
	 */
	private void computeTokens () {
		final String[] tokensArray = spacePattern.split(text);

		tokens = new ArrayList<String>(Arrays.asList(tokensArray));

	 	for (int i=0, n=tokensArray.length-1; i<n; i++) {
			tokens.add(
				new StringBuilder(tokensArray[i]).append(" ").append(tokensArray[i+1]).toString()
			);
		}
	}

	// ---------------------------------------------------------------------------------------------

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
}