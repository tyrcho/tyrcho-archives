package com.tyrcho.drools;

import org.drools.builder.KnowledgeBuilderError;

/**
 * Affiche les erreurs sur la sortie d'erreur standard.
 * 
 * @author daviotm
 */
public class ConsoleErrorHandler implements IDroolsErrorHandler {

	@Override
	public void handleError(KnowledgeBuilderError error) {
		System.err.println(error);
	}

}
