package game.pause;

import fxutils.Fonts;
import javafx.scene.control.Button;

public abstract class PauseButton extends Button {

	private static final String CSS = "pause-button";
	
	public PauseButton(String text) {
		super(text);
		getStyleClass().add(CSS);
		setFont(Fonts.UI_SMALL);
		setOnAction(eh -> action());
	}
	
	public abstract void action();
	
}
