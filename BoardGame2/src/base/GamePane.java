package base;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public abstract class GamePane extends StackPane implements AcceptsInput {
	
	private final ScaledPane scaledPane;
	
	public GamePane(ScaledPane scaledPane) {
		this.scaledPane = scaledPane;
		getChildren().add((Node) scaledPane);
	}
	
	public ScaledPane imageLayer() {
		return scaledPane;
	}
	
}
