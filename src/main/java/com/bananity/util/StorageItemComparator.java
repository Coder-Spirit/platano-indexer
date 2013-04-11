package com.bananity.util;


import com.bananity.text.TextNormalizer;
import java.util.Comparator;


public class StorageItemComparator implements Comparator<String>
{
	private final String token;

	public StorageItemComparator (String token) {
		this.token = token;
	}

	public int compare(String sr1, String sr2) {
		
		// En primer lugar comparamos SOLO la logitud
		int result = Integer.compare(sr1.length(), sr2.length());

		if (result == 0) {
			int indexOfTokenInSr1 = TextNormalizer.flattenText(sr1).indexOf(token);
			int indexOfTokenInSr2 = TextNormalizer.flattenText(sr2).indexOf(token);

			if (indexOfTokenInSr1 == -1) indexOfTokenInSr1 = Integer.MAX_VALUE;
			if (indexOfTokenInSr2 == -1) indexOfTokenInSr2 = Integer.MAX_VALUE;

			result = Integer.compare(indexOfTokenInSr1, indexOfTokenInSr2);

			if (result == 0) {
				// Si no queda otra: orden lexicográfico
				result = sr1.compareTo(sr2);
			}
		}

		return result;
	}
}