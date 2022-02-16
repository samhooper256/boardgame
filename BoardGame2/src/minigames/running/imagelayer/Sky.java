package minigames.running.imagelayer;

import java.util.*;

import base.panes.ImagePane;
import fxutils.Images;
import players.*;

final class Sky extends ImagePane implements PlayerNumbered {

	public static final List<Sky> LIST =
			Collections.unmodifiableList(Arrays.asList(new Sky(1), new Sky(2), new Sky(3), new Sky(4)));
	
	public static Sky get(int number) {
		return LIST.get(Player.validate(number) - 1);
	}
	
	private final int number;
	
	private Sky(int number) {
		super(Images.RUNNING_SKY);
		this.number = number;
	}

	void alignFor(int playerCount) {
		if(number() > playerCount)
			throw new IllegalArgumentException(String.format("number() > playerCount (%d > %d)", number(), playerCount));
		Coords c = Coords.p(playerCount);
		setIdealHeight(c.zoneHeight());
		setIdealY(c.top(number));
	}
	
	@Override
	public int number() {
		return number;
	}
	
}
