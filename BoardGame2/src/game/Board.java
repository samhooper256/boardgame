package game;

import java.util.stream.*;

import base.Updatable;
import base.input.GameInput;
import base.panes.*;
import events.Event;
import game.fx.BoardFXLayer;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import minigames.MinigameResult;
import players.*;
import tiles.Tile;

public class Board extends GamePane implements Updatable {

	public static final int TILE_COUNT = 36;
	public static final Duration FADE_IN_DURATION = Duration.millis(500), FADE_OUT_DURATION = FADE_IN_DURATION;
	private static final int MAX_PLAYER_COUNT = 4;
	private static final int PLAYER_COUNT = 4; //TODO remove later - user can pick how many players.
	
	private static final Board INSTANCE = new Board(PLAYER_COUNT);
	
	static {
		INSTANCE.init();
	}
	
	public static Board get() {
		return INSTANCE;
	}
	
	public static BoardImageLayer sp() {
		return get().imageLayer();
	}
	
	public static int maxPlayerCount() {
		return MAX_PLAYER_COUNT;
	}
	
	public static int nextTurn(int turn) {
		return turn == get().playerCount() ? 1 : turn + 1;
	}
	
	public static int prevTurn(int turn) {
		return turn == 1 ? Board.get().playerCount() : turn - 1;
	}
	
	private int playerCount, turn;
	private RollType lastRollType;
	private boolean readyToRoll, paused;
	
	private Board(int playerCount) {
		super(new BoardImageLayer(), new BoardFXLayer());
		imageLayer().setGamePane(this);
		fxLayer().setGamePane(this);
		this.playerCount = playerCount;
		turn = 1;
		readyToRoll = false;
		paused = false;
		lastRollType = RollType.RANDOM;
	}
	
	private void init() {
		imageLayer().init();
		fxLayer().init();
	}
	
	/** Called immediately before this {@link Board} is shown to the player. */
	public void start() {
		setupDie();
		imageLayer().start();
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
	
	public void playerLanded(Tile tile) {
		imageLayer().playerLanded(currentPlayer(), tile);
		tile.land(currentPlayer());
	}
	
	public void incrementTurn() {
		currentPlayer().turnFinished();
		if(turn == playerCount)
			turn = 1;
		else
			turn++;
		imageLayer().turnIncrementedTo(turn());
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
	
	@Override
	public void keyPressed(KeyCode kc) {
		if(kc == GameInput.controls().pause()) {
			if(paused) {
				fxLayer().unpause();
				paused = false;
			}
			else {
				fxLayer().pause();
				paused = true;
			}
		}
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
	
	@Override
	public void update(long diff) {
		imageLayer().update(diff);
	}
	
	@Override
	public BoardImageLayer imageLayer() {
		return (BoardImageLayer) super.imageLayer();
	}
	
	@Override
	public BoardFXLayer fxLayer() {
		return (BoardFXLayer) super.fxLayer();
	}
	
}