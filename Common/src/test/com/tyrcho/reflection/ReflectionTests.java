package test.com.tyrcho.reflection;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ObjectTreeBuilderTest.class,ObjectWalkerTest.class, ReflectionTest.class})
public class ReflectionTests {

}
