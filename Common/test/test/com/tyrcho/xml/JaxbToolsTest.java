package test.com.tyrcho.xml;

import junit.framework.Assert;

import org.junit.Test;

import com.tyrcho.xml.JaxbTools;

public class JaxbToolsTest {
	@Test
	public void readWithValidationTest() {
		Person person = JaxbTools.readObject(getClass().getResourceAsStream(
				"person.xml"), Person.class, getClass().getResource(
				"sample.xsd"));
		Assert.assertEquals("toto", person.getName());
	}

	@Test(expected = RuntimeException.class)
	public void readWithValidationErrorTest() {
		JaxbTools.readObject(getClass()
				.getResourceAsStream("invalidPerson.xml"), Person.class,
				getClass().getResource("sample.xsd"));
	}

	@Test
	public void readWithNoValidationTest() {
		Person person = JaxbTools.readObject(getClass().getResourceAsStream(
				"invalidPerson.xml"), Person.class);
		Assert.assertEquals("toto", person.getName());
	}
}
