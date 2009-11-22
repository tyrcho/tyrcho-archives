/**
 * 
 */
package com.tyrcho.magic.matchups.client.model;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class EventLevel {
	public int getKValue() {
		return kValue;
	}

	public String getLabel() {
		return label;
	}

	@PrimaryKey
	@Persistent
	private int kValue;
	@Persistent
	private String label;

	public EventLevel(int kValue, String label) {
		this.kValue = kValue;
		this.label = label;
	}
	
	@Override
	public String toString() {
		return label;
	}

	public static final EventLevel TRAINING = new EventLevel(0, "Training");
	public static final EventLevel FNM = new EventLevel(8, "FNM");
	public static final EventLevel DEFAULT_TOURNAMENT = new EventLevel(16,
			"Tournament");
	public static final EventLevel COMPETITIVE = new EventLevel(32,
			"Competitive");
	public static final EventLevel PROFESSIONAL = new EventLevel(48,
			"Professional");

	public static EventLevel[] values() {
		return new EventLevel[] { TRAINING, FNM, DEFAULT_TOURNAMENT,
				COMPETITIVE, PROFESSIONAL };
	}
}
