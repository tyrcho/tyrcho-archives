package test.com.tyrcho;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.com.tyrcho.drools.DroolsTest;
import test.com.tyrcho.introspection.IntrospectionTests;
import test.com.tyrcho.reflection.ReflectionTests;
import test.com.tyrcho.webservice.WebserviceTests;
import test.com.tyrcho.xml.XmlTests;

@RunWith(Suite.class)
@SuiteClasses( { DroolsTest.class, ReflectionTests.class, IntrospectionTests.class,
		WebserviceTests.class, XmlTests.class })
public class TyrchoTests {

}
