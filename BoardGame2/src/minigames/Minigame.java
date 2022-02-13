package minigames;

import base.*;
import base.input.GameInput;
import base.panes.*;
import game.*;
import javafx.scene.input.KeyCode;
import minigames.rewards.RewardsDisplay;
import players.list.*;

public abstract class Minigame extends GamePane implements Updatable {
	
	protected ReadOnlyPlayerList players;
	protected PlayerList playersRemaining;
	
	private final MiniTag tag;
	private final RewardsDisplay rewardsDisplay;
	
	private MinigameResult result;
	
	public Minigame(MiniTag tag, MinigameImageLayer imageLayer, FXLayer fxLayer) {
		super(imageLayer, fxLayer);
		this.tag = tag;
		rewardsDisplay = new RewardsDisplay();
		result = null;
		imageLayer.setGamePane(this);
		fxLayer.setGamePane(this);
		imageLayer().initRewardsDisplay();
		fxLayer().initRewardsDisplay();
	}
	
	/** <p>Assumes this {@link Minigame} has already been set as the {@link MainScene#baseContent()}. This method is
	 * called at the "peak" of the {@link BoardFade BoardFadeLayer's} transition (that is, when the player cannot
	 * see the minigame or the board).</p>
	 * 
	 * <p>This method will {@link RewardsDisplay#hide() hide} the {@link #rewardsDisplay()} before calling
	 * {@link #startMinigame()}.</p> */
	public final void start() {
		result = null;
		rewardsDisplay().hide();
		startMinigame();
	}
	
	/** Called by {@link #start()}. This method must call {@link MinigameImageLayer#start()}. */
	public abstract void startMinigame();
	
	/** If {@code true}, the {@link #rewardsDisplay()} is showing*/
	public abstract boolean isFinished();
	
	/** @throws IllegalStateException if not {@link #isFinished() finished}.*/
	public final MinigameResult getResult() {
		if(!isFinished())
			throw new IllegalStateException("Not finished");
		if(result == null)
			result = computeResult();
		return result;
	}
	
	/** This method can assume that this {@link Minigame} is {@link #isFinished() finished}.*/
	protected abstract MinigameResult computeResult();
	
	/** Called when the instructions fully disappear and this {@link Minigame} starts being playable. No-op by
	 * default. */
	public abstract void ingameStarted();
	
	@Override
	public final void keyPressed(KeyCode kc) {
		if(imageLayer().instructionsShowing()) {
			if(kc == GameInput.controls().next() && imageLayer().instructionsReadyToBeHidden())
				imageLayer().fadeOutInstructions();
		}
		else if(isFinished()) {
			if(kc == GameInput.controls().next())
				MainScene.get().fadeBackFromMinigame(getResult());
		}
		else {
			keyPressedIngame(kc);
		}
	}
	
	public abstract void keyPressedIngame(KeyCode kc);
	
	@Override
	public final void keyReleased(KeyCode kc) {
		if(isIngame())
			keyReleasedIngame(kc);
	}
	
	public abstract void keyReleasedIngame(KeyCode kc);
	
	
	public boolean isIngame() {
		return !imageLayer().instructionsShowing() && !isFinished();
	}
	
	public MiniTag tag() {
		return tag;
	}
	
	public RewardsDisplay rewardsDisplay() {
		return rewardsDisplay;
	}
	
	@Override
	public MinigameImageLayer imageLayer() {
		return (MinigameImageLayer) super.imageLayer();
	}
	
	@Override
	public MinigameFXLayer fxLayer() {
		return (MinigameFXLayer) super.fxLayer();
	}
	
	public ReadOnlyPlayerList players() {
		return players;
	}
	
	public PlayerList playersRemaining() {
		return playersRemaining;
	}

	/** @throws IllegalStateException if there is more than one player alive. */
	public int getOnlyPlayerRemaining() {
		if(playersRemaining().size() != 1)
			throw new IllegalStateException("Multiple players are still alive");
		return playersRemaining().get(0);
	}
	
}
