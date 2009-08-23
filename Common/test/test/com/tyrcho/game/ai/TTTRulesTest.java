package test.com.tyrcho.game.ai;

import static junit.framework.Assert.assertEquals;

import java.util.Collection;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import com.tyrcho.CollectionUtils;
import com.tyrcho.drools.IQueryDrools;
import com.tyrcho.drools.QueryDroolsFactory;

public class TTTRulesTest {
	private IQueryDrools queryDrools;

	@Test
	public void checkInitialMoves() {
		Collection<Object> results = queryDrools.query(Collections
				.singleton(new Position()));
		assertEquals("nombre d'éléments renvoyés", 10, results.size());
		Collection<Move> moves = CollectionUtils.filter(results, Move.class);
		assertEquals(9, moves.size());
	}

	@Before
	public void setUp() throws Exception {
		queryDrools = QueryDroolsFactory.buildQueryDrools(getClass(),
				"moves.drl");
	}

}
