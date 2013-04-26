package com.bananity.util;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * @author Andreu Correa Casablanca
 */
public final class HashBag<T> extends ABag<T>
{
	/**
	 *
	 */
	private int size;

	/**
	 *
	 */
	private int itemsCounter;

	/**
	 *
	 */
	private HashMap<T, Integer> internalMap;

	/**
	 *
	 */
	public HashBag () {
		size = 0;
		itemsCounter = 0;
		internalMap = new HashMap<T, Integer>();
	}

	/**
	 *
	 */
	public HashBag (IBag<T> b) {
		this();
		_addAll(b);
	}

	/**
	 *
	 */
	public HashBag (Collection<T> c) {
		this();
		_addAll(c);
	}

	/**
	 *
	 */
	public HashBag (T[] l) {
		this();
		_addAll(l);
	}

	/**
	 *
	 */
	public HashBag (HashBag<T> b) {
		size = b.size;
		itemsCounter = b.itemsCounter;
		internalMap = new HashMap<T, Integer>(b.internalMap);
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
	 *
	 */
	public boolean contains (final T o) {
		return (internalMap.get(o) != null);
	}


	/**
	 *
	 */
	public int getTimes (final T o) {
		Integer times = internalMap.get(o);

		return (times == null)?0:times;
	}


	/**
	 *
	 */
	public int decreaseValue (final T o) {
		int times = getTimes(o);

		if (times != 0) {
			if (times > 1) {
				internalMap.put(o, times-1);
				--size;
			} else if (times == 1) {
				internalMap.remove(o);
				--size; --itemsCounter;
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
	public int removeValue (final T o) {
		int times = getTimes(o);

		if (times != 0) {
			internalMap.remove(o);
			size -= times;
			--itemsCounter;
		}

		return times;
	}


	/**
	 *
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
	 *
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
	 *
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