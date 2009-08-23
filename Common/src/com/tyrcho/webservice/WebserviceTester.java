package com.tyrcho.webservice;

import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerException;
import javax.xml.ws.Service.Mode;

import com.tyrcho.io.Streams;
import com.tyrcho.xml.Xsl;

public class WebserviceTester {
	private GenericWebserviceCaller caller;
	private final String xsl;

	public WebserviceTester(String url, QName serviceName, String xsl) {
		this.xsl = xsl;
		caller = new GenericWebserviceCaller(url, serviceName);
	}

	public String execute(String inputXml) {
		try {
			String outputXml = caller.execute(inputXml, Mode.PAYLOAD);
			return Xsl.transform(Streams.inputStream(outputXml), Streams.inputStream(xsl));
		} catch (TransformerException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (SOAPException e) {
			throw new RuntimeException(e);
		}
	}

}
