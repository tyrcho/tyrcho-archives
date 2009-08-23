package com.tyrcho.drools;

import org.drools.builder.KnowledgeBuilderError;

/**
 * Traite des erreurs remontées par Drools lors de l'exécution des règles.
 * 
 * @author daviotm
 * 
 */
public interface IDroolsErrorHandler {
	/**
	 * Traite une erreur.
	 */
	void handleError(KnowledgeBuilderError error);
}
