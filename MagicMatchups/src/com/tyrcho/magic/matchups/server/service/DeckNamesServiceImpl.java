package com.tyrcho.magic.matchups.server.service;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;

import com.tyrcho.magic.matchups.client.service.DeckNamesService;
import com.tyrcho.magic.matchups.server.Deck;
import com.tyrcho.magic.matchups.server.PMF;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DeckNamesServiceImpl extends RemoteServiceServlet implements
		DeckNamesService {



	@Override
	public void saveDecks(String[] names) {
		PersistenceManager persistenceManager = PMF.get().getPersistenceManager();
		try{
			for (String name : names) {
				Deck deck = new Deck();
				deck.setName(name);
				persistenceManager.makePersistent(deck);
			}
		} finally {
			persistenceManager.close();
		}
		
	}

	@Override
	public String[] getDecks() {
		PersistenceManager persistenceManager = PMF.get().getPersistenceManager();
		try{
			List<Deck> decks = (List<Deck>) persistenceManager.newQuery(Deck.class).execute();
			List<String> deckNames = new ArrayList<String>();
			for (Deck deck : decks) {
				deckNames.add(deck.getName());
			}
			return deckNames.toArray(new String[0]);
		} finally {
			persistenceManager.close();
		}
	}
}
