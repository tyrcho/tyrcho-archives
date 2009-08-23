package test.com.tyrcho.drools;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.com.tyrcho.drools.engine.QueryEngineTest;

@RunWith(Suite.class)
@SuiteClasses({QueryDroolsTest.class, QueryEngineTest.class})

public class DroolsTest {

}