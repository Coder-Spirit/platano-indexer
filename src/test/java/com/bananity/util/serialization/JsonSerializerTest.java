package com.bananity.util.serialization;


// Main Class
import com.bananity.util.serialization.JsonSerializer;

// Java Utils
import java.util.LinkedHashMap;

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
public class JsonSerializerTest
{
	@Test
	public void test_ObjectToJsonString_checkNullSerialization () {
		Assert.assertEquals("null", JsonSerializer.ObjectToJsonString(null));
	}

	@Test
	public void test_ObjectToJsonString_checkBooleanSerialization () {
		Assert.assertEquals("true", JsonSerializer.ObjectToJsonString(true));
		Assert.assertEquals("false", JsonSerializer.ObjectToJsonString(false));
	}

	@Test
	public void test_ObjectToJsonString_checkIntegerSerialization () {
		Assert.assertEquals("3654", JsonSerializer.ObjectToJsonString(3654));
		Assert.assertEquals("0", JsonSerializer.ObjectToJsonString(0));
		Assert.assertEquals("-3654", JsonSerializer.ObjectToJsonString(-3654));
	}

	@Test
	public void test_ObjectToJsonString_checkLongSerialization () {
		Assert.assertEquals("4294967296", JsonSerializer.ObjectToJsonString(4294967296l));
		Assert.assertEquals("0", JsonSerializer.ObjectToJsonString(0l));
		Assert.assertEquals("-4294967296", JsonSerializer.ObjectToJsonString(-4294967296l));
	}

	@Test
	public void test_ObjectToJsonString_checkFloatTypesSerialization () {
		Assert.assertEquals("3.0E7", JsonSerializer.ObjectToJsonString(3e7f));
		Assert.assertEquals("100.0", JsonSerializer.ObjectToJsonString(100.f));
		Assert.assertEquals("0.3", JsonSerializer.ObjectToJsonString(0.3f));
		Assert.assertEquals("3.0E-17", JsonSerializer.ObjectToJsonString(3e-17f));
		Assert.assertEquals("0.0", JsonSerializer.ObjectToJsonString(0.f));
		Assert.assertEquals("-3.0E-17", JsonSerializer.ObjectToJsonString(-3e-17f));
		Assert.assertEquals("-0.3", JsonSerializer.ObjectToJsonString(-0.3f));
		Assert.assertEquals("-3.0E7", JsonSerializer.ObjectToJsonString(-3e7f));

		// JSON doesn't represents NaN nor infinite values
		Assert.assertEquals("null", JsonSerializer.ObjectToJsonString(Float.NaN));
		Assert.assertEquals("null", JsonSerializer.ObjectToJsonString(Float.POSITIVE_INFINITY));
		Assert.assertEquals("null", JsonSerializer.ObjectToJsonString(Float.NEGATIVE_INFINITY));
	}

	@Test
	public void test_ObjectToJsonString_checkDoubleTypesSerialization () {
		Assert.assertEquals("3.0E17", JsonSerializer.ObjectToJsonString(3e17));
		Assert.assertEquals("100.0", JsonSerializer.ObjectToJsonString(100.));
		Assert.assertEquals("0.3", JsonSerializer.ObjectToJsonString(0.3));
		Assert.assertEquals("3.0E-17", JsonSerializer.ObjectToJsonString(3e-17));
		Assert.assertEquals("0.0", JsonSerializer.ObjectToJsonString(0.));
		Assert.assertEquals("-3.0E-17", JsonSerializer.ObjectToJsonString(-3e-17));
		Assert.assertEquals("-0.3", JsonSerializer.ObjectToJsonString(-0.3));
		Assert.assertEquals("-3.0E17", JsonSerializer.ObjectToJsonString(-3e17));

		// JSON doesn't represents NaN nor infinite values
		Assert.assertEquals("null", JsonSerializer.ObjectToJsonString(Double.NaN));
		Assert.assertEquals("null", JsonSerializer.ObjectToJsonString(Double.POSITIVE_INFINITY));
		Assert.assertEquals("null", JsonSerializer.ObjectToJsonString(Double.NEGATIVE_INFINITY));
	}

	@Test
	public void test_ObjectToJsonString_checkStringSerialization () {
		Assert.assertEquals("\" Hola \\n \\t \\\"Mundo\\\" \"", JsonSerializer.ObjectToJsonString(" Hola \n \t \"Mundo\" "));
	}

	@Test
	public void test_ObjectToJsonString_checkMapSerialization () {
		// We use LinkedHashMap in order to have predictable iteration order.
		LinkedHashMap<String, Object> mapToSerialize = new LinkedHashMap<String, Object>();

		mapToSerialize.put("entero", 45);
		mapToSerialize.put("booleano", false);
		mapToSerialize.put("nulo", null);
		mapToSerialize.put("cadena", "Hola Mundo");

		Assert.assertEquals("{\"entero\":45,\"booleano\":false,\"nulo\":null,\"cadena\":\"Hola Mundo\"}", JsonSerializer.ObjectToJsonString(mapToSerialize));
	}
}