package events;

import java.util.*;

import base.panes.ImagePane;
import javafx.scene.Node;

public abstract class AbstractComplexEvent extends AbstractEvent implements ComplexEvent {

	protected final List<ImagePane> imagePanes;
	protected final List<Node> fxNodes;
	
	protected AbstractComplexEvent(EventTag tag) {
		super(tag);
		imagePanes = new ArrayList<>();
		fxNodes = new ArrayList<>();
	}
	
	@Override
	public List<ImagePane> imagePanes() {
		return imagePanes;
	}

	@Override
	public List<Node> fxNodes() {
		return fxNodes;
	}
	
}
