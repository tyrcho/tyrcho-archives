package test.com.tyrcho.drools.engine;

import static junit.framework.Assert.assertSame;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.tyrcho.drools.IQueryDrools;
import com.tyrcho.drools.conversion.IAnswerConverter;
import com.tyrcho.drools.conversion.IConversionFactory;
import com.tyrcho.drools.conversion.IQueryConverter;
import com.tyrcho.drools.engine.Engine;

public class QueryEngineTest {

	private IQueryDrools queryDrools;
	private IConversionFactory conversionFactory;
	private IAnswerConverter answerConverter;
	private IQueryConverter queryConverter;

	/**
	 * Vérifie l'enchainement et le nombre des appels aux convertisseurs et à
	 * Drools.
	 */
	@Test
	public void checkCallOrder() {
		Object query = "q";
		Object answer = "a";
		Collection<Object> inputFacts = Arrays.asList((Object) "i1", "i2");
		Collection<Object> outputFacts = Arrays.asList((Object) "o1", "o2");
		expect(queryConverter.convert(query)).andReturn(inputFacts);
		expect(answerConverter.convert(outputFacts)).andReturn(answer);
		expect(queryDrools.query(inputFacts)).andReturn(outputFacts);
		replay(queryConverter, conversionFactory, answerConverter, queryDrools);
		Object result = new Engine(queryDrools, conversionFactory)
				.executeRequest(query);
		assertSame(answer, result);
		verify(queryConverter, conversionFactory, answerConverter, queryDrools);
	}

	@Before
	public void setup() {
		queryDrools = createMock(IQueryDrools.class);
		conversionFactory = createMock(IConversionFactory.class);
		answerConverter = createMock(IAnswerConverter.class);
		queryConverter = createMock(IQueryConverter.class);
		expect(conversionFactory.buildAnswerConverter()).andReturn(
				answerConverter);
		expect(conversionFactory.buildQueryConverter()).andReturn(
				queryConverter);
	}
}
