package com.bananity.util;

// Main Class
import com.bananity.util.SearchTermStrings;

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

		HashBag2<String> 	hb_B 	= new HashBag2<String>(new String[] {
			"Banana Society",
			"The Banana"
		});
	}
}