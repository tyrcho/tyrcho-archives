package com.tyrcho.dictionary.model;

public class SessionParameters {
	private TwoWayDictionary dictionary;
	private boolean firstLanguage;
	private int questionCount;
	private String ignoredChars;
	private int randomPercent;
	private int badPercent;
	private int recentPercent;

	public SessionParameters(TwoWayDictionary dictionary,
			boolean firstLanguage, int questionCount, int randomPercent,
			int badPercent, int recentPercent) {
		this.randomPercent = randomPercent;
		this.badPercent = badPercent;
		this.recentPercent = recentPercent;
		setDictionary(dictionary);
		setFirstLanguage(firstLanguage);
		setQuestionCount(questionCount);
	}

	public int getBadPercent() {
		return badPercent;
	}

	public TwoWayDictionary getDictionary() {
		return dictionary;
	}

	public String getIgnoredChars() {
		return ignoredChars;
	}

	public int getQuestionCount() {
		return questionCount;
	}

	public int getRandomPercent() {
		return randomPercent;
	}

	public int getRecentPercent() {
		return recentPercent;
	}

	public boolean isFirstLanguage() {
		return firstLanguage;
	}

	public void setBadPercent(int badPercent) {
		this.badPercent = badPercent;
	}

	public void setDictionary(TwoWayDictionary dictionary) {
		this.dictionary = dictionary;
	}

	public void setFirstLanguage(boolean firstLanguage) {
		this.firstLanguage = firstLanguage;
	}

	public void setIgnoredChars(String ignoredChars) {
		this.ignoredChars = ignoredChars;
	}

	public void setQuestionCount(int questionCount) {
		this.questionCount = questionCount;
	}

	public void setRandomPercent(int randomPercent) {
		this.randomPercent = randomPercent;
	}

	public void setRecentPercent(int recentPercent) {
		this.recentPercent = recentPercent;
	}
}
