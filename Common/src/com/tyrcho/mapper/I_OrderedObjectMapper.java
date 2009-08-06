package com.tyrcho.mapper;


/**
 * Object mapper permettand d'accéder à la liste ordonnée des clés.
 * 
 * @author t0031027
 */
public interface I_OrderedObjectMapper<T, K> extends I_ObjectBuilder<T, K>, I_ObjectMapper<T, K>
{
	public K[] keys();
}
