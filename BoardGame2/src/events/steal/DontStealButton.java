package events.steal;

import base.panes.ImagePane;
import fxutils.*;
import game.board.Board;

public class DontStealButton extends ImagePane implements Fadeable {

	private final Fader fader;
	
	public DontStealButton(StealEvent stealEvent) {
		super(Images.DONT_STEAL);
		fader = new Fader(this).setDurations(Board.EVENT_FADE_DURATION);
		setOnMouseEntered(me -> setImage(Images.DONT_STEAL_HOVERED));
		setOnMouseExited(me -> setImage(Images.DONT_STEAL));
		setOnMouseClicked(me -> mouseClicked());
	}

	private void mouseClicked() {
		Board.get().requestEventFinish();
	}

	@Override
	public Fader fader() {
		return fader;
	}
	
}
