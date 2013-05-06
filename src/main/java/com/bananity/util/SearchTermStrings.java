package com.bananity.util;


// Bananity Classes
import com.bananity.text.SubstringCalculator;

// Java Utils
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


/**
 * @author Andreu Correa Casablanca
 */
public class SearchTermStrings
{
	/**
	 *
	 */
	private final static int THRESHOLD = 2;

	// ---------------------------------------------------------------------------------------------

	/**
	 *
	 */
	private String text;

	/**
	 *
	 */
	private ArrayList<String> 					tokens 			= null;

	/**
	 *
	 */
	private HashBag<String> 					textBag 		= null;

	/**
	 *
	 */
	private HashMap<String, Integer> 			wordsLengths 	= null;

	/**
	 *
	 */
	private HashMap<String, HashBag<String>> 	wordsBags 		= null;

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
	public HashBag<String> getTextBag () {
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
	public int hashCode () {
		return text.hashCode();
	}

	/**
	 *
	 */
	public boolean equals (Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof SearchTermStrings)) {
			return false;
		} else {
			return (this.text == ((SearchTermStrings)o).text);
		}
	}

	// ---------------------------------------------------------------------------------------------

	/**
	 *
	 */
	private void computeBags () {
		if (tokens == null) {
			computeTokens();
		}

		wordsBags = new HashMap<String, HashBag<String>>();

		ArrayList<String> allSubtokens = new ArrayList<String>();

		for (String token : tokens) {
			ArrayList<String> wordSubtokens;
			if (token.indexOf(" ") == -1) {
				// Alternative for threshold : "(maxTokenLength>1)?threshold:1"
				wordSubtokens = SubstringCalculator.getAllSubstrings(token, Math.min(token.length(), threshold));
			} else {
				wordSubtokens = SubstringCalculator.getWordsPairSubstrings(token, Math.min(token.length(), threshold+2));
			}

			allSubtokens.addAll(wordSubtokens);
			wordsBags.put(token, new HashBag<String>(wordSubtokens));
		}

		textBag = new HashBag<String>(allSubtokens);
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
		tokens = SubstringCalculator.tokenize(text, true);
	}

	// ---------------------------------------------------------------------------------------------

	/**
	 * In theory, this method isn't needed, only coded for security
	 */
	protected void finalize () throws Throwable {
		text 			= null;
		tokens 			= null;
		textBag 		= null;
		wordsLengths 	= null;
		wordsBags 		= null;

		super.finalize();
	}
}