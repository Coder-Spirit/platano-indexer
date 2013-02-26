package com.bananity.util;


import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class HashBag<T> {

	private int size;
	private HashMap<T, Integer> hb;

	public HashBag() {
		size = 0;
		hb = new HashMap<T, Integer>();
	}

	public HashBag( Collection<T> coll ) {
		size = 0;
		hb = new HashMap<T, Integer>();
		for ( T e : coll ) {
			put( e );
		}
	}

	public int size() {
		return size;
	}

	public Set<T> getUniqElements() {
		return (Set<T>)hb.keySet();
	}

	public void put( T obj ) {
		Integer i = hb.get( obj );
		if ( i == null ) {
			hb.put( obj, new Integer( 1 ) );
		} else {
			hb.put( obj, ++i );
		}
		size++;
	}

	public void put( T obj, int times ) {
		Integer i = hb.get( obj );
		if ( i == null ) {
			hb.put( obj, new Integer( times ) );
		} else {
			hb.put( obj, i + times );
		}
		size += times;
	}

	public void addAll( HashBag<T> b ) {
		for ( Map.Entry<T, Integer> bEntry : b.hb.entrySet() ) {
			put( bEntry.getKey(), bEntry.getValue() );
		}
	}

	public void addAll ( Collection<T> c ) {
		for (T o : c) put(o);
	}

	public ArrayList<T> toUniqueArrayList () {
		ArrayList<T> result = new ArrayList<T>();

		for (Map.Entry<T, Integer> bEntry : hb.entrySet()) {
			result.add(bEntry.getKey());
		}

		return result;
	}

	public ArrayList<T> toArrayList () {
		ArrayList<T> result = new ArrayList<T>();

		for (Map.Entry<T, Integer> bEntry : hb.entrySet()) {
			for (int i=0, n=bEntry.getValue(); i<n; i++)
				result.add(bEntry.getKey());
		}

		return result;
	}

	public boolean contains( T obj ) {
		return hb.get( obj ) != null;
	}

	public int getTimes( T obj ) {
		Integer i = hb.get( obj );
		return ( i == null )? 0 : i;
	}

	public void remove( T obj ) {
		Integer i = hb.get( obj );
		if ( i != null ) {
			if ( i > 1 ) {
				hb.put( obj, --i );
				size--;
			} else if ( i == 1 ) {
				hb.remove( obj );
				size--;
			} else {
				hb.remove( obj );
			}
		}
	}

	@SuppressWarnings("unchecked")
	public HashBag<T> doUnion( HashBag<T> b ) {
		HashBag<T> c = new HashBag<T>();
		for ( Map.Entry e : hb.entrySet() ) {
			T aux = (T)e.getKey();
			c.put( aux, Math.max( (Integer)e.getValue(), b.getTimes( aux ) ) );
		}
		for ( Map.Entry e : b.hb.entrySet() ) {
			T aux = (T)e.getKey();
			if ( !c.contains( aux ) ) {
				c.put( aux, (Integer)e.getValue() );
			}
		}
		return c;
	}

	@SuppressWarnings("unchecked")
	public HashBag<T> doIntersection( HashBag<T> b ) {
		HashBag<T> c = new HashBag<T>();
		HashBag<T> aa, bb;
		if ( size < b.size() ) {
			aa = this;
			bb = b;
		} else {
			aa = b;
			bb = this;
		}
		Integer min;
		for ( Map.Entry e : aa.hb.entrySet() ) {
			T aux = (T)e.getKey();
			min = Math.min( (Integer)e.getValue(), bb.getTimes( aux ) );
			if ( min > 0 ) {
				c.put( aux, min );
			}
		}
		return c;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder( "{" );
		boolean first = true;
		for ( Map.Entry e : hb.entrySet() ) {
			if ( first ) {
				first = false;
			} else {
				sb.append( ", " );
			}
			sb.append( ( e.getKey()).toString() );
			sb.append( " => " );
			sb.append( ((Integer)e.getValue()).toString() );
		}
		sb.append( "}" );
		return sb.toString();
	}
}
