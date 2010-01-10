package com.tyrcho.algo.lloyd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.csvreader.CsvReader;

public class Main {
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
		for (int count = 3; count < 10; count++) {

			for (int i = 0; i < 1000; i++) {
				Collection<Collection<Deck>> groups = clustering
						.computeGroups(count);
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
