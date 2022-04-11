package minigames.hurdles.imagelayer;

import fxutils.Images;
import game.MainScene;
import players.Player;

public final class Coords {

	private static final double SPRITE_SPACING = 20;
	private static final double ASSUMED_SPRITE_WIDTH = 80;
	
	private static final Coords INSTANCE = new Coords();
	
	public static Coords get() {
		return INSTANCE;
	}
	
	private final double[] xCenters;
	
	private Coords() {
		xCenters = new double[Player.maxCount()];
		for(int i = 0; i < xCenters.length; i++)
			xCenters[i] = ASSUMED_SPRITE_WIDTH * (i + 0.5) + SPRITE_SPACING * (i + 1);
	}

	public double xCenter(int player) {
		return xCenters[player - 1];
	}
	
	public double jumpBarY() {
		return MainScene.DEFAULT_HEIGHT - groundHeight() + 20;
	}
	
	public double jumpBarHeight() {
		return Images.JUMP_BAR.getHeight();
	}
	
	public double tickCenterY() {
		return jumpBarY() + jumpBarHeight() * .2;
	}
	
	/** the percent of the way the {@link JumpBarTick} is up from the bottom of the {@link JumpBar}. */
	public double tickPercent() {
		return 1 - (tickCenterY() - jumpBarY()) / jumpBarHeight();
	}
	
	public double groundHeight() {
		return Images.HURDLES_GROUND.getHeight();
	}
	public double groundY() {
		return MainScene.DEFAULT_HEIGHT - groundHeight();
	}
	
}
