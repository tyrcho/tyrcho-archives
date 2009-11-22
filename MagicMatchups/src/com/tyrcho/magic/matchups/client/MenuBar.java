package com.tyrcho.magic.matchups.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class MenuBar extends HorizontalPanel{
	public MenuBar() {
		Button editDeck = new Button("Edit Event");
		add(editDeck);
		editDeck.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				new EditEventDialog().center();
			}
		});
	}
}
