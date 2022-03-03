package events.injury;

import events.*;
import players.Player;
import players.passives.*;

public class InjuryEvent extends AbstractSimpleTextEvent {

	private static final String NAME = "Injury", DESCRIPTION = "Lose a turn.";
	
	public InjuryEvent() {
		super(EventTag.INJURY, NAME, DESCRIPTION);
	}
	
	@Override
	public void actOn(Player p) {
		p.acquirePassive(new Injury(p));
	}
	
}
