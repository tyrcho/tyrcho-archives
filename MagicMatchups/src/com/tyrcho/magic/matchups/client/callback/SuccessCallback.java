package com.tyrcho.magic.matchups.client.callback;

public interface SuccessCallback<T> {
	 /**
	   * Called when an asynchronous call completes successfully.
	   * 
	   * @param result the return value of the remote produced call
	   */
	  void onSuccess(T result);
}
