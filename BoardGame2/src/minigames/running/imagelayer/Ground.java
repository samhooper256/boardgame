package minigames.running.imagelayer;

import java.util.*;

import base.panes.ImagePane;
import fxutils.Images;
import players.*;

final class Ground extends ImagePane implements PlayerNumbered, Alignable {

	public static final List<Ground> LIST =
			Collections.unmodifiableList(Arrays.asList(new Ground(1), new Ground(2), new Ground(3), new Ground(4)));
	
	public static Ground get(int number) {
		return LIST.get(Player.validate(number) - 1);
	}
	
	private final int number;
	
	private Ground(int number) {
		super(Images.RUNNING_GROUND);
		this.number = number;
	}

	@Override
	public void alignFor(int playerCount) {
		if(number() > playerCount)
			throw new IllegalArgumentException(String.format("number() > playerCount (%d > %d)", number(), playerCount));
		Coords c = Coords.p(playerCount);
		setIdealBottomY(c.bottom(number()));
	}	
	
	@Override
	public int number() {
		return number;
	}
	
}
