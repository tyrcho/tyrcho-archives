package com.tyrcho.algo.lloyd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.csvreader.CsvReader;

public class Main {
	private static final int MAX_GROUPS_COUNT = 15;
	private static final int MIN_GROUPS_COUNT = 10;
	private static final int MAX_TRIES = 1000;
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
						Double
								.parseDouble(csvReader.get(i).replaceAll("%",
										"")));
			}
			System.out.println(d + " " + d.getValues());
			decks.add(d);
		}
		KMeansClustering<Deck> clustering = new KMeansClustering<Deck>(decks);
		double minDistance = Double.MAX_VALUE;
		Collection<Collection<Deck>> bestGroups = null;
		for (int count = MIN_GROUPS_COUNT; count < MAX_GROUPS_COUNT; count++) {

			for (int i = 0; i < MAX_TRIES; i++) {
				Collection<Collection<Deck>> groups = clustering
						.computeGroups(count, MAX_ITERATIONS);
				double maxDistance = clustering.computeMaxDistance(groups);
				if (maxDistance < minDistance) {
					minDistance = maxDistance;
					bestGroups = groups;
				}
			}
			System.out.println(bestGroups);
			System.out.println(minDistance);
		}
	}
}
