package com.tyrcho.mapper;

import java.util.Map;

/**
 * Construit un objet à partir d'un map d'attribut.
 * 
 * @author daviot
 * 
 */
public interface I_ObjectBuilder<T,K> {
	public T fromMap(Map<K, Object> map);
}
