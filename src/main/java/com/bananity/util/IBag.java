package com.bananity.util;


import java.util.ArrayList;
import java.util.Map;


/**
 * Bag Interface, to allow sets with item's counters
 *
 * @author Andreu Correa Casablanca
 */
public interface IBag<T> extends Iterable<Map.Entry<T, Integer>>
{
	public boolean 	contains (final T o);
	public int 		getTimes (final T o);

	public IBag<T> union (final IBag<T> b);
	public IBag<T> intersection (final IBag<T> b);
	public IBag<T> difference (final IBag<T> b);

	public int size();
	public int uniqueItemsCount();

	public ArrayList<T> toArrayList ();
	public ArrayList<T> toUniqueArrayList ();

	public double distance(IBag<T> b);
}