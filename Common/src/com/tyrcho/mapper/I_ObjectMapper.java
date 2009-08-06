package com.tyrcho.mapper;

import java.util.Map;

/**
 * Associe un Map à un objet pour faciliter l'accès à la persistance. Ce Map
 * contiend les champs qu'on souhaite rendre persistants.
 * 
 * @author daviot
 * 
 */
public interface I_ObjectMapper<T,K> 
{
	public Map<K, ?> toMap(T o);
}
