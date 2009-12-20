package com.tyrcho.magic.matchups.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("service/deckNames")
public interface DeckNamesService extends RemoteService {
	void saveDecks(String[] names);
	
	String [] getDecks();
}
