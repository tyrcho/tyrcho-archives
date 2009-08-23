package com.tyrcho.drools.conversion;

/**
 * Spécifie comment obtenir les convertisseurs de faits vers et depuis les
 * objets.
 * 
 * @author daviotm
 */
public interface IConversionFactory {

	public IAnswerConverter buildAnswerConverter();

	public IQueryConverter buildQueryConverter();

}