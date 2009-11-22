package com.tyrcho.magic.matchups.server.service;

import javax.jdo.PersistenceManager;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tyrcho.magic.matchups.client.model.Event;
import com.tyrcho.magic.matchups.client.service.EventService;
import com.tyrcho.magic.matchups.server.PMF;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class EventServiceImpl extends RemoteServiceServlet implements
		EventService {

	@Override
	public void addEvent(Event event) {
		PersistenceManager persistenceManager = PMF.get()
				.getPersistenceManager();
		try {
			persistenceManager.makePersistent(event);
		} finally {
			persistenceManager.close();
		}
	}
}
