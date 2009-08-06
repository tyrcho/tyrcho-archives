package com.tyrcho.introspection;

public class IntrospectorException extends RuntimeException {

	public IntrospectorException() {
		super();
	}

	public IntrospectorException(String message) {
		super(message);
	}

	public IntrospectorException(Throwable cause) {
		super(cause);
	}

	public IntrospectorException(String message, Throwable cause) {
		super(message, cause);
	}

}
