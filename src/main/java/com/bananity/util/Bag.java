package com.bananity.util;


import java.util.Collection;
import java.util.Map;


public interface Bag<T> extends Cloneable, Iterable<Map.Entry<T, Integer>>
{
	public void add (final T o);
	public void add (final T o, int times);

	public void addAll (Bag<T> b);
	public void addAll (Collection<T> c);
	public void addAll (T[] l);

	public boolean contains (T o);

	public int getTimes (T o);

	public int decreaseValue (T o);
	public int removeValue (T o);

	public Bag<T> union (Bag<T> b);
	public Bag<T> intersection (Bag<T> b);
	public Bag<T> difference (Bag<T> b);
}