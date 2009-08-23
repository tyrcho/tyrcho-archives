package test.com.tyrcho.introspection;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import com.tyrcho.introspection.Introspector;
import com.tyrcho.io.Streams;

public class IntrospectorTest {
	@Test
	public void simpleTest() throws IOException {
		Simple o = new Simple();
		o.privateString="pp";
		o.publicInt=12;
		String expected=Streams.readStream(getClass().getResourceAsStream("expectedSimple.txt"));
		expected=expected.replaceAll("hash1", Integer.toHexString(o.hashCode()));
		String res = Introspector.toString(o);
		Assert.assertEquals(expected, res);
	}

	static class Simple {
		private String privateString;
		public int publicInt;
	}

}
