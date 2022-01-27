package base.panes;

import fxutils.*;
import javafx.scene.image.Image;

public class FadeableImagePane extends ImagePane implements Fadeable {

	private final Fader fader;
	
	public FadeableImagePane(Image image) {
		super(image);
		fader = new Fader(this);
	}
	
	public FadeableImagePane(Image image, double idealWidth, double idealHeight) {
		super(image, idealWidth, idealHeight);
		fader = new Fader(this);
	}
	
	@Override
	public Fader fader() {
		return fader;
	}
	
}
