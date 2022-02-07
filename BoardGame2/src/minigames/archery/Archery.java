package minigames.archery;

import java.util.*;
import java.util.stream.Collectors;

import fxutils.Fader;
import game.board.Board;
import javafx.animation.*;
import javafx.scene.input.*;
import javafx.util.Duration;
import medals.*;
import minigames.*;
import minigames.archery.fx.ArcheryFXLayer;
import minigames.archery.waves.*;
import minigames.rewards.RewardsDisplay;
import players.Player;

/** The {@link Archery} consists of a series of {@link ArcheryWave waves}. All players take turns beating the
 * wave. If a player misses the target at any point, they are out for the rest of the game.
 * The minigame ends when there is one player remaining. */
public class Archery extends Minigame {

	private static final Duration WAVE_POPUP_DURATION = Duration.millis(1000); //Does not include time spent fading out.
	private static final Archery INSTANCE = new Archery(WaveGenerator.STANDARD);
	
	public static Archery get() {
		return INSTANCE;
	}
	
	public static ArcheryImageLayer sp() {
		return get().imageLayer();
	}
	
	private final WaveGenerator waveGenerator;
	private final Map<Player, Archer> archerMap;
	private final Set<Archer> movableArchers;
	private final SortedSet<Survival> survivals;
	private final boolean[] alive;
	private final Timeline waveTimeline; //TODO make this use update(long) ? 
	private final RewardsDisplay rewardsDisplay;
	
	private boolean arrowFired, finished;
	private int waveIndex, turn, aliveCount;
	private Target currentTarget;
	
	private Archery(WaveGenerator waveGenerator) {
		super(new ArcheryImageLayer(), new ArcheryFXLayer());
		this.waveGenerator = waveGenerator;
		archerMap = new HashMap<>();
		Board.get().players().forEachOrdered(p -> archerMap.put(p, new Archer(p.number())));
		waveIndex = 1;
		turn = 1;
		aliveCount = Board.get().playerCount();
		alive = new boolean[Board.maxPlayerCount() + 1];
		survivals = new TreeSet<>();
		movableArchers = new HashSet<>();
		arrowFired = finished = false;
		currentTarget = null;
		Fader wtfader = fxLayer().waveText().fader();
		waveTimeline = new Timeline(new KeyFrame(WAVE_POPUP_DURATION, eh -> wtfader.fadeOutAndHide()));
		rewardsDisplay = new RewardsDisplay();
		imageLayer().init();
		imageLayer().instructions().fader().setFadeOutFinishedAction(() -> startFirstWave());
		fxLayer().init();
	}
	
	@Override
	public void start() {
		waveIndex = 0;
		turn = 1;
		aliveCount = Board.get().playerCount();
		Arrays.fill(alive, 1, aliveCount + 1, true);
		survivals.clear();
		arrowFired = finished = false;
		currentTarget = null;
		rewardsDisplay().hide();
		updateControls(turn);
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
		turn = nextTurn(turn);
		updateControls(turn);
		if(turn <= oldTurn) {
			startNextWaveOrEndGame();
		}
		else {
			createNextTarget();
			arrowFired = false;
		}
	}
	
	private void startFirstWave() {
		if(waveIndex != 0)
			throw new IllegalStateException("Cannot start first wave");
		startNextWaveOrEndGame();
	}
	
	private void startNextWaveOrEndGame() {
		waveIndex++;
		if(aliveCount == 1)
			finish();
		else
			startNextWave();
	}
	
	private void startNextWave() {
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
		if(aliveCount == 0)
			finish();
		else
			incrementTurn();
	}
	
	private void kill(int player) {
		imageLayer().remove(archer(player));
		survivals.add(new Survival(player, waveIndex));
		alive[player] = false;
		aliveCount--;
	}
	
	private int nextTurn(int turn) {
		do {
			turn = turn == Board.get().playerCount() ? 1 : turn + 1;
		}
		while(!alive[turn]);
		return turn;
	}
	
	private void updateControls(int player) { //TODO fix this. This should not be a method; just keep track of the single archer who can move.
		movableArchers.clear();
		movableArchers.add(archer(player));
	}
	
	public Archer archer(int player) {
		return archerMap.get(Player.get(player));
	}
	
	public ArcheryWave currentWave() {
		return waveGenerator.get(waveIndex);
	}
	
	private void finish() {
		finished = true;
		if(aliveCount == 1)
			survivals.add(new Survival(getOnlyPlayerAlive(), waveIndex));
		rewardsDisplay.show(getResult());
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
		if(aliveCount != 1)
			throw new IllegalStateException("Multiple players are still alive");
		for(int i = 1; i < alive.length; i++)
			if(alive[i])
				return i;
		throw new IllegalStateException("Should not happen");
	}
	
	public MinigameResult getResult() {
		if(!isFinished())
			throw new IllegalStateException("No winner yet");
		//maps each wave that a player died on to the list (sorted ascending) of all players who survived to that wave.
		//The keys in the map are in descending order.
		SortedMap<Integer, List<Integer>> groups = survivals.stream().collect(Collectors.groupingBy(
				Survival::wave,
				() -> new TreeMap<>(Comparator.reverseOrder()),
				Collectors.collectingAndThen(
					Collectors.mapping(Survival::player, Collectors.toCollection(TreeSet::new)),
					treeSet -> Collections.unmodifiableList(new ArrayList<>(treeSet))
				)
		));
		List<MedalReward> rewards = new ArrayList<>();
		Medal medal = Medal.GOLD;
		for(Map.Entry<Integer, List<Integer>> e : groups.entrySet()) {
			for(int player : e.getValue())
				rewards.add(MedalReward.to(medal, player));
			medal = medal.down();
			if(medal == null)
				break;
		}
		return MinigameResult.of(rewards);
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public RewardsDisplay rewardsDisplay() {
		return rewardsDisplay;
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
