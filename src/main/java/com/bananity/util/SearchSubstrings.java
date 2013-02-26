package com.bananity.util;


import com.bananity.nlp.AnalyzerModule;

import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;


public class SearchSubstrings {

	private ArrayList<String> tokens;
	private ArrayList<Integer> tokensMaxLength;
	private ArrayList<HashBag<String>> tokensSubs; // substrings
	private HashBag<String> hashBag;
	private int maxTokenLength;

	private static final int THRESHOLD = 2;

	public SearchSubstrings (String searchTerm) throws Exception {
		this(searchTerm, THRESHOLD);
	}

	public SearchSubstrings (String searchTerm, int threshold) throws Exception {
		this(AnalyzerModule.analyze(searchTerm, true), threshold, true);
	}

	public SearchSubstrings( ArrayList<String> basicTokens ) {
		this( basicTokens, THRESHOLD, true );
	}

	public SearchSubstrings( ArrayList<String> basicTokens, int threshold, boolean addWordPairs ) {
		tokens = basicTokens;
		maxTokenLength = 0;
		hashBag = new HashBag<String>();
		tokensMaxLength = new ArrayList<Integer>();
		tokensSubs = new ArrayList<HashBag<String>>();
		
		for ( String s : tokens ) {
			maxTokenLength = Math.max( maxTokenLength, s.length() );
		}

		for ( String s : tokens ) {
			tokensMaxLength.add( s.length() );
			HashBag<String> tmp = AnalyzerModule.getSubstringsBag( s, (maxTokenLength>1)?threshold:1 );
			tokensSubs.add( tmp );
			hashBag.addAll( tmp );
		}

		// This block is a hack to add word pairs
		if (addWordPairs) {
			String pairFirst  	= null;
			String pairSecond 	= null;
			String pair 		= null;
			String s;

			ArrayList<String> 	al;
			HashBag<String> 	hb;

			for (int i=0, n=tokens.size(); i<n; i++) {
				s = tokens.get(i);

				if (pairFirst == null) {
					pairFirst = s;
				} else {
					pairSecond = s;
					pair = new StringBuilder(pairFirst).append(' ').append(pairSecond).toString();
					
					al = new ArrayList<String>();
					al.add(pair);
					hb = new HashBag<String>(al);

					maxTokenLength = Math.max( maxTokenLength, pair.length() );

					tokens.add(pair);
					tokensMaxLength.add( pair.length() );
					tokensSubs.add( hb );
					hashBag.addAll( hb );

					pairFirst = pairSecond;
				}
			}
		}
	}

	public int getMaxTokenLength() {
		return maxTokenLength;
	}

	public HashBag<String> getHashBag() {
		return hashBag;
	}

	public HashSet<String> getUniqByLength( int threshold ) {
		HashSet s = new HashSet();
		for ( int i = 0; i < tokens.size(); i++ ) {
			if ( tokensMaxLength.get( i ) <= threshold ) {
				s.add( tokens.get( i ) );
			} else {
				for ( String ss : tokensSubs.get( i ).getUniqElements() ) {
					if ( ss.length() >= threshold ) {
						s.add( ss );
					}
				}
			}
		}
		return s;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder( "[" );
		for ( int i = 0; i < tokens.size(); i++ ) {
			if ( i > 0 ) {
				sb.append( ", " );
			}
			
			sb.append( "{global: " )
				.append( hashBag.toString() )
				.append( ", token: " )
				.append( tokens.get( i ) )
				.append( ", length: " )
				.append( tokensMaxLength.get( i ) )
				.append( ", substrings: " )
				.append( tokensSubs.get( i ).toString() )
				.append( "}" );
		}
		sb.append( "]" );
		return sb.toString();
	}

}
