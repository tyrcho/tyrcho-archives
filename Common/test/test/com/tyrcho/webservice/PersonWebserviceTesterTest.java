package test.com.tyrcho.webservice;

import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tyrcho.io.Streams;
import com.tyrcho.webservice.WebserviceTester;

public class PersonWebserviceTesterTest {
	private static final QName SERVICE_NAME = new QName("urn:tyrcho.com:person",
			"PersonService");
	private static final String PERSON_URL = "http://localhost:8989/person";
	private static WebserviceTester webserviceTester;
	private static Endpoint endpoint;
	private static String inputXml;

	@BeforeClass
	public static void setup() throws IOException {
		endpoint = Endpoint.publish(PERSON_URL, new PersonService());
		webserviceTester = new WebserviceTester(PERSON_URL, SERVICE_NAME, Streams.readTextResource(
				"personTransform.xsl", PersonWebserviceTesterTest.class));
		inputXml = Streams.readStream(GenericWebserviceCallerTest.class
				.getResourceAsStream("personInput.xml"));
	}
	
	@Test
	public void simpleTest(){
		String result = webserviceTester.execute(inputXml);
		Assert.assertEquals("monsieur tyrcho", result);
	}
	
	@AfterClass
	public static void tearDown() {
		endpoint.stop();
	}
}
