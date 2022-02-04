package game.pause;

import base.Main;

public class Quit extends PauseButton {

	public Quit() {
		super("Quit");
	}

	@Override
	public void action() {
		Main.quitGame();
	}
	
}
