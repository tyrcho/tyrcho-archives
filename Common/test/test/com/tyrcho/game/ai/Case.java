/**
 * 
 */
package test.com.tyrcho.game.ai;

enum Case {
	AX(Row.A, Col.X), AY(Row.A, Col.Y), AZ(Row.A, Col.Z), BX(Row.B, Col.X), BY(
			Row.B, Col.Y), BZ(Row.B, Col.Z), CX(Row.C, Col.X), CY(Row.C, Col.Y), CZ(
			Row.C, Col.Z), ;
	private final Row r;
	private final Col c;

	Case(Row r, Col c) {
		this.r = r;
		this.c = c;
	}

	public Row getR() {
		return r;
	}

	public Col getC() {
		return c;
	}
	
	@Override
	public String toString() {
		return String.format("%s,%s", r,c);
	}

	public static Case get(Row r, Col c) {
		for (Case cas : values()) {
			if (r == cas.r && c == cas.c) {
				return cas;
			}
		}
		throw new IllegalArgumentException(""+r+c);
	}
}