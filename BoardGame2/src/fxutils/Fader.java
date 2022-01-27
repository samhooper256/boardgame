package fxutils;

import javafx.animation.FadeTransition;
import javafx.animation.Animation.Status;
import javafx.scene.Node;
import javafx.util.Duration;

public final class Fader {

	private final Node node;
	private final FadeTransition out;
	private final Duration outDuration;
	
	private Runnable fadeOutFinishedAction;
	
	public Fader(Node node, Duration outDuration) {
		this(node, outDuration, null);
	}	
	
	public Fader(Node node, Duration outDuration, Runnable fadeOutFinishedAction) {
		this.node = node;
		this.outDuration = outDuration;
		this.fadeOutFinishedAction = fadeOutFinishedAction;
		out = new FadeTransition(outDuration, node);
		out.setFromValue(1);
		out.setToValue(0);
		out.setOnFinished(eh -> {
			node.setVisible(false);	
			node.setOpacity(1);
			run(fadeOutFinishedAction());
		});
	}
	
	public Node node() {
		return node;
	}
	
	public Duration outDuration() {
		return outDuration;
	}
	
	/** Fades out the {@link #node()}, then sets its {@link #visibleProperty() visibility} to {@code false}, sets its
	 * {@link #opacityProperty()} to {@code 1}, and runs the {@link #fadeOutFinishedAction()}. */
	public void fadeOutAndHide() {
		out.playFromStart();
	}
	
	public void appear() {
		out.stop();
		node.setOpacity(1);
		node.setVisible(true);
	}
	
	public void setFadeOutFinishedAction(Runnable fadeOutFinishedAction) {
		this.fadeOutFinishedAction = fadeOutFinishedAction;
	}
	
	public Runnable fadeOutFinishedAction() {
		return fadeOutFinishedAction;
	}
	
	public boolean isFadingOut() {
		return out.getStatus() == Status.RUNNING;
	}
	
	/** Returns the equivalent of {@code (node().isVisible() && node().getOpacity() > 0)}. In other words, returns
	 * {@code true} iff the {@link #node()} is showing to the user, even if it is in the process of fading in/out. */
	public boolean isShowing() {
		return node().isVisible() && node().getOpacity() > 0;
	}
	
	private void run(Runnable r) {
		if(r != null)
			r.run();
	}
	
}
