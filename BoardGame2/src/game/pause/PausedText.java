package game.pause;

import fxutils.*;
import javafx.scene.control.Label;

public final class PausedText extends Label {

	private static final String CSS = "paused-text";
	
	public PausedText() {
		setText("Paused");
		getStyleClass().add(CSS);
		setFont(Fonts.UI_XLARGE);
	}
	
}
