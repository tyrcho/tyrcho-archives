package com.tyrcho.magic.matchups.client.widget.sae;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Provides all services for searching, saving, creating elements.
 * 
 * @author mdaviot
 */
public interface AsyncSAEService<T> {
	void add(T data, AsyncCallback<Void> callback);
	void save(T data, AsyncCallback<Void> callback);
	void delete(T data, AsyncCallback<Void> callback);

	void selectAll(AsyncCallback<List<T>> callback);

}
