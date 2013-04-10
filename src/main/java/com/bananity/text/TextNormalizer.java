package com.bananity.text;


import java.text.Normalizer;


/**
 * This class encapsulates text normalization code (manly for UTF8 encoding)
 *
 * @author Andreu Correa Casablanca
 */
public class TextNormalizer
{
	/**
	 * This method normalizs UTF8 strings into its canonical form
	 *
	 * @param 	text 	Text to be normalized
	 * @return 			normalized text
	 */
	public static String normalizeText (final String text) {
		if (!Normalizer.isNormalized(text, Normalizer.Form.NFC)) {
			return Normalizer.normalize(text, Normalizer.Form.NFC);
		} else {
			return text;
		}
	}

	/**
	 * This method flattens UTF8 strings (removes accents, diacritical marks and other marks, and also undercases the text)
	 * 
	 * @param 	text 	Text to be flattened
	 * @return 			flattened text
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