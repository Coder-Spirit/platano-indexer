package com.bananity.util;


import com.bananity.util.SearchTerm;
import com.bananity.util.StorageItemComparator;

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
public class StorageItemComparatorTest
{
	@Test
	public void test_compare () {
		SearchTerm _Bananity_St 	= new SearchTerm("Bananity");
		SearchTerm _bananity_St 	= new SearchTerm("bananity");
		SearchTerm _Bananity_On_St 	= new SearchTerm("Bananity On");
		SearchTerm _Cananity_St 	= new SearchTerm("Cananity");
		SearchTerm _Xananity_St 	= new SearchTerm("Xananity");
		SearchTerm _xananity_St 	= new SearchTerm("xananity");

		StorageItemComparator c1 = new StorageItemComparator("bananity");
		StorageItemComparator c2 = new StorageItemComparator("xananity");

		// Testing equality
		Assert.assertEquals(0, c1.compare(_Bananity_St, _Bananity_St));

		// Testing lexicographic and `indexOf` order
		Assert.assertEquals(1, c1.compare(_Cananity_St, _Bananity_St));
		Assert.assertEquals(-1, c1.compare(_Bananity_St, _Cananity_St));

		// Testing size, lexicographic and `indexOf` order 
		Assert.assertEquals(1, c1.compare(_Bananity_On_St, _Bananity_St));
		Assert.assertEquals(-1, c1.compare(_Bananity_St, _Bananity_On_St));

		// Testing `indexOf` order
		Assert.assertEquals(-1 , c2.compare(_Xananity_St, _Bananity_St));
		Assert.assertEquals(1 , c2.compare(_Bananity_St, _Xananity_St));
	}
}