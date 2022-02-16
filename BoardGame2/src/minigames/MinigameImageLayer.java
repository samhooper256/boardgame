package minigames;

import base.AcceptsInput;
import base.panes.*;
import fxutils.Images;
import game.*;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import minigames.rewards.RewardsDisplay;

/** <p>{@link #initRewardsDisplay()} must be called after construction.</p>
 * <p>The background is at the bottom of packet {@code 0}. The instructions occupy
 * {@link ImageLayer#packetsUnmodifiable() packet} {@code 1}. The {@link RewardsDisplay}
 * {@link RewardsDisplay#imagePanes() image panes} are in packet {@code 2}. The {@link Helper} is in packet {@code 3}.
 * </p>*/
public abstract class MinigameImageLayer extends AbstractImageLayer implements AcceptsInput {

	public static final Duration INSTRUCTIONS_FADE_OUT_DURATION = Duration.millis(300);
	
	protected final FadeableImagePane instructions, pressSpace;
	
	private final Helper helper;
	
	protected MinigameImageLayer(MiniTag tag) {
		if(tag.hasBackground())
			add(new ImagePane(tag.background()));
		helper = new Helper();
		instructions = new FadeableImagePane(tag.instructions());
		instructions.fader().setOutDuration(INSTRUCTIONS_FADE_OUT_DURATION);
		instructions.setIdealCenter(MainScene.CENTER_X, MainScene.CENTER_Y);
		instructions.fader().setFadeOutFinishedAction(() -> gamePane().ingameStarted());
		pressSpace = new FadeableImagePane(Images.PRESS_SPACE);
		pressSpace.fader().setOutDuration(INSTRUCTIONS_FADE_OUT_DURATION);
		pressSpace.setIdealCenter(MainScene.CENTER_X, MainScene.DEFAULT_HEIGHT * .8);
		addAll(1, instructions, pressSpace);
		add(3, helper());
	}
	
	final void initRewardsDisplay() {
		addAll(2, gamePane().rewardsDisplay().imagePanes());
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
	
	protected Helper helper() {
		return helper;
	}
	
	public FadeableImagePane instructions() {
		return instructions;
	}
	
	public FadeableImagePane pressSpace() {
		return pressSpace;
	}
	
}