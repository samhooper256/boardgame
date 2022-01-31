package game;

import base.panes.*;
import fxutils.Images;
import players.RollType;

public final class FixedDie extends ImagePane implements Die {
	
	private static final double HORIZONTAL_CENTER_DIST = 100, VERTICAL_CENTER_DIST = 140;
	
	private static final FixedDie[] DICE = new FixedDie[Die.FACES + 1];
	
	static {
		double hc = ScaledImageLayer.DEFAULT_WIDTH / 2, vc = ScaledImageLayer.DEFAULT_HEIGHT / 2,
				hcd = HORIZONTAL_CENTER_DIST, vcd = VERTICAL_CENTER_DIST;
		DICE[1] = new FixedDie(1, hc - hcd, vc - vcd);
		DICE[2] = new FixedDie(2, hc + hcd, vc - vcd);
		DICE[3] = new FixedDie(3, hc - hcd, vc);
		DICE[4] = new FixedDie(4, hc + hcd, vc);
		DICE[5] = new FixedDie(5, hc - hcd, vc + vcd);
		DICE[6] = new FixedDie(6, hc + hcd, vc + vcd);
	}
	
	public static FixedDie showing(int face) {
		return DICE[face];
	}
	
	private final int face;
	/** The coordinates of this {@link FixedDie} when the {@link Board} is showing a {@link RollType#CHOOSE choose}
	 * roll. */
	private final double choiceX, choiceY;
	
	private FixedDie(int face, double choiceXCenter, double choiceYCenter) {
		super(Images.die(face));
		this.face = face;
		this.choiceX = choiceXCenter;
		this.choiceY = choiceYCenter;
		setOnMouseClicked(eh -> clickAction());
	}
	
	/** Returns {@code 0} for the base die. */
	@Override
	public int face() {
		return face;
	}

	public void clickAction() {
		if(Board.get().readyToRoll())
			Board.get().executeTurn(face);
	}
	
	public double choiceX() {
		return choiceX - getIdealWidth() / 2;
	}
	
	public double choiceY() {
		return choiceY - getIdealHeight() / 2;
	}
	
}
