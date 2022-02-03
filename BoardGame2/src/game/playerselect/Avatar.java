package game.playerselect;

import base.panes.ImagePane;
import fxutils.Images;

public class Avatar extends ImagePane {

	public Avatar(int player) {
		super(Images.avatar(player));
	}
	
}
