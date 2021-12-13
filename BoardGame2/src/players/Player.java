package players;

import base.ImagePane;
import fxutils.Images;
import tiles.*;

public class Player extends ImagePane {

	public static final int MAX_PLAYER_COUNT = 4;
	
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
	private RollType rollType;
	
	private Player(int number) {
		super(Images.player(number));
		this.number = number;
		current = StartTile.get();
		rollType = RollType.RANDOM;
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
	
	public RollType rollType() {
		return rollType;
	}

	public void setRollType(RollType rollType) {
		this.rollType = rollType;
	}
	
	@Override
	public String toString() {
		return String.format("Player[%d]", number());
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj == this;
	}
	
	@Override
	public int hashCode() {
		return number();
	}
	
}
