package com.tyrcho.magic.matchups.client.widget.sae;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

/**
 * Stores data being edited (to keep the id).
 * 
 * @author mdaviot
 */
public abstract class AbstractEditor<T,W extends Widget> implements Editor<T> {
	private T data;
	private W widget;
	private final Class<W> widgetClass;
	private final Class<T> elementClass;

	public AbstractEditor(Class<W> widgetClass, Class<T> elementClass) {
		this.widgetClass = widgetClass;
		this.elementClass = elementClass;
		widget=buildWidget();
	}
	
	public T getData() {
		return data;
	}

	protected W buildWidget() {
		return GWT.create(widgetClass);
	}

	public T getValue() {
		updateValue();
		return getData();
	}

	public void setData(T data) {
		this.data = data;
	}

	public void setValue(T data) {
		setData(data);
		updateFields();
	}

	/**
	 * Copies value to displayed fields.
	 */
	protected abstract void updateFields();

	/**
	 * Copies displayed field to value.
	 */
	protected abstract void updateValue();

	
	@Override
	public W getWidget() {
		return widget;
	}
	
	@Override
	public void createEmpty() {
		setValue((T) GWT.create(elementClass));
	}

}
