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
public class MatchResult implements Serializable {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String id;

	@Persistent
	private boolean deck1WinsBeforeSide;
	@Persistent
	private int deck1WinsAfterSide;
	@Persistent
	private int deck2WinsAfterSide;

	public int getDeck1WinsAfterSide() {
		return deck1WinsAfterSide;
	}

	public int getDeck2WinsAfterSide() {
		return deck2WinsAfterSide;
	}

	public String getId() {
		return id;
	}

	public boolean isDeck1WinsBeforeSide() {
		return deck1WinsBeforeSide;
	}

	public void setDeck1WinsAfterSide(int deck1WinsAfterSide) {
		this.deck1WinsAfterSide = deck1WinsAfterSide;
	}

	public void setDeck1WinsBeforeSide(boolean deck1WinsBeforeSide) {
		this.deck1WinsBeforeSide = deck1WinsBeforeSide;
	}

	public void setDeck2WinsAfterSide(int deck2WinsAfterSide) {
		this.deck2WinsAfterSide = deck2WinsAfterSide;
	}

	public void setId(String id) {
		this.id = id;
	}
}
