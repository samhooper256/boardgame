package minigames.hurdles.imagelayer;

import fxutils.Images;
import game.MainScene;
import players.Player;

public final class Coords {

	private static final double SPACING = 20;
	
	private static final Coords INSTANCE = new Coords();
	
	public static Coords get() {
		return INSTANCE;
	}
	
	private final double[] xCenters;
	
	private Coords() {
		xCenters = new double[Player.maxCount()];
		for(int i = 0; i < xCenters.length; i++)
			xCenters[i] = Images.SPRITE_WIDTH * (i + 0.5) + SPACING * (i + 1);
	}

	public double xCenter(int player) {
		return xCenters[player - 1];
	}
	
	public double jumpBarY() {
		return MainScene.DEFAULT_HEIGHT - HurdlesImageLayer.GROUND_HEIGHT + 20;
	}
	
	public double jumpBarHeight() {
		return Images.JUMP_BAR.getHeight();
	}
	
	public double tickCenterY() {
		return jumpBarY() + jumpBarHeight() * .2;
	}
	
	/** */
	public double tickPercent() {
		return (tickCenterY() - jumpBarY()) / jumpBarHeight();
	}
	
}
