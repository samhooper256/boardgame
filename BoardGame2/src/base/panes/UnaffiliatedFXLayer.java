package base.panes;

import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import utils.Screen;

public class UnaffiliatedFXLayer extends Pane {

	public UnaffiliatedFXLayer() {
		this.setMouseTransparent(true);
		Scale s = new Scale(1, 1, 0, 0);
		s.xProperty().bind(Screen.wscaleBinding());
		s.yProperty().bind(Screen.hscaleBinding());
		getTransforms().add(s);
	}
	
}
