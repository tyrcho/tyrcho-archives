package com.tyrcho.magic.matchups.client.widget;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * Displays vertically couples of labels and editor.
 * 
 * @author mdaviot
 */
public class GridEditors extends Grid {
	private final Map<String, HasValue<?>> editors;

	public GridEditors(Map<String, HasValue<?>> editors) {
		super(editors.entrySet().size(), 2);
		this.editors = editors;
		int i=0;
		for (Entry<String, HasValue<?>> entry : editors.entrySet()) {
			setWidget(i, 0, new Label(entry.getKey()));
			setWidget(i, 1, (Widget) entry.getValue());
			i++;
		}
	}
	
	public Map<String, ?> getValues() {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Entry<String, HasValue<?>> entry : editors.entrySet()) {
			map.put(entry.getKey(), entry.getValue().getValue());
		}
		return map;
	}
	
	public Object getValue(String key) {
		return editors.get(key).getValue();
	}
	
	public void setValue(String key, Object value) {
		((HasValue<Object>)editors.get(key)).setValue(value);
	}
}
