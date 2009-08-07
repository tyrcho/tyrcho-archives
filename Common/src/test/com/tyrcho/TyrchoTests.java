package test.com.tyrcho;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.com.tyrcho.introspection.IntrospectionTests;
import test.com.tyrcho.reflection.ReflectionTests;

@RunWith(Suite.class)
@SuiteClasses({ReflectionTests.class, IntrospectionTests.class})
public class TyrchoTests {

}
