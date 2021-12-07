package players;

import base.ImagePane;
import fxutils.Images;
import tiles.*;

public class Player extends ImagePane {

	private static final Player[] PLAYERS = {
			new Player(1),
			new Player(2),
			new Player(3),
			new Player(4)
	};
	
	public static Player get(int n) {
		return PLAYERS[n - 1];
	}
	
	private final int number;
	
	private Tile current;
	
	private Player(int number) {
		super(Images.player(number));
		this.number = number;
		current = StartTile.get();
	}

	public Tile tile() {
		return current;
	}
	
	public void setTile(Tile newTile) {
		current = newTile;
	}
	
	public int number() {
		return number;
	}
	
	@Override
	public String toString() {
		return String.format("Player[%d]", number());
	}
	
}
