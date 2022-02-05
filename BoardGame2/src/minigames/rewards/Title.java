package minigames.rewards;

import fxutils.*;
import game.MainScene;
import javafx.scene.control.Label;
import utils.Screen;

final class Title extends Label implements Fadeable {

	private static final double Y = MainScene.DEFAULT_HEIGHT * .32;
	
	private final Fader fader;
	
	public Title() {
		super("Rewards!");
		fader = new Fader(this).setDurations(RewardsDisplay.FADE_TIME);
		setFont(Fonts.UI_MED);
		Screen.centerX(this).setLayoutY(Y);
	}

	@Override
	public Fader fader() {
		return fader;
	}
	
}
