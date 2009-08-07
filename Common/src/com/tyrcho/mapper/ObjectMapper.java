package com.tyrcho.mapper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.tyrcho.Maps;
import com.tyrcho.reflection.Reflection;

/**
 * Effectue les conversions d'un objet vers et de un map en utilisant la
 * r�flection pour lire/ecrire les champs d�clar�s dans la classe ou dans une
 * des classes parentes. Les m�thodes {@link #toMap(T)} et {@link #fromMap(Map)}
 * peuvent �tre surcharg�es pour traiter les champs qui ne correspondent pas
 * directement � une cl� du Map.
 * 
 * @author daviot
 */
public class ObjectMapper<T, K> implements I_ObjectMapper<T, K>,
		I_ObjectBuilder<T, K>
{
	protected Map<K, Field> fields = new HashMap<K, Field>();
	protected final Class<T> runtimeClass;
	protected final ObjectMapper<? super T, K> parentMapper;

	/**
	 * @param runtimeClass
	 *            n�cessaire car les types g�n�riques ne sont pas accessibles
	 *            durant l'ex�cution
	 * @param fields
	 *            les noms des champs de la classe (index�s par les noms des
	 *            colonnes de la base)
	 * @throws RuntimeException
	 *             encapsule une {@link SecurityException}
	 * @throws IllegalArgumentException
	 *             si l'un des champs n'existe pas
	 * 
	 */
	public ObjectMapper(Class<T> runtimeClass, Map<K, String> fields)
	{
		this(runtimeClass, fields, null);
	}

	/**
	 * @param runtimeClass
	 *            n�cessaire car les types g�n�riques ne sont pas accessibles
	 *            durant l'ex�cution
	 * @param fields
	 *            les noms des champs de la classe (index�s par les noms des
	 *            colonnes de la base)
	 * @param parentMapper
	 *            un mappeur capable de traiter des objets parents, il sera
	 *            appel� pour construire les maps ou initialiser les objets
	 *            avant d'utiliser les champs propres aux objets fils
	 * @throws RuntimeException
	 *             encapsule une {@link SecurityException}
	 * @throws IllegalArgumentException
	 *             si l'un des champs n'existe pas
	 * 
	 */
	public ObjectMapper(Class<T> runtimeClass, Map<K, String> fields,
			ObjectMapper<? super T, K> parentMapper)
	{
		this.runtimeClass = runtimeClass;
		this.parentMapper = parentMapper;
		for (Entry<K, String> entry : fields.entrySet())
		{
			K column = entry.getKey();
			String fieldName = entry.getValue();
			try
			{
				Field field = Reflection.getField(fieldName, runtimeClass);
				field.setAccessible(true);
				this.fields.put(column, field);
			}
			catch (SecurityException e)
			{
				throw new RuntimeException(e);
			}
			catch (NoSuchFieldException e)
			{
				throw new IllegalArgumentException("Champ inconnu : "
						+ fieldName, e);
			}
		}
	}

	/**
	 * Constructeur avec des param�tres "simplifi�s".
	 * 
	 * @param runtimeClass
	 *            n�cessaire car les types g�n�riques ne sont pas accessibles
	 *            durant l'ex�cution
	 * @param columns
	 *            les colonnes de la table en base
	 * @param fieldNames
	 *            les noms des champs de la classe (en correspondance avec les
	 *            colonnes)
	 * @throws RuntimeException
	 *             encapsule une {@link SecurityException}
	 * @throws IllegalArgumentException
	 *             si l'un des champs n'existe pas ou si les tableaux n'ont pas
	 *             la meme taille
	 * 
	 */
	public ObjectMapper(Class<T> runtimeClass, K[] columns, String[] fieldNames)
	{
		this(runtimeClass, Maps.asMap(columns, fieldNames));
	}

	public ObjectMapper(Class<T> runtimeClass, K[] columns,
			String[] fieldNames, ObjectMapper<? super T, K> parentMapper)
	{
		this(runtimeClass, Maps.asMap(columns, fieldNames), parentMapper);
	}

	public Map<K, Object> toMap(T o)
	{
		Map<K, Object> map = new HashMap<K, Object>();
		if (parentMapper != null)
		{
			map.putAll(parentMapper.toMap(o));
		}
		for (K column : fields.keySet())
		{
			Field field = fields.get(column);
			Object value;
			try
			{
				value = field.get(o);
			}
			catch (IllegalArgumentException e)
			{
				throw new RuntimeException(e);
			}
			catch (IllegalAccessException e)
			{
				throw new RuntimeException(e);
			}
			map.put(column, value);
		}
		return map;
	}

	public T fromMap(Map<K, Object> map)
	{
		try
		{
			T object = buildInstance();
			if (parentMapper != null)
			{
				parentMapper.initializeObject(map, object);
			}
			initializeObject(map, object);
			return object;
		}
		catch (IllegalArgumentException e)
		{
			throw new RuntimeException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
		catch (InstantiationException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * Remplit l'objet avec les valeurs pr�sentes dans le Map.
	 */
	protected void initializeObject(Map<K, Object> map, T object)
			throws IllegalAccessException
	{
		for (Entry<K, Object> entry : map.entrySet())
		{
			K column = entry.getKey();
			Field field = fields.get(column);
			if (field != null)
			{
				Object value = entry.getValue();
				try
				{
					field.set(object, value);
				}
				catch (IllegalArgumentException e)
				{
					throw new IllegalArgumentException(String.format("%s n'est pas une valeur autoris�e pour le champ %s.%s", value, runtimeClass.getName(), field.getName()));
				}
			}
		}
	}

	/**
	 * Il est indispensable que la classe runtimeClass d�finisse un constructeur
	 * sans arguments pour permettre � la reflection d'instancier dynamiquement
	 * l'objet dans cette m�thode. Sinon on peut surcharger cette m�thode pour
	 * constuire l'objet.
	 */
	protected T buildInstance() throws InstantiationException,
			IllegalAccessException
	{
		return runtimeClass.newInstance();
	}
}
