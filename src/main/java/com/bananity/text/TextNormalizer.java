package com.bananity.text;


import java.text.Normalizer;


/**
 * @author Andreu Correa Casablanca
 */
public class TextNormalizer
{
	/**
	 *
	 */
	public static String normalizeText (final String text) {
		if (!Normalizer.isNormalized(text, Normalizer.Form.NFC)) {
			return Normalizer.normalize(text, Normalizer.Form.NFC);
		} else {
			return text;
		}
	}

	/**
	 *
	 */
	public static String flattenText (final String text) {
		String nfdNormalizedText;

		if (!Normalizer.isNormalized(text, Normalizer.Form.NFD)) {
			nfdNormalizedText = Normalizer.normalize(text, Normalizer.Form.NFD).toLowerCase();
		} else {
			nfdNormalizedText = text.toLowerCase();
		}

		return nfdNormalizedText.replaceAll("\\p{IsM}", "");
	}
}