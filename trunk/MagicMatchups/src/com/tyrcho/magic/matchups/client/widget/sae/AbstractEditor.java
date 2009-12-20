package com.tyrcho.magic.matchups.client.widget.sae;

import com.google.gwt.user.client.ui.Widget;

/**
 * Stores data being edited (to keep the id).
 * 
 * @author mdaviot
 */
public abstract class AbstractEditor<T, W extends Widget> implements Editor<T> {
	private T data;
	private W widget;

	protected abstract T buildEmptyElement();

	protected abstract W buildWidget();

	@Override
	public void createEmpty() {
		setValue(buildEmptyElement());
	}

	public T getData() {
		return data;
	}

	public T getValue() {
		updateValue();
		return getData();
	}

	public W getWidget() {
		if (widget == null) {
			widget = buildWidget();
		}
		return widget;
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

}
