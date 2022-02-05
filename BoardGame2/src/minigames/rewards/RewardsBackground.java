package minigames.rewards;

import base.panes.ImagePane;
import fxutils.*;

/** {@link #isVisible() Visible} by default. */
public final class RewardsBackground extends ImagePane implements Fadeable {

	public final Fader fader;
	
	public RewardsBackground() {
		super(Images.REWARDS_BACKGROUND);
		fader = new Fader(this).setDurations(RewardsDisplay.FADE_TIME);
	}

	@Override
	public Fader fader() {
		return fader;
	}
	
	
}
