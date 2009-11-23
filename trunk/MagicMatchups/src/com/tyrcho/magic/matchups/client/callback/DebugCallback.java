package com.tyrcho.magic.matchups.client.callback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * A callback handler which alerts in case of errors and of success.
 * 
 * @author mdaviot
 * 
 */
public class DebugCallback<T> implements AsyncCallback<T> {

	private final String description;
	private final SuccessCallback<T> successCallback;

	public DebugCallback(String description, SuccessCallback<T> successCallback) {
		this.description = description;
		this.successCallback = successCallback;
	}

	@Override
	public void onFailure(Throwable caught) {
		String message = "Failed while " + description + " : "
				+ caught.getMessage();
		Window.alert(message);
		GWT.log(message, caught);
	}

	@Override
	public void onSuccess(T result) {
		successCallback.onSuccess(result);
	}

}
