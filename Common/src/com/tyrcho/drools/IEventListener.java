package com.tyrcho.drools;

import org.drools.event.process.ProcessEventListener;
import org.drools.event.rule.AgendaEventListener;
import org.drools.event.rule.WorkingMemoryEventListener;

/**
 * Re�oit tous les �v�nements lanc�s par la session Drools.
 * 
 * @author daviotm
 */
public interface IEventListener extends AgendaEventListener,
		WorkingMemoryEventListener, ProcessEventListener {

}
