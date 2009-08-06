package com.tyrcho.mapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.tyrcho.reflection.Reflection;

public abstract class AbstractAnnotatedObjectMapper<T> extends ObjectMapper<T, String>
{
	private static final Map<String, String> EMPTY_MAP = Collections.emptyMap();
	private Class<? extends Annotation> annotationClass;

	public AbstractAnnotatedObjectMapper(Class<T> runtimeClass, Class<? extends Annotation> annotationClass)
	{
		this(runtimeClass, annotationClass, null);
	}
	
	public AbstractAnnotatedObjectMapper(Class<T> runtimeClass, Class<? extends Annotation> annotationClass, ObjectMapper<? super T, String> parentMapper)
	{
		super(runtimeClass, EMPTY_MAP, parentMapper);
		this.annotationClass = annotationClass;
		initFields();
	}

	private void initFields()
	{
		List<Field> allFields = Reflection.getFields(runtimeClass);
		for (Field f : allFields)
		{
			if (isAnnotated(f))
			{
				f.setAccessible(true);
				fields.put(getKey(f), f);
			}
		}
	}

	protected boolean isAnnotated(Field f)
	{
		return f.isAnnotationPresent(annotationClass);
	}

	/**
	 * A implémenter pour interroger l'annotation du champ.
	 * (nécessaire car on ne peut pas faire implémenter une interface à une annotation).
	 */
	protected abstract String getKey(Field f);
}
