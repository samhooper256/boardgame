package minigames.archery;

import java.util.*;

import base.input.GameInput;
import game.*;
import javafx.scene.input.*;
import minigames.Minigame;
import minigames.archery.waves.*;
import players.Player;

/** The {@link ArcheryMinigame} consists of a series of {@link ArcheryWave waves}. All players take turns beating the
 * wave. If a player misses the target at any point, they are out for the rest of the game.
 * The minigame ends when there is one player remaining. */
public class ArcheryMinigame extends Minigame {

	private static final ArcheryMinigame INSTANCE = new ArcheryMinigame(WaveGenerator.STANDARD);
	
	public static ArcheryMinigame get() {
		return INSTANCE;
	}
	
	public static ArcheryScaledPane sp() {
		return get().imageLayer();
	}
	
	private final WaveGenerator waveGenerator;
	private final Map<Player, Archer> archerMap;
	private final Set<Archer> movableArchers;
	private final boolean[] alive;
	
	private boolean arrowFired;
	private int waveIndex, turn;
	private Target currentTarget;
	
	private ArcheryMinigame(WaveGenerator waveGenerator) {
		super(new ArcheryScaledPane(), new ArcheryFXLayer());
		this.waveGenerator = waveGenerator;
		archerMap = new HashMap<>();
		Board.get().players().forEachOrdered(p -> {
			archerMap.put(p, new Archer(p.image()));
		});
		Collection<Archer> archers = archerMap.values();
		imageLayer().init(archers);
		waveIndex = 1;
		turn = 1;
		alive = new boolean[Board.maxPlayerCount() + 1];
		for(int i = 1; i <= Board.get().playerCount(); i++)
			alive[i] = true;
		movableArchers = new HashSet<>();
		arrowFired = false;
		currentTarget = null;
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
		imageLayer().start();
		createNextTarget();
	}

	private void incrementWave() {
		waveIndex++;
		System.out.printf("wave incremented to: %d%n", waveIndex);
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
		if(turn < oldTurn)
			incrementWave();
		createNextTarget();
		arrowFired = false;
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
	
	private void win(int player) {
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
	public void mouseClicked(MouseEvent me) {
		if(!arrowFired) {
			archer(turn).mouseClicked(me);
			arrowFired = true;
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
	
	@Override
	public ArcheryScaledPane imageLayer() {
		return (ArcheryScaledPane) super.imageLayer();
	}
	
	@Override
	public ArcheryFXLayer fxLayer() {
		return (ArcheryFXLayer) super.fxLayer();
	}
}
