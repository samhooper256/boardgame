package game;

import base.*;
import fxutils.Images;

public final class FixedDie extends ImagePane {
	
	private static final double HORIZONTAL_CENTER_DIST = 100, VERTICAL_CENTER_DIST = 140;
	
	private static final FixedDie[] DICE = new FixedDie[7];
	
	static {
		for(int i = 0; i < DICE.length; i++)
			DICE[i] = new FixedDie(i);
		double hc = ScaledPane.DEFAULT_WIDTH / 2, vc = ScaledPane.DEFAULT_HEIGHT / 2,
				hcd = HORIZONTAL_CENTER_DIST, vcd = VERTICAL_CENTER_DIST;
		showing(1).setIdealCenter(hc - hcd, vc - vcd);
		showing(2).setIdealCenter(hc + hcd, vc - vcd);
		showing(3).setIdealCenter(hc - hcd, vc);
		showing(4).setIdealCenter(hc + hcd, vc);
		showing(5).setIdealCenter(hc - hcd, vc + vcd);
		showing(6).setIdealCenter(hc + hcd, vc + vcd);
	}
	
	public static FixedDie showing(int face) {
		return DICE[face];
	}
	
	private final int face;
	
	private FixedDie(int face) {
		super(Images.die(face));
		this.face = face;
		setOnMouseClicked(eh -> clickAction());
	}
	
	/** Returns {@code 0} for the base die. */
	public int face() {
		return face;
	}

	public void clickAction() {
		if(!Board.get().turnRunning())
			Board.get().executeTurn(face);
	}
	
}
