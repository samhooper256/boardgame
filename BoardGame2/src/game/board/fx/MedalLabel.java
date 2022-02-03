package game.board.fx;

import fxutils.Fonts;
import javafx.scene.control.Label;

public class MedalLabel extends Label {

	public MedalLabel(int value) {
		super();
		setFont(Fonts.UI_MED);
		setValue(value);
	}

	public int value() {
		return Integer.parseInt(getText());
	}
	
	public void setValue(int value) {
		setText(String.valueOf(value));
	}
	
}
