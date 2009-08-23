package com.tyrcho.game.ai;

import java.util.Collection;

public class AlphaBeta<Position, Move> {
	private final Context<Position, Move> context;
	private final static int INFINITY = Integer.MAX_VALUE;

	public AlphaBeta(Context<Position, Move> context) {
		this.context = context;
	}

	public int compute(Position p, int depth) {
		return alphabeta(p, depth, INFINITY, -INFINITY);
	}

	private int alphabeta(Position p, int depth, int alpha, int beta) {
		Collection<Move> moves;
		int value, localalpha = alpha, bestvalue = -INFINITY;

		if (context.checkwin(p))
			return -INFINITY;

		if (depth == 0)
			return context.evaluation(p);

		moves = context.makeMoveList(p);
		if (moves.size() == 0)
			context.handleNoMove(p);

		for (Move move : moves) {

			context.doMove(p, move);
			value = -alphabeta(p, depth - 1, -beta, -localalpha);
			context.undoMove(p, move);
			bestvalue = Math.max(value, bestvalue);
			if (bestvalue >= beta)
				break;
			if (bestvalue > localalpha)
				localalpha = bestvalue;
		}
		return bestvalue;
	}
}
