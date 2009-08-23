package com.tyrcho.drools.conversion;

import org.apache.log4j.Logger;

import com.tyrcho.aspect.AbstractAspect;

/**
 * Traitement des erreurs sur la conversion.
 * 
 * @author daviotm
 */
public class ConversionErrorHandlerAspect<T> extends AbstractAspect<T> {

	protected ConversionErrorHandlerAspect(Class<T> interfaceObject) {
		super(interfaceObject);
	}

	@Override
	protected void after() {
		if (exception != null) {
			Logger.getLogger(delegate.getClass()).error("Erreur de conversion",
					exception);
		}
	}

	@Override
	protected void before() {
	}

}
