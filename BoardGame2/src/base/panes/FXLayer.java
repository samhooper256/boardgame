package base.panes;

import javafx.scene.layout.StackPane;

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
