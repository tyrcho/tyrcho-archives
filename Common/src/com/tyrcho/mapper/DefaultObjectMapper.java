package com.tyrcho.mapper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Implémentation par défaut où l'object mapper renvoie un map contenant tous
 * les champs de l'objet indexés par leur nom.
 * 
 * @author daviot
 */
public class DefaultObjectMapper<T> extends ObjectMapper<T, String>
{
	public DefaultObjectMapper(Class<T> runtimeClass)
	{
		super(runtimeClass, buildFields(runtimeClass));
	}

	private static <T> Map<String, String> buildFields(Class<T> runtimeClass)
	{
		Map<String, String> map = new HashMap<String, String>();
		for (Field f : runtimeClass.getDeclaredFields())
		{
			map.put(f.getName(), f.getName());
		}
		return map;
	}
}
