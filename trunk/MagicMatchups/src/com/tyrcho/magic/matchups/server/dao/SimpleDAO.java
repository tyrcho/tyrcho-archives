package com.tyrcho.magic.matchups.server.dao;

import java.util.List;

import javax.jdo.PersistenceManager;

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

	public List<T> selectAll() {
		PersistenceManager persistenceManager = PMF.get().getPersistenceManager();
		try{
			return (List<T>) persistenceManager.newQuery(runtime).execute();
		} finally {
			persistenceManager.close();
		}
	}
}
