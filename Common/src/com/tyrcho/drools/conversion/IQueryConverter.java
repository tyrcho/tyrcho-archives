package com.tyrcho.drools.conversion;

import java.util.Collection;

/**
 * Spécifie comment convertir un objet entrant en liste de faits.
 * 
 * @author daviotm
 * 
 */
public interface IQueryConverter {
	Collection<Object> convert(Object paramter);
}
