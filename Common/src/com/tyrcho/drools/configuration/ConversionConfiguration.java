package com.tyrcho.drools.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ConversionConfiguration {
	@XmlElement(required = true)
	private String answerConverterClass;
	@XmlElement(required = true)
	private String queryConverterClass;

	public ConversionConfiguration() {
	}

	public String getAnswerConverterClass() {
		return answerConverterClass;
	}

	public String getQueryConverterClass() {
		return queryConverterClass;
	}

	public void setAnswerConverterClass(String answerConverterClass) {
		this.answerConverterClass = answerConverterClass;
	}

	public void setQueryConverterClass(String queryConverterClass) {
		this.queryConverterClass = queryConverterClass;
	}
}