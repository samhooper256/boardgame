package players.passives;

import players.Player;

public abstract class AbstractTemporaryPassive extends AbstractPassive implements TemporaryPassive {

	private int turnsRemaining;
	
	public AbstractTemporaryPassive(PassiveTag tag, Player player, int turnCount) {
		super(tag, player);
		turnsRemaining = turnCount;
	}
	
	@Override
	public int turnsRemaining() {
		return turnsRemaining;
	}

	@Override
	public void turnElapsed() {
		turnsRemaining--;
		if(turnsRemaining <= 0)
			wearOff();
	}
	
	@Override
	public void setTurnsRemaining(int turnsRemaining) {
		this.turnsRemaining = turnsRemaining;
	}

}
