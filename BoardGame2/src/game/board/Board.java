package game.board;

import java.util.stream.*;

import base.Updatable;
import base.panes.*;
import events.*;
import game.BoardFade;
import game.board.fx.BoardFXLayer;
import game.board.imagelayer.*;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import medals.MedalReward;
import minigames.MinigameResult;
import players.*;
import tiles.Tile;

public class Board extends GamePane implements Updatable {

	public static final int TILE_COUNT = 36;
	public static final Duration EVENT_FADE_DURATION = Duration.millis(500);
	
	private static final int MAX_PLAYER_COUNT = 4;
	
	private static final Board INSTANCE = new Board();
	
	static {
		INSTANCE.init();
	}
	
	public static Board get() {
		return INSTANCE;
	}
	
	public static BoardImageLayer il() {
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
	private boolean readyToRoll;
	/** {@code true} as soon as the text popup starts fading in, {@code false} as soon as it starts fading out. */
	private Event currentEvent;
	
	private Board() {
		super(new BoardImageLayer(), new BoardFXLayer());
	}
	
	private void init() {
		imageLayer().init();
	}
	
	/** Called immediately before this {@link Board} is shown to the player. */
	public void start(int playerCount) {
		this.playerCount = playerCount;
		turn = 1;
		readyToRoll = false;
		currentEvent = null;
		Player.resetAll();
		setupFirstDie();
		imageLayer().start();
		fxLayer().start();
	}
	
	public int turn() {
		return turn;
	}
	
	public void executeTurn(int diceRoll) {
		readyToRoll = false;
		imageLayer().walk(Player.get(turn), diceRoll);
	}
	
	public void playerLanded(Tile tile) {
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
	
	private void setupFirstDie() {
		RollType firstRollType = currentPlayer().rollType();
		BoardAnimations.setupFirstDie(firstRollType);
		lastRollType = firstRollType;
		readyToRoll = true;
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
		tryRequestEventFinish();
	}
	
	private void rollTransitionFinished() {
		readyToRoll = true;
	}
	
	/** Called immediately after the {@link BoardFade} disappears and the user is looking at the {@link Board}. */
	public void minigameFinished(MinigameResult mr) {
		for(MedalReward reward : mr.rewards())
			reward.apply();
		incrementTurn();
	}
	
	public void showSimpleTextEvent(SimpleTextEvent event) {
		readyToRoll = false;
		imageLayer().showSimpleTextEvent(event);
		fxLayer().showSimpleTextEvent(event);
		currentEvent = event;
	}
	
	public void tryRequestEventFinish() {
		if(currentEvent != null)
			requestEventFinish();
	}
	
	public boolean requestEventFinish() {
		boolean result = imageLayer().requestEventFinish();
		if(result)
			fxLayer().demandEventFinish();
		return result;
	}
	
	/** In the case of a {@link SimpleTextEvent}, this should be called as soon as the event popup completely finishes
	 * fading out. */
	public void eventFinished() {
		incrementTurn();
		currentEvent = null;
		readyToRoll = true;
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