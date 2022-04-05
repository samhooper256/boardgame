package game.board;

import java.util.*;
import java.util.stream.*;

import base.Updatable;
import base.panes.*;
import events.*;
import game.*;
import game.board.fx.BoardFXLayer;
import game.board.imagelayer.*;
import game.win.WinPane;
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
		setReadyToRoll(false);
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
		setReadyToRoll(false);
		Player p = Player.get(turn);
		if(p.tile().index() + diceRoll >= TILE_COUNT)
			diceRoll = TILE_COUNT - p.tile().index();
		imageLayer().walk(p, diceRoll);
	}
	
	public void playerLanded(Tile tile) {
		tile.land(currentPlayer());
	}
	
	public void incrementTurn() {
		currentPlayer().turnFinished();
		turn = nextTurn(turn);
		while(Player.get(turn).isInjured()) {
			Player.get(turn).turnFinished();
			turn = nextTurn(turn);
		}
		imageLayer().turnSetTo(turn());
		setupDie();
	}
	
	private int nextTurn(int turn) {
		return turn == playerCount() ? 1 : turn + 1;
	}
	
	private void setupFirstDie() {
		RollType firstRollType = currentPlayer().rollType();
		BoardAnimations.setupFirstDie(firstRollType);
		lastRollType = firstRollType;
		setReadyToRoll(true);
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
			setReadyToRoll(true);
	}
	
	private void setupChooseDie() {
		if(lastRollType == RollType.RANDOM)
			//readyToRoll is set to true after the transition finishes.
			BoardAnimations.transitionToChooseRoll(this::rollTransitionFinished);
		else
			setReadyToRoll(true);
	}
	
	private void rollTransitionFinished() {
		setReadyToRoll(true);
	}
	
	/** Called immediately after the {@link BoardFade} disappears and the user is looking at the {@link Board}. */
	public void minigameFinished(MinigameResult mr) {
		for(MedalReward reward : mr.rewards())
			reward.apply();
		incrementTurn();
	}
	
	public void showEvent(Event event) {
		setReadyToRoll(false);
		if(event instanceof SimpleTextEvent)
			showSimpleTextEvent((SimpleTextEvent) event);
		else
			showComplexEvent((ComplexEvent) event);
		currentEvent = event;
	}

	private void showSimpleTextEvent(SimpleTextEvent event) {
		imageLayer().showSimpleTextEvent(event);
		fxLayer().showSimpleTextEvent(event);
	}
	
	private void showComplexEvent(ComplexEvent event) {
		imageLayer().showComplexEvent(event);
		fxLayer().showComplexEvent(event);
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
		imageLayer().eventFinished();
		fxLayer().eventFinished();
		incrementTurn();
		currentEvent = null;
		setReadyToRoll(true);
	}
	
	public void endGame() {
		List<Player> ranking = players().sorted(Comparator.comparing(Player::score).reversed()
				.thenComparing(Player::number))
				.collect(Collectors.toCollection(ArrayList::new));
		WinPane.get().setupFor(ranking);
		MainScene.get().fadeToWinPane();
	}
	
	public Player currentPlayer() {
		return Player.get(turn());
	}
	
	public Event currentEvent() {
		return currentEvent;
	}
	
	public boolean readyToRoll() {
		return readyToRoll;
	}
	
	private void setReadyToRoll(boolean readyToRoll) {
		this.readyToRoll = readyToRoll;
		if(readyToRoll())
			RollableDie.get().notifyBoardWasSetReadyToRoll();
	}
	
	public int playerCount() {
		return playerCount;
	}
	
	/** Returns a {@link Stream} containing only the players up to (and including) {@link #playerCount()}. */
	public Stream<Player> players() {
		return IntStream.rangeClosed(1, playerCount).mapToObj(Player::get);
	}
	
	@Override
	public void update(long diff) {
		imageLayer().update(diff);
	}
	
	@Override
	public void keyPressed(KeyCode kc) {
		if(!(currentEvent instanceof ComplexEvent)) //true for nulls.
			tryRequestEventFinish();
		cheats().keyPressed(kc);
	}
	
	@Override
	public void keyReleased(KeyCode kc) {
		cheats().keyReleased(kc);
	}
	
	public BoardCheats cheats() {
		return BoardCheats.get();
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