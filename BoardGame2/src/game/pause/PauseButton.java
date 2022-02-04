package game.pause;

import javafx.scene.control.Button;

public abstract class PauseButton extends Button {

	private static final String CSS = "pause-button";
	
	public PauseButton(String text) {
		super(text);
		getStyleClass().add(CSS);
		setOnAction(eh -> action());
	}
	
	public abstract void action();
	
}
