package com.tyrcho;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Maps {

	public static <K, V> Map<K, V> asMap(K[] keys, V[] values) {
		if (keys.length != values.length) {
			throw new IllegalArgumentException(
					"Le nombre de colonnes doit �tre �gal au nombre de champs");
		}
		Map<K, V> map = new HashMap<K, V>();
		for (int i = 0; i < keys.length; i++) {
			map.put(keys[i], values[i]);
		}
		return map;
	}

	/**
	 * Cr�e un map en prenant un objet sur deux comme cl�, et les autres objets
	 * comme valeurs.
	 */
	public static Map varargsAsMap(Object[] args) {
		int length = args.length;
		if (length % 2 != 0) {
			throw new IllegalArgumentException(
					"Impossible de construire un map � partir d'un varargs de taille "
							+ length);
		}
		Map map = new HashMap();
		for (int i = 0; i < length / 2; i++) {
			map.put(args[2 * i], args[2 * i + 1]);
		}
		return map;
	}

	public static <K, V> Map<K, V> asMap(List<K> keys, List<V> values) {
		if (keys.size() != values.size()) {
			throw new IllegalArgumentException(
					"Le nombre de cl�s doit �tre �gal au nombre de valeurs");
		}
		Map<K, V> map = new HashMap<K, V>();
		Iterator<K> i=keys.iterator(); Iterator<V> j=values.iterator();
		for (; i.hasNext();) {
			map.put(i.next(), j.next());
		}
		return map;
	}

}
