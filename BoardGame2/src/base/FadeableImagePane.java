package base;

import javafx.animation.*;
import javafx.animation.Animation.Status;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class FadeableImagePane extends ImagePane {

	private ScaledPane removeFrom;
	private final FadeTransition fadeOut;
	
	public FadeableImagePane(Image image, Duration fadeOutDuration) {
		super(image);
		fadeOut = new FadeTransition(fadeOutDuration, this);
		fadeOut.setFromValue(1);
		fadeOut.setToValue(0);
		fadeOut.setOnFinished(eh -> fadeOutFinished());
	}
	
	/** @param removeFrom the {@link ScaledPane} to {@link ScaledPane#remove(ImagePane) remove} this
	 * {@link FadeableImagePane} from when it is completely faded out. */
	public void fadeOut(ScaledPane removeFrom) {
		this.removeFrom = removeFrom;
		fadeOut.playFromStart();
	}
	
	public boolean isFadingOut() {
		return fadeOut.getStatus() == Status.RUNNING;
	}
	
	private void fadeOutFinished() {
		removeFrom.remove(this);
	}
	
	public void makeFullyVisible() {
		fadeOut.stop();
		this.setOpacity(1);
	}
	
}
