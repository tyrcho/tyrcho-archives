package com.tyrcho.algo.lloyd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Calcule pour un ensemble de points des groupes minimisant la distance
 * relative entre les points de ces groupes.
 * 
 * @author tyrcho
 */
public class KMeansClustering<T extends NamedPoint> {
	private final Collection<T> points;
	private final Random random = new Random();

	public KMeansClustering(Collection<T> points) {
		this.points = points;
	}

	public Collection<Collection<T>> computeGroups(int groupsCount, int maxIterations) {
		if (groupsCount < points.size()) {
			Collection<Collection<T>> lastGroups = buildInitialGroups(groupsCount);
			Collection<Collection<T>> groups = lastGroups;
			int i=0;
			do {
				lastGroups = groups;
				groups = iterate(lastGroups);
			} while (!areSame(lastGroups, groups) && i++<maxIterations);
			return lastGroups;
		} else {
			throw new IllegalArgumentException(groupsCount + " > "
					+ points.size());
		}
	}

	private boolean areSame(Collection<Collection<T>> a,
			Collection<Collection<T>> b) {
		for (Iterator<Collection<T>> itA = a.iterator(), itB = b.iterator(); itA
				.hasNext();) {
			Collection<T> ca = itA.next();
			List<T> cb = new ArrayList<T>(itB.next());
			for (T p : ca) {
				if (cb.contains(p)) {
					cb.remove(p);
				} else {
					return false;
				}
			}
			if (!cb.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	// private Collection<Collection<T>> deepClone(Collection<Collection<T>>
	// groups) {
	// Collection<Collection<T>> clone = new ArrayList<Collection<T>>();
	// for (Collection<T> group : groups) {
	// Collection<T> copy = new ArrayList<T>(group);
	// clone.add(copy);
	// }
	// return clone;
	// }

	private Collection<Collection<T>> iterate(Collection<Collection<T>> groups) {
		Map<NamedPoint, Collection<T>> centrums = new LinkedHashMap<NamedPoint, Collection<T>>();
		for (Collection<T> group : groups) {
			centrums.put(Distance.computeCentroid(group), new ArrayList<T>());
		}
		for (Collection<T> group : groups) {
			for (T point : group) {
				NamedPoint centroid = Distance
						.closest(point, centrums.keySet());
				centrums.get(centroid).add(point);
			}
		}
		return centrums.values();
	}

	private Collection<Collection<T>> buildInitialGroups(int groupsCount) {
		List<T> remaining = new ArrayList<T>(points);
		Map<T, Collection<T>> groups = buildInitialCentrums(groupsCount,
				remaining);
		for (T point : remaining) {
			T centrum = Distance.closest(point, groups.keySet());
			groups.get(centrum).add(point);
		}
		return groups.values();
	}

	private Map<T, Collection<T>> buildInitialCentrums(int groupsCount,
			List<T> remaining) {
		Map<T, Collection<T>> groups = new HashMap<T, Collection<T>>();
		while (groupsCount > 0) {
			Collection<T> group = new ArrayList<T>();
			int i = random.nextInt(remaining.size());
			T point = remaining.get(i);
			group.add(point);
			remaining.remove(i);
			groups.put(point, group);
			groupsCount--;
		}
		return groups;
	}

	/**
	 * Distance max (sur les groupes) entre la distance max (au sein d'un
	 * groupe).
	 */
	public double computeMaxDistance(Collection<Collection<T>> groups) {
		double max = 0;
		for (Collection<T> group : groups) {
			if (group.size() >= 2) {
				List<T> copy = new ArrayList<T>(group);
				double maxDist = 0;
				for (int i = 0; i < copy.size() - 1; i++) {
					for (int j = i + 1; j < copy.size(); j++) {
						maxDist=Math.max(maxDist, Distance.computeDistance(copy.get(i), copy.get(j)));
					}
				}
				max=Math.max(maxDist, max);
			}
		}
		return max;
	}
}
