package tiles;

import events.*;
import fxutils.Images;
import players.Player;

public class EventTile extends Tile {

	private final Event event;
	
	public EventTile() {
		super(Images.EVENT_TILE);
		this.event = new AthenasBlessingEvent();
	}

	@Override
	public void land(Player p) {
		event.play(p);
	}
	
}
