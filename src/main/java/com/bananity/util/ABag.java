package com.bananity.util;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


/**
 * Abstract class that implements basic internals for IBag Interface
 *
 * @author Andreu Correa Casablanca
 * @see com.bananity.utils.IBag
 */
public abstract class ABag<T> implements IBag<T>
{
	/**
	 * Internal method to add items to the bag
	 */
	protected abstract void _add (final T o, final int times);

	/**
	 * Internal method to add items to the bag
	 */
	protected void _add (final T o) {
		_add(o, 1);
	}

	/**
	 * Internal method to add items to the bag
	 */
	protected void _addAll (final IBag<T> b) {
		for (Map.Entry<T, Integer> e : b) {
			_add(e.getKey(), e.getValue());
		}
	}

	/**
	 * Internal method to add items to the bag
	 */
	protected void _addAll (final Collection<T> c) {
		for (T item : c) {
			_add(item);
		}
	}

	/**
	 * Internal method to add items to the bag
	 */
	protected void _addAll (final T[] l) {
		for (T item : l) {
			_add(item);
		}
	}

	/**
	 * This method returns an ArrayList object with all the bag's items, with repetitions
	 */
	public ArrayList<T> toArrayList () {
		ArrayList<T> result = new ArrayList<T>();

		for (Map.Entry<T, Integer> e : this) {
			T v = e.getKey();

			for (int i=0, n=e.getValue(); i<n; i++) {
				result.add(v);
			}
		}

		return result;
	}

	/**
	 * This method returns an ArrayList object with all the bag's items, without repetitions
	 */
	public ArrayList<T> toUniqueArrayList () {
		ArrayList<T> result = new ArrayList<T>();

		for (Map.Entry<T, Integer> e : this) {
			result.add(e.getKey());
		}

		return result;
	}

	/**
	 * This method gives us the 'Jaccard' distance between this bag and 'b'
	 */
	public double distance(IBag<T> b) {
		IBag<T> i = this.intersection(b);
		if ( i.size() == 0 ) {
			return 1.0d;
		}
		
		return 1.0d - ((double)i.size() / (double)this.union(b).size());
	}
}