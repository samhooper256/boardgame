package players;

/** A {@link Passive} that only lasts for a given number of {@link #turnsRemaining() turns}. */
public interface TemporaryPassive extends Passive {
	
	int turnsRemaining();
	
	void turnElapsed();
	
	void wearOff();
	
}
