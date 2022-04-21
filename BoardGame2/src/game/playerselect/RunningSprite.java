package game.playerselect;

import java.util.*;

import base.panes.ImagePane;
import fxutils.Images;
import minigames.sprites.SpriteAnimated;
import minigames.sprites.SpriteAnimator;
import players.Player;
import utils.Colls;

public class RunningSprite extends ImagePane implements SpriteAnimated {

	public static final List<RunningSprite> LIST = Colls.ulist(
		new RunningSprite(1), new RunningSprite(2), new RunningSprite(3), new RunningSprite(4)
	);
	
	public static final RunningSprite forPlayer(int number) {
		return LIST.get(Player.validate(number) - 1);
	}
	
	private final int number;
	private final SpriteAnimator spriteAnimator;
	
	private RunningSprite(int number) {
		super(Images.stillSprite(number));
		this.number = number;
		spriteAnimator = new SpriteAnimator(this);
	}
	
	@Override
	public int number() {
		return number;
	}

	@Override
	public SpriteAnimator animator() {
		return spriteAnimator;
	}
	
	@Override
	public void update(long diff) {
		animator().update(diff);
	}

}
