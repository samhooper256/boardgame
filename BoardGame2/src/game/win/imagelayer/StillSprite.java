package game.win.imagelayer;

import java.util.List;

import base.panes.ImagePane;
import fxutils.Images;
import players.Player;
import players.PlayerNumbered;
import utils.Colls;

public class StillSprite extends ImagePane implements PlayerNumbered {
	
	private static final double SCALE_FACTOR = 1.5;
	
	public static final List<StillSprite> LIST = Colls.ulist(
		new StillSprite(1), new StillSprite(2), new StillSprite(3), new StillSprite(4)
	);
	
	public static StillSprite forPlayer(PlayerNumbered pn) {
		return forPlayer(pn.number());
	}
	
	public static StillSprite forPlayer(int number) {
		return LIST.get(Player.validate(number) - 1);
	}
	
	private final int number;
	
	private StillSprite(int number) {
		super(Images.stillSprite(number));
		setIdealSize(getIdealWidth() * SCALE_FACTOR, getIdealHeight() * SCALE_FACTOR);
		this.number = number;
	}
	
	@Override
	public int number() {
		return number;
	}
	
}
