package minigames;

import base.panes.ImagePane;
import fxutils.Images;
import game.MainScene;
import minigames.hurdles.imagelayer.Coords;
import players.*;

public class KeyIcon extends ImagePane implements PlayerNumbered {

	public static final double Y = MainScene.DEFAULT_HEIGHT - 64, WIDTH = Images.KEY_1.getWidth();
	
	private final int number;
	
	public KeyIcon(int number) {
		super(Images.singleKeyImage(number));
		this.number = number;
		setIdealCenterX(Coords.get().xCenter(number));
		setIdealY(Y);
	}

	@Override
	public int number() {
		return number;
	}
	
}
