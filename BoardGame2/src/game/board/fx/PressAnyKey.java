package game.board.fx;

import fxutils.*;
import game.MainScene;
import game.board.Board;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

public class PressAnyKey extends Label implements Fadeable {

	private final Fader fader;
	
	public PressAnyKey() {
		super("(press any key to continue)");
		fader = new Fader(this).setDurations(Board.EVENT_FADE_DURATION);
		setAlignment(Pos.CENTER);
		setTextAlignment(TextAlignment.CENTER);
		setFont(Fonts.UI_SMALL);
		setLayoutY(MainScene.DEFAULT_HEIGHT * .63);
		layoutXProperty().bind(widthProperty().multiply(-.5).add(MainScene.CENTER_X));
		setVisible(false);
	}

	@Override
	public Fader fader() {
		return fader;
	}
	
	
}
