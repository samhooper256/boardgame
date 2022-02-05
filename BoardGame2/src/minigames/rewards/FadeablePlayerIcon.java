package minigames.rewards;

import fxutils.*;
import players.PlayerIcon;

public class FadeablePlayerIcon extends PlayerIcon implements Fadeable {
	
	private static final double SCALE = 2;
	
	private final Fader fader;
	
	public FadeablePlayerIcon(int player) {
		super(player);
		setIdealWidth(getIdealWidth() * SCALE);
		setIdealHeight(getIdealHeight() * SCALE);
		fader = new Fader(this).setDurations(RewardsDisplay.FADE_TIME);
	}

	@Override
	public Fader fader() {
		return fader;
	}
	
}
