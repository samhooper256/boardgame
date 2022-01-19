package game;

import java.util.stream.*;

import base.panes.GamePane;
import events.Event;
import minigames.MinigameResult;
import players.*;

public class Board extends GamePane {

	public static final int TILE_COUNT = 36;
	
	private static final int MAX_PLAYER_COUNT = 4;
	private static final int PLAYER_COUNT = 3; //TODO remove later - user can pick how many players.
	
	private static final Board INSTANCE = new Board(PLAYER_COUNT);
	
	public static Board get() {
		return INSTANCE;
	}
	
	public static BoardScaledPane sp() {
		return get().imageLayer();
	}
	
	public static int maxPlayerCount() {
		return MAX_PLAYER_COUNT;
	}
	
	private int playerCount, turn;
	private RollType lastRollType;
	private boolean readyToRoll;
	
	private Board(int playerCount) {
		super(new BoardScaledPane());
		imageLayer().setGamePane(this);
		this.playerCount = playerCount;
		turn = 1;
		readyToRoll = false;
		lastRollType = RollType.RANDOM;
		imageLayer().init();
	}
	
	/** Called immediately before this {@link Board} is shown to the player. */
	public void start() {
		setupDie();
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
	
	public void incrementTurn() {
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
	
	@Override
	public BoardScaledPane imageLayer() {
		return (BoardScaledPane) super.imageLayer();
	}
	
}