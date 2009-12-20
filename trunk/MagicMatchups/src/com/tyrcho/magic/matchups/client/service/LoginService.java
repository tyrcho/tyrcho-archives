package com.tyrcho.magic.matchups.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.tyrcho.magic.matchups.client.model.LoginInfo;

@RemoteServiceRelativePath("service/login")
public interface LoginService extends RemoteService {
	public LoginInfo login(String requestUri);
}
