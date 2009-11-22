package com.tyrcho.magic.matchups.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.tyrcho.magic.matchups.client.model.EventLevel;

public class EditEventDialog extends DialogBox {
	public EditEventDialog() {
		super(false, true);
		setText("Edit deck");
		VerticalPanel panel = new VerticalPanel();
		DateBox date = new DateBox();
		panel.add(date);
		panel.add(new TextBox());
		Button close = new Button("Close");
		close.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				EditEventDialog.this.hide();
			}
		});
		panel.add(new RadioButtonGroup("level", EventLevel.values()));
		panel.add(close);
		setWidget(panel);
	}
	public static class RadioButtonGroup extends VerticalPanel {
		public RadioButtonGroup(String name, Object[] values) {
			for (Object v : values) {
				add(new RadioButton(name, v.toString()));
			}
		}
	}
}
