package com.tyrcho.magic.matchups.server.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

public class SimpleDAO<T> {
	private final Class<T> runtime;

	public SimpleDAO(Class<T> runtime) {
		this.runtime = runtime;

	}

	public void makePersistant(T value) {
		PersistenceManager persistenceManager = PMF.get()
				.getPersistenceManager();
		try {
			persistenceManager.makePersistent(value);
		} finally {
			persistenceManager.close();
		}
	}

	public void update(T value) {
		PersistenceManager persistenceManager = PMF.get()
				.getPersistenceManager();
		try {
			Object id = getId(value);
			T persisted = (T) persistenceManager.getObjectById(runtime, id);
			copyFields(value, persisted);
		} finally {
			persistenceManager.close();
		}
	}

	private Object getId(T value) {
		for (Field f : runtime.getDeclaredFields()) {// TODO : lookup parent
			// classes
			if (f.getAnnotation(Persistent.class) != null
					&& f.getAnnotation(PrimaryKey.class) != null) {
				f.setAccessible(true);
				try {
					return f.get(value);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}

			}
		}
		throw new IllegalArgumentException("No primary key for "
				+ runtime.getName());
	}

	private void copyFields(T value, T persisted) {
		for (Field f : runtime.getDeclaredFields()) {// TODO : lookup parent
			// classes
			if (f.getAnnotation(Persistent.class) != null
					&& f.getAnnotation(PrimaryKey.class) == null) {
				f.setAccessible(true);
				try {
					f.set(persisted, f.get(value));
				} catch (IllegalArgumentException e) {
					throw new RuntimeException(e);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}

	}

	public List<T> selectAll() {
		PersistenceManager persistenceManager = PMF.get()
				.getPersistenceManager();
		try {
			List<T> list = (List<T>) persistenceManager.newQuery(runtime)
					.execute();
			return new ArrayList<T>(list);
		} finally {
			persistenceManager.close();
		}
	}

	public void delete(T value) {
		PersistenceManager persistenceManager = PMF.get()
				.getPersistenceManager();
		try {
			Object id = getId(value);
			T persisted = (T) persistenceManager.getObjectById(runtime, id);
			persistenceManager.deletePersistent(persisted);
		} finally {
			persistenceManager.close();
		}
	}
}
