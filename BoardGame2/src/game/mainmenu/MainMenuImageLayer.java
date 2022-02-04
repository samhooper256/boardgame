package game.mainmenu;

import static game.MainScene.CENTER_X;
import static game.MainScene.CENTER_Y;
import static game.MainScene.DEFAULT_HEIGHT;
import static game.MainScene.DEFAULT_WIDTH;

import base.panes.*;
import fxutils.Images;

public class MainMenuImageLayer extends AbstractImageLayer {

	private final PlayButton playButton;
	private final QuitButton quitButton;
	
	MainMenuImageLayer() {
		ImagePane mainImage = new ImagePane(Images.MAIN_MENU);
		mainImage.setIdealCenter(CENTER_X, CENTER_Y * .5);
		playButton = new PlayButton();
		playButton.setIdealCenter(CENTER_X, DEFAULT_HEIGHT * .55);
		quitButton = new QuitButton();
		quitButton.setIdealCenter(CENTER_X, DEFAULT_HEIGHT * .7);
		addAll(mainImage, playButton, quitButton);
	}

	@Override
	public void updatePane(long diff) {
		
	}
	
	public PlayButton playButton() {
		return playButton;
	}
	
}
