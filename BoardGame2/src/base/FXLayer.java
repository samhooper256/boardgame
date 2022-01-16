package base;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class FXLayer extends StackPane {

	private final GamePane gamePane;
	
	public FXLayer(GamePane gamePane) {
		this.gamePane = gamePane;
		this.scaleXProperty().bind(gamePane().wscaleBinding());
		this.scaleYProperty().bind(gamePane().hscaleBinding());
	}
	
	public GamePane gamePane() {
		return gamePane;
	}
	
}
