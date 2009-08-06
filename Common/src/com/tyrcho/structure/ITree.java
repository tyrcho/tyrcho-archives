package com.tyrcho.structure;

import java.util.Map;

public interface ITree<T> {

	public abstract T getValue();

	public abstract Map<String,ITree<T>> getSubtrees();

}