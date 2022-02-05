package minigames.rewards;

import fxutils.*;
import game.MainScene;
import javafx.scene.control.Label;
import utils.Screen;

public class PressSpace extends Label implements Fadeable {

	private static final double Y = MainScene.DEFAULT_HEIGHT * .65;
	
	private final Fader fader;
	
	public PressSpace() {
		super("(press space)");
		fader = new Fader(this).setDurations(RewardsDisplay.FADE_TIME);
		setFont(Fonts.UI_SMALL);
		Screen.centerX(this).setLayoutY(Y);
	}

	@Override
	public Fader fader() {
		return fader;
	}
	
}
