package com.bananity.util;


import com.bananity.util.SearchTerm;
import com.bananity.util.SearchTermFactory;

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
public class SearchTermFactoryTest
{
	@Test
	public void test_get () {
		String k1 = new String("Key");
		String k2 = new String("Key");

		SearchTerm st1 = SearchTermFactory.get(k1);
		SearchTerm st2 = SearchTermFactory.get(k2);
		SearchTerm st3 = new SearchTerm("Key");

		Assert.assertSame(st1, st2);
		Assert.assertEquals(st1, st3);
	}
}