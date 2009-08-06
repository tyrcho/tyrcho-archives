package com.tyrcho.aspect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public abstract class AbstractAspect<T> implements InvocationHandler {

	protected T delegate;
	protected Object result;
	protected Exception exception;
	protected Object[] args;
	protected Method method;
	protected final Class<T> interfaceObject;

	protected AbstractAspect(Class<T> interfaceObject) {
		this.interfaceObject = interfaceObject;
	}

	public T applyTo(T delegate) {
		this.delegate=delegate;
		return interfaceObject.cast(Proxy.newProxyInstance(delegate
				.getClass().getClassLoader(),
				new Class[] { interfaceObject }, this));
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		if (args != null) {
			this.args = new Object[args.length];
			System.arraycopy(args, 0, this.args, 0, args.length);
		}
		this.method = method;
		before();
		try {
			result = method.invoke(delegate, args);
		} catch (Exception e) {
			exception = e;
		}
		after();
		if (null == exception) {
			return result;
		} else {
			throw exception;
		}
	}

	protected abstract void after();

	protected abstract void before();

}