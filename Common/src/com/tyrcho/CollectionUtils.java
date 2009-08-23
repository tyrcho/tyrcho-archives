package com.tyrcho;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionUtils {
	public static <T, U extends T> Collection<U> filter(
			Collection<T> collection, Class<U> clazz) {
		Collection<U> result = new ArrayList<U>(collection.size());
		for (T t : collection) {
			if(clazz.isInstance(t)) {
				result.add((U)t);
			}
		}
		return result;
	}
}
