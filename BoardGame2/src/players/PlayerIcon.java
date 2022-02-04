package players;

import base.panes.ImagePane;
import fxutils.Images;

public class PlayerIcon extends ImagePane {

	private final int number;
	
	public PlayerIcon(int number) {
		super(Images.player(number));
		this.number = number;
	}
	
	public int number() {
		return number;
	}
	
}
