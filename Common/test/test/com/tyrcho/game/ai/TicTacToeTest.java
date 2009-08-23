package test.com.tyrcho.game.ai;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.tyrcho.game.ai.AlphaBeta;

public class TicTacToeTest {
	private Context context;
	private AlphaBeta<Position, Move> alphaBeta;
	private Position initialPosition;

	@Before
	public void setup() {
		context = new Context();
		alphaBeta = new AlphaBeta<Position, Move>(context);
		initialPosition = new Position();
	}

	@Test
	public void testFirstMove() {
		int valueForStart = alphaBeta.compute(initialPosition, 9);
		Assert.assertEquals(Integer.MAX_VALUE, valueForStart);
	}

	@Test(expected=RuntimeException.class)
	public void testSecondMoveForDraw() {
		context.doMove(initialPosition, new Move(Case.get(Row.B, Col.Y),
				Player.O));
		alphaBeta.compute(initialPosition, 9);
	}
	@Test
	public void testThirdMoveForWin() {
		context.doMove(initialPosition, new Move(Case.get(Row.B, Col.Y),
				Player.O));
		context.doMove(initialPosition, new Move(Case.get(Row.A, Col.Y),
				Player.X));
		alphaBeta.compute(initialPosition, 9);
		int valueForStart = alphaBeta.compute(initialPosition, 9);
		Assert.assertEquals(Integer.MAX_VALUE, valueForStart);
	}
}
