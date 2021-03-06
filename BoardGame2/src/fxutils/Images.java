package fxutils;

import base.Main;
import base.input.GameInput;
import javafx.scene.image.*;
import minigames.MiniTag;
import players.Player;

/**
 * Utility class for creating {@link javafx.scene.image.Image} objects from resource files and working with
 * {@link ImageView ImageViews}.
 * @author Sam Hooper
 */
public final class Images {
	/** The factor by which the image pixel dimensions of sprite images are multiplied. */
	public static final double SPRITE_SCALE_FACTOR = .8; 
	
	/** All sprites have the same height. The height in pixels of the image file is 150; this constant is
	 * {@link #SPRITE_SCALE_FACTOR} of that ({@value #SPRITE_HEIGHT}). */
	public static final double SPRITE_HEIGHT = 120;
	public static final int SPRITES = 4;
	
	//these are both 80% of the image file's dimensions.
	private static final double
		STANDARD_SPRITE_WIDTH = 80,
		EXTENDED_SPRITE_WIDTH = 160;
	
	public static final Image
		//main menu stuff
		MAIN_MENU = get("main_menu.png"),
		PLAY_BUTTON = get("PlayButton.png"),
		QUIT_BUTTON = get("QuitButton.png"),
		//player select stuff
		SELECT_2 = get("two.png"),
		SELECT_3 = get("three.png"),
		SELECT_4 = get("four.png"),
		COUNT_SELECT_HOVER = get("count_select_hover.png"),
		//avatars
		TREE_AVATAR = get("Tree Avatar.png"),
		LYRE_AVATAR = get("Lyre Avatar.png"),
		WARRIOR_AVATAR = get("Warrior Avatar.png"),
		WINGS_AVATAR = get("Wings Avatar.png"),
		AVATAR_ACTIVE_BORDER = get("avatar_active_border.png"),
		AVATAR_INACTIVE_BORDER = get("avatar_inactive_border.png"),
		//player icons
		TREE = get("TreePlayer.png"), // player 1 - nymph/tree
		LYRE = get("LyrePlayer.png"), //player 2
		WARRIOR = get("WarriorPlayer.png"), //player 3 - warrior
		WINGS = get("WingsPlayer.png"), //player 4 - icarus/wings
		//die
		DIE0 = get("DiceBase.png"),
		DIE1 = get("Dice1.png"),
		DIE2 = get("Dice2.png"),
		DIE3 = get("Dice3.png"),
		DIE4 = get("Dice4.png"),
		DIE5 = get("Dice5.png"),
		DIE6 = get("Dice6.png"),
		DIE_GLOW = get("DieGlow.png"),
		//keys
		KEY_M = get("M.png"),
		KEY_X = get("X.png"),
		KEY_1 = get("1.png"),
		KEY_EQUALS = get("=.png"),
		//tiles
		EVENT_TILE = get("MedicalTile.png"),
		SAFE_TILE = get("SafeTile.png"),
		START_TILE = get("StarterTile.png"),
		ARCHERY_TILE = get("ArcheryTile.png"),
		HURDLES_TILE = get("HurdlesTile.png"),
		//medals
		GOLD_MEDAL = get("Gold Medal.png"),
		SILVER_MEDAL = get("Silver Medal.png"),
		BRONZE_MEDAL = get("Bronze Medal.png"),
		//board stuff
		RING = get("PlayerRing.png"),
		BOARD_BACKGROUND = get("BoardBackground.png"),
		//events
		EVENT_BACKGROUND = get("event_background.png"),
		STEAL_ICON_BACKGROUND = get("steal_icon_background.png"),
		STEAL_ICON_BACKGROUND_HIGHLIGHTED = get("steal_icon_background_highlighted.png"),
		DONT_STEAL = get("don't_steal.png"),
		DONT_STEAL_HOVERED = get("don't_steal_hovered.png"),
		//general minigame stuff
		PRESS_SPACE = get("PressSpace.png"),
		MEDAL_POINTS = get("MedalPoints.png"),
		//archery
		ARCHERY_INSTRUCTIONS = get("ArcheryInstructions.png"),
		REWARDS_BACKGROUND = get("rewards_background.png"),
		FENCE = get("fence.png"),
		ARROW = get("arrow.png"),
		TARGET = get("Target.png"),
		ARCHERY_BACKGROUND = get("archery_background.png"),
		//hurdles
		HURDLES_BACKGROUND = get("work in progress i guess.png"),
		HURDLES_INSTRUCTIONS = get("hurdles_instructions.png"),
		HURDLES_GROUND = get("hurdles_ground.png"),
		JUMP_BAR = get("jump_bar.png"),
		JUMP_BAR_BACKGROUND = get("jump_bar_background.png"),
		JUMP_BAR_TICK = get("jump_bar_tick.png"),
		HURDLE_BASE = get("hurdle base.png"),
		HURDLE_HEAD = get("hurdle head.png"),
		HURDLE_LEGS = get("hurdle legs.png"),
		//running
		RUNNING_TILE = get("Running_tile.png"),
		RUNNING_INSTRUCTIONS = get("RunningInstructions.png"),
		RUNNING_SKY = get("running sky.png"),
		RUNNING_GROUND_1 = get("running ground.png"),
		RUNNING_GROUND_2 = get("running ground.png"),
		OBSTACLE_1 = get("Obstacle1.png"),
		OBSTACLE_2 = get("Obstacle2.png"),
		OBSTACLE_3 = get("Obstacle3.png"),
		//misc
		HELPER = get("Helper.png"),
		//win
		WIN_BACKGROUND = get("WinBackground.png"),
		PODIUM_1 = get("1stPodium.png"),
		PODIUM_2 = get("2ndPodium.png"),
		PODIUM_3 = get("3rdPodium.png");
			
	private static final double[] SPRITE_WIDTHS = {
		0,
		STANDARD_SPRITE_WIDTH,
		EXTENDED_SPRITE_WIDTH,
		STANDARD_SPRITE_WIDTH,
		STANDARD_SPRITE_WIDTH
	};
	
	public static final double MAX_SPRITE_WIDTH;
	
	static {
		double max = 0;
		for(int p = 1; p <= Player.maxCount(); p++)
			max = Math.max(max, spriteWidth(p));
		MAX_SPRITE_WIDTH = max;
	}
	
	/** row is the player, index is the sprite for that player. */
	private static final Image[][] MINIGAME_SPRITES = {
		null,
		{
			getSprite(1, "NymphSprite0.png"),
			getSprite(1, "NymphSprite1.png"),
			getSprite(1, "NymphSprite2.png"),
			getSprite(1, "NymphSprite3.png")
		},
		{
			getSprite(2, "CentaurSprite0.png"),
			getSprite(2, "CentaurSprite1.png"),
			getSprite(2, "CentaurSprite2.png"),
			getSprite(2, "CentaurSprite3.png")
		},
		{
			getSprite(3, "WarriorSprite0.png"),
			getSprite(3, "WarriorSprite1.png"),
			getSprite(3, "WarriorSprite2.png"),
			getSprite(3, "WarriorSprite3.png")
		},
		{
			getSprite(4, "IcarusSprite0.png"),
			getSprite(4, "IcarusSprite1.png"),
			getSprite(4, "IcarusSprite2.png"),
			getSprite(4, "IcarusSprite3.png")
		}
	};
	
	public static final double PLAYER_ICON_IDEAL_SIZE = TREE.getWidth();
	
	private Images() {}

	public static Image tileImage(MiniTag tag) {
		switch(tag) {
			case ARCHERY: return ARCHERY_TILE;
			case HURDLES: return HURDLES_TILE;
			case RUNNING: return RUNNING_TILE;
			default: throw new UnsupportedOperationException(String.format("tile image for %s", tag));
		}
	}
	
	public static Image instructions(MiniTag tag) {
		switch(tag) {
			case ARCHERY: return ARCHERY_INSTRUCTIONS;
			case HURDLES: return HURDLES_INSTRUCTIONS;
			case RUNNING: return RUNNING_INSTRUCTIONS;
			default: throw new UnsupportedOperationException(String.format("instructions for %s", tag));
		}
	}
	
	public static Image background(MiniTag tag) {
		switch(tag) {
			case ARCHERY: return ARCHERY_BACKGROUND;
			case HURDLES: return HURDLES_BACKGROUND;
			case RUNNING: return null;
			default: throw new UnsupportedOperationException(String.format("background for %s", tag));
		}
	}
	
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
	
	/** Assumes {@code n} between {@code 1} and {@code 4} (inclusive). */
	public static Image avatar(int n) {
		switch(n) {
			case 1: return TREE_AVATAR;
			case 2: return LYRE_AVATAR;
			case 3: return WARRIOR_AVATAR;
			case 4: return WINGS_AVATAR;
		}
		throw new IllegalArgumentException(String.format("Invalid player number: %d", n));
	}
	
	/** Returns sprite {@code n} for the given player. {@code n} must be between {@code 0} and {@code 3} (inclusive). */
	public static Image sprite(int player, int n) { 
		Player.validate(player);
		return MINIGAME_SPRITES[player][n];
	}
	
	public static double spriteWidth(int player) {
		return SPRITE_WIDTHS[Player.validate(player)];
	}
	
	public static double spriteHeight(int player) {
		return SPRITE_HEIGHT; //all sprites have the same height.
	}
	
	public static int stillSpriteIndex(int player) {
		switch(player) {
			case 1: return 1;
			case 2: return 1;
			case 3: return 3;
			case 4: return 3;
			default: throw new IllegalArgumentException(String.format("Player: %d", player));
		}
	}
	public static Image stillSprite(int player) {
		return sprite(player, stillSpriteIndex(player));
	}
	
	public static int nextSpriteIndex(int index) {
		return (index + 1) % MINIGAME_SPRITES[1].length;
	}
	
	public static int airSpriteIndex(int player) {
		switch(player) {
			case 1: return 0;
			case 2: return 3;
			case 3: return 1;
			case 4: return 0;
			default: throw new IllegalArgumentException(String.format("Player: %d", player));
		}
	}
	
	public static Image airSprite(int player) {
		return sprite(player, airSpriteIndex(player));
	}
	
	public static Image playerCountIcon(int playerCount) {
		switch(playerCount) {
			case 2: return SELECT_2;
			case 3: return SELECT_3;
			case 4: return SELECT_4;
		}
		throw new IllegalArgumentException(String.format("playerCount = %d", playerCount));
	}
	
	public static Image runningGround(int variant) {
		switch(variant) {
			case 1: return RUNNING_GROUND_1;
			case 2: return RUNNING_GROUND_2;
			default: throw new IllegalArgumentException(String.format("variant: %d", variant));
		}
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
	
	private static Image getSprite(int player, String filename) {
		return get(filename, spriteWidth(player), SPRITE_HEIGHT, true, true);
	}
	
	public static Image singleKeyImage(int number) {
		switch(GameInput.controls().single(number)) {
			case DIGIT1: return KEY_1;
			case X: return KEY_X;
			case M: return KEY_M;
			case EQUALS: return KEY_EQUALS;
			default: throw new IllegalArgumentException(String.format("number: %d", number));
		}
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