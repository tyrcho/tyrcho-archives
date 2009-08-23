package com.tyrcho.drools;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.common.DefaultFactHandle;
import org.drools.event.process.ProcessEventListener;
import org.drools.event.rule.AgendaEventListener;
import org.drools.event.rule.WorkingMemoryEventListener;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.FactHandle;

/**
 * Interroge Drools :
 * <ul>
 * <li>insertion des faits
 * <li>execution des règles
 * <li>retourn les faits restants dans la mémoire
 * <li>gestion des erreurs
 * </ul>
 * 
 * @author daviotm
 * 
 */
public class QueryDrools implements IQueryDrools {

	private KnowledgeBase knowledgeBase;
	private KnowledgeBuilder kbuilder;
	Collection<IDroolsErrorHandler> errorHandlers = new ArrayList<IDroolsErrorHandler>();
	private Logger logger = Logger.getLogger(getClass().getName());
	private IEventListener eventListener=new LoggerEventListener();

	public QueryDrools(KnowledgeBuilder builder) {
		kbuilder = builder;
		knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
		knowledgeBase.addKnowledgePackages(kbuilder.getKnowledgePackages());
	}

	public void addErrorHandler(IDroolsErrorHandler errorHandler) {
		errorHandlers.add(errorHandler);
	}

	protected void fireError(KnowledgeBuilderError error) {
		for (IDroolsErrorHandler errorHandler : errorHandlers) {
			errorHandler.handleError(error);
		}
	}

	public Collection<Object> query(Iterable<?> facts) {
		StatefulKnowledgeSession session = knowledgeBase
				.newStatefulKnowledgeSession();
		for (Object fact : facts) {
			session.insert(fact);
		}
		session.addEventListener((AgendaEventListener)eventListener);
		session.addEventListener((WorkingMemoryEventListener)eventListener);
		session.addEventListener((ProcessEventListener)eventListener);
		int rules=session.fireAllRules();
		logger.info(rules+" rules fired for " + facts);
		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		for (KnowledgeBuilderError error : errors) {
			logger.error(error.toString());
			fireError(error);
		}
		Collection<FactHandle> factHandles = session.getFactHandles();
		Collection<Object> resultFacts = new ArrayList<Object>();
		for (FactHandle factHandle : factHandles) {
			resultFacts.add(((DefaultFactHandle) factHandle).getObject());
		}
		logger.info(resultFacts.size() + " facts returned");
		logger.debug(resultFacts.toString());
		session.dispose();
		return resultFacts;
	}

}
