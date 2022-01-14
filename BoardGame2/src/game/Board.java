package game;

import java.util.*;
import java.util.stream.*;

import base.*;
import events.Event;
import fxutils.Images;
import fxutils.Timing;
import javafx.geometry.*;
import javafx.util.Duration;
import minigames.MinigameResult;
import players.*;
import tiles.*;

public class Board extends AbstractScaledPane {

	public static final int TILE_COUNT = 36;
	
	private static final int PLAYER_COUNT = 2; //TODO remove later - user can pick how many players.
	
	private static final Board INSTANCE = new Board(PLAYER_COUNT);
	
	/** The delay between when a player lands on a minigame tile and when
	 * {@link MainScene#startMinigame(minigames.Minigame)} is called.*/
	private static final Duration LAND_DELAY_TO_MINIGAME = Duration.millis(500);
	
	public static Board get() {
		return INSTANCE;
	}
	
	private List<Tile> tileOrder;
	private int playerCount, turn;
	private RollType lastRollType;
	private boolean readyToRoll;
	
	private Board(int playerCount) {
		this.playerCount = playerCount;
		turn = 1;
		RollableDie die = RollableDie.get();
		die.setIdealCoords(DEFAULT_WIDTH / 2 - die.getIdealWidth() / 2, DEFAULT_HEIGHT / 2 - die.getIdealHeight() / 2);
		tileOrder = generateTileOrder();
		readyToRoll = false;
		ImagePane Background = new ImagePane(Images.BACKGROUND);
		add(Background);
		placeTiles();
		placePlayers();
		add(die);
		lastRollType = RollType.RANDOM;
	}

	private void placeTiles() {
		for(int i = 0; i < TILE_COUNT; i++) {
			Tile t = tileOrder.get(i);
			Point2D point = Main.POINTS.get(i);
			t.setIdealCoords(point);
			add(t);
		}
	}

	private void placePlayers() {
		for(int i = 1; i <= playerCount; i++) {
			add(Player.get(i));
			movePlayer(Player.get(i), tileAt(0));
		}
	}
	
	private List<Tile> generateTileOrder() {
		List<Tile> order = new ArrayList<>(TILE_COUNT);
		order.add(StartTile.get());
		for(TileSection section : TileSection.ORDER)
			order.addAll(section.randomOrder());
		return order;
	}
	
	/** Called immediately before this {@link Board} is shown to the player. */
	public void start() {
		setupDie();
	}
	
	public void clearAllImages() {
		getChildren().clear();
		images.clear();
	}
	
	/** The {@link Tile} at the given 0-based index, where the tile at index 0 is the start tile. */
	public Tile tileAt(int index) {
		return tileOrder.get(index);
	}

	public int tileIndex(Tile tile) {
		return tileOrder.indexOf(tile);
	}
	
	/** Moves the given {@link Player} to the given {@link Tile}.*/
	public void movePlayer(Player p, Tile t) {
		Tile oldTile = p.tile();
		oldTile.players().remove(p);
		p.setTile(t);
		t.players().add(p);
		t.arrangePlayers();
		oldTile.arrangePlayers();
	}
	
	/** Moves the given {@link Player} to the {@link Tile} at the given index. Updates the player's
	 * {@link Player#tile() current tile}.*/
	public void movePlayer(Player p, int tileIndex) {
		movePlayer(p, tileAt(tileIndex));
	}
	
	public int turn() {
		return turn;
	}
	
	public void executeTurn(int diceRoll) {
		readyToRoll = false;
		walk(Player.get(turn), diceRoll);
	}
	
	/** Updates the given {@link Player Player's} {@link Player#tile() current tile}. */
	private void walk(Player p, int distance) {
		WalkAnimation wa = new WalkAnimation(p, distance);
		wa.playFromStart();
	}
	
	/** Called by {@link WalkAnimation} to notify this {@link Board} that the animation has finished. */
	public void walkFinished(WalkAnimation w) {
		Tile destTile = tileAt(w.destTileIndex());
		Timing.doAfterDelay(LAND_DELAY_TO_MINIGAME, () -> {
			destTile.land(w.player());
			if(destTile instanceof SafeTile) //TODO this is not how this should be done.
				incrementTurn(); //Minigames and events will call this when they finish.
		});
	}
	
	private void incrementTurn() {
		Player.get(turn()).turnFinished();
		if(turn == playerCount)
			turn = 1;
		else
			turn++;
		setupDie();
	}
	
	private void setupDie() {
		RollType type = currentPlayer().rollType();
		switch(type) {
			case RANDOM: 
				setupRandomDie();
				break;
			case CHOOSE:
				setupChooseDie();
				break;
			default: throw new UnsupportedOperationException(String.format("Unsupported RollType: %s", type));
		}
		lastRollType = type;
	}
	
	private void setupRandomDie() {
		if(lastRollType == RollType.CHOOSE)
			//readyToRoll is set to true after the transition finishes.
			BoardAnimations.transitionToRandomRoll(this::rollTransitionFinished);
		else
			readyToRoll = true;
	}
	
	private void setupChooseDie() {
		if(lastRollType == RollType.RANDOM)
			//readyToRoll is set to true after the transition finishes.
			BoardAnimations.transitionToChooseRoll(this::rollTransitionFinished);
		else
			readyToRoll = true;
	}
	
	private void rollTransitionFinished() {
		readyToRoll = true;
	}
	
	public void minigameFinished(MinigameResult mr) {
		incrementTurn();
	}
	
	public void eventFinished(Event event) {
		incrementTurn();
	}
	
	public Player currentPlayer() {
		return Player.get(turn());
	}
	
	public boolean readyToRoll() {
		return readyToRoll;
	}
	
	public int playerCount() {
		return playerCount;
	}
	
	public Stream<Player> players() {
		return IntStream.rangeClosed(1, playerCount).mapToObj(Player::get);
	}
	
	
}