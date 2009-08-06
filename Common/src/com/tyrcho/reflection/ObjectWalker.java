package com.tyrcho.reflection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.tyrcho.structure.func.Action;

public class ObjectWalker {
	private final Object object;

	public ObjectWalker(Object object) {
		this.object = object;
	}

	public void walk(Action<Object> action, ObjectWalkerOptions... options) {
		walk(action, new ArrayList<Object>());
	}

	private void walk(Action<Object> action, Collection<Object> done) {
		action.act(object);
		done.add(object);
	}

	class BasicPolicy implements Iterator<Object> {
		private final Object parent;
		private final Collection<Object> done;

		BasicPolicy(Object parent, Collection<Object> done) {
			this.parent = parent;
			this.done = done;
		}

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Object next() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		

	}

}

