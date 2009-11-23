package com.tyrcho.magic.matchups.client.editor;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.tyrcho.magic.matchups.client.callback.CallbackFactory;
import com.tyrcho.magic.matchups.client.service.EventService;
import com.tyrcho.magic.matchups.client.service.EventServiceAsync;
import com.tyrcho.magic.matchups.client.service.ServiceFactory;

public class EditEventDialog extends DialogBox {

	private EventEditPanel eventEditor;
	private EventServiceAsync eventService = ServiceFactory.create(EventService.class);

	public EditEventDialog() {
		super(false, true);
		setText("Edit Event");
		VerticalPanel panel = new VerticalPanel();
		Button close = new Button("Close");
		close.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				AsyncCallback<Void> defaultCallback = CallbackFactory.buildDefault("Saving Event");
				eventService.add(eventEditor.getValue(),
						defaultCallback);
				EditEventDialog.this.hide();
			}
		});
		eventEditor = new EventEditPanel();
		panel.add(eventEditor.getWidget());
		panel.add(close);
		setWidget(panel);
	}
}
