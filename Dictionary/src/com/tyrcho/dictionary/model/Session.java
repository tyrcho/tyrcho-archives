package com.tyrcho.dictionary.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Session {
	private int score;
	private int questionCount;
	private final SessionParameters parameters;
	private List<String> entries;

	public void resetQuestions() {
		Set<String> dictEntries = parameters.getDictionary().getEntries(
				parameters.isFirstLanguage());
		questionCount = Math.min(dictEntries.size(), parameters.getQuestionCount());
		entries = new ArrayList<String>(questionCount);
		List<String> random = new ArrayList<String>(dictEntries);
		Collections.shuffle(random);
		List<String> recent = new ArrayList<String>(dictEntries);
		Collections.sort(recent, buildRecentComparator());
		List<String> badRatio = new ArrayList<String>(dictEntries);
		Collections.sort(badRatio, buildBadRatioComparator());
		List<List<String>> possibleLists = Arrays.asList(random, recent,
				badRatio);
		Random rand = new Random();
		for (int i = 0; i < questionCount; i++) {
			int r = rand.nextInt(100);
			List<String> list;
			if (r < parameters.getRecentPercent()) {
				list = recent;
			} else if (r < parameters.getRecentPercent() + parameters.getBadPercent()) {
				list = badRatio;
			} else {
				list = random;
			}
			addEntry(entries, list);
		}

		score = 0;
		int entriesCount = entries.size();
		if (entriesCount <= questionCount) {
			this.questionCount = entriesCount;
		} else {
			Collections.reverse(entries);
			Iterator<String> i = entries.iterator();
			while (entries.size() > questionCount) {
				i.next();
				i.remove();
			}
			Collections.reverse(entries);
		}
	}

	private void addEntry(List<String> entries, List<String> list) {
		String s = list.get(0);
		while (entries.contains(s)) {
			list.remove(0);
			s = list.get(0);
		}
		entries.add(s);
	}

	public Comparator<String> buildRecentComparator() {
		return new Comparator<String>() {
			public int compare(String s1, String s2) {
				DictionaryEntry entry1 = parameters.getDictionary().getEntry(
						s1, parameters.isFirstLanguage());
				DictionaryEntry entry2 = parameters.getDictionary().getEntry(
						s2, parameters.isFirstLanguage());
				return entry1.getTotalAnswers() - entry2.getTotalAnswers();
			}
		};
	}

	public Comparator<String> buildBadRatioComparator() {
		return new Comparator<String>() {
			public int compare(String s1, String s2) {
				DictionaryEntry entry1 = parameters.getDictionary().getEntry(
						s1, parameters.isFirstLanguage());
				DictionaryEntry entry2 = parameters.getDictionary().getEntry(
						s2, parameters.isFirstLanguage());
				float ratio1 = entry1.getGoodAnswerProportion();
				float ratio2 = entry2.getGoodAnswerProportion();
				float ratioDifference = ratio1 - ratio2;
				return ratioDifference > 0 ? 1 : ratioDifference == 0 ? 0 : -1;
			}
		};
	}

	public Session(SessionParameters parameters) {
		this.parameters = parameters;
		resetQuestions();
	}

	public void switchLanguage() {
		Set<String> newEntries = new HashSet<String>();
		for (String word : entries) {
			newEntries.addAll(parameters.getDictionary().getEntry(word,
					parameters.isFirstLanguage()).getTranslations());
		}
		entries = new ArrayList<String>(newEntries);
		Collections.shuffle(entries);
		questionCount = entries.size();
		parameters.setFirstLanguage(!parameters.isFirstLanguage());
	}

	// Mise � jour du score de la session
	public void updateScore() {
		score = score + 1;
	}

	public void resetScore() {
		score = 0;
	}

	// R�cup�ration du score de la session
	public String getScore() {
		return score + "/" + questionCount;
	}

	// R�cup�ration de l'it�rateur de la session
	public Iterator<Question> iterator() {
		return new SessionIterator();
	}

	class SessionIterator implements Iterator<Question> {
		private Iterator<String> iterator;

		SessionIterator() {
			iterator = entries.iterator();
		}

		public boolean hasNext() {
			return iterator.hasNext();
		}

		public Question next() {
			String word = iterator.next();
			return new Question(word, parameters.getDictionary().getEntry(word,
					parameters.isFirstLanguage()), parameters.getIgnoredChars());
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

}
