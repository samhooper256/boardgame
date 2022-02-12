package minigames.hurdles;

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
	
	private Hurdles() {
		super(MiniTag.HURDLES, new HurdlesImageLayer(), new HurdlesFXLayer());
	}
	
	@Override
	public HurdlesImageLayer imageLayer() {
		return (HurdlesImageLayer) super.imageLayer();
	}
	
	@Override
	public HurdlesFXLayer fxLayer() {
		return (HurdlesFXLayer) super.fxLayer();
	}

	@Override
	public void update(long diff) {
		imageLayer().update(diff);
	}

	@Override
	public void startMinigame() {
		playersRemaining = PlayerList.upTo(Board.get().playerCount());
		imageLayer().start();
	}
	
	@Override
	public void ingameStarted() {
		// TODO Auto-generated method stub
		
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

	public void kill(Jumper j) {
		imageLayer().kill(j);
		playersRemaining().remove(j.number());
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
