package mainmenu;

import base.*;
import fxutils.Images;

public class MainMenuScaledPane extends AbstractScaledPane {

	MainMenuScaledPane() {
		ImagePane mainImage = new ImagePane(Images.MAIN_MENU);
		add(mainImage);
		mainImage.setIdealCenter(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT / 4);
		PlayButton play = new PlayButton();
		play.setIdealCenter(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT / 2);
		add(play);
	}

	@Override
	public void updatePane(long diff) {
		
	}
	
}
