package minigames.archery;

import base.panes.*;
import minigames.archery.fx.WinBox;

public class ArcheryFXLayer extends FXLayer {
	
	private final WinBox winBox;
	
	public ArcheryFXLayer() {
		super();
		winBox = new WinBox();
		winBox.layoutXProperty().bind(winBox.widthProperty().multiply(.5).negate().add(ScaledPane.DEFAULT_WIDTH * .5));
		winBox.layoutYProperty().bind(winBox.heightProperty().multiply(.5).negate().add(ScaledPane.DEFAULT_HEIGHT * .5));
	}

	public void showWinner(int player) {
		winBox.setWinner(player);
		getChildren().clear();
		getChildren().add(winBox);
	}
	
	@Override
	public ArcheryMinigame gamePane() {
		return (ArcheryMinigame) super.gamePane();
	}
	
}
