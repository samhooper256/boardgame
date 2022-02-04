package events;

import players.*;

public class AthenasBlessingEvent implements SimpleTextEvent {

	private static final String NAME = "Athena's Blessing", DESCRIPTION = "Choose your next roll.";
	
	@Override
	public void actOn(Player p) {
		p.acquirePassive(new AthenasBlessing(p));
	}

	@Override
	public String name() {
		return NAME;
	}

	@Override
	public String description() {
		return DESCRIPTION;
	}
	
}
