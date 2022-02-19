package minigames.running.imagelayer;

import base.panes.ImagePane;
import fxutils.Images;
import players.*;

final class Ground extends ImagePane implements PlayerNumbered, Alignable {

	public static final double HEIGHT = Images.RUNNING_GROUND_1.getHeight();
	
	private final int number;
	
	public Ground(int number, int variant) {
		super(Images.runningGround(variant));
		this.number = Player.validate(number);
	}

	@Override
	public void alignFor(int playerCount) {
		if(number() > playerCount)
			throw new IllegalArgumentException(String.format("number() > playerCount (%d > %d)", number(), playerCount));
		Coords c = Coords.p(playerCount);
		setIdealY(c.groundY(number));
	}	
	
	@Override
	public int number() {
		return number;
	}
	
}
