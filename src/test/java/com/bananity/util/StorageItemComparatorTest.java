package com.bananity.util;


// Main Class
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
		StorageItemComparator c1 = new StorageItemComparator("bananity");
		StorageItemComparator c2 = new StorageItemComparator("xananity");

		// Testing equality
		Assert.assertEquals(0, c1.compare("Bananity", "Bananity"));

		// Testing lexicographic and `indexOf` order
		Assert.assertEquals(1, c1.compare("Cananity", "Bananity"));
		Assert.assertEquals(-1, c1.compare("Bananity", "Cananity"));

		// Testing size, lexicographic and `indexOf` order 
		Assert.assertEquals(1, c1.compare("Bananity On", "Bananity"));
		Assert.assertEquals(-1, c1.compare("Bananity", "Bananity On"));

		// Testing `indexOf` order
		Assert.assertEquals(-1 , c2.compare("Xananity", "Bananity"));
		Assert.assertEquals(1 , c2.compare("Bananity", "Xananity"));
	}
}