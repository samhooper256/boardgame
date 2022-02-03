package game.playerselect;

import base.panes.FXLayer;
import game.MainScene;

public class PlayerSelectFXLayer extends FXLayer {

	private static final double PLAYER_LABEL_Y = MainScene.DEFAULT_HEIGHT * .4;
	
	private final PlayerLabel playerLabel;
	
	public PlayerSelectFXLayer() {
		playerLabel = new PlayerLabel();
		playerLabel.layoutXProperty().bind(playerLabel.widthProperty().divide(-2).add(MainScene.CENTER_X));
		playerLabel.setLayoutY(PLAYER_LABEL_Y);
		getChildren().addAll(playerLabel);
	}
	
}
