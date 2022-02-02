package base.panes;

import javafx.scene.layout.Pane;
import utils.Screen;

public class UnaffiliatedFXLayer extends Pane {

	public UnaffiliatedFXLayer() {
		this.setMouseTransparent(true);
		getTransforms().add(Screen.getScale());
	}
	
}
