package base.panes;

import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import utils.Screen;

/** {@link FXLayer FXLayers} are {@link #isMouseTransparent() mouse transparent} by default. */
public class FXLayer extends Pane {

	private GamePane gamePane;
	
	/** This {@link FXLayer FXLayer's} {@link GamePane} must be {@link #setGamePane(GamePane) set} after this
	 * constructor is called. */
	public FXLayer() {
		this(null);
	}
	
	/** {@link #addScaleTransform()} must be called after this constructor is invoked <em>and</em> after the
	 * {@link GamePane} is {@link #setGamePane(GamePane) set}. */
	public FXLayer(GamePane gamePane) {
		this.gamePane = gamePane;
		this.setMouseTransparent(true);
		Scale s = new Scale(1, 1, 0, 0);
		s.xProperty().bind(Screen.wscaleBinding());
		s.yProperty().bind(Screen.hscaleBinding());
		getTransforms().add(s);
	}
	
	public void setGamePane(GamePane gamePane) {
		this.gamePane = gamePane;
	}
	
	public GamePane gamePane() {
		return gamePane;
	}
	
}
