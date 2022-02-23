package game;

import base.panes.FadeLayer;
import game.win.WinPane;
import javafx.util.Duration;

public class WinFade extends FadeLayer {

	private static final Duration IN = Duration.millis(1000), OUT = Duration.millis(1000);
	
	public WinFade() {
		super(IN, OUT);
	}
	
	@Override
	protected void peakAction() {
		MainScene.get().setBaseContent(WinPane.get());
	}

	@Override
	protected void endAction() {
		// TODO Auto-generated method stub
	}

}
