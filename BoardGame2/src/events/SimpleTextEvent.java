package events;

import game.board.Board;
import players.Player;

public interface SimpleTextEvent extends Event {

	String name();
	
	String description();
	
	@Override
	default void play(Player p) {
		actOn(p);
		Board.get().showSimpleTextEvent(this);
	}
	
	void actOn(Player p);
	
}
