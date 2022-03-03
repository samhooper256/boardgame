package events;

import game.board.Board;
import players.Player;

public interface Event {
	
	String name();
	
	EventTag tag();
	
	default void play(Player player) {
		Board.get().showEvent(this);
	}
	
}
