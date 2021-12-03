package mainmenu;

import base.*;
import fxutils.Images;

public class MainMenuPane extends AbstractScaledPane {

	private static final MainMenuPane INSTANCE = new MainMenuPane();
	
	public static MainMenuPane get() {
		return INSTANCE;
	}
	
	private MainMenuPane() {
		add(new ImagePane(Images.MAIN_MENU));
		ImagePane play = new ImagePane(Images.PLAY_BUTTON);
		play.setIdealCenter(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT / 2);
		add(play);
	}

}
