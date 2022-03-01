package events;

import players.Player;

public interface SimpleTextEvent extends Event {

	String description();
	
	@Override
	default void play(Player p) {
		actOn(p);
		Event.super.play(p);
	}
	
	void actOn(Player p);
	
}
