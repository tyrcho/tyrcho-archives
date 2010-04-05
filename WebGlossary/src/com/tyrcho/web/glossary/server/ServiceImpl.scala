package com.tyrcho.web.glossary.server

import com.tyrcho.web.glossary.client.GreetingService
import com.tyrcho.web.glossary.shared.FieldVerifier
import com.google.gwt.user.server.rpc.RemoteServiceServlet

class GreetingServiceImpl extends RemoteServiceServlet with
		GreetingService {
	def greetServer(input : String) : String = {
	
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		def serverInfo = getServletContext().getServerInfo();
		def userAgent = getThreadLocalRequest().getHeader("User-Agent");
		return "Hello, " + input + "!<br><br>I am running " + serverInfo + " with Scala !<br><br>It looks like you are using:<br>" + userAgent;
	}
}
