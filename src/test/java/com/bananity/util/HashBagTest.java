package com.bananity.util;


// Main Class
import com.bananity.util.HashBag;

// Java Utils
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

// Junit
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


/**
 * @author Andreu Correa Casablanca
 */
@RunWith(Parameterized.class)
public class HashBagTest
{
	private HashBag 	hb;
	private ArrayList 	al;

	/**
	 * This value must not be used in the tests
	 */
	private final static Integer 	intNotIn = 0;

	/**
	 * This value must not be used in the tests
	 */
	private final static String 	strNotIn = "NOTIN";

	public HashBagTest (HashBag hb, ArrayList al) {
		this.hb = hb;
		this.al = al;
	}

	@Parameters
	public static Collection<Object[]> data () {
		ArrayList<Integer> bAL = new ArrayList<Integer>(
			Arrays.asList(new Integer[] {
				1, 2, 1, 4, 3, 3
			})
		);

		HashBag<Integer> aHB = new HashBag<Integer>();
		HashBag<Integer> bHB = new HashBag<Integer>(bAL);

		for (Integer i : bAL) {
			aHB.put(i);
		}

		HashBag<Integer> cHB = new HashBag<Integer>();
		HashBag<Integer> dHB = new HashBag<Integer>();

		cHB.addAll(bAL);
		dHB.addAll(aHB);

		return Arrays.asList(new Object[][] {
			{aHB, bAL}, {bHB, bAL}, {cHB, bAL}, {dHB, bAL}
		});
	}

	@Test
	public void test_size () {
		Assert.assertEquals(al.size(), hb.size());
	}

	@Test
	public void test_getTimes () {
		for (Object o : al) {
			Assert.assertEquals(Collections.frequency(al, o), hb.getTimes(o));
		}

		if (al.get(0) instanceof Integer) {
			Assert.assertEquals(0, hb.getTimes(intNotIn));
		} else if (al.get(0) instanceof String) {
			Assert.assertEquals(0, hb.getTimes(strNotIn));
		}
	}

	@Test
	public void test_contains () {
		for (Object o : al) {
			Assert.assertTrue(hb.contains(o));
		}
		
		if (al.get(0) instanceof Integer) {
			Assert.assertFalse(hb.contains(intNotIn));
		} else if (al.get(0) instanceof String) {
			Assert.assertFalse(hb.contains(strNotIn));
		}
	}

	@Test
	public void test_remove () {
		HashBag hb = (HashBag)this.hb.clone();

		int hbSize = hb.size();

		for (Object o : al) {
			int oTimes = hb.getTimes(o);

			hb.remove(o);
			Assert.assertEquals(hbSize-1, hb.size());
			Assert.assertEquals(oTimes-1, hb.getTimes(o));
			if (oTimes-1 == 0) {
				Assert.assertFalse(hb.contains(o));
			}

			hbSize--;
		}
	}

	@Test
	public void test_toArrayList () {
		Assert.assertEquals(hb.size(), hb.toArrayList().size());
	}

	@Test
	public void test_toUniqueArrayList () {
		ArrayList al = hb.toUniqueArrayList();

		for (Object o : al) {
			Assert.assertTrue(hb.contains(o));
			Assert.assertEquals(1, Collections.frequency(al, o));
		}
	}

	@Test
	public void test_doInteresection () {
		HashBag<Integer> aHB = new HashBag(Arrays.asList(
			new Integer[] {1, 2, 3, 4}
		));

		HashBag<Integer> bHB = new HashBag(Arrays.asList(
			new Integer[] {3, 4, 5, 6}
		));

		Assert.assertEquals(2, aHB.doIntersection(bHB).size());
		Assert.assertEquals(2, bHB.doIntersection(aHB).size());
	}

	@Test
	public void test_doUnion () {
		HashBag<Integer> aHB = new HashBag(Arrays.asList(
			new Integer[] {1, 2, 3, 4}
		));

		HashBag<Integer> bHB = new HashBag(Arrays.asList(
			new Integer[] {3, 4, 5, 6}
		));

		HashBag<Integer> cHB = new HashBag(Arrays.asList(
			new Integer[] {3, 4, 5, 6, 3, 4}
		));

		// The union of a hashbag with itself is the initial hashbag
		Assert.assertEquals(hb.size(), hb.doUnion(hb).size());

		// Union with 2 common elements (with same cardinality)
		HashBag u1 = aHB.doUnion(bHB);
		HashBag u2 = bHB.doUnion(aHB);
		Assert.assertEquals(6, u1.size());
		Assert.assertEquals(u1.size(), u2.size());

		// Union with 2 common elements (with distinct cardinality)
		Assert.assertEquals(8, aHB.doUnion(cHB).size());
	}
}