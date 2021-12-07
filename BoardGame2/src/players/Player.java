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
	
	private Tile current;
	
	private Player(int n) {
		super(Images.player(n));
		current = StartTile.get();
	}

	public Tile currentTile() {
		return current;
	}
	
}
