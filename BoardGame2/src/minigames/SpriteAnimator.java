package minigames;

import base.Updatable;
import base.panes.ImagePane;
import fxutils.Images;
import javafx.scene.image.Image;

public final class SpriteAnimator implements Updatable {
	
	/** in nanoseconds */
	private static final long CYCLE_TIME = (long) .5e9;
	
	private final ImagePane imagePane;
	private final int number;
	
	private long cycleProgress;
	private boolean running;
	
	public SpriteAnimator(ImagePane imagePane, int number) {
		this.imagePane = imagePane;
		this.number = number;
		cycleProgress = 0;
	}
	
	@Override
	public void update(long diff) {
		if(isRunning()) {
			cycleProgress += diff;
			cycleProgress %= (long) CYCLE_TIME;
			imagePane().setImage(sprite(spriteIndex()));
		}
	}
	
	/** Pauses this {@link SpriteAnimator} ({@link #isRunning()} will be {@code false}). The {@link #imagePane()} image
	 * will be whatever it was at the moment this method was called. */
	public void pause() {
		running = false;
	}
	
	/** Pauses this {@link SpriteAnimator} ({@link #isRunning()} will be {@code false}) and sets the
	 * {@link #imagePane()} image to {@code image}. Assumes {@link Image} is an appropriate
	 * {@link Images#sprite(int, int) sprite} image. */
	public void pauseTo(Image image) {
		pause();
		imagePane().setImage(image);
	}
	
	/** {@link #pauseTo(Image) pauses to} the {@link #stillSprite()}. */
	public void pauseToStill() {
		pauseTo(stillSprite());
	}
	
	/** If the {@link SpriteAnimator} {@link #isRunning()}, it will still be running after this method is called.
	 * The {@link #imagePane()} image is therefore set to the first in the cycle immediately after this method is
	 * called (whether or not {@code this} was running). */
	public void resetProgress() {
		cycleProgress = 0;
		imagePane().setImage(sprite(0));
	}
	
	/** {@link #resetProgress() resets progress} and stops the {@link SpriteAnimator}. Sets the {@link #imagePane()}
	 * image to the first in the cycle. */
	public void stopAndReset() {
		running = false;
		resetProgress();
	}
	
	/** {@link #pause() pauses}, resets the {@link #cycleProgress()}, and sets the {@link #imagePane()} image to
	 * {@code image}. Assumes {@link Image} is an appropriate {@link Images#sprite(int, int) sprite} image. */
	public void stopAndResetTo(Image image) {
		running = false;
		cycleProgress = 0;
		imagePane().setImage(image);
	}
	
	/** Resumes this {@link SpriteAnimator} wherever {@link #cycleProgress()} left off. Does not change the
	 * {@link #imagePane()} image. */
	public void play() {
		running = true;
	}
	
	/** Equivalent to {@code play(); update(diff);}*/
	public void playAndUpdate(long diff) {
		play();
		update(diff);
	}
	
	/** Equivalent to {@link #resetProgress()} but ensures {@link #isRunning()} is {@code true} when the method
	 * returns. */
	public void restart() {
		resetProgress();
		running = true;
	}
	
	public ImagePane imagePane() {
		return imagePane;
	}
	
	public Image sprite(int n) {
		return Images.sprite(number, n);
	}
	
	public Image stillSprite() {
		return Images.stillSprite(number);
	}
	
	public int spriteIndex() {
		return (int) (cycleProgress / (CYCLE_TIME * .25));
	}
	
	public int number() {
		return number;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	/** The number of nanoseconds out of {@link #CYCLE_TIME} that have elapsed from the start of the current
	 * animation loop. */
	public long cycleProgress() {
		return cycleProgress;
	}
	
}
