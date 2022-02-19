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
	
	public static final double SPRITE_WIDTH = 80, SPRITE_HEIGHT = 120;
	public static final int SPRITES = 4;
	
	public static final Image
		//main menu stuff
		MAIN_MENU = get("main_menu.png"),
		PLAY_BUTTON = get("PlayButton.png"),
		QUIT_BUTTON = get("quit_button.png"),
		//player select stuff
		SELECT_2 = get("select2.png"),
		SELECT_3 = get("select3.png"),
		SELECT_4 = get("select4.png"),
		COUNT_SELECT_HOVER = get("count_select_hover.png"),
		//avatars
		TREE_AVATAR = get("Tree Avatar.png"),
		LYRE_AVATAR = get("Lyre Avatar.png"),
		WARRIOR_AVATAR = get("Warrior Avatar.png"),
		WINGS_AVATAR = get("Wings Avatar.png"),
		AVATAR_ACTIVE_BORDER = get("avatar_active_border.png"),
		AVATAR_INACTIVE_BORDER = get("avatar_inactive_border.png"),
		//player icons
		TREE = get("TreePlayer.png"), // player 1
		LYRE = get("LyrePlayer.png"), //player 2
		WARRIOR = get("WarriorPlayer.png"), //player 3
		WINGS = get("WingsPlayer.png"), //player 4
		//die
		DIE0 = get("DiceBase.png"),
		DIE1 = get("Dice1.png"),
		DIE2 = get("Dice2.png"),
		DIE3 = get("Dice3.png"),
		DIE4 = get("Dice4.png"),
		DIE5 = get("Dice5.png"),
		DIE6 = get("Dice6.png"),
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
		EVENT_BACKGROUND = get("event_background.png"),
		//general minigame stuff
		PRESS_SPACE = get("PressSpace.png"),
		//archery
		ARCHERY_INSTRUCTIONS = get("ArcheryInstructions.png"),
		REWARDS_BACKGROUND = get("rewards_background.png"),
		FENCE = get("fence.png"),
		ARROW = get("arrow.png"),
		TARGET = get("Target.png"),
		ARCHERY_BACKGROUND = get("archery_background.png"),
		//hurdles
		HURDLES_BACKGROUND = get("Sky.png"),
		HURDLES_INSTRUCTIONS = get("hurdles_instructions.png"),
		HURDLES_GROUND = get("hurdles_ground.png"),
		JUMP_BAR = get("jump_bar.png"),
		JUMP_BAR_BACKGROUND = get("jump_bar_background.png"),
		JUMP_BAR_TICK = get("jump_bar_tick.png"),
		HURDLE_HEAD = get("hurdle_head.png"),
		HURDLE_LEGS = get("hurdle_legs.png"),
		//running
		RUNNING_TILE = get("RunningTile.png"),
		RUNNING_INSTRUCTIONS = get("RunningInstructions.png"),
		RUNNING_SKY = get("RunningSky.png"),
		RUNNING_GROUND_1 = get("RunningGround1.png"),
		RUNNING_GROUND_2 = get("RunningGround2.png"),
		BIG_ROCK = get("BigRock.png"),
		SMALL_ROCK = get("SmallRock.png"),
		//misc
		HELPER = get("Helper.png");
			
	
	/** row is the player, index is the sprite for that player. */
	private static final Image[][] MINIGAME_SPRITES = {
		null,
		{
			getSprite("TreeSprite0.png"),
			getSprite("TreeSprite1.png"),
			getSprite("TreeSprite2.png"),
			getSprite("TreeSprite3.png")
		},
		{
			getSprite("LyreSprite0.png"),
			getSprite("LyreSprite1.png"),
			getSprite("LyreSprite2.png"),
			getSprite("LyreSprite3.png")
		},
		{
			getSprite("WarriorSprite0.png"),
			getSprite("WarriorSprite1.png"),
			getSprite("WarriorSprite2.png"),
			getSprite("WarriorSprite3.png")
		},
		{
			getSprite("WingsSprite0.png"),
			getSprite("WingsSprite1.png"),
			getSprite("WingsSprite2.png"),
			getSprite("WingsSprite3.png")
		}
	};
	
	public static final double PLAYER_IDEAL_SIZE = TREE.getWidth();
	
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
	
	public static int stillSpriteIndex(int player) {
		switch(player) {
			case 1: return 3;
			case 2: return 3;
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
			case 1: return 1;
			case 2: return 1;
			case 3: return 1;
			case 4: return 1;
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
	
	private static Image getSprite(String filename) {
		return get(filename, SPRITE_WIDTH, SPRITE_HEIGHT, true, true);
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