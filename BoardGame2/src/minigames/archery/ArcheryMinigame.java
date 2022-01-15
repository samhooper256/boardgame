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
	
	private ArcheryMinigame(WaveGenerator waveGenerator) {
		super(new ArcheryScaledPane());
		imageLayer().setGamePane(this);
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
		imageLayer().start();
		createNextTarget();
	}

	private void incrementWave() {
		waveIndex++;
		System.out.printf("wave incremented to: %d%n", waveIndex);
	}

	/** Assumes the given {@link Target} has already been trashed. */
	void targetHit(Target t) {
		int oldTurn = turn;
		updateControls(turn = nextTurn(turn));
		if(turn < oldTurn)
			incrementWave();
		createNextTarget();
		arrowFired = false;
	}

	public void createNextTarget() {
		imageLayer().add(currentWave().createTarget(this::targetHit));
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

	private void kill(int player) { //TODO
//		remove(archer(player));
//		alive[player] = false;
	}
	
	public boolean isMobile(Archer a) {
		return movableArchers.contains(a);
	}
	
	public Collection<Archer> archers() {
		return archerMap.values();
	}
	
	@Override
	public ArcheryScaledPane imageLayer() {
		return (ArcheryScaledPane) super.imageLayer();
	}
}
