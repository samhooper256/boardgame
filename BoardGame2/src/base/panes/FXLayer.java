package base.panes;

/** {@link FXLayer FXLayers} are {@link #isMouseTransparent() mouse transparent} by default. */
public class FXLayer extends UnaffiliatedFXLayer {

	private GamePane gamePane;
	
	/** This {@link FXLayer FXLayer's} {@link GamePane} must be {@link #setGamePane(GamePane) set} after this
	 * constructor is called. */
	public FXLayer() {
		this(null);
	}
	
	/** {@link #addScaleTransform()} must be called after this constructor is invoked <em>and</em> after the
	 * {@link GamePane} is {@link #setGamePane(GamePane) set}. */
	public FXLayer(GamePane gamePane) {
		super();
		this.gamePane = gamePane;
	}
	
	public void setGamePane(GamePane gamePane) {
		this.gamePane = gamePane;
	}
	
	public GamePane gamePane() {
		return gamePane;
	}
	
}
