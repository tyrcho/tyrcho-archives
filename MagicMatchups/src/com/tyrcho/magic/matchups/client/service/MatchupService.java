package com.tyrcho.magic.matchups.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.tyrcho.magic.matchups.client.model.Matchup;
import com.tyrcho.magic.matchups.client.widget.sae.SAEService;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("service/event")
public interface MatchupService extends RemoteService, SAEService<Matchup> {
}
