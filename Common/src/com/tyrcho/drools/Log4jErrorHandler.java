package com.tyrcho.drools;

import org.apache.log4j.Logger;
import org.drools.builder.KnowledgeBuilderError;

/**
 * Affiche les erreurs sur la sortie d'erreur standard.
 * 
 * @author daviotm
 */
public class Log4jErrorHandler implements IDroolsErrorHandler {
	private Logger logger=Logger.getLogger(getClass());
	
	@Override
	public void handleError(KnowledgeBuilderError error) {
		logger.error(error);
	}

}
