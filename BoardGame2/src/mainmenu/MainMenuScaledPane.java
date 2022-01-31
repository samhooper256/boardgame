package mainmenu;

import base.panes.*;
import fxutils.Images;

import static game.MainScene.DEFAULT_WIDTH;
import static game.MainScene.DEFAULT_HEIGHT;
public class MainMenuScaledPane extends AbstractImageLayer {

	private final PlayButton playButton;
	private final QuitButton quitButton;
	
	MainMenuScaledPane() {
		ImagePane mainImage = new ImagePane(Images.MAIN_MENU);
		mainImage.setIdealCenter(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT / 4);
		playButton = new PlayButton();
		playButton.setIdealCenter(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT * .55);
		quitButton = new QuitButton();
		quitButton.setIdealCenter(DEFAULT_WIDTH * .5, DEFAULT_HEIGHT * .7);
		ImagePane warrior = new ImagePane(Images.WARRIOR_SELECT);
		warrior.setIdealCenter(DEFAULT_WIDTH * .2, DEFAULT_HEIGHT * .3);
		addAll(mainImage, playButton, quitButton, warrior);
	}

	@Override
	public void updatePane(long diff) {
		
	}
	
	public PlayButton playButton() {
		return playButton;
	}
	
}
