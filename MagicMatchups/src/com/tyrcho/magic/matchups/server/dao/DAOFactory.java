package com.tyrcho.magic.matchups.server.dao;

public class DAOFactory {
	//TODO : add aspect with try/finally
	public static <T> SimpleDAO<T> buildDAO(Class<T> runtime) {
		return new SimpleDAO<T>(runtime);
	}
}
