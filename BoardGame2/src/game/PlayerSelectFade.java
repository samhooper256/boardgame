package game;

import base.panes.FadeLayer;
import javafx.util.Duration;

public class PlayerSelectFade extends FadeLayer {

	static final Duration FADE_IN = Duration.millis(500), FADE_OUT = FADE_IN;
	
	public PlayerSelectFade() {
		super(FADE_IN, FADE_OUT);
	}

	@Override
	protected void peakAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void endAction() {
		// TODO Auto-generated method stub
		
	}
	
}
