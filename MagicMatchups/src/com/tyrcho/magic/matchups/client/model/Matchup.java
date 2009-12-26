package com.tyrcho.magic.matchups.client.model;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Matchup implements Serializable {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String id;
	@Persistent
	private Deck deck1;
	@Persistent
	private Deck deck2;
	@Persistent
	private int deck1WinsBeforeSide;
	@Persistent
	private int deck1WinsAfterSide;
	@Persistent
	private int deck2WinsBeforeSide;
	@Persistent
	private int deck2WinsAfterSide;

	public Deck getDeck1() {
		return deck1;
	}

	public int getDeck1WinsAfterSide() {
		return deck1WinsAfterSide;
	}

	public int getDeck1WinsBeforeSide() {
		return deck1WinsBeforeSide;
	}

	public Deck getDeck2() {
		return deck2;
	}

	public int getDeck2WinsAfterSide() {
		return deck2WinsAfterSide;
	}

	public int getDeck2WinsBeforeSide() {
		return deck2WinsBeforeSide;
	}

	public String getId() {
		return id;
	}

	public void setDeck1(Deck deck1) {
		this.deck1 = deck1;
	}

	public void setDeck1WinsAfterSide(int deck1WinsAfterSide) {
		this.deck1WinsAfterSide = deck1WinsAfterSide;
	}

	public void setDeck1WinsBeforeSide(int deck1WinsBeforeSide) {
		this.deck1WinsBeforeSide = deck1WinsBeforeSide;
	}

	public void setDeck2(Deck deck2) {
		this.deck2 = deck2;
	}

	public void setDeck2WinsAfterSide(int deck2WinsAfterSide) {
		this.deck2WinsAfterSide = deck2WinsAfterSide;
	}

	public void setDeck2WinsBeforeSide(int deck2WinsBeforeSide) {
		this.deck2WinsBeforeSide = deck2WinsBeforeSide;
	}

	public void setId(String id) {
		this.id = id;
	}
}
