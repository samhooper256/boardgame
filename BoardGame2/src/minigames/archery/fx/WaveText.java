package minigames.archery.fx;

import fxutils.*;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class WaveText extends Label implements Fadeable {

	private static final Duration OUT_DURATION = Duration.millis(500);
	private static final String CSS = "wave-text";
	
	private final Fader fader;
	
	private int wave;
	
	public WaveText() {
		this(1);
	}
	
	public WaveText(int wave) {
		this.wave = wave;
		setText(wave);
		getStyleClass().add(CSS);
		fader = new Fader(this, OUT_DURATION);
	}

	public void setWave(int wave) {
		this.wave = wave;
		setText(wave);
	}
	
	public int getWave() {
		return wave;
	}
	
	private void setText(int wave) {
		this.setText(String.format("Wave %d", wave));
	}
	
	@Override
	public Fader fader() {
		return fader;
	}
	
}
