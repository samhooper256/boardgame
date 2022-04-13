package game.pause;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class ButtonBox extends VBox {

	private static final double SPACING = 8;
	
	public ButtonBox() {
		super(SPACING, new Resume(), new ToMainMenu(), new Quit());
		setAlignment(Pos.CENTER);
	}
	
}
