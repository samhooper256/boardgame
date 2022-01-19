package minigames.archery;

import base.panes.*;
import minigames.archery.fx.WinBox;

public class ArcheryFXLayer extends FXLayer {
	
	private final WinBox winBox;
	
	public ArcheryFXLayer() {
		super();
		winBox = new WinBox();
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
