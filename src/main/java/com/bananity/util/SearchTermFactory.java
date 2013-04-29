package com.bananity.util;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;


public class SearchTermFactory
{
	private static WeakHashMap<String, WeakReference<SearchTerm>> references = new WeakHashMap<String, WeakReference<SearchTerm>>();

	public static SearchTerm get (String text) {
		WeakReference<SearchTerm> wr = references.get(text);
		SearchTerm st;

		if (wr == null) {
			st = new SearchTerm(text);
			references.put(text, new WeakReference<SearchTerm>(st));
		} else {
			st = wr.get();

			if (st == null) {
				st = new SearchTerm(text);
				references.put(text, new WeakReference<SearchTerm>(st));
			}
		}

		return st;
	}
}