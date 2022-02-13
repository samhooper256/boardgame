package minigames.archery;

import java.util.*;

import fxutils.Fader;
import game.board.Board;
import javafx.animation.*;
import javafx.scene.input.*;
import javafx.util.Duration;
import minigames.*;
import minigames.archery.fx.ArcheryFXLayer;
import minigames.archery.imagelayer.*;
import minigames.archery.waves.*;
import players.Player;
import players.list.PlayerList;

/** The {@link Archery} {@link Minigame }consists of a series of {@link ArcheryWave waves}. All players take turns
 * beating shooting at the target; when all players have taken a shot, the remaining players move to the next wave. As
 * soon as a player misses, they are out for the rest of the minigame. */
public class Archery extends Minigame {

	private static final Duration WAVE_POPUP_DURATION = Duration.millis(1000); //Does not include time spent fading out.
	private static final Archery INSTANCE = new Archery(WaveGenerator.STANDARD);
	
	public static Archery get() {
		return INSTANCE;
	}
	
	public static ArcheryImageLayer il() {
		return get().imageLayer();
	}
	
	private final WaveGenerator waveGenerator;
	private final Map<Player, Archer> archerMap;
	private final Set<Archer> movableArchers;
	private final SortedSet<Survival> survivals;
	private final Timeline waveTimeline; //TODO make this use update(long) ? 
	
	private boolean arrowFired, finished;
	private int waveIndex, turn;
	private Target currentTarget;
	
	private Archery(WaveGenerator waveGenerator) {
		super(MiniTag.ARCHERY, new ArcheryImageLayer(), new ArcheryFXLayer());
		this.waveGenerator = waveGenerator;
		archerMap = new HashMap<>();
		Board.get().players().forEachOrdered(p -> archerMap.put(p, new Archer(p.number())));
		waveIndex = 1;
		turn = 1;
		survivals = new TreeSet<>();
		movableArchers = new HashSet<>();
		arrowFired = finished = false;
		currentTarget = null;
		Fader wtfader = fxLayer().waveText().fader();
		waveTimeline = new Timeline(new KeyFrame(WAVE_POPUP_DURATION, eh -> wtfader.fadeOutAndHide()));
		imageLayer().init();
	}
	
	@Override
	public void startMinigame() {
		playersRemaining = PlayerList.upTo(Board.get().playerCount());
		players = playersRemaining.frozen();
		waveIndex = 0;
		turn = 1;
		survivals.clear();
		arrowFired = finished = false;
		currentTarget = null;
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
	
	@Override
	public void ingameStarted() {
		if(waveIndex != 0)
			throw new IllegalStateException("Cannot start first wave");
		startNextWaveOrEndGame();
	}
	
	private void startNextWaveOrEndGame() {
		waveIndex++;
		if(playersRemaining().size() == 1)
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
		if(playersRemaining.size() == 0)
			finish();
		else
			incrementTurn();
	}

	/** Assumes the given player is alive. */
	private void kill(int player) {
		imageLayer().remove(archer(player));
		survivals.add(new Survival(player, waveIndex));
		playersRemaining().remove(player);
	}
	
	private int nextTurn(int turn) {
		return playersRemaining().smallestGreaterWrapping(turn);
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
		if(playersRemaining().size() == 1)
			survivals.add(new Survival(getOnlyPlayerRemaining(), waveIndex));
		rewardsDisplay().show(getResult());
	}
	
	@Override
	public void update(long diff) {
		imageLayer().update(diff);
	}
	
	@Override
	public void keyPressedIngame(KeyCode kc) {
		imageLayer().keyPressedIngame(kc);
	}
	
	@Override
	public void keyReleasedIngame(KeyCode kc) {
		imageLayer().keyReleasedIngame(kc);
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
	
	@Override
	protected MinigameResult computeResult() {
		return MinigameResult.from(survivals);
	}
	
	@Override
	public boolean isFinished() {
		return finished;
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
