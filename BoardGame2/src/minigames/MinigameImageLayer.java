package minigames;

import base.AcceptsInput;
import base.panes.*;
import fxutils.Images;
import game.MainScene;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public abstract class MinigameImageLayer extends AbstractImageLayer implements AcceptsInput {

	public static final Duration INSTRUCTIONS_FADE_OUT_DURATION = Duration.millis(300);
	
	protected final FadeableImagePane instructions, pressSpace;
	
	protected MinigameImageLayer(MiniTag tag) {
		add(new ImagePane(tag.background()));
		instructions = new FadeableImagePane(tag.instructions());
		instructions.fader().setOutDuration(INSTRUCTIONS_FADE_OUT_DURATION);
		instructions.setIdealCenter(MainScene.CENTER_X, MainScene.CENTER_Y);
		instructions.fader().setFadeOutFinishedAction(() -> gamePane().ingameStarted());
		pressSpace = new FadeableImagePane(Images.PRESS_SPACE);
		pressSpace.fader().setOutDuration(INSTRUCTIONS_FADE_OUT_DURATION);
		pressSpace.setIdealCenter(MainScene.CENTER_X, MainScene.DEFAULT_HEIGHT * .8);
		addAll(1, instructions, pressSpace);
	}
	
	/** Calls {@link #startMinigame()} then {@link #showInstructions()}. */
	public final void start() {
		startMinigame();
		showInstructions();
	}
	
	public abstract void startMinigame();
	
	public void showInstructions() {
		instructions().fader().appear();
		pressSpace().fader().appear();
	}
	
	public void fadeOutInstructions() {
		instructions().fader().fadeOutAndHide();
		pressSpace().fader().fadeOutAndHide();
	}
	
	/** {@code true} if the instructions are showing, even if they are in the process of fading out. */
	public boolean instructionsShowing() {
		return instructions().isVisible() && instructions().getOpacity() > 0;
	}
	
	@Override
	public final void keyPressed(KeyCode kc) {
		keyPressedIngame(kc);
	}

	public boolean instructionsReadyToBeHidden() {
		return instructionsShowing() && !instructions.fader().isFadingOut();
	}
	
	public abstract void keyPressedIngame(KeyCode kc);
	
	@Override
	public final void keyReleased(KeyCode kc) {
		keyReleasedIngame(kc);
	}
	
	public abstract void keyReleasedIngame(KeyCode kc);
	
	@Override
	public final void updatePane(long diff) {
		if(gamePane().isIngame())
			updateIngame(diff);
	}
	
	public abstract void updateIngame(long diff);
	
	@Override
	public Minigame gamePane() {
		return (Minigame) super.gamePane();
	}
	
	public FadeableImagePane instructions() {
		return instructions;
	}
	
	public FadeableImagePane pressSpace() {
		return pressSpace;
	}
	
}