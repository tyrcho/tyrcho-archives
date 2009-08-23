/**
 * 
 */
package test.com.tyrcho.game.ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Map.Entry;

class Position {
	Map<Case, Player> values = new HashMap<Case, Player>();
	Player lastPlayed = Player.X;
	private Stack<Case> lastCases = new Stack<Case>();

	public Stack<Case> getLastCases() {
		return lastCases;
	}

	public Player getLastPlayed() {
		return lastPlayed;
	}

	public Map<Case, Player> getValues() {
		return values;
	}

	public Collection<Case> usedCases() {
		List<Case> used = new ArrayList<Case>();
		for (Entry<Case, Player> entry : values.entrySet()) {
			if(entry.getValue()!=null) {
				used.add(entry.getKey());
			}
		}
		return used;
	}

	public void setLastPlayed(Player lastPlayed) {
		this.lastPlayed = lastPlayed;
	}

	void set(Row r, Col c, Player p) {
		Case cas = Case.get(r, c);
		values.put(cas, p);
		lastPlayed = p;
		lastCases.add(cas);
	}

	Player get(Row r, Col c) {
		Case cas = Case.get(r, c);
		return values.get(cas);
	}
}