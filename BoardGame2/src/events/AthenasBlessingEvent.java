package events;

import game.board.Board;
import players.*;

public class AthenasBlessingEvent implements Event {

	@Override
	public void play(Player p) {
		p.acquirePassive(new AthenasBlessing(p));
		Board.get().eventFinished(this);
	}
	
}
