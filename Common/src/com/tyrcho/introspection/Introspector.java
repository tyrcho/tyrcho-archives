package com.tyrcho.introspection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Allows to introspects an object and generate a representation of all its
 * fields recursively.
 * 
 * @author daviot
 * 
 */
public class Introspector
{
	private Map<Object,String> objects = new HashMap<Object, String>();

	private static void indent(String message, int deep, StringBuffer buffer)
	{
		for (int i = 0; i < deep; i++)
		{
			buffer.append("  ");
		}
		buffer.append(message);
	}

	private String collectionToString(Collection collection, int deep)
			throws IllegalArgumentException, IntrospectorException
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("\r");
		// indent(defaultToString(collection)+"\r", deep, buffer);
		indent("[\r", deep, buffer);
		for (Iterator i = collection.iterator(); i.hasNext();)
		{
			indent(objectToString(i.next(), deep + 1), deep, buffer);
			buffer.append("\r");
		}
		indent("]", deep, buffer);
		return buffer.toString();
	}

	private String arrayToString(Object[] array, int deep)
			throws IllegalArgumentException, IntrospectorException
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("\r");
		indent("[\r", deep, buffer);
		for (int i = 0; i < array.length; i++)
		{
			indent(objectToString(array[i], deep + 1), deep, buffer);
			buffer.append("\r");
		}
		indent("]", deep, buffer);
		return buffer.toString();
	}

	private String complexObjectToString(Object o, int deep)
			throws IllegalArgumentException, IllegalAccessException,
			IntrospectorException
	{
		List<Field> fields = new LinkedList<Field>();
		StringBuffer buffer = new StringBuffer();
		buffer.append("\r");
		indent(defaultToString(o) + "\r", deep, buffer);
		indent("(\r", deep, buffer);
		for (Class clazz = o.getClass(); clazz != null; clazz = clazz
				.getSuperclass())
		{
			fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
		}
		for (Iterator i = fields.iterator(); i.hasNext();)
		{
			Field field = (Field) i.next();
			if (Modifier.isStatic(field.getModifiers()))
			{
				continue;
			}
			field.setAccessible(true);
			String fieldName = field.getName();
			Object value = field.get(o);
			indent(fieldName + "=" + objectToString(value, deep + 1), deep + 1,
					buffer);
			buffer.append("\r");
		}
		indent(")\r", deep, buffer);
		return buffer.toString();
	}

	public String objectToString(Object o) throws IntrospectorException
	{
		return objectToString(o, 0);
	}

	private String objectToString(Object o, int deep)
			throws IntrospectorException
	{
		String representation = objects.get(o);
		if (representation != null)
		{
			if ("temp".equals(representation)) { return defaultToString(o); }
			return representation;
		}
		else if (o == null) { return "null"; }
		objects.put(o, "temp");
		try
		{
			if (o instanceof Collection)
			{
				representation = collectionToString((Collection) o, deep);
			}
			else if (o instanceof Map)
			{
				representation = mapToString((Map) o, deep);
			}
			else
			{
				Class c = o.getClass();
				if (c.isArray())
				{
					if (Object.class.isAssignableFrom(c.getComponentType()))
					{
						representation = arrayToString((Object[]) o, deep);
					}
				}
				else
				{
					try
					{
						c.getDeclaredMethod("toString", (Class[]) null);
						representation = o.toString();
					}
					catch (NoSuchMethodException e)
					{// toString undefined,
						representation = complexObjectToString(o, deep);
					}
				}
			}
			objects.put(o, representation);
			return representation;
		}
		catch (IllegalArgumentException e)
		{
			throw new IntrospectorException(e);
		}
		catch (SecurityException e)
		{
			throw new IntrospectorException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new IntrospectorException(e);
		}
	}

	private String mapToString(Map<?,?> map, int deep)
	throws IllegalArgumentException, IntrospectorException
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("\r");
		indent("[\r", deep, buffer);
		for (Entry entry:map.entrySet())
		{
			indent(objectToString(entry.getKey(), deep + 1), deep, buffer);
			buffer.append("=");
			indent(objectToString(entry.getValue(), deep + 1), deep, buffer);
			buffer.append("\r");
		}
		indent("]", deep, buffer);
		return buffer.toString();
	}

	private static String defaultToString(Object o)
	{
		return o.getClass().getName() + '@' + Integer.toHexString(o.hashCode());
	}
}
