package com.tyrcho.magic.matchups.server.service;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tyrcho.magic.matchups.client.widget.sae.SAEService;
import com.tyrcho.magic.matchups.server.dao.DAOFactory;
import com.tyrcho.magic.matchups.server.dao.SimpleDAO;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DaoServiceServlet<T> extends RemoteServiceServlet implements
		SAEService<T> {

	private final SimpleDAO<T> dao;

	public DaoServiceServlet(Class<T> runtime) {
		dao = DAOFactory.buildDAO(runtime);
	}

	@Override
	public void add(T element) {
		dao.makePersistant(element);
	}

	@Override
	public List<T> selectAll() {
		return dao.selectAll();
	}

	@Override
	public void save(T element) {
		dao.update(element);

	}

	@Override
	public void delete(T element) {
		dao.delete(element);
	}
}
