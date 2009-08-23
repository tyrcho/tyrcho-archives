package com.tyrcho.drools.engine;

import java.util.Collection;

import com.tyrcho.drools.IQueryDrools;
import com.tyrcho.drools.conversion.IAnswerConverter;
import com.tyrcho.drools.conversion.IConversionFactory;
import com.tyrcho.drools.conversion.IQueryConverter;

/**
 * Encadre l'appel à Drools par les appels aux convertisseurs.
 * 
 * @author daviotm
 */
public class Engine {
	private final IQueryDrools drools;
	private IQueryConverter factConverter;
	private IAnswerConverter answerConverter;

	/**
	 * Initialisation avec le moteur de requêtes Drools et la fabrique de
	 * convertisseurs.
	 */
	public Engine(IQueryDrools drools, IConversionFactory conversionFactory) {
		this.drools = drools;
		factConverter = conversionFactory.buildQueryConverter();
		answerConverter = conversionFactory.buildAnswerConverter();
	}

	/**
	 * Encadre l'appel à Drools par les appels aux convertisseurs.
	 */
	public Object executeRequest(Object request) {
		Collection<?> facts = factConverter.convert(request);
		Collection<Object> results = drools.query(facts);
		Object result = answerConverter.convert(results);
		return result;
	}
}
