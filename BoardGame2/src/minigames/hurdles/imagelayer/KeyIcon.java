package minigames.hurdles.imagelayer;

import java.util.*;

import base.panes.ImagePane;
import fxutils.Images;
import game.MainScene;
import players.PlayerNumbered;

public final class KeyIcon extends ImagePane implements PlayerNumbered {

	public static final double Y = MainScene.DEFAULT_HEIGHT - 64;
	
	public static final List<KeyIcon> LIST = Collections.unmodifiableList(Arrays.asList(
			new KeyIcon(1), new KeyIcon(2), new KeyIcon(3), new KeyIcon(4)));
	
	private final int number;
	
	private KeyIcon(int number) {
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
