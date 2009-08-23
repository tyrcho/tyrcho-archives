package com.tyrcho.reflection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.tyrcho.structure.func.Action;

public class ObjectWalker {
	private final Object object;

	public ObjectWalker(Object object) {
		this.object = object;
	}

	public void walk(Action<Object> action, ObjectWalkerOptions... options) {
		walk(action, object, new ArrayList<Object>());
	}

	private void walk(Action<Object> action, Object current,
			Collection<Object> done) {
		if (!done.contains(current)) {
			action.act(current);
			done.add(current);
			if (!isSimpleObject(current)) {
				if (current instanceof Collection<?>) {
					Collection<?> collection = (Collection<?>) current;
					for (Object object : collection) {
						walk(action, object, done);
					}
				} else if (current instanceof Map<?, ?>) {
					walk(action, ((Map<?, ?>) current).keySet(), done);
					walk(action, ((Map<?, ?>) current).values(), done);

				} else {
					for (Object value : Reflection.getAllFieldValues(current)
							.values()) {
						walk(action, value, done);
					}
				}
			}
		}
	}

	private static boolean isSimpleObject(Object current) {
		return current == null || current instanceof Boolean
				|| current instanceof Character || current instanceof Number
				|| current instanceof String;

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
