package game;

import base.ImagePane;
import fxutils.Images;

public final class FixedDie extends ImagePane {
		
	private final int face;
	
	private FixedDie(int face) {
		super(Images.die(face));
		this.face = face;
	}
	
	public int face() {
		return face;
	}
	
}
