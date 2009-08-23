package test.com.tyrcho.game.ai;

public class Move {
	public Case getC() {
		return c;
	}

	public Player getP() {
		return p;
	}

	private final Case c;
	private final Player p;

	public Move(Case c, Player p) {
		this.c = c;
		this.p = p;
	}
	
	@Override
	public String toString() {
		return String.format("%s : %s", p, c);
	}
}
