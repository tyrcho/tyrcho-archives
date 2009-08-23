package com.tyrcho.drools;

import org.drools.builder.KnowledgeBuilderError;

/**
 * Traite des erreurs remont�es par Drools lors de l'ex�cution des r�gles.
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
