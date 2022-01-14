package minigames.archery;

import java.util.*;

import base.*;
import base.input.GameInput;
import fxutils.Images;
import game.*;
import javafx.scene.input.*;
import javafx.util.Duration;
import minigames.Minigame;
import minigames.archery.waves.*;
import players.Player;
import utils.Intersections;

/** The {@link ArcheryMinigame} consists of a series of {@link ArcheryWave waves}. All players take turns beating the
 * wave. If a player misses the target at any point, they are out for the rest of the game.
 * The minigame ends when there is one player remaining. */
public class ArcheryMinigame extends Minigame {

	private static final Duration INSTRUCTIONS_FADE_OUT_DURATION = Duration.millis(300);
	private static final ArcheryMinigame INSTANCE = new ArcheryMinigame(WaveGenerator.STANDARD);
	
	public static ArcheryMinigame get() {
		return INSTANCE;
	}
	
	private final FadeableImagePane instructions, pressSpace;
	private final Fence fence;
	private final WaveGenerator waveGenerator;
	private final Map<Player, Archer> archerMap;
	private final Set<Archer> movableArchers;
	private final boolean[] alive;
	
	private boolean arrowFired;
	private int waveIndex, turn;
	
	private ArcheryMinigame(WaveGenerator waveGenerator) {
		super();
		this.waveGenerator = waveGenerator;
		instructions = new FadeableImagePane(Images.MINIGAME_INSTRUCTIONS, INSTRUCTIONS_FADE_OUT_DURATION);
		instructions.setIdealCenter(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT / 2);
		pressSpace = new FadeableImagePane(Images.PRESS_SPACE, INSTRUCTIONS_FADE_OUT_DURATION);
		pressSpace.setIdealCenter(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT * .8);
		fence = new Fence();
		fence.setIdealCenter(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT * .75);
		archerMap = new HashMap<>();
		Board.get().players().forEachOrdered(p -> {
			archerMap.put(p, new Archer(p.image()));
		});
		Collection<Archer> archers = archerMap.values();
		int archer = 1;
		for(Archer a : archers)
			a.setIdealCenter(DEFAULT_WIDTH / (archers.size() + 1) * archer++, DEFAULT_HEIGHT * .85);
		waveIndex = 1;
		turn = 1;
		alive = new boolean[Board.maxPlayerCount() + 1];
		for(int i = 1; i <= Board.maxPlayerCount(); i++)
			alive[i] = true;
		movableArchers = new HashSet<>();
		arrowFired = false;
		initMovableArchers();
	}

	private void initMovableArchers() {
		if(GameInput.isSingle())
			movableArchers.add(archer(1));
		else
			for(int i = 1; i <= Board.get().playerCount(); i++)
				movableArchers.add(archer(i));
	}
	
	@Override
	public void start() {
		instructions.makeFullyVisible();
		add(new ImagePane(Images.ARCHERY_BACKGROUND));
		addAll(archerMap.values());
		addAll(fence, instructions, pressSpace);
		createNextTarget();
	}

	private void incrementWave() {
		waveIndex++;
		System.out.printf("wave incremented to: %d%n", waveIndex);
	}

	public void createNextTarget() {
		add(currentWave().createTarget(this::targetHit));
	}
	
	/** Assumes the given {@link Target} has already been trashed. */
	private void targetHit(Target t) {
		int oldTurn = turn;
		updateControls(turn = nextTurn(turn));
		if(turn < oldTurn)
			incrementWave();
		createNextTarget();
		arrowFired = false;
	}

	/** Assumes the given {@link Arrow} has already been trashed. */
	public void arrowLeftScreen(Arrow a) {
		//TODO
	}
	
	private int nextTurn(int turn) {
		do {
			turn = turn == Board.get().playerCount() ? 1 : turn + 1;
		}
		while(!alive[turn]);
		return turn;
	}
	
	private void updateControls(int player) {
		if(GameInput.isSingle()) {
			movableArchers.clear();
			movableArchers.add(archer(player));
		}
	}
	
	public Archer archer(int playerNumber) {
		return archerMap.get(Player.get(playerNumber));
	}
	
	public ArcheryWave currentWave() {
		return waveGenerator.get(waveIndex);
	}
	
	@Override
	public void updateGame(long diff) {
		if(!instructionsShowing()) {
			for(ImagePane ip : imagesUnmodifiable())
				if(ip instanceof Updatable)
					((Updatable) ip).update(diff);
		}
	}
	
	@Override
	public void keyPressed(KeyCode kc) {
		if(instructionsShowing()) {
			if(kc == KeyCode.SPACE && !instructions.isFadingOut()) {
				instructions.fadeOut(this);
				pressSpace.fadeOut(this);
			}
		}
		else {
			if(kc == KeyCode.E)
				MainScene.get().fadeBackFromMinigame(null);
			for(Archer a : archerMap.values())
				a.keyPressed(kc);
		}
	}
	
	@Override
	public void keyReleased(KeyCode kc) {
		if(!instructionsShowing())
			for(Archer a : archerMap.values())
				a.keyReleased(kc);
	}
	
	@Override
	public void mouseClicked(MouseEvent me) {
		if(!arrowFired) {
			archer(turn).mouseClicked(me);
			arrowFired = true;
		}
	}

	private void kill(int player) {
		remove(archer(player));
		alive[player] = false;
	}
	
	/** {@code true} if the instructions are showing, even if they are in the process of fading out. */
	private boolean instructionsShowing() {
		return instructions.getOpacity() > 0;
	}
	
	/** Returns {@code true} iff any {@link Arrow Arrows}. intersect the given {@link ImagePane}. */
	public boolean anyArrowsIntersect(ImagePane ip) {
		for(ImagePane a : images)
			if(a instanceof Arrow && Intersections.test(a, ip))
				return true;
		return false;
	}
	
	public void addArrow(Arrow a) {
		add(a);
	}
	
	public Fence fence() {
		return fence;
	}
	
	public boolean isMobile(Archer a) {
		return movableArchers.contains(a);
	}
	
}
