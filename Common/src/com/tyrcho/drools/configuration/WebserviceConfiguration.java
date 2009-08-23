package com.tyrcho.drools.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WebserviceConfiguration {
	@XmlElement(required=true)
	private String endpointURL;
	@XmlElement(required=true)
	private DroolsConfiguration droolsConfiguration;
	@XmlElement(required=true)
	private ConversionConfiguration conversionConfiguration;
	@XmlElement(required=true)
	private String WebserviceImplementationClass;

	public String getWebserviceImplementationClass() {
		return WebserviceImplementationClass;
	}

	public void setWebserviceImplementationClass(
			String webserviceImplementationClass) {
		WebserviceImplementationClass = webserviceImplementationClass;
	}

	public WebserviceConfiguration() {
	}

	public ConversionConfiguration getConversionConfiguration() {
		return conversionConfiguration;
	}

	public DroolsConfiguration getDroolsConfiguration() {
		return droolsConfiguration;
	}

	public String getEndpointURL() {
		return endpointURL;
	}

	public void setConversionConfiguration(
			ConversionConfiguration conversionConfiguration) {
		this.conversionConfiguration = conversionConfiguration;
	}

	public void setDroolsConfiguration(DroolsConfiguration droolsConfiguration) {
		this.droolsConfiguration = droolsConfiguration;
	}

	public void setEndpointURL(String endpointURL) {
		this.endpointURL = endpointURL;
	}
}
