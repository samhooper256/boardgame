package minigames.running;

import java.util.*;

import game.board.Board;
import javafx.scene.input.KeyCode;
import minigames.*;
import minigames.running.fx.RunningFXLayer;
import minigames.running.imagelayer.*;
import players.list.PlayerList;

public class Running extends Minigame {

	public static final double STARTING_GAME_VELOCITY = -200;
	
	private static final Running INSTANCE = new Running();
	
	public static final Running get() {
		return INSTANCE;
	}
	
	public static RunningImageLayer il() {
		return get().imageLayer();
	}
	
	private final Set<Survival> survivals;
	
	private Running() {
		super(MiniTag.RUNNING, new RunningImageLayer(), new RunningFXLayer());
		survivals = new TreeSet<>();
	}

	@Override
	public void update(long diff) {
		if(!isFinished())
			imageLayer().update(diff); //only lets update through if ingame, so we don't need to verify that instructions aren't showing.
	}

	@Override
	public void startMinigame() {
		setPlayersRemaining(PlayerList.upTo(Board.get().playerCount()));
		survivals.clear();
		imageLayer().start();
	}

	public boolean isAlive(int player) {
		return playersRemaining().contains(player);
	}

	public void kill(Runner r) {
		imageLayer().kill(r);
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
	
	@Override
	public RunningImageLayer imageLayer() {
		return (RunningImageLayer) super.imageLayer();
	}
	
}
