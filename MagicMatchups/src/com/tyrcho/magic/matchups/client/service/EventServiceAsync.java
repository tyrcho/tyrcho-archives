package com.tyrcho.magic.matchups.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tyrcho.magic.matchups.client.model.Event;

public interface EventServiceAsync {

	void addEvent(Event event, AsyncCallback<Void> callback);

	void selectAll(AsyncCallback<List<Event>> callback);

	

}
