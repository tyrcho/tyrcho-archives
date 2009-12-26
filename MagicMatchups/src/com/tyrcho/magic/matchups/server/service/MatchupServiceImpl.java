package com.tyrcho.magic.matchups.server.service;

import com.tyrcho.magic.matchups.client.model.Matchup;
import com.tyrcho.magic.matchups.client.service.MatchupService;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class MatchupServiceImpl extends DaoServiceServlet<Matchup> implements
		MatchupService {

	public MatchupServiceImpl() {
		super(Matchup.class);
	}
}
