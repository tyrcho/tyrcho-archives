package com.tyrcho.drools.conversion;

import com.tyrcho.drools.configuration.ConversionConfiguration;


public class ConversionFactory implements IConversionFactory {

	private static <T> T instanciate(String className) {
		try {
			return (T) Class.forName(className).newInstance();
		} catch (InstantiationException e) {
			throw new IllegalArgumentException("Impossible d'instancier "
					+ className);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException("Impossible d'instancier "
					+ className);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Impossible d'instancier "
					+ className);
		}
	}

	private IAnswerConverter answerConverter;

	private IQueryConverter queryConverter;

	public ConversionFactory(ConversionConfiguration configuration) {
		this(configuration.getAnswerConverterClass(), configuration
				.getQueryConverterClass());
	}

	/**
	 * Construit les convertisseurs par réflexion.
	 * 
	 * @param answerClassName
	 *            la classe à utiliser pour le IAnswerConverter
	 * @param queryClassName
	 *            la classe à utiliser pour le IQueryConverter
	 */
	public ConversionFactory(String answerClassName, String queryClassName) {
		answerConverter = instanciate(answerClassName);
		queryConverter = instanciate(queryClassName);
	}

	public IAnswerConverter buildAnswerConverter() {
		return new ConversionErrorHandlerAspect<IAnswerConverter>(
				IAnswerConverter.class).applyTo(answerConverter);
	}

	public IQueryConverter buildQueryConverter() {
		return new ConversionErrorHandlerAspect<IQueryConverter>(
				IQueryConverter.class).applyTo(queryConverter);
	}
}
