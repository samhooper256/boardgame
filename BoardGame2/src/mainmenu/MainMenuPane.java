package mainmenu;

import base.*;
import fxutils.Images;

public class MainMenuPane extends AbstractScaledPane {

	private static final MainMenuPane INSTANCE = new MainMenuPane();
	
	public static MainMenuPane get() {
		return INSTANCE;
	}
	
	private MainMenuPane() {
		ImagePane mainImage = new ImagePane(Images.MAIN_MENU);
		add(mainImage);
		mainImage.setIdealCenter(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT / 4);
		PlayButton play = new PlayButton();
		play.setIdealCenter(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT / 2);
		add(play);
	}

}
