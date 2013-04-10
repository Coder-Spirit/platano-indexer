package com.bananity.util;


// Main Class
import com.bananity.text.TextNormalizer;

// Java Utils
import java.util.ArrayList;

// Junit
import org.junit.Assert;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


/**
 * @author Andreu Correa Casablanca
 */
@RunWith(JUnit4.class)
public class SearchesTokenizerTest
{
	@Test
	public void test_getAllSubstrings_SingleWord () {
		String singleWord = "Bananity";

		String[] _1Array = {
			"Bananity",
			"Bananit","ananity",
			"Banani","ananit","nanity",
			"Banan","anani","nanit","anity",
			"Bana","anan","nani","anit","nity",
			"Ban","ana","nan","ani","nit","ity",
			"Ba","an","na","an","ni","it","ty",
			"B","a","n","a","n","i","t","y"
		};

		String[] _2Array = {
			"Bananity",
			"Bananit","ananity",
			"Banani","ananit","nanity",
			"Banan","anani","nanit","anity",
			"Bana","anan","nani","anit","nity",
			"Ban","ana","nan","ani","nit","ity",
			"Ba","an","na","an","ni","it","ty"
		};

		String[] _3Array = {
			"Bananity",
			"Bananit","ananity",
			"Banani","ananit","nanity",
			"Banan","anani","nanit","anity",
			"Bana","anan","nani","anit","nity",
			"Ban","ana","nan","ani","nit","ity"
		};

		ArrayList<String> _1List = SearchesTokenizer.getAllSubstrings(singleWord, 1);
		ArrayList<String> _2List = SearchesTokenizer.getAllSubstrings(singleWord, 2);
		ArrayList<String> _3List = SearchesTokenizer.getAllSubstrings(singleWord, 3);

		Assert.assertEquals(_1Array.length, _1List.size());
		Assert.assertEquals(_2Array.length, _2List.size());
		Assert.assertEquals(_3Array.length, _3List.size());

		for (String token : _1Array) {
			Assert.assertTrue(_1List.contains(token));
		}

		for (String token : _2Array) {
			Assert.assertTrue(_1List.contains(token));
			Assert.assertTrue(_2List.contains(token));
		}

		for (String token : _3Array) {
			Assert.assertTrue(_1List.contains(token));
			Assert.assertTrue(_2List.contains(token));
			Assert.assertTrue(_3List.contains(token));
		}
	}

	@Test
	public void test_tokenize_singleWord () {
		ArrayList<String> tokenizeUnderCaseResult;
		ArrayList<String> tokenizeUpperCaseResult;
		ArrayList<String> tokenizeMixedCaseResult;

		String undercaseWord = "bananity";
		String upperCaseWord = "BANANITY";
		String mixedCaseWord = "Bananity";

		tokenizeUnderCaseResult = SearchesTokenizer.tokenize(undercaseWord, false);
		Assert.assertEquals(1, tokenizeUnderCaseResult.size());
		Assert.assertEquals("bananity", tokenizeUnderCaseResult.get(0));
		
		tokenizeUnderCaseResult = SearchesTokenizer.tokenize(undercaseWord, false, false);
		Assert.assertEquals(1, tokenizeUnderCaseResult.size());
		Assert.assertEquals("bananity", tokenizeUnderCaseResult.get(0));

		tokenizeUnderCaseResult = SearchesTokenizer.tokenize(undercaseWord, false, true);
		Assert.assertEquals(1, tokenizeUnderCaseResult.size());
		Assert.assertEquals("bananity", tokenizeUnderCaseResult.get(0));

		tokenizeUnderCaseResult = SearchesTokenizer.tokenize(undercaseWord, true);
		Assert.assertEquals(1, tokenizeUnderCaseResult.size());
		Assert.assertEquals("bananity", tokenizeUnderCaseResult.get(0));
		
		tokenizeUnderCaseResult = SearchesTokenizer.tokenize(undercaseWord, true, false);
		Assert.assertEquals(1, tokenizeUnderCaseResult.size());
		Assert.assertEquals("bananity", tokenizeUnderCaseResult.get(0));

		tokenizeUnderCaseResult = SearchesTokenizer.tokenize(undercaseWord, true, true);
		Assert.assertEquals(1, tokenizeUnderCaseResult.size());
		Assert.assertEquals("bananity", tokenizeUnderCaseResult.get(0));

		// TODO : For UpperCase and Mixed Case
	}

	// TODO for TwoWords and ThreeWords

	/*
	@Test
	public void test_getSubTokensList_SingleWord () {
		String singleWord = "Bananity";

		String[] _2Array = {
			"bananity",
			"bananit","ananity",
			"banani","ananit","nanity",
			"banan","anani","nanit","anity",
			"bana","anan","nani","anit","nity",
			"ban","ana","nan","ani","nit","ity",
			"ba","an","na","an","ni","it","ty"
		};

		ArrayList<String> _2List = SearchesTokenizer.getSubTokensList(singleWord);

		Assert.assertEquals(_2Array.length, _2List.size());

		for (String token : _2Array) {
			System.out.println("Testing token : " + token);
			Assert.assertTrue(_2List.contains(token));
		}
	}
	*/
}