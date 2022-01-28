package fxutils;

import base.Main;
import javafx.scene.image.*;

/**
 * Utility class for creating {@link javafx.scene.image.Image} objects from resource files and working with
 * {@link ImageView ImageViews}.
 * @author Sam Hooper
 *
 */
public final class Images {
	
	public static final Image
			EVENT_TILE = get("MedicalTile.png"),
			SAFE_TILE = get("SafeTile.png"),
			START_TILE = get("StarterTile.png"),
			DIE0 = get("DiceBase.png"),
			DIE1 = get("Dice1.png"),
			DIE2 = get("Dice2.png"),
			DIE3 = get("Dice3.png"),
			DIE4 = get("Dice4.png"),
			DIE5 = get("Dice5.png"),
			DIE6 = get("Dice6.png"),
			GOLD_MEDAL = get("Gold Medal.png"),
			SILVER_MEDAL = get("Silver Medal.png"),
			BRONZE_MEDAL = get("Bronze Medal.png"),
			WARRIOR_SELECT = get("Warrior Select.png"),
			WARRIOR_SPRITE1 = get("WarriorSprite1.png"),
			TREE = get("TreePlayer.png"),
			LYRE = get("LyrePlayer.png"),
			WARRIOR = get("SwordPlayer.png"),
			WINGS = get("WingsPlayer.png"),
			RING = get("PlayerRing.png"),
			ARCHERY = get("Archerytile.png"),
			MAIN_MENU = get("main_menu.png"),
			PLAY_BUTTON = get("PlayButton.png"),
			QUIT_BUTTON = get("quit_button.png"),
			MINIGAME_INSTRUCTIONS = get("minigame_instructions.png"),
			MINIGAME_WIN_BACKGROUND = get("minigame_win_background.png"),
			PRESS_SPACE = get("pressspace.png"),
			FENCE = get("fence.png"),
			FENCE2 = get("fence2.png"),
			ARROW = get("arrow.png"),
			TARGET = get("target.png"),
			ARCHERY_BACKGROUND = get("archerybackground.png"),
			BACKGROUND = get("BoardBackground.png");
	
	public static final double PLAYER_IDEAL_SIZE = TREE.getWidth();
	
	private Images() {}

	public static Image die(int face) {
		switch(face) {
			case 0: return DIE0;
			case 1: return DIE1;
			case 2: return DIE2;
			case 3: return DIE3;
			case 4: return DIE4;
			case 5: return DIE5;
			case 6: return DIE6;
		}
		throw new IllegalArgumentException(String.format("Invalid face: %d", face));
	}
	
	/** Assumes {@code n} between {@code 1} and {@code 4} (inclusive). */
	public static Image player(int n) {
		switch(n) {
			case 1: return TREE;
			case 2: return LYRE;
			case 3: return WARRIOR;
			case 4: return WINGS;
		}
		throw new IllegalArgumentException(String.format("Invalid player number: %d", n));
	}
	
	public static Image sprite1(int n) { //just returns the player image for players 1, 2, 4
		switch(n) {
			case 1: return TREE;
			case 2: return LYRE;
			case 3: return WARRIOR_SPRITE1;
			case 4: return WINGS;
		}
		throw new IllegalArgumentException(String.format("Invalid player number: %d", n));
	}
	
	/**
	 * Returns the image given by {@code filename} by invoking {@link Image#Image(java.io.InputStream)} with
	 * the appropriate {@link InputStream}. The file indicated by {@code filename} must be in the "resources"
	 * folder.
	 * @return the image given by {@code filename}
	 */
	public static Image get(String filename) {
		return new Image(Main.getResourceStream(filename));
	}
	
	/**
	 * Returns the image given by {@code filename} with the given properties. The file indicated by
	 * {@code filename} must be in the "resources" folder.
	 * See {@link Image#Image(String, double, double, boolean, boolean) for details on the arguments.
	 * @return the {@link Image} described by the given filename with the given properties.
	 */
	public static Image get(	String filename,
								double requestedWidth,
								double requestedHeight,
								boolean preserveRatio,
								boolean smooth) {
		return new Image(Main.getResourceStream(filename), requestedWidth, requestedHeight, preserveRatio, smooth);
	}
	
	/**
	 * Equivalent to {@code setFitSize(view, fitSize, fitSize)}.
	 * */
	public static void setFitSize(ImageView view, double fitSize) {
		setFitSize(view, fitSize, fitSize);
	}
	
	public static void setFitSize(ImageView view, double fitWidth, double fitHeight) {
		view.setFitWidth(fitWidth);
		view.setFitHeight(fitHeight);
	}
	
}