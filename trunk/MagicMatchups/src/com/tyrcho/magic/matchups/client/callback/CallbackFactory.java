package com.tyrcho.magic.matchups.client.callback;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Builds the default callback for the application
 * 
 * @author mdaviot
 *
 */
public class CallbackFactory {
	public static AsyncCallback<Void> buildDefault(String description) {
		return new DebugCallback(description);
	}
}
