package game.pause;

import game.MainScene;

public class ToMainMenu extends PauseButton {
	
	public ToMainMenu() {
		super("Back to Main Menu");
	}

	@Override
	public void action() {
		MainScene.get().returnToMainMenu();
	}
	
}
