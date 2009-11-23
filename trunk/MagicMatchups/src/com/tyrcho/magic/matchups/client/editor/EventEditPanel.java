package com.tyrcho.magic.matchups.client.editor;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.tyrcho.magic.matchups.client.model.Event;
import com.tyrcho.magic.matchups.client.model.EventLevel;
import com.tyrcho.magic.matchups.client.widget.GridEditors;
import com.tyrcho.magic.matchups.client.widget.RadioButtonGroup;
import com.tyrcho.magic.matchups.client.widget.sae.Editor;

public class EventEditPanel extends GridEditors implements Editor<Event> {
	private static final String LABEL_DATE = "Date";
	private static final String LABEL_NAME = "Name";
	private static final String LABEL_LEVEL = "Level";

	public EventEditPanel() {
		super(buildEditors());
	}

	private static Map<String, HasValue<?>> buildEditors() {
		LinkedHashMap<String, HasValue<?>> map = new LinkedHashMap<String, HasValue<?>>();
		map.put(LABEL_DATE,
				new DateBox(new DatePicker(), null, new DateBox.DefaultFormat(
						DateTimeFormat.getFormat("dd/MM/yy"))));
		map.put(LABEL_NAME, new TextBox());
		map.put(LABEL_LEVEL, new RadioButtonGroup<EventLevel>(LABEL_LEVEL,
				EventLevel.values()));
		return map;
	}

	@Override
	public Event getValue() {
		Event event = new Event();
		event.setDate((Date) getValue(LABEL_DATE));
		event.setLevel((EventLevel) getValue(LABEL_LEVEL));
		event.setName((String) getValue(LABEL_NAME));
		return event;
	}

	@Override
	public void setValue(Event value) {
		setValue(LABEL_DATE, value.getDate());
		setValue(LABEL_LEVEL, value.getLevel());
		setValue(LABEL_NAME, value.getName());
	}

	

}
