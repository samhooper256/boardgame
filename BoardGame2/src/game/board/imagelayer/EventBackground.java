package game.board.imagelayer;

import base.panes.ImagePane;
import fxutils.*;
import game.MainScene;
import game.board.Board;

public class EventBackground extends ImagePane implements Fadeable {

	private static final EventBackground INSTANCE = new EventBackground();
	
	public static EventBackground get() {
		return INSTANCE;
	}
	
	private final Fader fader;
	
	private EventBackground() {
		super(Images.EVENT_BACKGROUND);
		fader = new Fader(this).setDurations(Board.EVENT_FADE_DURATION)
				.setFadeOutFinishedAction(() -> Board.get().eventFinished());
		setIdealCenter(MainScene.CENTER_X, MainScene.CENTER_Y);
		setVisible(false);
	}

	@Override
	public Fader fader() {
		return fader;
	}
	
}