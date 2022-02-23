package tiles;

import fxutils.Images;
import players.Player;

public final class StartTile extends Tile {

	private static final StartTile INSTANCE = new StartTile();

	public static StartTile get() {
		return INSTANCE;
	}
	
	private StartTile() {
		super(Images.START_TILE);
	}

	@Override
	public void land(Player p) {
		//nothing
	}

}
