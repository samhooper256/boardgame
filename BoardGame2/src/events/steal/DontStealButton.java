package events.steal;

import javafx.scene.control.Button;

public class DontStealButton extends Button {

	private static final String CSS = "dont-steal-button";
	
	public DontStealButton() {
		super("Don't Steal");
		getStyleClass().add(CSS);
	}
	
}
