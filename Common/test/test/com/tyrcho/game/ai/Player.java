/**
 * 
 */
package test.com.tyrcho.game.ai;

enum Player {
	X, O;
	
	public Player opposite() {
		return this==X ? O : X;
	}
}