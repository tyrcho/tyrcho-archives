package com.tyrcho.magic.matchups.server;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class SingleMatchup {
	@Persistent
	private String deckName1;
	@Persistent
	private String deckName2;
	@Persistent
	private Integer deck1WinsBeforeSide;
	@Persistent
	private Integer deck2WinsBeforeSide;
	@Persistent
	private Integer deck1WinsAfterSide;
	@Persistent
	private Integer deck2WinsAfterSide;
	@Persistent
	private Integer deck1WinsUnspecified;
	@Persistent
	private Integer deck2WinsUnspecified;
	
	public String getDeckName1() {
		return deckName1;
	}
	public void setDeckName1(String deckName1) {
		this.deckName1 = deckName1;
	}
	public String getDeckName2() {
		return deckName2;
	}
	public void setDeckName2(String deckName2) {
		this.deckName2 = deckName2;
	}
	public Integer getDeck1WinsBeforeSide() {
		return deck1WinsBeforeSide;
	}
	public void setDeck1WinsBeforeSide(Integer deck1WinsBeforeSide) {
		this.deck1WinsBeforeSide = deck1WinsBeforeSide;
	}
	public Integer getDeck2WinsBeforeSide() {
		return deck2WinsBeforeSide;
	}
	public void setDeck2WinsBeforeSide(Integer deck2WinsBeforeSide) {
		this.deck2WinsBeforeSide = deck2WinsBeforeSide;
	}
	public Integer getDeck1WinsAfterSide() {
		return deck1WinsAfterSide;
	}
	public void setDeck1WinsAfterSide(Integer deck1WinsAfterSide) {
		this.deck1WinsAfterSide = deck1WinsAfterSide;
	}
	public Integer getDeck2WinsAfterSide() {
		return deck2WinsAfterSide;
	}
	public void setDeck2WinsAfterSide(Integer deck2WinsAfterSide) {
		this.deck2WinsAfterSide = deck2WinsAfterSide;
	}
	public Integer getDeck1WinsUnspecified() {
		return deck1WinsUnspecified;
	}
	public void setDeck1WinsUnspecified(Integer deck1WinsUnspecified) {
		this.deck1WinsUnspecified = deck1WinsUnspecified;
	}
	public Integer getDeck2WinsUnspecified() {
		return deck2WinsUnspecified;
	}
	public void setDeck2WinsUnspecified(Integer deck2WinsUnspecified) {
		this.deck2WinsUnspecified = deck2WinsUnspecified;
	}
}
