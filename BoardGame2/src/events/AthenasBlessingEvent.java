package events;

import game.Board;
import players.*;

public class AthenasBlessingEvent implements Event {

	@Override
	public void play(Player p) {
		p.setRollType(RollType.CHOOSE);
		Board.get().eventFinished(this);
	}
	
}
