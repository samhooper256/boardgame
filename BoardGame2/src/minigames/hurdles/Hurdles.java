package minigames.hurdles;

import minigames.*;
import minigames.hurdles.fx.HurdlesFXLayer;
import minigames.hurdles.imagelayer.HurdlesImageLayer;

public class Hurdles extends Minigame {

	private static final Hurdles INSTANCE = new Hurdles();
	
	public static Hurdles get() {
		return INSTANCE;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
	
}
