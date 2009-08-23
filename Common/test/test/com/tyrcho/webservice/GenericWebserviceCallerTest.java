package test.com.tyrcho.webservice;

import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerException;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service.Mode;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tyrcho.io.Streams;
import com.tyrcho.webservice.GenericWebserviceCaller;

public class GenericWebserviceCallerTest {
	private static final QName SERVICE_NAME = new QName("urn:tyrcho.com:hello",
			"HelloService");
	private static final String HELLO_URL = "http://localhost:8989/hello";
	private static Endpoint endpoint;
	private static String helloInputXml;
	private static String byeInputXml;
	private static GenericWebserviceCaller caller;

	@Test
	public void payloadHello() throws TransformerException, IOException,
			SOAPException {
		String hello = caller.execute(helloInputXml, Mode.PAYLOAD);
		Assert.assertTrue(hello.contains("Hello, tyrcho !"));
	}

	@Test
	public void messages() throws TransformerException, IOException,
			SOAPException {
		String hello = caller.execute(helloInputXml, Mode.MESSAGE);
		Assert.assertTrue(hello.contains("Hello, tyrcho !"));
		String bye = caller.execute(byeInputXml, Mode.MESSAGE);
		Assert.assertTrue(bye.contains("Bye, tyrcho !"));
	}

	@Test
	public void payloadBye() throws TransformerException, IOException,
			SOAPException {
		String hello = caller.execute(byeInputXml, Mode.PAYLOAD);
		Assert.assertTrue(hello.contains("Bye, tyrcho !"));
	}

	@BeforeClass
	public static void setup() throws IOException {
		endpoint = Endpoint.publish(HELLO_URL, new HelloWorldService());
		helloInputXml = Streams.readStream(GenericWebserviceCallerTest.class
				.getResourceAsStream("helloInput.xml"));
		byeInputXml = Streams.readStream(GenericWebserviceCallerTest.class
				.getResourceAsStream("byeInput.xml"));
		caller = new GenericWebserviceCaller(HELLO_URL, SERVICE_NAME);
	}

	@AfterClass
	public static void teardown() {
		endpoint.stop();
	}
}
