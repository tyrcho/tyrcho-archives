package com.tyrcho.drools.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class DroolsFileConfiguration {
	@XmlAttribute(required = true)
	private DroolsResourceType resourceType;
	@XmlAttribute(required = true)
	private String fileName;

	public DroolsResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(DroolsResourceType resourceType) {
		this.resourceType = resourceType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
