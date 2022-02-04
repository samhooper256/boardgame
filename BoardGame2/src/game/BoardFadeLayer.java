package game;

import base.panes.FadeLayer;
import fxutils.*;
import game.board.Board;
import javafx.util.Duration;
import minigames.*;

public class BoardFadeLayer extends FadeLayer implements Fadeable {
	
	public static final Duration FADE_IN_DURATION = Duration.millis(500), FADE_OUT_DURATION = FADE_IN_DURATION;
	
	private Minigame minigame;
	private MinigameResult result;
	private boolean toMinigame;
	
	public BoardFadeLayer() {
		super(FADE_IN_DURATION, FADE_OUT_DURATION);
		toMinigame = true;
	}
	
	/** Returns {@code this}. */
	public BoardFadeLayer setDestMinigame(Minigame destMinigame) {
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