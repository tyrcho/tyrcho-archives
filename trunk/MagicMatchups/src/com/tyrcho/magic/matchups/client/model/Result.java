package com.tyrcho.magic.matchups.client.model;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Result implements Serializable {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;

	@Persistent
	private Event event;
	@Persistent
	private String player1;
	@Persistent
	private String player2;
	@Persistent
	private String deck1;
	@Persistent
	private String deck2;
	@Persistent
	private MatchResult matchResult;

	public String getDeck1() {
		return deck1;
	}

	public String getDeck2() {
		return deck2;
	}

	public Event getEvent() {
		return event;
	}

	public Key getId() {
		return id;
	}

	public MatchResult getMatchResult() {
		return matchResult;
	}

	public String getPlayer1() {
		return player1;
	}

	public String getPlayer2() {
		return player2;
	}

	public void setDeck1(String deck1) {
		this.deck1 = deck1;
	}

	public void setDeck2(String deck2) {
		this.deck2 = deck2;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public void setMatchResult(MatchResult matchResult) {
		this.matchResult = matchResult;
	}

	public void setPlayer1(String player1) {
		this.player1 = player1;
	}

	public void setPlayer2(String player2) {
		this.player2 = player2;
	}

}
