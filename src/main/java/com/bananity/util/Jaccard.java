package com.bananity.util;

import java.util.ArrayList;

/**
 *  This class encapsulates many methods to calculate the Jaccard Distance
 *
 *  @author Alberto Rubio Muñoz
 *  @see <a href="http://en.wikipedia.org/wiki/Jaccard_index">Jaccard Index (Wikipedia Article)</a> 
 */
public class Jaccard {

	/**
	 *  @param simmetric 	This param specifies if you want a typical Jaccard distance or if you want to use the cardinal of `b` as a divisor
	 *
	 *  @return Jaccard distance between `a` and `b`
	 */
	public static double distance(ArrayList<String> a, ArrayList<String> b, boolean simmetric) {
		ArrayList<String> i = doIntersection(a, b);
		if (i.size() == 0) {
			return 1.0d;
		}

		double divisor;
		if (simmetric) {
			ArrayList<String> u = doUnion(a, b);
			divisor = (double)u.size();
		} else {
			divisor = (double)b.size();
		}

		return  1.0d - ((double)i.size() / divisor);
	}

	/**
	 *  @param simmetric 	This param specifies if you want a typical Jaccard distance or if you want to use the cardinal of `b` as a divisor
	 *
	 *  @return Jaccard distance between `a` and `b`
	 */
	public static double distance(HashBag<String> a, HashBag<String> b, boolean simmetric) {
		IBag<String> i = a.intersection( b );
		if ( i.size() == 0 ) {
			return 1.0d;
		}

		double divisor;
		if (simmetric) {
			IBag<String> u =  a.union( b );
			divisor = (double)u.size();
		} else {
			divisor = (double)b.size();
		}
		
		return 1.0d - ((double)i.size() / divisor);
	}


	/**
	 *  This method returns the union of `aa` and `bb`
	 */
	private static ArrayList<String> doUnion(ArrayList<String> aa, ArrayList<String>bb) {
		ArrayList<String> b = (ArrayList<String>)bb.clone();
		ArrayList<String> u = new ArrayList<String>();
		for (String s : aa) {
			if ( b.contains(s) ) {
				u.add(s);
				b.remove(s);
			} else {
				u.add(s);
			}
		}
		for (String s : b) {
			u.add(s);
		}
		return u;
	}

	/**
	 *  This method returns the intersection of `aa` and `bb`
	 */
	private static ArrayList<String> doIntersection(ArrayList<String> aa, ArrayList<String>bb) {
		ArrayList<String> i = new ArrayList<String>();
		ArrayList<String> l, h;
		if (aa.size() > bb.size()) {
			l = bb;
			h = (ArrayList<String>)aa.clone();
		} else {
			l = aa;
			h = (ArrayList<String>)bb.clone();
		}
		for (String s : l) {
			if ( h.contains(s) ) {
				i.add(s);
				h.remove(s);
			}
		}
		return i;
	}
}
