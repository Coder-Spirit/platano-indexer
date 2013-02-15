package com.bananity.util;


import com.bananity.util.ResultItemComparator;

import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.PriorityQueue;


public class CandidatesCache<T> {

	private final int limit;
	private final Set<T> used;
	private final PriorityQueue<T> queue;
	private final Comparator<T> comparator;

	public CandidatesCache (Comparator<T> comparator, int limit) throws Exception {
		if (limit <= 0) {
			throw new Exception( "BAD \"size\" value (" + limit + ")" );
		}

		this.limit = limit;
		used = new HashSet<T>();
		this.comparator = comparator;

		queue = new PriorityQueue<T>(limit, Collections.reverseOrder(comparator));
	}

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

	public String toString () {
		return queue.toString();
	}

	public int size () {
		return queue.size();
	}

	public ArrayList<T> getRecords () {
		ArrayList<T> a = new ArrayList<T>();
		while ( queue.size() > 0 ) {
			a.add( queue.poll() );
		}
		Collections.reverse( a );
		return a;
	}
}
