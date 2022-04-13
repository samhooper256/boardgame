package game.pause;

import game.MainScene;

public final class Resume extends PauseButton {

	public Resume() {
		super("Resume");
	}

	@Override
	public void action() {
		MainScene.get().pauseLayer().unpause();
	}
	
}
