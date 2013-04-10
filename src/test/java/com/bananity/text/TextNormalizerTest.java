package com.bananity.text;


// Main Class
import com.bananity.text.TextNormalizer;

// Junit
import org.junit.Assert;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


/**
 * @author Andreu Correa Casablanca
 */
@RunWith(JUnit4.class)
public class TextNormalizerTest
{
	@Test
	public void test_flattenText_checkAccentsRemoval () {
		Assert.assertEquals("aeiou", TextNormalizer.flattenText("AEIOU"));
		Assert.assertEquals("aeiou", TextNormalizer.flattenText("ÁÉÍÓÚ"));
		Assert.assertEquals("aeiou", TextNormalizer.flattenText("ÀÈÌÒÙ"));
		Assert.assertEquals("aeiou", TextNormalizer.flattenText("ÄËÏÖÜ"));
		Assert.assertEquals("aeiou", TextNormalizer.flattenText("ÂÊÎÔÛ"));

		Assert.assertEquals("aeiou", TextNormalizer.flattenText("áéíóú"));
		Assert.assertEquals("aeiou", TextNormalizer.flattenText("àèìòù"));
		Assert.assertEquals("aeiou", TextNormalizer.flattenText("äëïöü"));
		Assert.assertEquals("aeiou", TextNormalizer.flattenText("âêîôû"));
	}

	@Test
	public void test_flattenText_checkSpecialCharactersHandling () {
		Assert.assertEquals("c", TextNormalizer.flattenText("Ç"));
		Assert.assertEquals("c", TextNormalizer.flattenText("ç"));

		Assert.assertEquals("n", TextNormalizer.flattenText("Ñ"));
		Assert.assertEquals("n", TextNormalizer.flattenText("ñ"));
	}
}