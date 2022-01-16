package base.panes;

import javafx.scene.layout.StackPane;

public class FXLayer extends StackPane {

	private GamePane gamePane;
	
	/** This {@link FXLayer FXLayer's} {@link GamePane} must be {@link #setGamePane(GamePane) set} after this
	 * constructor is called. */
	public FXLayer() {
		this(null);
	}
	
	public FXLayer(GamePane gamePane) {
		this.gamePane = gamePane;
		this.scaleXProperty().bind(gamePane().wscaleBinding());
		this.scaleYProperty().bind(gamePane().hscaleBinding());
	}
	
	public void setGamePane(GamePane gamePane) {
		this.gamePane = gamePane;
	}
	
	public GamePane gamePane() {
		return gamePane;
	}
	
}
