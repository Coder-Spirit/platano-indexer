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
	public void test_tokenize_UnderCaseSingleWord () {
		String underCaseWord = "bananity";
		ArrayList<String> tokenizeUnderCaseResult;


		tokenizeUnderCaseResult = SearchesTokenizer.tokenize(underCaseWord, false);
		Assert.assertEquals(1, tokenizeUnderCaseResult.size());
		Assert.assertEquals("bananity", tokenizeUnderCaseResult.get(0));
		
		tokenizeUnderCaseResult = SearchesTokenizer.tokenize(underCaseWord, false, false);
		Assert.assertEquals(1, tokenizeUnderCaseResult.size());
		Assert.assertEquals("bananity", tokenizeUnderCaseResult.get(0));

		tokenizeUnderCaseResult = SearchesTokenizer.tokenize(underCaseWord, false, true);
		Assert.assertEquals(1, tokenizeUnderCaseResult.size());
		Assert.assertEquals("bananity", tokenizeUnderCaseResult.get(0));

		tokenizeUnderCaseResult = SearchesTokenizer.tokenize(underCaseWord, true);
		Assert.assertEquals(1, tokenizeUnderCaseResult.size());
		Assert.assertEquals("bananity", tokenizeUnderCaseResult.get(0));
		
		tokenizeUnderCaseResult = SearchesTokenizer.tokenize(underCaseWord, true, false);
		Assert.assertEquals(1, tokenizeUnderCaseResult.size());
		Assert.assertEquals("bananity", tokenizeUnderCaseResult.get(0));

		tokenizeUnderCaseResult = SearchesTokenizer.tokenize(underCaseWord, true, true);
		Assert.assertEquals(1, tokenizeUnderCaseResult.size());
		Assert.assertEquals("bananity", tokenizeUnderCaseResult.get(0));
	}

	@Test
	public void test_tokenize_MixedCaseSingleWord () {
		String mixedCaseWord = "Bananity";
		ArrayList<String> tokenizeMixedCaseResult;


		tokenizeMixedCaseResult = SearchesTokenizer.tokenize(mixedCaseWord, false);
		Assert.assertEquals(1, tokenizeMixedCaseResult.size());
		Assert.assertEquals("Bananity", tokenizeMixedCaseResult.get(0));
		
		tokenizeMixedCaseResult = SearchesTokenizer.tokenize(mixedCaseWord, false, false);
		Assert.assertEquals(1, tokenizeMixedCaseResult.size());
		Assert.assertEquals("Bananity", tokenizeMixedCaseResult.get(0));

		tokenizeMixedCaseResult = SearchesTokenizer.tokenize(mixedCaseWord, false, true);
		Assert.assertEquals(1, tokenizeMixedCaseResult.size());
		Assert.assertEquals("Bananity", tokenizeMixedCaseResult.get(0));

		tokenizeMixedCaseResult = SearchesTokenizer.tokenize(mixedCaseWord, true);
		Assert.assertEquals(1, tokenizeMixedCaseResult.size());
		Assert.assertEquals("bananity", tokenizeMixedCaseResult.get(0));
		
		tokenizeMixedCaseResult = SearchesTokenizer.tokenize(mixedCaseWord, true, false);
		Assert.assertEquals(1, tokenizeMixedCaseResult.size());
		Assert.assertEquals("bananity", tokenizeMixedCaseResult.get(0));

		tokenizeMixedCaseResult = SearchesTokenizer.tokenize(mixedCaseWord, true, true);
		Assert.assertEquals(1, tokenizeMixedCaseResult.size());
		Assert.assertEquals("bananity", tokenizeMixedCaseResult.get(0));
	}

	@Test
	public void test_tokenize_UpperCaseSingleWord () {
		String upperCaseWord = "BANANITY";
		ArrayList<String> tokenizeUpperCaseResult;


		tokenizeUpperCaseResult = SearchesTokenizer.tokenize(upperCaseWord, false);
		Assert.assertEquals(1, tokenizeUpperCaseResult.size());
		Assert.assertEquals("BANANITY", tokenizeUpperCaseResult.get(0));
		
		tokenizeUpperCaseResult = SearchesTokenizer.tokenize(upperCaseWord, false, false);
		Assert.assertEquals(1, tokenizeUpperCaseResult.size());
		Assert.assertEquals("BANANITY", tokenizeUpperCaseResult.get(0));

		tokenizeUpperCaseResult = SearchesTokenizer.tokenize(upperCaseWord, false, true);
		Assert.assertEquals(1, tokenizeUpperCaseResult.size());
		Assert.assertEquals("BANANITY", tokenizeUpperCaseResult.get(0));

		tokenizeUpperCaseResult = SearchesTokenizer.tokenize(upperCaseWord, true);
		Assert.assertEquals(1, tokenizeUpperCaseResult.size());
		Assert.assertEquals("bananity", tokenizeUpperCaseResult.get(0));
		
		tokenizeUpperCaseResult = SearchesTokenizer.tokenize(upperCaseWord, true, false);
		Assert.assertEquals(1, tokenizeUpperCaseResult.size());
		Assert.assertEquals("bananity", tokenizeUpperCaseResult.get(0));

		tokenizeUpperCaseResult = SearchesTokenizer.tokenize(upperCaseWord, true, true);
		Assert.assertEquals(1, tokenizeUpperCaseResult.size());
		Assert.assertEquals("bananity", tokenizeUpperCaseResult.get(0));
	}

	@Test
	public void test_tokenize_MixedCaseTwoWords () {
		String mixedCaseTwoWords = "Love Hate";
		ArrayList<String> tokenizeMixedCaseTwoWordsResult;


		tokenizeMixedCaseTwoWordsResult = SearchesTokenizer.tokenize(mixedCaseTwoWords, false);
		Assert.assertEquals(2, tokenizeMixedCaseTwoWordsResult.size());
		Assert.assertTrue(tokenizeMixedCaseTwoWordsResult.contains("Love"));
		Assert.assertTrue(tokenizeMixedCaseTwoWordsResult.contains("Hate"));
		
		tokenizeMixedCaseTwoWordsResult = SearchesTokenizer.tokenize(mixedCaseTwoWords, false, false);
		Assert.assertEquals(2, tokenizeMixedCaseTwoWordsResult.size());
		Assert.assertTrue(tokenizeMixedCaseTwoWordsResult.contains("Love"));
		Assert.assertTrue(tokenizeMixedCaseTwoWordsResult.contains("Hate"));

		tokenizeMixedCaseTwoWordsResult = SearchesTokenizer.tokenize(mixedCaseTwoWords, false, true);
		Assert.assertEquals(3, tokenizeMixedCaseTwoWordsResult.size());
		Assert.assertTrue(tokenizeMixedCaseTwoWordsResult.contains("Love"));
		Assert.assertTrue(tokenizeMixedCaseTwoWordsResult.contains("Hate"));
		Assert.assertTrue(tokenizeMixedCaseTwoWordsResult.contains("Love Hate"));

		tokenizeMixedCaseTwoWordsResult = SearchesTokenizer.tokenize(mixedCaseTwoWords, true);
		Assert.assertEquals(2, tokenizeMixedCaseTwoWordsResult.size());
		Assert.assertTrue(tokenizeMixedCaseTwoWordsResult.contains("love"));
		Assert.assertTrue(tokenizeMixedCaseTwoWordsResult.contains("hate"));
		
		tokenizeMixedCaseTwoWordsResult = SearchesTokenizer.tokenize(mixedCaseTwoWords, true, false);
		Assert.assertEquals(2, tokenizeMixedCaseTwoWordsResult.size());
		Assert.assertTrue(tokenizeMixedCaseTwoWordsResult.contains("love"));
		Assert.assertTrue(tokenizeMixedCaseTwoWordsResult.contains("hate"));

		tokenizeMixedCaseTwoWordsResult = SearchesTokenizer.tokenize(mixedCaseTwoWords, true, true);
		Assert.assertEquals(3, tokenizeMixedCaseTwoWordsResult.size());
		Assert.assertTrue(tokenizeMixedCaseTwoWordsResult.contains("love"));
		Assert.assertTrue(tokenizeMixedCaseTwoWordsResult.contains("hate"));
		Assert.assertTrue(tokenizeMixedCaseTwoWordsResult.contains("love hate"));
	}

	@Test
	public void test_tokenize_MixedCaseThreeWords () {
		String mixedCaseThreeWords = "The Banana Society";
		ArrayList<String> tokenizeMixedCaseThreeWordsResult;


		tokenizeMixedCaseThreeWordsResult = SearchesTokenizer.tokenize(mixedCaseThreeWords, false);
		Assert.assertEquals(3, tokenizeMixedCaseThreeWordsResult.size());
		Assert.assertTrue(tokenizeMixedCaseThreeWordsResult.contains("The"));
		Assert.assertTrue(tokenizeMixedCaseThreeWordsResult.contains("Banana"));
		Assert.assertTrue(tokenizeMixedCaseThreeWordsResult.contains("Society"));
		
		tokenizeMixedCaseThreeWordsResult = SearchesTokenizer.tokenize(mixedCaseThreeWords, false, false);
		Assert.assertEquals(3, tokenizeMixedCaseThreeWordsResult.size());
		Assert.assertTrue(tokenizeMixedCaseThreeWordsResult.contains("The"));
		Assert.assertTrue(tokenizeMixedCaseThreeWordsResult.contains("Banana"));
		Assert.assertTrue(tokenizeMixedCaseThreeWordsResult.contains("Society"));

		tokenizeMixedCaseThreeWordsResult = SearchesTokenizer.tokenize(mixedCaseThreeWords, false, true);
		Assert.assertEquals(5, tokenizeMixedCaseThreeWordsResult.size());
		Assert.assertTrue(tokenizeMixedCaseThreeWordsResult.contains("The"));
		Assert.assertTrue(tokenizeMixedCaseThreeWordsResult.contains("Banana"));
		Assert.assertTrue(tokenizeMixedCaseThreeWordsResult.contains("Society"));
		Assert.assertTrue(tokenizeMixedCaseThreeWordsResult.contains("The Banana"));
		Assert.assertTrue(tokenizeMixedCaseThreeWordsResult.contains("Banana Society"));

		tokenizeMixedCaseThreeWordsResult = SearchesTokenizer.tokenize(mixedCaseThreeWords, true);
		Assert.assertEquals(3, tokenizeMixedCaseThreeWordsResult.size());
		Assert.assertTrue(tokenizeMixedCaseThreeWordsResult.contains("the"));
		Assert.assertTrue(tokenizeMixedCaseThreeWordsResult.contains("banana"));
		Assert.assertTrue(tokenizeMixedCaseThreeWordsResult.contains("society"));
		
		tokenizeMixedCaseThreeWordsResult = SearchesTokenizer.tokenize(mixedCaseThreeWords, true, false);
		Assert.assertEquals(3, tokenizeMixedCaseThreeWordsResult.size());
		Assert.assertTrue(tokenizeMixedCaseThreeWordsResult.contains("the"));
		Assert.assertTrue(tokenizeMixedCaseThreeWordsResult.contains("banana"));
		Assert.assertTrue(tokenizeMixedCaseThreeWordsResult.contains("society"));

		tokenizeMixedCaseThreeWordsResult = SearchesTokenizer.tokenize(mixedCaseThreeWords, true, true);
		Assert.assertEquals(5, tokenizeMixedCaseThreeWordsResult.size());
		Assert.assertTrue(tokenizeMixedCaseThreeWordsResult.contains("the"));
		Assert.assertTrue(tokenizeMixedCaseThreeWordsResult.contains("banana"));
		Assert.assertTrue(tokenizeMixedCaseThreeWordsResult.contains("society"));
		Assert.assertTrue(tokenizeMixedCaseThreeWordsResult.contains("the banana"));
		Assert.assertTrue(tokenizeMixedCaseThreeWordsResult.contains("banana society"));
	}

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
			Assert.assertTrue(_2List.contains(token));
		}
	}
}