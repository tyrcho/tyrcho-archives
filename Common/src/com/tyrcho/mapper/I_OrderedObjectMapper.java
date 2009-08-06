package com.tyrcho.mapper;


/**
 * Object mapper permettand d'acc�der � la liste ordonn�e des cl�s.
 * 
 * @author t0031027
 */
public interface I_OrderedObjectMapper<T, K> extends I_ObjectBuilder<T, K>, I_ObjectMapper<T, K>
{
	public K[] keys();
}
