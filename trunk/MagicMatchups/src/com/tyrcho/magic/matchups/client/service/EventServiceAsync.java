package com.tyrcho.magic.matchups.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tyrcho.magic.matchups.client.model.Event;

public interface EventServiceAsync {

	void addEvent(Event event, AsyncCallback<Void> callback);

	

}
