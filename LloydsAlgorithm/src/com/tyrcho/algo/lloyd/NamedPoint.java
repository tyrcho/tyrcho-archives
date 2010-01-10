/**
 * 
 */
package com.tyrcho.algo.lloyd;

import java.util.Collection;

public interface NamedPoint {
	public Collection<? extends Number> getValues();
	public String getName();
}