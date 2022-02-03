package fxutils;

import javafx.animation.FadeTransition;
import javafx.animation.Animation.Status;
import javafx.scene.Node;
import javafx.util.Duration;

/** This class has setter methods that return {@code this}. {@link #inDuration()} and {@link #outDuration()} are set to
 * {@link Duration#ZERO} by default. */
public final class Fader {

	private final Node node;
	private final FadeTransition in, out;
	
	private Runnable fadeOutFinishedAction;
	private Runnable fadeInFinishedAction;
	
	public Fader(Node node) {
		this(node, null, null);
	}	
	
	public Fader(Node node, Runnable fadeInFinishedAction, Runnable fadeOutFinishedAction) {
		this.node = node;
		this.fadeInFinishedAction = fadeInFinishedAction;
		this.fadeOutFinishedAction = fadeOutFinishedAction;
		out = new FadeTransition(Duration.ZERO, node);
		out.setFromValue(1);
		out.setToValue(0);
		out.setOnFinished(eh -> {
			node.setVisible(false);	
			node.setOpacity(1);
			run(fadeOutFinishedAction());
		});
		in = new FadeTransition(Duration.ZERO, node);
		in.setFromValue(0);
		in.setToValue(1);
		in.setOnFinished(eh -> {
			//node is already visible and opacity is 1.
			run(fadeInFinishedAction());
		});
	}
	
	public Node node() {
		return node;
	}
	
	public Duration inDuration() {
		return in.getDuration();
	}
	
	public Fader setInDuration(Duration inDuration) {
		in.setDuration(inDuration);
		return this;
	}
	
	public Duration outDuration() {
		return out.getDuration();
	}
	
	public Fader setOutDuration(Duration outDuration) {
		out.setDuration(outDuration);
		return this;
	}
	
	/** Fades out the {@link #node()}, then sets its {@link #visibleProperty() visibility} to {@code false}, sets its
	 * {@link #opacityProperty()} to {@code 1}, and runs the {@link #fadeOutFinishedAction()}. */
	public void fadeOutAndHide() {
		stop();
		node.setOpacity(1);
		node.setVisible(true);
		out.playFromStart();
	}
	
	public void fadeIn() {
		stop();
		node.setOpacity(0);
		node.setVisible(true);
		in.playFromStart();
	}
	
	public void disappear() {
		stop();
		node.setVisible(false);
		node.setOpacity(1);
	}
	
	public void appear() {
		stop();
		node.setOpacity(1);
		node.setVisible(true);
	}
	
	public void stop() {
		in.stop();
		out.stop();
	}
	
	public Fader setFadeOutFinishedAction(Runnable fadeOutFinishedAction) {
		this.fadeOutFinishedAction = fadeOutFinishedAction;
		return this;
	}
	
	public Runnable fadeOutFinishedAction() {
		return fadeOutFinishedAction;
	}
	
	public Fader setFadeInFinishedAction(Runnable fadeInFinishedAction) {
		this.fadeInFinishedAction = fadeInFinishedAction;
		return this;
	}
	
	public Runnable fadeInFinishedAction() {
		return fadeInFinishedAction;
	}
	
	public boolean isFadingOut() {
		return out.getStatus() == Status.RUNNING;
	}
	
	public boolean isFadingIn() {
		return in.getStatus() == Status.RUNNING;
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
