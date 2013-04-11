package com.bananity.util;


// Main Class
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

	@Before
	public void setupJaccardTest () {
		abcList = new ArrayList<String>();
		bcdList = new ArrayList<String>();
		cdaList = new ArrayList<String>();
		dabList = new ArrayList<String>();

		abcList.add("a"); bcdList.add("b"); cdaList.add("c"); dabList.add("d");
		abcList.add("b"); bcdList.add("c"); cdaList.add("d"); dabList.add("a");
		abcList.add("c"); bcdList.add("d"); cdaList.add("a"); dabList.add("b");
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
	public void test_distance_ArrayList_checkEmpty () {
		// Checking distance for empty arrays
		Assert.assertEquals(1.0, Jaccard.distance(new ArrayList<String>(), abcList, false), 0.);
		Assert.assertEquals(1.0, Jaccard.distance(new ArrayList<String>(), abcList, true), 0.);
	}

	@Test
	public void test_distance_ArrayList_checkDistanceValues () {
		Assert.assertEquals(0.5, Jaccard.distance(abcList, bcdList, true), 0.00001);
		Assert.assertEquals(0.5, Jaccard.distance(bcdList, abcList, true), 0.00001);

		Assert.assertEquals(0.33333, Jaccard.distance(abcList, bcdList, false), 0.00001);
		Assert.assertEquals(0.33333, Jaccard.distance(bcdList, abcList, false), 0.00001);
	}
}