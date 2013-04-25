package com.bananity.util;

// Main Class
import com.bananity.util.SearchTermStrings;

// Java Utils
import java.util.Arrays;
import java.util.HashSet;

// Junit
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


/**
 * @author Andreu Correa Casablanca
 */
@RunWith(JUnit4.class)
public class SearchTermStringsTest
{
	@Test
	public void test_getMaxTokenLength () {
		SearchTermStrings sts_A = new SearchTermStrings("");
		Assert.assertEquals(0, sts_A.getMaxTokenLength());

		SearchTermStrings sts_B = new SearchTermStrings("Bananity");
		Assert.assertEquals(8, sts_B.getMaxTokenLength());

		SearchTermStrings sts_C = new SearchTermStrings("The Banana Society");
		Assert.assertEquals(14, sts_C.getMaxTokenLength());
	}

	@Test
	public void test_getText () {
		SearchTermStrings sts_A = new SearchTermStrings("Bananity");
		Assert.assertEquals("Bananity", sts_A.getText());
	}

	@Test
	public void test_getTextBag () {
		SearchTermStrings 	sts_A 	= new SearchTermStrings("The Banana Society");
		HashBag2<String> 	hb_A 	= sts_A.getTextBag();

		HashBag2<String> 	hb_Test = new HashBag2<String>(new String[] {
			"The Banana",
			"The Banan", "The Bana", "The Ban", "The Ba", "The B",
			"he Banana", "he Banan", "he Bana", "he Ban", "he Ba", "he B",
			"e Banana", "e Banan", "e Bana", "e Ban", "e Ba",
			"Banana Society",
			"Banana Societ", "Banana Socie", "Banana Soci", "Banana Soc", "Banana So", "Banana S",
			"anana Society", "anana Societ", "anana Socie", "anana Soci", "anana Soc", "anana So", "anana S",
			"nana Society", "nana Societ", "nana Socie", "nana Soci", "nana Soc", "nana So", "nana S",
			"ana Society", "ana Societ", "ana Socie", "ana Soci", "ana Soc", "ana So", "ana S",
			"na Society", "na Societ", "na Socie", "na Soci", "na Soc", "na So", "na S",
			"a Society", "a Societ", "a Socie", "a Soci", "a Soc", "a So",
			"The", "Th", "he",
			"Banana", "Banan", "Bana", "Ban", "Ba",
			"anana", "anan", "ana", "an",
			"nana", "nan", "na",
			"ana", "an",
			"na",
			"Society", "Societ", "Socie", "Soci", "Soc", "So",
			"ociety", "ociet", "ocie", "oci", "oc",
			"ciety", "ciet", "cie", "ci",
			"iety", "iet", "ie",
			"ety", "et",
			"ty"
		});
		
		Assert.assertEquals(hb_Test, hb_A);
	}

	@Test
	public void test_getUniqueByLength () {
		SearchTermStrings 	sts_A 	= new SearchTermStrings("The Banana Society");
		HashSet<String> 	hs_A 	= sts_A.getUniqueByLength(7);

		HashSet<String> 	hs_Test = new HashSet<String>(Arrays.asList(new String[] {
			"The Banana",
			"The Banan", "The Bana", "The Ban",
			"he Banana", "he Banan", "he Bana",
			"e Banana", "e Banan",
			"Banana Society",
			"Banana Societ", "Banana Socie", "Banana Soci", "Banana Soc", "Banana So", "Banana S",
			"anana Society", "anana Societ", "anana Socie", "anana Soci", "anana Soc", "anana So", "anana S",
			"nana Society", "nana Societ", "nana Socie", "nana Soci", "nana Soc", "nana So",
			"ana Society", "ana Societ", "ana Socie", "ana Soci", "ana Soc",
			"na Society", "na Societ", "na Socie", "na Soci",
			"a Society", "a Societ", "a Socie",
			"The",
			"Banana",
			"Society"
		}));

		Assert.assertEquals(hs_Test, hs_A);
	}
}