package com.tyrcho.mapper;

import java.util.Arrays;
import java.util.List;

/**
 * Convertit un objet de et vers une map, indexée par des clés. Ces clés peuvent
 * être listées (en conservant l'ordre dans lequel elles ont été entrées) de
 * manière à pouvoir utiliser le map pour l'affichage dans une JTable.
 * 
 * @author t0031027
 */
public class OrderedObjectMapper<T,K> extends ObjectMapper<T, K> implements I_OrderedObjectMapper<T, K>
{

	private final K[] keys;

	public OrderedObjectMapper(Class<T> runtimeClass, K[] keys, String[] fieldNames)
	{
		super(runtimeClass, keys, fieldNames);
		this.keys = keys;
	}

	public K[] keys()
	{
		return keys;
	}

}
