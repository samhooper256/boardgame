package game;

import base.panes.FadeLayer;
import fxutils.Backgrounds;
import game.mainmenu.MainMenu;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class WinToMainFade extends FadeLayer {

	private static final Duration IN = Duration.millis(500), OUT = Duration.millis(500);
	
	public WinToMainFade() {
		super(IN, OUT);
		setBackground(Backgrounds.of(Color.BURLYWOOD));
	}

	@Override
	protected void peakAction() {
		MainScene.get().setBaseContent(MainMenu.get());
	}
	
	@Override
	protected void endAction() {
		//nothing
	}

}
