package base;

import java.util.function.*;

import fxutils.Images;
import javafx.animation.*;
import javafx.util.Duration;
import utils.RNG;

public class Die extends ImagePane {
	
	
	private static final Duration ROLL_DURATION = Duration.millis(3000);
	private static final int FACE_COUNT = 10;
	private static final IntFunction<Duration> DURATION_FUNCTION = i -> {
		return Duration.millis((ROLL_DURATION.toMillis()  / (FACE_COUNT * FACE_COUNT)) * (i * i));
	};
	
	private int currentFace;
	
	public Die() {
		super(Images.DIE0);
		currentFace = 0;
		this.setOnMouseClicked(eh -> {
			roll();
		});
	}
	
	public void roll() {
		System.out.println("roll");
		Timeline t = new Timeline();
		for(int i = 0; i < FACE_COUNT; i++) {
			Duration duration = DURATION_FUNCTION.apply(i);
			System.out.println(duration.toMillis());
			t.getKeyFrames().add(new KeyFrame(duration, eh -> {
				setFace(differentFace());
			}));
		}
		t.playFromStart();
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

}
