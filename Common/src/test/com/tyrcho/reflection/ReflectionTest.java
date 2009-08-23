package test.com.tyrcho.reflection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.tyrcho.reflection.Reflection;

public class ReflectionTest {
	@Test
	public void getAllFieldValuesTest() {
		Simple s = new Simple();
		s.privateString = "s";
		s.publicInt = 2;
		Map<String, Object> values = Reflection.getAllFieldValues(s);
		Assert.assertEquals(2, values.get("publicInt"));
		Assert.assertEquals("s", values.get("privateString"));
	}

	@Test
	public void getAllFieldValuesCheckOrder() {
		Map<String, Object> values = Reflection
				.getAllFieldValues(new SeveralFields());
		org.junit.Assert.assertArrayEquals("arrays in same order", new String[] {"a","b","c","d","e","f","g"}, values.keySet().toArray());
	}

	static class SeveralFields {
		private String a, b, c, d, e, f, g;
	}

	@Test
	public void getAllFieldValuesTestChild() {
		Child s = new Child();
		s.publicInt = 2;
		s.value = 2.0;
		Map<String, Object> values = Reflection.getAllFieldValues(s);
		Assert.assertEquals(2.0, values.get("value"));
		Assert.assertEquals(2, values.get("publicInt"));
	}

	@Test
	public void getAllFieldValuesTestCollection() {
		Child s = new Child();
		s.strings = Arrays.asList("a", "b");
		Map<String, Object> values = Reflection.getAllFieldValues(s);
		Assert.assertSame(s.strings, values.get("strings"));
	}

	class Simple {
		private String privateString;
		public int publicInt;
	}

	class Child extends Simple {
		private double value;
		private Collection<String> strings;
	}
}
