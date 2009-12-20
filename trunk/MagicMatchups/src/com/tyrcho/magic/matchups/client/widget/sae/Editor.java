package com.tyrcho.magic.matchups.client.widget.sae;

import com.google.gwt.user.client.ui.Widget;


public interface Editor<T> {

	T getValue();

	void setValue(T value);
	
	void setEnabled(boolean enabled);
	
	void createEmpty();
	
	Widget getWidget();
	
	void clear();

}
