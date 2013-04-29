package com.bananity.util;


import com.bananity.util.CandidatesCache;

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
public class CandidatesCacheTest
{
	@Test
	public void test_put () {
		
		CandidatesCache<Integer> cc = new CandidatesCache<Integer> (new SimpleIntegerComparator(), 5);

		cc.put(4);
		cc.put(5);
		cc.put(3);

		Assert.assertEquals(3, cc.size());

		cc.put(29);
		cc.put(9);
		cc.put(11);

		Assert.assertEquals(5, cc.size());

		Assert.assertArrayEquals(
			new Integer[] {3, 4, 5, 9, 11},
			cc.getRecords().toArray(new Integer[] {})
		);
	}
}