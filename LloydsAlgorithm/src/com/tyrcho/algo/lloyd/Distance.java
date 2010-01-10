package com.tyrcho.algo.lloyd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Distance {
	private Distance() {
	}

	public static double computeDistance(NamedPoint a, NamedPoint b) {
		double d = 0;
		Iterator<? extends Number> bIt = b.getValues().iterator();
		for (Number number : a.getValues()) {
			d += Math.pow(number.doubleValue() - bIt.next().doubleValue(), 2);
		}
		return d;
	}

	/**
	 * Calcule un nouveau point dont les coordonnées sont les moyennes des coordonnées des points.
	 */
	public static <T extends NamedPoint> NamedPoint computeCentroid(Collection<T> points) {
		int size = points.iterator().next().getValues().size();
		List<Double> sumValues = new ArrayList<Double>(Collections.nCopies(
				size, 0.0));
		for (T point : points) {
			Collection<? extends Number> values = point.getValues();
			int i = 0;
			for (Number number : values) {
				sumValues.set(i, sumValues.get(i) + number.doubleValue());
				i++;
			}
		}
		final Collection<Double> moyenValues=new ArrayList<Double>();
		for (Double d : sumValues) {
			moyenValues.add(d/points.size());
		}
		return new NamedPoint() {
			@Override
			public Collection<Double> getValues() {
				return moyenValues;
			}
			
			@Override
			public String getName() {
				return "centroid";
			}
			
			@Override
			public String toString() {
				return moyenValues.toString();
			}
		};
	}

	public static <T extends NamedPoint> T closest(T origin,
			Collection<T> points) {
		Iterator<T> iterator = points.iterator();
		T closest = iterator.next();
		while (iterator.hasNext()) {
			T candidate = iterator.next();
			if (computeDistance(candidate, origin) < computeDistance(closest,
					origin)) {
				closest = candidate;
			}
		}
		return closest;
	}
}
