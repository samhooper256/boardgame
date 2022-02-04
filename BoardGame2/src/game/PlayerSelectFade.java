package game;

import base.panes.FadeLayer;
import javafx.util.Duration;
import players.Player;

public class PlayerSelectFade extends FadeLayer {

	static final Duration FADE_IN = Duration.millis(750), FADE_OUT = FADE_IN;
	
	private int playerCount;
	
	public PlayerSelectFade() {
		super(FADE_IN, FADE_OUT);
		playerCount = Player.maxCount();
	}

	@Override
	protected void peakAction() {
		MainScene.get().startGame(playerCount);
	}

	@Override
	protected void endAction() {
		// nothing
	}
	
	public void transitionToNewGame(int playerCount) {
		this.playerCount = Player.validate(playerCount);
		fader().fadeIn();
	}
	
}
