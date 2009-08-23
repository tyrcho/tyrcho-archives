package com.tyrcho.xml;

import java.io.InputStream;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

public class JaxbTools {
	/**
	 * Lit un objet avec validation, depuis un flux XML.
	 * 
	 * @param stream
	 *            le flux à lire
	 * @param clazz
	 *            la classe attendue de l'objet
	 * @param schema
	 *            l'URL où se trouve le schéma pour la validation ou null
	 */
	public static <T> T readObject(InputStream stream, Class<T> clazz,
			URL schema) {
		try {
			Unmarshaller unmarshaller = JAXBContext.newInstance(clazz)
					.createUnmarshaller();
			if (schema != null) {
				SchemaFactory schemaFactory = SchemaFactory
						.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
				unmarshaller.setSchema(schemaFactory.newSchema(schema));
			}
			return (T) unmarshaller.unmarshal(stream);
		} catch (JAXBException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (SAXException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * Lit un objet depuis un flux XML sans validation.
	 */
	public static <T> T readObject(InputStream stream, Class<T> clazz) {
		return readObject(stream, clazz, null);
	}
}
