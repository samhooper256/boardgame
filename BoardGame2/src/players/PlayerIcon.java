package players;

import base.panes.ImagePane;
import fxutils.Images;

public class PlayerIcon extends ImagePane implements PlayerRepresentation {

	private final int number;
	
	public PlayerIcon(int number) {
		super(Images.player(number));
		this.number = number;
	}
	
	@Override
	public int number() {
		return number;
	}
	
}
