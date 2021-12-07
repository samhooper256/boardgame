package game;

import java.util.function.*;

import base.ImagePane;
import fxutils.Images;
import javafx.animation.*;
import javafx.animation.Animation.Status;
import javafx.util.Duration;
import utils.RNG;

public class Die extends ImagePane {
	
	private static final Duration ROLL_DURATION = Duration.millis(3000);
	private static final int FACE_COUNT = 10;
	private static final IntFunction<Duration> DURATION_FUNCTION = i -> {
		return Duration.millis((ROLL_DURATION.toMillis()  / (FACE_COUNT * FACE_COUNT)) * (i * i));
	};
	
	private final Timeline timeline;
	
	private int currentFace;
	
	public Die() {
		super(Images.DIE0);
		currentFace = 0;
		timeline = new Timeline();
		for(int i = 0; i < FACE_COUNT; i++) {
			Duration duration = DURATION_FUNCTION.apply(i);
			timeline.getKeyFrames().add(new KeyFrame(duration, eh -> {
				setFace(differentFace());
			}));
		}
		Duration last = DURATION_FUNCTION.apply(FACE_COUNT - 1);
		timeline.getKeyFrames().add(new KeyFrame(last.add(Duration.seconds(1)), eh -> Board.get().executeTurn(face())));
		this.setOnMouseClicked(eh -> tryRoll());
	}
	
	public void tryRoll() {
		if(!isRolling())
			roll();
	}
	
	private void roll() {
		timeline.playFromStart();
	}
	
	/** Accepts an {@code int} from {@code 1} to {@code 6} (inclusive). */
	private void setFace(int face) {
		setImage(Images.die(face));
		currentFace = face;
	}
	
	private int differentFace() {
		int face;
		do
			face = RNG.intInclusive(1, 6);
		while(face == currentFace);
		return face;
	}
	
	/** Returns {@code true} if this die is ready to be rolled. */
	public boolean readyToRoll() {
		return !isRolling();
	}
	
	public boolean isRolling() {
		return timeline.getStatus() == Status.RUNNING;
	}

	public int face() {
		return currentFace;
	}
	
}
