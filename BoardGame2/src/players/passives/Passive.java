package players.passives;

import players.Player;

public interface Passive {

	Player player();
	
	/** Called when the {@link Passive#player() player} first acquires this {@link Passive}. Should only be called
	 * once.*/
	void activate();
	
	PassiveTag tag();
	
	default boolean isTemporary() {
		return this instanceof TemporaryPassive;
	}
	
}
