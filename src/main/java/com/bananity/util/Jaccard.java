package com.bananity.util;

import java.util.ArrayList;

public class Jaccard {

	public static double distance(ArrayList<String> a, ArrayList<String> b) {
		ArrayList<String> i = doIntersection(a, b);
		if (i.size() == 0) {
			return 1.0d;
		}
		ArrayList<String> u = doUnion(a, b);
		
		return  1.0d - ((double)i.size() / (double)u.size());
	}

	public static double distance(HashBag<String> a, HashBag<String> b) {
		long start = System.currentTimeMillis();
		HashBag<String> i = a.doIntersection( b );
		if ( i.size() == 0 ) {
			return 1.0d;
		}
		HashBag<String> u = a.doUnion( b );
		long end = System.currentTimeMillis();
		
		return 1.0d - ((double)i.size() / (double)u.size());
	}


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
