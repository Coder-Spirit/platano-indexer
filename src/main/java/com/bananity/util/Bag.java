package com.bananity.util;


import java.util.Collection;
import java.util.Map;


public interface Bag<T> extends Iterable<Map.Entry<T, Integer>>
{
	public void add (final T o);
	public void add (final T o, final int times);

	public void addAll (final Bag<T> b);
	public void addAll (final Collection<T> c);
	public void addAll (final T[] l);

	public boolean contains (final T o);

	public int getTimes (final T o);

	public int decreaseValue (final T o);
	public int removeValue (final T o);

	public Bag<T> union (final Bag<T> b);
	public Bag<T> intersection (final Bag<T> b);
	public Bag<T> difference (final Bag<T> b);

	public int size();
	public int uniqueItemsCount();
}