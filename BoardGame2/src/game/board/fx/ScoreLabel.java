package game.board.fx;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ScoreLabel extends Label {

	public static final Font FONT = MedalLabel.FONT;
	public static final Color TEXT_FILL = MedalLabel.TEXT_FILL;
	
	public ScoreLabel() {
		setFont(FONT);
		setTextFill(TEXT_FILL);
		setValue(0);
	}
	
	public void setValue(int value) {
		setText(String.format("(%d)", value));
	}

}
