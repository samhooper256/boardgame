package mainmenu;

import base.*;
import fxutils.Images;

public class PlayButton extends ImagePane {

	public PlayButton() {
		super(Images.PLAY_BUTTON);
		this.setOnMouseClicked(eh -> {
			MainScene.get().startGame();
		});
	}
	
}
