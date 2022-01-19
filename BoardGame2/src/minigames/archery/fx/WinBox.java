package minigames.archery.fx;

import base.panes.ScaledPane;
import javafx.geometry.*;
import javafx.scene.layout.*;

public class WinBox extends StackPane {

	private static final double
			WIDTH = ScaledPane.DEFAULT_WIDTH * .5,
			HEIGHT = ScaledPane.DEFAULT_HEIGHT * .5,
			VBOX_SPACING = 8,
			VBOX_TOP_PADDING = 100;
	
	private final VBox vBox;
	private final WinText winText;
	private final WinSpaceToReturn wstr;
	
	public WinBox() {
		vBox = new VBox(VBOX_SPACING);
		vBox.setAlignment(Pos.CENTER);
		vBox.setPadding(new Insets(VBOX_TOP_PADDING, 0, 0, 0));
		winText = new WinText();
		wstr = new WinSpaceToReturn();
		vBox.getChildren().addAll(winText, wstr);
		setMaxWidth(WIDTH);
		setMaxHeight(HEIGHT);
		getChildren().add(vBox);
	}
	
	public void setWinner(int player) {
		winText.setWinner(player);
	}
	
}
