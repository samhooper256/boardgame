package game.pause;

import fxutils.Fonts;
import game.MainScene;

public class ToMainMenu extends PauseButton {
	
	public ToMainMenu() {
		super("Back to Main Menu");
		setFont(Fonts.UI_SMALL);
	}

	@Override
	public void action() {
		MainScene.get().returnToMainMenu();
	}
	
}
