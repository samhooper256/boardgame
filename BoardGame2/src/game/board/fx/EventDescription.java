package game.board.fx;

import fxutils.*;
import game.MainScene;
import game.board.Board;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

public class EventDescription extends Label implements Fadeable {

	private static final double EVENT_BACKGROUND_PADDING = 10;
	
	private final Fader fader;
	
	public EventDescription() {
		super("");
		fader = new Fader(this).setDurations(Board.EVENT_FADE_DURATION);
		setWrapText(true);
		setTextAlignment(TextAlignment.CENTER);
		setAlignment(Pos.CENTER);
		setFont(Fonts.UI_MED);
		setLayoutX(MainScene.CENTER_X - Images.EVENT_BACKGROUND.getWidth() * .5 + EVENT_BACKGROUND_PADDING);
		layoutYProperty().bind(heightProperty().multiply(-.5).add(MainScene.DEFAULT_HEIGHT * .51));
		double width = Images.EVENT_BACKGROUND.getWidth() - EVENT_BACKGROUND_PADDING * 2;
		setMinWidth(width);
		setMaxWidth(width);
	}

	@Override
	public Fader fader() {
		return fader;
	}
	
}
