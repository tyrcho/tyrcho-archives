package test.com.tyrcho.xml;

import junit.framework.Assert;

import org.junit.Test;

import com.tyrcho.xml.Xsl;

public class XslTest {
	@Test
	public void simpleTransform() {
		String transform = Xsl.transform(getClass().getResourceAsStream(
				"input.xml"), getClass().getResourceAsStream("transform.xsl"));
		Assert.assertEquals("world", transform);
	}
}
