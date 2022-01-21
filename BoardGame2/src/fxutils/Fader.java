package fxutils;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Fader extends Label {

	private final Node node;
	private final FadeTransition out;
	private final Duration outDuration;
	
	public Fader(Node node, Duration outDuration) {
		this.node = node;
		this.outDuration = outDuration;
		out = new FadeTransition(outDuration, node);
	}	
	
	public Node node() {
		return node;
	}
	
	public Duration outDuration() {
		return outDuration;
	}
	
	/** Fades out the {@link #node()}, then sets its {@link #visibleProperty() visibility} to {@code false} and its
	 * {@link #opacityProperty()} to {@code 1}. */
	public void fadeOutAndHide() {
		out.setOnFinished(eh -> {
			node.setVisible(false);	
			node.setOpacity(1);
		});
	}
	
}
