package com.bananity.text;


// Java Utils
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;


/**
 * @author Andreu Correa Casablanca
 */
public class SubstringCalculator
{
	/**
	 *
	 */
	private final static Pattern spacePattern = Pattern.compile("\\s+");

	/**
	 *
	 */
	public static ArrayList<String> getAllSubstrings (String token, int minLength) {
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
	public static ArrayList<String> getWordsPairSubstrings (String token, int minLength) {
		final String[] pair = spacePattern.split(token);

		int leftLength 	= pair[0].length();
		int rightLength = pair[1].length();
		int spacePos 	= token.indexOf(" ");

		ArrayList<String> result = new ArrayList<String>();
		
		for (int lp=leftLength; lp > 0; lp--) {
			for (int rp=rightLength; (rp > 0) && (1+lp+rp >= minLength); rp--) {
				result.add(token.substring(spacePos-lp, spacePos+1+rp));
			}
		}

		return result;
	}

	/**
	 *
	 */
	public static ArrayList<String> tokenize (String text, boolean addWordPairs) {
		final String[] tokensArray = spacePattern.split(text);

		ArrayList<String> tokens = new ArrayList<String>(Arrays.asList(tokensArray));

	 	if (addWordPairs) {
	 		for (int i=0, n=tokensArray.length-1; i<n; i++) {
				tokens.add(
					new StringBuilder(tokensArray[i]).append(" ").append(tokensArray[i+1]).toString()
				);
			}
	 	}

		return tokens;
	}
}