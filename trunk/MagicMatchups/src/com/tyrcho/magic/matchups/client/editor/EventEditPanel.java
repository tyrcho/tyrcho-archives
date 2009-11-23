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
import com.tyrcho.magic.matchups.client.widget.sae.AbstractEditor;

public class EventEditPanel extends
		AbstractEditor<Event, EventEditPanel.EventEditPanelWidget> {
	public EventEditPanel() {
		super(EventEditPanelWidget.class, Event.class);
	}

	private static final String LABEL_DATE = "Date";
	private static final String LABEL_NAME = "Name";
	private static final String LABEL_LEVEL = "Level";

	public static class EventEditPanelWidget extends GridEditors {
		public EventEditPanelWidget() {
			super(buildEditors());
		}

		private static Map<String, HasValue<?>> buildEditors() {
			LinkedHashMap<String, HasValue<?>> map = new LinkedHashMap<String, HasValue<?>>();
			map.put(LABEL_DATE, new DateBox(new DatePicker(), null,
					new DateBox.DefaultFormat(DateTimeFormat
							.getFormat("dd/MM/yy"))));
			map.put(LABEL_NAME, new TextBox());
			map.put(LABEL_LEVEL, new RadioButtonGroup<EventLevel>(LABEL_LEVEL,
					EventLevel.values()));
			return map;
		}
	}

	@Override
	protected void updateFields() {
		getWidget().setValue(LABEL_DATE, getData().getDate());
		getWidget().setValue(LABEL_LEVEL, getData().getLevel());
		getWidget().setValue(LABEL_NAME, getData().getName());
	}

	@Override
	protected void updateValue() {
		getData().setDate((Date) getWidget().getValue(LABEL_DATE));
		getData().setLevel((EventLevel) getWidget().getValue(LABEL_LEVEL));
		getData().setName((String) getWidget().getValue(LABEL_NAME));

	}

	@Override
	public void setEnabled(boolean enabled) {
		getWidget().setEnabled(enabled);
	}

	@Override
	public void clear() {
		getWidget().clear();
		
	}


}
