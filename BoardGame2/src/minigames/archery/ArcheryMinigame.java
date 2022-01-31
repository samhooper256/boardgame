package minigames.archery;

import java.util.*;

import fxutils.Fader;
import game.*;
import javafx.animation.*;
import javafx.scene.input.*;
import javafx.util.Duration;
import minigames.*;
import minigames.archery.fx.ArcheryFXLayer;
import minigames.archery.waves.*;
import players.Player;

/** The {@link ArcheryMinigame} consists of a series of {@link ArcheryWave waves}. All players take turns beating the
 * wave. If a player misses the target at any point, they are out for the rest of the game.
 * The minigame ends when there is one player remaining. */
public class ArcheryMinigame extends Minigame {

	private static final Duration WAVE_POPUP_DURATION = Duration.millis(1000); //Does not include time spent fading out.
	private static final ArcheryMinigame INSTANCE = new ArcheryMinigame(WaveGenerator.STANDARD);
	
	public static ArcheryMinigame get() {
		return INSTANCE;
	}
	
	public static ArcheryImageLayer sp() {
		return get().imageLayer();
	}
	
	private final WaveGenerator waveGenerator;
	private final Map<Player, Archer> archerMap;
	private final Set<Archer> movableArchers;
	private final boolean[] alive;
	private final Deque<Integer> medalOrder;
	private final Timeline waveTimeline;
	
	private boolean arrowFired;
	private int waveIndex, turn, winner;
	private Target currentTarget;
	
	private ArcheryMinigame(WaveGenerator waveGenerator) {
		super(new ArcheryImageLayer(), new ArcheryFXLayer());
		this.waveGenerator = waveGenerator;
		archerMap = new HashMap<>();
		medalOrder = new ArrayDeque<>();
		Board.get().players().forEachOrdered(p -> {
			archerMap.put(p, new Archer(p.number()));
		});
		waveIndex = 1;
		turn = 1;
		winner = 0; //no winner
		alive = new boolean[Board.maxPlayerCount() + 1];
		for(int i = 1; i <= Board.get().playerCount(); i++)
			alive[i] = true;
		movableArchers = new HashSet<>();
		arrowFired = false;
		currentTarget = null;
		Fader wtfader = fxLayer().waveText().fader();
		waveTimeline = new Timeline(
			new KeyFrame(WAVE_POPUP_DURATION, eh -> wtfader.fadeOutAndHide())
		);
		initMovableArchers();
		imageLayer().init();
		imageLayer().instructions().fader().setFadeOutFinishedAction(() -> startFirstWave());
	}

	private void initMovableArchers() {
		movableArchers.add(archer(1));
	}
	
	@Override
	public void start() {
		waveIndex = 0;
		turn = 1;
		winner = 0; //no winner
		for(int i = 1; i <= Board.get().playerCount(); i++)
			alive[i] = true;
		arrowFired = false;
		currentTarget = null;
		updateControls(turn);
		medalOrder.clear();
		imageLayer().start();
		fxLayer().start();
	}

	/** Assumes the given {@link Target} has already been trashed. */
	void targetHit(Arrow a, Target t) {
		currentTarget = null;
		imageLayer().trash(a);
		incrementTurn();
	}

	/** Also {@link #incrementWave() increments the wave} if necessary. */
	public void incrementTurn() {
		int oldTurn = turn;
		updateControls(turn = nextTurn(turn));
		if(turn < oldTurn) {
			startNextWave();
		}
		else {
			createNextTarget();
			arrowFired = false;
		}
	}
	
	private void startFirstWave() {
		if(waveIndex != 0)
			throw new IllegalStateException("Cannot start first wave");
		startNextWave();
	}
	
	private void startNextWave() {
		waveIndex++;
		fxLayer().startWave(waveIndex);
		createNextTarget();
		arrowFired = false;
		waveTimeline.playFromStart();
	}
	
	public void createNextTarget() {
		imageLayer().add(currentTarget = currentWave().createTarget(this::targetHit));
	}
	
	/** Assumes the given {@link Arrow} has already been trashed. */
	public void arrowLeftScreen(Arrow a) {
		imageLayer().remove(currentTarget);
		kill(turn);
		int playersRemaining = playersRemaining();
		if(playersRemaining > 1)
			incrementTurn();
		else
			win(getOnlyPlayerAlive());
	}
	
	private void kill(int player) {
		imageLayer().remove(archer(player));
		alive[player] = false;
		medalOrder.addFirst(player);
	}
	
	private int nextTurn(int turn) {
		do {
			turn = turn == Board.get().playerCount() ? 1 : turn + 1;
		}
		while(!alive[turn]);
		return turn;
	}
	
	private void updateControls(int player) {
		movableArchers.clear();
		movableArchers.add(archer(player));
	}
	
	private void win(int player) {
		winner = player;
		imageLayer().win(player);
		fxLayer().showWinner(player);
	}
	
	public Archer archer(int player) {
		return archerMap.get(Player.get(player));
	}
	
	public ArcheryWave currentWave() {
		return waveGenerator.get(waveIndex);
	}
	
	@Override
	public void update(long diff) {
		imageLayer().update(diff);
	}
	
	@Override
	public void keyPressed(KeyCode kc) {
		imageLayer().keyPressed(kc);
	}
	
	@Override
	public void keyReleased(KeyCode kc) {
		imageLayer().keyReleased(kc);
	}
	
	@Override
	public void mouseReleased(MouseEvent me) {
		if(imageLayer().instructionsShowing())
			return;
		if(me.getButton() == MouseButton.PRIMARY) {
			if(!arrowFired) {
				archer(turn).click(me);
				arrowFired = true;
			}
		}
	}

	public boolean isMobile(Archer a) {
		return movableArchers.contains(a);
	}
	
	public Collection<Archer> archers() {
		return archerMap.values();
	}
	
	public Target currentTarget() {
		return currentTarget;
	}
	
	public int playersRemaining() {
		int players = 0;
		for(int i = 1; i < alive.length; i++)
			if(alive[i])
				players++;
		return players;
	}
	
	/** @throws IllegalStateException if there is more than one player alive. */
	public int getOnlyPlayerAlive() {
		int p = 0;
		for(int i = 1; i < alive.length; i++)
			if(alive[i])
				if(p == 0)
					p = i;
				else
					throw new IllegalStateException("Multiple players are still alive.");
		return p;
	}
	
	public boolean hasWinner() {
		return winner > 0;
	}
	
	public MinigameResult getResult() {
		if(!hasWinner())
			throw new IllegalStateException("No winner");
		medalOrder.addFirst(getOnlyPlayerAlive());
		return MinigameResult.simple(medalOrder.stream().mapToInt(x -> x).toArray());
	}
	
	@Override
	public ArcheryImageLayer imageLayer() {
		return (ArcheryImageLayer) super.imageLayer();
	}
	
	@Override
	public ArcheryFXLayer fxLayer() {
		return (ArcheryFXLayer) super.fxLayer();
	}
}
