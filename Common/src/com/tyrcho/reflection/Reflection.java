package com.tyrcho.reflection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * M�thodes utilitaires pour la r�flection.
 * 
 * @author daviot
 */
public final class Reflection
{
	private Reflection()
	{
	}

	/**
	 * Cherche dans les interfaces impl�ment�es par l'objet la premi�re qui
	 * h�rite du 2e parametre.
	 * 
	 * @throws IllegalArgumentException
	 *             si l'objet n'impl�mente pas l'interface
	 */
	@SuppressWarnings("unchecked")
	public static <I, IR extends I, C extends IR> Class<IR> computeInterface(
			C implementation, Class<I> interfaceClass)
	{
		for (Class<?> possibleInterface : implementation.getClass()
				.getInterfaces())
		{
			if (interfaceClass.isAssignableFrom(possibleInterface)) { return (Class<IR>) possibleInterface; }
		}
		throw new IllegalArgumentException("Aucune "
				+ interfaceClass.getClass().getName()
				+ " interface trouv�e pour "
				+ implementation.getClass().getName());
	}

	/**
	 * R�cup�re un champ d�clar� dans la classe ou un des ses anc�tres.
	 * 
	 * @see Class#getDeclaredField(java.lang.String)
	 */
	public static Field getField(String fieldName, Class clazz)
			throws SecurityException, NoSuchFieldException
	{
		try
		{
			return clazz.getDeclaredField(fieldName);
		}
		catch (NoSuchFieldException e)
		{
			Class superClass = clazz.getSuperclass();
			if (superClass == null)
			{
				throw e;
			}
			else
			{
				return getField(fieldName, superClass);
			}
		}
	}

	/**
	 * Liste tous les champs d�clar�s dans une classe et dans ses superclasses.
	 */
	public static List<Field> getFields(Class clazz)
	{
		Class superclass = clazz.getSuperclass();
		if (superclass == null)
		{
			return new ArrayList<Field>();
		}
		else
		{
			List<Field> fields = getFields(superclass);
			for (Field f : clazz.getDeclaredFields())
			{
				fields.add(f);
			}
			return fields;
		}
	}
}
