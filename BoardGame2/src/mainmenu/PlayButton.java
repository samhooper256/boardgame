package mainmenu;

import base.panes.ImagePane;
import fxutils.Images;
import game.MainScene;

public class PlayButton extends ImagePane {

	public PlayButton() {
		super(Images.PLAY_BUTTON);
		this.setOnMouseClicked(eh -> {
			MainScene.get().startGame(4);
		});
	}
	
}
