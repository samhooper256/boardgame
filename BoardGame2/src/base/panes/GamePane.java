package base.panes;

import base.AcceptsInput;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public abstract class GamePane extends StackPane implements AcceptsInput {
	
	private final ScaledPane scaledPane;
	private final FXLayer fxLayer;
	
	public GamePane(ScaledPane scaledPane) {
		this.scaledPane = scaledPane;
		fxLayer = new FXLayer(this);
		getChildren().addAll((Node) scaledPane, fxLayer);
	}
	
	public ScaledPane imageLayer() {
		return scaledPane;
	}
	
	public FXLayer fxLayer() {
		return fxLayer;
	}
	
	public DoubleBinding hscaleBinding() {
		return imageLayer().hscaleBinding();
	}
	
	public DoubleBinding wscaleBinding() {
		return imageLayer().wscaleBinding();
	}
	
}
