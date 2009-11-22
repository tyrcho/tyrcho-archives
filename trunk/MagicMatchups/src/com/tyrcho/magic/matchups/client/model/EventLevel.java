/**
 * 
 */
package com.tyrcho.magic.matchups.client.model;


public enum EventLevel {
	TRAINING(0, "Training"), FNM(8, "FNM"), DEFAULT_TOURNAMENT(16, "Tournament"), COMPETITIVE(
			32, "Competitive"), PROFESSIONAL(48, "Professional");

	public int getKValue() {
		return kValue;
	}

	public String getLabel() {
		return label;
	}

	private int kValue;
	private String label;

	private EventLevel(int kValue, String label) {
		this.kValue = kValue;
		this.label = label;
	}

	@Override
	public String toString() {
		return label;
	}

}
