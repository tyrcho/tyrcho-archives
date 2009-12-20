package com.tyrcho.magic.matchups.server.service;

import com.tyrcho.magic.matchups.client.model.Result;
import com.tyrcho.magic.matchups.client.service.ResultService;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ResultServiceImpl extends DaoServiceServlet<Result> implements
		ResultService {

	public ResultServiceImpl() {
		super(Result.class);
	}
}
