package com.bananity.util;


// Main Class
import com.bananity.util.ResultItemComparator;
import com.bananity.util.SearchTerm;

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
public class ResultItemComparatorTest
{
	@Test
	public void test_compare () {
		ResultItemComparator c1 = new ResultItemComparator(SearchesTokenizer.getSubTokensBag("Bananity"));
		ResultItemComparator c2 = new ResultItemComparator(SearchesTokenizer.getSubTokensBag("bananity"));

		// Equality comparisons
		Assert.assertEquals(0, c1.compare("Bananity", "Bananity"));
		Assert.assertEquals(0, c1.compare("Xananity", "Xananity"));
		Assert.assertEquals(0, c2.compare("Bananity", "Bananity"));
		Assert.assertEquals(0, c2.compare("Xananity", "Xananity"));

		// More Interesting comparisons
		Assert.assertEquals(1, c1.compare("Bananity On", "Bananity"));
		Assert.assertEquals(-1, c1.compare("Bananity", "Bananity On"));
	}

	@Test
	public void test_compare_regression1 () {
		ResultItemComparator c1 = new ResultItemComparator(SearchesTokenizer.getSubTokensBag("el viaje de la pegatina"));
		Assert.assertEquals(-1, c1.compare("El Viaje De La Pegatina", "De la"));

		ResultItemComparator c2 = new ResultItemComparator(SearchesTokenizer.getSubTokensBag("huevos fritos"));
		Assert.assertEquals(-1, c2.compare("huevos fritos", "Fritos"));
	}
}