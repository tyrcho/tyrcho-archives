package test.com.tyrcho.introspection;

import java.util.Arrays;
import java.util.Collection;

import com.tyrcho.introspection.IntrospectPanel;

public class IntrospectPanelTest {
	static class Person {
		String name,firstname;
		int age;
		Address[] addresses={new Address(),new Address() };
		Collection<String> phones=Arrays.asList("1","2");
	}
	static class Address{
		String label, street;
		int number;
	}
	
	public static void main(String[] args) {
		new IntrospectPanel(new Person());
	}
}
