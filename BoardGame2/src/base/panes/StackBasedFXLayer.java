package base.panes;

import javafx.scene.layout.StackPane;

public class StackBasedFXLayer extends UnaffiliatedFXLayer {

	private final StackPane base;
	
	public StackBasedFXLayer() {
		this.base = new StackPane();
		base.prefWidthProperty().bind(widthProperty());
		base.prefHeightProperty().bind(heightProperty());
		getChildren().add(base);
	}
	
	public StackPane base() {
		return base;
	}
	
}
