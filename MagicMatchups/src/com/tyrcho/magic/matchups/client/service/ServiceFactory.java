package com.tyrcho.magic.matchups.client.service;

import com.google.gwt.core.client.GWT;

public class ServiceFactory {
	 public static <T> T create(Class<?> classLiteral) {
		 return GWT.create(classLiteral);
	 }
}
