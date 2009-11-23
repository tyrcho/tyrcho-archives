package com.tyrcho.magic.matchups.client.widget.sae;


public interface Editor<T> {

	T getValue();

	void setValue(T value);
	
	void setEnabled(boolean enabled);

}
