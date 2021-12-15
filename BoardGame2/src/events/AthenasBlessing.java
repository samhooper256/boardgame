package events;

import players.*;

public class AthenasBlessing implements Event {

	@Override
	public void play(Player p) {
		p.setRollType(RollType.CHOOSE);
	}
	
}
