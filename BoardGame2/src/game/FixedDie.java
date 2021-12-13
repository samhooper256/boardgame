package game;

import base.ImagePane;
import fxutils.Images;

public final class FixedDie extends ImagePane {
	
	private static final FixedDie[] DICE = new FixedDie[7];
	
	static {
		for(int i = 0; i < DICE.length; i++)
			DICE[i] = new FixedDie(i);
	}
	
	public static FixedDie showing(int face) {
		return DICE[face];
	}
	
	private final int face;
	
	private FixedDie(int face) {
		super(Images.die(face));
		this.face = face;
	}
	
	/** Returns {@code 0} for the base die. */
	public int face() {
		return face;
	}
	
	
}
