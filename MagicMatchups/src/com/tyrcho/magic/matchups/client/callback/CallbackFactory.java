package com.tyrcho.magic.matchups.client.callback;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Builds the default callback for the application
 * 
 * @author mdaviot
 * 
 */
public class CallbackFactory {
	public static  AsyncCallback<Void> buildDefault(final String description) {
		return buildDefault(description, new SuccessCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				Window.alert(description + " OK");
			}
		});
	}

	public static <T> AsyncCallback<T> buildDefault(String description,
			SuccessCallback<T> successCallback) {
		return new DebugCallback<T>(description, successCallback);
	}
}
