package com.tyrcho.magic.matchups.server.service;

import com.tyrcho.magic.matchups.client.model.Event;
import com.tyrcho.magic.matchups.client.service.EventService;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class EventServiceImpl extends DaoServiceServlet<Event> implements
		EventService {

	public EventServiceImpl() {
		super(Event.class);
	}
}
