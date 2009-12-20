/**
 * 
 */
package com.tyrcho.magic.matchups.client.widget;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RadioButtonGroup<T> extends VerticalPanel implements HasValue<T>, HasSetEnabled {
	Map<RadioButton, T> buttons=new HashMap<RadioButton, T>();
	
	public RadioButtonGroup(String name, T[] values) {
		for (T v : values) {
			RadioButton button = new RadioButton(name, v.toString());
			buttons.put(button, v);
			add(button);
		}
	}
	
	public T getValue() {
		for (Entry<RadioButton, T> entry : buttons.entrySet()) {
			if(entry.getKey().getValue()) {
				return entry.getValue();
			}
		}
		return null;
	}

	@Override
	public void setValue(T value) {
		for (Entry<RadioButton, T> entry : buttons.entrySet()) {
			entry.getKey().setValue(entry.getValue().equals(value)); 
		}
	}

	@Override
	public void setValue(T value, boolean fireEvents) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<T> handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEnabled(boolean enabled) {
		for (RadioButton radioButton : buttons.keySet()) {
			radioButton.setEnabled(enabled);
		}
	}
}