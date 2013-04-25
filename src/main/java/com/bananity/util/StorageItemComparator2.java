package com.bananity.util;


import com.bananity.text.TextNormalizer;
import com.bananity.util.SearchTerm;

import java.util.Comparator;


public class StorageItemComparator2 implements Comparator<SearchTerm>
{
	private final String token;

	public StorageItemComparator2 (final String token) {
		this.token = token;
	}

	public int compare(final SearchTerm st1, final SearchTerm st2) {
		
		final String sr1 = st1.toString();
		final String sr2 = st2.toString();

		// En primer lugar comparamos SOLO la logitud
		int result = Integer.compare(sr1.length(), sr2.length());

		if (result == 0) {
			int indexOfTokenInSr1 = TextNormalizer.flattenText(sr1).toLowerCase().indexOf(token);
			int indexOfTokenInSr2 = TextNormalizer.flattenText(sr2).toLowerCase().indexOf(token);

			if (indexOfTokenInSr1 == -1) indexOfTokenInSr1 = Integer.MAX_VALUE;
			if (indexOfTokenInSr2 == -1) indexOfTokenInSr2 = Integer.MAX_VALUE;

			result = Integer.compare(indexOfTokenInSr1, indexOfTokenInSr2);

			if (result == 0) {
				int freqSt1 = st1.getLcFlattenStrings().getTextBag().getTimes(token);
				int freqSt2 = st2.getLcFlattenStrings().getTextBag().getTimes(token);

				// The order change is needed (greater freq is equivalent to smaller distance)
				result = Integer.compare(freqSt2, freqSt1);

				if (result == 0) {
					// Si no queda otra: orden lexicográfico
					result = sr1.compareTo(sr2);
				}
			}
		}

		return result;
	}
}