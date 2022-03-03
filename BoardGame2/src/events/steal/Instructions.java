package events.steal;

import fxutils.*;
import game.MainScene;
import game.board.Board;
import javafx.scene.control.Label;

public class Instructions extends Label implements Fadeable {

	private final Fader fader;
	
	public Instructions() {
		super("Choose a player to steal from - or don't");
		fader = new Fader(this).setDurations(Board.EVENT_FADE_DURATION);
		setFont(Fonts.UI_SMALL);
		layoutXProperty().bind(widthProperty().multiply(-.5).add(MainScene.CENTER_X));
		setLayoutY(StealEvent.INSTRUCTIONS_Y);
	}

	@Override
	public Fader fader() {
		return fader;
	}
}
