package com.bananity.util;


// Java Utils
import java.util.Set;
import java.util.List;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * This class is used to mantain limited priority queues (used to store ordered search results)
 *
 * @author Alberto Rubio Muñoz
 */
public class CandidatesCache<T> {

	private final int limit;
	private final Set<T> used;
	private final PriorityQueue<T> queue;
	private final Comparator<T> comparator;

	/**
	 * Constructor
	 */
	public CandidatesCache (Comparator<T> comparator, int limit) {
		this.limit = limit;
		used = new HashSet<T>();
		this.comparator = comparator;

		queue = new PriorityQueue<T>(limit, Collections.reverseOrder(comparator));
	}

	/**
	 * Tries to put the element 'e' in the internal queue
	 */
	public void put (T e) {
		if ( !used.contains( e ) ) {
			if ( queue.size() < limit ) {
				queue.offer( e );
				used.add( e );
			} else {
				if ( comparator.compare(e, queue.peek()) < 0 ) {
					T toRemove = queue.poll();
					used.remove( toRemove );
					queue.offer( e );
					used.add( e );
				}
			}
		}
	}

	public int size () {
		return queue.size();
	}

	/**
	 * Returns the internal queue as an ArrayList
	 */
	public ArrayList<T> getRecords () {
		ArrayList<T> result = new ArrayList<T>();
		
		while ( queue.size() > 0 ) {
			result.add(0, queue.poll());
		}
		
		return result;
	}
}
