package base;

import javafx.animation.*;
import javafx.scene.image.Image;

public class FadeableImagePane extends ImagePane {

	private ScaledPane removeFrom;
	private final FadeTransition fadeOut = new FadeTransition();
	
	public FadeableImagePane(Image image) {
		super(image);
		fadeOut.setFromValue(1);
		fadeOut.setToValue(0);
		fadeOut.setOnFinished(eh -> fadeOutFinished());
	}
	
	/** @param removeFrom the {@link ScaledPane} to {@link ScaledPane#remove(ImagePane) remove} this
	 * {@link FadeableImagePane} from when it is completely faded out. */
	public void fadeOut(ScaledPane removeFrom) {
		this.removeFrom = removeFrom;
	}
	
	private void fadeOutFinished() {
		removeFrom.remove(this);
	}
	
	public void makeFullyVisible() {
		fadeOut.stop();
		this.setOpacity(1);
	}
	
}
