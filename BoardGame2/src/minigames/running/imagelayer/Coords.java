package minigames.running.imagelayer;

import game.MainScene;
import minigames.running.Running;

/** The screen for the {@link Running} minigame is divided into zones based on the number of players. To get coordinate
 * information for a given number of players, use {@link Coords#p(int)}. There is a zone for each player; zones are
 * numbered starting at {@code 1}. */
final class Coords {

	private static final Coords P2 = new Coords(2), P3 = new Coords(3), P4 = new Coords(4);
	
	public static Coords p(int playerCount) {
		switch(playerCount) {
			case 2: return P2;
			case 3: return P3;
			case 4: return P4;
			default: throw new IllegalArgumentException(String.format("Player count: %d", playerCount));
		}
	}
	
	private final int playerCount;
	
	private Coords(int playerCount) {
		this.playerCount = playerCount;
	}
	
	public double top(int zone) {
		return MainScene.DEFAULT_HEIGHT * (zone - 1) / playerCount;
	}
	
	public double bottom(int zone) {
		if(zone == playerCount)
			return MainScene.DEFAULT_HEIGHT - 1;
		return top(zone + 1) - 1;
	}
	
	public double groundY(int zone) {
		return bottom(zone) - Ground.HEIGHT;
	}
	
	public double playerBottomY(int zone) {
		return groundY(zone); //don't subtract 1
	}
	
	public double zoneHeight() {
		return MainScene.DEFAULT_HEIGHT / playerCount;
	}
	
}
