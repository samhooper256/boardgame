package base.panes;

import javafx.scene.layout.StackPane;

/** {@link FXLayer FXLayers} are {@link #isMouseTransparent() mouse transparent} by default. */
public class FXLayer extends StackPane {

	private GamePane gamePane;
	
	/** This {@link FXLayer FXLayer's} {@link GamePane} must be {@link #setGamePane(GamePane) set} after this
	 * constructor is called. */
	public FXLayer() {
		this(null);
	}
	
	/** {@link #init()} must be called after this constructor is invoked <em>and</em> after the {@link GamePane} is
	 * {@link #setGamePane(GamePane) set}. */
	public FXLayer(GamePane gamePane) {
		this.gamePane = gamePane;
		this.setMouseTransparent(true);
	}
	
	public void init() {
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
