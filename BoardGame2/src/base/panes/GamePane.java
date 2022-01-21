package base.panes;

import base.AcceptsInput;
import fxutils.Backgrounds;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public abstract class GamePane extends StackPane implements AcceptsInput {
	
	private final ScaledPane scaledPane;
	private final FXLayer fxLayer;
	
	/** An empty {@link FXLayer} will be used. */
	public GamePane(ScaledPane scaledPane) {
		this(scaledPane, new FXLayer());
	}
	
	public GamePane(ScaledPane scaledPane, FXLayer fxLayer) {
		this.scaledPane = scaledPane;
		this.fxLayer = fxLayer;
		fxLayer.setGamePane(this);
		fxLayer.addScaleTransform();
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
	
	/** WIDTH (x) scale binding. */
	public DoubleBinding wscaleBinding() {
		return imageLayer().wscaleBinding();
	}
	
}
