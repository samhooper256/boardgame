package events.athenasblessing;

import events.*;
import players.Player;
import players.passives.AthenasBlessing;

public class AthenasBlessingEvent extends AbstractSimpleTextEvent {

	private static final String NAME = "Athena's Blessing", DESCRIPTION = "Choose your next roll.";
	
	public AthenasBlessingEvent() {
		super(EventTag.ATHENAS_BLESSING, NAME, DESCRIPTION);
	}
	
	@Override
	public void actOn(Player p) {
		p.acquirePassive(new AthenasBlessing(p));
	}
	
}