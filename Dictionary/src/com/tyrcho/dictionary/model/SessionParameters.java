package com.tyrcho.dictionary.model;

public class SessionParameters {
	private TwoWayDictionary dictionary;

	private boolean firstLanguage;

	private int questionCount;

	private boolean random;

	private String ignoredChars;

	public boolean isRandom() {
		return random;
	}

	public void setRandom(boolean random) {
		this.random = random;
	}

	public SessionParameters(TwoWayDictionary dictionary,
			boolean firstLanguage, int questionCount, boolean random) {
		setRandom(random);
		setDictionary(dictionary);
		setFirstLanguage(firstLanguage);
		setQuestionCount(questionCount);
	}

	public TwoWayDictionary getDictionary() {
		return dictionary;
	}

	public void setDictionary(TwoWayDictionary dictionary) {
		this.dictionary = dictionary;
	}

	public boolean isFirstLanguage() {
		return firstLanguage;
	}

	public void setFirstLanguage(boolean firstLanguage) {
		this.firstLanguage = firstLanguage;
	}

	public int getQuestionCount() {
		return questionCount;
	}

	public void setQuestionCount(int questionCount) {
		this.questionCount = questionCount;
	}

	public String getIgnoredChars() {
		return ignoredChars;
	}

	public void setIgnoredChars(String ignoredChars) {
		this.ignoredChars = ignoredChars;
	}
}
