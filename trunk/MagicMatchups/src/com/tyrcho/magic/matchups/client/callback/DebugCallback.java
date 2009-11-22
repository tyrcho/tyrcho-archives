package com.tyrcho.magic.matchups.client.callback;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * A callback handler which alerts in case of errors and of success.
 * 
 * @author mdaviot
 *
 */
public class DebugCallback implements AsyncCallback<Void>{

	private final String description;

	public DebugCallback(String description) {
		this.description = description;
	}

	@Override
	public void onFailure(Throwable caught) {
		Window.alert("Failed while "+description +" : "+ caught.getMessage());
		caught.printStackTrace();
	}

	@Override
	public void onSuccess(Void result) {
		Window.alert(description+ " OK");
	}

}
