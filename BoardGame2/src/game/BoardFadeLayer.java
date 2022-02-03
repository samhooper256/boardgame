package game;

import fxutils.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import minigames.*;

public class BoardFadeLayer extends StackPane implements Fadeable {
	
	public static final Duration FADE_IN_DURATION = Duration.millis(500), FADE_OUT_DURATION = FADE_IN_DURATION;
	
	private final Fader fader;
	
	private Minigame minigame;
	private MinigameResult result;
	private boolean toMinigame;
	
	public BoardFadeLayer() {
		fader = new Fader(this).setInDuration(FADE_IN_DURATION).setOutDuration(FADE_OUT_DURATION)
				.setFadeInFinishedAction(this::peakAction).setFadeOutFinishedAction(this::endAction);
		setMouseTransparent(true);
		setBackground(Backgrounds.of(Color.WHITE));
		Nodes.setPrefSize(this, Double.MAX_VALUE);
		toMinigame = true;
		fader().disappear();
	}
	
	/** Returns {@code this}. */
	public BoardFadeLayer setDestMinigame(Minigame destMinigame) {
		this.minigame = destMinigame;
		return this;
	}
	
	private void peakAction() {
		if(toMinigame) {
			minigame.start();
			MainScene.get().setBaseContent(minigame);
		}
		else {
			MainScene.get().setBaseContent(Board.get());
		}
		fader().fadeOutAndHide();
	}
	
	private void endAction() {
		if(!toMinigame)
			Board.get().minigameFinished(result);
	}
	
	@Override
	public Fader fader() {
		return fader;
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