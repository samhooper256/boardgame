package events;

import players.Player;

public interface Event {
	
	EventTag tag();

	public void play(Player p);
	
}
