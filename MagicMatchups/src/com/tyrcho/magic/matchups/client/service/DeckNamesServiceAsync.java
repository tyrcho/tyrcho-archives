package com.tyrcho.magic.matchups.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface DeckNamesServiceAsync {
	void saveDecks(String[] names, AsyncCallback<Void> callback);

	void getDecks(AsyncCallback<String[]> callback);
}
