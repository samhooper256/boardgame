package game.fx;

import fxutils.Fonts;
import javafx.scene.control.Label;

public final class PausedText extends Label {

	public PausedText() {
		setText("Paused");
		setFont(Fonts.UI_XLARGE);
	}
	
}
