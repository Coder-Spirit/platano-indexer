package com.bananity.util;


// Main Class
import com.bananity.util.HashBag;
import com.bananity.util.Jaccard;

// Java Utils
import java.util.ArrayList;

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
public class JaccardTest
{
	private ArrayList<String> abcList;
	private ArrayList<String> bcdList;
	private ArrayList<String> cdaList;
	private ArrayList<String> dabList;
	private ArrayList<String> abcdList;

	private HashBag<String> abcHB;
	private HashBag<String> bcdHB;
	private HashBag<String> cdaHB;
	private HashBag<String> dabHB;
	private HashBag<String> abcdHB;

	@Before
	public void setupJaccardTest () {
		abcList = new ArrayList<String>();
		bcdList = new ArrayList<String>();
		cdaList = new ArrayList<String>();
		dabList = new ArrayList<String>();
		abcdList = new ArrayList<String>();

		abcList.add("a"); bcdList.add("b"); cdaList.add("c"); dabList.add("d");
		abcList.add("b"); bcdList.add("c"); cdaList.add("d"); dabList.add("a");
		abcList.add("c"); bcdList.add("d"); cdaList.add("a"); dabList.add("b");

		abcdList.add("a"); abcdList.add("b"); abcdList.add("c"); abcdList.add("d");

		abcHB = new HashBag(abcList);
		bcdHB = new HashBag(bcdList);
		cdaHB = new HashBag(cdaList);
		dabHB = new HashBag(dabList);
		abcdHB = new HashBag(abcdList);
	}


	@Test
	public void test_distance_ArrayList_checkEquality () {
		// Checking Equality
		Assert.assertEquals(0., Jaccard.distance(abcList, abcList, false), 0.);
		Assert.assertEquals(0., Jaccard.distance(bcdList, bcdList, false), 0.);
		Assert.assertEquals(0., Jaccard.distance(cdaList, cdaList, false), 0.);
		Assert.assertEquals(0., Jaccard.distance(dabList, dabList, false), 0.);

		Assert.assertEquals(0., Jaccard.distance(abcList, abcList, true), 0.);
		Assert.assertEquals(0., Jaccard.distance(bcdList, bcdList, true), 0.);
		Assert.assertEquals(0., Jaccard.distance(cdaList, cdaList, true), 0.);
		Assert.assertEquals(0., Jaccard.distance(dabList, dabList, true), 0.);
	}

	@Test
	public void test_distance_HashBag_checkEquality () {
		// Checking Equality
		Assert.assertEquals(0., Jaccard.distance(abcHB, abcHB, false), 0.);
		Assert.assertEquals(0., Jaccard.distance(bcdHB, bcdHB, false), 0.);
		Assert.assertEquals(0., Jaccard.distance(cdaHB, cdaHB, false), 0.);
		Assert.assertEquals(0., Jaccard.distance(dabHB, dabHB, false), 0.);

		Assert.assertEquals(0., Jaccard.distance(abcHB, abcHB, true), 0.);
		Assert.assertEquals(0., Jaccard.distance(bcdHB, bcdHB, true), 0.);
		Assert.assertEquals(0., Jaccard.distance(cdaHB, cdaHB, true), 0.);
		Assert.assertEquals(0., Jaccard.distance(dabHB, dabHB, true), 0.);
	}

	@Test
	public void test_distance_ArrayList_checkDistanceRelations () {
		// Checking distance relations (without symmetry)
		Assert.assertEquals(Jaccard.distance(abcList, bcdList, false), Jaccard.distance(abcList, cdaList, false), 0.);
		Assert.assertEquals(Jaccard.distance(abcList, cdaList, false), Jaccard.distance(abcList, dabList, false), 0.);
		Assert.assertEquals(Jaccard.distance(bcdList, cdaList, false), Jaccard.distance(bcdList, dabList, false), 0.);
		Assert.assertEquals(Jaccard.distance(bcdList, dabList, false), Jaccard.distance(bcdList, abcList, false), 0.);
		Assert.assertEquals(Jaccard.distance(cdaList, dabList, false), Jaccard.distance(cdaList, abcList, false), 0.);
		Assert.assertEquals(Jaccard.distance(cdaList, abcList, false), Jaccard.distance(cdaList, bcdList, false), 0.);
		Assert.assertEquals(Jaccard.distance(dabList, abcList, false), Jaccard.distance(dabList, bcdList, false), 0.);
		Assert.assertEquals(Jaccard.distance(dabList, bcdList, false), Jaccard.distance(dabList, cdaList, false), 0.);

		// Checking distance relations (with symmetry)
		Assert.assertEquals(Jaccard.distance(abcList, bcdList, true), Jaccard.distance(abcList, cdaList, true), 0.);
		Assert.assertEquals(Jaccard.distance(abcList, cdaList, true), Jaccard.distance(abcList, dabList, true), 0.);
		Assert.assertEquals(Jaccard.distance(bcdList, cdaList, true), Jaccard.distance(bcdList, dabList, true), 0.);
		Assert.assertEquals(Jaccard.distance(bcdList, dabList, true), Jaccard.distance(bcdList, abcList, true), 0.);
		Assert.assertEquals(Jaccard.distance(cdaList, dabList, true), Jaccard.distance(cdaList, abcList, true), 0.);
		Assert.assertEquals(Jaccard.distance(cdaList, abcList, true), Jaccard.distance(cdaList, bcdList, true), 0.);
		Assert.assertEquals(Jaccard.distance(dabList, abcList, true), Jaccard.distance(dabList, bcdList, true), 0.);
		Assert.assertEquals(Jaccard.distance(dabList, bcdList, true), Jaccard.distance(dabList, cdaList, true), 0.);
	}

	@Test
	public void test_distance_HashBag_checkDistanceRelations () {
		// Checking distance relations (without symmetry)
		Assert.assertEquals(Jaccard.distance(abcHB, bcdHB, false), Jaccard.distance(abcHB, cdaHB, false), 0.);
		Assert.assertEquals(Jaccard.distance(abcHB, cdaHB, false), Jaccard.distance(abcHB, dabHB, false), 0.);
		Assert.assertEquals(Jaccard.distance(bcdHB, cdaHB, false), Jaccard.distance(bcdHB, dabHB, false), 0.);
		Assert.assertEquals(Jaccard.distance(bcdHB, dabHB, false), Jaccard.distance(bcdHB, abcHB, false), 0.);
		Assert.assertEquals(Jaccard.distance(cdaHB, dabHB, false), Jaccard.distance(cdaHB, abcHB, false), 0.);
		Assert.assertEquals(Jaccard.distance(cdaHB, abcHB, false), Jaccard.distance(cdaHB, bcdHB, false), 0.);
		Assert.assertEquals(Jaccard.distance(dabHB, abcHB, false), Jaccard.distance(dabHB, bcdHB, false), 0.);
		Assert.assertEquals(Jaccard.distance(dabHB, bcdHB, false), Jaccard.distance(dabHB, cdaHB, false), 0.);

		// Checking distance relations (with symmetry)
		Assert.assertEquals(Jaccard.distance(abcHB, bcdHB, true), Jaccard.distance(abcHB, cdaHB, true), 0.);
		Assert.assertEquals(Jaccard.distance(abcHB, cdaHB, true), Jaccard.distance(abcHB, dabHB, true), 0.);
		Assert.assertEquals(Jaccard.distance(bcdHB, cdaHB, true), Jaccard.distance(bcdHB, dabHB, true), 0.);
		Assert.assertEquals(Jaccard.distance(bcdHB, dabHB, true), Jaccard.distance(bcdHB, abcHB, true), 0.);
		Assert.assertEquals(Jaccard.distance(cdaHB, dabHB, true), Jaccard.distance(cdaHB, abcHB, true), 0.);
		Assert.assertEquals(Jaccard.distance(cdaHB, abcHB, true), Jaccard.distance(cdaHB, bcdHB, true), 0.);
		Assert.assertEquals(Jaccard.distance(dabHB, abcHB, true), Jaccard.distance(dabHB, bcdHB, true), 0.);
		Assert.assertEquals(Jaccard.distance(dabHB, bcdHB, true), Jaccard.distance(dabHB, cdaHB, true), 0.);
	}

	@Test
	public void test_distance_ArrayList_checkEmpty () {
		// Checking distance for empty arrays
		Assert.assertEquals(1.0, Jaccard.distance(new ArrayList<String>(), abcList, false), 0.);
		Assert.assertEquals(1.0, Jaccard.distance(new ArrayList<String>(), abcList, true), 0.);
	}

	@Test
	public void test_distance_HashBag_checkEmpty () {
		// Checking distance for empty arrays
		Assert.assertEquals(1.0, Jaccard.distance(new HashBag<String>(), abcHB, false), 0.);
		Assert.assertEquals(1.0, Jaccard.distance(new HashBag<String>(), abcHB, true), 0.);
	}

	@Test
	public void test_distance_ArrayList_checkDistanceValues () {
		Assert.assertEquals(0.5, Jaccard.distance(abcList, bcdList, true), 0.00001);
		Assert.assertEquals(0.5, Jaccard.distance(bcdList, abcList, true), 0.00001);
		Assert.assertEquals(0.25, Jaccard.distance(abcdList, abcList, true), 0.00001);
		Assert.assertEquals(0.25, Jaccard.distance(abcList, abcdList, true), 0.00001);

		Assert.assertEquals(0.33333, Jaccard.distance(abcList, bcdList, false), 0.00001);
		Assert.assertEquals(0.33333, Jaccard.distance(bcdList, abcList, false), 0.00001);
		Assert.assertEquals(0., Jaccard.distance(abcdList, abcList, false), 0.00001);
		Assert.assertEquals(0.25, Jaccard.distance(abcList, abcdList, false), 0.00001);
	}

	@Test
	public void test_distance_HashBag_checkDistanceValues () {
		Assert.assertEquals(0.5, Jaccard.distance(abcHB, bcdHB, true), 0.00001);
		Assert.assertEquals(0.5, Jaccard.distance(bcdHB, abcHB, true), 0.00001);
		Assert.assertEquals(0.25, Jaccard.distance(abcdHB, abcHB, true), 0.00001);
		Assert.assertEquals(0.25, Jaccard.distance(abcHB, abcdHB, true), 0.00001);

		Assert.assertEquals(0.33333, Jaccard.distance(abcHB, bcdHB, false), 0.00001);
		Assert.assertEquals(0.33333, Jaccard.distance(bcdHB, abcHB, false), 0.00001);
		Assert.assertEquals(0., Jaccard.distance(abcdHB, abcHB, false), 0.00001);
		Assert.assertEquals(0.25, Jaccard.distance(abcHB, abcdHB, false), 0.00001);
	}
}