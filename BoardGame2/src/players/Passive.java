package players;

public interface Passive {

	Player player();
	
	/** Called when the {@link Passive#player() player} first acquires this {@link Passive}. Should only be called
	 * once.*/
	void activate();
	
}
