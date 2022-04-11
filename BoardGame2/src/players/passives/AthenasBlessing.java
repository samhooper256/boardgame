package players.passives;

import players.*;

public class AthenasBlessing extends AbstractTemporaryPassive {

	private static final int TURN_DURATION = 2;

	public AthenasBlessing(Player player) {
		super(PassiveTag.ATHENAS_BLESSING, player, TURN_DURATION);
	}

	@Override
	public void activate() {
		player().setRollType(RollType.CHOOSE);
	}

	@Override
	public void wearOff() {
		player().setRollType(RollType.RANDOM);
	}
	
	@Override
	public String toString() {
		return String.format("AthenasBlessing@%x[player=%d, turnsRemaining=%d]", hashCode(), player().number(), turnsRemaining());
	}
	
}
