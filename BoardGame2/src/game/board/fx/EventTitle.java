package game.board.fx;

import fxutils.*;
import game.MainScene;
import game.board.Board;
import javafx.scene.control.Label;

public class EventTitle extends Label implements Fadeable {

	private static final String CSS = "event-title";
	private static final double Y = MainScene.DEFAULT_HEIGHT * .35;
	
	private final Fader fader;
	
	public EventTitle() {
		this("");
	}
	
	public EventTitle(String text) {
		super(text);
		this.fader = new Fader(this).setDurations(Board.EVENT_FADE_DURATION);
		layoutXProperty().bind(widthProperty().multiply(-.5).add(MainScene.CENTER_X));
		setLayoutY(Y);
		getStyleClass().add(CSS);
	}

	@Override
	public Fader fader() {
		return fader;
	}
	
}
