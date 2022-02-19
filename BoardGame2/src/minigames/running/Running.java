package minigames.running;

import java.util.*;
import java.util.function.LongToDoubleFunction;

import game.board.Board;
import javafx.scene.input.KeyCode;
import minigames.*;
import minigames.running.fx.RunningFXLayer;
import minigames.running.imagelayer.*;
import players.list.PlayerList;

public class Running extends Minigame {

	private static final double STARTING_VELOCITY = -200, VELOCITY_CONSTANT = -20;
	private static final LongToDoubleFunction VELOCITY_FUNCTION =
			t -> STARTING_VELOCITY + VELOCITY_CONSTANT * Math.sqrt(t / 1e9);
	private static final Running INSTANCE = new Running();
	
	public static final Running get() {
		return INSTANCE;
	}
	
	public static RunningImageLayer il() {
		return get().imageLayer();
	}
	
	public static double startingVelocity() {
		return STARTING_VELOCITY;
	}
	
	private final Set<Survival> survivals;
	private double velocity;
	private long timeElapsed;
	
	private Running() {
		super(MiniTag.RUNNING, new RunningImageLayer(), new RunningFXLayer());
		survivals = new TreeSet<>();
	}

	@Override
	public void startMinigame() {
		setPlayersRemaining(PlayerList.upTo(Board.get().playerCount()));
		survivals.clear();
		velocity = STARTING_VELOCITY;
		timeElapsed = 0;
		fxLayer().start();
		imageLayer().start();
	}
	
	@Override
	public void update(long diff) {
		if(!isFinished())
			imageLayer().update(diff); //only lets update through if ingame, so we don't need to verify that instructions aren't showing.
		timeElapsed += diff;
		velocity = VELOCITY_FUNCTION.applyAsDouble(timeElapsed);
	}

	public boolean isAlive(int player) {
		return playersRemaining().contains(player);
	}

	/** Assumes the given {@link Runner} has a {@link Runner#lethalObstacle() lethal obstacle}. */
	public void kill(Runner r) {
		imageLayer().kill(r);
		fxLayer().displayIndexOfLethalObstacle(r);
		survivals.add(new Survival(r.number(), r.lethalObstacle().index()));
		playersRemaining().remove(r.number());
		if(isFinished())
			finish();
	}
	
	private void finish() {
		rewardsDisplay().show(getResult());
	}
	
	@Override
	public boolean isFinished() {
		return playersRemaining().size() == 0;
	}

	@Override
	protected MinigameResult computeResult() {
		return MinigameResult.from(survivals);
	}

	@Override
	public void ingameStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressedIngame(KeyCode kc) {
		imageLayer().keyPressedIngame(kc);
	}

	@Override
	public void keyReleasedIngame(KeyCode kc) {
		imageLayer().keyReleasedIngame(kc);
	}
	
	/** Will always be negative. */
	public double velocity() {
		return velocity;
	}
	
	public double velocityMultiplier() {
		return velocity / startingVelocity();
	}
	
	@Override
	public RunningImageLayer imageLayer() {
		return (RunningImageLayer) super.imageLayer();
	}

	@Override
	public RunningFXLayer fxLayer() {
		return (RunningFXLayer) super.fxLayer();
	}
	
}
