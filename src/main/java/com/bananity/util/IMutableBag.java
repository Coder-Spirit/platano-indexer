package com.bananity.util;


import java.util.Collection;


/**
 * This interface is defined to uncouple mutable and immutable bags
 *
 * @author Andreu Correa Casablanca
 * @see com.bananity.util.IBag
 */
public interface IMutableBag<T> extends IBag<T>
{
	public void add (final T o);
	public void add (final T o, final int times);

	public void addAll (final IBag<T> b);
	public void addAll (final Collection<T> c);
	public void addAll (final T[] l);

	public int decreaseValue (final T o);
	public int removeValue (final T o);
}