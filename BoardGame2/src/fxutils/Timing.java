package fxutils;

import javafx.animation.*;
import javafx.util.Duration;

public final class Timing {

	private Timing() {
		
	}
	
	public static void doAfterDelay(Duration delay, Runnable action) {
		Timeline t = new Timeline();
		t.getKeyFrames().add(new KeyFrame(delay, eh -> action.run()));
		t.playFromStart();
	}
	
}
