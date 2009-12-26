package com.tyrcho.magic.matchups.server.service;

import com.tyrcho.magic.matchups.client.model.Deck;
import com.tyrcho.magic.matchups.client.service.DeckService;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DeckServiceImpl extends DaoServiceServlet<Deck> implements
		DeckService {

	public DeckServiceImpl() {
		super(Deck.class);
	}
}
