package base.panes;

import base.AcceptsInput;
import javafx.scene.Node;
import javafx.scene.layout.*;

public abstract class GamePane extends StackPane implements AcceptsInput {
	
	private final ImageLayer imageLayer;
	private final FXLayer fxLayer;
	
	/** An empty {@link FXLayer} will be used. */
	public GamePane(ImageLayer imageLayer) {
		this(imageLayer, new FXLayer());
	}
	
	public GamePane(ImageLayer imageLayer, FXLayer fxLayer) {
		this.imageLayer = imageLayer;
		this.fxLayer = fxLayer;
		fxLayer.setGamePane(this);
		getChildren().addAll((Node) imageLayer, fxLayer);
	}
	
	public ImageLayer imageLayer() {
		return imageLayer;
	}
	
	public FXLayer fxLayer() {
		return fxLayer;
	}
	
}
