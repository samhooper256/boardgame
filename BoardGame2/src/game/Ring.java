package game;

import base.panes.*;
import fxutils.Images;
import javafx.util.Duration;
import players.Player;

public class Ring extends FadeableImagePane {

	private static final Duration
			FADE_OUT_DURATION = Board.FADE_OUT_DURATION,
			FADE_IN_DURATION = Board.FADE_IN_DURATION;
	
	public Ring() {
		super(Images.RING, Images.RING.getWidth() * 1.5, Images.RING.getHeight() * 1.5);
		fader().setInDuration(FADE_IN_DURATION).setOutDuration(FADE_OUT_DURATION);
	}
	
	public void lockCoordinatesTo(Player p) {
		this.idealXProperty().bind(p.idealXProperty().add(p.idealWidthProperty().multiply(.5))
				.subtract(idealWidthProperty().multiply(.5)));
		this.idealYProperty().bind(p.idealYProperty().add(p.idealHeightProperty().multiply(.5))
				.subtract(idealHeightProperty().multiply(.5)));
	}
	
}