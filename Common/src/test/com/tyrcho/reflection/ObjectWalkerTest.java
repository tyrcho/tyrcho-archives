package test.com.tyrcho.reflection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import test.com.tyrcho.reflection.ReflectionTest.Simple;

import com.tyrcho.Maps;
import com.tyrcho.reflection.ObjectWalker;
import com.tyrcho.reflection.Reflection;
import com.tyrcho.structure.func.Action;

public class ObjectWalkerTest {
	@Test
	public void walkObjectTest() {
		Child child = new Child();
		child.publicInt = 3;
		child.strings = Arrays.asList("a", "b");
		child.value = 1.0;
		child.map=Maps.asMap(Arrays.asList(1,2,3), Arrays.asList("10", "20", "30"));
		StoreAction storeAction = new StoreAction();
		new ObjectWalker(child).walk(storeAction);
		Assert.assertTrue(storeAction.values.contains(child));
		Assert.assertTrue(storeAction.values.contains(child.publicInt));
		Assert.assertTrue(storeAction.values.contains(child.strings));
		Assert.assertTrue(storeAction.values.contains(child.value));
		Assert.assertTrue(storeAction.values.containsAll(child.strings));
		Assert.assertTrue(storeAction.values.containsAll(child.map.values()));
		Assert.assertTrue(storeAction.values.containsAll(child.map.keySet()));
	}
	
	@Test
	public void walkObjectRecursiveTest() {
		Child child = new Child();
		child.value = 1.0;
		child.misc=Arrays.asList("a", child);
		StoreAction storeAction = new StoreAction();
		new ObjectWalker(child).walk(storeAction);
		Assert.assertTrue(storeAction.values.contains(child));
		Assert.assertTrue(storeAction.values.contains(child.value));
		Assert.assertTrue(storeAction.values.contains("a"));
	}

	class StoreAction implements Action<Object> {
		Collection<Object> values = new ArrayList<Object>();

		@Override
		public void act(Object value) {
			values.add(value);
		}
	}

	class Simple {
		private String privateString;
		public int publicInt;
	}

	class Child extends Simple {
		private double value;
		private Collection<String> strings;
		private Map<Integer, String> map;
		private Collection<Object> misc;
	}
}
