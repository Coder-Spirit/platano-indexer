package com.bananity.util;


import java.util.Comparator;
import java.util.ArrayList;

/**
 * @author Andreu Correa Casablanca
 */
public class SortedLists
{
	public static <T> boolean sortedInsert (Comparator<T> c, ArrayList<T> al, int limit, T item) {
		
		int insertPos = al.size();

		for (int i = al.size()-1; i >= 0; i--) {
			if (c.compare(al.get(i), item) > 0) {
				insertPos--;
			} else {
				break;
			}
		}

		if (insertPos < limit) {
			//for (int) {
			//
			//}

			return true;
		} else {
			return false;
		}
	}
}