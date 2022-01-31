package game;

import base.Updatable;
import base.panes.*;
import fxutils.Images;
import javafx.util.Duration;
import players.Player;

/** Rings are <em>not</em> {@link #isVisible() visible} by default. */
public class Ring extends FadeableImagePane implements Updatable {

	private static final double ANGULAR_VELOCITY = 15; //degrees/sec
	
	private static final Duration
			FADE_OUT_DURATION = Board.FADE_OUT_DURATION,
			FADE_IN_DURATION = Board.FADE_IN_DURATION;
	
	public Ring() {
		super(Images.RING, Images.RING.getWidth() * 1.5, Images.RING.getHeight() * 1.5);
		fader().setInDuration(FADE_IN_DURATION).setOutDuration(FADE_OUT_DURATION);
		fader().disappear();
	}
	
	public void lockCoordinatesTo(Player p) {
		this.idealXProperty().bind(p.idealXProperty().add(p.idealWidthProperty().multiply(.5))
				.subtract(idealWidthProperty().multiply(.5)));
		this.idealYProperty().bind(p.idealYProperty().add(p.idealHeightProperty().multiply(.5))
				.subtract(idealHeightProperty().multiply(.5)));
	}
	
	@Override
	public void update(long diff) {
		double sec = diff / 1e9;
		this.setRotate(this.getRotate() + ANGULAR_VELOCITY * sec);
	}
	
}