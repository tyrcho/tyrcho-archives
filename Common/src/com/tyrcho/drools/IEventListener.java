package com.tyrcho.drools;

import org.drools.event.process.ProcessEventListener;
import org.drools.event.rule.AgendaEventListener;
import org.drools.event.rule.WorkingMemoryEventListener;

/**
 * Reçoit tous les évènements lancés par la session Drools.
 * 
 * @author daviotm
 */
public interface IEventListener extends AgendaEventListener,
		WorkingMemoryEventListener, ProcessEventListener {

}
