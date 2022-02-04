package base.panes;

import fxutils.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public abstract class FadeLayer extends StackPane implements Fadeable {

	private final Fader fader;
	
	public FadeLayer(Duration in, Duration out) {
		fader = new Fader(this).setInDuration(in).setOutDuration(out)
				.setFadeInFinishedAction(this::peakActionWithFadeOut).setFadeOutFinishedAction(this::endAction);
		setMouseTransparent(true);
		setBackground(Backgrounds.of(Color.WHITE));
		Nodes.setPrefSize(this, Double.MAX_VALUE);
		fader().disappear();
	}
	
	@Override
	public Fader fader() {
		return fader;
	}
	
	private void peakActionWithFadeOut() {
		peakAction();
		fader().fadeOutAndHide();
	}
	
	protected abstract void peakAction();
	
	protected abstract void endAction();
	
}
