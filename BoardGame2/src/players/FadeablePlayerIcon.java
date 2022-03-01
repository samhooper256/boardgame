package players;

import fxutils.*;
import javafx.util.Duration;

public class FadeablePlayerIcon extends PlayerIcon implements Fadeable {
	
	private final Fader fader;
	
	public FadeablePlayerIcon(int player, Duration durations) {
		super(player);
		fader = new Fader(this).setDurations(durations);
	}

	@Override
	public Fader fader() {
		return fader;
	}
	
}
