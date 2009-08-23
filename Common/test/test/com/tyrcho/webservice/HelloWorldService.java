package test.com.tyrcho.webservice;

import javax.jws.WebService;

@WebService(targetNamespace="urn:tyrcho.com:hello", portName="helloPort", serviceName="HelloService")
public class HelloWorldService implements HelloWorld {
	public String bye(String user) {
		return String.format("Bye, %s !", user);
	}
	
	public String hello(String user) {
		return String.format("Hello, %s !", user);
	}
	
	
}
