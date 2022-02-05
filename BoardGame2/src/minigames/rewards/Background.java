package minigames.rewards;

import base.panes.ImagePane;
import fxutils.*;
import utils.Screen;

/** {@link #isVisible() Visible} by default. */
final class Background extends ImagePane implements Fadeable {

	public final Fader fader;
	
	public Background() {
		super(Images.REWARDS_BACKGROUND);
		fader = new Fader(this).setDurations(RewardsDisplay.FADE_TIME);
		Screen.center(this);
	}

	@Override
	public Fader fader() {
		return fader;
	}
	
	
}
