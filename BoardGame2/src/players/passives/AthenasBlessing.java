package players.passives;

import players.*;

public class AthenasBlessing extends AbstractTemporaryPassive {

	private static final int TURN_DURATION = 2;

	public AthenasBlessing(Player player) {
		super(PassiveTag.ATHENAS_BLESSING, player, TURN_DURATION);
	}

	@Override
	public void activate() {
		System.out.printf("%s activated%n", this);
		player().setRollType(RollType.CHOOSE);
	}

	@Override
	public void wearOff() {
		System.out.printf("%s wears off%n", this);
		player().setRollType(RollType.RANDOM);
	}
	
	@Override
	public String toString() {
		return String.format("AthenasBlessing@%x[player=%d, turnsRemaining=%d]", hashCode(), player().number(), turnsRemaining());
	}
	
}
