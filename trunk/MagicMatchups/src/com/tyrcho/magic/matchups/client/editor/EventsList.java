package com.tyrcho.magic.matchups.client.editor;

import java.util.List;

import com.google.gwt.user.client.ui.FlexTable;
import com.tyrcho.magic.matchups.client.callback.CallbackFactory;
import com.tyrcho.magic.matchups.client.callback.SuccessCallback;
import com.tyrcho.magic.matchups.client.model.Event;
import com.tyrcho.magic.matchups.client.service.EventService;
import com.tyrcho.magic.matchups.client.service.EventServiceAsync;
import com.tyrcho.magic.matchups.client.service.ServiceFactory;

public class EventsList extends FlexTable {
	EventServiceAsync eventService = ServiceFactory.create(EventService.class);

	public EventsList() {
		eventService.selectAll(CallbackFactory.buildDefault(
				"Select all events", new SuccessCallback<List<Event>>() {
					@Override
					public void onSuccess(List<Event> result) {
						setEvents(result);
					}
				}));
	}

	public void setEvents(List<Event> events) {
		int i = 0;
		for (Event event : events) {
			setText(i++, 0, event.getName());
		}
	}
}
