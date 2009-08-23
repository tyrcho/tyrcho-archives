package com.tyrcho.drools.conversion;

import java.util.Collection;

/**
 * Sp�cifie comment convertir une liste de faits en objet sortant.
 * 
 * @author daviotm
 * 
 */
public interface IAnswerConverter {
	Object convert(Collection<?> facts);
}
