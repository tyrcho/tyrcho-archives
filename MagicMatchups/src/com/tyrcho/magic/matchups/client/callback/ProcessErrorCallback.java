package com.tyrcho.magic.matchups.client.callback;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * A callback handler which only alerts in case of errors.
 * 
 * @author mdaviot
 *
 */
public class ProcessErrorCallback implements AsyncCallback<Void>{

	@Override
	public void onFailure(Throwable caught) {
		Window.alert(caught.getMessage());
		caught.printStackTrace();
	}

	@Override
	public void onSuccess(Void result) {
		//do nothing
	}

}
