package game;

import javafx.animation.*;
import javafx.util.Duration;

public class ToPlayerSelectAnimation extends Transition {

	public static final Duration DURATION = Duration.millis(800);
	
	public ToPlayerSelectAnimation() {
		setCycleDuration(DURATION);
		setInterpolator(Interpolator.EASE_OUT);
	}

	@Override
	protected void interpolate(double frac) {
		MainScene.get().contentLayer().setLayoutY(frac * -MainScene.get().getHeight());
		MainScene.get().selectLayer().setLayoutY((1 - frac) * MainScene.get().getHeight());
	}

	@Override
	public void play() {
		MainScene.get().selectLayer().setVisible(true);
		super.play();
	}
	
}
