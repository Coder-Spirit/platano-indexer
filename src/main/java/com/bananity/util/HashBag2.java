package com.bananity.util;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * @author Andreu Correa Casablanca
 */
public final class HashBag2<T> implements Bag<T>
{
	/**
	 *
	 */
	private int size;
	private HashMap<T, Integer> internalMap;

	/**
	 *
	 */
	public HashBag2 () {
		size = 0;
		internalMap = new HashMap<T, Integer>();
	}

	/**
	 *
	 */
	public HashBag2 (HashBag2<T> b) {
		size = b.size;
		internalMap = (HashMap<T, Integer>)b.internalMap.clone();
	}

	/**
	 *
	 */
	public HashBag2 (Bag<T> b) {
		this();
		addAll(b);
	}

	/**
	 *
	 */
	public HashBag2 (Collection<T> c) {
		this();
		addAll(c);
	}

	/**
	 *
	 */
	public HashBag2 (T[] l) {
		this();
		addAll(l);
	}

	/**
	 *
	 */
	public Object clone () {
		return new HashBag2<T>(this);
	}

	/**
	 *
	 */
	public Iterator<Map.Entry<T, Integer>> iterator () {
		return internalMap.entrySet().iterator();
	}

	/**
	 *
	 */
	public void add (final T o) {
		add(o, 1);
	}

	/**
	 *
	 */
	public void add (final T o, int times) {
		Integer _n = internalMap.get(o);

		if (_n == null) {
			internalMap.put(o, times);
		} else {
			internalMap.put(o, _n+times);
		}

		size += times;
	}


	/**
	 *
	 */
	public void addAll (Bag<T> b) {
		for (Map.Entry<T, Integer> e : b) {
			add(e.getKey(), e.getValue());
		}
	}

	/**
	 *
	 */
	public void addAll (Collection<T> c) {
		for (T item : c) {
			add(item);
		}
	}

	/**
	 *
	 */
	public void addAll (T[] l) {
		for (T item : l) {
			add(item);
		}
	}


	/**
	 *
	 */
	public boolean contains (T o) {
		return (internalMap.get(o) != null);
	}


	/**
	 *
	 */
	public int getTimes (T o) {
		Integer times = internalMap.get(o);

		return (times == null)?0:times;
	}


	/**
	 *
	 */
	public int decreaseValue (T o) {
		int times = getTimes(o);

		if (times != 0) {
			if (times > 1) {
				internalMap.put(o, --times);
				--size;
			} else if (times == 1) {
				internalMap.remove(o);
				--size; --times;
			} else {
				// Strange case... should not happen
				internalMap.remove(o);
			}
		}
		
		return times;
	}

	/**
	 *
	 */
	public int removeValue (T o) {
		int times = getTimes(o);

		if (times != 0) {
			internalMap.remove(o);
			size -= times;
		}

		return times;
	}


	/**
	 *
	 */
	public Bag<T> union (Bag<T> b) {
		return null;
	}

	/**
	 *
	 */
	public Bag<T> intersection (Bag<T> b) {
		return null;
	}

	/**
	 *
	 */
	public Bag<T> difference (Bag<T> b) {
		return null;
	}
}