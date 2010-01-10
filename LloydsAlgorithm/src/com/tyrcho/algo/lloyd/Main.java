package com.tyrcho.algo.lloyd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.csvreader.CsvReader;

public class Main {
	private static final int MAX_GROUPS_COUNT = 15;
	private static final int MIN_GROUPS_COUNT = 10;
	private static final int MAX_TRIES = 10000;
	private static final int MAX_ITERATIONS = 50;

	static class Deck implements NamedPoint {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Collection<Double> getValues() {
			return values;
		}

		@Override
		public String toString() {
			return name;
		}

		private Collection<Double> values = new ArrayList<Double>();
	}

	public static void main(String[] args) throws IOException {
		CsvReader csvReader = new CsvReader(args[0], ';');
		csvReader.readHeaders();
		Collection<Deck> decks = new ArrayList<Deck>();
		while (csvReader.readRecord()) {
			Deck d = new Deck();
			d.setName(csvReader.get(0));
			for (int i = 1; i < csvReader.getColumnCount(); i++) {
				d.getValues().add(
						Double.parseDouble(csvReader.get(i).replaceAll("%", "")
								.replaceAll(",", ".")));
			}
			System.out.println(d + " " + d.getValues());
			decks.add(d);
		}
		KMeansClustering<Deck> clustering = new KMeansClustering<Deck>(decks);
		double minDistance = Double.MAX_VALUE;
		Collection<Collection<Deck>> bestGroups = null;
		for (int count = MIN_GROUPS_COUNT; count < MAX_GROUPS_COUNT; count++) {

			for (int i = 0; i < MAX_TRIES; i++) {
				Collection<Collection<Deck>> groups = clustering.computeGroups(
						count, MAX_ITERATIONS);
				double maxDistance = clustering.computeMaxDistance(groups);
				if (maxDistance < minDistance) {
					minDistance = maxDistance;
					bestGroups = groups;
				}
			}
			List<List<Deck>> sorted = new ArrayList<List<Deck>>();
			for (Collection<Deck> group : bestGroups) {
				List<Deck> copy = new ArrayList<Deck>(group);
				Collections.sort(copy, new Comparator<Deck>() {
					@Override
					public int compare(Deck o1, Deck o2) {
						return o1.getName().compareTo(o2.getName());
					}
				});
				sorted.add(copy);
			}
			Collections.sort(sorted, new DeckCollectionComparator());
			System.out.println(sorted);
			System.out.println(minDistance);
		}
	}

	static class DeckCollectionComparator implements
			Comparator<Collection<Deck>> {

		@Override
		public int compare(Collection<Deck> o1, Collection<Deck> o2) {
			int diff = o2.size() - o1.size();
			if (diff == 0) {
				Iterator<Deck> d2i = o2.iterator();
				for (Deck d1 : o1) {
					int compare = d1.getName().compareTo(d2i.next().getName());
					if (compare != 0) {
						return compare;
					}
				}
				return 0;
			} else {
				return diff;
			}
		}

	}
}
