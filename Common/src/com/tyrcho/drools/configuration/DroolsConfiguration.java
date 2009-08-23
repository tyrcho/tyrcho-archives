package com.tyrcho.drools.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class DroolsConfiguration {
	@XmlElement(name="droolFile")
	private DroolsFileConfiguration[] droolFiles;
	@XmlElement(name="errorHandlerClass")
	private String[] errorHandlerClasses;

	public String[] getErrorHandlerClasses() {
		return errorHandlerClasses;
	}

	public void setErrorHandlerClasses(String[] errorHandlerClasses) {
		this.errorHandlerClasses = errorHandlerClasses;
	}

	public DroolsFileConfiguration[] getDroolFiles() {
		return droolFiles;
	}

	public void setDroolFiles(DroolsFileConfiguration[] droolFiles) {
		this.droolFiles = droolFiles;
	}
}
