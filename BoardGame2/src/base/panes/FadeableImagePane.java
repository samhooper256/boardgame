package base.panes;

import fxutils.*;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class FadeableImagePane extends ImagePane implements Fadeable {

	private final Fader fader;
	
	public FadeableImagePane(Image image, Duration fadeOutDuration) {
		this(image, fadeOutDuration, null);
	}
	
	public FadeableImagePane(Image image, Duration fadeOutDuration, Runnable fadeOutFinishedAction) {
		super(image);
		fader = new Fader(this, fadeOutDuration, fadeOutFinishedAction);
	}
	
	@Override
	public Fader fader() {
		return fader;
	}
	
}
