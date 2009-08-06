package com.tyrcho.reflection;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tyrcho.structure.ITree;

public class ObjectTreeBuilder {
	private final Object object;

	public ObjectTreeBuilder(Object object) {
		this.object = object;
	}

	public ITree<Object> buildTree() {
		return new LazyTree(object);
	}

	class LazyTree implements ITree<Object> {
		private boolean initialized = false;
		private Map<String, ITree<Object>> subtrees = new HashMap<String, ITree<Object>>();
		private final Object value;

		LazyTree(Object value) {
			this.value = value;
		}
		private void initialize() throws IllegalArgumentException,
				IllegalAccessException {
			if (value != null) {
				List<Field> fields = Reflection.getFields(value.getClass());
				for (Field field : fields) {
					field.setAccessible(true);
					Object subValue = field.get(value);
					subtrees.put(field.getName(), new LazyTree(subValue));
				}
			}
		}

		@Override
		public Object getValue() {
			return value;
		}

		@Override
		public Map<String, ITree<Object>> getSubtrees() {
			if (!initialized) {
				try {
					initialize();
				} catch (IllegalArgumentException e) {
					throw new RuntimeException(e);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
			return subtrees;
		}
	}
}
