package game;

import base.panes.FadeLayer;
import game.board.Board;
import javafx.util.Duration;
import minigames.*;

public class BoardFade extends FadeLayer {
	
	public static final Duration FADE_IN = Duration.millis(500), FADE_OUT = FADE_IN;
	
	private Minigame minigame;
	private MinigameResult result;
	private boolean toMinigame;
	
	public BoardFade() {
		super(FADE_IN, FADE_OUT);
		toMinigame = true;
	}
	
	/** Returns {@code this}. */
	public BoardFade setDestMinigame(Minigame destMinigame) {
		this.minigame = destMinigame;
		return this;
	}
	
	@Override
	public void peakAction() {
		if(toMinigame) {
			minigame.start();
			MainScene.get().setBaseContent(minigame);
		}
		else {
			MainScene.get().setBaseContent(Board.get());
		}
	}
	
	@Override
	public void endAction() {
		if(!toMinigame)
			Board.get().minigameFinished(result);
	}
	
	public void toMinigame(Minigame minigame) {
		toMinigame = true;
		this.minigame = minigame;
		fader().fadeIn();
	}
	
	public void toBoard(MinigameResult result) {
		this.result = result;
		toMinigame = false;
		fader().fadeIn();
	}
	
}