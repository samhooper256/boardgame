package game.playerselect;

import fxutils.Fonts;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

public class PlayerLabel extends Label {

	public PlayerLabel() {
		super("How many players?");
		setFont(Fonts.UI_LARGE);
		setTextAlignment(TextAlignment.CENTER);
		setAlignment(Pos.CENTER);
	}
	
}
