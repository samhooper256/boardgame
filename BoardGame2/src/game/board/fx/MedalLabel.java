package game.board.fx;

import fxutils.Fonts;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MedalLabel extends Label {

	public static final Font FONT = Fonts.UI_MED;
	public static final Color TEXT_FILL = Color.WHITE;
	
	public MedalLabel(int value) {
		setFont(FONT);
		setTextFill(TEXT_FILL);
		setValue(value);
	}

	public int value() {
		return Integer.parseInt(getText());
	}
	
	public void setValue(int value) {
		setText(String.valueOf(value));
	}
	
}
