package minigames.running;

import game.board.Board;
import javafx.scene.input.KeyCode;
import minigames.*;
import minigames.running.fx.RunningFXLayer;
import minigames.running.imagelayer.RunningImageLayer;
import players.list.PlayerList;

public class Running extends Minigame {

	public static final double STARTING_GAME_VELOCITY = -200;
	
	private static final Running INSTANCE = new Running();
	
	public static final Running get() {
		return INSTANCE;
	}
	
	private Running() {
		super(MiniTag.RUNNING, new RunningImageLayer(), new RunningFXLayer());
	}

	@Override
	public void update(long diff) {
		if(!isFinished())
			imageLayer().update(diff); //only lets update through if ingame, so we don't need to verify that instructions aren't showing.
	}

	@Override
	public void startMinigame() {
		setPlayersRemaining(PlayerList.upTo(Board.get().playerCount()));
		imageLayer().start();
	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected MinigameResult computeResult() {
		// TODO Auto-generated method stub
		return null;
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
	
}
