package com.tyrcho.magic.matchups.server.service;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tyrcho.magic.matchups.client.model.Event;
import com.tyrcho.magic.matchups.client.service.EventService;
import com.tyrcho.magic.matchups.server.dao.DAOFactory;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class EventServiceImpl extends RemoteServiceServlet implements
		EventService {

	@Override
	public void addEvent(Event event) {
		DAOFactory.buildDAO(Event.class).makePersistant(event);
	}

	@Override
	public List<Event> selectAll() {
		return DAOFactory.buildDAO(Event.class).selectAll();
	}
}
