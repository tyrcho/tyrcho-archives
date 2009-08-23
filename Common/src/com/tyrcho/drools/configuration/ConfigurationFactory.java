package com.tyrcho.drools.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.SchemaFactory;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

public class ConfigurationFactory {
	private static Logger logger=Logger.getLogger(ConfigurationFactory.class);
	
	public static WebserviceConfiguration readConfiguration(String filePath) {
		File file = new File(filePath);
		try {
			InputStream stream = new FileInputStream(file);
			Unmarshaller unmarshaller = JAXBContext.newInstance(
					WebserviceConfiguration.class).createUnmarshaller();
			URL schema = ConfigurationFactory.class.getResource("wsConf.xsd");
			SchemaFactory schemaFactory = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			unmarshaller.setSchema(schemaFactory.newSchema(schema));
			WebserviceConfiguration conf = (WebserviceConfiguration) unmarshaller
					.unmarshal(stream);
			logger.info("Configuration lue depuis "+file.getAbsolutePath());
			return conf;
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Could not find "
					+ file.getAbsolutePath(), e);
		} catch (JAXBException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (SAXException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
