package test.com.tyrcho.game.ai;

import java.util.Collection;
import java.util.LinkedList;

public class Context implements com.tyrcho.game.ai.Context<Position, Move> {

	@Override
	public boolean checkwin(Position p) {
		Player lastPlayed = p.getLastPlayed();
		for (Row r : Row.values()) {
			if (checkWin(r, p, lastPlayed)) {
				return true;
			}
		}
		for (Col c : Col.values()) {
			if (checkWin(c, p, lastPlayed)) {
				return true;
			}
		}
		if (p.get(Row.A, Col.X) == lastPlayed
				&& p.get(Row.B, Col.Y) == lastPlayed
				&& p.get(Row.C, Col.Z) == lastPlayed) {
			return true;
		}
		if (p.get(Row.A, Col.Z) == lastPlayed
				&& p.get(Row.B, Col.Y) == lastPlayed
				&& p.get(Row.C, Col.X) == lastPlayed) {
			return true;
		}
		return false;
	}

	private boolean checkWin(Row r, Position p, Player lastPlayed) {
		for (Col col : Col.values()) {
			if (p.get(r, col) != lastPlayed) {
				return false;
			}
		}
		return true;
	}

	private boolean checkWin(Col col, Position p, Player lastPlayed) {
		for (Row r : Row.values()) {
			if (p.get(r, col) != lastPlayed) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void doMove(Position p, Move m) {
		p.set(m.getC().getR(), m.getC().getC(), m.getP());

	}

	@Override
	public int evaluation(Position p) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void handleNoMove(Position p) {
		throw new RuntimeException("partie nulle");

	}

	@Override
	public Collection<Move> makeMoveList(Position p) {
		Collection<Move> moves=new LinkedList<Move>();
		Player toPlay = p.getLastPlayed().opposite();
		for (Row row : Row.values()) {
			for (Col col : Col.values()) {
				if(p.get(row, col)==null) {
					moves.add(new Move(Case.get(row, col), toPlay));
				}
			}
		}
		return moves;
	}

	@Override
	public void undoMove(Position p, Move m) {
		Player lastPlayed = p.getLastPlayed().opposite();
		Case lastMove = p.getLastCases().pop();
		p.set(lastMove.getR(), lastMove.getC(), null);
		p.setLastPlayed(lastPlayed);

	}

}
