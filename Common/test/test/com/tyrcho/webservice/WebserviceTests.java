package test.com.tyrcho.webservice;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({WebserviceTesterTest.class, GenericWebserviceCallerTest.class})
public class WebserviceTests {

}
