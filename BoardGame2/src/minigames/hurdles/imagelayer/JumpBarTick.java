package minigames.hurdles.imagelayer;

import java.util.*;

import base.panes.ImagePane;
import fxutils.Images;
import players.Player;

public class JumpBarTick extends ImagePane {

	public static final List<JumpBarTick> LIST = Collections.unmodifiableList(Arrays.asList(
			new JumpBarTick(1), new JumpBarTick(2), new JumpBarTick(3), new JumpBarTick(4)));

	public static JumpBarTick get(int number) {
		return LIST.get(Player.validate(number) - 1);
	}
	
	private final int number;
	
	private JumpBarTick(int number) {
		super(Images.JUMP_BAR_TICK);
		setIdealCenter(Coords.get().xCenter(number), Coords.get().tickCenterY());
		this.number = number;
	}
	
	public int number() {
		return number;
	}
	
}
