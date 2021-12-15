package players;

public abstract class AbstractTemporaryPassive extends AbstractPassive implements TemporaryPassive {

	private int turnsRemaining;
	
	public AbstractTemporaryPassive(Player player, int turnCount) {
		super(player);
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

}
