package minigames.rewards;

import fxutils.*;
import medals.*;

public class FadeableMedalIcon extends MedalIcon implements Fadeable {

	private static final double SCALE = .5;
	
	private final Fader fader;
	
	public FadeableMedalIcon(Medal medal) {
		super(medal, SCALE);
		fader = new Fader(this).setDurations(RewardsDisplay.FADE_TIME);
	}

	@Override
	public Fader fader() {
		return fader;
	}
	
}
