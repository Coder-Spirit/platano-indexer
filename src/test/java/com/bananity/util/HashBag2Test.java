package com.bananity.util;

// Main Class
import com.bananity.util.HashBag2;

// Java Utils
import java.util.Arrays;

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
public class HashBag2Test
{
	@Test
	public void test_equals () {
		// Empty HashBag
		HashBag2<String> aHB = new HashBag2<String>();
		Assert.assertEquals(0, aHB.size());
		Assert.assertEquals(0, aHB.uniqueItemsCount());

		// HashBag from an array
		HashBag2<String> bHB = new HashBag2<String>(new String[] {"a", "b", "c"});

		// HashBag from a Collection
		HashBag2<String> cHB = new HashBag2<String>(Arrays.asList(new String[] {"a", "b", "c"}));
		
		// HashBag from a HashBag
		HashBag2<String> dHB = new HashBag2<String>(bHB);

		// HashBag from a Bag
		HashBag2<String> eHB = new HashBag2<String>((Bag)bHB);

		// 
		HashBag2<String> fHB = new HashBag2<String>(new String[] {"a", "b", "c", "d"});
		HashBag2<String> gHB = new HashBag2<String>(new String[] {"a", "b", "c", "c"});


		// Checking equality (and it's symmetry)
		Assert.assertEquals(bHB, cHB);
		Assert.assertEquals(cHB, bHB);

		Assert.assertEquals(cHB, dHB);
		Assert.assertEquals(dHB, cHB);

		Assert.assertEquals(dHB, eHB);
		Assert.assertEquals(eHB, dHB);

		Assert.assertEquals(eHB, bHB);
		Assert.assertEquals(bHB, eHB);

		// Checking equality (just it's reflexivity)
		Assert.assertEquals(aHB, aHB);
		Assert.assertEquals(bHB, bHB);
		Assert.assertEquals(cHB, cHB);
		Assert.assertEquals(dHB, dHB);
		Assert.assertEquals(eHB, eHB);

		// Checking inequality
		Assert.assertNotEquals(aHB, bHB);
		Assert.assertNotEquals(aHB, cHB);
		Assert.assertNotEquals(aHB, dHB);
		Assert.assertNotEquals(aHB, eHB);

		Assert.assertNotEquals(bHB, fHB);
		Assert.assertNotEquals(bHB, gHB);
		Assert.assertNotEquals(fHB, gHB);

		// Checking inequality between different classes
		Assert.assertNotEquals(aHB, new Integer(1));
	}

	@Test
	public void test_add () {
		HashBag2<String> aHB = new HashBag2<String>();
		Assert.assertFalse(aHB.contains("Bananity"));
		Assert.assertFalse(aHB.contains("Pinterest"));

		aHB.add("Bananity", 3);
		Assert.assertTrue(aHB.contains("Bananity"));
		Assert.assertEquals(3, aHB.getTimes("Bananity"));
		Assert.assertEquals(3, aHB.size());
		Assert.assertEquals(1, aHB.uniqueItemsCount());

		aHB.add("Pinterest", 5);
		Assert.assertTrue(aHB.contains("Pinterest"));
		Assert.assertEquals(5, aHB.getTimes("Pinterest"));
		Assert.assertEquals(8, aHB.size());
		Assert.assertEquals(2, aHB.uniqueItemsCount());

		aHB.add("Bananity", 1);
		Assert.assertEquals(4, aHB.getTimes("Bananity"));
		Assert.assertEquals(9, aHB.size());
		Assert.assertEquals(2, aHB.uniqueItemsCount());

		aHB.add("Pinterest", 0);
		Assert.assertEquals(5, aHB.getTimes("Pinterest"));
		Assert.assertEquals(9, aHB.size());
		Assert.assertEquals(2, aHB.uniqueItemsCount());
	}
}