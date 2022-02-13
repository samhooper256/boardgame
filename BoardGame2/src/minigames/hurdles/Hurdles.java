package minigames.hurdles;

import java.util.*;

import game.board.Board;
import javafx.scene.input.KeyCode;
import minigames.*;
import minigames.hurdles.fx.HurdlesFXLayer;
import minigames.hurdles.imagelayer.*;
import players.list.PlayerList;

public class Hurdles extends Minigame {

	private static final Hurdles INSTANCE = new Hurdles();
	
	public static Hurdles get() {
		return INSTANCE;
	}
	
	public static HurdlesImageLayer il() {
		return get().imageLayer();
	}
	
	private final Set<Survival> survivals;
	
	private Hurdles() {
		super(MiniTag.HURDLES, new HurdlesImageLayer(), new HurdlesFXLayer());
		survivals = new TreeSet<>();
	}
	
	@Override
	public void update(long diff) {
		if(!isFinished())
			imageLayer().update(diff);
	}

	@Override
	public void startMinigame() {
		playersRemaining = PlayerList.upTo(Board.get().playerCount());
		survivals.clear();
		imageLayer().start();
	}
	
	@Override
	public void ingameStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isFinished() {
		return playersRemaining().size() == 0;
	}

	@Override
	protected MinigameResult computeResult() {
		return MinigameResult.from(survivals);
	}

	public void kill(Jumper j) {
		imageLayer().kill(j);
		survivals.add(new Survival(j.number(), j.lethalHurdle().index()));
		playersRemaining().remove(j.number());
		if(isFinished())
			finish();
	}
	
	private void finish() {
		rewardsDisplay().show(getResult());
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
	public HurdlesImageLayer imageLayer() {
		return (HurdlesImageLayer) super.imageLayer();
	}
	
	@Override
	public HurdlesFXLayer fxLayer() {
		return (HurdlesFXLayer) super.fxLayer();
	}
	
}
