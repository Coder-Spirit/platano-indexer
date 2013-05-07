package com.bananity.util;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Class similar to Set, but associates a counter to each item
 *
 * @author 	Andreu Correa Casablanca
 * @see 	com.bananity.util.Bag
 * @see 	com.bananity.util.ABag
 */
public final class HashBag<T> extends ABag<T>
{
	/**
	 * Bag size (the sum of all items counters)
	 */
	private int size;

	/**
	 * Number of different items inside the bag
	 */
	private int itemsCounter;

	/**
	 * The 'storage'
	 */
	private HashMap<T, Integer> internalMap;

	/**
	 * Basic constructor, gives as an empty HashBag
	 */
	public HashBag () {
		size = 0;
		itemsCounter = 0;
		internalMap = new HashMap<T, Integer>();
	}

	/**
	 * 'Copy' constructor
	 */
	public HashBag (IBag<T> b) {
		this();
		_addAll(b);
	}

	/**
	 * 'Copy' constructor (more efficient than `HashBag(IBag<T> b)`)
	 */
	public HashBag (HashBag<T> b) {
		size = b.size;
		itemsCounter = b.itemsCounter;
		internalMap = new HashMap<T, Integer>(b.internalMap);
	}

	/**
	 * 'Copy' constructor
	 */
	public HashBag (Collection<T> c) {
		this();
		_addAll(c);
	}

	/**
	 * 'Copy' constructor
	 */
	public HashBag (T[] l) {
		this();
		_addAll(l);
	}


	/**
	 * This method is declared in the Iterable interface
	 * @see java.lang.Iterable
	 */
	public Iterator<Map.Entry<T, Integer>> iterator () {
		return internalMap.entrySet().iterator();
	}


	/**
	 * This method is declared in ABag
	 * @see com.bananity.util.ABag
	 */
	protected void _add (final T o, final int times) {
		if (times > 0) {
			Integer _n = internalMap.get(o);

			if (_n == null) {
				_internalNewInsert(o, times);
			} else {
				internalMap.put(o, _n+times);
				size += times;
			}
		}
	}


	/**
	 * Returns 'true' if 'o' ∈ this
	 */
	public boolean contains (final T o) {
		return (internalMap.get(o) != null);
	}


	/**
	 * Returns the number of repetitions of 'o' inside the bag
	 */
	public int getTimes (final T o) {
		Integer times = internalMap.get(o);

		return (times == null)?0:times;
	}


	/**
	 * Returns a union bag: this ∪ b
	 */
	public IBag<T> union (final IBag<T> b) {
		HashBag<T> uBag = new HashBag<T>();
		
		T aux;
		int times;

		for (Map.Entry<T, Integer> e : internalMap.entrySet()) {
			aux = e.getKey();
			uBag._internalNewInsert(aux, Math.max(e.getValue(), b.getTimes(aux)));
		}

		for (Map.Entry<T, Integer> e : b) {
			aux = e.getKey();
			if (uBag.internalMap.get(aux) == null) {
				uBag._internalNewInsert(aux, e.getValue());
			}
		}

		return uBag;
	}

	/**
	 * Returns an intersection bag: this ∩ b
	 */
	public IBag<T> intersection (final IBag<T> b) {
		HashBag<T> iBag = new HashBag<T>();

		IBag<T> minBag, maxBag;

		int min;

		if (itemsCounter < b.uniqueItemsCount()) {
			minBag = this;
			maxBag = b;
		} else {
			minBag = b;
			maxBag = this;
		}

		for (Map.Entry<T, Integer> e : minBag) {
			min = Math.min(e.getValue(), maxBag.getTimes(e.getKey()));

			if (min > 0) {
				iBag._internalNewInsert(e.getKey(), min);
			}
		}

		return iBag;
	}

	/**
	 * Returns the bags difference: this \ b
	 */
	public IBag<T> difference (final IBag<T> b) {
		HashBag<T> dBag = new HashBag<T>();
		int bTimes;

		for (Map.Entry<T, Integer> e : internalMap.entrySet()) {
			bTimes = b.getTimes(e.getKey());
			if (bTimes < e.getValue()) {
				dBag._internalNewInsert(e.getKey(), e.getValue()-bTimes);
			}
		}

		return dBag;
	}

	/**
	 *
	 */
	public int size() {
		return size;
	}

	/**
	 *
	 */
	public int uniqueItemsCount() {
		return itemsCounter;
	}

	@Override
	public int hashCode() {
		return internalMap.hashCode()*994009 + size*997 + itemsCounter;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof HashBag) {
			try {
				return equals((HashBag<T>)o);
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean equals(HashBag<T> o) {
		if (internalMap.hashCode() == o.internalMap.hashCode() && o.size==size && o.itemsCounter==itemsCounter) {
			for (Map.Entry<T, Integer> e : o.internalMap.entrySet()) {
				if (!e.getValue().equals(internalMap.get(e.getKey()))) {
					return false;
				}
			}

			return true;
		} else {
			return false;
		}
	}

	/**
	 *
	 */
	private void _internalNewInsert (final T o, final int times) {
		internalMap.put(o, times);
		size += times;
		++itemsCounter;
	}
}