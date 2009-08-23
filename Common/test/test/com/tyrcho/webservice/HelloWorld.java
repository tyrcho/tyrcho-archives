package test.com.tyrcho.webservice;

import javax.jws.WebService;

@WebService
public interface HelloWorld {
	String hello(String user);
	String bye(String user);
}