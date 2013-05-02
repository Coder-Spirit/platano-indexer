package com.bananity.util;


// Java Utils
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * This class is used to mantain limited priority queues (used to store ordered search results)
 *
 * @author Alberto Rubio Muñoz
 */
public class CandidatesCache<T> {

	private final int limit;
	
	private final HashSet<T> used;
	private final ArrayList<T> queue;
	private final Comparator<T> comparator;

	/**
	 * Constructor
	 */
	public CandidatesCache (Comparator<T> comparator, int limit) {
		this.limit 		= limit;
		this.comparator = comparator;
		
		this.used 		= new HashSet<T>();
		this.queue 		= new ArrayList<T>();
	}

	/**
	 * Tries to put the element 'e' in the internal queue
	 */
	public void put (T e) {
		if (!used.contains(e)) {
			SortedLists.sortedInsert(comparator, queue, limit, e);
			used.add(e);
		}
	}

	public int size () {
		return queue.size();
	}

	/**
	 * Returns the internal queue as an ArrayList
	 */
	public ArrayList<T> getRecords () {
		return queue;
	}
}
