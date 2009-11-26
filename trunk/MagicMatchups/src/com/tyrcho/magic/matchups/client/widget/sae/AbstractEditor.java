package com.tyrcho.magic.matchups.client.widget.sae;

import com.google.gwt.user.client.ui.Widget;

/**
 * Stores data being edited (to keep the id).
 * 
 * @author mdaviot
 */
public abstract class AbstractEditor<T,W extends Widget> implements Editor<T> {
	private T data;
	private final Lazy<W> widgetBuilder;
	private final Lazy<T> emptyElementBuilder;
	private W widget;

	public AbstractEditor(Lazy<W> widgetBuilder, Lazy<T> emptyElementBuilder) {
		this.widgetBuilder = widgetBuilder;
		this.emptyElementBuilder = emptyElementBuilder;
	}
	
	public T getData() {
		return data;
	}

	public W getWidget() {
		if(widget==null) { widget=widgetBuilder.build();}
		return widget;
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
	public void createEmpty() {
		setValue(emptyElementBuilder.build());
	}

}
