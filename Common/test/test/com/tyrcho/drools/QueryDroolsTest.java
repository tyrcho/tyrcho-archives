package test.com.tyrcho.drools;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.Collection;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import com.tyrcho.drools.IQueryDrools;
import com.tyrcho.drools.QueryDroolsFactory;

public class QueryDroolsTest {

	private IQueryDrools queryDrools;

	@Test
	public void sayHello() {
		Collection<Object> results = queryDrools.query(Collections
				.singleton("toto"));
		assertEquals("nombre d'éléments renvoyés", 2, results.size());
		assertTrue("toto présent", results.contains("toto"));
		assertTrue("hello présent", results.contains("hello, world!"));
	}

	@Before
	public void setUp() throws Exception {
		queryDrools = QueryDroolsFactory.buildQueryDrools(getClass(),
				"simple.drl");
	}

}
