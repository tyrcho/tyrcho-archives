package com.tyrcho.drools;

import org.apache.log4j.Logger;
import org.drools.definition.rule.Rule;
import org.drools.event.process.ProcessCompletedEvent;
import org.drools.event.process.ProcessNodeLeftEvent;
import org.drools.event.process.ProcessNodeTriggeredEvent;
import org.drools.event.process.ProcessStartedEvent;
import org.drools.event.rule.ActivationCancelledEvent;
import org.drools.event.rule.ActivationCreatedEvent;
import org.drools.event.rule.ActivationEvent;
import org.drools.event.rule.AfterActivationFiredEvent;
import org.drools.event.rule.AgendaGroupPoppedEvent;
import org.drools.event.rule.AgendaGroupPushedEvent;
import org.drools.event.rule.BeforeActivationFiredEvent;
import org.drools.event.rule.ObjectInsertedEvent;
import org.drools.event.rule.ObjectRetractedEvent;
import org.drools.event.rule.ObjectUpdatedEvent;
/**
 * Loggue tous les évènements dans log4j en debug.
 * 
 * @author daviotm
 */
public class LoggerEventListener implements IEventListener {
	private Logger logger = Logger.getLogger(getClass());

	@Override
	public void objectInserted(ObjectInsertedEvent event) {
		logger.debug("inserted " + event.getObject());
	}

	@Override
	public void objectRetracted(ObjectRetractedEvent event) {
		logger.debug("retracted " + event.getOldObject());
	}

	@Override
	public void objectUpdated(ObjectUpdatedEvent event) {
		logger.debug("updated " + event.getObject());
	}

	
	@Override
	public void afterNodeLeft(ProcessNodeLeftEvent event) {
		logger.debug("afterNodeLeft " + event.getNodeInstance().getNodeName());
	}

	@Override
	public void afterNodeTriggered(ProcessNodeTriggeredEvent event) {
		logger.debug("afterNodeTriggered "
				+ event.getNodeInstance().getNodeName());
	}

	@Override
	public void afterProcessCompleted(ProcessCompletedEvent event) {
		logger.debug("afterProcessCompleted "
				+ event.getProcessInstance().getProcessName());
	}

	@Override
	public void afterProcessStarted(ProcessStartedEvent event) {
		logger.debug("afterProcessStarted "
				+ event.getProcessInstance().getProcessName());
	}

	@Override
	public void beforeNodeLeft(ProcessNodeLeftEvent event) {
		logger.debug("afterNodeLeft " + event.getNodeInstance().getNodeName());
	}

	@Override
	public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {
		logger.debug("beforeNodeTriggered "
				+ event.getNodeInstance().getNodeName());
	}

	@Override
	public void beforeProcessCompleted(ProcessCompletedEvent event) {
		logger.debug("beforeProcessCompleted "
				+ event.getProcessInstance().getProcessName());
	}

	@Override
	public void beforeProcessStarted(ProcessStartedEvent event) {
		logger.debug("beforeProcessStarted "
				+ event.getProcessInstance().getProcessName());
	}

	@Override
	public void activationCancelled(ActivationCancelledEvent event) {
		logActivationEvent("activationCancelled", event);
	}

	@Override
	public void activationCreated(ActivationCreatedEvent event) {
		logActivationEvent("activationCreated", event);
	}

	@Override
	public void afterActivationFired(AfterActivationFiredEvent event) {
		logActivationEvent("afterActivationFired ",event);
	}

	private void logActivationEvent(String eventName, ActivationEvent event) {
		Rule rule = event.getActivation().getRule();
		Logger.getLogger(rule.getPackageName()+"."+rule.getName()).debug(eventName);
	}

	@Override
	public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
		logger.debug("agendaGroupPopped "+event.getAgendaGroup().getName());
	}

	@Override
	public void agendaGroupPushed(AgendaGroupPushedEvent event) {
		logger.debug("agendaGroupPushed "+event.getAgendaGroup().getName());
	}

	@Override
	public void beforeActivationFired(BeforeActivationFiredEvent event) {
		logActivationEvent("beforeActivationFired ",event);
	}

}
