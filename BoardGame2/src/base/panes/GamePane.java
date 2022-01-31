package base.panes;

import base.AcceptsInput;
import javafx.scene.Node;
import javafx.scene.layout.*;

public abstract class GamePane extends StackPane implements AcceptsInput {
	
	private final ScaledImageLayer scaledPane;
	private final FXLayer fxLayer;
	
	/** An empty {@link FXLayer} will be used. */
	public GamePane(ScaledImageLayer scaledPane) {
		this(scaledPane, new FXLayer());
	}
	
	public GamePane(ScaledImageLayer scaledPane, FXLayer fxLayer) {
		this.scaledPane = scaledPane;
		this.fxLayer = fxLayer;
		fxLayer.setGamePane(this);
		getChildren().addAll((Node) scaledPane, fxLayer);
	}
	
	public ScaledImageLayer imageLayer() {
		return scaledPane;
	}
	
	public FXLayer fxLayer() {
		return fxLayer;
	}
	
}
