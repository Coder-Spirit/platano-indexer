package com.bananity.text;


// Main Class
import com.bananity.text.SubstringCalculator;

// Java Utils
import java.util.Arrays;
import java.util.ArrayList;

// Junit
import static org.hamcrest.CoreMatchers.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


/**
 * @author Andreu Correa Casablanca
 */
@RunWith(JUnit4.class)
public class SubstringCalculatorTest
{
	@Test
	public void test_getAllSubstrings () {
		String str_A = "Bananity";

		ArrayList<String> al_Test_1 = new ArrayList<String>(Arrays.asList(new String[] {
			"Bananity",
			"Bananit","ananity",
			"Banani","ananit","nanity",
			"Banan","anani","nanit","anity",
			"Bana","anan","nani","anit","nity",
			"Ban","ana","nan","ani","nit","ity",
			"Ba","an","na","an","ni","it","ty",
			"B","a","n","a","n","i","t","y"
		}));

		ArrayList<String> al_Test_3 = new ArrayList<String>(Arrays.asList(new String[] {
			"Bananity",
			"Bananit","ananity",
			"Banani","ananit","nanity",
			"Banan","anani","nanit","anity",
			"Bana","anan","nani","anit","nity",
			"Ban","ana","nan","ani","nit","ity"
		}));

		ArrayList<String> al_A_1 = SubstringCalculator.getAllSubstrings(str_A, 1);
		ArrayList<String> al_A_3 = SubstringCalculator.getAllSubstrings(str_A, 3);

		Assert.assertThat(
			al_A_1,
			hasItems(
				al_Test_1.toArray(new String[al_Test_1.size()])
			)
		);
		Assert.assertThat(
			al_Test_1,
			hasItems(
				al_A_1.toArray(new String[al_A_1.size()])
			)
		);

		Assert.assertThat(
			al_A_3,
			hasItems(
				al_Test_3.toArray(new String[al_Test_3.size()])
			)
		);
		Assert.assertThat(
			al_Test_3,
			hasItems(
				al_A_3.toArray(new String[al_A_3.size()])
			)
		);
	}

	@Test
	public void test_getWordsPairSubstrings () {
		String str_A = "The Banana";

		ArrayList<String> al_Test_3 = new ArrayList<String>(Arrays.asList(new String[] {
			"The Banana", "The Banan", "The Bana", "The Ban", "The Ba", "The B",
			"he Banana", "he Banan", "he Bana", "he Ban", "he Ba", "he B",
			"e Banana", "e Banan", "e Bana", "e Ban", "e Ba", "e B"
		}));

		ArrayList<String> al_Test_4 = new ArrayList<String>(Arrays.asList(new String[] {
			"The Banana", "The Banan", "The Bana", "The Ban", "The Ba", "The B",
			"he Banana", "he Banan", "he Bana", "he Ban", "he Ba", "he B",
			"e Banana", "e Banan", "e Bana", "e Ban", "e Ba"
		}));

		ArrayList<String> al_A_3 = SubstringCalculator.getWordsPairSubstrings(str_A, 3);
		ArrayList<String> al_A_4 = SubstringCalculator.getWordsPairSubstrings(str_A, 4);

		Assert.assertThat(
			al_A_3,
			hasItems(
				al_Test_3.toArray(new String[al_Test_3.size()])
			)
		);
		Assert.assertThat(
			al_Test_3,
			hasItems(
				al_A_3.toArray(new String[al_A_3.size()])
			)
		);

		Assert.assertThat(
			al_A_4,
			hasItems(
				al_Test_4.toArray(new String[al_Test_4.size()])
			)
		);
		Assert.assertThat(
			al_Test_4,
			hasItems(
				al_A_4.toArray(new String[al_A_4.size()])
			)
		);
	}

	@Test
	public void test_tokenize () {
		String str_A = "The Banana Society";

		ArrayList<String> al_Test_False = new ArrayList<String>(Arrays.asList(new String[] {
			"The", "Banana", "Society"
		}));

		ArrayList<String> al_Test_True = new ArrayList<String>(Arrays.asList(new String[] {
			"The", "Banana", "Society", "The Banana", "Banana Society"
		}));

		ArrayList<String> al_A_False = SubstringCalculator.tokenize(str_A, false);
		ArrayList<String> al_A_True = SubstringCalculator.tokenize(str_A, true);

		Assert.assertThat(
			al_A_False,
			hasItems(
				al_Test_False.toArray(new String[al_Test_False.size()])
			)
		);
		Assert.assertThat(
			al_Test_False,
			hasItems(
				al_A_False.toArray(new String[al_A_False.size()])
			)
		);

		Assert.assertThat(
			al_A_True,
			hasItems(
				al_Test_True.toArray(new String[al_Test_True.size()])
			)
		);
		Assert.assertThat(
			al_Test_True,
			hasItems(
				al_A_True.toArray(new String[al_A_True.size()])
			)
		);
	}
}