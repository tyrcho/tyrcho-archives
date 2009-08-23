package com.tyrcho.game.ai;

import java.util.Collection;

public interface Context<Position, Move> {
	boolean checkwin(Position p);
	int evaluation(Position p);
	Collection<Move> makeMoveList(Position p);
	void handleNoMove(Position p);
	void doMove(Position p, Move m);
	void undoMove(Position p, Move m);
}
