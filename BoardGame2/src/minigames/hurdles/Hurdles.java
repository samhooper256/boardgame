package minigames.hurdles;

import javafx.scene.input.KeyCode;
import minigames.*;
import minigames.hurdles.fx.HurdlesFXLayer;
import minigames.hurdles.imagelayer.HurdlesImageLayer;

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

	@Override
	public void keyPressedIngame(KeyCode kc) {
		imageLayer().keyPressedIngame(kc);
	}

	@Override
	public void keyReleasedIngame(KeyCode kc) {
		// TODO Auto-generated method stub
		
	}
	
}
