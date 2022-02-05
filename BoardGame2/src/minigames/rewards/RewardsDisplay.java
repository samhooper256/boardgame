package minigames.rewards;

import java.util.*;

import base.panes.*;
import fxutils.Fadeable;
import javafx.util.Duration;
import minigames.MinigameResult;

/** The {@link #imagePanes()} used as part of this {@link RewardsDisplay} must be added to {@link ImageLayer} explicitly
 * for them to appear. */
public final class RewardsDisplay {
	
	public static final Duration FADE_TIME = Duration.millis(500);
	
	private final RewardsBackground background;
	private final List<ImagePane> imagePanes;
	
	public RewardsDisplay() {
		background = new RewardsBackground();
		imagePanes = Collections.unmodifiableList(Arrays.asList(background));
		for(ImagePane ip : imagePanes)
			ip.setVisible(false);
	}
	
	public void show(MinigameResult mr) {
		background.fader().fadeIn();
	}
	
	public void hide() {
		for(ImagePane ip : imagePanes)
			if(ip instanceof Fadeable)
				((Fadeable) ip).fader().disappear();
			else
				ip.setVisible(false);
	}
	
	public List<ImagePane> imagePanes() {
		return imagePanes;
	}
	
}
