package minigames.hurdles.imagelayer;

import java.util.*;

import base.panes.ImagePane;
import fxutils.Images;

public class JumpBarTick extends ImagePane {

	public static final double Y = JumpBar.Y + JumpBar.HEIGHT * .2;
	
	public static final List<JumpBarTick> LIST = Collections.unmodifiableList(Arrays.asList(
			new JumpBarTick(1), new JumpBarTick(2), new JumpBarTick(3), new JumpBarTick(4)));
	
	private final int number;
	
	private JumpBarTick(int number) {
		super(Images.JUMP_BAR_TICK);
		setIdealCenter(Coords.get().xCenter(number), Y);
		this.number = number;
	}
	
	public int number() {
		return number;
	}
	
}
