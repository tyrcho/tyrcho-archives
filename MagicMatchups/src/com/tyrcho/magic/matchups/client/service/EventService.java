package com.tyrcho.magic.matchups.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.tyrcho.magic.matchups.client.model.Event;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("service/event")
public interface EventService extends RemoteService {
	void addEvent(Event event);
	
	List<Event> selectAll();
}
