package events.steal;

import base.panes.ImagePane;
import fxutils.*;

public class DontStealButton extends ImagePane {

	public DontStealButton() {
		super(Images.DONT_STEAL);
		setOnMouseEntered(me -> setImage(Images.DONT_STEAL_HOVERED));
		setOnMouseExited(me -> setImage(Images.DONT_STEAL));
	}
	
}
