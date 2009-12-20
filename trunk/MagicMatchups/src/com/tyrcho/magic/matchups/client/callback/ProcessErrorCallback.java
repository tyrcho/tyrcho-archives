package com.tyrcho.magic.matchups.client.callback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * A callback handler which only alerts in case of errors.
 * 
 * @author mdaviot
 * 
 */
public class ProcessErrorCallback<T> implements AsyncCallback<T> {

	private final SuccessCallback<? super T> successCallback;

	public ProcessErrorCallback(SuccessCallback<? super T> successCallback) {
		this.successCallback = successCallback;
	}

	@Override
	public void onFailure(Throwable caught) {
		String message = caught.getMessage();
		Window.alert(message);
		GWT.log(message, caught);
	}

	@Override
	public void onSuccess(T result) {
		successCallback.onSuccess(result);
	}

}
