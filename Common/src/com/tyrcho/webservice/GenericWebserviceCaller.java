package com.tyrcho.webservice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.Service.Mode;
import javax.xml.ws.soap.SOAPBinding;

/**
 * Permet d'appeler un WS quelconque, sans connaitre les structures XML à
 * envoyer ou recevoir.
 */
public class GenericWebserviceCaller {

	private final QName portName;
	private final URL wsdlLoc;
	private Service service;

	/**
	 * Construit un appelant générique pour une url de webservice et pour un
	 * service hébergé à cette URL.
	 */
	public GenericWebserviceCaller(String url, QName serviceName) {
		try {
			wsdlLoc = new URL(url + "?wsdl");
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException(url, e);
		}
		service = Service.create(wsdlLoc, serviceName);
		portName = new QName("port" + new Random().nextInt(999999));
		service.addPort(this.portName, SOAPBinding.SOAP11HTTP_BINDING, url);
	}

	/**
	 * Effectue un appel pour l'XML passé en paramètre.
	 * 
	 * @param mode
	 *            PAYLOAD pour utiliser les XML contenus dans le soap:body,
	 *            MESSAGE pour accéder à l'enveloppe et au header
	 * @return l'XML renvoyé par le service
	 */
	public String execute(String xmlString, Mode mode)
			throws TransformerException, IOException, SOAPException {
		StringReader sr = new StringReader(xmlString);
		Source xmlSource = (Source) new StreamSource(sr);
		Source response = null;
		switch (mode) {
		case PAYLOAD:
			Dispatch<Source> dispSource = service.createDispatch(portName,
					Source.class, mode);
			response = dispSource.invoke(xmlSource);
			break;
		case MESSAGE:
			Dispatch<SOAPMessage> dispMessage = service.createDispatch(
					portName, SOAPMessage.class, mode);
			SOAPMessage message = getSOAPMessageFromSource(xmlSource);
			SOAPMessage soapResponse = dispMessage.invoke(message);
			response = soapResponse.getSOAPPart().getContent();
			break;
		}
		return getXMLFromSource(response);
	}

	private static SOAPMessage getSOAPMessageFromSource(Source src)
			throws TransformerException, IOException, SOAPException {
		SOAPMessage response = null;
		String xmlString = getXMLFromSource(src);
		xmlString = xmlString.replaceAll(
				"<\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?>", "");
		String msgContent = new String(
				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Body>"
						+ xmlString + "</soapenv:Body></soapenv:Envelope>");
		StringReader sr = new StringReader(msgContent);
		Source xmlSource = (Source) new StreamSource(sr);
		MessageFactory factory = MessageFactory.newInstance();
		response = factory.createMessage();
		response.getSOAPPart().setContent(xmlSource);
		response.saveChanges();
		return response;
	}

	private static String getXMLFromSource(Source source)
			throws TransformerException, IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			StreamResult sr = new StreamResult(bos);
			Transformer tf = TransformerFactory.newInstance().newTransformer();
			tf.transform(source, sr);
			return bos.toString();
		} finally {
			bos.close();
		}
	}
}
