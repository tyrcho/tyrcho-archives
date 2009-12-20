package com.tyrcho.magic.matchups.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tyrcho.magic.matchups.client.model.LoginInfo;

public interface LoginServiceAsync {

	void login(String requestUri, AsyncCallback<LoginInfo> callback);

}
