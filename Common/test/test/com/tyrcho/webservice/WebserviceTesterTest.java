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

public class WebserviceTesterTest {
	private static final QName SERVICE_NAME = new QName("urn:tyrcho.com:hello",
			"HelloService");
	private static final String HELLO_URL = "http://localhost:8989/hello";
	private static WebserviceTester webserviceTester;
	private static Endpoint endpoint;
	private static String helloInputXml;

	@BeforeClass
	public static void setup() throws IOException {
		endpoint = Endpoint.publish(HELLO_URL, new HelloWorldService());
		webserviceTester = new WebserviceTester(HELLO_URL, SERVICE_NAME, Streams.readTextResource(
				"transform.xsl", WebserviceTesterTest.class));
		helloInputXml = Streams.readStream(GenericWebserviceCallerTest.class
				.getResourceAsStream("helloInput.xml"));
	}
	
	@Test
	public void simpleTest(){
		String result = webserviceTester.execute(helloInputXml);
		Assert.assertEquals("Hello, tyrcho !", result);
	}
	
	@AfterClass
	public static void tearDown() {
		endpoint.stop();
	}
}
