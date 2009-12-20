package com.tyrcho.magic.matchups.client.widget.sae;

import java.util.List;


/**
 * Provides all services for searching, saving, creating elements.
 * 
 * @author mdaviot
 */
public interface SAEService<T> {
	void add(T data);
	void save(T data);
	void delete(T data);
	List<T> selectAll();

}
