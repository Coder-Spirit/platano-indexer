package com.bananity.util;


import java.util.Comparator;


public class SimpleIntegerComparator implements Comparator<Integer>
{
	public int compare (Integer a, Integer b) {
		return a.compareTo(b);
	}
}