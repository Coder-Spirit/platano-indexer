package com.bananity.nlp;

import com.bananity.util.HashBag;

import java.util.Map;
import java.util.List;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;

import java.text.Normalizer;
import java.text.Normalizer.Form;

import java.io.StringReader;

import org.apache.lucene.util.Version;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;


public class AnalyzerModule {

	private static final String DUMMY = null;
	private static final Version v = Version.LUCENE_40;

	private static Analyzer getAnalyzer () {
		return new StandardAnalyzer( v, CharArraySet.EMPTY_SET );
	}

	private static String removeAccents( String text ) {
		return ( text == null )? "" : Normalizer.normalize(text, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}

	private static ArrayList<String> analyze( Analyzer a, String input, boolean normalize ) throws Exception {
		ArrayList<String> result = new ArrayList<String>();
		TokenStream stream  = a.tokenStream( DUMMY , new StringReader( input ) );
		while( stream.incrementToken() ) {
			result.add( (normalize)? removeAccents( stream.getAttribute(CharTermAttribute.class).toString().toLowerCase() ) : stream.getAttribute(CharTermAttribute.class).toString() );
		}
		return result;
	}

	public static ArrayList<String> analyze( String input, boolean normalize ) throws Exception {
		return analyze( getAnalyzer(), input, normalize );
	}

	public static String normalize( String input ) {
		return removeAccents( input.toLowerCase().replaceAll( "[ ]+", "_" ) );
	}

	public static ArrayList<String> getAllSubstrings( String token, int minLength ) {
		int l = token.length();
		ArrayList<String> t = new ArrayList<String>();

		if (l < minLength) {
			return t;
		}

		minLength--;

		for ( int c = 0; c < l; c++ ) {
			for ( int r = minLength; r < l - c; r++ ){
				t.add( token.substring( c, c + r + 1 ) );
			}
		}
		return t;
	}

	public static HashBag<String> getSubstringsBag( String token, int minLength ) {
		HashBag<String> result = new HashBag<String>();

		int l = token.length();
		if (l < minLength) {
			return result;
		}

		minLength--;

		for ( int c = 0; c < l; c++ ) {
			for ( int r = minLength; r < l - c; r++ ) {
				result.put ( token.substring( c, c + r + 1 ) );
			}
		}

		return result;
	}
}
