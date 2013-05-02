package com.bananity.util;


// Main class to test
import com.bananity.util.SortedLists;

// Java Utils
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

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
public class SortedListsTest
{
	@Test
	public void test_sortedInsert () {
		final Comparator c 				= new SimpleIntegerComparator();
		final ArrayList<Integer> al1 	= new ArrayList<Integer>();

		// This block of asserts covers the case where size < limit
		Assert.assertTrue(SortedLists.sortedInsert(c, al1, 6, 5));
		Assert.assertEquals(al1, Arrays.asList(new Integer[] {
			5
		}));

		Assert.assertTrue(SortedLists.sortedInsert(c, al1, 6, 7));
		Assert.assertEquals(al1, Arrays.asList(new Integer[] {
			5, 7
		}));

		Assert.assertTrue(SortedLists.sortedInsert(c, al1, 6, 3));
		Assert.assertEquals(al1, Arrays.asList(new Integer[] {
			3, 5, 7
		}));

		Assert.assertTrue(SortedLists.sortedInsert(c, al1, 6, 9));
		Assert.assertEquals(al1, Arrays.asList(new Integer[] {
			3, 5, 7, 9
		}));

		Assert.assertTrue(SortedLists.sortedInsert(c, al1, 6, 15));
		Assert.assertEquals(al1, Arrays.asList(new Integer[] {
			3, 5, 7, 9, 15
		}));

		// We clone the list to check different behaviors
		final ArrayList<Integer> al2 = new ArrayList<Integer>(al1);
		final ArrayList<Integer> al3 = new ArrayList<Integer>(al1);

		// This block of asserts covers the case where size is near limit
		Assert.assertTrue(SortedLists.sortedInsert(c, al1, 6, 8));
		Assert.assertEquals(al1, Arrays.asList(new Integer[] {
			3, 5, 7, 8, 9, 15
		}));

		Assert.assertTrue(SortedLists.sortedInsert(c, al2, 6, 10));
		Assert.assertEquals(al2, Arrays.asList(new Integer[] {
			3, 5, 7, 9, 10, 15
		}));

		Assert.assertTrue(SortedLists.sortedInsert(c, al3, 6, 16));
		Assert.assertEquals(al3, Arrays.asList(new Integer[] {
			3, 5, 7, 9, 15, 16
		}));

		// This block of assets covers the case where size == limit
		Assert.assertFalse(SortedLists.sortedInsert(c, al1, 6, 16));
		Assert.assertEquals(al1, Arrays.asList(new Integer[] {
			3, 5, 7, 8, 9, 15
		}));

		Assert.assertTrue(SortedLists.sortedInsert(c, al1, 6, 1));
		Assert.assertEquals(al1, Arrays.asList(new Integer[] {
			1, 3, 5, 7, 8, 9
		}));

		Assert.assertTrue(SortedLists.sortedInsert(c, al2, 6, 12));
		Assert.assertEquals(al2, Arrays.asList(new Integer[] {
			3, 5, 7, 9, 10, 12
		}));

		Assert.assertTrue(SortedLists.sortedInsert(c, al2, 6, 9));
		Assert.assertEquals(al2, Arrays.asList(new Integer[] {
			3, 5, 7, 9, 9, 10
		}));
	}
}