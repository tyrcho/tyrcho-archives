package com.tyrcho;

import java.util.HashMap;
import java.util.Map;

public class Maps
{

	public static <K, V> Map<K, V> arraysAsMap(K[] keys, V[] values)
	{
		if (keys.length != values.length) { throw new IllegalArgumentException(
				"Le nombre de colonnes doit être égal au nombre de champs"); }
		Map<K, V> map = new HashMap<K, V>();
		for (int i = 0; i < keys.length; i++)
		{
			map.put(keys[i], values[i]);
		}
		return map;
	}

	/**
	 * Crée un map en prenant un objet sur deux comme clé, et les autres
	 * objets comme valeurs.
	 */
	public static Map varargsAsMap(Object[] args)
	{
		int length = args.length;
		if (length % 2 !=0)
		{
			throw new IllegalArgumentException("Impossible de construire un map à partir d'un varargs de taille "+length);
		}
		Map map=new HashMap();
		for (int i=0; i<length/2; i++)
		{
			map.put(args[2*i], args[2*i+1]);
		}
		return map;
	}

}
