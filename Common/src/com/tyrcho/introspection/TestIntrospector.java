package com.tyrcho.introspection;


public class TestIntrospector {
	static class A {
		B b;
	}
	static class B {
		C c;
	}
	static class C {
		A a;		
	}
	public static void main(String[] args) throws Exception{
		A a=new A();
		a.b=new B();
		a.b.c=new C();
		a.b.c.a=a;
		System.out.println(new Introspector().objectToString(a));
		System.out.println(new Introspector().objectToString(TestIntrospector.class.getClassLoader()));		
		System.out.println(new Introspector().objectToString(TestIntrospector.class.getDeclaredClasses()));
	}
}

