package com.bananity.util;

// Main Class
import com.bananity.util.HashBag;

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
public class HashBagTest
{
	@Test
	public void test_equals () {
		// Empty HashBag
		HashBag<String> aHB = new HashBag<String>();
		Assert.assertEquals(0, aHB.size());
		Assert.assertEquals(0, aHB.uniqueItemsCount());

		// HashBag from an array
		HashBag<String> bHB = new HashBag<String>(new String[] {"a", "b", "c"});

		// HashBag from a Collection
		HashBag<String> cHB = new HashBag<String>(Arrays.asList(new String[] {"a", "b", "c"}));
		
		// HashBag from a HashBag
		HashBag<String> dHB = new HashBag<String>(bHB);

		// HashBag from a Bag
		HashBag<String> eHB = new HashBag<String>((IBag)bHB);

		// 
		HashBag<String> fHB = new HashBag<String>(new String[] {"a", "b", "c", "d"});
		HashBag<String> gHB = new HashBag<String>(new String[] {"a", "b", "c", "c"});


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
	public void test_union () {
		HashBag<String> aHB = new HashBag<String>(new String[] {
			"Bty", "Bty", "Love", "Love", "Love", "Hate"
		});

		HashBag<String> bHB = new HashBag<String>(new String[] {
			"Bty", "Love", "Love", "Hate", "Hate", "Hate"
		});

		HashBag<String> abHB = new HashBag<String>(new String[] {
			"Bty", "Bty", "Love", "Love", "Love", "Hate", "Hate", "Hate"
		});

		Assert.assertEquals(aHB, aHB.union(aHB));
		Assert.assertEquals(abHB, aHB.union(bHB));
		Assert.assertEquals(abHB, bHB.union(aHB));
	}

	@Test
	public void test_intersection () {
		HashBag aHB = new HashBag<String>(new String[] {
			"Bty", "Bty", "Love", "Love", "Love", "Hate"
		});

		HashBag<String> bHB = new HashBag<String>(new String[] {
			"Bty", "Love", "Love", "Hate", "Hate", "Hate"
		});

		HashBag<String> abHB = new HashBag<String>(new String[] {
			"Bty", "Love", "Love", "Hate"
		});

		Assert.assertEquals(aHB, aHB.intersection(aHB));
		Assert.assertEquals(abHB, aHB.intersection(bHB));
		Assert.assertEquals(abHB, bHB.intersection(aHB));
	}

	@Test
	public void test_difference () {
		HashBag aHB = new HashBag<String>(new String[] {
			"Bty", "Bty", "Love", "Love", "Love", "Hate"
		});

		HashBag bHB = new HashBag<String>(new String[] {
			"Love", "Love", "Love"
		});

		HashBag<String> abHB = new HashBag<String>(new String[] {
			"Bty", "Bty", "Hate"
		});

		Assert.assertEquals(new HashBag<String>(), aHB.difference(aHB));
		Assert.assertEquals(abHB, aHB.difference(bHB));
		Assert.assertEquals(new HashBag<String>(), bHB.difference(aHB));
	}
}