package players.passives;

/** A {@link Passive} that only lasts for a given number of {@link #turnsRemaining() turns}. */
public interface TemporaryPassive extends Passive {
	
	int turnsRemaining();
	
	/** Called when a turn has elapsed. */
	void turnElapsed();
	
	void wearOff();
	
	void setTurnsRemaining(int turnsRemaining);
	
}
