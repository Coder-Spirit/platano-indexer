package com.bananity.util;


import java.util.Collection;
import java.util.Map;


public abstract class ABag<T> implements IBag<T>
{
	/**
	 *
	 */
	protected abstract void _add (final T o, final int times);

	/**
	 *
	 */
	protected void _add (final T o) {
		_add(o, 1);
	}

	/**
	 *
	 */
	protected void _addAll (final IBag<T> b) {
		for (Map.Entry<T, Integer> e : b) {
			_add(e.getKey(), e.getValue());
		}
	}

	/**
	 *
	 */
	protected void _addAll (final Collection<T> c) {
		for (T item : c) {
			_add(item);
		}
	}

	/**
	 *
	 */
	protected void _addAll (final T[] l) {
		for (T item : l) {
			_add(item);
		}
	}
}