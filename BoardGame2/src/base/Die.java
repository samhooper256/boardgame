package base;

import java.util.function.*;

import fxutils.Images;
import javafx.animation.*;
import javafx.util.Duration;

public class Die extends ImagePane {
	
	
	private static final Duration ROLL_DURATION = Duration.millis(2000);
	private static final int FACE_COUNT = 10;
	private static final IntFunction<Duration> DURATION_FUNCTION = i -> {
		return Duration.millis(FACE_COUNT * Math.sqrt(i) / Math.sqrt(ROLL_DURATION.toMillis()));
	};
	
	public Die() {
		super(Images.DIE0);
		this.setOnMouseClicked(eh -> {
			roll();
		});
	}
	
	public void roll() {
		System.out.println("roll");
		Timeline t = new Timeline();
		for(int i = 0; i < FACE_COUNT; i++) {
			t.getKeyFrames().add(new KeyFrame(DURATION_FUNCTION.apply(i), eh -> {
				
			}));
		}
	}
	
	/** Accepts an {@code int} from {@code 1} to {@code 6} (inclusive). */
	private void setFace(int face) {
		
	}
	

}
