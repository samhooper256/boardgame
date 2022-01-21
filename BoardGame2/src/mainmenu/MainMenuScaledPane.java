package mainmenu;

import base.panes.*;
import fxutils.Images;

public class MainMenuScaledPane extends AbstractScaledPane {

	MainMenuScaledPane() {
		ImagePane mainImage = new ImagePane(Images.MAIN_MENU);
		mainImage.setIdealCenter(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT / 4);
		PlayButton play = new PlayButton();
		play.setIdealCenter(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT * .55);
		ImagePane warrior = new ImagePane(Images.WARRIOR_SELECT);
		warrior.setIdealCenter(DEFAULT_WIDTH * .2, DEFAULT_HEIGHT * .3);
		addAll(mainImage, play, warrior);
	}

	@Override
	public void updatePane(long diff) {
		
	}
	
}
